package app.model;

import java.util.ArrayList;
import java.util.Arrays;

import static app.model.Color.EMPTY;
/*
Due gruppi separati di 4 tessere dello
stesso tipo che formano un quadrato 2x2.
Le tessere dei due gruppi devono essere
dello stesso tipo.
 */
/**
 * Classe che rappresenta il settimo algoritmo di CO
 * @author Ettori Faccincani
 * immutable
 */
public class Algo_CO_7 extends Strategy { // quarto prima colonna
    private final int[][] visitedMatrix = new int[ROWS][COLS];
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
        if (!DFSHelper.isIndexValid(i, j, ROWS, COLS) || board[i][j].color != color || DFSHelper.isVisited(i, j, visitedMatrix))
            return;
        dfs(i + 1, j, color, board);
        dfs(i - 1, j, color, board);
        dfs(i, j + 1, color, board);
        dfs(i, j - 1, color, board);
    }
    /**
     * controlla se dal punto in questione (alto a sx) parte un quadrato 2x2 dello stesso colore
     * @author Ettori
     * @param: pos x
     * @param: pos y
     * @param: colore
     * @param: matrice
     * @return: true sse trova un quadrato dello stesso colore
     */
    private boolean checkForSquare(int x, int y, Color color, Card[][] board) {
        ArrayList<Integer> cells = new ArrayList<>(Arrays.asList(x, y, x + 1, y, x, y + 1, x + 1, y + 1));
        for (int i = 0; i < cells.size(); i += 2) {
            if (!DFSHelper.isIndexValid(cells.get(i), cells.get(i + 1), ROWS, COLS))
                return false;
            if (visitedMatrix[cells.get(i)][cells.get(i + 1)] == 1)
                return false;
            if (board[cells.get(i)][cells.get(i + 1)].color != color)
                return false;
        }
        return true;
    }
    /**
     * controlla se la matrice matcha con l'obbiettivo
     * @author Ettori
     * @param: la matrice della board
     * @return: true sse ha trovato un match
     */
    @Override
    public boolean checkMatch(Card[][] board) {
        int count = 0;
        boolean foundSquare;
        DFSHelper.resetVisitedMatrix(visitedMatrix, ROWS, COLS);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].color == EMPTY)
                    continue;
                foundSquare = checkForSquare(i, j, board[i][j].color, board);
                dfs(i, j, board[i][j].color, board);
                if (foundSquare)
                    count++;
            }
        }
        return count >= 2;
    }
}
