package app.controller;

import app.model.Card;
import app.model.CommonObjective;
import app.model.Player;
import app.model.PrivateObjective;

import java.util.ArrayList;
import java.util.Random;

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
        int numPlayers = 3; // questo dato sarà dato dall'utente
        board = new ServerBoard(numPlayers);
        startGame();
    }

    public void startGame(){return;} // inizializza il server e comincia l'interazione con i client
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
    public void setActivePlayer(Player p){activePlayer = p;}
}