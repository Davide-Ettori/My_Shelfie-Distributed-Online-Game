package app.model;

import static app.model.Color.*;

public class Algo_CO_11 extends Strategy { // sesto prima colonna
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
        if (color == PINK)
            return 5;
        return -1;
    }

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
