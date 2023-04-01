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
 * class which represent the player on the client side, mutable,
 * implements Serializable because it will be sent in the socket network
 * @author Ettori Faccincani
 */
public class PlayerTUI implements Serializable{
    private String name;
    public String chairmanName;
    public NetMode netMode;
    public int numPlayers;
    private boolean CO_1_Done = false;
    private boolean CO_2_Done = false;
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
    private Thread chatThread = null; // sintassi dei messaggi sulla chat --> @nome_destinatario contenuto_messaggio --> sintassi obbligatoria
    private String fullChat = "";
    private boolean endGame = false;
    private ObjectInputStream inStream;
    private final String DAVIDE_HOTSPOT_IP = "172.20.10.3" ;
    private final String DAVIDE_POLIMI_IP = "10.168.91.35";
    private final String DAVIDE_XIAOMI_IP_F = "192.168.74.95";
    private final String DAVIDE_XIAOMI_IP_G = "192.168.86.95";
    private final String DAVIDE_IP_MILANO = "172.17.0.129";
    private final String DAVIDE_IP_MANTOVA = "192.168.1.21";

    public PlayerTUI(String n, boolean isChairManBool){name = n; isChairMan = isChairManBool;}
    public PlayerTUI(){
        // costruttore vuoto
    }

    /**
     * Clone the player on the client in the player on the server
     * @author Ettori
     */
    public PlayerTUI clone(PlayerTUI p){ // copia la versione sul server dentro a quella del client
        name = p.name;
        isChairMan = p.isChairMan;
        library = new Library(p.library);
        objective = p.objective;
        pointsUntilNow = p.pointsUntilNow;
        state = p.state;
        board = new Board(p.board);
        librariesOfOtherPlayers = new ArrayList<>(p.librariesOfOtherPlayers);
        mySocket = p.mySocket;
        CO_1_Done = p.CO_1_Done;
        CO_2_Done = p.CO_2_Done;
        fullChat = p.fullChat;
        chairmanName = p.chairmanName;
        activeName = p.activeName;
        numPlayers = p.numPlayers;
        endGame = p.endGame;
        return this;
    }
    public PrivateObjective getPrivateObjective(){
        return objective;
    }

    /**
     * start the main game process on the client side
     * @param mode type of the network chosen by the user
     * @author Ettori
     */
    public PlayerTUI(NetMode mode) { // Costruttore iniziale
        netMode = mode;
        if(netMode == RMI)
            return;
        try {
            Socket socket = new Socket(DAVIDE_IP_MANTOVA, Server.PORT);
            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
            String resp = (String) inStream.readObject();
            if(!resp.equals("CLI")){
                System.out.println("\nClient unable to connect, wrong UI choice");
                outStream.writeObject("FAIL");
                System.exit(0);
            }
            outStream.writeObject("SUCCESS");
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

    /**
     * Receive the status of the player from the server and attend the start of the game
     * @author Ettori Faccincani
     */
    private void getInitialState(){
        PlayerTUI p;
        try {
            System.out.println("\nBe patient, the game will start soon...");
            p = (PlayerTUI) inStream.readObject();
            clone(p);
            drawAll();
        }catch(Exception e){System.out.println(e); System.exit(0);}
        chatThread = new Thread(() ->{
            Scanner in = new Scanner(System.in);
            while(true)
                sendChatMsg(in.nextLine());
        });
        chatThread.start();
        if(name.equals(chairmanName))
            waitForMove();
        else
            waitForTurn();
    }

    /**
     * Attend the start of the turn, meanwhile can update the board if someone else changes it
     * @author Ettori Faccincani
     */
    private void waitForTurn(){ // funzione principale di attesa
        try {
            Message msg = (Message) inStream.readObject();
            switch (msg.getType()) {
                case YOUR_TURN -> {
                    activeName = name;
                    drawAll();
                    waitForMove();
                }
                case CHANGE_TURN -> {
                    activeName = msg.getAuthor();
                    drawAll();
                    waitForTurn();
                }
                case UPDATE_BOARD -> {
                    board = new Board((Board) msg.getContent());
                    drawAll();
                    waitForTurn();
                }
                case UPDATE_GAME -> {
                    HashMap<String, Object> map = (HashMap<String, Object>) msg.getContent();
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
                    System.out.println("\nThe game is finished, this is the final scoreboard\n" + msg.getContent());
                    Thread.sleep(1000 * 5);
                    System.exit(0); // il gioco finisce e tutto si chiude forzatamente
                }
                case CHAT -> {
                    fullChat += msg.getContent() + "\n";
                    drawAll();
                    waitForTurn();
                }
                case CO_1 ->{
                    System.out.println(msg.getAuthor() + " completed the first common objective getting " + msg.getContent() + " points");
                    waitForTurn();
                }
                case CO_2 ->{
                    System.out.println(msg.getAuthor() + " completed the second common objective getting " + msg.getContent() + " points");
                    waitForTurn();
                }
                case LIB_FULL ->{
                    System.out.println(msg.getAuthor() + " completed the library, the game will continue until the next turn of " + chairmanName);
                    endGame = true;
                    waitForTurn();
                }
            }
        }catch(Exception e){System.out.println(e);}
    }

    /**
     * Attend the move of the player and start the chat, after the move check if the move is correct
     * @author Ettori Faccincani
     */
    private void waitForMove(){
        chatThread.interrupt();
        chatThread = new Thread(() ->{
            try{
                while(true){
                    Message msg = (Message) inStream.readObject();
                    if(msg.getType() == CHAT) {
                        System.out.println(msg.getContent());
                        fullChat += msg.getContent() + "\n";
                    }
                    else
                        System.out.println("\nHere i am only expecting chat message, no other types");
                }
            }catch(Exception e){System.out.println(e);}
        });
        chatThread.start();
        // ora mi aspetto le mosse, voglio quindi attivare i thread che ascoltano le chat degli altri
        if(board.isBoardUnplayable()){
            board.fillBoard(numPlayers);
            try {
                outStream.writeObject(new Message(UPDATE_BOARD, name, board));
            }catch (Exception e){System.out.println(e);}
        }
        String coordString, coordOrder, column;
        String[] rawCoords;
        ArrayList<Integer> coords = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int temp; // helper per fare gli scambi
        while(true){
            System.out.print("\nInsert coordinates of the cards to pick: ");
            coordString = in.nextLine();
            rawCoords = coordString.split(" ");
            if(coordString.charAt(0) == '@'){
                sendChatMsg(coordString);
                continue;
            }
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
            if(coordOrder.charAt(0) == '@'){
                sendChatMsg(coordOrder);
                continue;
            }
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
            column = in.nextLine();
            if(column.charAt(0) == '@'){
                sendChatMsg(column);
                continue;
            }
            col = Integer.parseInt(column);
            if(library.checkCol(col, coords.size() / 2))
                break;
            System.out.println("\nInvalid selection");
        }
        pickCards(coords, col);
        drawAll();
        int points;
        try {
            if (board.commonObjective_1.algorithm.checkMatch(library.library) && !CO_1_Done) { // non devi riprendere il CO se lo hai già fatto una volta
                points = board.pointsCO_1.pop();
                pointsUntilNow += points;
                CO_1_Done = true;
                outStream.writeObject(new Message(CO_1, name, Integer.toString(points)));
                System.out.println("\nWell done, you completed the first common objective and you gain " + points + " points");
                Thread.sleep(1000);
            }
            if (board.commonObjective_2.algorithm.checkMatch(library.library) && !CO_2_Done) {
                points = board.pointsCO_2.pop();
                pointsUntilNow += points;
                CO_2_Done = true;
                outStream.writeObject(new Message(CO_1, name, Integer.toString(points)));
                System.out.println("\nWell done, you completed the second common objective and you gain " + points + " points");
                Thread.sleep(1000);
            }
        }catch(Exception e){System.out.println(e);}
        try {
            if (library.isFull() && !endGame) {
                endGame = true;
                pointsUntilNow++;
                outStream.writeObject(new Message(LIB_FULL, name, null));
                System.out.println("\nWell done, you are the first player to complete the library, the game will continue until the next turn of " + chairmanName);
                Thread.sleep(1000);
            }
        }catch (Exception e){System.out.println(e);}
        System.out.println("\nYou made your move, now wait for other players to acknowledge it...");
        HashMap<String, Object> map = new HashMap<>();
        map.put("board", board);
        map.put("library", library);
        try {
            outStream.writeObject(new Message(UPDATE_GAME, name, map));
            int timer = 5;
            Thread.sleep(1000 * timer); // aspetto che tutti abbiano il tempo di capire cosa è successo nel turno
            state = NOT_ACTIVE;
            System.out.println("Your turn is over...");
            Thread.sleep(1000);
            outStream.writeObject(new Message(END_TURN, name, this));

        }catch(Exception e){System.out.println(e);}
        chatThread.interrupt();
        chatThread = new Thread(() ->{ // inizio il thread che ascolta i comandi da terminale
            while(true)
                sendChatMsg(in.nextLine());
        });
        chatThread.start();
        waitForTurn(); // mi metto in attesa che diventi il mio turno
    }

    /**
     * Check if the index of the columns to switch are valid
     * @param index_1 column to switch
     * @param index_2 column to switch
     * @param size  number of columns asked to switch
     * @return true if the index are valid
     * @author Ettori Faccincani
     */
    private boolean isCharValid(int index_1, int index_2, int size){
        return index_1 > 0 && index_1 <= size && index_2 > 0 && index_2 < size;
    }

    /**
     * Return to the player the current order of the cards to be placed
     * @param arr list of the cards
     * @author Ettori Faccincani
     */
    private void printCurOrder(ArrayList<Integer> arr){
        System.out.println("\nThe current order of your cards is: ");
        for(int i = 0; i < arr.size(); i += 2) {
            board.getGameBoard()[arr.get(i)][arr.get(i + 1)].draw();
            System.out.print(" ");
        }
    }

    /**
     * Send with socket the message of the chat
     * @param msg content of the message
     * @author Ettori
     */
    private void sendChatMsg(String msg){
        if(msg.charAt(0) != '@')
            return;
        String dest = msg.substring(1, msg.indexOf(' '));
        msg = msg.substring(msg.indexOf(' '));
        msg = name + " says:" + msg;
        try{
            outStream.writeObject(new Message(CHAT, dest, msg));
        }catch(Exception e){System.out.println(e);}
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
    private void deployCards(int col, ArrayList<Card> cards) {
        library.insertCards(col, cards);
    }
    /**
     * print the personal library and then print the other players libraries
     * @author Giammusso
     */
    private void printLibrary(){
        library.draw("My Library");
        for(int i = 0; i < librariesOfOtherPlayers.size(); i++){
            if(!librariesOfOtherPlayers.get(i).name.equals(name)){ //if it is not my personal library
                librariesOfOtherPlayers.get(i).draw("Library of "+librariesOfOtherPlayers.get(i).name);
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
        clearScreen();
        if(activeName.equals(name)){
            System.out.println("Wake up! It's your turn!");
        }else{
            System.out.println("Now " + activeName + " is playing...");
        }
        board.draw();
        objective.draw();
        library.draw();
        for(int i = 0; i < numPlayers; i++){
            if(!librariesOfOtherPlayers.get(i).name.equals(name))
                librariesOfOtherPlayers.get(i).draw("\nLibrary of " + librariesOfOtherPlayers.get(i).name);
        }
        System.out.println("\nYou have achieved " + pointsUntilNow + " points from the common objectives, until now");
        if(isChairMan)
            System.out.println("\nYou are the Chairman on this game!");
        else
            System.out.println("\nThe chairman of this game is: " + chairmanName);
        System.out.println("\nGame Chat: \n" + fullChat);
    }
    private void clearScreen(){
        for(int i = 0;i < 12; i++){
            System.out.println();
        }
    }
}