package app.model;

import static app.model.Color.EMPTY;

/*
Cinque colonne di altezza crescente o
decrescente: a partire dalla prima colonna
a sinistra o a destra, ogni colonna successiva
deve essere formata da una tessera in più.
Le tessere possono essere di qualsiasi tipo.
 */
public class Algo_CO_12 extends Strategy {
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

    @Override
    public boolean checkMatch(Card[][] board) {
        return checkLowTriangle_1(board) || checkLowTriangle_2(board) || checkLowTriangle_1(invert(board)) || checkLowTriangle_2(invert(board));
    }
}
