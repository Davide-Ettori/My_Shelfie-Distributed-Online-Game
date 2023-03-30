package app.controller;

import app.model.*;
import app.model.player.PlayerGUI;

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
public class GameGUI {
    private final int PORT = 3000;
    public static final int MAX_PLAYERS = 4;
    private int numPlayers;
    private ArrayList<PlayerGUI> players;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Socket> playersSocket = new ArrayList<>();
    private ArrayList<ObjectOutputStream> outStreams = new ArrayList<>();
    private ArrayList<ObjectInputStream> inStreams = new ArrayList<>();
    private final ArrayList<CommonObjective> bucketOfCO = Initializer.setBucketOfCO();
    private final ArrayList<PrivateObjective> bucketOfPO = Initializer.setBucketOfPO();
    private boolean time = true;
    private ServerSocket serverSocket; // Questa è la unica socket del server. Potresti aver bisogno di passarla come argomento a Board

    public GameGUI(){
        int numPlayers = 0;
        PlayerGUI p;
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
        Thread th = null;
        System.out.println("\nServer listening...");
        while(numPlayers < 4 && time){
            try{
                playersSocket.add(serverSocket.accept());
                th = new Thread(() ->{
                    try{getUserName(playersSocket.get(playersSocket.size() - 1));}
                    catch(Exception e){System.out.println(e.toString());}
                });
                th.start();
                numPlayers++;
            }
            catch(Exception e){System.out.println(e.toString());}
        }
        if(th == null || numPlayers < 2)
            System.exit(0);
        try{th.join();}
        catch(Exception e){System.out.println(e.toString());}

        System.out.println("\nThe game starts!");
        System.exit(0);

        getChairman().board.initBoard(numPlayers);
        for(int i = 0; i < players.size(); i++)
            players.get(i).board = new Board(getChairman().board);
        startGame();
    }
    synchronized private void getUserName(Socket socket) throws Exception{
        outStreams.add(new ObjectOutputStream(socket.getOutputStream()));
        inStreams.add(new ObjectInputStream(socket.getInputStream()));
        outStreams.get(outStreams.size() - 1).writeObject("GUI");
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
    private void listenForReconnection(){return;}
    private String getChairmanName(){return names.get(0);}
    private PlayerGUI getChairman(){return players.get(0);}
    public void startGame(){ // inizializza la Board e comincia l'interazione con i client
        setCommonObjective();
        setPrivateObjective();
        getChairman().setState(ACTIVE);
        return;
    }
    public void endGame(){return;} // chiude tutte le connessioni e termina la partita
    public void restartGame(){return;} // ricomincia una partita interrotta a metà
    private boolean isNameTaken(String name){return names.contains(name);}
    private int getNameIndex(String name){
        for(int i = 0; i < names.size(); i++){
            if(names.get(i).equals(name))
                return i;
        }
        return -1;
    }
    private void setCommonObjective(){ // la situazione iniziale è quella del getChairman(), gli altri si adattano e poi comincia il gioco
        getChairman().board.setCO_1(bucketOfCO.get(0));
        getChairman().board.setCO_2(bucketOfCO.get(1));
    }
    private void setPrivateObjective(){
        for(int i = 0; i < players.size(); i++)
            players.get(i).setPrivateObjective(bucketOfPO.get(i));
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
    private Socket getSocketByName(String name){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getName().equals(name))
                return playersSocket.get(i);
        }
        return null;
    }
    public ArrayList<Library> getOtherLibraries(String name){ // chiamato da remoto
        ArrayList<Library> res = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getName().equals(name))
                continue;
            res.add(players.get(i).library);
        }
        return res;
    }
    public String getActivePlayer(){ // chiamato da remoto
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getState() == ACTIVE)
                return players.get(i).getName();
        }
        return "";
    }
    public void advanceTurn(String name){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getName().equals(name)){
                players.get((i + 1) % players.size()).setState(ACTIVE);
            }
        }
        // notifica tutti i giocatori che il turno è cambiato tramite un messaggio socket, a quel punto loro richiederanno il nuovo stato
    }
    private void sendFinalScoresToAll(){return;}
}