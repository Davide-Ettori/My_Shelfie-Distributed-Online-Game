package app.model2;

import java.util.ArrayList;
import java.util.LinkedList;

public class Clientboard implements Board {
    private int DIM = 9;
    private Card[][] gameBoard = new Card[DIM][DIM];
    public CommonObjective commonObjective_1;
    public CommonObjective commonObjective_2;
    public LinkedList<Integer> pointCO_1 = new LinkedList<Integer>();
    public LinkedList<Integer> pointCO_2 = new LinkedList<Integer>();

    //public Player player; commentato altrimenti dà errore

    public Clientboard(Card[][] gameBoard,CommonObjective commonObjective_1,CommonObjective commonObjective_2,LinkedList<Integer> pointCO_1,LinkedList<Integer> pointCO_2/*,Player player*/){
        this.gameBoard=gameBoard;
        this.commonObjective_1=commonObjective_1;
        this.commonObjective_2=commonObjective_2;
        this.pointCO_1=pointCO_1;
        this.pointCO_2=pointCO_2;
        //this.player=player; commentato altrimenti dà errore
    }
    private void createBoard(int numplayer) {

    }

    ;

    private boolean isBoardUnplayable() {

        return true;
    }

    ;

    private boolean isCardPickable(ArrayList<Integer> cardpositions) {

        return true;
    }

    ;

    private boolean isCardNear(int x, int y) {

        return true;
    }

    private boolean isCardAligned(ArrayList<Integer> cardposition) {

        return true;
    }

    ;

    public void updateBoard(Board board) {

    }

    ;

    public void sendCurrentBoard() {

    }

    ;

    private void draw() {

    }

    ;
}

