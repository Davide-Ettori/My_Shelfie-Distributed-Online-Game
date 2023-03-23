package app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import static app.model.Color.*;

public class Board{
    private final int DIM = 9;
    private Card[][] gameBoard = new Card[DIM][DIM];
    public CommonObjective commonObjective_1;
    public CommonObjective commonObjective_2;
    public LinkedList<Integer> pointsCO_1;
    public LinkedList<Integer> pointsCO_2;
    public Player player;
    private ArrayList<Card> bucketOfCards;
    private int[][] gameMatrix;

    public Board(int numPlayers, CommonObjective CO_1, CommonObjective CO_2, Player p){
        commonObjective_1 = CO_1;
        commonObjective_2 = CO_2;
        if(numPlayers == 2){
            pointsCO_1 = new LinkedList<Integer>(Arrays.asList(4, 8)); // vanno presi con il metodo list.pop() --> da destra verso sinistra
            pointsCO_2 = new LinkedList<Integer>(Arrays.asList(4, 8));
        }
        else if(numPlayers == 3){
            pointsCO_1 = new LinkedList<Integer>(Arrays.asList(4,6,8));
            pointsCO_2 = new LinkedList<Integer>(Arrays.asList(4,6,8));
        }
        else{
            pointsCO_1 = new LinkedList<Integer>(Arrays.asList(2,4,6,8));
            pointsCO_2 = new LinkedList<Integer>(Arrays.asList(2,4,6,8));
        }
        player = p;
    }
    public Board(Board c){ // copy constructor
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                gameBoard[i][j] = new Card(c.gameBoard[i][j]);
            }
        }
        commonObjective_1 = c.commonObjective_1;
        commonObjective_2 = c.commonObjective_2;
        pointsCO_1 = new LinkedList<>(c.pointsCO_1);
        pointsCO_2 = new LinkedList<>(c.pointsCO_2);
        bucketOfCards = new ArrayList<>(c.bucketOfCards);
        player = new Player(player);
    }
    public Card[][] getGameBoard(){return gameBoard;} // getter che saranno utili in seguito
    public CommonObjective getCO_1(){return commonObjective_1;}
    public CommonObjective getCO_2(){return commonObjective_2;}
    public LinkedList<Integer> getPCO_1(){return pointsCO_1;}
    public LinkedList<Integer> getPCO_2(){return pointsCO_2;}
    private boolean isValidIndex(int x, int y){
        return x >= 0 && x < DIM && y >= 0 && y < DIM;
    }
    private boolean isAlone(int x, int y){ // returna true sse la carta è da sola, ovvero non ha nessuna carta adiacente
        if(isValidIndex(x + 1, y) && gameBoard[x + 1][y].color != EMPTY)
            return false;
        if(isValidIndex(x - 1, y) && gameBoard[x - 1][y].color != EMPTY)
            return false;
        if(isValidIndex(x, y + 1) && gameBoard[x][y + 1].color != EMPTY)
            return false;
        if(isValidIndex(x, y - 1) && gameBoard[x][y - 1].color != EMPTY)
            return false;
        return true;
    }
    public boolean isBoardUnplayable() {
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                if(gameBoard[i][j].color != EMPTY && !isAlone(i, j)) // ha trovato una carta non vuota e che non è sola, quindi la board non è ingiocabile
                    return false;
            }
        }
        // se è unplayable devi chiamare il metodo fillBoard(numeroDiGiocatori)
        return true;
    }
    public boolean hasOneFreeSide(int x, int y){
        if(!isValidIndex(x , y) || gameBoard[x][y].color == EMPTY) // controlla che la carda corrente sia sensata, poi controlla le carte vicine
            return false;
        if(isValidIndex(x + 1, y) && gameBoard[x + 1][y].color == EMPTY)
            return true;
        if(isValidIndex(x - 1, y) && gameBoard[x - 1][y].color == EMPTY)
            return true;
        if(isValidIndex(x, y + 1) && gameBoard[x][y + 1].color == EMPTY)
            return true;
        if(isValidIndex(x, y - 1) && gameBoard[x][y - 1].color == EMPTY)
            return true;
        return false;
    }
    public boolean areCardsPickable(ArrayList<Integer> cardPositions) {
        for(int i = 0; i < cardPositions.size(); i += 2 ){
            if(!hasOneFreeSide(cardPositions.get(i), cardPositions.get(i + 1)))
                return false;
        }
        return areCardsAligned(cardPositions);
    }
    public boolean areCardsAligned(ArrayList<Integer> cardPosition){
        boolean allInRow = true;
        for(int i = 0; i < cardPosition.size(); i += 2){
            if(cardPosition.get(i) != cardPosition.get(0))
                allInRow = false;
        }
        boolean allInCol = true;
        for(int i = 1; i < cardPosition.size(); i += 2){
            if(cardPosition.get(i) != cardPosition.get(1))
                allInCol = false;
        }
        return allInRow || allInCol;
    }
    /** ------------------------------------------------------------------------------------------------------------- */
    public void initBoard(int numPlayers){
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
                    bucketOfCards.remove(0); // bucketOfCards viene anche lui scambiato con il server tramite il client
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
    public void setCO_1(CommonObjective obj){commonObjective_1 = obj;} // questi 2 sono i setter usati inizialmente dalla classe Game
    public void setCO_2(CommonObjective obj){commonObjective_2 = obj;}
    private void draw() {return;}
}