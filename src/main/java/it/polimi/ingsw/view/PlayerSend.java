package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Library;
import it.polimi.ingsw.model.NetMode;
import it.polimi.ingsw.model.PrivateObjective;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * class which represent the Player object sent with the network
 * @author Ettori Giammusso
 */
public class PlayerSend implements Serializable {
    public String name;
    public PrivateObjective objective;
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
    /** the full chat between the players of the game */
    public String fullChat = "";
    /** the name of the chairman of the game */
    public String chairmanName;
    /** the network mode chosen by the user */
    public NetMode netMode;
    /** the UI mode chosen by the user */
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

    /**
     * copy constructor for the Player object
     * @author Ettori
     * @param p the Player object to copy
     */
    public PlayerSend(Player p){
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
     * copy constructor for the PlayerSend object
     * @author Ettori
     * @param p the Player object to copy
     */
    public PlayerSend(PlayerSend p){
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
}
