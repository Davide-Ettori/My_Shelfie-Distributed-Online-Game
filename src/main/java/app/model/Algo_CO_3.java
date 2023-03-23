package app.model;

import static app.model.Color.EMPTY;
/*
Quattro tessere dello stesso tipo
ai quattro angoli della Libreria.
 */
public class Algo_CO_3 extends Strategy {
    @Override
    public boolean checkMatch(Card[][] board) {
        Color color = board[0][0].color;
        if (color == EMPTY)
            return false;
        return (board[ROWS - 1][COLS - 1].color == color) && (board[0][COLS - 1].color == color) && (board[ROWS - 1][0].color == color);
    }
}
