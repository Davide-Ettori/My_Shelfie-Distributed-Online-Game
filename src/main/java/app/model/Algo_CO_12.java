package app.model;

import static app.model.Color.EMPTY;

public class Algo_CO_12 extends Strategy { // sesto seconda colonna
    private boolean checkLowTriangle_1(Card[][] board) {
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

    private boolean checkHighTriangle_1(Card[][] board) {
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
    private boolean checkLowTriangle_2(Card[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i > j && board[i][j].color == EMPTY)
                    return false;
                if (i <= j && board[i][j].color != EMPTY)
                    return false;
            }
        }
        return true;
    }

    private boolean checkHighTriangle_2(Card[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i < j && board[i][j].color == EMPTY)
                    return false;
                if (i >= j && board[i][j].color != EMPTY)
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean checkMatch(Card[][] board) {

        return checkLowTriangle_1(board) || checkHighTriangle_1(board) || checkLowTriangle_2(board) || checkHighTriangle_2(board);
    }
}
