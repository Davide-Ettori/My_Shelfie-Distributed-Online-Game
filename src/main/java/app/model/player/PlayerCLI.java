package app.model.player;

import app.controller.*;
import app.model.*;
import playground.socket.Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Scanner;

import static app.controller.MessageType.*;
import static app.controller.NameStatus.*;
import static app.model.State.*;
import static app.model.player.NetMode.*;

/**
 * class which represent the player on the client side
 * @author Ettori Faccincani
 * mutable
 * implements Serializable because it will be sent in the socket network
 */
public class PlayerCLI implements Serializable{
    private String name;
    public NetMode netMode;
    public int numPlayers;
    private String activeName = "";
    private boolean isChairMan;
    public Library library;
    private PrivateObjective objective;
    public int pointsUntilNow;
    private State state;
    public Board board;
    public ArrayList<Library> librariesOfOtherPlayers = new ArrayList<>();
    private Socket mySocket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private final String DAVIDE_HOTSPOT_IP = "172.20.10.3" ;
    private final String DAVIDE_POLIMI_IP = "10.168.91.35";
    private final String DAVIDE_XIAOMI_IP_F = "192.168.74.95";
    private final String DAVIDE_XIAOMI_IP_G = "192.168.86.95";

    public PlayerCLI(String n, boolean isChairManBool){name = n; isChairMan = isChairManBool;}
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
    public PrivateObjective getPrivateObjective(){
        return objective;
    }
    public PlayerCLI(NetMode mode) { // Costruttore iniziale
        netMode = mode;
        if(netMode == RMI)
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
        getInitialState();
    }
    private void getInitialState(){
        PlayerCLI p;
        try {
            p = (PlayerCLI) inStream.readObject();
            clone(p);
        }catch(Exception e){System.out.println(e); System.exit(0);}
        waitForTurn();
    }
    private void waitForTurn(){ // qui riceve 3 possibili messaggi, funzione principale di attesa
        try {
            Message msg = (Message) inStream.readObject();
            switch (msg.getType()) {
                case YOUR_TURN -> {
                    activeName = name;
                    waitForMove();
                }
                case CHANGE_TURN -> {
                    activeName = msg.getAuthor();
                    waitForTurn();
                }
                case UPDATE_GAME -> {
                    HashMap<String, Object> map = new HashMap<>();
                    map = (HashMap<String, Object>) msg.getContent();
                    board = new Board((Board) map.get("board"));
                    for (int i = 0; i < librariesOfOtherPlayers.size(); i++) {
                        if (librariesOfOtherPlayers.get(i).name.equals(msg.getAuthor()))
                            librariesOfOtherPlayers.set(i, new Library((Library) map.get("library")));
                    }
                    drawAll();
                    System.out.println("\nPlayer: " + msg.getAuthor() + " made his move");
                    waitForTurn();
                }
                case FINAL_SCORE -> {
                    System.out.println("\nThe game is finished, this is the final scoreboard" + (String) msg.getContent());
                    Thread.sleep(1000 * 5);
                    System.exit(0);
                }
            }
        }catch(Exception e){System.out.println(e);}
    }
    private void waitForMove(){
        // controlla se la board è unplayble
        // nel caso la refilla e poi la manda al server così che la rimandi in broadcast
        String coordString, coordOrder;
        String[] rawCoords;
        ArrayList<Integer> coords = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int temp; // helper per fare gli scambi
        while(true){
            System.out.print("\nInsert coords of the cards to pick: ");
            coordString = in.nextLine();
            rawCoords = coordString.split(" ");
            if(rawCoords.length % 2 == 1){
                System.out.println("\nInvalid selection");
                continue;
            }
            for(int i = 0; i < rawCoords.length; i += 2){
                coords.add(Integer.parseInt(rawCoords[i]));
                coords.add(Integer.parseInt(rawCoords[i + 1]));
            }
            if(board.areCardsPickable(coords))
                break;
        }
        int index_1, index_2;
        while(true){
            printCurOrder(coords);
            System.out.print("\nInsert which cards to switch (-1 for exit): ");
            coordOrder = in.nextLine();
            if(coordOrder.equals("-1"))
                break;
            index_1 = Character.getNumericValue(coordOrder.charAt(0));
            index_2 = Character.getNumericValue(coordOrder.charAt(2));
            if(coordOrder.length() != 3 || !isCharValid(index_1, index_2, coords.size() / 2)){
                System.out.println("\nInvalid selection");
            }
            temp = coords.get(index_1);
            coords.set(index_1, index_2);
            coords.set(index_2, temp);
        }
        int col;
        while(true){
            System.out.print("\nInsert the column where you wish to put the cards: ");
            col = Integer.parseInt(in.nextLine());
            if(library.checkCol(col, coords.size() / 2))
                break;
            System.out.println("\nInvalid selection");
        }
        pickCards(coords, col);
        if(board.commonObjective_1.algorithm.checkMatch(library.library))
            pointsUntilNow += board.pointsCO_1.pop();
        if(board.commonObjective_2.algorithm.checkMatch(library.library))
            pointsUntilNow += board.pointsCO_2.pop();

        // controlla se la library è piena --> lo fai sul server, viene più comodo
        HashMap<String, Object> map = new HashMap<>();
        map.put("board", board);
        map.put("library", library);
        try {
            outStream.writeObject(new Message(UPDATE_GAME, name, map));
            int timer = 5;
            Thread.sleep(1000 * timer); // aspetto che tutti abbiano il tempo di capire cosa è successo nel turno
            state = NOT_ACTIVE;
            outStream.writeObject(new Message(END_TURN, name, this)); // notifico la fine turno
        }catch(Exception e){System.out.println(e);}
        waitForTurn(); // mi metto in attesa che diventi il mio turno
    }
    private boolean isCharValid(int index_1, int index_2, int size){
        return index_1 > 0 && index_1 <= size && index_2 > 0 && index_2 < size;
    }
    private void printCurOrder(ArrayList<Integer> arr){
        System.out.print(arr.get(0) + ", " + arr.get(1));
        for(int i = 2; i < arr.size(); i += 2)
            System.out.println(" - " + arr.get(i) + ", " + arr.get(i + 1));
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
    private void pickCards(ArrayList<Integer> coord, int col) { // Coordinate accoppiate. Questo metodo verrà chiamato quando la GUI o la CLI rilevano una scelta dall'utente
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < coord.size(); i += 2) {
            cards.add(new Card(board.getGameBoard()[coord.get(i)][coord.get(i + 1)]));
            board.getGameBoard()[coord.get(i)][coord.get(i + 1)] = new Card(coord.get(i), coord.get(i + 1)); // dopo che hai preso una carta, tale posto diventa EMPTY
        }

        deployCards(col, cards);
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
     * print the personal library and then print the other players libraries
     * @author Giammusso
     */
    private void printLibrary(){
        library.draw("My Library");
        for(int i=0; i<librariesOfOtherPlayers.size(); i++){
            if(!librariesOfOtherPlayers.get(i).name.equals(name)){ //if it is not my personal library
                librariesOfOtherPlayers.get(i).draw("Library of"+librariesOfOtherPlayers.get(i).name);
            }
        }
    }
    public State getState(){return state;}
    public void setState(State s){state = s;}
    public void setActiveName(String n){activeName = n;}
    /**
     * print the name of the active player, the 2 CO, the PO, the board, the libraries,
     * and then prints spaces before the next execution of drawAll
     * @author Gumus
     */
    public void drawAll(){
        /*System.out.print("\033[H\033[2J"); //\033[H porta il cursore all'inizio, \033[2J cancella tutto quello che c'è dopo il cursore; ma non funziona
        System.out.flush();*/
        if(activeName.equals(name)){
            System.out.println("Wake up! It's your turn!");
        }else{
            System.out.println("Now "+activeName+"is playing...");
        }
        board.commonObjective_1.draw();
        board.commonObjective_1.draw();
        objective.draw();
        board.draw();
        printLibrary();
        for(int i=0;i<12;i++){
            System.out.println();
        }
    }
}