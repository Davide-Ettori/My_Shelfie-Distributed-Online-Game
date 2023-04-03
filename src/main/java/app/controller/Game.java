package app.controller;

import app.model.*;
import app.model.NetMode;
import app.model.Player;
import app.view.UIMode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static app.controller.MessageType.*;
import static app.controller.NameStatus.*;
import static app.model.State.*;
/**
 * class which represent the instance of the -current game
 * @author Ettori Faccincani
 * in theory it is mutable, but it is only instanced one time, at the start of the server
 */
public class Game implements Serializable {
    private final int PORT = 3000;
    private int targetPlayers;
    private int numPlayers;
    private int endPlayer;
    private int activePlayer = 0;
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<NetMode> netModes = new ArrayList<>();
    private ArrayList<UIMode> uiModes = new ArrayList<>();
    private ArrayList<Socket> playersSocket = new ArrayList<>();
    private ArrayList<ObjectOutputStream> outStreams = new ArrayList<>();
    private ArrayList<ObjectInputStream> inStreams = new ArrayList<>();
    private ArrayList<CommonObjective> bucketOfCO = Initializer.setBucketOfCO();
    private ArrayList<PrivateObjective> bucketOfPO = Initializer.setBucketOfPO();
    private boolean endGameSituation = false;
    private boolean time = true;
    private ArrayList<Thread> chatThreads = new ArrayList<>();
    private ServerSocket serverSocket; // Questa è l'unica socket del server. Potresti aver bisogno di passarla come argomento a Board
    /**
     * normal constructor for this type of object, this class is also the main process on the server
     * @param maxP the number of players for this game, chosen before by the user
     */
    public Game(int maxP){
        targetPlayers = maxP;
        if(FILEHelper.havaCachedServer()) { // per prima cosa dovresti controllare che non ci sia un server nella cache, nel caso lo carichi
            clone(FILEHelper.loadServerCLI());
            // da qui in poi fai continuare il server che hai caricato dalla cache
        }
        shuffleObjBucket();
        numPlayers = 0;
        new Thread(() -> { // imposto un timer di un minuto per aspettare le connessioni dei client
            try {
                int timer = 1;
                Thread.sleep(1000 * 60 * timer); // aspetto 5 minuti
                time = false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        try{serverSocket = new ServerSocket(PORT);}
        catch(Exception e){System.out.println(e.toString());}
        ArrayList<Thread> ths = new ArrayList<>();
        Thread th;
        System.out.println("\nServer listening...");

        while(numPlayers < targetPlayers && time){
            try{
                playersSocket.add(serverSocket.accept());
                th = new Thread(() ->{
                    try{getUserName(playersSocket.get(playersSocket.size() - 1));}
                    catch(Exception e){System.out.println(e.toString());}
                });
                th.start();
                ths.add(th);
                numPlayers++;
            }
            catch(Exception e){System.out.println(e.toString());}
        }
        for(Thread t: ths){
            try{t.join();}
            catch(Exception e){System.out.println(e.toString());}
        }
        if(numPlayers < targetPlayers){
            System.out.println("\nPlayer number not sufficient");
            System.exit(0);
        }
        System.out.println("\nThe game starts!");
        Player p;

        for(int i = 0; i < names.size(); i++){
            p = new Player(names.get(i), i == 0);
            p.board = new Board(numPlayers, bucketOfCO.get(0), bucketOfCO.get(1));
            p.board.name = names.get(i);
            if(i == 0)
                p.board.initBoard(numPlayers);
            else
                p.board = new Board(getChairman().board);
            p.library = new Library(names.get(i));
            p.setPrivateObjective(getPrivateObjective());
            p.pointsUntilNow = 0;
            p.setState(NOT_ACTIVE);
            p.activeName = getChairmanName();
            p.chairmanName = getChairmanName();
            for(int j = 0; j < numPlayers; j++)
                p.librariesOfOtherPlayers.add(new Library(names.get(j)));
            p.numPlayers = numPlayers;
            p.netMode = netModes.get(i);
            p.uiMode = uiModes.get(i);
            try {
                outStreams.get(i).writeObject(p);
            }catch (Exception e){System.out.println(e);}
            players.add(new Player().clone(p));
        }
        waitMoveFromClient();
    }

    /**
     * Check if the name that the client choose is already TAKEN
     * @param socket socket of the client that send his name
     * @author Ettori
     */
    synchronized private void getUserName(Socket socket){
        try {
            outStreams.add(new ObjectOutputStream(socket.getOutputStream()));
            inStreams.add(new ObjectInputStream(socket.getInputStream()));
            while (true) {
                String name = (String) inStreams.get(inStreams.size() - 1).readObject();
                if (isNameTaken(name) || name.equals("all") || name.equals("names")) {
                    outStreams.get(outStreams.size() - 1).writeObject(TAKEN);
                    continue;
                }
                outStreams.get(outStreams.size() - 1).writeObject(NOT_TAKEN);
                netModes.add((NetMode) inStreams.get(inStreams.size() - 1).readObject());
                uiModes.add((UIMode) inStreams.get(inStreams.size() - 1).readObject());
                names.add(name);
                break;
            }
        }catch(Exception e){System.out.println(e);}
    }

    /**
     * Send the message to the client that a new turn start,
     * two cases if is the turn of the client or is the turn of another client
     * @author Ettori Faccincani
     */
    private void notifyNewTurn(){
        System.out.println("enter notify new turn");
        for(int i = 0; i < numPlayers; i++){
            try {
                if (i == activePlayer) {
                    outStreams.get(i).writeObject(new Message(YOUR_TURN, "server", ""));
                    players.get(i).setState(ACTIVE);
                }
                else
                    outStreams.get(i).writeObject(new Message(CHANGE_TURN, "server", names.get(activePlayer)));
                System.out.println(i);
            }catch (Exception e){System.out.println(e);}
        }
        waitMoveFromClient();
    }

    /**
     * Wait the move of the client that are playing and set the chat,
     * when the client made the move and send it to server update Board and Library
     * @author Ettori Faccincani
     */
    private void waitMoveFromClient(){
        startChatServerThread();
        try {
            Message msg = (Message) inStreams.get(activePlayer).readObject(); // riceve UPDATE_GAME, UPDATE_BOARD, CHAT, CO_1, CO_2 e LIB_FULL
            if(msg.getType() == CHAT){
                sendChatToClients(names.get(activePlayer), msg.getAuthor(), (String)msg.getContent());
                waitMoveFromClient();
            }
            for (int i = 0; i < numPlayers; i++) { // broadcast a tutti tranne a chi ha mandato il messaggio
                if (i != activePlayer)
                    outStreams.get(i).writeObject(msg);
            }
            if(msg.getType() == UPDATE_GAME)
                waitForEndTurn();
            else
                waitMoveFromClient();
        }catch(Exception e){System.out.println(e);}
    }

    /**
     * start all the threads that listen for chat messages from the clients (and send the messages back to the players)
     */
    private void startChatServerThread(){
        if(chatThreads.size() != 0) // se non ci sono, inizializzo i thread che leggono un eventuale chat message dai client NON_ACTIVE (quello active non ne ha bisogno)
            return;
        flushAllBuffer();
        for(int i = 0; i < numPlayers; i++){
            if(i == activePlayer)
                continue;
            int finalI = i;
            chatThreads.add(new Thread(() ->{
                int index = finalI;
                while(true){
                    try{
                        Message msg = (Message) inStreams.get(index).readObject();
                        sendChatToClients(names.get(index), msg.getAuthor(), (String)msg.getContent()); // in questo caso l'author è il destinatario
                    }
                    catch(Exception e){System.out.println(e);}
                }
            }));
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
    private void sendChatToClients(String from, String to, String msg){
        try {
            if (to.equals("all")) {
                for (int i = 0; i < numPlayers; i++) {
                    if (!names.get(i).equals(from))
                        outStreams.get(i).writeObject(new Message(CHAT, "", msg));
                }
            }
            else if(getNameIndex(to) != -1){
                outStreams.get(getNameIndex(to)).writeObject(new Message(CHAT, "", msg));
            }
        }catch (Exception e){System.out.println(e);}
    }
    /**
     * Wait the end of the turn of the client and check if the library is full
     * @author Ettori Faccincani
     */
    private void waitForEndTurn(){
        System.out.println("wait for turn");
        stopChatThread();
        try {
            Message msg = (Message) inStreams.get(activePlayer).readObject();
            if(msg.getType() == END_TURN){
                System.out.println("end turn received");
                players.get(activePlayer).clone((Player) msg.getContent());
                if(players.get(activePlayer).library.isFull()) { // se la library ricevuta è piena entro nella fase finale del gioco
                    endGameSituation = true;
                    endPlayer = activePlayer;
                    for(int i = 0; i < names.size(); i++){
                        if(i != activePlayer)
                            outStreams.get(i).writeObject(new Message(LIB_FULL, names.get(i), null));
                    }
                    Thread.sleep(3000); // dai tempo agli altri giocatori di leggere il messaggio
                }
                System.out.println("turn advancing");
                advanceTurn();
            }
            else
                System.out.println("\nUnexpected message (not type = END_TURN) to server from: " + names.get(activePlayer));
        }catch(Exception e){System.out.println(e);}
    }

    /**
     * stops all the threads listening for chat messages from NON-active players
     */
    private void stopChatThread(){
        try{
            for (Thread chatThread : chatThreads) {
                chatThread.interrupt();
            }
            chatThreads = new ArrayList<>();
        }catch(Exception e){System.out.println(e);}
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
    private Player getChairman(){return players.get(0);}

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
     * Set the status of the players for the next turn and assign activePlayer to who play this turn
     * @author Ettori Faccincani
     */
    public void advanceTurn(){
        System.out.println("enter advance turn");
        players.get(activePlayer).setState(NOT_ACTIVE);
        do {
            activePlayer = (activePlayer + 1) % numPlayers;
        }while(players.get(activePlayer).getState() == DISCONNECTED);
        players.get(activePlayer).setState(ACTIVE);

        if(activePlayer == 0 && endGameSituation)
            sendFinalScoresToAll();
        try { // dai tempo al client active di andare in waitForTurn()
            Thread.sleep(1000);
        } catch (Exception e){System.out.println(e);}
        notifyNewTurn();
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
        Player p;
        for(int i = 0; i < numPlayers; i++){
            p = players.get(i);
            scores.add(p.pointsUntilNow + p.library.countGroupedPoints() + p.getPrivateObjective().countPoints(p.library.library) + i == endPlayer ? 1 : 0);
        }
        names.sort((a, b) -> {
            int n, m;
            n = scores.get(names.indexOf(a));
            m = scores.get(names.indexOf(b));
            if (n == m)
                return 0;
            if (n > m)
                return 1;
            return -1;
        });
        scores.sort(null);
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
        for(int i = 0; i < numPlayers; i++) {
            try {
                outStreams.get(i).writeObject(new Message(FINAL_SCORE, "server", getFinalScore()));
            } catch (Exception e) {System.out.println(e);}
        }
        FILEHelper.writeSucc(); // server uscito con successo, non hai messo niente nella cache
        System.exit(0);
    }

    /**
     * Make a clone of the server, helps for the persistence
     * @param g server status
     * @author Ettori
     */
    private void clone(Game g){
        targetPlayers = g.targetPlayers;
        numPlayers = g.numPlayers;
        endPlayer = g.endPlayer;
        endGameSituation = g.endGameSituation;
        activePlayer = g.activePlayer;
        players = g.players;
        names = g.names;
        playersSocket = g.playersSocket;
        outStreams = g.outStreams;
        inStreams = g.inStreams;
        bucketOfCO = g.bucketOfCO;
        bucketOfPO = g.bucketOfPO;
        time = g.time;
        serverSocket = g.serverSocket;
        chatThreads = g.chatThreads;
    }

    /**
     * flush the buffers of the socket networks communicating with all the clients
     */
    private void flushAllBuffer(){
        for(ObjectOutputStream out : outStreams) {
            try {
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}