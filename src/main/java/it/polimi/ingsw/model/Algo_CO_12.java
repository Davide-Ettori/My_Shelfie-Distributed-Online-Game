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
     * checks if the array is sorted, strictly
     * @param arr the integer array to check
     * @return true iff the array is sorted with no duplicates
     */
    private boolean checkSort(int[] arr){
        for(int i = 1; i < COLS; i++){
            if(arr[i - 1] + 1 != arr[i])
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