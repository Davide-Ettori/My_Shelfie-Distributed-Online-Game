package app.model_1;

import java.util.ArrayList;

import static app.model_1.State.NOT_ACTIVE;

public class Player {
    private String name;
    private boolean isChairMan;
    private Library library;
    private PrivateObjective objective;
    public int pointsUntilNow;
    private State state;

    public Player(boolean isChair, String namePlayer, PrivateObjective obj){
        name = namePlayer;
        isChairMan = isChair;
        library = new Library();
        objective = obj;
        pointsUntilNow = 0;
        state = NOT_ACTIVE;
    }

    private boolean pickCards (ArrayList<Integer> coord){return true;} // coordinate accoppiate
    private boolean deployCards (ArrayList<Card> cards, int col){return true;}
    private int chooseCol(int numCards){return -1;}
    private ArrayList<Card> chooseCardsOrder(ArrayList<Card> cards){return cards;}
    public State getState(){return state;}
    public void setState(State newState){state = newState;}
}


