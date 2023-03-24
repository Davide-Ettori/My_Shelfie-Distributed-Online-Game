package app;

import app.model.Algo_CO_1;
import app.model.Card;

import app.model.CommonObjective;
import app.model.PrivateObjective;

import java.util.ArrayList;
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


public class Initaliazer { // Inizializza lo stato iniziale (casuale) del gioco
    // solo metodi statici

    public final int ROWS = 6;
    public final int COLS = 5;
    private ArrayList<PrivateObjective> bucketOfPO;
    private ArrayList<CommonObjective> bucketOfCO;

    public void setEmpty(Card[][] mat) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (mat[j][i].color == null) {
                    mat[j][i] = new Card(EMPTY);
                }

            }
        }
    }

    public void setBucketOfPO() {
        Card[][] mat = null;

        PrivateObjective obj0;
        PrivateObjective obj1;
        PrivateObjective obj2;
        PrivateObjective obj3;
        PrivateObjective obj4;
        PrivateObjective obj5;
        PrivateObjective obj6;
        PrivateObjective obj7;
        PrivateObjective obj8;
        PrivateObjective obj9;
        PrivateObjective obj10;
        PrivateObjective obj11;

        for (int i = 0; i < 11; i++) {
            if (i == 0) {
                mat[0][0] = new Card(PINK);
                mat[0][2] = new Card(BLUE);
                mat[1][5] = new Card(GREEN);
                mat[2][4] = new Card(WHITE);
                mat[3][1] = new Card(YELLOW);
                mat[5][2] = new Card(BLUE);
                setEmpty(mat);

                obj0 = new PrivateObjective(mat, 1, "");

                bucketOfPO.add(obj0);

            }
            else if (i == 1) {
                mat[1][1] = new Card(PINK);
                mat[2][0] = new Card(GREEN);
                mat[2][2] = new Card(YELLOW);
                mat[3][4] = new Card(WHITE);
                mat[4][3] = new Card(CYAN);
                mat[5][4] = new Card(BLUE);

                setEmpty(mat);

                obj1 = new PrivateObjective(mat, 1, "");

                bucketOfPO.add(obj1);

            }
            else if (i == 2) {
                mat[1][0] = new Card(BLUE);
                mat[1][3] = new Card(YELLOW);
                mat[2][2] = new Card(PINK);
                mat[3][1] = new Card(GREEN);
                mat[3][4] = new Card(CYAN);
                mat[5][0] = new Card(WHITE);

                setEmpty(mat);

                obj2 = new PrivateObjective(mat, 1, "");

                bucketOfPO.add(obj2);

            }
            else if (i == 3) {
                mat[0][4] = new Card(YELLOW);
                mat[2][0] = new Card(CYAN);
                mat[2][2] = new Card(BLUE);
                mat[3][3] = new Card(PINK);
                mat[4][1] = new Card(WHITE);
                mat[4][2] = new Card(GREEN);

                setEmpty(mat);

                obj3 = new PrivateObjective(mat, 1, "");

                bucketOfPO.add(obj3);

            }
            else if (i == 4) {
                mat[1][1] = new Card(CYAN);
                mat[3][1] = new Card(BLUE);
                mat[3][2] = new Card(WHITE);
                mat[4][4] = new Card(PINK);
                mat[5][0] = new Card(YELLOW);
                mat[5][3] = new Card(GREEN);

                setEmpty(mat);

                obj4 = new PrivateObjective(mat, 1, "");

                bucketOfPO.add(obj4);

            }
            else if (i == 5) {
                mat[0][2] = new Card(CYAN);
                mat[0][4] = new Card(GREEN);
                mat[2][3] = new Card(WHITE);
                mat[4][1] = new Card(YELLOW);
                mat[4][3] = new Card(BLUE);
                mat[5][0] = new Card(PINK);

                setEmpty(mat);

                obj5 = new PrivateObjective(mat, 1, "");

                bucketOfPO.add(obj5);

            }
            else if (i == 6) {
                mat[0][0] = new Card(GREEN);
                mat[1][4] = new Card(BLUE);
                mat[2][1] = new Card(PINK);
                mat[3][0] = new Card(CYAN);
                mat[4][4] = new Card(YELLOW);
                mat[5][3] = new Card(WHITE);

                setEmpty(mat);

                obj6 = new PrivateObjective(mat, 1, "");

                bucketOfPO.add(obj6);
            }
            else if (i == 7) {
                mat[0][4] = new Card(BLUE);
                mat[1][1] = new Card(GREEN);
                mat[2][2] = new Card(CYAN);
                mat[3][0] = new Card(PINK);
                mat[4][3] = new Card(WHITE);
                mat[5][3] = new Card(YELLOW);

                setEmpty(mat);

                obj7 = new PrivateObjective(mat, 1, "");

                bucketOfPO.add(obj7);

            }
            else if (i == 8) {
                mat[0][2] = new Card(YELLOW);
                mat[2][2] = new Card(GREEN);
                mat[3][4] = new Card(WHITE);
                mat[4][1] = new Card(CYAN);
                mat[4][4] = new Card(PINK);
                mat[5][0] = new Card(BLUE);

                setEmpty(mat);

                obj8 = new PrivateObjective(mat, 1, "");

                bucketOfPO.add(obj8);

            }
            else if (i == 9) {
                mat[0][4] = new Card(CYAN);
                mat[1][1] = new Card(YELLOW);
                mat[2][0] = new Card(WHITE);
                mat[3][3] = new Card(GREEN);
                mat[4][1] = new Card(BLUE);
                mat[5][3] = new Card(PINK);

                setEmpty(mat);

                obj9 = new PrivateObjective(mat, 1, "");

                bucketOfPO.add(obj9);

            }
            else if (i == 10) {
                mat[0][2] = new Card(PINK);
                mat[1][1] = new Card(WHITE);
                mat[2][0] = new Card(YELLOW);
                mat[3][2] = new Card(BLUE);
                mat[4][4] = new Card(GREEN);
                mat[5][3] = new Card(CYAN);

                setEmpty(mat);

                obj10 = new PrivateObjective(mat, 1, "");

                bucketOfPO.add(obj10);

            }
            else if (i == 11) {
                mat[0][2] = new Card(WHITE);
                mat[1][1] = new Card(PINK);
                mat[2][2] = new Card(BLUE);
                mat[3][3] = new Card(CYAN);
                mat[4][4] = new Card(YELLOW);
                mat[5][0] = new Card(GREEN);

                setEmpty(mat);

                obj11 = new PrivateObjective(mat, 1, "");

                bucketOfPO.add(obj11);

            }
        }
    }

    public void setBucketOfCO() {
        CommonObjective obj0;
        CommonObjective obj1;
        CommonObjective obj2;
        CommonObjective obj3;
        CommonObjective obj4;
        CommonObjective obj5;
        CommonObjective obj6;
        CommonObjective obj7;
        CommonObjective obj8;
        CommonObjective obj9;
        CommonObjective obj10;
        CommonObjective obj11;


        for (int i = 0; i < 11; i++) {
            if (i == 0) {
                Algo_CO_1 algoCo0 = new Algo_CO_1();

                obj0 = new CommonObjective(algoCo0, "");

                bucketOfCO.add(obj0);

            }
            else if (i == 1) {
                Algo_CO_2 algoCo1 = new Algo_CO_2();

                obj1 = new CommonObjective(algoCo1, "");

                bucketOfCO.add(obj1);

            }
            else if (i == 2) {
                Algo_CO_3 algoCo2 = new Algo_CO_3();

                obj2 = new CommonObjective(algoCo2, "");

                bucketOfCO.add(obj2);

            }
            else if (i == 3) {
                Algo_CO_4 algoCo3 = new Algo_CO_4();

                obj3 = new CommonObjective(algoCo3, "");

                bucketOfCO.add(obj3);

            }
            else if (i == 4) {
                Algo_CO_5 algoCo4 = new Algo_CO_5();

                obj4 = new CommonObjective(algoCo4, "");

                bucketOfCO.add(obj4);

            }
            else if (i == 5) {
                Algo_CO_6 algoCo5 = new Algo_CO_6();

                obj5 = new CommonObjective(algoCo5, "");

                bucketOfCO.add(obj5);

            }
            else if (i == 6) {
                Algo_CO_7 algoCo6 = new Algo_CO_7();

                obj6 = new CommonObjective(algoCo6, "");

                bucketOfCO.add(obj6);

            }
            else if (i == 7) {
                Algo_CO_8 algoCo7 = new Algo_CO_8();

                obj7 = new CommonObjective(algoCo7, "");

                bucketOfCO.add(obj7);

            }
            else if (i == 8) {
                Algo_CO_9 algoCo8 = new Algo_CO_9();

                obj8 = new CommonObjective(algoCo8, "");

                bucketOfCO.add(obj8);

            }
            else if (i == 9) {
                Algo_CO_10 algoCo9 = new Algo_CO_10();

                obj9 = new CommonObjective(algoCo9, "");

                bucketOfCO.add(obj9);

            }
            else if (i == 10) {
                Algo_CO_11 algoCo10 = new Algo_CO_11();

                obj10 = new CommonObjective(algoCo10, "");

                bucketOfCO.add(obj10);

            }
            else if (i == 11) {
                Algo_CO_12 algoCo11 = new Algo_CO_12();

                obj11 = new CommonObjective(algoCo11, "");

                bucketOfCO.add(obj11);

            }
        }


    }


}


