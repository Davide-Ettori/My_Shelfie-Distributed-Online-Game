package it.polimi.ingsw.model;

import static it.polimi.ingsw.model.Color.*;
/*
Eight tiles of the same type. There’s no restriction about the position of these tiles.
 */
/**
 * class which represent the number eleven objective (common). Immutable
 * @author Ettori Faccincani
 */
public class Algo_CO_11 extends Strategy { // sesto prima colonna
    /**
     * map every color to his unique index (integer from 0 to 5)
     * @author Ettori
     * @param color color
     * @return unique index
     */
    private int map(Color color) {
        if (color == GREEN)
            return 0;
        if (color == CYAN)
            return 1;
        if (color == BLUE)
            return 2;
        if (color == YELLOW)
            return 3;
        if (color == WHITE)
            return 4;
        return 5;
    }
    /**
     * check if the matrix match with the objective
     * @author Ettori
     * @param board the matrix of the board
     * @return true iff it found a match
     */
    @Override
    public boolean checkMatch(Card[][] board) {
        int[] colorsCounter = new int[]{0, 0, 0, 0, 0, 0};
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].color == EMPTY)
                    continue;
                colorsCounter[map(board[i][j].color)]++;
            }
        }
        for (int i = 0; i < 6; i++) {
            if (colorsCounter[i] >= 8)
                return true;
        }
        return false;
    }
}
