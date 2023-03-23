package app.model;

import java.util.ArrayList;
import java.util.Arrays;

import static app.model.Color.EMPTY;

public class Algo_CO_10 extends Strategy { // quinto seconda colonna
    @Override
    public boolean checkMatch(Card[][] board) {
        Color color;
        ArrayList<Integer> cells;
        boolean flag;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].color == EMPTY)
                    continue;
                color = board[i][j].color;
                cells = new ArrayList<>(Arrays.asList(i, j, i - 1, j - 1, i + 1, j + 1, i - 1, j + 1, i + 1, j - 1));
                flag = true;
                for (int k = 0; k < cells.size(); k += 2) {
                    if (!DFSHelper.isIndexValid(cells.get(k), cells.get(k + 1), ROWS, COLS))
                        continue;
                    if (board[cells.get(k)][cells.get(k + 1)].color != color)
                        flag = false;
                }
                if (flag)
                    return true;
            }
        }
        return false;
    }
}
