package app.controller;

import app.model.Board;
import app.model.Card;
import app.model.CommonObjective;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import static app.model.Color.*;

public class ServerBoard implements Board {
    private Card[][] gameBoard = new Card[DIM][DIM];
    private int[][] gameMatrix;
    private CommonObjective commonObjective_1;
    private CommonObjective commonObjective_2;
    public LinkedList<Integer> pointsCO_1;
    public LinkedList<Integer> pointsCO_2;
    private ArrayList<Card> bucketOfCards;

    public ServerBoard(int numPlayers){
        for(int i = 0; i < DIM; i++){ // inizializzo la board con solo carte vuote, costruttore apposito
            for(int j = 0; j < DIM; j++)
                gameBoard[i][j] = new Card(i, j);
        }
        if(numPlayers == 2){
            pointsCO_1 = new LinkedList<Integer>(Arrays.asList(4, 8)); // vanno presi con il metodo list.pop() --> da destra verso sinistra
            pointsCO_2 = new LinkedList<Integer>(Arrays.asList(4, 8)); // li puoi vedere con il metodo list.peekLast() --> returna l'ultimo elemento
        }
        else if(numPlayers == 3){
            pointsCO_1 = new LinkedList<Integer>(Arrays.asList(4,6,8));
            pointsCO_2 = new LinkedList<Integer>(Arrays.asList(4,6,8));
        }
        else{
            pointsCO_1 = new LinkedList<Integer>(Arrays.asList(2,4,6,8));
            pointsCO_2 = new LinkedList<Integer>(Arrays.asList(2,4,6,8));
        }
        bucketOfCards = new ArrayList<>(); // andrebbe inizializzato con i 132 possibili valori delle carte
        createBoard(numPlayers);
    }
    private void draw(){return;}
    public Card[][] getGameBoard(){return gameBoard;}
    public CommonObjective getCO_1(){return commonObjective_1;}
    public CommonObjective getCO_2(){return commonObjective_2;}
    public void setCO_1(CommonObjective obj){commonObjective_1 = obj;} // questi 2 sono i setter usati inizialmente dalla classe Game
    public void setCO_2(CommonObjective obj){commonObjective_2 = obj;}
    public LinkedList<Integer> getPCO_1(){return pointsCO_1;}
    public LinkedList<Integer> getPCO_2(){return pointsCO_2;}

    private void createBoard(int numPlayers){
        shuffleCardsBucket();
        gameMatrix = new int[][]{ // questo è lo schema della board --> 0: sempre vuota, 2,3,4: numero di giocatori minimo per attivarla
                {0,0,0,3,4,0,0,0,0},
                {0,0,0,2,2,4,0,0,0},
                {0,0,3,2,2,2,3,0,0},
                {0,4,2,2,2,2,2,2,3},
                {4,2,2,2,2,2,2,2,4},
                {3,2,2,2,2,2,2,4,0},
                {0,0,3,2,2,2,3,0,0},
                {0,0,0,4,2,2,0,0,0},
                {0,0,0,0,4,3,0,0,0}
        };
        fillBoard(numPlayers);
        sendCurrentBoard(); // manda ciò che hai creato al client
    }
    public void fillBoard(int numPlayers){
        Card card;
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                if(gameMatrix[i][j] == 0)
                    continue;
                if(gameBoard[i][j].color != EMPTY)
                    continue;
                if(numPlayers >= gameMatrix[i][j]){
                    card = bucketOfCards.get(0);
                    card.setX(i);
                    card.setY(j);
                    gameBoard[i][j] = card;
                    bucketOfCards.remove(0);
                }
            }
        }
    }
    public void shuffleCardsBucket(){
        Random rand = new Random();
        Card temp;
        int j;
        for(int i = 0; i < bucketOfCards.size(); i++){
            j = rand.nextInt(bucketOfCards.size());
            temp = bucketOfCards.get(i);
            bucketOfCards.set(i, bucketOfCards.get(j));
            bucketOfCards.set(j, temp);
        }
    }
    public void updateBoard(Board board) {
        gameBoard = board.getGameBoard();
        commonObjective_1 = board.getCO_1();
        commonObjective_2 = board.getCO_2();
        pointsCO_1 = board.getPCO_1();
        pointsCO_2 = board.getPCO_2();
    }
    public void sendCurrentBoard() {
        // serializza this e lo manda al server --> lo faremo in seguito
        return;
    }
}