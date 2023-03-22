package app.model;

import static app.model.Color.EMPTY;

class Algo_CO_2 extends Strategy { // primo seconda colonna
    private boolean checkDiagonal(int x, int y, Color color, Card[][] board) {
        for (int i = 0; i < 5; i++) {
            if (!DFSHelper.isIndexValid(x + i, y + i, ROWS, COLS))
                return false;
            if (board[x + i][y + i].color != color)
                return false;
        }
        return true;
    }

    @Override
    public boolean checkMatch(Card[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].color == EMPTY)
                    continue;
                if (checkDiagonal(i, j, board[i][j].color, board))
                    return true;
            }
        }
        return false;
    }
}
