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
 * Classe che rappresenta il primo algoritmo di CO
 * @author Ettori Faccincani
 * immutable
 */
public class Algo_CO_1 extends Strategy {
    private final int[][] visitedMatrix = new int[ROWS][COLS];
    private int countVisitedCards;
    /**
     * classica ricerca in profondità
     * @author Ettori
     * @param: pos x
     * @param: pos y
     * @param: colore
     * @param: matrice della board
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
     * controlla se la matrice matcha con l'obbiettivo
     * @author Ettori
     * @param: la matrice della board
     * @return: true sse ha trovato un match
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
