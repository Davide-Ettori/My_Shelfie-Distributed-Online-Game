package app.controller;

import app.model.*;
import app.model.player.PlayerCLI;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import static app.controller.NameStatus.*;
import static app.model.State.*;
/**
 * class which represent the instance of the current game
 * @author Ettori Faccincani
 * in theory it is mutable, but it is only instanced one time, at the start of the server
 */
public class GameCLI {
    private final int PORT = 3000;
    public static final int MAX_PLAYERS = 4;
    private int numPlayers;
    private int activePlayer = -1;
    private ArrayList<PlayerCLI> players = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Socket> playersSocket = new ArrayList<>();
    private ArrayList<ObjectOutputStream> outStreams = new ArrayList<>();
    private ArrayList<ObjectInputStream> inStreams = new ArrayList<>();
    private final ArrayList<CommonObjective> bucketOfCO = Initializer.setBucketOfCO();
    private final ArrayList<PrivateObjective> bucketOfPO = Initializer.setBucketOfPO();
    private boolean time = true;
    private ServerSocket serverSocket; // Questa è l'unica socket del server. Potresti aver bisogno di passarla come argomento a Board

    public GameCLI(){
        shuffleObjBucket();
        int numPlayers = 0;
        new Thread(() -> { // imposto un timer di un minuto per aspettare le connessioni dei client
            try {
                Thread.sleep(1000 * 60 * 5); // aspetto 5 minuti
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

        while(numPlayers < 4 && time){
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
        if(numPlayers < 2){
            System.out.println("\nGiocatori insufficienti");
            System.exit(0);
        }
        System.out.println("\nThe game starts!");
        System.exit(0);
        PlayerCLI p;

        for(int i = 0; i < names.size(); i++){
            p = new PlayerCLI(names.get(i), i == 0);
            p.board = new Board(numPlayers, bucketOfCO.get(0), bucketOfCO.get(1));
            p.board.name = names.get(i);
            if(i == 0)
                p.board.fillBoard(numPlayers);
            else
                p.board = new Board(getChairman().board);
            p.library = new Library(names.get(i));
            p.setPrivateObjective(getPrivateObjective());
            p.pointsUntilNow = 0;
            p.setState(NOT_ACTIVE);
            p.setActiveName(getChairmanName());
            for(int j = 0; j < numPlayers; j++)
                p.librariesOfOtherPlayers.add(new Library(names.get(j)));
            p.numPlayers = numPlayers;
            try {
                outStreams.get(i).writeObject(p);
            }catch (Exception e){System.out.println(e);}
        }
        advanceTurn();
    }
    synchronized private void getUserName(Socket socket) throws Exception{
        outStreams.add(new ObjectOutputStream(socket.getOutputStream()));
        inStreams.add(new ObjectInputStream(socket.getInputStream()));
        outStreams.get(outStreams.size() - 1).writeObject("CLI");
        while(true){
            String name = (String) inStreams.get(inStreams.size() - 1).readObject();
            if(isNameTaken(name)){
                outStreams.get(outStreams.size() - 1).writeObject(TAKEN);
                continue;
            }
            outStreams.get(outStreams.size() - 1).writeObject(NOT_TAKEN);
            names.add(name);
            return;
        }
    }
    private void notifyNewTurn(){
        for(int i = 0; i < numPlayers; i++){
            try {
                if (i == activePlayer)
                    outStreams.get(i).writeObject(new Message("your turn", "server", null));
                else
                    outStreams.get(i).writeObject(new Message("change turn", "server", names.get(activePlayer)));
            }catch (Exception e){System.out.println(e);}
        }
        waitMoveFromClient();
    }
    private void waitMoveFromClient(){
        try {
            Message msg = (Message) inStreams.get(activePlayer).readObject();
            for(int i = 0; i < numPlayers; i++)
                outStreams.get(i).writeObject(msg);
        }catch(Exception e){System.out.println(e);}
        waitForEndTurn();
    }
    private void waitForEndTurn(){
        try {
            Message msg = (Message) inStreams.get(activePlayer).readObject();
            if(msg.getType().equals("end turn")){
                players.get(activePlayer).clone((PlayerCLI) msg.getContent());
                advanceTurn();
            }
        }catch(Exception e){System.out.println(e);}
    }
    private void listenForReconnection(){return;}
    private String getChairmanName(){return names.get(0);}
    private PlayerCLI getChairman(){return players.get(0);}
    private boolean isNameTaken(String name){return names.contains(name);}
    private int getNameIndex(String name){
        for(int i = 0; i < names.size(); i++){
            if(names.get(i).equals(name))
                return i;
        }
        return -1;
    }
    private PrivateObjective getPrivateObjective(){
        PrivateObjective res = bucketOfPO.get(0);
        bucketOfPO.remove(0);
        return res;
    }
    private void shuffleObjBucket(){ // randomizza gli obbiettivi
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
    public void advanceTurn(){
        if(activePlayer == -1) {
            activePlayer++;
            players.get(activePlayer).setState(ACTIVE);
        }
        players.get(activePlayer).setState(NOT_ACTIVE);
        activePlayer = (activePlayer + 1) % numPlayers;
        players.get(activePlayer).setState(ACTIVE);
        notifyNewTurn();
    }
    private void sendFinalScoresToAll(){return;}
}