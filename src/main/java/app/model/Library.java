package app.model;

import java.util.ArrayList;

import static app.model.Color.EMPTY;
/**
 * class representing each player's private library
 * @author Ettori Giammusso
 * mutable
 * */
public class Library {
    public final int ROWS = 6;
    public final int COLS = 5;
    public Card[][] library = new Card[ROWS][COLS];
    private final int[][] visitedMatrix = new int[ROWS][COLS];
    private int countVisitedCards;

    public Library(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                library[i][j] = new Card();
            }
        }
    }
    public Library(Library l){ // copy constructor
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                library[i][j] = new Card(l.library[i][j]);
            }
        }
        countVisitedCards = 0;
    }

    /**
     * check if the library is full
     * @author Ettori Giammusso
     * @return true or false, depending on if library is full or not
     */
    public boolean isFull(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(library[i][j].color == EMPTY)
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
     * @return true iff the cards can stay inside the library column
     */
    public boolean checkCol(int col, int numCards){
        int freeCard = getFirstFreeCard(col) + 1;
        return freeCard >= numCards;
    }
    /**
     * take the index of the first free cell of the column, starting from the lower position and going up
     * @author Ettori Giammusso
     * @param col index of column
     * @return row index of the first free cell
     */
    private int getFirstFreeCard(int col){
        for(int i = ROWS - 1; i >= 0; i--){
            if(library[i][col].color == EMPTY){
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
            library[place - i][col] = cards.get(i);
        }
        return;
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
     * @return true iff the position is valid
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
        if (indexNotValid(i, j) || library[i][j].color != color || visitedMatrix[i][j] == 1)
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
                if(library[i][j].color != EMPTY) {
                    countVisitedCards = 0;
                    dfs(i, j, library[i][j].color);
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
    public void draw(){return;}
    /**
     * check that the 2 library have the cards with the same color
     * @author Ettori Giammusso
     * @param lib library that need to be checked
     * @return true iff the library are equals
     */
    public boolean sameLibraryColor(Library lib){
        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLS; j++){
                if(library[i][j].color != lib.library[i][j].color){
                    return false;
                }
            }
        }
        return true;
    }
}