package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;

import static it.polimi.ingsw.model.Color.EMPTY;
/*
Two groups each containing 4 tiles of the same type in a 2x2 square. The tiles of one square can be different from those of the other square.
 */
/**
 * class which represent the number seven objective (common). Immutable
 * @author Ettori Faccincani
 */
public class Algo_CO_7 extends Strategy { // quarto prima colonna
    private final int[][] visitedMatrix = new int[ROWS][COLS];
    /**
     * classic dfs
     * @author Ettori
     * @param i pos x
     * @param j pos y
     * @param color color
     * @param board matrix of the board
     */
    private void dfs(int i, int j, Color color, Card[][] board) {
        if (!DFSHelper.isIndexValid(i, j) || board[i][j].color != color || DFSHelper.isVisited(i, j, visitedMatrix))
            return;
        dfs(i + 1, j, color, board);
        dfs(i - 1, j, color, board);
        dfs(i, j + 1, color, board);
        dfs(i, j - 1, color, board);
    }
    /**
     * check if it starts a 2x2 square of the same color from the chose cell (high sx)
     * @author Ettori
     * @param x pos x
     * @param y pos y
     * @param color color
     * @param board matrix
     * @return true iff it find a square of the same color
     */
    private boolean checkForSquare(int x, int y, Color color, Card[][] board) {
        ArrayList<Integer> cells = new ArrayList<>(Arrays.asList(x, y, x + 1, y, x, y + 1, x + 1, y + 1));
        for (int i = 0; i < cells.size(); i += 2) {
            if (!DFSHelper.isIndexValid(cells.get(i), cells.get(i + 1)))
                return false;
            if (visitedMatrix[cells.get(i)][cells.get(i + 1)] == 1)
                return false;
            if (board[cells.get(i)][cells.get(i + 1)].color != color)
                return false;
        }
        return true;
    }
    /**
     * check if the matrix match with the objective
     * @author Ettori
     * @param board the matrix of the board
     * @return true iff it found a match
     */
    @Override
    public boolean checkMatch(Card[][] board) {
        int count = 0;
        boolean foundSquare;
        ArrayList<Color> colors = new ArrayList<>();
        DFSHelper.resetVisitedMatrix(visitedMatrix);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].color == EMPTY)
                    continue;
                foundSquare = checkForSquare(i, j, board[i][j].color, board);
                if (foundSquare) {
                    dfs(i, j, board[i][j].color, board);
                    colors.add(board[i][j].color);
                }
            }
        }
        for(int i = 0; i < colors.size() - 1; i++){
            if(colors.subList(i + 1, colors.size()).contains(colors.get(i)))
                return true;
        }
        return false;
    }
}
