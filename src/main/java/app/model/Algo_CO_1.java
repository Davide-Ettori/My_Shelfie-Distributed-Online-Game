package app.model;

import static app.model.Color.EMPTY;

public class Algo_CO_1 extends Strategy { // primo della prima colonna
    private final int[][] visitedMatrix = new int[ROWS][COLS];
    private int countVisitedCards;

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

    ;
}
