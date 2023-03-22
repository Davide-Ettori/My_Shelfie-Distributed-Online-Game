package app.model;

import playground.rmi.Client;

import java.net.Socket;
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

    public static void main(String[] args){
        // prendi da input il nickname del utente
        // controlla se è già stato preso e nel caso chiedilo di nuovo
        // aspetta che il server gli mandi le informazioni iniziali
        // deserializza la classe Player che ti è arrivata tramite il buffer della socket
        // new Player(player); // crea la classe Player effettiva con cui l'utente giocherà
    }

    public Player(boolean isChair, String namePlayer) {
        name = namePlayer;
        isChairMan = isChair;
        library = new Library();
        pointsUntilNow = 0;
        state = NOT_ACTIVE;
    }
    public Player(Player p){
        name = p.name;
        isChairMan = p.isChairMan;
        library = p.library;
        objective = p.objective;
        pointsUntilNow = p.pointsUntilNow;
        state = p.state;
        board = p.board;
        startGame();
    }

    public void startGame(){
        // comincia ad ascoltare gli input che vengono dall'utente
        return;
    }
    public String getName() {
        return name;
    }

    public void setPrivateObjective(PrivateObjective obj) {
        objective = obj;
    }

    private boolean pickCards(ArrayList<Integer> coord) { // coordinate accoppiate
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < coord.size(); i += 2) {
            cards.add(board.getGameBoard()[coord.get(i)][coord.get(i + 1)]);
        }

        deployCards(chooseCol(cards.size()), chooseCardsOrder(cards));
        return true;
    }

    private boolean deployCards(int col, ArrayList<Card> cards) {
        library.insertCards(col, cards);
        return true;
    }

    private int chooseCol(int numCards) {
        int col;
        while (true) { // questo va fino a che l'utente sceglie la colonna, per ora lo forziamo a mano
            col = 2;
            if (col <= 0 || col > library.COLS) // scelta non valida
                continue;
            if (!library.checkCol(col, numCards)) // scelta non valida
                continue;
            break;
        }
        return col;
    }

    private void swapCards(ArrayList<Card> cards, int i, int j) {
        Card temp = cards.get(i);
        cards.set(i, cards.get(j));
        cards.set(j, temp);
        return;
    }

    private ArrayList<Card> chooseCardsOrder(ArrayList<Card> cards) {
        while (true) { // questo va fino a che l'utente sceglie l'ordine delle carte, per ora lo forziamo a mano
            swapCards(cards, 0, 1); // scambio 2 carte a caso
            break;
        }
        return cards;
    }
    public State getState() {
        return state;
    }
    public void setState(State newState) {
        state = newState;
    }
    public void setBoard(Board b){board.updateBoard(b);}
}