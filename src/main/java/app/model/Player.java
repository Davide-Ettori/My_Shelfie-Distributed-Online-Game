package app.model;

import app.controller.*;
import app.model.chat.ReceiveChat;
import app.model.chat.SendChat;
import app.view.UIMode;
import org.json.simple.JSONObject;
import playground.socket.Server;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
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
    /** the UI mode chosen by the user */
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
    private transient Socket mySocket;
    private transient ObjectInputStream inStream;
    private transient ObjectOutputStream outStream;
    private transient Thread chatThread = null; // sintassi dei messaggi sulla chat --> @nome_destinatario contenuto_messaggio --> sintassi obbligatoria
    private String fullChat = "";
    private boolean endGame = false;
    private final transient BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private final String DAVIDE_HOTSPOT_IP = "172.20.10.3" ;
    private final String DAVIDE_POLIMI_IP = "10.168.91.35";
    private final String DAVIDE_XIAOMI_IP_F = "192.168.74.95";
    private final String DAVIDE_XIAOMI_IP_G = "192.168.58.95";
    private final String DAVIDE_IP_MILANO = "172.17.0.129";
    private final String DAVIDE_IP_MANTOVA = "192.168.1.21";
    private final String SAMUG_IP_MILANO = "192.168.1.3";
    private final String LOCAL_HOST = "127.0.0.1"; //è il computer stesso

    /**
     * standard constructor, starts the main game process on the client side
     * @param mode type of the network chosen by the user
     * @param ui type of ui chosen by the user
     * @author Ettori
     */
    public Player(NetMode mode, UIMode ui) { // Costruttore iniziale
        uiMode = ui;
        netMode = mode;
        System.out.println("\nSoon you will need to enter your nickname for the game");
        try {
            mySocket = new Socket(LOCAL_HOST, Server.PORT);
            outStream = new ObjectOutputStream(mySocket.getOutputStream());
            inStream = new ObjectInputStream(mySocket.getInputStream());
        }catch (Exception e){System.out.println("\nServer is either full or inactive, try later"); return;}
        System.out.println("\nClient connected");
        chooseUserName();
    }
    /**
     * constructor used by the server to initializer a base Player object
     * @author Ettori
     */
    public Player(){}
    /**
     * Clone the player on the client in the player on the server
     * @author Ettori
     * @param p the Player that will be cloned in the current Object
     */
    public void clone(Player p){ // copia la versione sul server dentro a quella del client
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
    }
    /**
     * method for choosing the nickname of the player for the future game
     * @author Ettori
     */
    private void chooseUserName(){
        while(true){
            Scanner in = new Scanner(System.in);
            System.out.print("\nInsert your name: ");
            name = in.nextLine();
            NameStatus status;
            try {
                outStream.writeObject(name);
                status = (NameStatus) inStream.readObject();
            }catch(Exception e){throw new RuntimeException(e);};

            if(status == NOT_TAKEN)
                break;
            System.out.println("Name Taken, choose another name");
        }
        try {
            outStream.writeObject(netMode);
            outStream.writeObject(uiMode);
        }catch(Exception e){throw new RuntimeException(e);}
        System.out.println("\nName: '" + name + "' accepted by the server!");
        getInitialState();
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
        }catch(Exception e){throw new RuntimeException(e);}
        if(name.equals(chairmanName)) {
            startChatReceiveThread();
            waitForMove();
        }
        else {
            startChatSendThread();
            waitForEvents();
        }
    }
    /**
     * helper function for handling the your turn event notification from the server
     * @author Ettori
     */
    private void handleYourTurnEvent(){
        startChatReceiveThread();
        activeName = name;
        drawAll();
        waitForMove();
    }
    /**
     * helper function for handling the change event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleChangeTurnEvent(Message msg){
        startChatSendThread();
        activeName = (String) msg.getContent();
        drawAll();
        waitForEvents();
    }
    /**
     * helper function for handling the unplayble board fixing event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleUpdateUnplayableEvent(Message msg){
        JSONObject jsonObject = (JSONObject) msg.getContent();
        board = new Board((Board) jsonObject.get("board"));
        drawAll();
        System.out.println("\nBoard updated because it was unplayable");
        waitForEvents();
    }
    /**
     * helper function for handling the update game notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleUpdateGameEvent(Message msg){
        stopChatThread();
        try {
            outStream.writeObject(new Message(STOP, null, null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = (JSONObject) msg.getContent();
        board = new Board((Board)jsonObject.get("board"));
        for(int i = 0; i < numPlayers; i++){
            if(librariesOfOtherPlayers.get(i).name.equals(msg.getAuthor()))
                librariesOfOtherPlayers.set(i, new Library((Library)jsonObject.get("library")));
        }
        drawAll();
        System.out.println("\nPlayer: " + msg.getAuthor() + " made his move, now wait for the turn to change (chat disabled)...");
        waitForEvents();
    }
    /**
     * helper function for handling the final score calculation event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleFinalScoreEvent(Message msg){
        System.out.println("\nThe game is finished, this is the final scoreboard\n" + msg.getContent());
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(0); // il gioco finisce e tutto si chiude forzatamente
    }
    /**
     * helper function for handling the chat message event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleChatEvent(Message msg){
        fullChat += msg.getContent();
        drawAll();
        waitForEvents();
    }
    /**
     * helper function for handling the achievement of the first common objective event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleCO_1Event(Message msg){
        System.out.println(msg.getAuthor() + " completed the first common objective getting " + msg.getContent() + " points");
        waitForEvents();
    }
    /**
     * helper function for handling the achievement of the second common objective event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleCO_2Event(Message msg){
        System.out.println(msg.getAuthor() + " completed the second common objective getting " + msg.getContent() + " points");
        waitForEvents();
    }
    /**
     * helper function for handling the completion of the library event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleLibFullEvent(Message msg){
        System.out.println(msg.getAuthor() + " completed the library, the game will continue until the next turn of " + chairmanName);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        endGame = true;
        waitForEvents();
    }
    /**
     * function  used to wait for notification from the server while the player is NON active
     * @author Ettori
     * @author Ettori Faccincani
     */
    private void waitForEvents(){ // funzione principale di attesa
        try {
            Message msg = (Message) inStream.readObject();
            switch (msg.getType()) {
                case YOUR_TURN -> handleYourTurnEvent();
                case CHANGE_TURN -> handleChangeTurnEvent(msg);
                case UPDATE_UNPLAYBLE -> handleUpdateUnplayableEvent(msg);
                case UPDATE_GAME -> handleUpdateGameEvent(msg);
                case FINAL_SCORE -> handleFinalScoreEvent(msg);
                case CHAT -> handleChatEvent(msg);
                case CO_1 -> handleCO_1Event(msg);
                case CO_2 -> handleCO_2Event(msg);
                case LIB_FULL -> handleLibFullEvent(msg);
            }
        }catch(Exception e){throw new RuntimeException(e);}
    }
    /**
     * helper method used for getting the cards chosen by the user (coordinates)
     * @author Ettori
     * @return the list og the cards chosen by the player
     */
    private ArrayList<Integer> selectCards(){
        String coordString;
        String[] rawCoords;
        ArrayList<Integer> coords = new ArrayList<>();
        while(true){
            System.out.print("\nInsert coordinates of the cards to pick (or @ for chat):\n");
            try {
                coordString = br.readLine();
            } catch (IOException e) {
                System.out.println("errore");
                throw new RuntimeException(e);
            }
            rawCoords = coordString.split(" ");

            if(coordString.charAt(0) == '@'){
                sendChatMsg(coordString);
                continue;
            }
            if(!checkRawCoords(rawCoords)){
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
        return coords;
    }

    /**
     * helper method for letting the user select the order of the cards chosen before
     * @author Ettori
     * @param coords the coordinates of the cards chosen
     * @return the list of the cards in the right order
     */
    private ArrayList<Integer> selectOrder(ArrayList<Integer> coords){
        String coordOrder;
        int temp_1, temp_2, index_1, index_2;
        while(true){
            printCurOrder(coords);
            if(coords.size() == 2)
                break;
            System.out.print("\nInsert which cards to switch (-1 for exit) (or @ for chat):\n");
            try {
                coordOrder = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
                continue;
            }
            index_1 *= 2;
            index_2 *= 2;
            temp_1 = coords.get(index_1);
            temp_2 = coords.get(index_1 + 1);
            coords.set(index_1, coords.get(index_2));
            coords.set(index_1 + 1, coords.get(index_2 + 1));
            coords.set(index_2, temp_1);
            coords.set(index_2 + 1, temp_2);
        }
        return coords;
    }
    /**
     * helper method useful for getting the column chosen by the user (where tha cards will be placed)
     * @author Ettori
     * @return the column chosen by the player
     */
    private int selectColumn(int size){
        int col;
        String column;
        while(true){
            System.out.print("\nInsert the column where you wish to put the cards (or @ for chat):\n");
            try {
                column = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if(column.charAt(0) == '@'){
                sendChatMsg(column);
                continue;
            }
            col = Integer.parseInt(column);
            if(library.checkCol(col, size))
                break;
            System.out.println("\nInvalid selection");
        }
        return col;
    }
    /**
     * method that checks if some of the common objectives where achieved by the current player, and in that case points will be added
     * @author Ettori
     * @return true iff at least one objective was completed
     */
    private boolean checkCO(){
        int points;
        boolean change = false;
        try {
            if (board.commonObjective_1.algorithm.checkMatch(library.library) && !CO_1_Done) { // non devi riprendere il CO se lo hai già fatto una volta
                points = board.pointsCO_1.pop();
                pointsUntilNow += points;
                CO_1_Done = true;
                outStream.writeObject(new Message(CO_1, name, Integer.toString(points)));
                System.out.println("\nWell done, you completed the first common objective and you gain " + points + " points (chat disabled)...");
                Thread.sleep(1000);
                change = true;
            }
            if (board.commonObjective_2.algorithm.checkMatch(library.library) && !CO_2_Done) {
                points = board.pointsCO_2.pop();
                pointsUntilNow += points;
                CO_2_Done = true;
                outStream.writeObject(new Message(CO_1, name, Integer.toString(points)));
                System.out.println("\nWell done, you completed the second common objective and you gain " + points + " points (chat disabled)...");
                Thread.sleep(1000);
                change = true;
            }
        }catch(Exception e){throw new RuntimeException(e);}
        return change;
    }
    /**
     * method that checks if the current player completed his library, and in that case notify all the other players (and add 1 point)
     * @author Ettori
     * @return true iff the library was completed
     */
    private boolean checkLibFull(){
        try {
            if (library.isFull() && !endGame) {
                endGame = true;
                pointsUntilNow++;
                outStream.writeObject(new Message(LIB_FULL, name, null));
                System.out.println("\nWell done, you are the first player to complete the library, the game will continue until the next turn of " + chairmanName + " (chat disabled)...");
                Thread.sleep(1000);
                return true;
            }
        }catch (Exception e){throw new RuntimeException(e);}
        return false;
    }

    /**
     * helper method which update the board when it becomes unplayable (also notify other players)
     * @author Ettori
     */
    private void fixUnplayableBoard(){
        JSONObject boardStatus;
        board.fillBoard(numPlayers);
        drawAll();
        System.out.println("\nBoard updated because it was unplayble");
        try {
            boardStatus = new JSONObject();
            boardStatus.put("board", board);
            outStream.writeObject(new Message(UPDATE_UNPLAYBLE, name, boardStatus));
        }catch (Exception e){throw new RuntimeException(e);}
    }

    /**
     * method that sends the last move done by the current player to all other clients (after the move is done on this player)
     * @author Ettori
     */
    private void sendDoneMove(){
        JSONObject gameStatus = new JSONObject(), playerStatus = new JSONObject();
        System.out.println("\nYou made your move, now wait for other players to acknowledge it (chat disabled)...");
        gameStatus.put("board", new Board(board));
        gameStatus.put("library", new Library(library));

        try {
            outStream.writeObject(new Message(UPDATE_GAME, name, gameStatus));
            state = NOT_ACTIVE;
            Thread.sleep(1000 * 5); // aspetto che tutti abbiano il tempo di capire cosa è successo nel turno
            new Thread(() -> { // aspetto un secondo e poi mando la notifica di fine turno
                try {
                    Thread.sleep(1000);
                    playerStatus.put("player", this);
                    outStream.writeObject(new Message(END_TURN, name, playerStatus));
                }catch (Exception e){throw new RuntimeException(e);}
            }).start();

        }catch(Exception e){throw new RuntimeException(e);}

        waitForEvents();
    }
    /**
     * method which waits for the current player's move, checks it and, then, send it to all other users (it also updates the library of this player)
     * @author Ettori Faccincani
     */
    private void waitForMove(){
        if(board.isBoardUnplayable())
            fixUnplayableBoard();

        ArrayList<Integer> coords = selectOrder(selectCards());
        int col = selectColumn(coords.size() / 2);
        pickCards(coords, col);

        drawAll();

        boolean change = checkCO();
        change = change || checkLibFull();
        if(change)
            drawAll();

        stopChatThread();
        sendDoneMove();
    }
    /**
     * Check if the input by the user is correct
     * @param s array of the coordinates
     * @return true if the input is correct
     * @author Faccincani
     */
    private boolean checkRawCoords(String[] s) {
        if (s.length % 2 == 1)
            return false;
        else {
            for (int i = 0; i < s.length; i++) {
                if (Integer.parseInt(s[i]) < 0 || Integer.parseInt(s[i]) > 9)
                    return false;
                }
            }
        return true;
    }
    /**
     * stops all the thread interaction related to the chat (should be only ReceiveChat)
     * @author Ettori
     */
    private void stopChatThread(){
        try {
            if(chatThread.getClass().getSimpleName().equals("SendChat"))
                chatThread.interrupt();
            else
                chatThread.interrupt();
        }catch (Exception e){System.out.println();}
    }
    /**
     * starts all the threads that listen for chat message from other clients (receiving)
      @author Ettori
     */
    private void startChatReceiveThread(){
        stopChatThread();
        chatThread = new ReceiveChat(this);
        chatThread.start();
    }

    /**
     * starts all the threads that listen for chat message from the user (sending)
      @author Ettori
     */
    private void startChatSendThread(){
        stopChatThread();
        chatThread = new SendChat(this, br);
        chatThread.start();
    }
    /**
     * Check if the index to switch are valid
     * @param index_1 first card
     * @param index_2 second card
     * @param size  number of switch
     * @return true if the index are valid
     * @author Ettori Faccincani
     */
    private boolean isCharValid(int index_1, int index_2, int size){
        return index_1 >= 0 && index_1 < size && index_2 > 0 && index_2 < size;
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
        System.out.println();
    }
    /**
     * Send with socket network the message of the chat to the right players
     * @param msg content of the message
     * @author Ettori
     */
    public void sendChatMsg(String msg){
        if(msg.charAt(0) != '@')
            return;
        if(!msg.contains(" ")){
            if(msg.substring(1).equals("names"))
                showAllNames();
            return;
        }
        String dest = msg.substring(1, msg.indexOf(' '));
        msg = msg.substring(msg.indexOf(' '));
        msg = name + " says:" + msg + " (to " + dest + ") at " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "\n";

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
        }catch(Exception e){throw new RuntimeException(e);}
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
     * Getter for the private objective
     * @author Ettori
     * @return the private objective of the player
     */
    public PrivateObjective getPrivateObjective(){return objective;}
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
     */
    private void deployCards(int col, ArrayList<Card> cards) {
        library.insertCards(col, cards);
    }
    /**
     * find the current state of the player (ACTIVE, NOT_ACTIVE, DISCONNECTED)
      @author Ettori
     * @return the state of the player (enum value)
     */
    public State getState(){return state;}
    /**
     * set the current state of the player
      @author Ettori
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
     * setter for the attribute name
      @author Ettori
     * @param n the name to set
     */
    public void setName(String n){name = n;}
    /**
     * setter for the attribute isChairMan
      @author Ettori
     * @param b the boolean to set
     */
    public void setIsChairMan(boolean b){isChairMan = b;}
    /**
     * getter for the socket input stream (from the server)
      @author Ettori
     * @return the input stream of this player
     */
    public ObjectInputStream getInStream(){return inStream;}
    /**
     * add a string (chat message) to the full chat of the game
      @author Ettori
     * @param s the message received, it will be added to the fullChat attribute
     */
    public void addToFullChat(String s){fullChat += s;}
}