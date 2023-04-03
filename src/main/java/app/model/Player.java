package app.model;

import app.controller.*;
import app.view.UIMode;
import playground.socket.Server;

import java.io.IOException;
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

/**
 * class which represent the player on the client side, mutable,
 * implements Serializable because it will be sent in the socket network
 * @author Ettori Faccincani
 */
public class Player implements Serializable{
    private String name;
    /** the name of the chairman of the game */
    public String chairmanName;
    /** the network mode chosen by the user */
    public NetMode netMode;
    /** teh UI mode chosen by the user */
    public UIMode uiMode;
    /** number of players in this current game */
    public int numPlayers;
    private boolean CO_1_Done = false;
    private boolean CO_2_Done = false;
    /** the name of the player currently having his turn */
    public String activeName = "";
    private boolean isChairMan;
    /** the personal library of this player */
    public Library library;
    private PrivateObjective objective;
    /** points achieved until now with the common objectives */
    public int pointsUntilNow;
    private State state;
    /** the board seen and used by this player */
    public Board board;
    /** list of the libraries of all the players in the game */
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
    private final String SAMUG_IP_MILANO = "192.168.1.3";

    /**
     * constructor used by the server to initializer a base Player object
     * @param n the name of the player
     * @param isChairManBool true iff the player is the chairman of the game
     */
    public Player(String n, boolean isChairManBool){name = n; isChairMan = isChairManBool;}
    /**
     * empty constructor, used ONLY by the server to create an empty player (and then clone it)
     */
    public Player(){}
    /**
     * standard constructor, starts the main game process on the client side
     * @param mode type of the network chosen by the user
     * @param ui type of ui chosen by the user
     * @author Ettori
     */
    public Player(NetMode mode, UIMode ui) { // Costruttore iniziale
        uiMode = ui;
        netMode = mode;
        try {
            mySocket = new Socket(DAVIDE_HOTSPOT_IP, Server.PORT);
            outStream = new ObjectOutputStream(mySocket.getOutputStream());
            inStream = new ObjectInputStream(mySocket.getInputStream());
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
        try {
            outStream.writeObject(netMode);
            outStream.writeObject(uiMode);
        }catch(Exception e){System.out.println(e);}
        System.out.println("\nName: '" + name + "' accepted by the server!");
        getInitialState();
    }

    /**
     * Clone the player on the client in the player on the server
     * @author Ettori
     * @param p the Player that will be cloned in the current Object
     * @return the 'this' object the was cloned before
     */
    public Player clone(Player p){ // copia la versione sul server dentro a quella del client
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
     * Receive the status of the player from the server and attend the start of the game
     * @author Ettori Faccincani
     */
    private void getInitialState(){
        Player p;
        try {
            System.out.println("\nBe patient, the game will start soon...");
            p = (Player) inStream.readObject();
            clone(p);
            drawAll();
        }catch(Exception e){System.out.println(e); System.exit(0);}
        if(name.equals(chairmanName)) {
            chatThread = new Thread(() -> {}); // placeholder thread, used only at the start
            startChatReceiveThread();
            waitForMove();
        }
        else {
            startChatSendThread();
            waitForTurn();
        }
    }

    /**
     * Attend the start of the turn, meanwhile can update the board if someone else changes it. it can also receive notifications of important events
     * @author Ettori Faccincani
     */
    private void waitForTurn(){ // funzione principale di attesa
        try {
            Message msg = (Message) inStream.readObject();
            switch (msg.getType()) {
                case YOUR_TURN -> {
                    startChatReceiveThread();
                    activeName = name;
                    drawAll();
                    waitForMove();
                }
                case CHANGE_TURN -> {
                    startChatSendThread();
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
            System.out.print("\nInsert coordinates of the cards to pick (or @ for chat): ");
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
            System.out.println("\nInvalid selection");
        }
        int index_1, index_2;
        while(true){
            printCurOrder(coords);
            System.out.print("\nInsert which cards to switch (-1 for exit) (or @ for chat): ");
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
            System.out.print("\nInsert the column where you wish to put the cards (or @ for chat): ");
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
        boolean change = false;
        try {
            if (board.commonObjective_1.algorithm.checkMatch(library.library) && !CO_1_Done) { // non devi riprendere il CO se lo hai già fatto una volta
                points = board.pointsCO_1.pop();
                pointsUntilNow += points;
                CO_1_Done = true;
                outStream.writeObject(new Message(CO_1, name, Integer.toString(points)));
                System.out.println("\nWell done, you completed the first common objective and you gain " + points + " points");
                Thread.sleep(1000);
                change = true;
            }
            if (board.commonObjective_2.algorithm.checkMatch(library.library) && !CO_2_Done) {
                points = board.pointsCO_2.pop();
                pointsUntilNow += points;
                CO_2_Done = true;
                outStream.writeObject(new Message(CO_1, name, Integer.toString(points)));
                System.out.println("\nWell done, you completed the second common objective and you gain " + points + " points");
                Thread.sleep(1000);
                change = true;
            }
        }catch(Exception e){System.out.println(e);}
        try {
            if (library.isFull() && !endGame) {
                endGame = true;
                pointsUntilNow++;
                outStream.writeObject(new Message(LIB_FULL, name, null));
                System.out.println("\nWell done, you are the first player to complete the library, the game will continue until the next turn of " + chairmanName);
                Thread.sleep(1000);
                change = true;
            }
        }catch (Exception e){System.out.println(e);}
        if(change)
            drawAll();
        System.out.println("\nYou made your move, now wait for other players to acknowledge it... (chat disabled)");
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
        waitForTurn();
    }
    private void startChatReceiveThread(){
        flushInputBuffer();
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
    }
    private void startChatSendThread(){
        flushInputBuffer();
        try {
            chatThread.interrupt();
        }catch (Exception e){}
        chatThread = new Thread(() ->{ // inizio il thread che ascolta i comandi da terminale
            Scanner in = new Scanner(System.in);
            String s;
            while(true) {
                s = in.nextLine().trim();
                sendChatMsg(s);
            }
        });
        chatThread.start();
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
     * Send with socket network the message of the chat to the right players
     * @param msg content of the message
     * @author Ettori
     */
    private void sendChatMsg(String msg){
        if(msg.charAt(0) != '@')
            return;
        if(!msg.contains(" ")){
            if(msg.substring(1).equals("names"))
                showAllNames();
            return;
        }
        String dest = msg.substring(1, msg.indexOf(' '));
        msg = msg.substring(msg.indexOf(' '));
        msg = "\n" + name + " says:" + msg;

        if(!doesPlayerExists(dest) && !dest.equals("all")) {
            System.out.println("\nThe name chosen does not exists");
            return;
        }
        if(dest.equals(name)){
            System.out.println("\nYou can't send chat messages to yourself");
            return;
        }
        fullChat += msg;
        try{
            outStream.writeObject(new Message(CHAT, dest, msg));
        }catch(Exception e){System.out.println(e);}
    }
    /**
     * prints the name of all the players in the terminal, so that the user can choose the receiver of the chat messages
     * @author Ettori
     */
    private void showAllNames(){
        System.out.print("\nThe active players of this game are: ");
        for(Library lib : librariesOfOtherPlayers) {
            if(lib.name.equals(name))
                continue;
            System.out.print(lib.name + " ");
        }
        System.out.println("and you");
    }
    /**
     * check if the name of a player exists in the game (used by the chat)
     * @author Ettori
     * @param name the name to check in this game
     * @return true iff that player actually exists in the current game
     */
    private boolean doesPlayerExists(String name){
        for(Library lib : librariesOfOtherPlayers){
            if(lib.name.equals(name))
                return true;
        }
        return false;
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
     * setter for the PO
     * @author Ettori
     * @param obj  the PO that needs to be set
     */
    public void setPrivateObjective(PrivateObjective obj) {objective = obj;}
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
            board.getGameBoard()[coord.get(i)][coord.get(i + 1)] = new Card(); // dopo che hai preso una carta, tale posto diventa EMPTY
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
     * find the current state of the player (ACTIVE, NOT_ACTIVE, DISCONNECTED)
     * @return the state of the player (enum value)
     */
    public State getState(){return state;}
    /**
     * set the current state of the player
     * @param s the state that must be set
     */
    public void setState(State s){state = s;}
    /**
     * print the name of the active player, the 2 CO, the PO, the board, the libraries,
     * and then prints spaces before the next execution of drawAll. It also prints general (useful) information
     * @author Gumus
     */
    public void drawAll(){
        // System.out.flush(); non funziona sul terminale di intellij
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

    /**
     * prints 12 spaces (rows) to simulate the flush of the terminal
     * @author Gumus
     */
    private void clearScreen(){
        for(int i = 0; i < 12; i++){
            System.out.println();
        }
    }

    /**
     * flush the input buffer of the terminal (System.in)
     * @author Ettori
     */
    private void flushInputBuffer(){
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}