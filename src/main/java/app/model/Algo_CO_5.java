package app.model;

import static app.model.Color.EMPTY;
/*
Quattro gruppi separati formati ciascuno
da quattro tessere adiacenti dello stesso
tipo (non necessariamente come mostrato
in figura). Le tessere di un gruppo possono
essere diverse da quelle di un altro gruppo.
 */
/**
 * class which represent the number five objective (common)
 * @author Ettori Faccincani
 * immutable
 */
public class Algo_CO_5 extends Strategy { // terzo prima colonna
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
                if (countVisitedCards >= 4)
                    match++;
            }
        }
        return match >= 4;
    }
}
