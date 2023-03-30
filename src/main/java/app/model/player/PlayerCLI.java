package app.model.player;

import app.controller.*;
import app.model.*;
import playground.socket.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import java.util.Scanner;

import static app.controller.NameStatus.*;
import static app.model.State.*;
/**
 * class which represent the player on the client side
 * @author Ettori Faccincani
 * mutable
 * implements Serializable because it will be sent in the socket network
 */
public class PlayerCLI implements Serializable{
    private String name;
    private boolean isChairMan;
    public Library library;
    private PrivateObjective objective;
    public int pointsUntilNow;
    private State state;
    public Board board;
    private ArrayList<Library> librariesOfOtherPlayers;
    private Socket mySocket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private final String DAVIDE_HOTSPOT_IP = "172.20.10.3" ;
    private final String DAVIDE_POLIMI_IP = "10.168.91.35";
    private final String DAVIDE_XIAOMI_IP_F = "192.168.74.95";
    private final String DAVIDE_XIAOMI_IP_G = "192.168.86.95";

    public PlayerCLI(String netMode) { // Costruttore iniziale
        if(netMode.equals("r"))
            return;
        try {
            Socket socket = new Socket(DAVIDE_XIAOMI_IP_G, Server.PORT);
            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
            String resp = (String) inStream.readObject();
            if(!resp.equals("CLI")){
                System.out.println("\nClient unable to connect, wrong UI choice");
                System.exit(0);
            }
        }catch (Exception e){System.out.println("\nServer is full, try later"); return;}
        System.out.println("\nClient connected");
        while(true){
            Scanner in = new Scanner(System.in);
            System.out.print("\nInsert your name: ");
            name = in.nextLine();
            NameStatus status = TAKEN;
            try {
                outStream.writeObject(name);
                status = (NameStatus) inStream.readObject();
            }catch(Exception e){System.out.println(e.toString());};

            if(status == NOT_TAKEN)
                break;
            System.out.println("Name Taken, choose another name");
        }
        System.out.println("\nName: '" + name + "' accepted by the server!");
        while(true){}
    }
    public PlayerCLI(boolean isChair, String namePlayer) { // Costruttore semplice. Ancora non so se servirà per davvero
        name = namePlayer;
        isChairMan = isChair;
        library = new Library();
        pointsUntilNow = 0;
        state = NOT_ACTIVE;
    }
    public PlayerCLI(PlayerCLI p){ // copy constructor
        name = p.name;
        isChairMan = p.isChairMan;
        library = new Library(p.library);
        objective = p.objective;
        pointsUntilNow = p.pointsUntilNow;
        state = p.state;
        board = new Board(p.board);
        librariesOfOtherPlayers = new ArrayList<>(p.librariesOfOtherPlayers);
        mySocket = p.mySocket;
        inStream = p.inStream;
        outStream = p.outStream;
    }
    /**
     * getter for the name
     * @author Ettori
     * @return the name of the player
     */
    public String getName() {
        return name;
    }
    /**
     * setter for the board
     * @author Ettori
     * @param b the board which will be set
     */
    public void setBoard(Board b){board = new Board(b);}
    /**
     * getter for the board
     * @author Ettori
     * @return the current board of this player
     */
    public Board getBoard(Board b){return board;}
    /**
     * setter for the PO
     * @author Ettori
     * @param obj  the PO that needs to be set
     */
    public void setPrivateObjective(PrivateObjective obj) {
        objective = obj;
    }
    /**
     * take the cards from the board and transfer them in the player library
     * @author Ettori
     * @param coord the list of coupled coordinates of the cards that the player want to take from the board
     * @return true iff the transfer of the cards was successful (the cards are in the correct position)
     */
    private boolean pickCards(ArrayList<Integer> coord) { // Coordinate accoppiate. Questo metodo verrà chiamato quando la GUI o la CLI rilevano una scelta dall'utente
        if(!board.areCardsPickable(coord))
            return false; // hai scelto delle carte che non possono essere prese
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < coord.size(); i += 2) {
            cards.add(new Card(board.getGameBoard()[coord.get(i)][coord.get(i + 1)]));
            board.getGameBoard()[coord.get(i)][coord.get(i + 1)] = new Card(coord.get(i), coord.get(i + 1)); // dopo che hai preso una carta, tale posto diventa EMPTY
        }

        deployCards(chooseCol(cards.size()), chooseCardsOrder(cards));
        return true;
    }
    /**
     * physically position the cards in the chosen column
     * @author Ettori
     * @param col column
     * @param cards list of the chosen cards
     * @return true iff is successful
     */
    private boolean deployCards(int col, ArrayList<Card> cards) {
        library.insertCards(col, cards);
        return true;
    }
    /**
     * allow the player to choose in which column he/she want to put the cards
     * @author Ettori
     * @param numCards number of cards
     * @return the chosen column (check also if the column is valid)
     */
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
    /**
     * helper function to trade the order of cards in the list chosen by the player
     * @author Ettori
     * @param cards list of cards
     * @param i index number 1
     * @param j index number 2
     */
    private void swapCards(ArrayList<Card> cards, int i, int j) {
        Card temp = cards.get(i);
        cards.set(i, cards.get(j));
        cards.set(j, temp);
        return;
    }
    /**
     * allow the player to choose in which order place the chosen cards inside the library
     * @author Ettori
     * @param cards list of cards
     * @return list of cards in the order chosen by the player
     */
    private ArrayList<Card> chooseCardsOrder(ArrayList<Card> cards) {
        while (true) { // questo va fino a che l'utente sceglie l'ordine delle carte, per ora lo forziamo a mano
            swapCards(cards, 0, 1); // scambio 2 carte a caso
            break;
        }
        return cards;
    }
    /** ------------------------------------------------------------------------------------------------------------- */
    public void clone(PlayerCLI p){ // copia la versione sul server dentro a quella del client
        name = p.name;
        isChairMan = p.isChairMan;
        library = new Library(p.library);
        objective = p.objective;
        pointsUntilNow = p.pointsUntilNow;
        state = p.state;
        board = new Board(p.board);
        librariesOfOtherPlayers = new ArrayList<>(p.librariesOfOtherPlayers);
        mySocket = p.mySocket;
    }
    public void startGame(){
        startRedrawThread();
        startUpdatePlayerFromRemoteThread();
        // aspetta che il server ti faccia iniziare la partita, ovvero aspetta il tuo primo turno
        startTurn();
        return;
    }
    private void startTurn(){
        if(board.isBoardUnplayable()) {
            board.fillBoard(librariesOfOtherPlayers.size() + 1); // così il player sa quanti giocatori ci sono e puo' refillare la board
            // poi devi anche mandare la nuova board refillata al server
        }
        if(state == DISCONNECTED){
            endTurn();
            return;
        }
        setState(ACTIVE);
        // esegui le operazioni del tuo turno
        endTurn();
    }
    private void endTurn(){
        state = NOT_ACTIVE;
        //gameRMI.updatePlayersBoardAfterEndTurn(this, name); // In realtà qui dentro stai anche già mandando la library. Pensa a possibile ridondanza
        // manda al server la notifica che hai finito il turno
    }
    public State getState() {
        return state;
    }
    public void setState(State newState) {
        state = newState;
    }
    public void setSocket(Socket s){mySocket = s;}
    public void setObjective(PrivateObjective obj){objective = obj;}
    private void startRedrawThread(){return;} // funzione che start il thread che andrà ad aggiornare la GUI ogni x millisecondi.
    private void startUpdatePlayerFromRemoteThread(){return;}
    // Mentre non è il tuo turno (NOT_ACTIVE) devi chiedere al server ogni x ms la nuova versione per aggiornarla. Avremo un Thread dedicato a parte
}