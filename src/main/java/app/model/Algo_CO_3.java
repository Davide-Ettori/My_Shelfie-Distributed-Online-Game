package app.model;

import static app.model.Color.EMPTY;

class Algo_CO_3 extends Strategy { // secondo prima colonna
    @Override
    public boolean checkMatch(Card[][] board) {
        Color color = board[0][0].color;
        if (color == EMPTY)
            return false;
        return (board[ROWS - 1][COLS - 1].color == color) && (board[0][COLS - 1].color == color) && (board[ROWS - 1][0].color == color);
    }
}
