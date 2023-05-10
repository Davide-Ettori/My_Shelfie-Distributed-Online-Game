package it.polimi.ingsw.model;

import java.util.ArrayList;

/*
Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line.
 */
/**
 * class which represent the number eight objective (common). Immutable
 * @author Ettori Faccincani
 */

public class Algo_CO_8 extends Strategy { // quarto seconda colonna
    /**
     * check if the chosen row has no empty cards
     * @author Ettori
     * @param board matrix
     * @param r row
     * @return true iff there are no empty on the row
     */
    private boolean notEmptyOnRow(Card[][] board, int r){
        for(int i = 0; i < COLS; i++){
            if(board[r][i].color == Color.EMPTY)
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
        for (int i = 0; i < ROWS; i++) {
            colors = new ArrayList<>();
            for (int j = 0; j < COLS; j++) {
                if (!colors.contains(board[i][j].color))
                    colors.add(board[i][j].color);
            }
            if (colors.size() == 5 && notEmptyOnRow(board, i))
                count++;
        }
        return count >= 2;
    }
}
