package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.TUI.PlayerTUI;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * class which represent the parent of PlayerGUI and PlayerTUI. Immutable
 * @author Ettori Giammusso
 */
public class Player extends UnicastRemoteObject implements Serializable {

    /** timer for pinging the socket from client to server */
    public static final int pingTimeout = 1000 * 10;
    /** variable that chooses from debugging or running */
    public static final boolean showErrors = false;
    protected String name;
    protected PrivateObjective objective;
    /** list of the libraries of all the players in the game */
    public ArrayList<Library> librariesOfOtherPlayers = new ArrayList<>();
    /** the board seen and used by this player */
    public Board board;
    /** the personal library of this player */
    public Library library;
    /** true iff the player is the chairman of this game */
    public boolean isChairMan;
    protected transient ObjectInputStream inStream;
    protected transient ObjectOutputStream outStream;
    protected transient Socket mySocket;
    protected String fullChat = "";

    //The following attributes are shared between playerTUI and PlayerGUI
    /** the name of the chairman of the game */
    public String chairmanName;
    /** the network mode chosen by the user */
    public NetMode netMode;
    /** the UI mode chosen by the user - not sure if necessary */
    public UIMode uiMode;
    /** number of players in this current game */
    public int numPlayers;
    protected boolean CO_1_Done = false;
    protected boolean CO_2_Done = false;
    /** the name of the player currently having his turn */
    public String activeName = "";

    /** points achieved until now with the common objectives */
    public int pointsUntilNow;
    protected boolean endGame = false;
    /** points achieved until now with the common objectives by all the other players: (name, points) */
    public HashMap<String, Integer> pointsMap = new HashMap<>();
    protected boolean closed = false;
    protected transient Thread turnThread = new Thread(() ->{});

    /**
     * constructor used by the server to initializer a base Player object
     * @author Ettori
     * @throws RemoteException exc
     */
    public Player() throws RemoteException {
        super();
    }
    /**
     * copy constructor for the Player object
     * @author Ettori
     * @param p the Player object to copy
     * @throws RemoteException exc
     */
    public Player(Player p) throws RemoteException {
        super();
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
        pointsMap = p.pointsMap;
    }

    /**
     * constructor that built a Player object from an old PlayerSend Object (used for persistence)
     * @param p the old PlayerObject that was saved on the server
     * @author Ettori
     * @throws RemoteException exc
     */
    public Player(PlayerSend p) throws RemoteException {
        super();
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
        pointsMap = p.pointsMap;
    }
    /**
     * Clone the player on the client in the player on the server
     * @author Ettori
     * @param p the Player that will be cloned in the current Object
     */
    public void clone(PlayerTUI p){}
    /**
     * Check if the input by the user is correct
     * @param s array of the coordinates
     * @return true if the input is correct
     * @author Faccincani
     */
    protected boolean checkRawCoords(String[] s) {
        try {
            if (s.length % 2 == 1)
                return false;
            else {
                for (int i = 0; i < s.length; i++) {
                    if (Integer.parseInt(s[i]) < 0 || Integer.parseInt(s[i]) > 9)
                        return false;
                }
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }
    /**
     * check if the name of a player exists in the game (used by the chat)
     * @author Ettori
     * @param name the name to check in this game
     * @return true iff that player actually exists in the current game
     */
    protected boolean doesPlayerExists(String name){
        for(Library lib : librariesOfOtherPlayers){
            if(lib.name.equals(name))
                return true;
        }
        return false;
    }
    /**
     * take the cards from the board and transfer them in the player library
     * @author Ettori
     * @param coord the list of coupled coordinates of the cards that the player want to take from the board
     */
    protected void pickCards(ArrayList<Integer> coord, int col) { // Paired Coordinates. This method will be called when the GUI o the TUI detect a choice made by the player
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < coord.size(); i += 2) {
            cards.add(new Card(board.getGameBoard()[coord.get(i)][coord.get(i + 1)]));
            board.getGameBoard()[coord.get(i)][coord.get(i + 1)] = new Card(); // after a card is picked, it became EMPTY
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
     * setter for the PO
     * @author Ettori
     * @param obj  the PO that needs to be set
     */
    public void setPrivateObjective(PrivateObjective obj) {objective = obj;}
    /**
     * setter for the attribute name
     * @author Ettori
     * @param n the name to set
     */
    public void setName(String n){name = n;}
    /**
     * setter for the attribute isChairMan
     * @author Ettori
     * @param b the boolean to set
     */
    public void setIsChairMan(boolean b){isChairMan = b;}
    /**
     * getter for the socket input stream (from the server)
     * @author Ettori
     * @return the input stream of this player
     */
    public ObjectInputStream getInStream(){return inStream;}
    /**
     * getter for the name
     * @author Ettori Giammusso
     * @return the name of the player
     */
    public String getName() {
        return name;
    }
    /**
     * getter for the isChairMan
     * @author Ettori Giammusso
     * @return if is chairman
     */
    public boolean getIsChairMan() {
        return isChairMan;
    }
    /**
     * getter for the first common objective
     * @author Ettori Giammusso
     * @return the first common objective
     */
    public boolean getCO_1_Done() {
        return CO_1_Done;
    }
    /**
     * getter for the second common objective
     * @author Ettori Giammusso
     * @return the second common objective
     */
    public boolean getCO_2_Done() {
        return CO_2_Done;
    }
    /**
     * getter for the full chat
     * @author Ettori
     * @return the full chat until now
     */
    public String getFullChat() {return fullChat;}
    /**
     * getter for the endGame
     * @author Ettori Giammusso
     * @return if the game is in endGame
     */
    public boolean getEndGame() {return endGame;}
    /**
     * Getter for the private objective
     * @author Ettori
     * @return the private objective of the player
     */
    public PrivateObjective getPrivateObjective(){return objective;}
}
