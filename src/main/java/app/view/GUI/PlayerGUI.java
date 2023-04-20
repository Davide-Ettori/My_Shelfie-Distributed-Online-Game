package app.view.GUI;

import app.view.IP;
import app.controller.*;
import app.model.*;

import app.view.UIMode;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Date;

import static app.controller.MessageType.*;
import static app.controller.NameStatus.*;
import static app.model.Color.EMPTY;
import static app.model.NetMode.RMI;
import static app.model.NetMode.SOCKET;
import static app.view.Dimensions.*;
import static java.awt.Color.*;
import static java.awt.GridBagConstraints.*;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * class which represent the player on the client side, mutable,
 * implements Serializable because it will be sent in the socket network
 * @author Ettori Faccincani
 */
public class PlayerGUI extends Player implements Serializable, PlayerI{
    private final transient int DIM = 9;
    private final transient int ROWS = 6;
    private final transient int COLS = 5;
    private final transient int cardBorderSize = 2;
    private final transient java.awt.Color borderColor = BLACK;
    private final transient int libFullX = 6; // y sulla board
    private final transient int libFullY = 7; // x sulla board
    private final transient Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //get the dimension of the screen
    private final transient GridBagConstraints gbc = new GridBagConstraints();
    private transient JFrame mainFrame;
    private transient JPanel infoBox, internalPanelSide, internalPanelHigh, internalPanelLow, player1Panel, player2Panel, player3Panel, gameBoardPanel, myLibraryPanel, chatPanel, CO1Panel, CO2Panel, POPanel, chairmanPanel;
    private transient JLabel POLabel, CO1Label, CO2Label, pointsCO1Label, pointsCO2Label, chairmanLabel, library1Label, library2Label, library3Label, boardLabel, libraryLabel, generalLabel;
    private transient JTextField chairmanInfo, activeTurnInfo, curPointsInfo, titleInfo, chooseColText, insertMessage, insertPlayer, CO1Title, CO2Title, POTitle, chairmanTitle;
    private transient JTextArea library1Text, library2Text, library3Text, boardText, myLibraryText, chatTitle, tempChatHistory;
    private transient JScrollPane chatHistory;
    private transient JButton pickCardsBtn, sendMessageBtn;

    private final transient JLabel[][] boardCards = new JLabel[DIM][DIM];
    private final transient JLabel[][] myLibraryCards = new JLabel[ROWS][COLS];
    private final transient ArrayList<JLabel[][]> otherLibrariesCards = new ArrayList<>(Arrays.asList(new JLabel[ROWS][COLS], new JLabel[ROWS][COLS], new JLabel[ROWS][COLS]));

    private transient ArrayList<Integer> cardsPicked = new ArrayList<>();
    private transient GameI server;

    /**
     * constructor that copies a generic Player object inside a new PlayerTUI object
     * @param p the Player object to copy, received by the server
     */
    public PlayerGUI(Player p) throws RemoteException {
        super();
        netMode = p.netMode;
        uiMode = p.uiMode;
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
    }
    /**
     * standard constructor, starts the main game process on the client side
     * @param mode type of the network chosen by the user
     * @param ui type of ui chosen by the user
     * @author Ettori
     */
    public PlayerGUI(NetMode mode, UIMode ui) throws RemoteException {
        super(); // Costruttore iniziale
        uiMode = ui;
        netMode = mode;
        System.out.println("\nSoon you will need to enter your nickname for the game");
        //showMessageDialog(new JFrame(), "Soon you will need to enter your nickname for the game");
        try {
            mySocket = new Socket(IP.activeIP, Initializer.PORT);
            outStream = new ObjectOutputStream(mySocket.getOutputStream());
            inStream = new ObjectInputStream(mySocket.getInputStream());
            outStream.writeObject(false);
        }catch (Exception e){alert("\nServer is either full or inactive, try later"); return;}
        System.out.println("\nClient connected");
        showChooseNameWindow();
    }
    /**
     * Clone the player on the client in the player on the server
     * @author Ettori
     * @param p the Player that will be cloned in the current Object
     */
    public void clone(PlayerGUI p){ // copia la versione sul server dentro a quella del client
        netMode = p.netMode;
        uiMode = p.uiMode;
        name = p.name;
        isChairMan = p.isChairMan;
        library = new Library(p.library);
        objective = p.objective;
        pointsUntilNow = p.pointsUntilNow;
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
     * method that update the information about the game
     * @author Ettori
     */
    private void updateInfo(){
        pointsCO1Label.setIcon(board.pointsCO_1.size() == 0 ? new ImageIcon (new ImageIcon("assets/scoring tokens/scoring_back_EMPTY.jpg").getImage().getScaledInstance(pointsDim, pointsDim, Image.SCALE_SMOOTH)) : new ImageIcon (new ImageIcon(pathPointsCO + "_" + board.pointsCO_1.peekLast() + ".jpg").getImage().getScaledInstance(pointsDim, pointsDim, Image.SCALE_SMOOTH)));
        pointsCO2Label.setIcon(board.pointsCO_2.size() == 0 ? new ImageIcon (new ImageIcon("assets/scoring tokens/scoring_back_EMPTY.jpg").getImage().getScaledInstance(pointsDim, pointsDim, Image.SCALE_SMOOTH)) : new ImageIcon (new ImageIcon(pathPointsCO + "_" + board.pointsCO_2.peekLast() + ".jpg").getImage().getScaledInstance(pointsDim, pointsDim, Image.SCALE_SMOOTH)));
        activeTurnInfo.setText(" The active player is " + activeName + " ");
        curPointsInfo.setText(" " + pointsUntilNow + " points achieved until now ");
        tempChatHistory.setText(fullChat);
    }
    /**
     * method that update the board of the game
     * @author Ettori
     */
    private void updateBoard(){
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                if(i == libFullX && j == libFullY)
                    continue;
                boardCards[i][j].setIcon(new ImageIcon(new ImageIcon(board.getGameBoard()[i][j].imagePath).getImage().getScaledInstance(cardDimBoard, cardDimBoard, Image.SCALE_SMOOTH)));
                boardCards[i][j].setVisible(board.getGameBoard()[i][j].color != EMPTY);
            }
        }
        boardCards[libFullX][libFullY].setVisible(!endGame);
    }
    /**
     * method that update the library of the active player
     * @author Ettori
     */
    private void updateMyLibrary(){
        for(int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                myLibraryCards[i][j].setIcon(new ImageIcon(new ImageIcon(library.gameLibrary[i][j].imagePath).getImage().getScaledInstance(cardDimBoard, cardDimBoard, Image.SCALE_SMOOTH)));
                myLibraryCards[i][j].setVisible(library.gameLibrary[i][j].color != EMPTY);
            }
        }
    }
    /**
     * method that update the libraries of all the player except the one which is active
     * @author Ettori
     */
    private void updateOtherLibraries(){
        for(int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                otherLibrariesCards.get(0)[i][j].setIcon(new ImageIcon(new ImageIcon(librariesOfOtherPlayers.get(0).gameLibrary[i][j].imagePath).getImage().getScaledInstance(cardDimBoard, cardDimBoard, Image.SCALE_SMOOTH)));
                otherLibrariesCards.get(0)[i][j].setVisible(librariesOfOtherPlayers.get(0).gameLibrary[i][j].color != EMPTY);
            }
        }
        if(numPlayers >= 3){
            for(int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    otherLibrariesCards.get(1)[i][j].setIcon(new ImageIcon(new ImageIcon(librariesOfOtherPlayers.get(1).gameLibrary[i][j].imagePath).getImage().getScaledInstance(cardDimBoard, cardDimBoard, Image.SCALE_SMOOTH)));
                    otherLibrariesCards.get(1)[i][j].setVisible(librariesOfOtherPlayers.get(1).gameLibrary[i][j].color != EMPTY);
                }
            }
        }
        if(numPlayers >= 4){
            for(int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    otherLibrariesCards.get(2)[i][j].setIcon(new ImageIcon(new ImageIcon(librariesOfOtherPlayers.get(2).gameLibrary[i][j].imagePath).getImage().getScaledInstance(cardDimBoard, cardDimBoard, Image.SCALE_SMOOTH)));
                    otherLibrariesCards.get(2)[i][j].setVisible(librariesOfOtherPlayers.get(2).gameLibrary[i][j].color != EMPTY);
                }
            }
        }
    }
    /**
     * Function that update the GUI with the new information
     * @author Ettori Giammusso
     */
    private void updateGUI(){
        updateInfo();
        updateBoard();
        updateMyLibrary();
        updateOtherLibraries();
    }
    /**
     * check if the card was already picked before
     * @author Ettori
     * @param x the x coord
     * @param y the y coord
     * @return -1 if it was not picked before, the current index if it was picked before
     */
    private int getCardIndex(int x, int y){
        for(int i = 0; i < cardsPicked.size(); i += 2){
            if(cardsPicked.get(i) == x && cardsPicked.get(i + 1) == y)
                return i;
        }
        return -1;
    }
    /**
     * method that reads the cards chosen and the column chosen, then it tries to pick the cards (if possible)
     * @author Ettori
     */
    private void tryToPickCards(){
        ArrayList<Integer> cards = new ArrayList<>(cardsPicked);
        cardsPicked = new ArrayList<>();
        int col;
        try{col = Integer.parseInt(chooseColText.getText());}
        catch (Exception e){
            alert("Invalid Selection");
            chooseColText.setText("");
            for(int i = 0; i < cards.size(); i += 2)
                boardCards[cards.get(i)][cards.get(i + 1)].setBorder(BorderFactory.createLineBorder(borderColor, 0));
            return;
        }
        chooseColText.setText("");
        if(!board.areCardsPickable(cards) || !library.checkCol(col, cards.size() / 2))
            alert("Invalid Selection");
        else{
            activeName = "..."; // non voglio poter più prendere carte
            pickCards(cards, col);
            updateGUI();
            boolean change_1 = checkCO();
            boolean change_2 = checkLibFull();
            if(change_1 || change_2)
                updateInfo();

            sendDoneMove(); // mando la mossa al server
            }
        for(int i = 0; i < cards.size(); i += 2)
            boardCards[cards.get(i)][cards.get(i + 1)].setBorder(BorderFactory.createLineBorder(borderColor, 0));
    }
    /**
     * method for choosing the nickname of the player for the future game, implemented with the Swing GUI
     * @author Ettori
     */
    private void showChooseNameWindow(){
        JFrame frame;
        JPanel panel;
        JButton sendBtn;
        JTextField textInput;
        frame = new JFrame(); // creo la finestra

        sendBtn = new JButton("Choose Name"); // bottone per mandare il nome
        textInput = new JTextField(20); // textbox input dall'utente

        textInput.setBounds(100, 20, 165, 25);
        textInput.addActionListener(event -> sendBtn.doClick()); // se l'utente preme invio, chiama automaticamente il bottone sendBtn

        sendBtn.addActionListener((event) ->{ // funzione di event listener
            NameStatus status = null;
            name = textInput.getText();
            if(name.length() == 0 || name.charAt(0) == '@' || name.equals("all") || name.equals("names")){
                alert("Name invalid, choose another name"); // vedi JavaDoc di alert
                textInput.setText("");
                return;
            }
            try {
                outStream.writeObject(name);
                status = (NameStatus) inStream.readObject();
            }catch(Exception e){connectionLost(e);};

            if(status == NOT_TAKEN){
                alert("\nName: '" + name + "' accepted by the server!");
                //System.exit(0);
                try {
                    outStream.writeObject(netMode);
                    outStream.writeObject(uiMode);
                }catch(Exception e){connectionLost(e);}
                getInitialState(); // partire a lavorare da questa funzione in poi
                frame.setVisible(false);
                return;
            }
            alert("Name Taken, choose another name");
            textInput.setText("");
        });

        panel = new JPanel(); // creo un pannello, dandogli i parametri dimensionali
        panel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        panel.setLayout(new GridLayout(2,1)); // griglia con 1 riga e 2 colonne

        panel.add(textInput); // Aggiunge i componenti in ordine di griglia: tutta la prima riga, tutta la seconda, ecc. (sx -> dx)
        panel.add(sendBtn);

        frame.add(panel, BorderLayout.CENTER); // aggiungo il pannello alla finestra
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Window for Choosing the name");
        frame.pack(); // preparo la finestra
        frame.setVisible(true); // mostro il tutto a schermo, GUI
    }
    /**
     * Receive the status of the player from the server and attend the start of the game
     * @author Ettori Faccincani
     */
    private void getInitialState(){
        // da qui in poi bisogna lavorarci
        PlayerGUI p;
        try {
            alert("Be patient, the game will start soon...");
            p = new PlayerGUI((Player)inStream.readObject());
            clone(p);
            new Thread(this::initGUI).start();
        }catch(Exception e){connectionLost(e);}
        try { // ottieni la reference al server remoto
            server = (GameI) LocateRegistry.getRegistry(IP.activeIP, Initializer.PORT_RMI).lookup("Server");
        } catch (RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }
        if(netMode == RMI) {
            try { // provo a chiamare un metodo remoto --> devi sempre farlo in un try catch, può fallire
                //server.stampa("hello world");
                server.addClient(name, this);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        if(netMode == SOCKET)
            new Thread(this::waitForEvents).start();
    }
    /**
     * function used to wait for notification from the server while the player is NON active
     * @author Ettori Faccincani
     */
    private void waitForEvents(){ // funzione principale di attesa
        while(true){
            try {
                Message msg = (Message) inStream.readObject();
                //System.out.println(msg.getType());
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
                    //case STOP -> {} // non devi fare niente
                }
            }catch(Exception e){connectionLost(e);}
        }
    }
    /**
     * helper function for handling the turn event notification from the server
     * @author Ettori
     */
    private void handleYourTurnEvent(){
        activeName = name;
        if(board.isBoardUnplayable())
            fixUnplayableBoard();
        updateInfo();
        updateBoard();
        updateOtherLibraries();
    }
    /**
     * helper function for handling the change event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleChangeTurnEvent(Message msg){
        activeName = (String) msg.getContent();
        updateInfo();
        updateBoard();
        updateOtherLibraries();
    }
    /**
     * helper function for handling the unplayble board fixing event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleUpdateUnplayableEvent(Message msg){
        JSONObject jsonObject = (JSONObject) msg.getContent();
        board = (Board) jsonObject.get("board");
        updateBoard();
        alert("\nBoard updated because it was unplayable");
    }
    /**
     * helper function for handling the update game notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleUpdateGameEvent(Message msg){
        if(netMode == SOCKET)
            sendToServer(new Message(STOP, null, null));
        JSONObject jsonObject = (JSONObject) msg.getContent();
        board = (Board)jsonObject.get("board");
        for(int i = 0; i < numPlayers - 1; i++){
            if(librariesOfOtherPlayers.get(i).name.equals(msg.getAuthor()))
                librariesOfOtherPlayers.set(i, (Library)jsonObject.get("library"));
        }
        //updateBoard();
        //updateOtherLibraries();
        alert("\nPlayer: " + msg.getAuthor() + " made his move, now wait for the turn to change (chat disabled)...");
    }
    /**
     * helper function for handling the final score calculation event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleFinalScoreEvent(Message msg){
        alert("\nThe game is finished, this is the final scoreboard:\n\n" + msg.getContent());
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
        updateInfo();
    }
    /**
     * helper function for handling the achievement of the first common objective event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleCO_1Event(Message msg){
        alert(msg.getAuthor() + " completed the first common objective getting " + msg.getContent() + " points");
        board.pointsCO_1.remove(board.pointsCO_1.size() - 1);
        updateInfo();
        //Game.waitForSeconds(standardTimer);
    }
    /**
     * helper function for handling the achievement of the second common objective event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleCO_2Event(Message msg){
        alert(msg.getAuthor() + " completed the second common objective getting " + msg.getContent() + " points");
        board.pointsCO_2.remove(board.pointsCO_2.size() - 1);
        updateInfo();
        //Game.waitForSeconds(standardTimer);
    }
    /**
     * helper function for handling the completion of the library event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleLibFullEvent(Message msg){
        alert(msg.getAuthor() + " completed the library, the game will continue until the next turn of " + chairmanName);
        //Game.waitForSeconds(standardTimer);
        endGame = true;
        boardCards[libFullX][libFullY].setVisible(false);
    }
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
                sendToServer(new Message(CO_1, name, Integer.toString(points)));
                alert("\nWell done, you completed the first common objective and you gain " + points + " points (chat disabled)...");
                //Game.waitForSeconds(standardTimer);
                change = true;
            }
            if (board.commonObjective_2.algorithm.checkMatch(library.gameLibrary) && !CO_2_Done) {
                lastIndex = board.pointsCO_2.size() - 1;
                points = board.pointsCO_2.get(lastIndex);
                board.pointsCO_2.remove(lastIndex);
                pointsUntilNow += points;
                CO_2_Done = true;
                sendToServer(new Message(CO_2, name, Integer.toString(points)));
                alert("\nWell done, you completed the second common objective and you gain " + points + " points (chat disabled)...");
                //Game.waitForSeconds(standardTimer);
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
                sendToServer(new Message(LIB_FULL, name, null));
                alert("\nWell done, you are the first player to complete the library, the game will continue until the next turn of " + chairmanName + " (chat disabled)...");
                updateBoard();
                //Game.waitForSeconds(standardTimer);
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
        updateGUI();
        alert("\nBoard updated because it was unplayble");
        try {
            boardStatus = new JSONObject();
            boardStatus.put("board", board);
            sendToServer(new Message(UPDATE_UNPLAYBLE, name, boardStatus));
        }catch (Exception e){connectionLost(e);}
    }
    /**
     * method that sends the last move done by the current player to all other clients (after the move is done on this player)
     * @author Ettori
     */
    private void sendDoneMove(){
        gameStatus = new JSONObject();
        playerStatus = new JSONObject();
        alert("\nYou made your move, now wait for other players to acknowledge it (chat disabled)...");
        gameStatus.put("board", new Board(board));
        gameStatus.put("library", new Library(library));

        try {
            sendToServer(new Message(UPDATE_GAME, name, gameStatus));
            //Game.waitForSeconds(standardTimer * 2); // aspetto che tutti abbiano il tempo di capire cosa è successo nel turno
            Game.waitForSeconds(standardTimer * 6 / 5);
            new Thread(() -> { // aspetto un secondo e poi mando la notifica di fine turno
                try {
                    Game.waitForSeconds(standardTimer / 2.5);
                    if(netMode == SOCKET)
                        playerStatus.put("player", new Player(this));
                    sendToServer(new Message(END_TURN, name, playerStatus));
                }catch (Exception e){connectionLost(e);}
            }).start();

        }catch(Exception e){connectionLost(e);}
    }
    /**
     * Send with socket network the message of the chat to the right players
     * @author Ettori
     */
    public void sendChatMsg(){
        String dest = insertPlayer.getText();
        String msg = insertMessage.getText();
        insertPlayer.setText("");
        insertMessage.setText("");

        msg = name + " says: " + msg + " (to " + dest + ") at " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "\n";

        if(!doesPlayerExists(dest) && !dest.equals("all")) {
            alert("\nThe name chosen does not exists");
            return;
        }
        if(dest.equals(name)){
            alert("\nYou can't send chat messages to yourself");
            return;
        }
        fullChat += msg;
        tempChatHistory.setText(fullChat);
        try{
            sendToServer(new Message(CHAT, dest, msg));
        }catch(Exception e){connectionLost(e);}
    }
    /**
     * helper function for alerting a message to the user (pop-up)
     * @param s the string og the message to show
     */
    private void alert(String s){showMessageDialog(null, s);}
    /**
     * function that handle the eventual disconnection
     * @param e the exception to throw
     * @author Ettori
     */
    public void connectionLost(Exception e){
        if(Game.showErrors)
            throw new RuntimeException(e);
        else
            alert("\nThe connection was lost and the application is disconnecting...");
        Game.waitForSeconds(standardTimer / 2.5);
        System.exit(0);
    }
    /**
     * Function that initialize all the GUI
     * @author Ettori Faccincani Giammusso
     */
    public void initGUI(){
        //Creation:
        //External
        //Internals: first level of abstraction
        mainFrame =  new JFrame("My Shelfie");

        //Internals: second level of abstraction
        gbc.insets = new Insets(generalBorder,generalBorder,generalBorder,generalBorder);

        //CYAN

        POLabel = new JLabel(new ImageIcon(new ImageIcon(objective.imagePath).getImage().getScaledInstance(PO_w, PO_h, Image.SCALE_SMOOTH)));
        POLabel.setPreferredSize(new Dimension(PO_w, PO_h));

        POPanel = new JPanel(new GridBagLayout());
        POTitle = new JTextField(" Your private objective ");
        POTitle.setMinimumSize(new Dimension(textCols * textCharsNum, textCols));
        POTitle.setEditable(false);
        POTitle.setBorder(null);

        CO1Label = new JLabel(new ImageIcon(new ImageIcon(board.commonObjective_1.imagePath).getImage().getScaledInstance(CO_w, CO_h, Image.SCALE_SMOOTH)));
        CO1Label.setLayout(null);
        CO1Label.setPreferredSize(new Dimension(CO_w, CO_h));

        CO1Panel = new JPanel(new GridBagLayout());
        CO1Title = new JTextField(" The first common objective ");
        CO1Title.setBorder(null);
        CO1Title.setMinimumSize(new Dimension(textCols * textCharsNum, textCols));
        CO1Title.setEditable(false);

        CO2Label = new JLabel(new ImageIcon(new ImageIcon(board.commonObjective_2.imagePath).getImage().getScaledInstance(CO_w, CO_h, Image.SCALE_SMOOTH)));
        CO2Label.setLayout(null);
        CO2Label.setPreferredSize(new Dimension(CO_w, CO_h));

        CO2Panel = new JPanel(new GridBagLayout());
        CO2Title = new JTextField(" The second common objective ");
        CO2Title.setBorder(null);
        CO2Title.setMinimumSize(new Dimension(textCols * textCharsNum, textCols));
        CO2Title.setEditable(false);

        pointsCO1Label = new JLabel(new ImageIcon (new ImageIcon("assets/scoring tokens/scoring.jpg").getImage().getScaledInstance(pointsDim, pointsDim, Image.SCALE_SMOOTH)));
        pointsCO1Label.setPreferredSize(new Dimension(pointsDim, pointsDim));

        pointsCO2Label = new JLabel(new ImageIcon (new ImageIcon("assets/scoring tokens/scoring.jpg").getImage().getScaledInstance(pointsDim, pointsDim, Image.SCALE_SMOOTH)));
        pointsCO2Label.setPreferredSize(new Dimension(pointsDim, pointsDim));

        chairmanLabel = new JLabel(isChairMan ? new ImageIcon (new ImageIcon("assets/misc/firstplayertoken.png").getImage().getScaledInstance(chairmanDim, chairmanDim, Image.SCALE_SMOOTH)) : new ImageIcon (new ImageIcon("assets/misc/sfondo parquet.jpg").getImage().getScaledInstance(chairmanDim, chairmanDim, Image.SCALE_SMOOTH)));
        chairmanLabel.setPreferredSize(new Dimension(chairmanDim, chairmanDim));

        chairmanPanel = new JPanel(new GridBagLayout());
        chairmanTitle = new JTextField(" Chairman Icon ");
        chairmanTitle.setBorder(null);
        chairmanTitle.setMinimumSize(new Dimension(textCols * textCharsNum, textCols));
        chairmanTitle.setEditable(false);

        chairmanPanel.setVisible(isChairMan);

        infoBox = new JPanel(new GridBagLayout());
        chairmanInfo = new JTextField();
        chairmanInfo.setText(" The chairman is " + chairmanName + " ");
        chairmanInfo.setBorder(null);
        chairmanInfo.setMinimumSize(new Dimension(textCols * (textCharsNum), textCols));
        chairmanInfo.setEditable(false);
        activeTurnInfo = new JTextField();
        activeTurnInfo.setBorder(null);
        activeTurnInfo.setMinimumSize(new Dimension(textCols * (textCharsNum), textCols));
        activeTurnInfo.setEditable(false);
        curPointsInfo = new JTextField();
        curPointsInfo.setBorder(null);
        curPointsInfo.setMinimumSize(new Dimension(textCols * (textCharsNum), textCols));
        curPointsInfo.setEditable(false);
        titleInfo = new JTextField(" INFORMATIONS ABOUT THE GAME ");
        titleInfo.setBorder(null);
        titleInfo.setMinimumSize(new Dimension(textCols * (textCharsNum), textCols));
        titleInfo.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.25;
        infoBox.add(titleInfo,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.25;
        infoBox.add(chairmanInfo,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.25;
        gbc.fill = HORIZONTAL;
        infoBox.add(activeTurnInfo,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.25;
        gbc.fill = NONE;
        infoBox.add(curPointsInfo,gbc);

        //

        //Internals: third level of abstraction
        //...in development

        //Addition: (Hierarchy of panels inside panels)
        //SECOND LEVEL
        //CYAN
        internalPanelSide = new JPanel(new GridBagLayout());
        Insets insets = CO1Label.getInsets();

        CO1Label.add(pointsCO1Label);
        pointsCO1Label.setBounds(insets.left + 85, insets.top + 27, pointsDim, pointsDim);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.anchor = CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        CO1Panel.add(CO1Title, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0;
        gbc.weighty = 0.9;
        CO1Panel.add(CO1Label,gbc);
        gbc.weighty = 0.2;
        gbc.anchor = CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 100;
        gbc.insets = new Insets(25,0,0,0);
        internalPanelSide.add(CO1Panel,gbc);
        gbc.insets = new Insets(generalBorder, generalBorder, generalBorder, generalBorder);

        insets = CO2Label.getInsets();

        CO2Label.add(pointsCO2Label);
        pointsCO2Label.setBounds(insets.left + 85, insets.top + 27, pointsDim, pointsDim);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.anchor = CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        CO2Panel.add(CO2Title, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0;
        gbc.weighty = 0.9;
        CO2Panel.add(CO2Label,gbc);
        gbc.anchor = CENTER;
        gbc.weighty = 0.2;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 100;
        internalPanelSide.add(CO2Panel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.anchor = CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        POPanel.add(POTitle, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0;
        gbc.weighty = 0.9;
        POPanel.add(POLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        gbc.ipady = 100;
        gbc.weighty = 0.2;
        internalPanelSide.add(POPanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.anchor = CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        chairmanPanel.add(chairmanTitle, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0;
        gbc.weighty = 0.9;
        chairmanPanel.add(chairmanLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipadx = 0;
        gbc.ipady = 100;
        gbc.weighty = 0.2;
        internalPanelSide.add(chairmanPanel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.ipadx = 0;
        gbc.ipady = 100;
        gbc.weighty = 0.2;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0,0,25,0);
        internalPanelSide.add(infoBox, gbc);
        gbc.insets = new Insets(generalBorder, generalBorder, generalBorder, generalBorder);
        gbc.weightx = 0.0;

        //GREEN
        internalPanelLow = new JPanel(new GridBagLayout());

        player1Panel = new JPanel(new GridBagLayout());
        //Text
        library1Text = new JTextArea(1, textCols * 2 / 3);
        library1Text.setEditable(false);
        //Library of the player 1
        library1Label = new JLabel(new ImageIcon(new ImageIcon("assets/boards/bookshelf_orth.png").getImage().getScaledInstance(lib_w, lib_h, Image.SCALE_SMOOTH)));
        library1Label.setLayout(null);
        library1Label.setPreferredSize(new Dimension(lib_w, lib_h));

        player2Panel = new JPanel(new GridBagLayout());

        //Text
        library2Text = new JTextArea(1, textCols * 2 / 3);
        library2Text.setEditable(false);

        //Library of the player 2
        library2Label = new JLabel(new ImageIcon(new ImageIcon("assets/boards/bookshelf_orth.png").getImage().getScaledInstance(lib_w, lib_h, Image.SCALE_SMOOTH)));
        library2Label.setLayout(null);
        library1Label.setPreferredSize(new Dimension(lib_w, lib_h));

        player3Panel = new JPanel(new GridBagLayout());

        //Text
        library3Text = new JTextArea(1, textCols * 2 / 3);
        library3Text.setEditable(false);

        //Library of the player 3
        library3Label = new JLabel(new ImageIcon(new ImageIcon("assets/boards/bookshelf_orth.png").getImage().getScaledInstance(lib_w, lib_h, Image.SCALE_SMOOTH)));
        library3Label.setLayout(null);
        library1Label.setPreferredSize(new Dimension(lib_w, lib_h));

        library1Text.setText(" Library of " + librariesOfOtherPlayers.get(0).name + " ");
        if(numPlayers >= 3)
            library2Text.setText(" Library of " + librariesOfOtherPlayers.get(1).name + " ");
        if(numPlayers >= 4)
            library3Text.setText(" Library of " + librariesOfOtherPlayers.get(2).name + " ");


        if(numPlayers < 4)
            player3Panel.setVisible(false);
        if(numPlayers < 3)
            player2Panel.setVisible(false);


        JLabel tempLabel;
        JPanel tempPanel;
        insets = library1Label.getInsets();
        // fill library
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                tempLabel = new JLabel();
                tempPanel = new JPanel();

                tempLabel.setPreferredSize(new Dimension(cardLib_w, cardLib_h));
                tempLabel.setVisible(false);

                tempPanel.add(tempLabel);
                tempPanel.setPreferredSize(new Dimension(cardLib_w, cardLib_h));
                tempPanel.setBackground(new java.awt.Color(0, 0, 0, 0));
                tempPanel.setOpaque(false);

                tempPanel.setBounds(insets.left + (cardLib_w * j) + 30 + (9 * j), insets.top + (cardLib_h * i) + 18 + (2 * i), cardLib_w, cardLib_h);

                library1Label.add(tempPanel,gbc);
                otherLibrariesCards.get(0)[i][j] = tempLabel;
            }
        }

        insets = library2Label.getInsets();
        // fill library
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                tempLabel = new JLabel();
                tempPanel = new JPanel();

                tempLabel.setPreferredSize(new Dimension(cardLib_w, cardLib_h));
                tempLabel.setVisible(false);

                tempPanel.add(tempLabel);
                tempPanel.setPreferredSize(new Dimension(cardLib_w, cardLib_h));
                tempPanel.setBackground(new java.awt.Color(0, 0, 0, 0));
                tempPanel.setOpaque(false);

                tempPanel.setBounds(insets.left + (cardLib_w * j) + 30 + (9 * j), insets.top + (cardLib_h * i) + 18 + (2 * i), cardLib_w, cardLib_h);

                library2Label.add(tempPanel,gbc);
                otherLibrariesCards.get(1)[i][j] = tempLabel;
            }
        }

        insets = library3Label.getInsets();
        // fill library
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                tempLabel = new JLabel();
                tempPanel = new JPanel();

                tempLabel.setPreferredSize(new Dimension(cardLib_w, cardLib_h));
                tempLabel.setVisible(false);

                tempPanel.add(tempLabel);
                tempPanel.setPreferredSize(new Dimension(cardLib_w, cardLib_h));
                tempPanel.setBackground(new java.awt.Color(0, 0, 0, 0));
                tempPanel.setOpaque(false);

                tempPanel.setBounds(insets.left + (cardLib_w * j) + 30 + (9 * j), insets.top + (cardLib_h * i) + 18 + (2 * i), cardLib_w, cardLib_h);

                library3Label.add(tempPanel,gbc);
                otherLibrariesCards.get(2)[i][j] = tempLabel;
            }
        }

        //player 1

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        player1Panel.add(library1Text,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        player1Panel.add(library1Label,gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.ipady = 0;
        gbc.weightx = 0.3;
        gbc.weighty = 0.0;
        internalPanelLow.add(player1Panel,gbc);
        //player 2
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        player2Panel.add(library2Text,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        player2Panel.add(library2Label,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.ipady = 0;
        gbc.weightx = 0.3;
        gbc.weighty = 0.0;
        internalPanelLow.add(player2Panel,gbc);
        //player 3
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        player3Panel.add(library3Text,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        player3Panel.add(library3Label,gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.ipady = 0;
        gbc.weightx = 0.3;
        gbc.weighty = 0.0;
        internalPanelLow.add(player3Panel,gbc);
        gbc.weightx = 0.0;
        //blue
        internalPanelHigh = new JPanel(new GridBagLayout());
        gameBoardPanel = new JPanel(new GridBagLayout()); //the chairman is just a card in the matrix
        //Text on top of the board
        boardText = new JTextArea(" Board of the Game ");
        boardText.setEditable(false);
        boardLabel = new JLabel(new ImageIcon(new ImageIcon("assets/boards/livingroom.png").getImage().getScaledInstance(boardDim, boardDim, Image.SCALE_SMOOTH)));
        boardLabel.setPreferredSize(new Dimension(boardDim, boardDim));
        boardLabel.setLayout(new GridBagLayout());
        // fill the board
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                tempLabel = new JLabel();
                tempPanel = new JPanel();

                tempLabel.setPreferredSize(new Dimension(cardDimBoard, cardDimBoard));
                tempLabel.setVisible(false);

                gbc.insets = new Insets(0,0,0,0);

                gbc.gridx = j;
                gbc.gridy = i;
                gbc.ipadx = 2;
                gbc.ipady = 2;
                gbc.weightx = 0.0;
                gbc.weighty = 0.0;
                gbc.gridwidth = 1;
                gbc.gridheight = 1;

                if(i == libFullX && j == libFullY) {
                    tempPanel.setLayout(null);
                    gbc.gridwidth = 2;
                    gbc.gridheight = 2;
                }

                tempPanel.add(tempLabel);
                tempPanel.setPreferredSize(new Dimension(cardDimBoard, cardDimBoard));
                tempPanel.setBackground(new java.awt.Color(0, 0, 0, 0));
                tempPanel.setOpaque(false);
                boardLabel.add(tempPanel,gbc);

                if(i == libFullX && j == libFullY){
                    insets = tempPanel.getInsets();
                    tempLabel.setBounds(insets.left + 4, insets.top, cardDimBoard, cardDimBoard);
                }

                boardCards[i][j] = tempLabel;
            }
        }

        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                int finalI = i;
                int finalJ = j;
                boardCards[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(board.getGameBoard()[finalI][finalJ].color == EMPTY || !name.equals(activeName) || board.isBoardUnplayable())
                            return;
                        int index = getCardIndex(finalI, finalJ);
                        if(index == -1){
                            if(cardsPicked.size() == 6)
                                return;
                            boardCards[finalI][finalJ].setBorder(BorderFactory.createLineBorder(borderColor, cardBorderSize));
                            cardsPicked.add(finalI);
                            cardsPicked.add(finalJ);
                        }
                        else{
                            boardCards[finalI][finalJ].setBorder(BorderFactory.createLineBorder(borderColor, 0));
                            cardsPicked.remove(index);
                            cardsPicked.remove(index);
                        }
                    }
                });
            }
        }

        boardCards[libFullX][libFullY].setIcon(new ImageIcon(new ImageIcon("assets/scoring tokens/end game.jpg").getImage().getScaledInstance(cardDimBoard, cardDimBoard, Image.SCALE_SMOOTH)));
        gbc.insets = new Insets(generalBorder,generalBorder,generalBorder,generalBorder);
        myLibraryPanel = new JPanel(new GridBagLayout());
        //Text on top of my library
        myLibraryText = new JTextArea(" Your personal Library (" + name + ") ");
        myLibraryText.setEditable(false);
        chooseColText = new JTextField();
        chooseColText.setText("Insert col: ");
        chooseColText.setMinimumSize(new Dimension(textCols * textCharsNum, textCols));
        chooseColText.setBorder(null);
        chooseColText.addActionListener(event -> pickCardsBtn.doClick());
        pickCardsBtn = new JButton("Pick Cards");
        pickCardsBtn.setFocusPainted(false);
        pickCardsBtn.setPreferredSize(new Dimension(btnW, btnH));
        pickCardsBtn.addActionListener(e -> new Thread(this::tryToPickCards).start());
        libraryLabel = new JLabel(new ImageIcon(new ImageIcon("assets/boards/bookshelf_orth.png").getImage().getScaledInstance(lib_w, lib_h, Image.SCALE_SMOOTH)));
        libraryLabel.setPreferredSize(new Dimension(lib_w, lib_h));
        libraryLabel.setLayout(null);
        insets = libraryLabel.getInsets();
        // fill library
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                tempLabel = new JLabel();
                tempPanel = new JPanel();

                tempLabel.setPreferredSize(new Dimension(cardLib_w, cardLib_h));
                tempLabel.setVisible(false);

                tempPanel.add(tempLabel);
                tempPanel.setPreferredSize(new Dimension(cardLib_w, cardLib_h));
                tempPanel.setBackground(new java.awt.Color(0, 0, 0, 0));
                tempPanel.setOpaque(false);

                tempPanel.setBounds(insets.left + (cardLib_w * j) + 30 + (9 * j), insets.top + (cardLib_h * i) + 18 + (2 * i), cardLib_w, cardLib_h);

                libraryLabel.add(tempPanel,gbc);
                myLibraryCards[i][j] = tempLabel;
            }
        }
        gbc.insets = new Insets(generalBorder,generalBorder,generalBorder,generalBorder);
        chatPanel = new JPanel(new GridBagLayout());
        chatTitle = new JTextArea(" Chat history of the Game ");
        chatTitle.setEditable(false);
        tempChatHistory = new JTextArea(textCols, textCols - 8);
        tempChatHistory.setMinimumSize(new Dimension(textCols * (textCharsNum), textCols * (textCharsNum)));
        tempChatHistory.setEditable(false);
        chatHistory = new JScrollPane(tempChatHistory);
        chatHistory.setMinimumSize(new Dimension(textCols * (textCharsNum), textCols * (textCharsNum)));
        insertMessage = new JTextField(textCols);
        insertMessage.setMinimumSize(new Dimension(textCols * textCharsNum, textCols));
        insertMessage.setText("Insert the message: ");
        insertMessage.setBorder(null);
        insertMessage.addActionListener(event -> sendMessageBtn.doClick());
        insertPlayer = new JTextField(textCols);
        insertPlayer.setBorder(null);
        insertPlayer.setText("Insert the player: ");
        insertPlayer.setMinimumSize(new Dimension(textCols * textCharsNum, textCols));
        sendMessageBtn = new JButton("Send Message");
        sendMessageBtn.setFocusPainted(false);
        sendMessageBtn.setPreferredSize(new Dimension());
        sendMessageBtn.setPreferredSize(new Dimension(btnW, btnH));
        sendMessageBtn.addActionListener(e -> new Thread(this::sendChatMsg).start());
        //BOARD
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.1;
        gameBoardPanel.add(boardText,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.9;
        gameBoardPanel.add(boardLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.0;
        internalPanelHigh.add(gameBoardPanel,gbc);
        //LIBRARY
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.1;
        myLibraryPanel.add(myLibraryText,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6;
        myLibraryPanel.add(libraryLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.15;
        myLibraryPanel.add(chooseColText,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.15;
        myLibraryPanel.add(pickCardsBtn,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.25;
        internalPanelHigh.add(myLibraryPanel,gbc);
        //CHAT
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        chatPanel.add(chatTitle,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        //gbc.fill = GridBagConstraints.BOTH;
        chatPanel.add(chatHistory,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        //gbc.fill = NONE;
        chatPanel.add(insertPlayer,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        chatPanel.add(insertMessage,gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.0;
        chatPanel.add(sendMessageBtn,gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.25;
        internalPanelHigh.add(chatPanel,gbc);


        //FIRST LEVEL - RED

        generalLabel = new JLabel(new ImageIcon(new ImageIcon("assets/misc/sfondo parquet.jpg").getImage().getScaledInstance(screenSize.width * 5 / 6, screenSize.height * 9 / 10, Image.SCALE_SMOOTH)));
        generalLabel.setPreferredSize(new Dimension(screenSize.width * 5 / 6, screenSize.height * 8 / 10));
        generalLabel.setLayout(new GridBagLayout());

        internalPanelHigh.setBackground(new Color(0, 0, 0, 0));
        internalPanelSide.setBackground(new Color(0, 0, 0, 0));
        internalPanelLow.setBackground(new Color(0, 0, 0, 0));
        //infoBox.setBackground(new Color(0, 0, 0, 0));
        player1Panel.setBackground(new Color(0, 0, 0, 0));
        player2Panel.setBackground(new Color(0, 0, 0, 0));
        player3Panel.setBackground(new Color(0, 0, 0, 0));
        //gameBoardPanel.setBackground(new Color(0, 0, 0, 0));
        //myLibraryPanel.setBackground(new Color(0, 0, 0, 0));
        //chatPanel.setBackground(new Color(0, 0, 0, 0));
        CO1Panel.setBackground(new Color(0, 0, 0, 0));
        CO2Panel.setBackground(new Color(0, 0, 0, 0));
        POPanel.setBackground(new Color(0, 0, 0, 0));
        chairmanPanel.setBackground(new Color(0, 0, 0, 0));


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.8;
        gbc.weighty = 0.6;
        generalLabel.add(internalPanelHigh,gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.8;
        gbc.weighty = 0.4;
        generalLabel.add(internalPanelLow,gbc);


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.8;
        gbc.gridheight = 2;
        generalLabel.add(internalPanelSide,gbc);

        mainFrame.add(generalLabel, BorderLayout.CENTER);
        mainFrame.setSize(screenSize.width * 5 / 6, screenSize.height * 9 / 10);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.pack(); // serve? nel frame che chiede il nome era stato messo e funziona
        mainFrame.setVisible(true);
        new Thread(this::updateGUI).start();
    }
    /******************************************** RMI ***************************************************************/
    public void receivedEventRMI(Message msg){ // funzione principale di attesa
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
    }
    public void sendToServer(Message msg){
        if(netMode == SOCKET) {
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
}