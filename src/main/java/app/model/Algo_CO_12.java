package app.model;

import static app.model.Color.EMPTY;

/*
Cinque colonne di altezza crescente o
decrescente: a partire dalla prima colonna
a sinistra o a destra, ogni colonna successiva
deve essere formata da una tessera in più.
Le tessere possono essere di qualsiasi tipo.
 */
/**
 * class which represent the number twelve objective (common)
 * @author Ettori Faccincani
 * immutable
 */
public class Algo_CO_12 extends Strategy {
    /**
     * check for triangle, with diagonal
     * @author Ettori
     * @param board matrix
     * @return true iff it find a triangle
     */
    private boolean checkLowTriangle_1(Card[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i >= j && board[i][j].color == EMPTY)
                    return false;
                if (i < j && board[i][j].color != EMPTY)
                    return false;
            }
        }
        return true;
    }
    /**
     * check for triangle, no diagonal
     * @author Ettori
     * @param board matrix
     * @return true iff it find a triangle
     */
    private boolean checkLowTriangle_2(Card[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i > j && board[i][j].color == EMPTY)
                    return false;
                if (i <= j && board[i][j].color != EMPTY)
                    return false;
            }
        }
        return true;
    }
    /**
     * invert all the rows of a matrix and return a new one
     * @author Ettori
     * @param board matrix
     * @return matrix with all the rows inverted
     */
    private Card[][]invert(Card[][] board){
        Card[][] res = new Card[ROWS][COLS];
        int k;
        for(int i = 0; i < ROWS; i++){
            k = 0;
            for(int j = COLS - 1; j >= 0; j--){
                res[i][k++] = board[i][j];
            }
        }
        return res;
    }
    /**
     * check if the matrix match with the objective
     * @author Ettori
     * @param board the matrix of the board
     * @return true iff it found a match
     */
    @Override
    public boolean checkMatch(Card[][] board) {
        return checkLowTriangle_1(board) || checkLowTriangle_2(board) || checkLowTriangle_1(invert(board)) || checkLowTriangle_2(invert(board));
    }
}
