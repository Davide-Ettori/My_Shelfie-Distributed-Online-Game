package app.model;

import java.util.ArrayList;

import static app.model.Color.EMPTY;

/*
Due colonne formate ciascuna
da 6 diversi tipi di tessere.
 */
/**
 * class which represent the number six objective (common). Immutable
 * @author Ettori Faccincani
 */
public class Algo_CO_6 extends Strategy { // terzo seconda colonna
    /**
     * check if there are no empty on the chosen column
     * @author Ettori
     * @param board matrix
     * @param c column
     * @return true iff there are no empty on the column
     */
    private boolean notEmptyOnCol(Card[][] board, int c){
        for(int i = 0; i < ROWS; i++){
            if(board[i][c].color == EMPTY)
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
        int count = 0;
        ArrayList<Color> colors;
        for (int j = 0; j < COLS; j++) {
            colors = new ArrayList<>();
            for (int i = 0; i < ROWS; i++) {
                if (!colors.contains(board[i][j].color) && board[i][j].color != EMPTY)
                    colors.add(board[i][j].color);
            }
            System.out.println(colors.size());
            if (colors.size() == 6)
                count++;
        }
        return count >= 2;
    }
}
