package app.controller;

import app.model.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import static app.model.State.*;
/**
 * class which represent the instance of the current game
 * @author Ettori Faccincani
 * in theory it is mutable, but it is only instanced one time, at the start of the server
 */
public class Game {
    public static final int MAX_PLAYERS = 4;
    private int numPlayers;
    private ArrayList<Player> players;
    private ArrayList<String> names;
    private ArrayList<Socket> playersSocket;
    private ArrayList<ObjectOutputStream> outStreams;
    private ArrayList<ObjectInputStream> inStreams;
    private Player chairman;
    private final ArrayList<CommonObjective> bucketOfCO = Initializer.setBucketOfCO();
    private final ArrayList<PrivateObjective> bucketOfPO = Initializer.setBucketOfPO();
    private boolean time = true;
    private Socket serverSocket; // Questa è la unica socket del server. Potresti aver bisogno di passarla come argomento a Board
    public static void main(String[] args){
        new Game();
    }

    public Game(){
        int numPlayers = 0;
        Player p;
        new Thread(() -> { // imposto un timer di un minuto per aspettare le connessioni dei client
            try {
                Thread.sleep(1000 * 60); // aspetto un minuto
                time = false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        // in questo ciclo aspetta che i giocatori si connettano, massimo per un minuto ad esempio
        // il primo che si connette è anche il chairman
        while(numPlayers < 4 && time){
            // riceve una richiesta di nickname, la accetta o rifiuta
            // se la accetta crea un oggetto di tipo player e lo manda al client
            // poi aggiunge il player alla lista e incrementa il player counter di 1

            String name = "pippo"; // Esempio a caso, il nome dovrebbe inserirlo l'utente sul client e poi viene arriva qui al server
            if(isNameTaken(name))
                continue;
            if(numPlayers == 0){
                p = new Player(true, name);
                chairman = p;
            }
            else{
                p = new Player(false, name);
            }
            players.add(p);
            //playersSocket.add(socket); // aggiunge la socket del client che si è appena connesso. corrispondono univocamente con l'indice nel ArrayList
            numPlayers++;
            numPlayers = 3; // Esempio a caso che setta il numero di giocatori a tre. Ovviamente andrà eliminato

            break; // per ora serve questi break perché non ci sono client che si possono connettere
        }
        if(numPlayers < 2) // se non ci sono abbastanza giocatori chiudi il server e annulla la partita
            System.exit(0);
        chairman.board.initBoard(numPlayers);
        for(int i = 0; i < players.size(); i++)
            players.get(i).board = new Board(chairman.board);
        startGame();
    }
    private void getUserName(Socket s){return;}
    private void listenForReconnection(){return;}
    public void startGame(){ // inizializza la Board e comincia l'interazione con i client
        setCommonObjective();
        setPrivateObjective();
        chairman.setState(ACTIVE);
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
    private void setCommonObjective(){ // la situazione iniziale è quella del chairman, gli altri si adattano e poi comincia il gioco
        chairman.board.setCO_1(bucketOfCO.get(0));
        chairman.board.setCO_2(bucketOfCO.get(1));
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
    public void updatePlayersBoardAfterEndTurn(Player p, String name){ // chiamato da remoto alla fine di ogni turno
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getName().equals(name)) // il nome è univoco
                players.set(i, new Player(p));
            else
                players.get(i).board = new Board(p.board);
        }
        advanceTurn(name);
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