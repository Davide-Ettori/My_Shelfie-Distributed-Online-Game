package app.model;

import static app.model.Color.EMPTY;

/*
Cinque colonne di altezza crescente o
decrescente: a partire dalla prima colonna
a sinistra o a destra, ogni colonna successiva
deve essere formata da una tessera in più.
Le tessere possono essere di qualsiasi tipo.
 */
/**
 * Classe che rappresenta il dodicesimo algoritmo di CO
 * @author Ettori Faccincani
 */

public class Algo_CO_12 extends Strategy {
    /**
     * controlla che ci sia un triangolo, con la diagonale
     * @author Ettor
     * @param: matrice
     * @return: true sse trova il triangolo
     */
    private boolean checkLowTriangle_1(Card[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i >= j && board[i][j].color == EMPTY)
                    return false;
                if (i < j && board[i][j].color != EMPTY)
                    return false;
            }
        }
        return true;
    }
    /**
     * controlla che ci sia un triangolo, senza la diagonale
     * @author Ettori
     * @param: matrice
     * @return: true sse trova il triangolo
     */
    private boolean checkLowTriangle_2(Card[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (i > j && board[i][j].color == EMPTY)
                    return false;
                if (i <= j && board[i][j].color != EMPTY)
                    return false;
            }
        }
        return true;
    }
    /**
     * inverte tutte le righe di una matrice e la returna nuova
     * @author Ettori
     * @param: matrice
     * @return: matrice con le righe invertite
     */
    private Card[][]invert(Card[][] board){
        Card[][] res = new Card[ROWS][COLS];
        int k;
        for(int i = 0; i < ROWS; i++){
            k = 0;
            for(int j = COLS - 1; j >= 0; j--){
                res[i][k++] = board[i][j];
            }
        }
        return res;
    }
    /**
     * controlla se la matrice matcha con l'obbiettivo
     * @author Ettori
     * @param: la matrice della board
     * @return: true sse ha trovato un match
     */
    @Override
    public boolean checkMatch(Card[][] board) {
        return checkLowTriangle_1(board) || checkLowTriangle_2(board) || checkLowTriangle_1(invert(board)) || checkLowTriangle_2(invert(board));
    }
}
