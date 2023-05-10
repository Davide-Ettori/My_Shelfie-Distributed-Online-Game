package it.polimi.ingsw.model;

/*
Five columns of increasing or decreasing height. Starting from the first column on the left or on the right, each next column must be made of exactly one more tile. Tiles can be of any type.
 */
/**
 * class which represent the number twelve objective (common). Immutable
 * @author Ettori Faccincani
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
                if (i >= j && board[i][j].color == Color.EMPTY)
                    return false;
                if (i < j && board[i][j].color != Color.EMPTY)
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
                if (i > j && board[i][j].color == Color.EMPTY)
                    return false;
                if (i <= j && board[i][j].color != Color.EMPTY)
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
     * checks if the array is sorted, strictly
     * @param arr the interger array to check
     * @return true iff the array is sorted with no duplicates
     */
    private boolean checkSort(int[] arr){
        for(int i = 1; i < COLS; i++){
            if(arr[i - 1] >= arr[i])
                return false;
        }
        return true;
    }
    /**
     * check if the matrix match with the objective
     * @author Ettori
     * @param board the matrix of the board
     * @return true iff it found a match
     */
    @Override
    public boolean checkMatch(Card[][] board) {
        int[] heights_1 = new int[]{0,0,0,0,0};
        int[] heights_2 = new int[]{0,0,0,0,0};
        for(int i = 0; i < COLS; i++){
            for(int j = ROWS - 1; j >= 0; j--){
                if(board[j][i].color != Color.EMPTY) {
                    heights_1[i]++;
                    heights_2[COLS - 1 - i]++;
                }
            }
        }
        return checkSort(heights_1) || checkSort(heights_2);
    }
}
