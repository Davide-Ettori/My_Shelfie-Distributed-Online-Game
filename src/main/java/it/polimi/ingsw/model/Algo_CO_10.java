package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;

import static it.polimi.ingsw.model.Color.EMPTY;
/*
Five tiles of the same type forming an X
 */
/**
 * class which represent the number ten objective (common). Immutable
 * @author Ettori Faccincani
 */
public class Algo_CO_10 extends Strategy { // quinto seconda colonna
    /**
     * check if the matrix match with the objective
     * @author Ettori
     * @param board the matrix of the board
     * @return true iff it found a match
     */
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
                    if (!DFSHelper.isIndexValid(cells.get(k), cells.get(k + 1))) {
                        flag = false;
                        continue;
                    }
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
