package it.polimi.ingsw.model;

import java.util.ArrayList;

import static it.polimi.ingsw.model.Color.EMPTY;

/*
Three columns each formed by 6 tiles
of maximum three different types. One column can show the same or a different combination of another column.
 */
/**
 * class which represent the number nine objective (common). Immutable
 * @author Ettori Faccincani
 */
public class Algo_CO_9 extends Strategy { // quinto prima colonna
    /**
     * check that the chosen column has no empty
     * @author Ettori
     * @param board matrix
     * @param c column
     * @return true iff there are no empty on column
     */
    private boolean notEmptyOnCol(Card[][] board, int c){
        for(int i = 0; i < ROWS; i++){
            if(board[i][c].color == EMPTY) {
                return false;
            }
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
            if (colors.size() >= 1 && colors.size() <= 3 && notEmptyOnCol(board, j))
                count++;
        }
        return count >= 3;
    }
}
