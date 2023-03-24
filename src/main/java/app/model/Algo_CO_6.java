package app.model;

import java.util.ArrayList;

import static app.model.Color.EMPTY;

/*
Due colonne formate ciascuna
da 6 diversi tipi di tessere.
 */
/**
 * Classe che rappresenta il sesto algoritmo di CO
 * @author Ettori Faccincani
 */
public class Algo_CO_6 extends Strategy { // terzo seconda colonna
    /**
     * controlla che non ci siano empty sulla colonna in questione
     * @author Ettori
     * @param: matrice
     * @param: colonna
     * @return: true sse non ci sono empty sulla colonna
     */
    private boolean notEmptyOnCol(Card[][] board, int c){
        for(int i = 0; i < ROWS; i++){
            if(board[i][c].color == EMPTY)
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
        ArrayList<Color> colors;
        for (int j = 0; j < COLS; j++) {
            colors = new ArrayList<>();
            for (int i = 0; i < ROWS; i++) {
                if (!colors.contains(board[i][j].color))
                    colors.add(board[i][j].color);
            }
            if (colors.size() == 6 && notEmptyOnCol(board, j))
                count++;
        }
        return count >= 2;
    }
}
