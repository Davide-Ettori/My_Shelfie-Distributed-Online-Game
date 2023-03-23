package app.model;

import java.util.ArrayList;

import static app.model.Color.*;

/*
Quattro righe formate ciascuna
da 5 tessere di uno, due o tre tipi
differenti. Righe diverse possono avere
combinazioni diverse di tipi di tessere.
 */
public class Algo_CO_4 extends Strategy { // secondo seconda colonna
    private boolean notEmptyOnRow(Card[][] board, int r){
        for(int i = 0; i < COLS; i++){
            if(board[r][i].color == EMPTY)
                return false;
        }
        return true;
    }
    @Override
    public boolean checkMatch(Card[][] board) {
        int count = 0;
        ArrayList<Color> colors;
        for (int i = 0; i < ROWS; i++) {
            colors = new ArrayList<>();
            for (int j = 0; j < COLS; j++) {
                if (!colors.contains(board[i][j].color))
                    colors.add(board[i][j].color);
            }
            if (colors.size() >= 1 && colors.size() <= 3 && notEmptyOnRow(board, i))
                count++;
        }
        return count >= 4;
    }
}
