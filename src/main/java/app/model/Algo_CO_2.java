package app.model;

import static app.model.Color.EMPTY;
/*
Cinque tessere dello stesso tipo che
formano una diagonale.
 */
/**
 * Classe che rappresenta il secondo algoritmo di CO
 * @author Ettori Faccincani
 * immutable
 */
public class Algo_CO_2 extends Strategy {
    /**
     * controlla la diagonale dx to sx
     * @author Ettori
     * @param: pos x
     * @param: pos y
     * @param: colore
     * @param: matrice
     * @return: true sse trova la diagonale
     */

    private boolean checkDiagonal_1(int x, int y, Color color, Card[][] board) {
        for (int i = 0; i < 5; i++) {
            if (!DFSHelper.isIndexValid(x + i, y + i, ROWS, COLS))
                return false;
            if (board[x + i][y + i].color != color)
                return false;
        }
        return true;
    }
    /**
     * controlla la diagonale sx to dx
     * @author Ettori
     * @param: pos x
     * @param: pos y
     * @param: colore
     * @param: matrice
     * @return: true sse trova la diagonale
     */
    private boolean checkDiagonal_2(int x, int y, Color color, Card[][] board) {
        for (int i = 0; i < 5; i++) {
            if (!DFSHelper.isIndexValid(x - i, y + i, ROWS, COLS))
                return false;
            if (board[x - i][y + i].color != color)
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
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j].color == EMPTY)
                    continue;
                if (checkDiagonal_1(i, j, board[i][j].color, board) || checkDiagonal_2(i, j, board[i][j].color, board))
                    return true;
            }
        }
        return false;
    }
}
