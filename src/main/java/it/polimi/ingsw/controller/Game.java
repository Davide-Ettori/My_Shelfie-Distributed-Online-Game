package it.polimi.ingsw.controller;


import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.IP;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * class which represent the instance of the current game
 * @author Ettori Faccincani
 * in theory it is mutable, but it is only instanced one time, at the start of the server
 */
public class Game extends UnicastRemoteObject implements Serializable, GameI {
    /** variable that represent the standard timer of the app for advancing the state of the game */
    public static final double waitTimer = 2.5;
    /** variable that represent the fast timer of the app for small waiting task */
    public static final double fastTimer = 1;
    /** variable that represent the timer for the new turn (changing) interaction */
    public static final double passTimer = 1;
    /** variable that represent the standard timer of the app for showing events */
    public static final double showTimer = 2.5;
    /** variable that represent if we want to run or debug our application */
    public static boolean showErrors = false;
    private final int targetPlayers;
    private int numPlayers;
    private int activePlayer = 0;
    private ArrayList<PlayerSend> players = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private final transient ArrayList<Socket> playersSocket = new ArrayList<>();
    private final transient ArrayList<ObjectOutputStream> outStreams = new ArrayList<>();
    private final transient ArrayList<ObjectInputStream> inStreams = new ArrayList<>();
    private final ArrayList<CommonObjective> bucketOfCO = Initializer.setBucketOfCO();
    private final ArrayList<PrivateObjective> bucketOfPO = Initializer.setBucketOfPO();
    private boolean endGameSituation = false;
    private boolean timeExp = true;
    private transient ArrayList<Thread> chatThreads = new ArrayList<>();
    private transient ServerSocket serverSocket;
    private transient boolean closed = false;
    private final transient HashMap<String, PlayerI> rmiClients = new HashMap<>();
    private transient Game gameTemp = null;
    private final transient ArrayList<String> disconnectedPlayers = new ArrayList<>();
    private boolean advance = false; //true iif the server has to force a new turn after resilience activation
    private final transient Object waitMoveLock = new Object();
    private final transient Object disconnectionLock = new Object();
    private transient String playerNoChat = "";
    /**
     * normal constructor for this type of object, this class is also the main process on the server
     * @param maxP the number of players for this game, chosen before by the user
     * @param old contains yes/no, used to determine if the player wants to load and older game
     */
    public Game(int maxP, String old) throws RemoteException {
        super();
        System.setProperty("java.rmi.server.hostname", IP.activeIP);
        try {
            LocateRegistry.createRegistry(Initializer.PORT_RMI).rebind("Server", this); // host the server on the network
        }catch (Exception e){
            System.out.println("\nPort 5555 is already used\n");
            System.exit(0);
        }

        targetPlayers = maxP;
        if(old.equals("yes")){
            if(FILEHelper.havaCachedServer()) {// check if there's a server in the cache, if so, use it
                gameTemp = FILEHelper.loadServer();
                FILEHelper.writeFail();
                if(gameTemp.numPlayers != maxP) {
                    System.out.println("\nThe old game is not compatible, starting a new game...");
                    gameTemp = null;
                }
                else {
                    System.out.println("\nLoading the old game...");
                }
            }
            else {
                System.out.println("\nThere is no game to load, starting a new game...");
            }
        }
        FILEHelper.writeFail();
        shuffleObjBucket();
        numPlayers = 0;
        new Thread(() -> { // start a timer that wait for clients connections
            double minutes = 2.5;
            Game.waitForSeconds(60 * minutes);
            if(!timeExp)
                return;
            System.out.println("\nTime limit exceeded, not enough players connected\n");
            System.exit(0);
        }).start();

        try{serverSocket = new ServerSocket(Initializer.PORT, 10, InetAddress.getByName(IP.activeIP));}
        catch(Exception e){connectionLost(e);}
        System.out.println("\nServer listening... (@exit for closing the server)\n");

        new Thread(() ->{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String s;
            while(true){
                try {
                    s = br.readLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if(!s.equals("@exit"))
                    continue;
                System.out.println("\nServer exiting...\n");
                Game.waitForSeconds(Game.fastTimer);
                System.exit(0);
            }
        }).start();

        listenForPlayersConnections();

        if(gameTemp != null){
            if(gameTemp.names.containsAll(names)) {
                initializeOldClients();
                if(gameTemp.endGameSituation){
                    boolean temp; //variable used to check if the rmi is ready
                    for(int i = 0; i < numPlayers; i++) {
                        try {
                            temp = (boolean)inStreams.get(i).readObject();
                        } catch (IOException | ClassNotFoundException e) {
                            connectionLost(e);
                        }
                    }
                    Game.waitForSeconds(Game.waitTimer);
                    sendFinalScoresToAll();
                }
            }
            else{
                System.out.println("\nThe names of the clients do not match the old ones, starting a new game...");
                initializeAllClients();
            }
            gameTemp = null;
        }
        else
            initializeAllClients();

        new Thread(() ->{
            while (true){
                Game.waitForSeconds(Game.waitTimer);
                if(getActivePlayersNumber() != 0)
                    continue;
                System.out.println("\nThe server il closing because there are no connected players\n");
                System.exit(0);
            }
        }).start();

        boolean temp; //variable used to check if the rmi is ready
        for(int i = 0; i < numPlayers; i++) {
            try {
                temp = (boolean)inStreams.get(i).readObject();
            } catch (IOException | ClassNotFoundException e) {
                connectionLost(e);
            }
        }
        for(int i = 0; i < numPlayers; i++){
            if(rmiClients.containsKey(names.get(i)))
                continue;
            try {
                playersSocket.get(i).setSoTimeout(Player.pingTimeout);
            } catch (SocketException e) {
                connectionLost(e);
            }
        }
        new Thread(this::pingRMI).start();
        new Thread(this::listenForReconnection).start();
        if(!rmiClients.containsKey(names.get(0)))
            new Thread(this::waitMoveFromClient).start();
        else
            startChatServerThread();
    }
    /**
     * method that get an old client status by his name
     * @param n the name of the old client
     * @param playerList the list of all the old clients
     * @author Ettori
     * @return the client that was playing previously and needs to be alive again
     */
    private Player getClientByName(String n, ArrayList<PlayerSend> playerList){
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).name.equals(n)) {
                try {
                    return new Player(playerList.get(i));
                } catch (RemoteException e) {
                    connectionLost(e);
                }
            }
        }
        System.out.println("\nAncient client not found...");
        System.exit(0);
        return null;
    }
    /**
     * get the only player connected to the game currently
     * @return the index of the only player left active
     * @author Ettori
     */
    private int getLastPlayer(){
        for(int i = 0; i < numPlayers; i++){
            if(!disconnectedPlayers.contains(names.get(i)))
                return i;
        }
        return -1;
    }
    /**
     * helper method for initializing the old clients that were playing in the previous game
     * @author Ettori
     */
    private void initializeOldClients(){
        for(int i = 0; i < numPlayers; i++){
            try {
                gameTemp.players.get(i).activeName = gameTemp.names.get(0);
                outStreams.get(i).writeObject(new Player(getClientByName(names.get(i), gameTemp.players)));
                players.add(new PlayerSend(getClientByName(names.get(i), gameTemp.players)));
            }catch (Exception e){connectionLost(e);}
        }
        int temp = names.indexOf(gameTemp.names.get(0));
        String n = names.get(0);
        names.set(0, names.get(temp));
        names.set(temp, n);

        ObjectOutputStream outTemp = outStreams.get(0);
        outStreams.set(0, outStreams.get(temp));
        outStreams.set(temp, outTemp);

        ObjectInputStream inTemp = inStreams.get(0);
        inStreams.set(0, inStreams.get(temp));
        inStreams.set(temp, inTemp);
    }
    /**
     * helper method for initializing all the clients (players) with the same board state
     * @author Ettori
     */
    private void initializeAllClients(){
        randomizeChairman();
        Player p = null;
        for(int i = 0; i < names.size(); i++){
            try {
                p = new Player();
            } catch (RemoteException e) {
                connectionLost(e);
            }
            for(int j = 0; j < numPlayers; j++){
                if(i == j)
                    continue;
                try {
                    p.pointsMap.put(names.get(j), 0);
                }catch (Exception e){
                    System.out.println("\nServer unable to start...\n");
                    System.exit(0);
                }
            }
            p.setName(names.get(i));
            p.setIsChairMan(i == 0);
            p.board = new Board(numPlayers, bucketOfCO.get(0), bucketOfCO.get(1));
            p.board.name = names.get(i);
            if(i == 0)
                p.board.initBoard(numPlayers);
            else
                p.board = new Board(getChairman().board);
            p.library = new Library(names.get(i));
            p.setPrivateObjective(getPrivateObjective());
            p.pointsUntilNow = 0;
            p.activeName = getChairmanName();
            p.chairmanName = getChairmanName();
            try {
                for (int j = 0; j < numPlayers; j++) {
                    if (!names.get(j).equals(names.get(i)))
                        p.librariesOfOtherPlayers.add(new Library(names.get(j)));
                }
            }catch (Exception e){connectionLost(e);}
            p.numPlayers = numPlayers;
            try {
                outStreams.get(i).writeObject(new Player(p));
            }catch (Exception e){connectionLost(e);}
            players.add(new PlayerSend(p));
        }
    }
    /**
     * choose a random chairman from all the players who connected to the game
     * @author Ettori
     */
    private void randomizeChairman(){
        int temp = new Random().nextInt(numPlayers);
        String n = null;
        try {
            n = names.get(0);
        }catch (Exception e){connectionLost(e);}
        names.set(0, names.get(temp));
        names.set(temp, n);

        ObjectOutputStream outTemp = outStreams.get(0);
        outStreams.set(0, outStreams.get(temp));
        outStreams.set(temp, outTemp);

        ObjectInputStream inTemp = inStreams.get(0);
        inStreams.set(0, inStreams.get(temp));
        inStreams.set(temp, inTemp);
    }
    /**
     * helper function which waits for client's connection to the server socket, when all are connected the game starts
     * @author Ettori
     */
    private void listenForPlayersConnections(){
        ArrayList<Thread> ths = new ArrayList<>();
        ObjectInputStream clientIn;
        ObjectOutputStream clientOut;
        Thread th;

        while(numPlayers < targetPlayers){
            try{
                playersSocket.add(serverSocket.accept());
                clientOut = new ObjectOutputStream(playersSocket.get(playersSocket.size() - 1).getOutputStream());
                clientIn = new ObjectInputStream(playersSocket.get(playersSocket.size() - 1).getInputStream());
                clientOut.flush();
                boolean isFake = (boolean) clientIn.readObject();
                if(isFake) {
                    playersSocket.remove(playersSocket.size() - 1);
                    continue;
                }
                ObjectInputStream finalClientIn = clientIn;
                ObjectOutputStream finalClientOut = clientOut;
                th = new Thread(() ->{
                    try{getUserName(finalClientIn, finalClientOut);}
                    catch(Exception e){connectionLost(e);}
                });
                th.start();
                ths.add(th);
                numPlayers++;
            }
            catch(Exception e){
                try {
                    playersSocket.get(playersSocket.size() - 1).close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                playersSocket.remove(playersSocket.size() - 1);
            }
        }
        timeExp = false;
        for(Thread t: ths){
            try{t.join();}
            catch(Exception e){connectionLost(e);}
        }
        if(numPlayers < targetPlayers){
            System.out.println("\nPlayer number not sufficient\n");
            System.exit(0);
        }
        System.out.println("\nThe game started\n");
    }
    /**
     * method that listen for an old client to restart his previous game, in tha same old state
     * @param s the socket of the player
     * @param out the output stream of the player
     * @param in the input stream of the player
     * @author Ettori
     */
    synchronized private void tryToReconnectClient(Socket s, ObjectOutputStream out, ObjectInputStream in){
        try {
            String name = (String) in.readObject();
            if(disconnectedPlayers.contains(name)){
                rmiClients.remove(name);
                out.writeObject(NameStatus.FOUND);
                PlayerSend p = new PlayerSend(players.get(names.indexOf(name)));
                p.activeName = names.get(activePlayer);
                out.writeObject(new Player(p));
                inStreams.set(names.indexOf(name), in);
                outStreams.set(names.indexOf(name), out);
                playersSocket.set(names.indexOf(name), s);
                disconnectedPlayers.remove(name);
                boolean temp = (boolean)in.readObject(); //variable used to check if the rmi is ready
                if(!rmiClients.containsKey(name)) {
                    try {
                        s.setSoTimeout(Player.pingTimeout);
                    } catch (SocketException e) {
                        connectionLost(e);
                    }
                    if (getActivePlayersNumber() >= 3) // if there are only 2 players, the turn will change, so there is no need to listen to the chat
                        new ChatBroadcast(this, names.indexOf(name)).start();
                }
                if(getActivePlayersNumber() == 2){
                    disconnectedPlayers.remove(name);
                    if(advance){
                        sendToClient(activePlayer, new Message(MessageType.SHOW_EVENT, null, "Player " + name + " reconnected, the game is resuming..."));
                        Game.waitForSeconds(Game.fastTimer * 1.5);
                        advance = false;
                        new Thread(this::advanceTurn).start();
                    }
                    else {
                        sendToClient(activePlayer, new Message(MessageType.SHOW_EVENT, null, "Player " + name + " reconnected to the game"));
                        new ChatBroadcast(this, names.indexOf(name)).start(); // the chat thread will be stopped naturally after the next UPDATE_GAME
                    }
                }
                else{
                    disconnectedPlayers.remove(name);
                    for(int i = 0; i < numPlayers; i++){
                        if(names.get(i).equals(name))
                            continue;
                        sendToClient(i, new Message(MessageType.SHOW_EVENT, null, "Player " + name + " reconnected to the game"));
                    }
                }
            }
            else
                out.writeObject(NameStatus.NOT_FOUND);
        }catch (Exception e){
            try {
                s.close();
                out.close();
                in.close();
            } catch (IOException ignored) {}
        }
    }
    /**
     * method that wait permanently for a new client to connect to the existing game
     * @author Ettori
     */
    private void listenForReconnection(){
        Socket s = null;
        while(true){
            try {
                s = serverSocket.accept();
            } catch (IOException e) {
                continue;
            }
            Socket finalS = s;
            new Thread(() -> {
                ObjectOutputStream out = null;
                ObjectInputStream in = null;
                try {
                    out = new ObjectOutputStream(finalS.getOutputStream());
                    out.flush();
                    in = new ObjectInputStream(finalS.getInputStream());
                } catch (IOException ignored) {}
                tryToReconnectClient(finalS, out, in);
            }).start();
        }
    }
    /**
     * Check if the name that the client choose is already TAKEN
     * @param in the input stream of the socket
     * @param out the output stream of the socket
     * @author Ettori
     */
    synchronized private void getUserName(ObjectInputStream in, ObjectOutputStream out){
        try {
            outStreams.add(out);
            inStreams.add(in);
            while (true) {
                String name = (String) inStreams.get(inStreams.size() - 1).readObject();
                if (isNameTaken(name)) {
                    outStreams.get(outStreams.size() - 1).writeObject(NameStatus.TAKEN);
                    continue;
                }
                if(gameTemp != null && gameTemp.names.contains(name))
                    outStreams.get(outStreams.size() - 1).writeObject(NameStatus.OLD);
                else
                    outStreams.get(outStreams.size() - 1).writeObject(NameStatus.NOT_TAKEN);
                names.add(name);
                break;
            }
        }catch(Exception e){
            try {
                playersSocket.get(playersSocket.size() - 1).close();
                outStreams.get(outStreams.size() - 1).close();
                inStreams.get(inStreams.size() - 1).close();
            } catch (IOException ignored) {}
            outStreams.remove(outStreams.size() - 1);
            inStreams.remove(inStreams.size() - 1);
            playersSocket.remove(playersSocket.size() - 1);
        }
    }
    /**
     * Make a random choose of the objective (Common and Private)
     * @author Ettori
     */
    private void shuffleObjBucket(){
        Random rand = new Random();
        CommonObjective temp_1;
        PrivateObjective temp_2;
        int j;
        for(int i = 0; i < bucketOfCO.size(); i++){
            j = rand.nextInt(bucketOfCO.size());
            temp_1 = bucketOfCO.get(i);
            bucketOfCO.set(i, bucketOfCO.get(j));
            bucketOfCO.set(j, temp_1);
        }
        for(int i = 0; i < bucketOfPO.size(); i++){
            j = rand.nextInt(bucketOfPO.size());
            temp_2 = bucketOfPO.get(i);
            bucketOfPO.set(i, bucketOfPO.get(j));
            bucketOfPO.set(j, temp_2);
        }
    }
    /**
     * Wait the move of the client that are playing and set the chat,
     * when the client made the move and send it to server update Board and Library
     * @author Ettori Faccincani
     */
    private void waitMoveFromClient(){
        synchronized (waitMoveLock) {
            startChatServerThread();
            while (true) {
                Message msg = null;
                try {
                    msg = (Message) inStreams.get(activePlayer).readObject();
                } catch (IOException | ClassNotFoundException e) {
                    playerDisconnected(activePlayer, e);
                    return;
                }
                try {
                    if (msg == null)
                        continue;
                    if (msg.getType() == MessageType.PING)
                        continue;
                    if (msg.getType() == MessageType.CHAT) {
                        sendChatToClients(names.get(activePlayer), msg.getAuthor(), (String) msg.getContent());
                        continue;
                    }
                    if(msg.getType() == MessageType.STOP){
                        continue;
                    }
                    for (int i = 0; i < numPlayers; i++) {
                        if (i != activePlayer)
                            sendToClient(i, msg);
                    }
                    if (msg.getType() == MessageType.LIB_FULL && !endGameSituation) {
                        endGameSituation = true;
                        continue;
                    }
                    if (msg.getType() == MessageType.UPDATE_UNPLAYABLE) {
                        Board b = (Board) msg.getContent();
                        for (int i = 0; i < numPlayers; i++)
                            players.get(i).board = new Board(b);
                        FILEHelper.writeServer(this);
                    }
                    if (msg.getType() == MessageType.UPDATE_GAME) {
                        PlayerSend p = (PlayerSend) msg.getContent();
                        for (int i = 0; i < numPlayers; i++) {
                            if (i == activePlayer)
                                continue;
                            players.get(i).board = p.board;
                            players.get(i).pointsMap.put(names.get(activePlayer), p.pointsUntilNow);
                            for (int j = 0; j < numPlayers - 1; j++) {
                                if (players.get(i).librariesOfOtherPlayers.get(j).name.equals(names.get(activePlayer)))
                                    players.get(i).librariesOfOtherPlayers.set(j, p.library);
                            }
                        }
                        players.set(activePlayer, p);
                        players.get(activePlayer).activeName = names.get(activePlayer);
                        FILEHelper.writeServer(this);
                        if (!rmiClients.containsKey(names.get(activePlayer)))
                            sendToClient(activePlayer, new Message(MessageType.STOP, null, null));
                        break;
                    }
                } catch (Exception e) {
                    connectionLost(e);
                }
            }
        }
        Game.waitForSeconds(Game.passTimer);
        advanceTurn();
    }
    /**
     * Set the status of the players for the next turn and assign activePlayer to who will play this turn
     * @author Ettori Faccincani
     */
    public void advanceTurn(){
        synchronized (disconnectionLock) {
            if(getActivePlayersNumber() == 0)
                connectionLost(new RuntimeException("All players disconnected"));
            if (getActivePlayersNumber() == 1 && disconnectedPlayers.size() > 0) {
                Game.waitForSeconds(Game.passTimer);
                sendToClient(getLastPlayer(), new Message(MessageType.SHOW_EVENT, null, "The game is temporarily paused because you are the only connected player"));
                advance = true;
                activePlayer = getLastPlayer();
                new ChatBroadcast(this, activePlayer).start();
                playerNoChat = names.get(activePlayer);
                return;
            }
        }
        do{
            activePlayer = (activePlayer + 1) % numPlayers;
        }
        while(disconnectedPlayers.contains(names.get(activePlayer)));
        if(activePlayer == 0 && endGameSituation) {
            System.out.println("\nThe game is ending...");
            Game.waitForSeconds(Game.fastTimer);
            sendFinalScoresToAll();
            return;
        }
        notifyNewTurn();
    }
    /**
     * Send the message to the client that a new turn start (two cases, if is the turn of the client or is the turn of another client)
     * @author Ettori Faccincani
     */
    private void notifyNewTurn(){
        for(int i = 0; i < numPlayers; i++){
            try {
                if (i != activePlayer)
                    sendToClient(i, new Message(MessageType.CHANGE_TURN, "server", names.get(activePlayer)));
            }catch (Exception e){connectionLost(e);}
        }
        new Thread(() -> sendToClient(activePlayer, new Message(MessageType.YOUR_TURN, "server", ""))).start();
        if(!rmiClients.containsKey(names.get(activePlayer)))
            new Thread(this::waitMoveFromClient).start();
        else
            startChatServerThread();
    }
    /**
     * start all the threads that listen for chat messages from the clients (and sends the messages back to the players)
     * @author Ettori
     */
    private void startChatServerThread(){
        chatThreads = new ArrayList<>();
        for(int i = 0; i < numPlayers; i++){
            if(i == activePlayer || rmiClients.containsKey(names.get(i)) || disconnectedPlayers.contains(names.get(i)) || names.get(i).equals(playerNoChat))
                continue;
            chatThreads.add(new ChatBroadcast(this, i));
            chatThreads.get(chatThreads.size() - 1).start();
        }
        playerNoChat = "";
    }
    /**
     * Send message in the chat to other client
     * @param from who send the message
     * @param to who receive the message
     * @param msg text inside the message
     * @author Ettori
     */
    public void sendChatToClients(String from, String to, String msg){
        try {
            if (to.equals("all")) {
                for (int i = 0; i < numPlayers; i++) {
                    if (!names.get(i).equals(from))
                        sendToClient(i, new Message(MessageType.CHAT, "", msg));
                    players.get(i).fullChat += msg;
                }
            }
            else if(getNameIndex(to) != -1){
                sendToClient(getNameIndex(to) ,new Message(MessageType.CHAT, "", msg));
                players.get(getNameIndex(to)).fullChat += msg;
                players.get(getNameIndex(from)).fullChat += msg;
            }
        }catch (Exception e){connectionLost(e);}
    }
    /**
     * find and return the name of the chairman of this game
     * @return the name of the chairman (String)
     */
    private String getChairmanName(){return names.get(0);}
    /**
     * find and return the chairman Player
     * @return the chairman Object (Player)
     */
    private PlayerSend getChairman(){return players.get(0);}

    /**
     * check if the name is already taken by other players
     * @param name the name to check
     * @return true iff the name is already taken
     */
    private boolean isNameTaken(String name){return names.contains(name);}

    /**
     * get the index of a certain player, by the name
     * @param name the name of the player
     * @return the index of the player having that name, -1 if not found
     */
    private int getNameIndex(String name){
        for(int i = 0; i < names.size(); i++){
            if(names.get(i).equals(name))
                return i;
        }
        return -1;
    }
    /**
     * choose the private objective, one for every player
     * @return the chosen private objective
     */
    private PrivateObjective getPrivateObjective(){
        PrivateObjective res = bucketOfPO.get(0);
        bucketOfPO.remove(0);
        return res;
    }
    /**
     * Count the points at the end of the game (not private or common objective)
     * and sum to the points made until now
     * @return the order of the player each one with his score
     * @author Ettori
     */
    private String getFinalScore(){
        ArrayList<Integer> scores = new ArrayList<>();
        StringBuilder res = new StringBuilder();
        PlayerSend p;
        for(int i = 0; i < numPlayers; i++){
            p = players.get(i);
            scores.add(p.pointsUntilNow + p.library.countGroupedPoints() + p.objective.countPoints(p.library.gameLibrary));
        }
        String tempName;
        int tempScore;
        for(int i = 0; i < numPlayers; i++){
            for(int j = i; j < numPlayers; j++){
                if(scores.get(j) < scores.get(i)){
                    tempName = names.get(i);
                    tempScore = scores.get(i);
                    names.set(i, names.get(j));
                    scores.set(i, scores.get(j));
                    names.set(j, tempName);
                    scores.set(j, tempScore);
                }
            }
        }
        Collections.reverse(names);
        Collections.reverse(scores);
        for(int i = 0; i < numPlayers; i++)
            res.append("Place number ").append(i + 1).append(": ").append(names.get(i)).append(" with ").append(scores.get(i)).append(" points\n");
        return res.toString();
    }
    /**
     * Send the final score to all the clients
     * @author Ettori
     */
    private void sendFinalScoresToAll(){
        String finalScores = getFinalScore();
        FILEHelper.writeSucc(); // the server finished with success, so nothing has to be written in the cache
        Thread finalTh = new Thread(() ->{
            for(int i = 0; i < numPlayers; i++)
                sendToClient(i, new Message(MessageType.FINAL_SCORE, "server", finalScores));
        });
        finalTh.start();
        try {
            finalTh.join();
        } catch (InterruptedException e) {
            connectionLost(e);
        }
        System.out.println("The game is finished successfully");
        Game.waitForSeconds(Game.waitTimer * 5);
        System.exit(0);
    }
    /**
     * getter for the input streams from the server to all the clients
     * @return the ArrayList containing all the input streams
     */
    public ArrayList<ObjectInputStream> getInStreams(){return inStreams;}
    /**
     * getter for the list of names of the players active in this game
     * @return the ArrayList containing all the names of the connected players
     */
    public ArrayList<String> getNames(){return names;}
    /**
     * shortcut for the Thread.sleep(int) function, it accepts SECONDS, NOT MILLISECONDS
     * @param n the (decimal) number of seconds to wait
     */
    public static void waitForSeconds(double n){
        try {
            Thread.sleep((long) (n * 1000));
        } catch (InterruptedException ignored) {}
    }
    /**
     * function that handle the eventual disconnection
     * @param e the exception to throw
     * @author Ettori
     */
    public void connectionLost(Exception e){
        if(closed)
            return;
        //e.printStackTrace();
        if(Game.showErrors)
            throw new RuntimeException(e);
        else{
            closed = true;
            System.out.println("\nConnection lost, the server is closing...\n");
            new Thread(() ->{
                try {
                    for(Socket s: playersSocket)
                        s.close();
                    for(ObjectOutputStream out: outStreams)
                        out.close();
                    serverSocket.close();
                } catch (Exception ignored) {}
            }).start();
            Game.waitForSeconds(Game.fastTimer * 1.5);
            System.exit(0);
        }
    }
    /**
     * method which acknowledge that one of the client disconnected and set the game to continue without the lost client
     * @param i the index of the lost client
     * @author Ettori
     */
     public void playerDisconnected(int i, Exception exc) {
         synchronized (disconnectionLock) {
             if (Game.showErrors)
                 connectionLost(exc);
             if (disconnectedPlayers.contains(names.get(i)))
                 return;
             //System.out.println("disco " + names.get(i));
             try {
                 playersSocket.get(i).setSoTimeout(0);
                 outStreams.get(i).flush();
             } catch (IOException ignored) {}
             disconnectedPlayers.add(names.get(i));
             rmiClients.remove(names.get(i));
         }
         if (getActivePlayersNumber() == 1)
             new Thread(this::disconnectedTimer).start();
         if(getActivePlayersNumber() == 0){
             System.out.println("The server is closing because there are no connected players...");
             System.exit(0);
         }
         if (i == activePlayer){
             for (int j = 0; j < numPlayers; j++) {
                 int finalJ = j;
                 new Thread(() -> sendToClient(finalJ, new Message(MessageType.DISCONNECTED, names.get(i), null))).start();
             }
             if(getActivePlayersNumber() > 1){
                 Game.waitForSeconds(Game.fastTimer);
                 advanceTurn();
             }
             else{
                 activePlayer = getLastPlayer();
                 Game.waitForSeconds(Game.fastTimer);
                 notifyNewTurn();
             }
         }
         else{
             for(int x = 0; x < numPlayers; x++){
                 if(!disconnectedPlayers.contains(names.get(x))) {
                     sendToClient(x, new Message(MessageType.LOST_CLIENT, names.get(i), null));
                 }
             }
         }
     }
    /**
     * method that checks if one player has been alone for more than 1 minute, in that case that player is declared winner and the game end
     * @author Ettori
     */
    private void disconnectedTimer(){
        int name = getLastPlayer();
        Game.waitForSeconds(30);
        if(getActivePlayersNumber() != 1)
            return;
        Game.waitForSeconds(30);
        if(getActivePlayersNumber() != 1)
            return;
        Game.waitForSeconds(30);
        if(getActivePlayersNumber() == 1 && getLastPlayer() == name){
            FILEHelper.writeSucc();
            sendToClient(0, new Message(MessageType.SHOW_EVENT, "win", "You have won because all the other players have disconnected"));
            Game.waitForSeconds(Game.waitTimer * 3);
            System.exit(0);
        }
    }
    /**
     * method that find the number of players which are currently connected to the game
     * @author Ettori
     * @return the number of connected players
     */
    private int getActivePlayersNumber(){return numPlayers - disconnectedPlayers.size();}
    /**
     * general method to respond to a client, it chooses the right network connection of the player
     * @author Ettori
     * @param i the index of the player to contact
     * @param msg the message that must be sent
     */
    public void sendToClient(int i, Message msg){
        if(i < 0 || i >= names.size())
            throw new RuntimeException("Sending to a NON existing player");
        if (disconnectedPlayers.contains(names.get(i)))
            return;
        //System.out.println("Sending " + msg.getType() + " to " + names.get(i));
        if (!rmiClients.containsKey(names.get(i)) || msg.getType() == MessageType.FINAL_SCORE) {
            try {
                outStreams.get(i).writeObject(msg);
            } catch (IOException e) {
                playerDisconnected(i, e);
            }
        } else {
            try {
                rmiClients.get(names.get(i)).receivedEventRMI(msg);
            } catch (RemoteException e) {
                playerDisconnected(i, e);
            }
        }
    }
    /******************************************** RMI ***************************************************************/
    /**
     * method called from remote used to add a client to the store of all the RMI clients
     * @author Ettori
     * @param name the nickname of the player
     * @param p the player object, passed as the remote interface
     */
    public void addClient(String name, PlayerI p){
        rmiClients.put(name, p);
    }
    /**
     * method called from remote which is equivalent to the waitMoveFromClient() method for the socket
     * @author Ettori
     * @param msg the message that the client want to send to the remote server
     */
    public void redirectToClientRMI(Message msg){
        switch (msg.getType()){
            case CHAT -> {
                String from = (String)msg.getContent();
                from = from.substring(0, from.indexOf(" "));
                sendChatToClients(from, msg.getAuthor(), (String)msg.getContent());
            }
            case UPDATE_GAME -> {
                synchronized (waitMoveLock) {
                    for (int i = 0; i < numPlayers; i++) {
                        if (i != activePlayer)
                            sendToClient(i, msg);
                    }
                    PlayerSend p = (PlayerSend) msg.getContent();
                    for (int i = 0; i < numPlayers; i++) {
                        if (i == activePlayer)
                            continue;
                        players.get(i).board = p.board;
                        players.get(i).pointsMap.put(names.get(activePlayer), p.pointsUntilNow);
                        for (int j = 0; j < numPlayers - 1; j++) {
                            if (players.get(i).librariesOfOtherPlayers.get(j).name.equals(names.get(activePlayer)))
                                players.get(i).librariesOfOtherPlayers.set(j, p.library);
                        }
                    }
                    players.set(activePlayer, p);
                    players.get(activePlayer).activeName = names.get(activePlayer);
                    FILEHelper.writeServer(this);
                    if (!rmiClients.containsKey(names.get(activePlayer)))
                        sendToClient(activePlayer, new Message(MessageType.STOP, null, null));
                }
                Game.waitForSeconds(Game.passTimer);
                advanceTurn();
            }
            case PING, STOP ->{}
            default -> {
                if(msg.getType() == MessageType.LIB_FULL && !endGameSituation)
                    endGameSituation = true;
                if(msg.getType() == MessageType.UPDATE_UNPLAYABLE){
                    Board b = (Board) msg.getContent();
                    for (int i = 0; i < numPlayers; i++)
                        players.get(i).board = new Board(b);
                    FILEHelper.writeServer(this);
                }
                for (int i = 0; i < numPlayers; i++) {
                    if (i != activePlayer)
                        sendToClient(i,msg);
                }
            }
        }
    }
    /**
     * method that allow the server to be pinged from an RMI client
     * @author Ettori
     */
    public void ping(){}

    /**
     * method that periodically pings all the current client connected with RMI
     * @author Ettori
     */
    public void pingRMI(){
        AtomicBoolean flag = new AtomicBoolean(false);
        while(true){
            Game.waitForSeconds(Game.waitTimer * 2);
            for(String n: names){
                if(!rmiClients.containsKey(n) || disconnectedPlayers.contains(n) || (endGameSituation && activePlayer == 0))
                    continue;
                flag.set(false);
                new Thread(() ->{
                    try {
                        rmiClients.get(n).pingClient();
                        flag.set(true);
                    } catch (RemoteException e) {
                        playerDisconnected(names.indexOf(n), e);
                    }
                }).start();
                Game.waitForSeconds(Game.fastTimer);
                if(!flag.get())
                    playerDisconnected(names.indexOf(n), new RuntimeException("Player Disconnected"));
                //System.out.println("Pingo " + n);
            }
        }
    }
}