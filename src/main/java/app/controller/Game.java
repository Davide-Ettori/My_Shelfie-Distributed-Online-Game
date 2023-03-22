package app.controller;

import app.model.*;

import java.util.ArrayList;
import java.util.Random;

import static app.model.State.*;

public class Game {
    public static final int MAX_PLAYERS = 4;
    private int numPlayers;
    private ArrayList<Player> players;
    private Player activePlayer;
    private Player chairman;
    private ServerBoard board;
    private ArrayList<CommonObjective> bucketOfCO;
    private ArrayList<PrivateObjective> bucketOfPO;

    public static void main(String[] args){
        new Game();
    }

    public Game(){
        int numPlayers;
        while(true){
            numPlayers = 3; // questo dato sarà fornito dall'utente
            break; // in questo ciclo aspetta che i giocatori si connettano, massimo per un minuto ad esempio
            // quando un giocatore si connette lo aggiungi alla lista dei giocatori
            // il primo che si connette è anche il chairman
        }
        board = new ServerBoard(numPlayers);
        startGame();
    }

    public void startGame(){ // inizializza la serverBoard e comincia l'interazione con i client
        setCommonObjective();
        setPrivateObjective();
        setActivePlayer(chairman);

        return;
    }
    public void endGame(){return;} // chiude tutte le connessioni e termina la partita
    public void restartGame(){return;} // ricomincia una partita interrotta a metà
    public boolean isNameTaken(String name){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getName().equals(name))
                return false;
        }
        return true;
    }
    private void setCommonObjective(){
        board.setCO_1(bucketOfCO.get(0));
        board.setCO_2(bucketOfCO.get(1));
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
    public void setActivePlayer(Player p){activePlayer = p; p.setState(ACTIVE);}
}