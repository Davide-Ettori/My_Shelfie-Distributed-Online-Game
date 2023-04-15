package app.view.GUI;

import app.IP;
import app.controller.*;
import app.model.*;

import app.view.TUI.PlayerTUI;
import app.view.UIMode;
import org.json.simple.JSONObject;
import playground.socket.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

import static app.controller.MessageType.*;
import static app.controller.NameStatus.*;
import static app.model.State.*;
import static app.view.Dimensions.*;
import static java.awt.GridBagConstraints.*;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * class which represent the player on the client side, mutable,
 * implements Serializable because it will be sent in the socket network
 * @author Ettori Faccincani
 */
public class PlayerGUI extends Player implements Serializable{
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //get the dimension of the screen
    private JFrame mainFrame;
    private final transient BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // da togliere in futuro perchè inutile
    private final GridBagConstraints gbc = new GridBagConstraints();
    private JPanel infoBox, internalPanelLow, internalPanelHigh, internalPanelMid, player1Panel, player2Panel, player3Panel, gameBoardPanel, myLibraryPanel, chatPanel, generalPanel;
    private JLabel POLabel, CO1Label, CO2Label, pointsCO1Label, pointsCO2Label, chairmanLabel, library1Label, library2Label, library3Label, boardLabel, libraryLabel;
    private JTextField chairmanInfo, activeTurnInfo, curPointsInfo, titleInfo, chooseColText, insertMessage, insertPlayer;
    private JTextArea library1Text, library2Text, library3Text, boardText, myLibraryText, chatTitle, tempChatHistory;
    private JScrollPane chatHistory;
    private JButton pickCardsBtn, sendMessageBtn;
    /**
     * Function that update the GUI with the new information
     * @author Ettori Giammusso
     */
    private void updateGUI(){
        pointsCO1Label.setIcon(board.pointsCO_1.size() == 0 ? new ImageIcon (new ImageIcon("assets/scoring tokens/scoring_back_EMPTY.jpg").getImage().getScaledInstance(pointsDim, pointsDim, Image.SCALE_SMOOTH)) : new ImageIcon (new ImageIcon(pathPointsCO + "_" + board.pointsCO_1.peekLast() + ".jpg").getImage().getScaledInstance(pointsDim, pointsDim, Image.SCALE_SMOOTH)));
        pointsCO2Label.setIcon(board.pointsCO_2.size() == 0 ? new ImageIcon (new ImageIcon("assets/scoring tokens/scoring_back_EMPTY.jpg").getImage().getScaledInstance(pointsDim, pointsDim, Image.SCALE_SMOOTH)) : new ImageIcon (new ImageIcon(pathPointsCO + "_" + board.pointsCO_2.peekLast() + ".jpg").getImage().getScaledInstance(pointsDim, pointsDim, Image.SCALE_SMOOTH)));
        activeTurnInfo.setText("The current active player is " + activeName);
        curPointsInfo.setText("You have achieved " + pointsUntilNow + " points until now");
        tempChatHistory.setText(fullChat);

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

        CO1Label = new JLabel(new ImageIcon(new ImageIcon(board.commonObjective_1.imagePath).getImage().getScaledInstance(CO_w, CO_h, Image.SCALE_SMOOTH)));
        //CO1Label.setIcon(); setta la nuova immagine a runtime
        CO1Label.setLayout(new GridBagLayout());
        CO1Label.setPreferredSize(new Dimension(CO_w, CO_h));

        CO2Label = new JLabel(new ImageIcon(new ImageIcon(board.commonObjective_2.imagePath).getImage().getScaledInstance(CO_w, CO_h, Image.SCALE_SMOOTH)));
        CO2Label.setLayout(new GridBagLayout());
        CO2Label.setPreferredSize(new Dimension(CO_w, CO_h));

        pointsCO1Label = new JLabel(new ImageIcon (new ImageIcon("assets/scoring tokens/scoring.jpg").getImage().getScaledInstance(pointsDim, pointsDim, Image.SCALE_SMOOTH)));
        pointsCO1Label.setPreferredSize(new Dimension(pointsDim, pointsDim));

        pointsCO2Label = new JLabel(new ImageIcon (new ImageIcon("assets/scoring tokens/scoring.jpg").getImage().getScaledInstance(pointsDim, pointsDim, Image.SCALE_SMOOTH)));
        pointsCO2Label.setPreferredSize(new Dimension(pointsDim, pointsDim));

        chairmanLabel = new JLabel(isChairMan ? new ImageIcon (new ImageIcon("assets/misc/firstplayertoken.png").getImage().getScaledInstance(chairmanDim, chairmanDim, Image.SCALE_SMOOTH)) : new ImageIcon (new ImageIcon("assets/misc/sfondo parquet.jpg").getImage().getScaledInstance(chairmanDim, chairmanDim, Image.SCALE_SMOOTH)));
        chairmanLabel.setPreferredSize(new Dimension(chairmanDim, chairmanDim));

        infoBox = new JPanel(new GridBagLayout());
        chairmanInfo = new JTextField(textCols - 5);
        chairmanInfo.setText("The chairman of this game is " + chairmanName);
        chairmanInfo.setEditable(false);
        activeTurnInfo = new JTextField(textCols - 5);
        activeTurnInfo.setEditable(false);
        curPointsInfo = new JTextField(textCols - 5);
        curPointsInfo.setEditable(false);
        titleInfo = new JTextField("INFORMATIONS ABOUT THE GAME");
        titleInfo.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        infoBox.add(titleInfo,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        infoBox.add(chairmanInfo,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        infoBox.add(activeTurnInfo,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        infoBox.add(curPointsInfo,gbc);

        //

        //Internals: third level of abstraction
        //...in development

        //Addition: (Hierarchy of panels inside panels)
        //SECOND LEVEL
        //CYAN
        internalPanelLow = new JPanel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 100 + COPointsPadding_x;
        gbc.ipady = 0;
        gbc.anchor = LINE_END;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        CO1Label.add(pointsCO1Label,gbc);
        gbc.weightx = 0.2;
        gbc.anchor = CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.ipady = 0;
        internalPanelLow.add(CO1Label,gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 100 + COPointsPadding_x;
        gbc.ipady = 0;
        gbc.anchor = LINE_END;
        gbc.weightx = 1.0;
        CO2Label.add(pointsCO2Label,gbc);
        gbc.anchor = CENTER;
        gbc.weightx = 0.2;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.ipady = 0;
        internalPanelLow.add(CO2Label,gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.ipady = 0;
        gbc.weightx = 0.2;
        internalPanelLow.add(POLabel, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.ipady = 0;
        gbc.weightx = 0.2;
        internalPanelLow.add(chairmanLabel, gbc);
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.ipadx = 100;
        gbc.ipady = 0;
        gbc.weightx = 0.2;
        internalPanelLow.add(infoBox, gbc);
        gbc.weightx = 0.0;

        //GREEN
        internalPanelMid = new JPanel(new GridBagLayout());

        player1Panel = new JPanel(new GridBagLayout());
        //Text
        library1Text = new JTextArea(1, textCols * 2 / 3);
        library1Text.setEditable(false);
        //Library of the player 1
        library1Label = new JLabel(new ImageIcon(new ImageIcon("assets/boards/bookshelf_orth.png").getImage().getScaledInstance(libSecondaryDim, libSecondaryDim, Image.SCALE_SMOOTH)));
        library1Label.setLayout(new GridBagLayout());
        library1Label.setPreferredSize(new Dimension(libSecondaryDim, libSecondaryDim));

        player2Panel = new JPanel(new GridBagLayout());

        //Text
        library2Text = new JTextArea(1, textCols * 2 / 3);
        library2Text.setEditable(false);

        //Library of the player 2
        library2Label = new JLabel(new ImageIcon(new ImageIcon("assets/boards/bookshelf_orth.png").getImage().getScaledInstance(libSecondaryDim, libSecondaryDim, Image.SCALE_SMOOTH)));
        library2Label.setLayout(new GridBagLayout());
        library1Label.setPreferredSize(new Dimension(libSecondaryDim, libSecondaryDim));

        player3Panel = new JPanel(new GridBagLayout());

        //Text
        library3Text = new JTextArea(1, textCols * 2 / 3);
        library3Text.setEditable(false);

        //Library of the player 3
        library3Label = new JLabel(new ImageIcon(new ImageIcon("assets/boards/bookshelf_orth.png").getImage().getScaledInstance(libSecondaryDim, libSecondaryDim, Image.SCALE_SMOOTH)));
        library3Label.setLayout(new GridBagLayout());
        library1Label.setPreferredSize(new Dimension(libSecondaryDim, libSecondaryDim));

        library2Text.setText("Empty Library");
        library3Text.setText("Empty Library");

        library1Text.setText("Library of " + librariesOfOtherPlayers.get(0).name);
        if(numPlayers >= 3)
            library2Text.setText("Library of " + librariesOfOtherPlayers.get(1).name);
        if(numPlayers >= 4)
            library3Text.setText("Library of " + librariesOfOtherPlayers.get(2).name);

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
        internalPanelMid.add(player1Panel,gbc);
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
        internalPanelMid.add(player2Panel,gbc);
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
        internalPanelMid.add(player3Panel,gbc);
        gbc.weightx = 0.0;
        //blue
        internalPanelHigh = new JPanel(new GridBagLayout());
        gameBoardPanel = new JPanel(new GridBagLayout()); //the chairman is just a card in the matrix
        //Text on top of the board
        boardText = new JTextArea("Board of the Game");
        boardText.setEditable(false);
        boardLabel = new JLabel(new ImageIcon(new ImageIcon("assets/boards/livingroom.png").getImage().getScaledInstance(boardDim, boardDim, Image.SCALE_SMOOTH)));
        boardLabel.setPreferredSize(new Dimension(boardDim, boardDim));
        boardLabel.setLayout(new GridBagLayout());
        myLibraryPanel = new JPanel(new GridBagLayout());
        //Text on top of my library
        myLibraryText = new JTextArea("Your personal Library");
        myLibraryText.setEditable(false);
        chooseColText = new JTextField(textCols);
        chooseColText.setText("Insert the column: ");
        pickCardsBtn = new JButton("Pick Cards");
        pickCardsBtn.setPreferredSize(new Dimension(btnW, btnH));
        libraryLabel = new JLabel(new ImageIcon(new ImageIcon("assets/boards/bookshelf_orth.png").getImage().getScaledInstance(libPrimaryDim, libPrimaryDim, Image.SCALE_SMOOTH)));
        libraryLabel.setPreferredSize(new Dimension(libPrimaryDim, libPrimaryDim));
        libraryLabel.setLayout(new GridBagLayout());
        chatPanel = new JPanel(new GridBagLayout());
        chatTitle = new JTextArea("Chat history of the Game");
        chatTitle.setEditable(false);
        tempChatHistory = new JTextArea(textCols / 3, textCols);
        tempChatHistory.setEditable(false);
        chatHistory = new JScrollPane(tempChatHistory);
        insertMessage = new JTextField(textCols);
        insertMessage.setText("Insert the message: ");
        insertPlayer = new JTextField(textCols);
        insertPlayer.setText("Insert the player: ");
        sendMessageBtn = new JButton("Send Message");
        sendMessageBtn.setPreferredSize(new Dimension());
        sendMessageBtn.setPreferredSize(new Dimension(btnW, btnH));
        sendMessageBtn.addActionListener(e -> insertPlayer.setText("click !!!"));
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
        gbc.weightx = 0.0;
        gbc.weighty = 0.6;
        myLibraryPanel.add(libraryLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.0;
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
        gbc.weightx = 0.4;
        gbc.weighty = 0.0;
        chatPanel.add(chatHistory,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.0;
        chatPanel.add(insertPlayer,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 0.0;
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

        generalPanel = new JPanel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 0.8;
        gbc.weighty = 0.5;
        generalPanel.add(internalPanelHigh,gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.3;
        generalPanel.add(internalPanelMid,gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        generalPanel.add(internalPanelLow,gbc);

        mainFrame.add(generalPanel, BorderLayout.CENTER);
        mainFrame.setSize(screenSize.width * 4 / 5, screenSize.height * 4 / 5); // a tutto schermo completo mi dava problemi, così sta a 4/5 delle dimensioni
        mainFrame.setResizable(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.pack(); // serve? nel frame che chiede il nome era stato messo e funziona
        mainFrame.setVisible(true);
        new Thread(this::updateGUI).start();
    }

    /**
     * constructor that copies a generic Player object inside a new PlayerTUI object
     * @param p the Player object to copy, received by the server
     */
    public PlayerGUI(Player p){
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
    public PlayerGUI(NetMode mode, UIMode ui) { // Costruttore iniziale
        uiMode = ui;
        netMode = mode;
        alert("\nSoon you will need to enter your nickname for the game");
        try {
            mySocket = new Socket(IP.LOCAL_HOST, Server.PORT);
            outStream = new ObjectOutputStream(mySocket.getOutputStream());
            inStream = new ObjectInputStream(mySocket.getInputStream());
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
            NameStatus status;
            name = textInput.getText();
            if(name.length() == 0 || name.charAt(0) == '@' || name.equals("all") || name.equals("names")){
                alert("Name invalid, choose another name"); // vedi JavaDoc di alert
                textInput.setText("");
                return;
            }
            try {
                outStream.writeObject(name);
                status = (NameStatus) inStream.readObject();
            }catch(Exception e){throw new RuntimeException(e);};

            if(status == NOT_TAKEN){
                alert("\nName: '" + name + "' accepted by the server!");
                //System.exit(0);
                try {
                    outStream.writeObject(netMode);
                    outStream.writeObject(uiMode);
                }catch(Exception e){throw new RuntimeException(e);}
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
        }catch(Exception e){throw new RuntimeException(e);}
        if(name.equals(chairmanName))
            new Thread(this::waitForMove).start();
        else
            new Thread(this::waitForEvents).start();
    }
    /**
     * function used to wait for notification from the server while the player is NON active
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
     * helper function for handling the turn event notification from the server
     * @author Ettori
     */
    private void handleYourTurnEvent(){
        activeName = name;
        updateGUI();
        waitForMove();
    }
    /**
     * helper function for handling the change event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleChangeTurnEvent(Message msg){
        activeName = (String) msg.getContent();
        updateGUI();
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
        updateGUI();
        alert("\nBoard updated because it was unplayable");
        waitForEvents();
    }
    /**
     * helper function for handling the update game notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleUpdateGameEvent(Message msg){
        try {
            outStream.writeObject(new Message(STOP, null, null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = (JSONObject) msg.getContent();
        board = (Board)jsonObject.get("board");
        for(int i = 0; i < numPlayers; i++){
            if(librariesOfOtherPlayers.get(i).name.equals(msg.getAuthor()))
                librariesOfOtherPlayers.set(i, (Library)jsonObject.get("library"));
        }
        updateGUI();
        alert("\nPlayer: " + msg.getAuthor() + " made his move, now wait for the turn to change (chat disabled)...");
        waitForEvents();
    }
    /**
     * helper function for handling the final score calculation event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleFinalScoreEvent(Message msg){
        alert("\nThe game is finished, this is the final scoreboard:\n\n" + msg.getContent());
        Game.waitForSeconds(5);
        System.exit(0); // il gioco finisce e tutto si chiude forzatamente
    }
    /**
     * helper function for handling the chat message event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleChatEvent(Message msg){
        fullChat += msg.getContent();
        updateGUI();
        waitForEvents();
    }
    /**
     * helper function for handling the achievement of the first common objective event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleCO_1Event(Message msg){
        alert(msg.getAuthor() + " completed the first common objective getting " + msg.getContent() + " points");
        Game.waitForSeconds(2.5);
        waitForEvents();
    }
    /**
     * helper function for handling the achievement of the second common objective event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleCO_2Event(Message msg){
        alert(msg.getAuthor() + " completed the second common objective getting " + msg.getContent() + " points");
        Game.waitForSeconds(2.5);
        waitForEvents();
    }
    /**
     * helper function for handling the completion of the library event notification from the server
     * @author Ettori
     * @param msg the message containing the necessary information for reacting to the event
     */
    private void handleLibFullEvent(Message msg){
        alert(msg.getAuthor() + " completed the library, the game will continue until the next turn of " + chairmanName);
        Game.waitForSeconds(2.5);
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

        updateGUI();

        boolean change = checkCO();
        change = change || checkLibFull();
        if(change)
            updateGUI();

        sendDoneMove();
    }
    /**
     * helper method used for getting the cards chosen by the user (coordinates)
     * @author Ettori
     * @return the list og the cards chosen by the player
     */
    private ArrayList<Integer> selectCards(){
        String coordString;
        String[] rawCoords;
        ArrayList<Integer> coords;
        while(true){
            System.out.print("\nInsert coordinates of the cards to pick (or @ for chat):\n");
            try {
                coordString = br.readLine();
            } catch (IOException e) {
                alert("errore");
                throw new RuntimeException(e);
            }
            if(coordString.length() == 0)
                continue;
            rawCoords = coordString.split(" ");

            if(coordString.charAt(0) == '@'){
                sendChatMsg(coordString);
                continue;
            }
            if(!checkRawCoords(rawCoords)){
                alert("\nInvalid selection");
                continue;
            }
            coords = new ArrayList<>();
            for(int i = 0; i < rawCoords.length; i += 2){
                coords.add(Integer.parseInt(rawCoords[i]));
                coords.add(Integer.parseInt(rawCoords[i + 1]));
            }
            if(board.areCardsPickable(coords) && library.maxCardsInsertable() >= coords.size() / 2)
                break;
            alert("\nInvalid selection");
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
            if(coordOrder.length() == 0 || coordOrder.equals("-1"))
                break;
            if(coordOrder.charAt(0) == '@'){
                sendChatMsg(coordOrder);
                continue;
            }
            try {
                index_1 = Character.getNumericValue(coordOrder.charAt(0));
                index_2 = Character.getNumericValue(coordOrder.charAt(2));
            }catch (Exception e){alert("\nInvalid selection"); continue;}
            if(coordOrder.length() != 3 || !isCharValid(index_1, index_2, coords.size() / 2)){
                alert("\nInvalid selection");
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
            if(column.length() == 0)
                continue;
            if(column.charAt(0) == '@'){
                sendChatMsg(column);
                continue;
            }
            col = Integer.parseInt(column);
            if(library.checkCol(col, size))
                break;
            alert("\nInvalid selection");
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
                alert("\nWell done, you completed the first common objective and you gain " + points + " points (chat disabled)...");
                Game.waitForSeconds(2.5);
                change = true;
            }
            if (board.commonObjective_2.algorithm.checkMatch(library.gameLibrary) && !CO_2_Done) {
                lastIndex = board.pointsCO_2.size() - 1;
                points = board.pointsCO_2.get(lastIndex);
                board.pointsCO_2.remove(lastIndex);
                pointsUntilNow += points;
                CO_2_Done = true;
                outStream.writeObject(new Message(CO_1, name, Integer.toString(points)));
                alert("\nWell done, you completed the second common objective and you gain " + points + " points (chat disabled)...");
                Game.waitForSeconds(2.5);
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
                alert("\nWell done, you are the first player to complete the library, the game will continue until the next turn of " + chairmanName + " (chat disabled)...");
                Game.waitForSeconds(2.5);
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
        board.fillBoard(numPlayers);
        updateGUI();
        alert("\nBoard updated because it was unplayble");
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
        gameStatus = new JSONObject();
        playerStatus = new JSONObject();
        alert("\nYou made your move, now wait for other players to acknowledge it (chat disabled)...");
        gameStatus.put("board", new Board(board));
        gameStatus.put("library", new Library(library));

        try {
            outStream.writeObject(new Message(UPDATE_GAME, name, gameStatus));
            state = NOT_ACTIVE;
            Game.waitForSeconds(5); // aspetto che tutti abbiano il tempo di capire cosa è successo nel turno
            new Thread(() -> { // aspetto un secondo e poi mando la notifica di fine turno
                try {
                    Game.waitForSeconds(1);
                    playerStatus.put("player", new Player(this));
                    outStream.writeObject(new Message(END_TURN, name, playerStatus));
                }catch (Exception e){throw new RuntimeException(e);}
            }).start();

        }catch(Exception e){throw new RuntimeException(e);}

        waitForEvents();
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
        alert("\nThe current order of your cards is: ");
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
    public void sendChatMsg(String msg){
        if(msg.charAt(0) != '@')
            return;
        if(!msg.contains(" "))
            return;
        String dest = msg.substring(1, msg.indexOf(' '));
        msg = msg.substring(msg.indexOf(' '));
        msg = name + " says:" + msg + " (to " + dest + ") at " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + "\n";

        if(!doesPlayerExists(dest) && !dest.equals("all")) {
            alert("\nThe name chosen does not exists");
            return;
        }
        if(dest.equals(name)){
            alert("\nYou can't send chat messages to yourself");
            return;
        }
        fullChat += msg;
        try{
            outStream.writeObject(new Message(CHAT, dest, msg));
        }catch(Exception e){throw new RuntimeException(e);}
    }
    /**
     * prints 12 spaces (rows) to simulate the flush of the terminal
     * @author Gumus
     */
    private void clearScreen(){
        for(int i = 0; i < 12; i++){
            //alert();
        }
    }
    /**
     * helper function for alerting a message to the user (pop-up)
     * @param s the string og the message to show
     */
    private void alert(String s){showMessageDialog(null, s);}

    /**
     * helper function for closing a Swing GUI window
     * @param frame the window to close
     */
    private void closeWindow(JFrame frame){frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));}
}
