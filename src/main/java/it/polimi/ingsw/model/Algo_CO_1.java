package it.polimi.ingsw.model;

import static it.polimi.ingsw.model.Color.EMPTY;
/*
Six groups each containing at least
2 tiles of the same type (not necessarily in the depicted shape).
The tiles of one group can be different from those of another group.
 */

/**
 * class which represent the number one objective (common).
 * Immutable
 * @author Ettori Faccincani
 */

public class Algo_CO_1 extends Strategy {
    private final int[][] visitedMatrix = new int[ROWS][COLS];
    private int countVisitedCards;
    /**
     * classic dfs
     * @author Ettori
     * @param i pos x
     * @param j pos y
     * @param color color
     * @param board matrix of the board
     */
    private void dfs(int i, int j, Color color, Card[][] board) {
        if (!DFSHelper.isIndexValid(i, j) || board[i][j].color != color || visitedMatrix[i][j] == 1)
            return;
        countVisitedCards++;
        visitedMatrix[i][j] = 1;
        dfs(i + 1, j, color, board);
        dfs(i - 1, j, color, board);
        dfs(i, j + 1, color, board);
        dfs(i, j - 1, color, board);
    }
    /**
     * check if the matrix match with the objective
     * @author Ettori
     * @param board the matrix of the board
     * @return true iff it found a match
     */

    @Override
    public boolean checkMatch(Card[][] board) {
        int match = 0;
        DFSHelper.resetVisitedMatrix(visitedMatrix);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].color == EMPTY)
                    continue;
                countVisitedCards = 0;
                dfs(i, j, board[i][j].color, board);
                if (countVisitedCards >= 2)
                    match++;
            }
        }
        return match >= 6;
    }
}
