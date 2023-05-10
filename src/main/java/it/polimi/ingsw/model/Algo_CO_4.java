package it.polimi.ingsw.model;

import java.util.ArrayList;

import static it.polimi.ingsw.model.Color.*;

/*
Four lines each formed by 5 tiles of maximum three different types. One line can show the same or a different combination of another line.
 */
/**
 * class which represent the number four objective (common). Immutable
 * @author Ettori Faccincani
 */
public class Algo_CO_4 extends Strategy { // secondo seconda colonna
    /**
     * check if the chosen colon has no empty
     * @author Ettori
     * @param board matrix
     * @param r row
     * @return true iff there is no empty
     */
    private boolean notEmptyOnRow(Card[][] board, int r){
        for(int i = 0; i < COLS; i++){
            if(board[r][i].color == EMPTY)
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
            if (colors.size() >= 1 && colors.size() <= 3 && notEmptyOnRow(board, i))
                count++;
        }
        return count >= 4;
    }
}
