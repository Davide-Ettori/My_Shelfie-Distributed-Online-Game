package app.model;

import java.util.ArrayList;

import static app.model.Color.*;

/*
Quattro righe formate ciascuna
da 5 tessere di uno, due o tre tipi
differenti. Righe diverse possono avere
combinazioni diverse di tipi di tessere.
 */
/**
 * class which represent the number four objective (common)
 * @author Ettori Faccincani
 * immutable
 */
public class Algo_CO_4 extends Strategy { // secondo seconda colonna
    /**
     * check if the chosen colon has no empty
     * @author Ettori
     * @param: matrix
     * @param: row
     * @return: true iff there is no empty
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
     * @param: the matrix of the board
     * @return: true iff it found a match
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
