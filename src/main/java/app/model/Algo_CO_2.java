package app.model;

import static app.model.Color.EMPTY;
/*
Cinque tessere dello stesso tipo che
formano una diagonale.
 */
/**
 * class which represent the number two objective (common)
 * @author Ettori Faccincani
 * immutable
 */
public class Algo_CO_2 extends Strategy {
    /**
     * check the diagonal from right to left
     * @author Ettori
     * @param: pos x
     * @param: pos y
     * @param: color
     * @param: matrix
     * @return: true iff found the diagonal
     */

    private boolean checkDiagonal_1(int x, int y, Color color, Card[][] board) {
        for (int i = 0; i < 5; i++) {
            if (!DFSHelper.isIndexValid(x + i, y + i, ROWS, COLS))
                return false;
            if (board[x + i][y + i].color != color)
                return false;
        }
        return true;
    }
    /**
     * check the diagonal from left to right
     * @author Ettori
     * @param: pos x
     * @param: pos y
     * @param: color
     * @param: matrix
     * @return: true iff found the diagonal
     */
    private boolean checkDiagonal_2(int x, int y, Color color, Card[][] board) {
        for (int i = 0; i < 5; i++) {
            if (!DFSHelper.isIndexValid(x - i, y + i, ROWS, COLS))
                return false;
            if (board[x - i][y + i].color != color)
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
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].color == EMPTY)
                    continue;
                if (checkDiagonal_1(i, j, board[i][j].color, board) || checkDiagonal_2(i, j, board[i][j].color, board))
                    return true;
            }
        }
        return false;
    }
}
