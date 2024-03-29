package it.polimi.ingsw.view.TUI;

import it.polimi.ingsw.view.*;

import it.polimi.ingsw.chat.ReceiveChat;
import it.polimi.ingsw.chat.SendChat;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

/**
 * class which represent the player on the client side, mutable,
 * implements Serializable because it will be sent in the socket network
 * @author Ettori Faccincani
 */
public class PlayerTUI extends Player implements Serializable, PlayerI {
    private transient Thread chatThread = null;
    private final transient BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private transient GameI server;
    /**
     * constructor that copies a generic Player object inside a new PlayerTUI object
     * @param p the Player object to copy, received by the server
     */
    public PlayerTUI(Player p) throws RemoteException {
        super();
        name = p.getName();
        isChairMan = p.getIsChairMan();
        library = new Library(p.library);
        objective = p.getPrivateObjective();
        pointsUntilNow = p.pointsUntilNow;
        board = new Board(p.board);
        librariesOfOtherPlayers = new ArrayList<>(p.librariesOfOtherPlayers);
        CO_1_Done = p.getCO_1_Done();
        CO_2_Done = p.getCO_2_Done();
        fullChat = p.getFullChat();
        chairmanName = p.chairmanName;
        activeName = p.activeName;
        numPlayers = p.numPlayers;
        endGame = p.getEndGame();
        pointsMap = p.pointsMap;
    }
    /**
     * standard constructor, starts the main game process on the client side
     * @param mode type of the network chosen by the user
     * @author Ettori
     */
    public PlayerTUI(NetMode mode, String opt) throws RemoteException {
        super();
        System.setProperty("java.rmi.server.hostname", IP.activeIP);
        uiMode = UIMode.TUI;
        netMode = mode;
        System.out.println("\nSoon you will need to enter your nickname for the game");
        try {
            mySocket = new Socket(IP.activeIP, Initializer.PORT);
            outStream = new ObjectOutputStream(mySocket.getOutputStream());
            inStream = new ObjectInputStream(mySocket.getInputStream());
            outStream.flush();
            if(!opt.equals("yes")){
                outStream.writeObject(false);
            }
        }catch (Exception e){System.out.println("\nServer is inactive, try later"); connectionLost(e);}
        System.out.println("\nClient connected \n\n");
        int width = 200;
        int height = 30;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("SansSerif", Font.BOLD, 24));

        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString("My Shelfie", 10, 20);

        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++)
                sb.append(image.getRGB(x, y) == -16777216 ? " " : "$");
            if (sb.toString().trim().isEmpty())
                continue;
            System.out.println(sb);
        }
        System.out.println("\n\n");
        chooseUserName();
    }

    /**
     * Clone the player on the client in the player on the server
     * @author Ettori
     * @param p the Player that will be cloned in the current Object
     */
    @Override
    public void clone(PlayerTUI p){
        name = p.name;
        isChairMan = p.isChairMan;
        library = new Library(p.library);
        objective = p.objective;
        pointsUntilNow = p.pointsUntilNow;
        board = new Board(p.board);
        librariesOfOtherPlayers = new ArrayList<>(p.librariesOfOtherPlayers);
        CO_1_Done = p.CO_1_Done;
        CO_2_Done = p.CO_2_Done;
        fullChat = p.fullChat;
        chairmanName = p.chairmanName;
        activeName = p.activeName;
        numPlayers = p.numPlayers;
        endGame = p.endGame;
        pointsMap = p.pointsMap;
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
            if(name.length() == 0 || name.charAt(0) == '@' || name.equals("exit") || name.equals("all") || name.equals("...") || name.equals("names")){
                System.out.println("Name invalid, choose another name");
                continue;
            }
            try {
                outStream.writeObject(name);
                status = (NameStatus) inStream.readObject();
            }catch(Exception e){connectionLost(e);}

            if(status == NameStatus.NOT_TAKEN) {
                System.out.println("\nName: '" + name + "' accepted by the server!");
                break;
            }
            if(status == NameStatus.OLD){
                System.out.println("\nName: '" + name + " was found in a previous game");
                break;
            }
            if(status == NameStatus.NOT_FOUND){
                System.out.println("\nAnother game is running and your name was not found...\n");
                System.exit(0);
            }
            if(status == NameStatus.FOUND){
                getPreviousState();
                return;
            }
            System.out.println("Name Taken, choose another name\n");
        }
        getInitialState();
    }
    /**
     * Receive the status of the player (previously disconnected) from the server and restart the game
     * @author Ettori Faccincani
     */
    private void getPreviousState(){
        PlayerTUI p;
        try {
            System.out.println("\nReconnecting to the running game...");
            p = new PlayerTUI((Player)inStream.readObject());
            clone(p);
            drawAll();
            if(netMode == NetMode.RMI)
                new Thread(this::listenForEndGame).start();
            System.out.println("List of the commands of the chat\n@all for message to everyone\n@\"name\" for private message\n@names to see the nicknames of all the players\n@exit to close the game\n");
        }catch(Exception e){connectionLost(e);}
        try {
            server = (GameI)LocateRegistry.getRegistry(IP.activeIP, Initializer.PORT_RMI).lookup("Server");
        } catch (RemoteException | NotBoundException e) {
            connectionLost(e);
        }
        if(netMode == NetMode.RMI) {
            try {
                server.addClient(name, this);
            } catch (RemoteException e) {
                connectionLost(e);
            }
        }
        try {
            outStream.writeObject(true);
        } catch (IOException e) {
            connectionLost(e);
        }
        if(netMode == NetMode.SOCKET)
            new Thread(this::ping).start();
        else
            new Thread(this::pingRMI).start();
        startChatSendThread();
        if(netMode == NetMode.SOCKET) {
            waitForEvents();
        }
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
            activeName = chairmanName;
            drawAll();
            if(netMode == NetMode.RMI)
                new Thread(this::listenForEndGame).start();
            System.out.println("List of the commands of the chat\n@all for message to everyone\n@\"name\" for private message\n@names to see the nicknames of all the players\n@exit to close the game");
        }catch(Exception e){connectionLost(e);}
        try {
            server = (GameI)LocateRegistry.getRegistry(IP.activeIP, Initializer.PORT_RMI).lookup("Server");
        } catch (RemoteException | NotBoundException e) {
            connectionLost(e);
        }
        if(netMode == NetMode.RMI) {
            try {
                server.addClient(name, this);
            } catch (RemoteException e) {
                connectionLost(e);
            }
        }
        try {
            outStream.writeObject(true);
        } catch (IOException e) {
            connectionLost(e);
        }
        if(netMode == NetMode.SOCKET)
            new Thread(this::ping).start();
        else
            new Thread(this::pingRMI).start();
        if(name.equals(chairmanName)) {
            if(netMode == NetMode.SOCKET) {
                startChatReceiveThread();
            }
            waitForMove();
        }
        else {
            startChatSendThread();
            if(netMode == NetMode.SOCKET) {
                waitForEvents();
            }
        }
    }
    /**
     * add a string (chat message) to the full chat of the game
     * @author Ettori
     * @param s the message received, it will be added to the fullChat attribute
     */
    public void addToFullChat(String s){fullChat += s;}
    /**
     * function  used to wait for notification from the server while the player is NON active
     * @author Ettori Faccincani
     */
    private void waitForEvents(){
        boolean flag; //used to indicate that this player has become the active one
        while(true){
            flag = false;
            try {
                Message msg = (Message) inStream.readObject();
                switch (msg.getType()) {
                    case YOUR_TURN -> {handleYourTurnEvent(); flag = true;}
                    case CHANGE_TURN -> handleChangeTurnEvent(msg);
                    case UPDATE_UNPLAYABLE -> handleUpdateUnplayableEvent(msg);
                    case UPDATE_GAME -> handleUpdateGameEvent(msg);
                    case FINAL_SCORE -> handleFinalScoreEvent(msg);
                    case CHAT -> handleChatEvent(msg);
                    case CO_1 -> handleCO_1Event(msg);
                    case CO_2 -> handleCO_2Event(msg);
                    case LIB_FULL -> handleLibFullEvent(msg);
                    case DISCONNECTED -> handleDisconnectedEvent(msg);
                    case LOST_CLIENT -> handleLostClientEvent(msg);
                    case SHOW_EVENT -> handleShowEvent(msg);
                }
                if(flag)
                    break;
            }catch(Exception e){
                connectionLost(e);
            }
        }
        waitForMove();
    }
    /**
     * helper function for handling the show event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleShowEvent(Message msg){
        if(msg.getAuthor() != null && msg.getAuthor().equals("win")){
            System.out.println("\n" + msg.getContent());
            Game.waitForSeconds(Game.waitTimer);
            System.exit(0);
        }
        System.out.println("\n" + msg.getContent());
    }
    /**
     * helper function for handling the client (not active) disconnection notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    public void handleLostClientEvent(Message msg){
        System.out.println("\nPlayer " + msg.getAuthor() + " disconnected from the game");
    }
    /**
     * helper function for handling the turn event notification from the server
     * @author Ettori
     */
    private void handleYourTurnEvent(){
        activeName = name;
        drawAll();
        stopChatThread();
        if(netMode == NetMode.SOCKET) {
            startChatReceiveThread();
        }
        turnThread = new Thread(() ->{
            try {
                Thread.sleep(1000 * 60 * 5);
            } catch (InterruptedException e) {
                return;
            }
            if(activeName.equals(name))
                sendDoneMove();
        });
        turnThread.start();
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
    }
    /**
     * helper function for handling the unplayble board fixing event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleUpdateUnplayableEvent(Message msg){
        board = (Board) msg.getContent();
        drawAll();
        System.out.println("\nBoard updated because it was unplayable");
    }
    /**
     * helper function for handling the update game notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleUpdateGameEvent(Message msg){
        stopChatThread();
        if(netMode == NetMode.SOCKET)
            sendToServer(new Message(MessageType.STOP, null, null)); // send this message on your Socket (to yourself)
        PlayerSend p = (PlayerSend) msg.getContent();
        board = p.board;
        for(int i = 0; i < numPlayers - 1; i++){
            if(librariesOfOtherPlayers.get(i).name.equals(msg.getAuthor()))
                librariesOfOtherPlayers.set(i, p.library);
        }
        pointsMap.put(msg.getAuthor(), p.pointsUntilNow);
        if(endGame)
            drawAll();
        System.out.println("\nPlayer " + msg.getAuthor() + " made his move, now wait for the turn to change...");
    }
    /**
     * helper function for handling the final score calculation event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleFinalScoreEvent(Message msg){
        System.out.println("\nThe game is finished, this is the final scoreboard:\n\n" + msg.getContent());
        Game.waitForSeconds(Game.showTimer);
        System.exit(0);
    }
    /**
     * helper function for handling the chat message event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleChatEvent(Message msg){
        fullChat += msg.getContent();
        if(activeName.equals(name))
            System.out.println(msg.getContent());
        else
            drawAll();
    }
    /**
     * helper function for handling the achievement of the first common objective event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleCO_1Event(Message msg){
        System.out.println(msg.getAuthor() + " completed the first common objective getting " + msg.getContent() + " points");
        Game.waitForSeconds(Game.waitTimer);
    }
    /**
     * helper function for handling the achievement of the second common objective event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleCO_2Event(Message msg){
        System.out.println(msg.getAuthor() + " completed the second common objective getting " + msg.getContent() + " points");
        Game.waitForSeconds(Game.waitTimer);
    }
    /**
     * helper function for handling the completion of the library event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleLibFullEvent(Message msg){
        System.out.println(msg.getAuthor() + " completed the library, the game will continue until the next turn of " + chairmanName);
        Game.waitForSeconds(Game.waitTimer);
        endGame = true;
    }
    /**
     * helper function for handling the disconnection event notification from the server (of the active client)
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleDisconnectedEvent(Message msg){
        System.out.println("\nThe active player (" + msg.getAuthor() + ") disconnected from the game");
        if(netMode == NetMode.SOCKET)
            sendToServer(new Message(MessageType.STOP, null, null));
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
                continue;
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
                coords.add(Integer.parseInt(rawCoords[i]) - 1);
                coords.add(Integer.parseInt(rawCoords[i + 1]) - 1);
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
                continue;
            }
            if(coordOrder.length() == 0)
                continue;
            if(coordOrder.equals("-1"))
                break;
            if(coordOrder.charAt(0) == '@'){
                sendChatMsg(coordOrder);
                continue;
            }
            try {
                index_1 = Character.getNumericValue(coordOrder.charAt(0)) - 1;
                index_2 = Character.getNumericValue(coordOrder.charAt(2)) - 1;
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
                continue;
            }
            if(column.length() == 0)
                continue;
            if(column.charAt(0) == '@'){
                sendChatMsg(column);
                continue;
            }
            col = Integer.parseInt(column) - 1;
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
            if (board.commonObjective_1.algorithm.checkMatch(library.gameLibrary) && !CO_1_Done && board.pointsCO_1.size() > 0) { // you can't take another time the CO if you already did
                lastIndex = board.pointsCO_1.size() - 1;
                points = board.pointsCO_1.get(lastIndex);
                board.pointsCO_1.remove(lastIndex);
                pointsUntilNow += points;
                CO_1_Done = true;
                sendToServer(new Message(MessageType.CO_1, name, Integer.toString(points)));
                System.out.println("\nWell done, you completed the first common objective and you gain " + points + " points...");
                Game.waitForSeconds(Game.showTimer);
                change = true;
            }
            if (board.commonObjective_2.algorithm.checkMatch(library.gameLibrary) && !CO_2_Done && board.pointsCO_2.size() > 0) {
                lastIndex = board.pointsCO_2.size() - 1;
                points = board.pointsCO_2.get(lastIndex);
                board.pointsCO_2.remove(lastIndex);
                pointsUntilNow += points;
                CO_2_Done = true;
                sendToServer(new Message(MessageType.CO_2, name, Integer.toString(points)));
                System.out.println("\nWell done, you completed the second common objective and you gain " + points + " points...");
                Game.waitForSeconds(Game.showTimer);
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
                sendToServer(new Message(MessageType.LIB_FULL, name, null));
                System.out.println("\nWell done, you are the first player to complete the library, the game will continue until the next turn of " + chairmanName + "...");
                Game.waitForSeconds(Game.showTimer);
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
            sendToServer(new Message(MessageType.UPDATE_UNPLAYABLE, name, new Board(board)));
        }catch (Exception e){connectionLost(e);}
    }
    /**
     * method that sends the last move done by the current player to all other clients (after the move is done on this player)
     * @author Ettori
     */
    private void sendDoneMove(){
        System.out.println("You made your move, now wait for other players to acknowledge it...");
        sendToServer(new Message(MessageType.UPDATE_GAME, name, new PlayerSend(this)));
        if(netMode == NetMode.SOCKET) {
            Game.waitForSeconds(Game.fastTimer);
            waitForEvents();
        }
        turnThread.interrupt();
    }
    /**
     * stops all the thread interaction related to the chat (should be only ReceiveChat)
     * @author Ettori
     */
    private void stopChatThread(){
        if(chatThread == null){
            return;
        }
        try {
            chatThread.interrupt();
        }catch (Exception e){connectionLost(e);}
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
    public boolean sendChatMsg(String msg){
        if(msg.length() == 0)
            return false;
        if(msg.equals("@exit")){
            System.out.println("\nThe game is exiting...");
            Game.waitForSeconds(Game.fastTimer * 2);
            System.exit(0);
        }


        if(msg.charAt(0) != '@')
            return false;
        if(!msg.contains(" ")){
            if(msg.substring(1).equals("names"))
                showAllNames();
            return false; //return false because isn't a message to other player
        }
        String dest = msg.substring(1, msg.indexOf(' '));
        msg = msg.substring(msg.indexOf(' '));
        msg = name + " says:" + msg + " (to " + dest + ") at " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "\n";

        if(!doesPlayerExists(dest) && !dest.equals("all")) {
            System.out.println("\nThe name chosen does not exists");
            return false;
        }
        if(dest.equals(name)){
            System.out.println("\nYou can't send chat messages to yourself");
            return false;
        }
        fullChat += msg;
        try{
            sendToServer(new Message(MessageType.CHAT, dest, msg));
        }catch(Exception e){connectionLost(e);}
        return true;
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
        clearScreen();
        if(activeName.equals(name)){
            System.out.println("Your turn is now started, play your move !");
        }else{
            System.out.println("Now " + activeName + " is playing his turn...");
        }
        board.draw();
        System.out.println("\nThere are " + (endGame ? "0" : "1") + " points available for completing the library");
        objective.draw();
        library.draw();
        for(int i = 0; i < numPlayers - 1; i++)
            librariesOfOtherPlayers.get(i).draw("\nLibrary of " + librariesOfOtherPlayers.get(i).name + " (" + pointsMap.get(librariesOfOtherPlayers.get(i).name) + " points)");

        //points of CO are always even and other type of points are add at the end of the game
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
        if(closed)
            return;
        closed = true;
        if(Player.showErrors)
            throw new RuntimeException(e);
        else
            System.out.println("\nThe connection was lost and the application is disconnecting...\n");
        Game.waitForSeconds(Game.waitTimer);
        System.exit(0);
    }
    /**
     * method that periodically pings the server from socket client
     * @author Ettori
     */
    public void ping(){
        while(true){
            Game.waitForSeconds(Game.waitTimer * 2);
            try {
                outStream.writeObject(new Message(MessageType.PING, null, null));
            } catch (IOException e) {
                connectionLost(e);
            }
        }
    }
    /**
     * method that periodically pings the server from RMI client
     * @author Ettori
     */
    public void pingRMI(){
        while(true){
            Game.waitForSeconds(Game.waitTimer * 2);
            try {
                server.ping();
            } catch (RemoteException e) {
                connectionLost(e);
            }
        }
    }
    /********************************************* RMI ***************************************************************/
    /**
     * method (called from remote) that is the equivalent of wait for events of the socket version
     * @param msg the message received from the server
     * @author Ettori
     */
    public void receivedEventRMI(Message msg){
        switch (msg.getType()) {
            case YOUR_TURN -> {handleYourTurnEvent(); waitForMove();}
            case CHANGE_TURN -> handleChangeTurnEvent(msg);
            case UPDATE_UNPLAYABLE -> handleUpdateUnplayableEvent(msg);
            case UPDATE_GAME -> handleUpdateGameEvent(msg);
            case FINAL_SCORE -> handleFinalScoreEvent(msg);
            case CHAT -> handleChatEvent(msg);
            case CO_1 -> handleCO_1Event(msg);
            case CO_2 -> handleCO_2Event(msg);
            case LIB_FULL -> handleLibFullEvent(msg);
            case DISCONNECTED -> handleDisconnectedEvent(msg);
            case LOST_CLIENT -> handleLostClientEvent(msg);
            case SHOW_EVENT -> handleShowEvent(msg);
        }
    }
    /**
     * general method to send a message to the server, it chooses the right network connection of the player
     * @author Ettori
     * @param msg the message that must be sent
     */
    public void sendToServer(Message msg){

        if(netMode == NetMode.SOCKET) {
            try {
                outStream.writeObject(msg);
            } catch (IOException e) {
                connectionLost(e);
            }
        }
        else{
            try {
                server.redirectToClientRMI(msg);
            } catch (RemoteException e) {
                connectionLost(e);
            }
        }
    }
    /**
     * method that listen for the final score of the game, for RMI clients
     * @author Ettori
     */
    private void listenForEndGame(){
        Message msg = null;
        while(true){
            try {
                msg = (Message) inStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                connectionLost(e);
            }
            if(msg == null)
                connectionLost(new NullPointerException());
            if(msg.getType() != MessageType.FINAL_SCORE)
                connectionLost(new RuntimeException("listenForEndGame method in TUI received a message different than FINAL_SCORE"));
            handleFinalScoreEvent(msg);
        }
    }
    /**
     * method that allow the server ping the RMI client
     * @author Ettori
     */
    public void pingClient(){}
}