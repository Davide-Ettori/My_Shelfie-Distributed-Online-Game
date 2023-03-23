package app.model;

import java.util.ArrayList;

import static app.model.Color.EMPTY;

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

    public boolean isFull(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(library[i][j].color == EMPTY)
                    return false;
            }
        }
        return true;
    }
    public boolean checkCol(int col, int numCards){
        int freeCard = getFirstFreeCard(col) + 1;
        return freeCard >= numCards;
    }
    private int getFirstFreeCard(int col){
        for(int i = ROWS - 1; i >= 0; i--){
            if(library[i][col].color == EMPTY){
                return i;
            }
        }
        return -1;
    }
    public void insertCards(int col, ArrayList<Card> cards){
        int place = getFirstFreeCard(col);
        for(int i = 0; i < cards.size(); i++){
            library[place - i][col] = cards.get(i);
        }
        return;
    }
    private void resetVisitedMatrix(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                visitedMatrix[i][j] = 0;
            }
        }
    }
    private boolean indexNotValid(int x, int y){
        return x < 0 || x >= ROWS || y < 0 || y >= COLS;
    }
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
}