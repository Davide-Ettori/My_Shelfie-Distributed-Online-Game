package app.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class ClientBoard implements Board {
    private int DIM = 9;
    private Card[][] gameBoard = new Card[DIM][DIM];
    public CommonObjective commonObjective_1;
    public CommonObjective commonObjective_2;
    public LinkedList<Integer> pointCO_1;
    public LinkedList<Integer> pointCO_2;
    public Player player;

    public ClientBoard(int numPlayers, CommonObjective commonObjective_1, CommonObjective commonObjective_2, Player player){
        createBoard(numPlayers);
        this.commonObjective_1 = commonObjective_1;
        this.commonObjective_2 = commonObjective_2;
        if(numPlayers == 2){
            this.pointCO_1 = new LinkedList<Integer>(Arrays.asList(4, 8)); // vanno presi con il metodo list.pop() --> da destra verso sinistra
            this.pointCO_2 = new LinkedList<Integer>(Arrays.asList(4, 8));
        }
        else if(numPlayers == 3){
            this.pointCO_1 = new LinkedList<Integer>(Arrays.asList(4,6,8));
            this.pointCO_2 = new LinkedList<Integer>(Arrays.asList(4,6,8));
        }
        else{
            this.pointCO_1 = new LinkedList<Integer>(Arrays.asList(2,4,6,8));
            this.pointCO_2 = new LinkedList<Integer>(Arrays.asList(2,4,6,8));
        }
        this.player = player;
    }
    private void createBoard(int numPlayers) {return;} // la crea lui oppure la crea solo il server e poi la manda a tutti i client all'inizio ? forse meglio la seconda ?
    private boolean isBoardUnplayable() {
        return true;
    }
    private boolean isCardPickable(ArrayList<Integer> cardPositions) {
        return true;
    }
    private boolean isCardNear(int x, int y) {
        return true;
    }
    private boolean isCardAligned(ArrayList<Integer> cardPosition) {
        return true;
    }
    public void updateBoard(Board board) {return;}
    public void sendCurrentBoard() {return;}
    private void draw() {return;}
}