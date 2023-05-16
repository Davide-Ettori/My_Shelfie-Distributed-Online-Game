package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.Dimensions;
import it.polimi.ingsw.view.IP;
import it.polimi.ingsw.view.UIMode;


import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Date;

import static java.awt.Color.*;
import static java.awt.GridBagConstraints.*;
import static javax.swing.JOptionPane.*;

/**
 * class which represent the player on the client side, mutable,
 * implements Serializable because it will be sent in the socket network
 * @author Ettori Faccincani
 */
public class PlayerGUI extends Player implements Serializable, PlayerI {
    private final transient int DIM = 9;
    private final transient int ROWS = 6;
    private final transient int COLS = 5;
    private final transient int cardBorderSize = 2;
    private final transient java.awt.Color borderColor = BLACK;
    private final transient int libFullX = 6;
    private final transient int libFullY = 7;
    private final transient Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final transient GridBagConstraints gbc = new GridBagConstraints();
    private final transient GridBagConstraints gbc2 = new GridBagConstraints();
    private transient JFrame mainFrame;
    private transient JPanel infoBox, internalPanelSide, internalPanelHigh, internalPanelLow, player1Panel, player2Panel, player3Panel, gameBoardPanel, myLibraryPanel, chatPanel, CO1Panel, CO2Panel, POPanel, chairmanPanel, mainPanel, chooseColPanel;
    private transient JLabel POLabel, CO1Label, CO2Label, pointsCO1Label, pointsCO2Label, chairmanLabel, library1Label, library2Label, library3Label, boardLabel, libraryLabel, generalLabel;
    private transient JTextField chairmanInfo, activeTurnInfo, curPointsInfo, titleInfo, insertMessage, insertPlayer, CO1Title, CO2Title, POTitle, chairmanTitle, eventText, textInput;
    private transient JTextArea library1Text, library2Text, library3Text, boardText, myLibraryText, chatTitle, tempChatHistory;
    private transient JRadioButton r1, r2, r3, r4, r5;
    private transient ButtonGroup btnGroup;
    private transient JScrollPane chatHistory;
    private transient JButton pickCardsBtn, sendMessageBtn, sendBtn;

    private final transient JLabel[][] boardCards = new JLabel[DIM][DIM];
    private final transient JLabel[][] myLibraryCards = new JLabel[ROWS][COLS];
    private final transient ArrayList<JLabel[][]> otherLibrariesCards = new ArrayList<>(Arrays.asList(new JLabel[ROWS][COLS], new JLabel[ROWS][COLS], new JLabel[ROWS][COLS]));

    private transient ArrayList<Integer> cardsPicked = new ArrayList<>();
    private transient GameI server;
    private transient ClassLoader classLoader = getClass().getClassLoader();

    /**
     * constructor that copies a generic Player object inside a new PlayerTUI object
     * @param p the Player object to copy, received by the server
     */
    public PlayerGUI(Player p) throws RemoteException {
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
    public PlayerGUI(NetMode mode, String opt) throws RemoteException {
        super();
        screenSize.height = 950;
        screenSize.width = 1600;
        System.setProperty("java.rmi.server.hostname", IP.activeIP);
        uiMode = UIMode.GUI;
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
        }catch (Exception e){alert("\nServer is inactive, try later"); connectionLost(e);}
        System.out.println("\nClient connected");
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/song.wav")));

            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); //lower the volume of 10 decibel

            clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        new Thread(this::showChooseNameWindow).start();
    }
    /**
     * Clone the player on the client in the player on the server
     * @author Ettori
     * @param p the Player that will be cloned in the current Object
     */
    public void clone(PlayerGUI p){
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
     * method that update the information about the game
     * @author Ettori
     */
    private void updateInfo(){
        //Game.waitForSeconds(Game.fastTimer);
        SwingUtilities.invokeLater(() -> {
            pointsCO1Label.setIcon(board.pointsCO_1.size() == 0 ? new ImageIcon (new ImageIcon(classLoader.getResource("scoring tokens/scoring_back_EMPTY.jpg")).getImage().getScaledInstance(Dimensions.pointsDim, Dimensions.pointsDim, Image.SCALE_SMOOTH)) : new ImageIcon (new ImageIcon(classLoader.getResource(Dimensions.pathPointsCO + "_" + board.pointsCO_1.peekLast() + ".jpg")).getImage().getScaledInstance(Dimensions.pointsDim, Dimensions.pointsDim, Image.SCALE_SMOOTH)));
            pointsCO2Label.setIcon(board.pointsCO_2.size() == 0 ? new ImageIcon (new ImageIcon(classLoader.getResource("scoring tokens/scoring_back_EMPTY.jpg")).getImage().getScaledInstance(Dimensions.pointsDim, Dimensions.pointsDim, Image.SCALE_SMOOTH)) : new ImageIcon (new ImageIcon(classLoader.getResource(Dimensions.pathPointsCO + "_" + board.pointsCO_2.peekLast() + ".jpg")).getImage().getScaledInstance(Dimensions.pointsDim, Dimensions.pointsDim, Image.SCALE_SMOOTH)));
            activeTurnInfo.setText(" The active player is " + activeName + " ");
            curPointsInfo.setText(" " + pointsUntilNow + " points achieved until now ");
            tempChatHistory.setText(fullChat);
            mainFrame.revalidate();
            mainFrame.repaint();
        });
    }
    /**
     * method that update the board of the game
     * @author Ettori
     */
    private void updateBoard(){
        SwingUtilities.invokeLater(() ->{
            String path;
            for(int i = 0; i < DIM; i++){
                for(int j = 0; j < DIM; j++){
                    if(i == libFullX && j == libFullY)
                        continue;
                    path = board.getGameBoard()[i][j].imagePath.trim();
                    try {
                        boardCards[i][j].setIcon(new ImageIcon(new ImageIcon(classLoader.getResource(path)).getImage().getScaledInstance(Dimensions.cardDimBoard, Dimensions.cardDimBoard, Image.SCALE_SMOOTH)));
                    }catch(Exception ignored){}
                    boardCards[i][j].setVisible(board.getGameBoard()[i][j].color != it.polimi.ingsw.model.Color.EMPTY);
                }
            }
            boardCards[libFullX][libFullY].setVisible(!endGame);
            mainFrame.revalidate();
            mainFrame.repaint();
        });
    }
    /**
     * method that update the library of the active player
     * @author Ettori
     */
    private void updateMyLibrary(){
        SwingUtilities.invokeLater(() ->{
            for(int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    try {
                        myLibraryCards[i][j].setIcon(new ImageIcon(new ImageIcon(classLoader.getResource(library.gameLibrary[i][j].imagePath)).getImage().getScaledInstance(Dimensions.cardDimBoard, Dimensions.cardDimBoard, Image.SCALE_SMOOTH)));
                    }
                    catch (Exception ignored){}
                    myLibraryCards[i][j].setVisible(library.gameLibrary[i][j].color != it.polimi.ingsw.model.Color.EMPTY);
                }
            }
            mainFrame.revalidate();
            mainFrame.repaint();
            //myLibraryCards[2][2].setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("item tiles/Cornici1.3.png")).getImage().getScaledInstance(Dimensions.cardDimBoard, Dimensions.cardDimBoard, Image.SCALE_SMOOTH)));
        });
        //myLibraryCards[2][2].setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("item tiles/Cornici1.3.png")).getImage().getScaledInstance(Dimensions.cardDimBoard, Dimensions.cardDimBoard, Image.SCALE_SMOOTH)));
    }
    /**
     * method that update the event text of the game, notifying the most recent event
     * @author Ettori
     */
    private void updateEventText(String s){
        SwingUtilities.invokeLater(() ->{
            eventText.setText(s);
            mainFrame.revalidate();
            mainFrame.repaint();
        });
    }
    /**
     * method that update the libraries of all the player except the one which is active
     * @author Ettori
     */
    private void updateOtherLibraries(){
        SwingUtilities.invokeLater(() ->{
            library1Text.setText(" Library of " + librariesOfOtherPlayers.get(0).name + " (" + pointsMap.get(librariesOfOtherPlayers.get(0).name) + " points) ");
            for(int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    try {
                        otherLibrariesCards.get(0)[i][j].setIcon(new ImageIcon(new ImageIcon(classLoader.getResource(librariesOfOtherPlayers.get(0).gameLibrary[i][j].imagePath)).getImage().getScaledInstance(Dimensions.cardDimBoard, Dimensions.cardDimBoard, Image.SCALE_SMOOTH)));
                    }catch(Exception ignored){}
                    otherLibrariesCards.get(0)[i][j].setVisible(librariesOfOtherPlayers.get(0).gameLibrary[i][j].color != it.polimi.ingsw.model.Color.EMPTY);
                }
            }
            if(numPlayers >= 3){
                library2Text.setText(" Library of " + librariesOfOtherPlayers.get(1).name + " (" + pointsMap.get(librariesOfOtherPlayers.get(1).name) + " points) ");
                for(int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        try {
                            otherLibrariesCards.get(1)[i][j].setIcon(new ImageIcon(new ImageIcon(classLoader.getResource(librariesOfOtherPlayers.get(1).gameLibrary[i][j].imagePath)).getImage().getScaledInstance(Dimensions.cardDimBoard, Dimensions.cardDimBoard, Image.SCALE_SMOOTH)));
                        }catch(Exception ignored){}
                        otherLibrariesCards.get(1)[i][j].setVisible(librariesOfOtherPlayers.get(1).gameLibrary[i][j].color != it.polimi.ingsw.model.Color.EMPTY);
                    }
                }
            }
            if(numPlayers >= 4){
                library3Text.setText(" Library of " + librariesOfOtherPlayers.get(2).name + " (" + pointsMap.get(librariesOfOtherPlayers.get(2).name) + " points) ");
                for(int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        try {
                            otherLibrariesCards.get(2)[i][j].setIcon(new ImageIcon(new ImageIcon(classLoader.getResource(librariesOfOtherPlayers.get(2).gameLibrary[i][j].imagePath)).getImage().getScaledInstance(Dimensions.cardDimBoard, Dimensions.cardDimBoard, Image.SCALE_SMOOTH)));
                        }catch (Exception ignored){}
                        otherLibrariesCards.get(2)[i][j].setVisible(librariesOfOtherPlayers.get(2).gameLibrary[i][j].color != it.polimi.ingsw.model.Color.EMPTY);
                    }
                }
            }
            mainFrame.revalidate();
            mainFrame.repaint();
        });
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
        int col = -1;

        if(r1.isSelected())
            col = 1;
        if(r2.isSelected())
            col = 2;
        if(r3.isSelected())
            col = 3;
        if(r4.isSelected())
            col = 4;
        if(r5.isSelected())
            col = 5;
        col--;
        if(cards.size() == 0 || !board.areCardsPickable(cards) || !library.checkCol(col, cards.size() / 2))
            alert("Invalid Selection");
        else{
            activeName = "...";
            pickCards(cards, col);
            updateGUI();
            boolean change_1 = checkCO();
            boolean change_2 = checkLibFull();
            if(change_1 || change_2)
                updateInfo();

            sendDoneMove(); // send the move to the server
            }
        for(int i = 0; i < cards.size(); i += 2)
            boardCards[cards.get(i)][cards.get(i + 1)].setBorder(BorderFactory.createLineBorder(borderColor, 0));
    }
    /**
     * method for choosing the nickname of the player for the future game, implemented with the Swing GUI
     * @author Ettori Giammusso
     */
    private void showChooseNameWindow(){
        mainFrame =  new JFrame("My Shelfie");
        mainPanel = new JPanel(new GridBagLayout());

        //PLACEHOLDERS:
        JPanel tempPanel1 = new JPanel();
        tempPanel1.setPreferredSize(new Dimension(Dimensions.placeholderW, Dimensions.placeholderH));
        tempPanel1.setBackground(new java.awt.Color(0, 0, 0, 0));
        tempPanel1.setOpaque(false);
        JPanel tempPanel2 = new JPanel();
        tempPanel2.setPreferredSize(new Dimension(Dimensions.placeholderW, Dimensions.placeholderH));
        tempPanel2.setBackground(new java.awt.Color(0, 0, 0, 0));
        tempPanel2.setOpaque(false);

        //WALLPAPER:
        //if you want a random wallpaper, uncomment the following line and comment the next line
        //JLabel generalLabelChooseName = new JLabel(new ImageIcon(new ImageIcon("Publisher material/Display_" + (new Random().nextInt(5) + 1) + ".jpg").getImage().getScaledInstance(screenSize.width * 5 / 6, screenSize.height * 9 / 10, Image.SCALE_SMOOTH)));
        JLabel generalLabelChooseName = new JLabel(new ImageIcon(new ImageIcon(classLoader.getResource("Publisher material/Display_1.jpg")).getImage().getScaledInstance(screenSize.width * 5 / 6, screenSize.height * 9 / 10, Image.SCALE_SMOOTH)));
        generalLabelChooseName.setPreferredSize(new Dimension(screenSize.width * 5 / 6, screenSize.height * 8 / 10 + 65));
        generalLabelChooseName.setLayout(new GridBagLayout());

        //my shelfie title
        JLabel myShelfieTitleLabel = new JLabel(new ImageIcon(new ImageIcon(classLoader.getResource("Publisher material/Title 2000x618px.png")).getImage().getScaledInstance(screenSize.width * 4 / 6, screenSize.height * 4 / 10, Image.SCALE_SMOOTH)));

        sendBtn = new JButton("Choose Name"); // button to send the name
        textInput = new JTextField(20); // textbox input from the user

        textInput.setBounds(100, 20, 165, 25);
        textInput.addActionListener(event -> sendBtn.doClick()); // if you press enter on the keyboard, automatically use sendBtn

        sendBtn.addActionListener((event) ->{ // function of event listener
            NameStatus status = null;
            name = textInput.getText();
            if(name.length() == 0 || name.charAt(0) == '@' || name.equals("all") || name.equals("names") || name.equals("...") || name.equals("exit")){
                alert("Name invalid, choose another name");
                textInput.setText("");
                return;
            }
            try {
                outStream.writeObject(name);
                status = (NameStatus) inStream.readObject();
            }catch(Exception e){connectionLost(e);}

            if(status == NameStatus.NOT_TAKEN){
                alert("\nName: '" + name + "' accepted by the server!");
                textInput.setVisible(false);
                sendBtn.setVisible(false);

                gbc2.gridx = 0;
                gbc2.gridy = 3;
                generalLabelChooseName.add(tempPanel1,gbc2);
                gbc2.gridx = 0;
                gbc2.gridy = 4;
                generalLabelChooseName.add(tempPanel1,gbc2);

                new Thread(this::getInitialState).start();
                return;
            }
            if(status == NameStatus.OLD){
                alert("\nName: " + name + " was found in a previous game");
                textInput.setVisible(false);
                sendBtn.setVisible(false);

                gbc2.gridx = 0;
                gbc2.gridy = 3;
                generalLabelChooseName.add(tempPanel1,gbc2);
                gbc2.gridx = 0;
                gbc2.gridy = 4;
                generalLabelChooseName.add(tempPanel1,gbc2);

                new Thread(this::getInitialState).start();
                return;
            }
            if(status == NameStatus.NOT_FOUND){
                alert("\nAnother game is running and your name was not found...");
                System.exit(0);
            }
            if(status == NameStatus.FOUND){
                textInput.setVisible(false);
                sendBtn.setVisible(false);

                gbc2.gridx = 0;
                gbc2.gridy = 3;
                generalLabelChooseName.add(tempPanel1,gbc2);
                gbc2.gridx = 0;
                gbc2.gridy = 4;
                generalLabelChooseName.add(tempPanel1,gbc2);

                new Thread(this::getPreviousState).start();
                return;
            }
            alert("Name Taken, choose another name");
            textInput.setText("");
        });

        gbc2.gridx=0;
        gbc2.gridy=0;
        generalLabelChooseName.add(myShelfieTitleLabel,gbc2);
        gbc2.gridx=0;
        gbc2.gridy=1;
        gbc2.insets = new Insets(0,0,15,0);
        generalLabelChooseName.add(textInput,gbc2);
        gbc2.gridx=0;
        gbc2.gridy=2;
        gbc2.insets = new Insets(0,0,0,0);
        generalLabelChooseName.add(sendBtn,gbc2);
        gbc2.gridx=0;
        gbc2.gridy=0;
        mainPanel.add(generalLabelChooseName,gbc2);

        mainFrame.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
        mainFrame.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.9), (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.9));
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("My Shelfie");
        mainFrame.setIconImage(new ImageIcon(classLoader.getResource("Publisher material/Icon 50x50px.png")).getImage());
        //mainFrame.pack(); // setup of the window
        mainFrame.setLocationRelativeTo(null); //the frame is centered when printed on the screen
        mainFrame.setVisible(true); // show the frame
    }
    /**
     * Receive the status of the player (previously disconnected) from the server and restart the game
     * @author Ettori Faccincani
     */
    private void getPreviousState(){
        PlayerGUI p;
        try {
            alert("Reconnecting to the running game...");
            p = new PlayerGUI((Player)inStream.readObject());
            clone(p);
            Thread th = new Thread(this::initGUI);
            th.start();
            th.join();
            updateInfo();
            if(netMode == NetMode.RMI)
                new Thread(this::listenForEndGame).start();
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
        updateInfo();
        if(netMode == NetMode.SOCKET) {
            new Thread(this::waitForEvents).start();
            new Thread(this::ping).start();
        }
        else
            new Thread(this::pingRMI).start();
    }
    /**
     * Receive the status of the player from the server and attend the start of the game
     * @author Ettori Faccincani
     */
    private void getInitialState(){
        PlayerGUI p;
        try {
            //alert("Be patient, the game will start soon...");
            p = new PlayerGUI((Player)inStream.readObject());
            clone(p);
            activeName = chairmanName;
            Thread th = new Thread(this::initGUI);
            th.start();
            th.join();
            updateInfo();
            if(netMode == NetMode.RMI)
                new Thread(this::listenForEndGame).start();
        }catch(Exception e){connectionLost(e);}
        try {
            server = (GameI) LocateRegistry.getRegistry(IP.activeIP, Initializer.PORT_RMI).lookup("Server");
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
        if(netMode == NetMode.SOCKET) {
            new Thread(this::waitForEvents).start();
            new Thread(this::ping).start();
        }
        else
            new Thread(this::pingRMI).start();
    }
    /**
     * function used to wait for notification from the server while the player is NON active
     * @author Ettori Faccincani
     */
    private void waitForEvents(){
        while(true){
            try {
                Message msg = (Message) inStream.readObject();
                switch (msg.getType()) {
                    case YOUR_TURN -> handleYourTurnEvent();
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
            }catch(Exception e){
                connectionLost(e);
            }
        }
    }
    /**
     * helper function for handling the show event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleShowEvent(Message msg){
        if(msg.getAuthor() != null && msg.getAuthor().equals("win")){
            updateEventText(" The game is finished, look at the results");
            alert((String) msg.getContent());
            Game.waitForSeconds(Game.waitTimer);
            System.exit(0);
        }
        updateEventText(" "  + msg.getContent());
    }
    /**
     * helper function for handling the client (not active) disconnection notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleLostClientEvent(Message msg){
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/disconnect.wav")));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        updateEventText(" Player " + msg.getAuthor() + " disconnected from the game");
    }
    /**
     * helper function for handling the turn event notification from the server
     * @author Ettori
     */
    private void handleYourTurnEvent(){
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/turn.wav")));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        activeName = name;
        if(board.isBoardUnplayable())
            fixUnplayableBoard();
        if(!endGame) {
            updateBoard();
            updateOtherLibraries();
        }
        updateInfo();
        updateEventText(" Your turn is now started, play your move !");
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
        updateEventText(" Now " + activeName + " is playing his turn...");
    }
    /**
     * helper function for handling the unplayble board fixing event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleUpdateUnplayableEvent(Message msg){
        board = (Board) msg.getContent();
        updateBoard();
        updateEventText(" Board updated because it was unplayable");
    }
    /**
     * helper function for handling the update game notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleUpdateGameEvent(Message msg){
        if(netMode == NetMode.SOCKET)
            sendToServer(new Message(MessageType.STOP, null, null));
        PlayerSend p = (PlayerSend) msg.getContent();
        board = p.board;
        for(int i = 0; i < numPlayers - 1; i++){
            if(librariesOfOtherPlayers.get(i).name.equals(msg.getAuthor()))
                librariesOfOtherPlayers.set(i, p.library);
        }
        pointsMap.put(msg.getAuthor(), p.pointsUntilNow);
        if(endGame)
            updateGUI();
        updateEventText(" Player " + msg.getAuthor() + " made his move, now wait for the turn to change...");
    }
    /**
     * helper function for handling the final score calculation event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleFinalScoreEvent(Message msg){
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/ending.wav")));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        updateEventText(" The game is finished, look at the results");
        alert("\nThe game is finished, this is the final scoreboard:\n\n" + msg.getContent());
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
        updateInfo();
    }
    /**
     * helper function for handling the achievement of the first common objective event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleCO_1Event(Message msg){
        updateEventText(" " + msg.getAuthor() + " completed the first common objective getting " + msg.getContent() + " points");
        board.pointsCO_1.remove(board.pointsCO_1.size() - 1);
        updateInfo();
    }
    /**
     * helper function for handling the achievement of the second common objective event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleCO_2Event(Message msg){
        updateEventText(" " + msg.getAuthor() + " completed the second common objective getting " + msg.getContent() + " points");
        board.pointsCO_2.remove(board.pointsCO_2.size() - 1);
        updateInfo();
    }
    /**
     * helper function for handling the completion of the library event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleLibFullEvent(Message msg){
        updateEventText(" " + msg.getAuthor() + " completed the library, the game will continue until the next turn of " + chairmanName);
        endGame = true;
        boardCards[libFullX][libFullY].setVisible(false);
    }
    /**
     * helper function for handling the disconnection event notification from the server (of the active client)
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleDisconnectedEvent(Message msg){
        updateEventText(" The active player (" + msg.getAuthor() + ") disconnected from the game");
        if(netMode == NetMode.SOCKET)
            sendToServer(new Message(MessageType.STOP, null, null));
    }
    /**
     * method that checks if the current player completed a common objective, and in that case notify all the other players (and add the points)
     * @author Ettori
     * @return true iff one of the objectives was completed
     */
    private boolean checkCO(){
        int points, lastIndex;
        boolean change = false;
        try {
            if (board.commonObjective_1.algorithm.checkMatch(library.gameLibrary) && !CO_1_Done && board.pointsCO_1.size() > 0) { // you can't take the CO if you already did
                try {
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/CO1.wav")));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    clip.start();
                }catch(Exception e){
                    e.printStackTrace();
                }
                lastIndex = board.pointsCO_1.size() - 1;
                points = board.pointsCO_1.get(lastIndex);
                board.pointsCO_1.remove(lastIndex);
                pointsUntilNow += points;
                CO_1_Done = true;
                sendToServer(new Message(MessageType.CO_1, name, Integer.toString(points)));
                updateEventText(" Well done, you completed the first common objective and you gain " + points + " points...");
                Game.waitForSeconds(Game.showTimer);
                change = true;
            }
            if (board.commonObjective_2.algorithm.checkMatch(library.gameLibrary) && !CO_2_Done && board.pointsCO_2.size() > 0) {
                try {
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/CO2.wav")));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    clip.start();
                }catch(Exception e){
                    e.printStackTrace();
                }
                lastIndex = board.pointsCO_2.size() - 1;
                points = board.pointsCO_2.get(lastIndex);
                board.pointsCO_2.remove(lastIndex);
                pointsUntilNow += points;
                CO_2_Done = true;
                sendToServer(new Message(MessageType.CO_2, name, Integer.toString(points)));
                updateEventText(" Well done, you completed the second common objective and you gain " + points + " points...");
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
                try {
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(new BufferedInputStream(getClass().getResourceAsStream("/library.wav")));
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    clip.start();
                }catch(Exception e){
                    e.printStackTrace();
                }
                endGame = true;
                pointsUntilNow++;
                sendToServer(new Message(MessageType.LIB_FULL, name, null));
                updateEventText(" Well done, you are the first player to complete the library, the game will continue until the next turn of " + chairmanName + "...");
                updateBoard();
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
        updateGUI();
        updateEventText(" Board updated because it was unplayble");
        try {
            sendToServer(new Message(MessageType.UPDATE_UNPLAYABLE, name, new Board(board)));
        }catch (Exception e){connectionLost(e);}
    }
    /**
     * method that sends the last move done by the current player to all other clients (after the move is done on this player)
     * @author Ettori
     */
    private void sendDoneMove(){
        updateEventText(" You made your move, now wait for other players to acknowledge it...");
        sendToServer(new Message(MessageType.UPDATE_GAME, name, new PlayerSend(this)));
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
            sendToServer(new Message(MessageType.CHAT, dest, msg));
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
        if(closed)
            return;
        closed = true;
        if(Player.showErrors)
            throw new RuntimeException(e);
        else
            new Thread(() -> updateEventText(" The connection was lost and the application is disconnecting...")).start();
        Game.waitForSeconds(Game.waitTimer);
        System.out.println("\nThe connection was lost and the application is disconnecting...\n");
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
    /******************************************** RMI ***************************************************************/
    /**
     * method (called from remote) that is the equivalent of wait for events of the socket version
     * @param msg the message received from the server
     * @author Ettori
     */
    public void receivedEventRMI(Message msg){
        switch (msg.getType()) {
            case YOUR_TURN -> handleYourTurnEvent();
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
        while(true) {
            try {
                msg = (Message) inStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                connectionLost(e);
            }
            if (msg == null)
                connectionLost(new NullPointerException());
            if (msg.getType() != MessageType.FINAL_SCORE)
                connectionLost(new RuntimeException("listenForEndGame method in GUI received a message different than FINAL_SCORE"));
            handleFinalScoreEvent(msg);
        }
    }
    /**
     * method that allow the server ping the RMI client
     * @author Ettori
     */
    public void pingClient(){}
    /**
     * Function that initialize all the GUI
     * @author Ettori Faccincani Giammusso
     */
    public void initGUI(){
        //Creation:
        //External
        //Internals: first level of abstraction


        //Internals: second level of abstraction
        gbc.insets = new Insets(Dimensions.generalBorder, Dimensions.generalBorder, Dimensions.generalBorder, Dimensions.generalBorder);

        //CYAN

        POLabel = new JLabel(new ImageIcon(new ImageIcon(classLoader.getResource(objective.imagePath)).getImage().getScaledInstance(Dimensions.PO_w, Dimensions.PO_h, Image.SCALE_SMOOTH)));
        POLabel.setPreferredSize(new Dimension(Dimensions.PO_w, Dimensions.PO_h));

        POPanel = new JPanel(new GridBagLayout());
        POTitle = new JTextField(" Your private objective ");
        POTitle.setMinimumSize(new Dimension(Dimensions.textCols * Dimensions.textCharsNum, Dimensions.textCols));
        POTitle.setEditable(false);
        POTitle.setBorder(null);

        CO1Label = new JLabel(new ImageIcon(new ImageIcon(classLoader.getResource(board.commonObjective_1.imagePath)).getImage().getScaledInstance(Dimensions.CO_w, Dimensions.CO_h, Image.SCALE_SMOOTH)));
        CO1Label.setLayout(null);
        CO1Label.setPreferredSize(new Dimension(Dimensions.CO_w, Dimensions.CO_h));

        CO1Panel = new JPanel(new GridBagLayout());
        CO1Title = new JTextField(" The first common objective ");
        CO1Title.setBorder(null);
        CO1Title.setMinimumSize(new Dimension(Dimensions.textCols * Dimensions.textCharsNum, Dimensions.textCols));
        CO1Title.setEditable(false);

        CO2Label = new JLabel(new ImageIcon(new ImageIcon(classLoader.getResource(board.commonObjective_2.imagePath)).getImage().getScaledInstance(Dimensions.CO_w, Dimensions.CO_h, Image.SCALE_SMOOTH)));
        CO2Label.setLayout(null);
        CO2Label.setPreferredSize(new Dimension(Dimensions.CO_w, Dimensions.CO_h));

        CO2Panel = new JPanel(new GridBagLayout());
        CO2Title = new JTextField(" The second common objective ");
        CO2Title.setBorder(null);
        CO2Title.setMinimumSize(new Dimension(Dimensions.textCols * Dimensions.textCharsNum, Dimensions.textCols));
        CO2Title.setEditable(false);

        pointsCO1Label = new JLabel(new ImageIcon (new ImageIcon(classLoader.getResource("scoring tokens/scoring.jpg")).getImage().getScaledInstance(Dimensions.pointsDim, Dimensions.pointsDim, Image.SCALE_SMOOTH)));
        pointsCO1Label.setPreferredSize(new Dimension(Dimensions.pointsDim, Dimensions.pointsDim));

        pointsCO2Label = new JLabel(new ImageIcon (new ImageIcon(classLoader.getResource("scoring tokens/scoring.jpg")).getImage().getScaledInstance(Dimensions.pointsDim, Dimensions.pointsDim, Image.SCALE_SMOOTH)));
        pointsCO2Label.setPreferredSize(new Dimension(Dimensions.pointsDim, Dimensions.pointsDim));

        chairmanLabel = new JLabel(isChairMan ? new ImageIcon (new ImageIcon(classLoader.getResource("misc/firstplayertoken.png")).getImage().getScaledInstance(Dimensions.chairmanDim, Dimensions.chairmanDim, Image.SCALE_SMOOTH)) : new ImageIcon (new ImageIcon(classLoader.getResource("misc/sfondo parquet.jpg")).getImage().getScaledInstance(Dimensions.chairmanDim, Dimensions.chairmanDim, Image.SCALE_SMOOTH)));
        chairmanLabel.setPreferredSize(new Dimension(Dimensions.chairmanDim, Dimensions.chairmanDim));

        chairmanPanel = new JPanel(new GridBagLayout());
        chairmanTitle = new JTextField(" Chairman Icon ");
        chairmanTitle.setBorder(null);
        chairmanTitle.setMinimumSize(new Dimension(Dimensions.textCols * Dimensions.textCharsNum, Dimensions.textCols));
        chairmanTitle.setEditable(false);

        chairmanPanel.setVisible(isChairMan);

        infoBox = new JPanel(new GridBagLayout());
        chairmanInfo = new JTextField();
        chairmanInfo.setText(" The chairman is " + chairmanName + " ");
        chairmanInfo.setBorder(null);
        chairmanInfo.setMinimumSize(new Dimension(Dimensions.textCols * (Dimensions.textCharsNum + 2), Dimensions.textCols));
        chairmanInfo.setEditable(false);
        activeTurnInfo = new JTextField();
        activeTurnInfo.setBorder(null);
        activeTurnInfo.setMinimumSize(new Dimension(Dimensions.textCols * (Dimensions.textCharsNum + 2), Dimensions.textCols));
        activeTurnInfo.setEditable(false);
        curPointsInfo = new JTextField();
        curPointsInfo.setBorder(null);
        curPointsInfo.setMinimumSize(new Dimension(Dimensions.textCols * (Dimensions.textCharsNum + 2), Dimensions.textCols));
        curPointsInfo.setEditable(false);
        titleInfo = new JTextField(" INFORMATIONS ABOUT THE GAME ");
        titleInfo.setBorder(null);
        titleInfo.setMinimumSize(new Dimension(Dimensions.textCols * (Dimensions.textCharsNum + 2), Dimensions.textCols));
        titleInfo.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.25;
        infoBox.add(titleInfo,gbc);
        gbc.fill = HORIZONTAL;
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
        infoBox.add(activeTurnInfo,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.25;
        infoBox.add(curPointsInfo,gbc);
        gbc.fill = NONE;

        //

        //Internals: third level of abstraction
        //...in development

        //Addition: (Hierarchy of panels inside panels)
        //SECOND LEVEL
        //CYAN
        internalPanelSide = new JPanel(new GridBagLayout());
        Insets insets = CO1Label.getInsets();

        CO1Label.add(pointsCO1Label);
        pointsCO1Label.setBounds(insets.left + 85, insets.top + 27, Dimensions.pointsDim, Dimensions.pointsDim);

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
        gbc.insets = new Insets(Dimensions.generalBorder, Dimensions.generalBorder, Dimensions.generalBorder, Dimensions.generalBorder);

        insets = CO2Label.getInsets();

        CO2Label.add(pointsCO2Label);
        pointsCO2Label.setBounds(insets.left + 85, insets.top + 27, Dimensions.pointsDim, Dimensions.pointsDim);

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
        gbc.insets = new Insets(Dimensions.generalBorder, Dimensions.generalBorder, Dimensions.generalBorder, Dimensions.generalBorder);
        gbc.weightx = 0.0;

        //GREEN
        internalPanelLow = new JPanel(new GridBagLayout());

        player1Panel = new JPanel(new GridBagLayout());
        //Text
        library1Text = new JTextArea(1, Dimensions.textCols * 2 / 3);
        library1Text.setEditable(false);
        //Library of the player 1
        library1Label = new JLabel(new ImageIcon(new ImageIcon(classLoader.getResource("boards/bookshelf_orth.png")).getImage().getScaledInstance(Dimensions.lib_w, Dimensions.lib_h, Image.SCALE_SMOOTH)));
        library1Label.setLayout(null);
        library1Label.setPreferredSize(new Dimension(Dimensions.lib_w, Dimensions.lib_h));

        player2Panel = new JPanel(new GridBagLayout());

        //Text
        library2Text = new JTextArea(1, Dimensions.textCols * 2 / 3);
        library2Text.setEditable(false);

        //Library of the player 2
        library2Label = new JLabel(new ImageIcon(new ImageIcon(classLoader.getResource("boards/bookshelf_orth.png")).getImage().getScaledInstance(Dimensions.lib_w, Dimensions.lib_h, Image.SCALE_SMOOTH)));
        library2Label.setLayout(null);
        library1Label.setPreferredSize(new Dimension(Dimensions.lib_w, Dimensions.lib_h));

        player3Panel = new JPanel(new GridBagLayout());

        //Text
        library3Text = new JTextArea(1, Dimensions.textCols * 2 / 3);
        library3Text.setEditable(false);

        //Library of the player 3
        library3Label = new JLabel(new ImageIcon(new ImageIcon(classLoader.getResource("boards/bookshelf_orth.png")).getImage().getScaledInstance(Dimensions.lib_w, Dimensions.lib_h, Image.SCALE_SMOOTH)));
        library3Label.setLayout(null);
        library1Label.setPreferredSize(new Dimension(Dimensions.lib_w, Dimensions.lib_h));

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

                tempLabel.setPreferredSize(new Dimension(Dimensions.cardLib_w, Dimensions.cardLib_h));
                tempLabel.setVisible(false);

                tempPanel.add(tempLabel);
                tempPanel.setPreferredSize(new Dimension(Dimensions.cardLib_w, Dimensions.cardLib_h));
                tempPanel.setBackground(new java.awt.Color(0, 0, 0, 0));
                tempPanel.setOpaque(false);

                tempPanel.setBounds(insets.left + (Dimensions.cardLib_w * j) + 30 + (9 * j), insets.top + (Dimensions.cardLib_h * i) + 18 + (2 * i), Dimensions.cardLib_w, Dimensions.cardLib_h);

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

                tempLabel.setPreferredSize(new Dimension(Dimensions.cardLib_w, Dimensions.cardLib_h));
                tempLabel.setVisible(false);

                tempPanel.add(tempLabel);
                tempPanel.setPreferredSize(new Dimension(Dimensions.cardLib_w, Dimensions.cardLib_h));
                tempPanel.setBackground(new java.awt.Color(0, 0, 0, 0));
                tempPanel.setOpaque(false);

                tempPanel.setBounds(insets.left + (Dimensions.cardLib_w * j) + 30 + (9 * j), insets.top + (Dimensions.cardLib_h * i) + 18 + (2 * i), Dimensions.cardLib_w, Dimensions.cardLib_h);

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

                tempLabel.setPreferredSize(new Dimension(Dimensions.cardLib_w, Dimensions.cardLib_h));
                tempLabel.setVisible(false);

                tempPanel.add(tempLabel);
                tempPanel.setPreferredSize(new Dimension(Dimensions.cardLib_w, Dimensions.cardLib_h));
                tempPanel.setBackground(new java.awt.Color(0, 0, 0, 0));
                tempPanel.setOpaque(false);

                tempPanel.setBounds(insets.left + (Dimensions.cardLib_w * j) + 30 + (9 * j), insets.top + (Dimensions.cardLib_h * i) + 18 + (2 * i), Dimensions.cardLib_w, Dimensions.cardLib_h);

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
        gbc.weightx = 0.1;
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
        gbc.weightx = 0.1;
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
        gbc.weightx = 0.1;
        gbc.weighty = 0.0;
        internalPanelLow.add(player3Panel,gbc);

        eventText = new JTextField(120);
        updateEventText(" Last relevant event of the Game ");
        eventText.setMinimumSize(new Dimension(Dimensions.textCols * 45, Dimensions.textCols + 8));
        eventText.setEditable(false);
        eventText.setBorder(null);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.2;
        gbc.gridwidth = 3;
        //gbc.fill = HORIZONTAL;
        gbc.insets = new Insets(0,15,10,0);
        internalPanelLow.add(eventText, gbc);

        //blue
        internalPanelHigh = new JPanel(new GridBagLayout());
        gameBoardPanel = new JPanel(new GridBagLayout()); //the chairman is just a card in the matrix
        //Text on top of the board
        boardText = new JTextArea(" Board of the Game ");
        boardText.setEditable(false);
        boardLabel = new JLabel(new ImageIcon(new ImageIcon(classLoader.getResource("boards/livingroom.png")).getImage().getScaledInstance(Dimensions.boardDim, Dimensions.boardDim, Image.SCALE_SMOOTH)));
        boardLabel.setPreferredSize(new Dimension(Dimensions.boardDim, Dimensions.boardDim));
        boardLabel.setLayout(new GridBagLayout());
        // fill the board
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                tempLabel = new JLabel();
                tempPanel = new JPanel();

                tempLabel.setPreferredSize(new Dimension(Dimensions.cardDimBoard, Dimensions.cardDimBoard));
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
                tempPanel.setPreferredSize(new Dimension(Dimensions.cardDimBoard, Dimensions.cardDimBoard));
                tempPanel.setBackground(new java.awt.Color(0, 0, 0, 0));
                tempPanel.setOpaque(false);
                boardLabel.add(tempPanel,gbc);

                if(i == libFullX && j == libFullY){
                    insets = tempPanel.getInsets();
                    tempLabel.setBounds(insets.left + 4, insets.top, Dimensions.cardDimBoard, Dimensions.cardDimBoard);
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
                        if(board.getGameBoard()[finalI][finalJ].color == it.polimi.ingsw.model.Color.EMPTY || !name.equals(activeName) || board.isBoardUnplayable())
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

        boardCards[libFullX][libFullY].setIcon(new ImageIcon(new ImageIcon(classLoader.getResource("scoring tokens/end game.jpg")).getImage().getScaledInstance(Dimensions.cardDimBoard, Dimensions.cardDimBoard, Image.SCALE_SMOOTH)));
        gbc.insets = new Insets(Dimensions.generalBorder, Dimensions.generalBorder, Dimensions.generalBorder, Dimensions.generalBorder);
        myLibraryPanel = new JPanel(new GridBagLayout());
        //Text on top of my library
        myLibraryText = new JTextArea(" Your personal Library (" + name + ") ");
        myLibraryText.setEditable(false);

        chooseColPanel = new JPanel(new FlowLayout());
        r1 = new JRadioButton("col 1");
        r2 = new JRadioButton("col 2");
        r3 = new JRadioButton("col 3");
        r4 = new JRadioButton("col 4");
        r5 = new JRadioButton("col 5");
        r1.setSelected(true);

        btnGroup = new ButtonGroup();

        btnGroup.add(r1);
        btnGroup.add(r2);
        btnGroup.add(r3);
        btnGroup.add(r4);
        btnGroup.add(r5);

        chooseColPanel.add(r1);
        chooseColPanel.add(r2);
        chooseColPanel.add(r3);
        chooseColPanel.add(r4);
        chooseColPanel.add(r5);

        pickCardsBtn = new JButton("Pick Cards");
        pickCardsBtn.setFocusPainted(false);
        pickCardsBtn.setPreferredSize(new Dimension(Dimensions.btnW, Dimensions.btnH));
        pickCardsBtn.addActionListener(e -> new Thread(this::tryToPickCards).start());
        libraryLabel = new JLabel(new ImageIcon(new ImageIcon(classLoader.getResource("boards/bookshelf_orth.png")).getImage().getScaledInstance(Dimensions.lib_w, Dimensions.lib_h, Image.SCALE_SMOOTH)));
        libraryLabel.setPreferredSize(new Dimension(Dimensions.lib_w, Dimensions.lib_h));
        libraryLabel.setLayout(null);
        insets = libraryLabel.getInsets();
        // fill library
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                tempLabel = new JLabel();
                tempPanel = new JPanel();

                tempLabel.setPreferredSize(new Dimension(Dimensions.cardLib_w, Dimensions.cardLib_h));
                tempLabel.setVisible(false);

                tempPanel.add(tempLabel);
                tempPanel.setPreferredSize(new Dimension(Dimensions.cardLib_w, Dimensions.cardLib_h));
                tempPanel.setBackground(new java.awt.Color(0, 0, 0, 0));
                tempPanel.setOpaque(false);

                tempPanel.setBounds(insets.left + (Dimensions.cardLib_w * j) + 30 + (9 * j), insets.top + (Dimensions.cardLib_h * i) + 18 + (2 * i), Dimensions.cardLib_w, Dimensions.cardLib_h);

                libraryLabel.add(tempPanel,gbc);
                myLibraryCards[i][j] = tempLabel;
            }
        }
        gbc.insets = new Insets(Dimensions.generalBorder, Dimensions.generalBorder, Dimensions.generalBorder, Dimensions.generalBorder);
        chatPanel = new JPanel(new GridBagLayout());
        chatTitle = new JTextArea(" Chat history of the Game ");
        chatTitle.setEditable(false);
        tempChatHistory = new JTextArea(Dimensions.textCols, Dimensions.textCols - 8);
        tempChatHistory.setMinimumSize(new Dimension(Dimensions.textCols * (Dimensions.textCharsNum), Dimensions.textCols * (Dimensions.textCharsNum)));
        tempChatHistory.setEditable(false);
        tempChatHistory.setText(fullChat);
        chatHistory = new JScrollPane(tempChatHistory);
        chatHistory.setMinimumSize(new Dimension(Dimensions.textCols * (Dimensions.textCharsNum), Dimensions.textCols * (Dimensions.textCharsNum)));
        insertMessage = new JTextField(Dimensions.textCols);
        insertMessage.setMinimumSize(new Dimension(Dimensions.textCols * Dimensions.textCharsNum, Dimensions.textCols));
        insertMessage.setText("Insert message: ");
        insertMessage.setBorder(null);
        insertMessage.addActionListener(event -> sendMessageBtn.doClick());
        insertPlayer = new JTextField(Dimensions.textCols);
        insertPlayer.setBorder(null);
        insertPlayer.setText("Insert player (all for everyone) : ");
        insertPlayer.setMinimumSize(new Dimension(Dimensions.textCols * Dimensions.textCharsNum, Dimensions.textCols));
        sendMessageBtn = new JButton("Send Message");
        sendMessageBtn.setFocusPainted(false);
        sendMessageBtn.setPreferredSize(new Dimension());
        sendMessageBtn.setPreferredSize(new Dimension(Dimensions.btnW, Dimensions.btnH));
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
        gbc.weightx = 0.6;
        gbc.weighty = 0.15;
        myLibraryPanel.add(chooseColPanel,gbc);
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
        chatPanel.add(chatHistory,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
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

        generalLabel = new JLabel(new ImageIcon(new ImageIcon(classLoader.getResource("misc/sfondo parquet.jpg")).getImage().getScaledInstance(screenSize.width * 5 / 6, screenSize.height * 9 / 10, Image.SCALE_SMOOTH)));
        generalLabel.setPreferredSize(new Dimension(screenSize.width * 5 / 6, screenSize.height * 8 / 10 + 65));
        generalLabel.setLayout(new GridBagLayout());

        internalPanelHigh.setBackground(new Color(0, 0, 0, 0));
        internalPanelSide.setBackground(new Color(0, 0, 0, 0));
        internalPanelLow.setBackground(new Color(0, 0, 0, 0));
        CO1Panel.setBackground(new Color(0, 0, 0, 0));
        CO2Panel.setBackground(new Color(0, 0, 0, 0));
        POPanel.setBackground(new Color(0, 0, 0, 0));
        chairmanPanel.setBackground(new Color(0, 0, 0, 0));
        player1Panel.setBackground(new Color(0, 0, 0, 0));
        player2Panel.setBackground(new Color(0, 0, 0, 0));
        player3Panel.setBackground(new Color(0, 0, 0, 0));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.8;
        gbc.weighty = 0.5;
        generalLabel.add(internalPanelHigh,gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.8;
        gbc.weighty = 0.3;
        generalLabel.add(internalPanelLow,gbc);


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.8;
        gbc.gridheight = 2;
        generalLabel.add(internalPanelSide,gbc);

        mainPanel.removeAll();//remove all the precedent GUI used to choose the name
        //Now add the new GUI to play the game
        gbc2.gridx=0;
        gbc2.gridy=0;
        mainPanel.add(generalLabel,gbc2);

        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(mainFrame, "Are you sure to close the game ? (progress will be saved)", "Close Window", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                    System.exit(0);
            }
        });
        Game.waitForSeconds(Game.fastTimer);
        new Thread(this::updateGUI).start();
    }
}