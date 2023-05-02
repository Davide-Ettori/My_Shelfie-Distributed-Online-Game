package app.controller;

import app.model.*;
import app.view.IP;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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

import static app.controller.MessageType.*;
import static app.controller.NameStatus.*;
import static app.view.UIMode.*;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * class which represent the instance of the current game
 * @author Ettori Faccincani
 * in theory it is mutable, but it is only instanced one time, at the start of the server
 */
public class Game extends UnicastRemoteObject implements Serializable, GameI {
    /** variable that represent the standard timer of the app for advancing the state of the game */
    public static final double waitTimer = 2.5;
    /** variable that represent the standard timer of the app for showing events */
    public static final double showTimer = 2.5;
    /** variable that represent if we want to run or debug our application */
    public static boolean showErrors = false;
    /** variable that represent the name of the first player, which is also hosting the server */
    public static String serverPlayer = "";
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
    private boolean advance = false;
    /**
     * normal constructor for this type of object, this class is also the main process on the server
     * @param maxP the number of players for this game, chosen before by the user
     * @param old contains yes/no, used to determine if the player wants to load and older game
     */
    public Game(int maxP, String old) throws RemoteException {
        super();
        System.setProperty("java.rmi.server.hostname", IP.activeIP);
        LocateRegistry.createRegistry(Initializer.PORT_RMI).rebind("Server", this); // hosto il server sulla rete

        targetPlayers = maxP;
        if(old.equals("yes")){
            if(FILEHelper.havaCachedServer()) {// per prima cosa dovresti controllare che ci sia un server nella cache, nel caso lo carichi
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
            else
                System.out.println("\nThere is no game to load, starting a new game...");
        }
        FILEHelper.writeFail();
        shuffleObjBucket();
        numPlayers = 0;
        new Thread(() -> { // imposto un timer di un minuto per aspettare le connessioni dei client
            double minutes = 2;
            Game.waitForSeconds(60 * minutes);
            if(!timeExp)
                return;
            System.out.println("\nTime limit exceeded, not enough players connected");
            System.exit(0);
        }).start();

        try{serverSocket = new ServerSocket(Initializer.PORT, 10, InetAddress.getByName(IP.activeIP));}
        catch(Exception e){connectionLost(e);}
        System.out.println("\nServer listening...");

        listenForPlayersConnections();

        if(gameTemp != null){
            if(gameTemp.names.containsAll(names)) {
                initializeOldClients();
                if(gameTemp.endGameSituation){
                    Game.waitForSeconds(Game.waitTimer / 2.5);
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
        Game.waitForSeconds(Game.waitTimer / 2.5);
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
            waitMoveFromClient();
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
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("\nAncient client not found...");
        System.exit(0);
        try {
            return new Player();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * helper method for initializing the old clients that were playing in the previous game
     * @author Ettori
     */
    private void initializeOldClients(){
        for(int i = 0; i < numPlayers; i++){
            try {
                gameTemp.players.get(i).activeName = gameTemp.names.get(0);
                outStreams.get(i).writeObject(getClientByName(names.get(i), gameTemp.players));
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
        Player p;
        for(int i = 0; i < names.size(); i++){
            try {
                p = new Player();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            for(int j = 0; j < numPlayers; j++){
                if(i == j)
                    continue;
                p.pointsMap.put(names.get(j), 0);
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
                outStreams.get(i).writeObject(p);
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
                boolean isFake = (boolean) clientIn.readObject();
                if(isFake) {
                    playersSocket.remove(playersSocket.size() - 1);
                    continue;
                }
                ObjectInputStream finalClientIn = clientIn;
                ObjectOutputStream finalClientOut = clientOut;
                th = new Thread(() ->{
                    try{getUserName(finalClientIn, finalClientOut);}
                    catch(Exception e){System.out.println(e);}
                });
                th.start();
                ths.add(th);
                numPlayers++;
            }
            catch(Exception e){connectionLost(e);}
        }
        timeExp = false;
        for(Thread t: ths){
            try{t.join();}
            catch(Exception e){connectionLost(e);}
        }
        if(numPlayers < targetPlayers){
            System.out.println("\nPlayer number not sufficient");
            System.exit(0);
        }
        System.out.println("\nThe game started");
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
                out.writeObject(FOUND);
                PlayerSend p = new PlayerSend(players.get(names.indexOf(name)));
                p.activeName = names.get(activePlayer);
                out.writeObject(new Player(p));
                inStreams.set(names.indexOf(name), in);
                outStreams.set(names.indexOf(name), out);
                playersSocket.set(names.indexOf(name), s);
                disconnectedPlayers.remove(name);
                new Thread(() ->{
                    Game.waitForSeconds(Game.waitTimer / 2.5);
                    if(rmiClients.containsKey(name))
                        return;
                    try {
                        s.setSoTimeout(Player.pingTimeout);
                    } catch (SocketException e) {
                        connectionLost(e);
                    }
                    if(getActivePlayersNumber() >= 3) // se ci sono solo 2 player il turno cambierà quindi non devo ascoltare la chat
                        new ChatBroadcast(this, names.indexOf(name)).start();
                }).start();
                if(getActivePlayersNumber() == 2){
                    if(Client.uiModeCur == GUI)
                        showMessageDialog(null, "The game is now resuming and the next turn is starting...");
                    else
                        System.out.println("\nThe game is now resuming and the next turn is starting...");
                    if(advance){
                        advance = false;
                        advanceTurn();
                    }
                }
            }
            else
                out.writeObject(NOT_FOUND);
        }catch (Exception e){
            try {
                s.close();
                out.close();
                in.close();
            } catch (IOException ex) {
                return;
            }
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
                    outStreams.get(outStreams.size() - 1).writeObject(TAKEN);
                    continue;
                }
                if(gameTemp != null && gameTemp.names.contains(name))
                    outStreams.get(outStreams.size() - 1).writeObject(OLD);
                else
                    outStreams.get(outStreams.size() - 1).writeObject(NOT_TAKEN);
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
        startChatServerThread();
        while(true){
            Message msg = null;
            try {
                msg = (Message) inStreams.get(activePlayer).readObject();
            } catch (IOException | ClassNotFoundException e) {
                playerDisconnected(activePlayer);
                return;
            }
            try {
                if(msg.getType() == PING)
                    continue;
                if(msg.getType() == CHAT){
                    sendChatToClients(names.get(activePlayer), msg.getAuthor(), (String)msg.getContent());
                    continue;
                }
                for (int i = 0; i < numPlayers; i++) {
                    if (i != activePlayer)
                        sendToClient(i,msg);
                }
                if(msg.getType() == LIB_FULL && !endGameSituation){
                    endGameSituation = true;
                    continue;
                }
                if(msg.getType() == UPDATE_UNPLAYBLE){
                    JSONObject jsonObject = (JSONObject) msg.getContent();
                    Board b = (Board) jsonObject.get("board");
                    for(int i = 0; i < numPlayers; i++)
                        players.get(i).board = new Board(b);
                }
                if(msg.getType() == UPDATE_GAME) {
                    JSONObject jsonObject = (JSONObject) msg.getContent();
                    players.get(activePlayer).library = (Library) jsonObject.get("library");
                    players.get(activePlayer).board = (Board) jsonObject.get("board");
                    players.get(activePlayer).pointsUntilNow = (int) jsonObject.get("points");
                    for(int i = 0; i < numPlayers; i++) {
                        if(i == activePlayer)
                            continue;
                        players.get(i).board = (Board) jsonObject.get("board");
                        players.get(i).pointsMap.put(names.get(activePlayer), (int) jsonObject.get("points"));
                        for(int j = 0; j < numPlayers - 1; j++){
                            if(players.get(i).librariesOfOtherPlayers.get(j).name.equals(names.get(activePlayer)))
                                players.get(i).librariesOfOtherPlayers.set(j, (Library) jsonObject.get("library"));
                        }
                    }
                    FILEHelper.writeServer(this);
                    if(!rmiClients.containsKey(names.get(activePlayer)))
                        sendToClient(activePlayer, new Message(STOP, null, null));
                    break;
                }
            }catch(Exception e){connectionLost(e);}
        }
        waitForEndTurn();
    }
    /**
     * Wait the end of the turn of the client and check if the library is full
     * @author Ettori Faccincani
     */
    private void waitForEndTurn(){
        Message msg = null;
        try {
            msg = (Message) inStreams.get(activePlayer).readObject();
        } catch (IOException | ClassNotFoundException e) {
            playerDisconnected(activePlayer);
            return;
        }
        try {
            if(msg.getType() == PING) {
                waitForEndTurn();
                return;
            }
            if(msg.getType() != END_TURN)
                throw new RuntimeException();
            JSONObject jsonObject = (JSONObject) msg.getContent();
            players.set(activePlayer, (PlayerSend) jsonObject.get("player"));
            advanceTurn();
        }catch(Exception e){connectionLost(e);}
    }
    /**
     * Set the status of the players for the next turn and assign activePlayer to who will play this turn
     * @author Ettori Faccincani
     */
    public void advanceTurn(){
        if(getActivePlayersNumber() == 1 && disconnectedPlayers.size() > 0){
            advance = true;
            if(endGameSituation && activePlayer == 0)
                return;
            if(Client.uiModeCur == GUI)
                showMessageDialog(null, "The game is temporarily paused because you are the only connected player");
            else
                System.out.println("\nThe game is temporarily paused because you are the only connected player");
            activePlayer = names.indexOf(Game.serverPlayer);
            return;
        }
        do{
            activePlayer = (activePlayer + 1) % numPlayers;
        }
        while(disconnectedPlayers.contains(names.get(activePlayer)));
        if(activePlayer == 0 && endGameSituation) {
            System.out.println("\nThe game is ending...");
            waitForSeconds(Game.waitTimer / 2.5);
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
                if (i != activePlayer) {
                    sendToClient(i, new Message(CHANGE_TURN, "server", names.get(activePlayer)));
                }
            }catch (Exception e){connectionLost(e);}
        }
        new Thread(() -> {
            Game.waitForSeconds(Game.waitTimer / 2.5);
            sendToClient(activePlayer, new Message(YOUR_TURN, "server", ""));
        }).start();
        if(!rmiClients.containsKey(names.get(activePlayer)))
            waitMoveFromClient();
        else {
            startChatServerThread();
        }
    }
    /**
     * start all the threads that listen for chat messages from the clients (and sends the messages back to the players)
     * @author Ettori
     */
    private void startChatServerThread(){
        chatThreads = new ArrayList<>();
        for(int i = 0; i < numPlayers; i++){
            if(i == activePlayer || rmiClients.containsKey(names.get(i)) || disconnectedPlayers.contains(names.get(i)))
                continue;
            chatThreads.add(new ChatBroadcast(this, i));
            chatThreads.get(chatThreads.size() - 1).start();
        }
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
                        sendToClient(i, new Message(CHAT, "", msg));
                    players.get(i).fullChat += msg;
                }
            }
            else if(getNameIndex(to) != -1){
                sendToClient(getNameIndex(to) ,new Message(CHAT, "", msg));
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
        FILEHelper.writeSucc(); // server uscito con successo, non devi mettere niente nella cache
        Thread finalTh = new Thread(() ->{
            for(int i = 0; i < numPlayers; i++) {
                if(names.get(i).equals(Game.serverPlayer))
                    continue;
                sendToClient(i, new Message(FINAL_SCORE, "server", finalScores));
            }
        });
        finalTh.start();
        try {
            finalTh.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        waitForSeconds(Game.waitTimer / 2.5);
        sendToClient(names.indexOf(Game.serverPlayer), new Message(FINAL_SCORE, "server", finalScores));
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
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * function that handle the eventual disconnection
     * @param e the exception to throw
     * @author Ettori
     */
    public void connectionLost(Exception e){
        if(closed)
            return;
        if(Game.showErrors)
            throw new RuntimeException(e);
        else{
            closed = true;
            System.out.println("\nConnection lost, the server is closing...");
            try {
                serverSocket.close();
                for(Socket s: playersSocket)
                    s.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            while (true){}
        }
    }
    /**
     * method which acknowledge that one of the client disconnected and set the game to continue without the lost client
     * @param i the index of the lost client
     * @author Ettori
     */
    public void playerDisconnected(int i) {
        if (disconnectedPlayers.contains(names.get(i)))
            return;
        try {
            playersSocket.get(i).setSoTimeout(0);
        } catch (SocketException e) {
            System.out.println("Errore della socket");
        }
        disconnectedPlayers.add(names.get(i));
        rmiClients.remove(names.get(i));
        if (getActivePlayersNumber() == 1)
            new Thread(this::disconnectedTimer).start();
        if (i == activePlayer) {
            for (int j = 0; j < numPlayers; j++)
                sendToClient(j, new Message(DISCONNECTED, names.get(activePlayer), null));
            advanceTurn();
        }
    }
    /**
     * method that checks if one player has been alone for more than 1 minute, in that case that player is declared winner and the game end
     * @author Ettori
     */
    private void disconnectedTimer(){
        Game.waitForSeconds(30);
        if(getActivePlayersNumber() != 1)
            return;
        Game.waitForSeconds(30);
        if(getActivePlayersNumber() != 1)
            return;
        Game.waitForSeconds(30);
        if(getActivePlayersNumber() == 1){
            FILEHelper.writeSucc();
            if(Client.uiModeCur == GUI)
                showMessageDialog(null, "You have won because all the other players have disconnected");
            else
                System.out.println("\nYou have won because all the other players have disconnected");
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
        if(disconnectedPlayers.contains(names.get(i)))
            return;
        if(!rmiClients.containsKey(names.get(i)) || msg.getType() == FINAL_SCORE){
            try {
                outStreams.get(i).writeObject(msg);
            } catch (IOException e) {
                if(msg.getType() == FINAL_SCORE)
                    return;
                playerDisconnected(i);
            }
        }
        else{
            try {
                rmiClients.get(names.get(i)).receivedEventRMI(msg);
            } catch (RemoteException e) {
                if(msg.getType() == FINAL_SCORE)
                    return;
                playerDisconnected(i);
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
            case END_TURN -> {
                JSONObject jsonObject = (JSONObject) msg.getContent();
                players.set(activePlayer, (PlayerSend) jsonObject.get("player"));
                advanceTurn();
            }
            case UPDATE_GAME -> {
                for (int i = 0; i < numPlayers; i++) {
                    if (i != activePlayer)
                        sendToClient(i,msg);
                }
                JSONObject jsonObject = (JSONObject) msg.getContent();
                players.get(activePlayer).library = (Library) jsonObject.get("library");
                players.get(activePlayer).board = (Board) jsonObject.get("board");
                players.get(activePlayer).pointsUntilNow = (int) jsonObject.get("points");
                for(int i = 0; i < numPlayers; i++) {
                    if(i == activePlayer)
                        continue;
                    players.get(i).board = (Board) jsonObject.get("board");
                    players.get(i).pointsMap.put(names.get(activePlayer), (int) jsonObject.get("points"));
                    for(int j = 0; j < numPlayers - 1; j++){
                        if(players.get(i).librariesOfOtherPlayers.get(j).name.equals(names.get(activePlayer)))
                            players.get(i).librariesOfOtherPlayers.set(j, (Library) jsonObject.get("library"));
                    }
                }
                FILEHelper.writeServer(this);
                if(!rmiClients.containsKey(names.get(activePlayer)))
                    sendToClient(activePlayer, new Message(STOP, null, null));
            }
            default -> {
                if(msg.getType() == LIB_FULL && !endGameSituation)
                    endGameSituation = true;
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
        while(true){
            waitForSeconds(Game.waitTimer * 2);
            for(String n: names){
                if(!rmiClients.containsKey(n) || disconnectedPlayers.contains(n) || (endGameSituation && activePlayer == 0))
                    continue;
                try {
                    rmiClients.get(n).pingClient();
                } catch (RemoteException e) {
                    playerDisconnected(names.indexOf(n));
                }
            }
        }
    }
}