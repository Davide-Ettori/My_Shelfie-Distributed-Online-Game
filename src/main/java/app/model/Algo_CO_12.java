package app.model;

import static app.model.Color.EMPTY;

class Algo_CO_12 extends Strategy { // sesto seconda colonna
    private boolean checkLowTriangle(Card[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i >= j && board[i][j].color == EMPTY)
                    return false;
                if (i < j && board[i][j].color != EMPTY)
                    return false;
            }
        }
        return true;
    }

    private boolean checkHighTriangle(Card[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i <= j && board[i][j].color == EMPTY)
                    return false;
                if (i > j && board[i][j].color != EMPTY)
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkMatch(Card[][] board) {
        return checkLowTriangle(board) || checkHighTriangle(board);
    }
}
