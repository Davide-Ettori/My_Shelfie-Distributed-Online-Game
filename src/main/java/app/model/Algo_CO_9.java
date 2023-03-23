package app.model;

import java.util.ArrayList;

import static app.model.Color.EMPTY;

/*
Tre colonne formate ciascuna da
6 tessere di uno, due o tre tipi differenti.
Colonne diverse possono avere
combinazioni diverse di tipi di tessere.
 */
public class Algo_CO_9 extends Strategy { // quinto prima colonna
    private boolean notEmptyOnCol(Card[][] board, int c){
        for(int i = 0; i < ROWS; i++){
            if(board[i][c].color == EMPTY)
                return false;
        }
        return true;
    }
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
            if (colors.size() >= 1 && colors.size() <= 3 && notEmptyOnCol(board, j))
                count++;
        }
        return count >= 3;
    }
}
