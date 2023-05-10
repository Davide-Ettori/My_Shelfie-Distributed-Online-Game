package it.polimi.ingsw.model;

import static it.polimi.ingsw.model.Color.EMPTY;
/*
Four tiles of the same type in the four corners of the bookshelf.
 */
/**
 * class which represent the number three objective (common). Immutable
 * @author Ettori Faccincani
 */
public class Algo_CO_3 extends Strategy {
    /**
     * check if the matrix match with the objective
     * @author Ettori
     * @param board the matrix of the board
     * @return true iff it found a match
     */
    @Override
    public boolean checkMatch(Card[][] board) {
        Color color = board[0][0].color;
        if (color == EMPTY)
            return false;
        return (board[ROWS - 1][COLS - 1].color == color) && (board[0][COLS - 1].color == color) && (board[ROWS - 1][0].color == color);
    }
}
