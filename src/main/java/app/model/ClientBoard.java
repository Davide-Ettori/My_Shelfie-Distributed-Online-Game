package app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static app.model.Color.*;

public class ClientBoard implements Board {
    private final int DIM = 9;
    private Card[][] gameBoard = new Card[DIM][DIM];
    public CommonObjective commonObjective_1;
    public CommonObjective commonObjective_2;
    public LinkedList<Integer> pointsCO_1;
    public LinkedList<Integer> pointsCO_2;
    public Player player;

    public ClientBoard(int numPlayers, CommonObjective CO_1, CommonObjective CO_2, Player p){
        createBoard(numPlayers);
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
    public Card[][] getGameBoard(){return gameBoard;} // getter che saranno utili in seguito
    public CommonObjective getCO_1(){return commonObjective_1;}
    public CommonObjective getCO_2(){return commonObjective_2;}
    public LinkedList<Integer> getPCO_1(){return pointsCO_1;}
    public LinkedList<Integer> getPCO_2(){return pointsCO_2;}
    private void createBoard(int numPlayers) {return;} // la crea lui oppure la crea solo il server e poi la manda a tutti i client all'inizio ? forse meglio la seconda ?
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
    private boolean isBoardUnplayable() {
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                if(gameBoard[i][j].color != EMPTY && !isAlone(i, j)) // ha trovato una carta non vuota e che non è sola, quindi la board non è ingiocabile
                    return false;
            }
        }
        return true;
    }
    private boolean hasOneFreeSide(int x, int y){
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
    private boolean isCardPickable(ArrayList<Integer> cardPositions) {
        for(int i = 0; i < cardPositions.size(); i += 2 ){
            if(!hasOneFreeSide(cardPositions.get(i), cardPositions.get(i + 1)))
                return false;
        }
        return areCardsAligned(cardPositions);
    }
    private boolean areCardsAligned(ArrayList<Integer> cardPosition){
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
    public void updateBoard(Board board) {
        gameBoard = board.getGameBoard();
        commonObjective_1 = board.getCO_1();
        commonObjective_2 = board.getCO_2();
        pointsCO_1 = board.getPCO_1();
        pointsCO_2 = board.getPCO_2();
    }
    public void sendCurrentBoard() {
        // serializza this e lo manda al server
        return;
    }
    private void draw() {return;}
}