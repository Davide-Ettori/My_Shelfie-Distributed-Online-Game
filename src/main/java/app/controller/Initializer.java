package app.controller;

import app.model.Card;

import app.model.CommonObjective;
import app.model.PrivateObjective;

import java.util.ArrayList;
import java.util.Arrays;

import app.model.Algo_CO_1;
import app.model.Algo_CO_2;
import app.model.Algo_CO_3;
import app.model.Algo_CO_4;
import app.model.Algo_CO_5;
import app.model.Algo_CO_6;
import app.model.Algo_CO_7;
import app.model.Algo_CO_8;
import app.model.Algo_CO_9;
import app.model.Algo_CO_10;
import app.model.Algo_CO_11;
import app.model.Algo_CO_12;

import static app.model.Color.*;
/**
 * helper class to initialize all the bucket of cards and objective that are needed for the start of the game.
 * Immutable
 * @author Ettori Faccincani
 *
 */
public class Initializer {
    private static final int ROWS = 6;
    private static final int COLS = 5;
    private static final int cardNum = 22;
    /**
     * return a new full matrix of empty cards - STATIC
     * @author Ettori Faccincani
     * @return the new matrix
     */
    private static Card[][] setEmpty() {
        Card[][] res = new Card[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++)
                res[i][j] = new Card();
        }
        return res;
    }
    /**
     * return the list of initialized private objective - STATIC
     * @author Ettori Faccincani
     * @return the list of all the possible private objective
     */
    public static ArrayList<PrivateObjective> setBucketOfPO() {
        Card[][] mat_1 = setEmpty();
        Card[][] mat_2 = setEmpty();
        Card[][] mat_3 = setEmpty();
        Card[][] mat_4 = setEmpty();
        Card[][] mat_5 = setEmpty();
        Card[][] mat_6 = setEmpty();
        Card[][] mat_7 = setEmpty();
        Card[][] mat_8 = setEmpty();
        Card[][] mat_9 = setEmpty();
        Card[][] mat_10 = setEmpty();
        Card[][] mat_11 = setEmpty();
        Card[][] mat_12 = setEmpty();

        mat_1[0][0] = new Card(PINK);
        mat_1[0][2] = new Card(BLUE);
        mat_1[1][4] = new Card(GREEN);
        mat_1[2][4] = new Card(WHITE);
        mat_1[3][1] = new Card(YELLOW);
        mat_1[5][2] = new Card(BLUE);

        mat_2[1][1] = new Card(PINK);
        mat_2[2][0] = new Card(GREEN);
        mat_2[2][2] = new Card(YELLOW);
        mat_2[3][4] = new Card(WHITE);
        mat_2[4][3] = new Card(CYAN);
        mat_2[5][4] = new Card(BLUE);

        mat_3[1][0] = new Card(BLUE);
        mat_3[1][3] = new Card(YELLOW);
        mat_3[2][2] = new Card(PINK);
        mat_3[3][1] = new Card(GREEN);
        mat_3[3][4] = new Card(CYAN);
        mat_3[5][0] = new Card(WHITE);

        mat_4[0][4] = new Card(YELLOW);
        mat_4[2][0] = new Card(CYAN);
        mat_4[2][2] = new Card(BLUE);
        mat_4[3][3] = new Card(PINK);
        mat_4[4][1] = new Card(WHITE);
        mat_4[4][2] = new Card(GREEN);

        mat_5[1][1] = new Card(CYAN);
        mat_5[3][1] = new Card(BLUE);
        mat_5[3][2] = new Card(WHITE);
        mat_5[4][4] = new Card(PINK);
        mat_5[5][0] = new Card(YELLOW);
        mat_5[5][3] = new Card(GREEN);

        mat_6[0][2] = new Card(CYAN);
        mat_6[0][4] = new Card(GREEN);
        mat_6[2][3] = new Card(WHITE);
        mat_6[4][1] = new Card(YELLOW);
        mat_6[4][3] = new Card(BLUE);
        mat_6[5][0] = new Card(PINK);

        mat_7[0][0] = new Card(GREEN);
        mat_7[1][4] = new Card(BLUE);
        mat_7[2][1] = new Card(PINK);
        mat_7[3][0] = new Card(CYAN);
        mat_7[4][4] = new Card(YELLOW);
        mat_7[5][3] = new Card(WHITE);

        mat_8[0][4] = new Card(BLUE);
        mat_8[1][1] = new Card(GREEN);
        mat_8[2][2] = new Card(CYAN);
        mat_8[3][0] = new Card(PINK);
        mat_8[4][3] = new Card(WHITE);
        mat_8[5][3] = new Card(YELLOW);

        mat_9[0][2] = new Card(YELLOW);
        mat_9[2][2] = new Card(GREEN);
        mat_9[3][4] = new Card(WHITE);
        mat_9[4][1] = new Card(CYAN);
        mat_9[4][4] = new Card(PINK);
        mat_9[5][0] = new Card(BLUE);

        mat_10[0][4] = new Card(CYAN);
        mat_10[1][1] = new Card(YELLOW);
        mat_10[2][0] = new Card(WHITE);
        mat_10[3][3] = new Card(GREEN);
        mat_10[4][1] = new Card(BLUE);
        mat_10[5][3] = new Card(PINK);

        mat_11[0][2] = new Card(PINK);
        mat_11[1][1] = new Card(WHITE);
        mat_11[2][0] = new Card(YELLOW);
        mat_11[3][2] = new Card(BLUE);
        mat_11[4][4] = new Card(GREEN);
        mat_11[5][3] = new Card(CYAN);

        mat_12[0][2] = new Card(WHITE);
        mat_12[1][1] = new Card(PINK);
        mat_12[2][2] = new Card(BLUE);
        mat_12[3][3] = new Card(CYAN);
        mat_12[4][4] = new Card(YELLOW);
        mat_12[5][0] = new Card(GREEN);

        return new ArrayList<>(Arrays.asList(
                new PrivateObjective(mat_1, 1),
                new PrivateObjective(mat_2, 2),
                new PrivateObjective(mat_3, 3),
                new PrivateObjective(mat_4, 4),
                new PrivateObjective(mat_5, 5),
                new PrivateObjective(mat_6, 6),
                new PrivateObjective(mat_7, 7),
                new PrivateObjective(mat_8, 8),
                new PrivateObjective(mat_9, 9),
                new PrivateObjective(mat_10, 10),
                new PrivateObjective(mat_11, 11),
                new PrivateObjective(mat_12, 12)
        ));
    }
    /**
     * return the list of initialized common objective - STATIC
     * @author Ettori Faccincani
     * @return the list of all the possible common objective
     */
    public static ArrayList<CommonObjective> setBucketOfCO() {
        return new ArrayList<>(Arrays.asList(
                new CommonObjective(new Algo_CO_1(), "1"),
                new CommonObjective(new Algo_CO_2(), "2"),
                new CommonObjective(new Algo_CO_3(), "3"),
                new CommonObjective(new Algo_CO_4(), "4"),
                new CommonObjective(new Algo_CO_5(), "5"),
                new CommonObjective(new Algo_CO_6(), "6"),
                new CommonObjective(new Algo_CO_7(), "7"),
                new CommonObjective(new Algo_CO_8(), "8"),
                new CommonObjective(new Algo_CO_9(), "9"),
                new CommonObjective(new Algo_CO_10(), "10"),
                new CommonObjective(new Algo_CO_11(), "11"),
                new CommonObjective(new Algo_CO_12(), "12")
        ));
    }
    /**
     * return the list of initialized cards - STATIC
     * @author Ettori
     * @return the list of all the possible cards present in the game (132)
     */
    public static ArrayList<Card> setBucketOfCards(){
        ArrayList<Card> res = new ArrayList<>();
        for(int i = 0; i < cardNum; i++) {
            res.add(new Card(CYAN));
            res.add(new Card(BLUE));
            res.add(new Card(YELLOW));
            res.add(new Card(WHITE));
            res.add(new Card(GREEN));
            res.add(new Card(PINK));
        }
        return res;
    }
}