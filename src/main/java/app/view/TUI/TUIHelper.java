package app.view.TUI;

import app.model.Card;
import app.model.Color;

import static app.model.Color.*;

public class TUIHelper {
    public static boolean active = true;
    /**
     * method which maps every color to a corresponding letter or symbol
     * @author Ettori
     * @param c the color to map
     * @return the char mapped to the input color
     */
    public static char mapColor(Color c){
        if(c == PINK)
            return 'P';
        if(c == CYAN)
            return 'C';
        if(c == BLUE)
            return 'B';
        if(c == GREEN)
            return 'G';
        if(c == YELLOW)
            return 'Y';
        if(c == WHITE)
            return 'W';
        if(c == EMPTY)
            return '#';
        return '?';
    }
    /**
     * method which prints a generic matrix on the user terminal
     * @author Ettori
     * @param mat the matrix to print
     * @param r rows of the matrix
     * @param c columns of the matrix
     * @param title a String which is the title of the given matrix
     */
    public static void drawMatrix(Card[][] mat, int r, int c, String title) {
        System.out.println("\n" + title);
        System.out.print("  ");
        for (int i = 0; i < c; i++)
            System.out.print(i + " ");
        System.out.println();
        for (int i = 0; i < r; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < c; j++)
                System.out.print(mapColor(mat[i][j].color) + " ");
            System.out.println();
        }
        return;
    }
}