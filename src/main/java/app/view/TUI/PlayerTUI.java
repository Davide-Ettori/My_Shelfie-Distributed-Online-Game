package app.view.TUI;

import app.view.IP;
import app.controller.*;
import app.model.*;
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

import static app.controller.MessageType.*;
import static app.controller.NameStatus.*;
import static app.model.State.*;

/**
 * class which represent the player on the client side, mutable,
 * implements Serializable because it will be sent in the socket network
 * @author Ettori Faccincani
 */
public class PlayerTUI extends Player implements Serializable{
    private transient Thread chatThread = null; // sintassi dei messaggi sulla chat --> @nome_destinatario contenuto_messaggio --> sintassi obbligatoria
    private final transient BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * constructor that copies a generic Player object inside a new PlayerTUI object
     * @param p the Player object to copy, received by the server
     */
    public PlayerTUI(Player p){
        netMode = p.netMode;
        uiMode = p.uiMode;
        name = p.getName();
        isChairMan = p.getIsChairMan();
        library = new Library(p.library);
        objective = p.getPrivateObjective();
        pointsUntilNow = p.pointsUntilNow;
        state = p.getState();
        board = new Board(p.board);
        librariesOfOtherPlayers = new ArrayList<>(p.librariesOfOtherPlayers);
        CO_1_Done = p.getCO_1_Done();
        CO_2_Done = p.getCO_2_Done();
        fullChat = p.getFullChat();
        chairmanName = p.chairmanName;
        activeName = p.activeName;
        numPlayers = p.numPlayers;
        endGame = p.getEndGame();
    }
    /**
     * standard constructor, starts the main game process on the client side
     * @param mode type of the network chosen by the user
     * @param ui type of ui chosen by the user
     * @author Ettori
     */
    public PlayerTUI(NetMode mode, UIMode ui) { // Costruttore iniziale
        uiMode = ui;
        netMode = mode;
        System.out.println("\nSoon you will need to enter your nickname for the game");
        try {
            mySocket = new Socket(IP.activeIP, Server.PORT);
            outStream = new ObjectOutputStream(mySocket.getOutputStream());
            inStream = new ObjectInputStream(mySocket.getInputStream());
            outStream.writeObject(false);
        }catch (Exception e){System.out.println("\nServer is either full or inactive, try later"); return;}
        System.out.println("\nClient connected");
        chooseUserName();
    }

    /**
     * Clone the player on the client in the player on the server
     * @author Ettori
     * @param p the Player that will be cloned in the current Object
     */
    @Override
    public void clone(PlayerTUI p){ // copia la versione sul server dentro a quella del client
        netMode = p.netMode;
        uiMode = p.uiMode;
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
        NameStatus status = null;
        while(true){
            System.out.print("\nInsert your name: ");
            try {
                name = br.readLine();
            } catch (IOException e) {
                System.out.println("errore");
                connectionLost(e);
            }
            if(name.length() == 0 || name.charAt(0) == '@'){
                System.out.println("Name invalid, choose another name");
                continue;
            }
            try {
                outStream.writeObject(name);
                status = (NameStatus) inStream.readObject();
            }catch(Exception e){connectionLost(e);};

            if(status == NOT_TAKEN)
                break;
            System.out.println("Name Taken, choose another name");
        }
        try {
            outStream.writeObject(netMode);
            outStream.writeObject(uiMode);
        }catch(Exception e){connectionLost(e);}
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
            p = new PlayerTUI((Player)inStream.readObject());
            clone(p);
            drawAll();
        }catch(Exception e){connectionLost(e);}
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
        }catch(Exception e){connectionLost(e);}
    }
    /**
     * helper function for handling the turn event notification from the server
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
        board = (Board) jsonObject.get("board");
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
            connectionLost(e);
        }
        JSONObject jsonObject = (JSONObject) msg.getContent();
        board = (Board)jsonObject.get("board");
        for(int i = 0; i < numPlayers - 1; i++){
            if(librariesOfOtherPlayers.get(i).name.equals(msg.getAuthor()))
                librariesOfOtherPlayers.set(i, (Library)jsonObject.get("library"));
        }
        //drawAll();
        System.out.println("\nPlayer: " + msg.getAuthor() + " made his move, now wait for the turn to change (chat disabled)...");
        Game.waitForSeconds(standardTimer);
        waitForEvents();
    }
    /**
     * helper function for handling the final score calculation event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleFinalScoreEvent(Message msg){
        System.out.println("\nThe game is finished, this is the final scoreboard:\n\n" + msg.getContent());
        Game.waitForSeconds(standardTimer * 2);
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
        Game.waitForSeconds(standardTimer);
        waitForEvents();
    }
    /**
     * helper function for handling the achievement of the second common objective event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleCO_2Event(Message msg){
        System.out.println(msg.getAuthor() + " completed the second common objective getting " + msg.getContent() + " points");
        Game.waitForSeconds(standardTimer);
        waitForEvents();
    }
    /**
     * helper function for handling the completion of the library event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleLibFullEvent(Message msg){
        System.out.println(msg.getAuthor() + " completed the library, the game will continue until the next turn of " + chairmanName);
        Game.waitForSeconds(standardTimer);
        endGame = true;
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

        boolean change_1 = checkCO();
        boolean change_2 = checkLibFull();
        if(change_1 || change_2)
            drawAll();

        stopChatThread();
        sendDoneMove();
    }
    /**
     * helper method used for getting the cards chosen by the user (coordinates)
     * @author Ettori
     * @return the list og the cards chosen by the player
     */
    private ArrayList<Integer> selectCards(){
        String coordString = "";
        String[] rawCoords;
        ArrayList<Integer> coords;
        while(true){
            System.out.print("\nInsert coordinates of the cards to pick (or @ for chat):\n");
            try {
                coordString = br.readLine();
            } catch (IOException e) {
                System.out.println("errore");
                connectionLost(e);
            }
            if(coordString.length() == 0)
                continue;
            rawCoords = coordString.split(" ");

            if(coordString.charAt(0) == '@'){
                sendChatMsg(coordString);
                continue;
            }
            if(!checkRawCoords(rawCoords)){
                System.out.println("\nInvalid selection");
                continue;
            }
            coords = new ArrayList<>();
            for(int i = 0; i < rawCoords.length; i += 2){
                coords.add(Integer.parseInt(rawCoords[i]));
                coords.add(Integer.parseInt(rawCoords[i + 1]));
            }
            if(board.areCardsPickable(coords) && library.maxCardsInsertable() >= coords.size() / 2)
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
        String coordOrder = "";
        int temp_1, temp_2, index_1, index_2;
        while(true){
            printCurOrder(coords);
            if(coords.size() == 2)
                break;
            System.out.print("\nInsert which cards to switch (-1 for exit) (or @ for chat):\n");
            try {
                coordOrder = br.readLine();
            } catch (IOException e) {
                connectionLost(e);
            }
            if(coordOrder.length() == 0 || coordOrder.equals("-1"))
                break;
            if(coordOrder.charAt(0) == '@'){
                sendChatMsg(coordOrder);
                continue;
            }
            try {
                index_1 = Character.getNumericValue(coordOrder.charAt(0));
                index_2 = Character.getNumericValue(coordOrder.charAt(2));
            }catch (Exception e){System.out.println("\nInvalid selection"); continue;}
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
        String column = "";
        while(true){
            System.out.print("\nInsert the column where you wish to put the cards (or @ for chat):\n");
            try {
                column = br.readLine();
            } catch (IOException e) {
                connectionLost(e);
            }
            if(column.length() == 0)
                continue;
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
        int points, lastIndex;
        boolean change = false;
        try {
            if (board.commonObjective_1.algorithm.checkMatch(library.gameLibrary) && !CO_1_Done) { // non devi riprendere il CO se lo hai già fatto una volta
                lastIndex = board.pointsCO_1.size() - 1;
                points = board.pointsCO_1.get(lastIndex);
                board.pointsCO_1.remove(lastIndex);
                pointsUntilNow += points;
                CO_1_Done = true;
                outStream.writeObject(new Message(CO_1, name, Integer.toString(points)));
                System.out.println("\nWell done, you completed the first common objective and you gain " + points + " points (chat disabled)...");
                Game.waitForSeconds(standardTimer);
                change = true;
            }
            if (board.commonObjective_2.algorithm.checkMatch(library.gameLibrary) && !CO_2_Done) {
                lastIndex = board.pointsCO_2.size() - 1;
                points = board.pointsCO_2.get(lastIndex);
                board.pointsCO_2.remove(lastIndex);
                pointsUntilNow += points;
                CO_2_Done = true;
                outStream.writeObject(new Message(CO_1, name, Integer.toString(points)));
                System.out.println("\nWell done, you completed the second common objective and you gain " + points + " points (chat disabled)...");
                Game.waitForSeconds(standardTimer);
                change = true;
            }
        }catch(Exception e){connectionLost(e);}
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
                Game.waitForSeconds(standardTimer);
                return true;
            }
        }catch (Exception e){connectionLost(e);}
        return false;
    }
    /**
     * helper method which update the board when it becomes unplayable (also notify other players)
     * @author Ettori
     */
    private void fixUnplayableBoard(){
        board.fillBoard(numPlayers);
        drawAll();
        System.out.println("\nBoard updated because it was unplayble");
        try {
            boardStatus = new JSONObject();
            boardStatus.put("board", board);
            outStream.writeObject(new Message(UPDATE_UNPLAYBLE, name, boardStatus));
        }catch (Exception e){connectionLost(e);}
    }
    /**
     * method that sends the last move done by the current player to all other clients (after the move is done on this player)
     * @author Ettori
     */
    private void sendDoneMove(){
        gameStatus = new JSONObject();
        playerStatus = new JSONObject();
        System.out.println("\nYou made your move, now wait for other players to acknowledge it (chat disabled)...");
        gameStatus.put("board", new Board(board));
        gameStatus.put("library", new Library(library));

        try {
            outStream.writeObject(new Message(UPDATE_GAME, name, gameStatus));
            state = NOT_ACTIVE;
            //Game.waitForSeconds(standardTimer * 2); // aspetto che tutti abbiano il tempo di capire cosa è successo nel turno
            Game.waitForSeconds(standardTimer / 5);
            new Thread(() -> { // aspetto un secondo e poi mando la notifica di fine turno
                try {
                    Game.waitForSeconds(standardTimer / 2.5);
                    playerStatus.put("player", new Player(this));
                    outStream.writeObject(new Message(END_TURN, name, playerStatus));
                }catch (Exception e){connectionLost(e);}
            }).start();

        }catch(Exception e){connectionLost(e);}

        waitForEvents();
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
        }catch(Exception e){connectionLost(e);}
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
     * print the name of the active player, the 2 CO, the PO, the board, the libraries,
     * and then prints spaces before the next execution of drawAll. It also prints general (useful) information
     * @author Gumus
     */
    public void drawAll(){
        //System.out.flush(); //non funziona sul terminale di intellij
        clearScreen();
        if(activeName.equals(name)){
            System.out.println("Wake up! It's your turn!");
        }else{
            System.out.println("Now " + activeName + " is playing...");
        }
        board.draw();
        objective.draw();
        library.draw();
        for(int i = 0; i < numPlayers - 1; i++)
            librariesOfOtherPlayers.get(i).draw("\nLibrary of " + librariesOfOtherPlayers.get(i).name);
        if(pointsUntilNow % 2 == 1){
            System.out.println("\nYou have achieved " + (pointsUntilNow - 1) + " points from the common objectives (until now)");
            System.out.println("You also achieved 1 point for being the first player to complete the library");
        }else{
            System.out.println("\nYou have achieved " + pointsUntilNow + " points from the common objectives (until now)");
        }
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
     * function that handle the eventual disconnection
     * @param e the exception to throw
     * @author Ettori
     */
    public void connectionLost(Exception e){
        if(Game.showErrors)
            throw new RuntimeException(e);
        else
            System.out.println("\nThe connection was lost and the application is disconnecting...");
        Game.waitForSeconds(standardTimer);
        System.exit(0);
    }
}