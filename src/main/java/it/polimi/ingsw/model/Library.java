package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

import static it.polimi.ingsw.model.Color.*;

/**
 * class representing each player's private library. Mutable
 * @author Ettori Giammusso
 *
 * */
public class Library implements Serializable {
    /** the rows of the game library */
    public final int ROWS = 6;
    /** the columns of the game library */
    public final int COLS = 5;
    /** the matrix which contains the cards placed by the user */
    public Card[][] gameLibrary = new Card[ROWS][COLS];
    private final int[][] visitedMatrix = new int[ROWS][COLS];
    private int countVisitedCards;
    /** the name of the player using this library */
    public String name;

    /**
     * normal constructor for this type of objects
     * @param n the name of the player using this library
     */
    public Library(String n){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                gameLibrary[i][j] = new Card();
            }
        }
        name = n;
    }

    /**
     * copy constructor for the Library Objects, useful for deep copy
     * @param l the library which you need to copy
     */
    public Library(Library l){ // copy constructor
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                gameLibrary[i][j] = new Card(l.gameLibrary[i][j]);
            }
        }
        countVisitedCards = 0;
        name = l.name;
    }

    /**
     * check if the library is full
     * @author Ettori Giammusso
     * @return true or false, depending on if library is full or not
     */
    public boolean isFull(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(gameLibrary[i][j].color == EMPTY)
                    return false;
            }
        }
        return true;
    }

    /**
     * check if the chosen column have enough space for the cards
     * @author Ettori Giammusso
     * @param col index of column
     * @param numCards number of cards
     * @return true if the cards can stay inside the library column
     */
    public boolean checkCol(int col, int numCards){
        if(col < 0 || col >= COLS)
            return false;
        int freeCard = getFirstFreeCard(col) + 1;
        return freeCard >= numCards;
    }

    /**
     * find the maximum number of cards insertable in the current library
     * @author Ettori Gumus Giammusso
     * @return the maximum number of cards insertable in the current library (int)
     */
    public int maxCardsInsertable(){
        int res = 0;
        for(int i = 0; i < COLS; i++)
            res = Math.max(res, getFirstFreeCard(i) + 1);
        return res;
    }

    /**
     * take the index of the first free cell of the column, starting from the lower position and going up
     * @author Ettori Giammusso
     * @param col index of column
     * @return row index of the first free cell
     */
    private int getFirstFreeCard(int col){
        for(int i = ROWS - 1; i >= 0; i--){
            if(gameLibrary[i][col].color == EMPTY){
                return i;
            }
        }
        return -1;
    }

    /**
     * insert the cards in the library of the current player
     * @author Ettori Giammusso
     * @param col index of the column in which insert the cards
     * @param cards the cards that needs to be physically inserted in the library
     */
    public void insertCards(int col, ArrayList<Card> cards){
        int place = getFirstFreeCard(col);
        for(int i = 0; i < cards.size(); i++){
            gameLibrary[place - i][col] = cards.get(i);
        }
    }

    /**
     * reset the matrix used in the DFS to memorize the visited nodes
     * @author Ettori Giammusso
     */
    private void resetVisitedMatrix(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                visitedMatrix[i][j] = 0;
            }
        }
    }

    /**
     * check if the position in the matrix is valid
     * @author Ettori Giammusso
     * @param x position X
     * @param y position Y
     * @return true if the position is valid
     */
    private boolean indexNotValid(int x, int y){
        return x < 0 || x >= ROWS || y < 0 || y >= COLS;
    }

    /**
     * classic algorithm of in-depth-research (or depth-first search)
     * @author Ettori Giammusso
     * @param i initial X position
     * @param j final Y position
     * @param color color to follow
     */
    private void dfs(int i, int j, Color color) {
        if (indexNotValid(i, j) || gameLibrary[i][j].color != color || visitedMatrix[i][j] == 1)
            return;
        countVisitedCards++;
        visitedMatrix[i][j] = 1;
        dfs(i + 1, j, color);
        dfs(i - 1, j, color);
        dfs(i, j + 1, color);
        dfs(i, j - 1, color);
    }

    /**
     * count the points gained thanks to adjacent cards
     * @author Ettori Giammusso
     * @return the number of points made by this player
     */
    public int countGroupedPoints(){
        int points = 0;
        resetVisitedMatrix();
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(gameLibrary[i][j].color != EMPTY) {
                    countVisitedCards = 0;
                    dfs(i, j, gameLibrary[i][j].color);
                    if(countVisitedCards == 3){
                        points += 2;
                    }
                    else if(countVisitedCards == 4){
                        points += 3;
                    }
                    else if(countVisitedCards == 5){
                        points += 5;
                    }
                    else if(countVisitedCards >= 6){
                        points += 8;
                    }
                }
            }
        }
        return points;
    }

    /**
     * method that draws the library of the owner
     * @author Gumus
     */
    public void draw(){
        System.out.println("\nYour own library (" + name +")");
        System.out.print("   ");
        for (int i = 0; i < COLS; i++)
            System.out.print((i + 1) + "   ");
        System.out.println();
        for (int i = 0; i < ROWS; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < COLS; j++) {
                gameLibrary[i][j].draw();
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * method that draws the library of a player, not the owner
     * @author Gumus
     */
    public void draw(String title){
        System.out.println(title);
        System.out.print("   ");
        for (int i = 0; i < COLS; i++)
            System.out.print((i + 1) + "   ");
        System.out.println();
        for (int i = 0; i < ROWS; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < COLS; j++) {
                gameLibrary[i][j].draw();
                System.out.print(" ");
            }
            System.out.println();
        }

    }
    /**
     * check that the 2 library have the cards with the same color
     * @author Ettori Giammusso
     * @param lib library that need to be checked
     * @return true if the library are equals
     */
    public boolean sameLibraryColor(Library lib){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(gameLibrary[i][j].color != lib.gameLibrary[i][j].color){
                    return false;
                }
            }
        }
        return true;
    }
}