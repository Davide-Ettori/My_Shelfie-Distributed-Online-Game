package app.model;

import java.util.ArrayList;
/*
Due righe formate ciascuna
da 5 diversi tipi di tessere.
 */
public class Algo_CO_8 extends Strategy { // quarto seconda colonna
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
            if (colors.size() == 5)
                count++;
        }
        return count >= 2;
    }
}
