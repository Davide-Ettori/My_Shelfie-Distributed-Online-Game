package app.controller;

import app.model.*;
import app.model.player.PlayerCLI;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import static app.controller.MessageType.*;
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
    private int endPlayer;
    private int activePlayer = -1;
    private ArrayList<PlayerCLI> players = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Socket> playersSocket = new ArrayList<>();
    private ArrayList<ObjectOutputStream> outStreams = new ArrayList<>();
    private ArrayList<ObjectInputStream> inStreams = new ArrayList<>();
    private final ArrayList<CommonObjective> bucketOfCO = Initializer.setBucketOfCO();
    private final ArrayList<PrivateObjective> bucketOfPO = Initializer.setBucketOfPO();
    private boolean endGameSituation = false;
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
            System.out.println("\nPlayer number not sufficient");
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
        String resp = (String) inStreams.get(inStreams.size() - 1).readObject();
        if(resp.equals("FAIL")){
            numPlayers--;
            return;
        }
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
                    outStreams.get(i).writeObject(new Message(YOUR_TURN, "server", null));
                else
                    outStreams.get(i).writeObject(new Message(CHANGE_TURN, "server", names.get(activePlayer)));
            }catch (Exception e){System.out.println(e);}
        }
        waitMoveFromClient();
    }
    private void waitMoveFromClient(){
        try {
            Message msg = (Message) inStreams.get(activePlayer).readObject(); // riceve sia UPDATE_GAME che UPDATE_BOARD, ma fa sempre la stessa cosa (come è giusto che sia)
            for (int i = 0; i < numPlayers; i++) { // broadcast a tutti tranne a chi ha mandato il messaggio
                if (i != activePlayer)
                    outStreams.get(i).writeObject(msg);
            }
        }catch(Exception e){System.out.println(e);}
        waitForEndTurn();
    }
    private void waitForEndTurn(){
        try {
            Message msg = (Message) inStreams.get(activePlayer).readObject();
            if(msg.getType() == END_TURN){
                players.get(activePlayer).clone((PlayerCLI) msg.getContent());
                if(players.get(activePlayer).library.isFull()) { // se la library ricevuta è piena entro nella fase finale del gioco
                    endGameSituation = true;
                    endPlayer = activePlayer;
                }
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
        else {
            players.get(activePlayer).setState(NOT_ACTIVE);
            do {
                activePlayer = (activePlayer + 1) % numPlayers;
            }while(players.get(activePlayer).getState() == DISCONNECTED);
            players.get(activePlayer).setState(ACTIVE);
        }
        if(activePlayer == 0 && endGameSituation)
            sendFinalScoresToAll();
        notifyNewTurn();
    }
    private String getFinalScore(){
        ArrayList<Integer> scores = new ArrayList<>();
        StringBuilder res = new StringBuilder();
        PlayerCLI p;
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
    private void sendFinalScoresToAll(){
        for(int i = 0; i < numPlayers; i++)
            try{
                outStreams.get(i).writeObject(new Message(FINAL_SCORE, "server", getFinalScore()));
            }catch (Exception e){System.out.println(e);}
    }
}