package app.model;

import app.controller.Game;

import java.net.Socket;
import java.util.ArrayList;

import static app.model.State.*;

public class Player {
    private String name;
    private boolean isChairMan;
    private Library library;
    private PrivateObjective objective;
    public int pointsUntilNow;
    private State state;
    public Board board;
    private ArrayList<Library> librariesOfOtherPlayers;
    private Socket mySocket;
    private Game gameRMI;

    public static void main(String[] args){
        // prendi da input il nickname del utente
        // apri una connessione socket TCP con il server
        // prendi il RMI del server usando il naming e l'ip giusto
        // controlla se è già stato preso e nel caso chiedilo di nuovo
        // aspetta che il server gli mandi le informazioni iniziali
        // deserializza la classe Player che ti è arrivata tramite il buffer della socket
        Socket socket = null; // questa dovrebbe essere la socket TCP che ho inizializzato poco prima
        Game gameRMIInstance = null; // questo dovrebbe essere l'oggetto remoto che sta sul server
        Player player = new Player(false, "pluto"); // questo oggetto dovrebbe venire dal server, questo è un esempio
        player.setSocket(socket); // setto la socket di questo player con quella che ho creato prima
        new Player(player, gameRMIInstance); // crea la classe Player effettiva con cui l'utente giocherà, tengo il riferimento al RMI del server
        // ovviamente, per adesso intellij da molti errore di NullPointer, spariranno quando implementeremo la network
    }

    public Player(boolean isChair, String namePlayer) {
        name = namePlayer;
        isChairMan = isChair;
        library = new Library();
        pointsUntilNow = 0;
        state = NOT_ACTIVE;
    }
    public Player(Player p, Game rmi){
        name = p.name;
        isChairMan = p.isChairMan;
        library = p.library;
        objective = p.objective;
        pointsUntilNow = p.pointsUntilNow;
        state = p.state;
        board = p.board;
        gameRMI = rmi;
        librariesOfOtherPlayers = new ArrayList<>();
        startGame();
    }
    public Player(Player p){ // copy constructor
        name = p.name;
        isChairMan = p.isChairMan;
        library = new Library(p.library);
        objective = p.objective;
        pointsUntilNow = p.pointsUntilNow;
        state = p.state;
        board = new Board(p.board);
        gameRMI = p.gameRMI;
        librariesOfOtherPlayers = new ArrayList<>(p.librariesOfOtherPlayers);
        mySocket = p.mySocket;
    }

    public String getName() {
        return name;
    }

    public void setPrivateObjective(PrivateObjective obj) {
        objective = obj;
    }

    private boolean pickCards(ArrayList<Integer> coord) { // Coordinate accoppiate. Questo metodo verrà chiamato quando la GUI o la CLI rilevano una scelta dall'utente
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < coord.size(); i += 2) {
            cards.add(board.getGameBoard()[coord.get(i)][coord.get(i + 1)]);
            board.getGameBoard()[coord.get(i)][coord.get(i + 1)] = new Card(coord.get(i), coord.get(i + 1)); // dopo che hai preso una carta, tale posto diventa EMPTY
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
    /** ------------------------------------------------------------------------------------------------------------- */
    public void startGame(){
        startRedrawThread();
        startGetRemoteBoardThread();
        // aspetta che il server ti faccia iniziare la partita, ovvero aspetta il tuo primo turno
        startTurn();
        return;
    }
    private void startTurn(){
        librariesOfOtherPlayers = gameRMI.getOtherLibraries(name);
        if(state == DISCONNECTED){
            endTurn();
            return;
        }
        setState(ACTIVE);
        // esegui le operazioni del tuo turno
        endTurn();
    }
    private void endTurn(){
        gameRMI.updatePlayers(this, name); // In realtà qui dentro stai anche già mandando la library. Pensa a possibile ridondanza
        // manda al server la notifica che hai finito il turno
        // sarà il server a metterti NOT_ACTIVE
    }
    public State getState() {
        return state;
    }
    public void setState(State newState) {
        state = newState;
    }
    public void setBoard(Board b){board.updateBoard(b);}
    public void setSocket(Socket s){mySocket = s;}
    public Library getLibrary(){return library;}
    private void startRedrawThread(){return;} // funzione che start il thread che andrà ad aggiornare la GUI ogni x millisecondi.
    private void startGetRemoteBoardThread(){return;}
    // Mentre non è il tuo turno (NOT_ACTIVE) devi chiedere al server ogni x ms la nuova versione per aggiornarla. Avremo un Thread dedicato a parte
}