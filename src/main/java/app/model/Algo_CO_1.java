package app.model;

import static app.model.Color.EMPTY;
/*
Sei gruppi separati formati ciascuno
da due tessere adiacenti dello stesso tipo
(non necessariamente come mostrato in
figura). Le tessere di un gruppo possono
essere diverse da quelle di un altro gruppo.
 */

/**
 * class which represent the number one objective (common)
 * @author Ettori Faccincani
 * immutable
 */

public class Algo_CO_1 extends Strategy {
    private final int[][] visitedMatrix = new int[ROWS][COLS];
    private int countVisitedCards;
    /**
     * classic dfs
     * @author Ettori
     * @param: pos x
     * @param: pos y
     * @param: color
     * @param: matrix of the board
     * @return: void
     */
    private void dfs(int i, int j, Color color, Card[][] board) {
        if (!DFSHelper.isIndexValid(i, j, ROWS, COLS) || board[i][j].color != color || visitedMatrix[i][j] == 1)
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
     * @param: the matrix of the board
     * @return: true iff it found a match
     */

    @Override
    public boolean checkMatch(Card[][] board) {
        int match = 0;
        DFSHelper.resetVisitedMatrix(visitedMatrix, ROWS, COLS);
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
