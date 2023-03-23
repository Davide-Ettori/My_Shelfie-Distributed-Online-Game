package app.model;

import java.util.ArrayList;
/*
Tre colonne formate ciascuna da
6 tessere di uno, due o tre tipi differenti.
Colonne diverse possono avere
combinazioni diverse di tipi di tessere.
 */
public class Algo_CO_9 extends Strategy { // quinto prima colonna
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
            if (colors.size() <= 3)
                count++;
        }
        return count >= 3;
    }
}
