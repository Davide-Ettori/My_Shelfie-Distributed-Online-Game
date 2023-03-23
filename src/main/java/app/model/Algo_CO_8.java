package app.model;

import java.util.ArrayList;

import static app.model.Color.EMPTY;

/*
Due righe formate ciascuna
da 5 diversi tipi di tessere.
 */
public class Algo_CO_8 extends Strategy { // quarto seconda colonna
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
            if (colors.size() == 5 && notEmptyOnRow(board, i))
                count++;
        }
        return count >= 2;
    }
}
