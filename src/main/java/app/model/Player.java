package app.model;

import playground.rmi.Client;

import java.util.ArrayList;

import static app.model.State.NOT_ACTIVE;

public class Player {
    private String name;
    private boolean isChairMan;
    private Library library;
    private PrivateObjective objective;
    public int pointsUntilNow;
    private State state;
    private ClientBoard board;

    public Player(boolean isChair, String namePlayer, PrivateObjective obj, ClientBoard b){
        name = namePlayer;
        isChairMan = isChair;
        library = new Library();
        objective = obj;
        pointsUntilNow = 0;
        state = NOT_ACTIVE;
        board = b;
    }

    private boolean pickCards (ArrayList<Integer> coord){ // coordinate accoppiate
        ArrayList<Card> cards = new ArrayList<>();
        for(int i = 0; i < coord.size(); i += 2){
            cards.add(board.getGameBoard()[coord.get(i)][coord.get(i + 1)]);
        }

        deployCards(chooseCol(cards.size()), chooseCardsOrder(cards));
        return true;
    }
    private boolean deployCards (int col, ArrayList<Card> cards){
        library.insertCards(col, cards);
        return true;
    }
    private int chooseCol(int numCards){
        int col;
        while(true){ // questo va fino a che l'utente sceglie la colonna, per ora lo forziamo a mano
            col = 2;
            if(col <= 0 || col > library.COLS) // scelta non valida
                continue;
            if(!library.checkCol(col, numCards)) // scelta non valida
                continue;
            break;
        }
        return col;
    }
    private void swapCards(ArrayList<Card> cards, int i, int j){
        Card temp = cards.get(i);
        cards.set(i, cards.get(j));
        cards.set(j, temp);
        return;
    }
    private ArrayList<Card> chooseCardsOrder(ArrayList<Card> cards){
        while(true){ // questo va fino a che l'utente sceglie l'ordine delle carte, per ora lo forziamo a mano
            swapCards(cards, 0, 1); // scambio 2 carte a caso
            break;
        }
        return cards;
    }
    public State getState(){return state;}
    public void setState(State newState){state = newState;}
}
