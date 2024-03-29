package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.Initializer;

import java.io.Serializable;
import java.util.*;

import static it.polimi.ingsw.model.Color.*;

/**
 * class which represent the common board where the players choose the cards. Mutable
 * @author Ettori Faccincani
 *
 */
public class Board implements Serializable {
    private final int DIM = 9;
    private final Card[][] gameBoard = new Card[DIM][DIM];
    /** the first common objective */
    public CommonObjective commonObjective_1;
    /** the second common objective */
    public CommonObjective commonObjective_2;
    /** the points available for the first common objective */
    public LinkedList<Integer> pointsCO_1;
    /** the points available for the second common objective */
    public LinkedList<Integer> pointsCO_2;
    private ArrayList<Card> bucketOfCards = Initializer.setBucketOfCards();
    private int[][] gameMatrix;
    /** the name of the player owning this board */
    public String name;
    /**
     * normal constructor for this type of object
     * @param numPlayers the number of players in this game
     * @param CO_1 the first common objective chosen
     * @param CO_2 the second common objective chosen
     */
    public Board(int numPlayers, CommonObjective CO_1, CommonObjective CO_2){
        commonObjective_1 = CO_1;
        commonObjective_2 = CO_2;
        if(numPlayers == 2){
            pointsCO_1 = new LinkedList<>(Arrays.asList(4, 8)); // they need to be taken with the method list.pop() --> from right to left
            pointsCO_2 = new LinkedList<>(Arrays.asList(4, 8));
        }
        else if(numPlayers == 3){
            pointsCO_1 = new LinkedList<>(Arrays.asList(4,6,8));
            pointsCO_2 = new LinkedList<>(Arrays.asList(4,6,8));
        }
        else{
            pointsCO_1 = new LinkedList<>(Arrays.asList(2,4,6,8));
            pointsCO_2 = new LinkedList<>(Arrays.asList(2,4,6,8));
        }
    }

    /**
     * copy constructor for this class, used for deep copying objects
     * @param b the object to copy (game board)
     */
    public Board(Board b){
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                gameBoard[i][j] = new Card(b.gameBoard[i][j]);
            }
        }
        commonObjective_1 = b.commonObjective_1;
        commonObjective_2 = b.commonObjective_2;
        pointsCO_1 = new LinkedList<>(b.pointsCO_1);
        pointsCO_2 = new LinkedList<>(b.pointsCO_2);
        bucketOfCards = new ArrayList<>(b.bucketOfCards);
        name = b.name;
        gameMatrix = b.gameMatrix;
    }

    /**
     * getter for the game board
     * @author Ettori
     */
    public Card[][] getGameBoard(){return gameBoard;} // useful getter :-)

    /**
     * getter for the CO 1
     * @author Ettori
     */
    public CommonObjective getCO_1(){return commonObjective_1;}

    /**
     * getter for the CO 2
     * @author Ettori
     */
    public CommonObjective getCO_2(){return commonObjective_2;}

    /**
     * check if the index is valid in the current board
     * @author Ettori
     * @param x pos x
     * @param y pos y
     * @return true iff index is valid
     */
    private boolean isValidIndex(int x, int y){
        return x >= 0 && x < DIM && y >= 0 && y < DIM;
    }

    /**
     * check if the card has other card near
     * @author Ettori
     * @param x pos x
     * @param y pos y
     * @return true iff the card is alone
     */
    private boolean isAlone(int x, int y){
        return (!isValidIndex(x + 1, y) || gameBoard[x + 1][y].color == EMPTY) && ((!isValidIndex(x - 1, y) || gameBoard[x - 1][y].color == EMPTY) || (!isValidIndex(x, y + 1) || gameBoard[x][y + 1].color == EMPTY) || (!isValidIndex(x, y - 1) || gameBoard[x][y - 1].color == EMPTY));
    }

    /**
     * check if the current board is unplayable
     * @author Ettori
     * @return true if it is unplayable
     */
    public boolean isBoardUnplayable() {
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                if(gameBoard[i][j].color != EMPTY && !isAlone(i, j)) // found a not empty and not lonely card, so the board is not unplayable
                    return false;
            }
        }
        // if it's unplayable, call the method fillBoard(number of players)
        return true;
    }

    /**
     * check if the card has at least one free side
     * @author Ettori
     * @param x pos x
     * @param y pos y
     * @return true if it has at least one free side
     */
    public boolean hasOneFreeSide(int x, int y){
        if(!isValidIndex(x , y) || gameBoard[x][y].color == EMPTY) // check if the current card is in a valid spot, then look for near cards
            return false;
        if(isValidIndex(x + 1, y) && gameBoard[x + 1][y].color == EMPTY)
            return true;
        if(isValidIndex(x - 1, y) && gameBoard[x - 1][y].color == EMPTY)
            return true;
        if(isValidIndex(x, y + 1) && gameBoard[x][y + 1].color == EMPTY)
            return true;
        if(isValidIndex(x, y - 1) && gameBoard[x][y - 1].color == EMPTY)
            return true;
        return false;
    }

    /**
     * check if the cards picked are all near one to the other
     * @author Ettori
     * @param coords list of paired coordinates
     * @return true if for each position it exists (at least) one card adjacent to it
     */
    private boolean areCardsNear(ArrayList<Integer> coords){
        boolean flag;
        for(int i = 0; i < coords.size(); i += 2){
            flag = false;
            for(int j = 0; j < coords.size(); j += 2){
                //check if the cards have distance each other equals to 1 (if yes, are near)
                if(Math.abs(coords.get(i) - coords.get(j)) + Math.abs(coords.get(i + 1) - coords.get(j + 1)) == 1)
                    flag = true;
            }
            if(!flag)
                return false;
        }
        return true;
    }

    /**
     * check if the cards picked are in a valid position
     * @author Ettori
     * @param cardPositions list of paired coordinates
     * @return true iff they are in a valid position
     */
    public boolean areCardsPickable(ArrayList<Integer> cardPositions) {
        for(int i = 0; i < cardPositions.size(); i += 2 ){
            if(!hasOneFreeSide(cardPositions.get(i), cardPositions.get(i + 1)))
                return false;
        }
        return cardPositions.size() == 2 || (areCardsAligned(cardPositions) && areCardsNear(cardPositions));
    }

    /**
     * check if the cards are on a straight line
     * @author Ettori
     * @param cardPosition list of paired coordinates
     * @return true iff they are on a straight line
     */
    public boolean areCardsAligned(ArrayList<Integer> cardPosition){
        boolean allInRow = true;
        for(int i = 0; i < cardPosition.size(); i += 2){
            //!equals is null-safe
            if(!Objects.equals(cardPosition.get(i), cardPosition.get(0)))
                allInRow = false;
        }
        boolean allInCol = true;
        for(int i = 1; i < cardPosition.size(); i += 2){
            //!equals is null-safe
            if(!Objects.equals(cardPosition.get(i), cardPosition.get(1)))
                allInCol = false;
        }
        return allInRow || allInCol;
    }

    /**
     * initialize a new board
     * @author Ettori
     * @param numPlayers number of players
     */
    public void initBoard(int numPlayers){
        shuffleCardsBucket();
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++)
                gameBoard[i][j] = new Card();
        }
        fillBoard(numPlayers);
    }

    /**
     * fill the board (start of the game or when it is unplayable)
     * @author Ettori
     * @param numPlayers number of players
     */
    public void fillBoard(int numPlayers){
        gameMatrix = new int[][]{
                {0,0,0,3,4,0,0,0,0},
                {0,0,0,2,2,4,0,0,0},
                {0,0,3,2,2,2,3,0,0},
                {0,4,2,2,2,2,2,2,3},
                {4,2,2,2,2,2,2,2,4},
                {3,2,2,2,2,2,2,4,0},
                {0,0,3,2,2,2,3,0,0},
                {0,0,0,4,2,2,0,0,0},
                {0,0,0,0,4,3,0,0,0}
        };
        Card card;
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++){
                if(gameMatrix[i][j] == 0)
                    continue;
                if(gameBoard[i][j].color == EMPTY && numPlayers >= gameMatrix[i][j]){
                    card = bucketOfCards.get(0);
                    gameBoard[i][j] = new Card(card);
                    bucketOfCards.remove(0); // bucketOfCards gets exchanged with the server through the client
                }
            }
        }
    }

    /**
     * randomize the card array so that we get a random board
     * @author Ettori
     */
    public void shuffleCardsBucket(){
        Random rand = new Random();
        Card temp;
        int j;
        for(int i = 0; i < bucketOfCards.size(); i++){
            j = rand.nextInt(bucketOfCards.size());
            temp = bucketOfCards.get(i);
            bucketOfCards.set(i, bucketOfCards.get(j));
            bucketOfCards.set(j, temp);
        }
    }

    /**
     * setter for the gameBoard (only used by testing)
     * @author Gumus Giammusso
     * @param g the matrix to copy in the board
     */
    public void setGameBoard(Card[][] g){
        for(int i = 0; i < DIM; i++){
            for(int j = 0; j < DIM; j++)
                gameBoard[i][j] = new Card(g[i][j]);
            }
    }

    /**
     * method that print the game board
     * @author Gumus
     */
    public void draw() {
        System.out.println("\nThe main board of the game");
        System.out.print("   ");
        for (int i = 0; i < DIM; i++)
            System.out.print((i + 1) + "   ");
        System.out.println();
        for (int i = 0; i < DIM; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < DIM; j++) {
                gameBoard[i][j].draw();
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("\n---------------------------------------------");
        commonObjective_1.draw(pointsCO_1.size() != 0 ? pointsCO_1.peekLast() : 0);
        System.out.println("\n---------------------------------------------\n");
        commonObjective_2.draw(pointsCO_2.size() != 0 ? pointsCO_2.peekLast() : 0);
        System.out.println("---------------------------------------------\n");
    }
}