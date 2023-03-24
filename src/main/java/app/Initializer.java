package app;

import app.model.Algo_CO_1;
import app.model.Card;

import app.model.CommonObjective;
import app.model.PrivateObjective;

import java.util.ArrayList;

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


public class Initializer { // Inizializza lo stato iniziale (casuale) del gioco
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

        PrivateObjective obj;

        for (int i = 0; i < 12; i++) {

            switch(i){
                case 0:
                    mat[0][0] = new Card(PINK);
                    mat[0][2] = new Card(BLUE);
                    mat[1][5] = new Card(GREEN);
                    mat[2][4] = new Card(WHITE);
                    mat[3][1] = new Card(YELLOW);
                    mat[5][2] = new Card(BLUE);
                    setEmpty(mat);
                    break;
                case 1:
                    mat[1][1] = new Card(PINK);
                    mat[2][0] = new Card(GREEN);
                    mat[2][2] = new Card(YELLOW);
                    mat[3][4] = new Card(WHITE);
                    mat[4][3] = new Card(CYAN);
                    mat[5][4] = new Card(BLUE);
                    setEmpty(mat);

                    break;
                case 2:
                    mat[1][0] = new Card(BLUE);
                    mat[1][3] = new Card(YELLOW);
                    mat[2][2] = new Card(PINK);
                    mat[3][1] = new Card(GREEN);
                    mat[3][4] = new Card(CYAN);
                    mat[5][0] = new Card(WHITE);
                    break;
                case 3:
                    mat[0][4] = new Card(YELLOW);
                    mat[2][0] = new Card(CYAN);
                    mat[2][2] = new Card(BLUE);
                    mat[3][3] = new Card(PINK);
                    mat[4][1] = new Card(WHITE);
                    mat[4][2] = new Card(GREEN);
                    break;
                case 4:
                    mat[1][1] = new Card(CYAN);
                    mat[3][1] = new Card(BLUE);
                    mat[3][2] = new Card(WHITE);
                    mat[4][4] = new Card(PINK);
                    mat[5][0] = new Card(YELLOW);
                    mat[5][3] = new Card(GREEN);
                    break;
                case 5:
                    mat[0][2] = new Card(CYAN);
                    mat[0][4] = new Card(GREEN);
                    mat[2][3] = new Card(WHITE);
                    mat[4][1] = new Card(YELLOW);
                    mat[4][3] = new Card(BLUE);
                    mat[5][0] = new Card(PINK);
                    setEmpty(mat);

                    break;
                case 6:
                    mat[0][0] = new Card(GREEN);
                    mat[1][4] = new Card(BLUE);
                    mat[2][1] = new Card(PINK);
                    mat[3][0] = new Card(CYAN);
                    mat[4][4] = new Card(YELLOW);
                    mat[5][3] = new Card(WHITE);
                    setEmpty(mat);

                    break;
                case 7:
                    mat[0][4] = new Card(BLUE);
                    mat[1][1] = new Card(GREEN);
                    mat[2][2] = new Card(CYAN);
                    mat[3][0] = new Card(PINK);
                    mat[4][3] = new Card(WHITE);
                    mat[5][3] = new Card(YELLOW);
                    setEmpty(mat);

                    break;
                case 8:
                    mat[0][2] = new Card(YELLOW);
                    mat[2][2] = new Card(GREEN);
                    mat[3][4] = new Card(WHITE);
                    mat[4][1] = new Card(CYAN);
                    mat[4][4] = new Card(PINK);
                    mat[5][0] = new Card(BLUE);
                    setEmpty(mat);

                    break;
                case 9:
                    mat[0][4] = new Card(CYAN);
                    mat[1][1] = new Card(YELLOW);
                    mat[2][0] = new Card(WHITE);
                    mat[3][3] = new Card(GREEN);
                    mat[4][1] = new Card(BLUE);
                    mat[5][3] = new Card(PINK);
                    setEmpty(mat);

                    break;
                case 10:
                    mat[0][2] = new Card(PINK);
                    mat[1][1] = new Card(WHITE);
                    mat[2][0] = new Card(YELLOW);
                    mat[3][2] = new Card(BLUE);
                    mat[4][4] = new Card(GREEN);
                    mat[5][3] = new Card(CYAN);
                    setEmpty(mat);

                    break;
                case 11:
                    mat[0][2] = new Card(WHITE);
                    mat[1][1] = new Card(PINK);
                    mat[2][2] = new Card(BLUE);
                    mat[3][3] = new Card(CYAN);
                    mat[4][4] = new Card(YELLOW);
                    mat[5][0] = new Card(GREEN);
                    setEmpty(mat);

                    break;
            }
            obj = new PrivateObjective(mat, 1, "");
            bucketOfPO.add(obj);
        }
    }
    public void setBucketOfCO() {
        CommonObjective obj;


        for (int i = 0; i < 12; i++) {
            switch (i){
                case 0:
                    Algo_CO_1 algoCo0 = new Algo_CO_1();
                    obj = new CommonObjective(algoCo0, "");
                    break;
                case 1:
                    Algo_CO_2 algoCo1 = new Algo_CO_2();
                    obj = new CommonObjective(algoCo1, "");
                    break;
                case 2:
                    Algo_CO_3 algoCo2 = new Algo_CO_3();
                    obj = new CommonObjective(algoCo2, "");
                    break;
                case 3:
                    Algo_CO_4 algoCo3 = new Algo_CO_4();
                    obj = new CommonObjective(algoCo3, "");
                    break;
                case 4:
                    Algo_CO_5 algoCo4 = new Algo_CO_5();
                    obj = new CommonObjective(algoCo4, "");
                    break;
                case 5:
                    Algo_CO_6 algoCo5 = new Algo_CO_6();
                    obj = new CommonObjective(algoCo5, "");
                    break;
                case 6:
                    Algo_CO_7 algoCo6 = new Algo_CO_7();
                    obj = new CommonObjective(algoCo6, "");
                    break;
                case 7:
                    Algo_CO_8 algoCo7 = new Algo_CO_8();
                    obj = new CommonObjective(algoCo7, "");
                    break;
                case 8:
                    Algo_CO_9 algoCo8 = new Algo_CO_9();
                    obj = new CommonObjective(algoCo8, "");
                    break;
                case 9:
                    Algo_CO_10 algoCo9 = new Algo_CO_10();
                    obj = new CommonObjective(algoCo9, "");
                    break;
                case 10:
                    Algo_CO_11 algoCo10 = new Algo_CO_11();
                    obj = new CommonObjective(algoCo10, "");
                    break;
                case 11:
                    Algo_CO_12 algoCo11 = new Algo_CO_12();
                    obj = new CommonObjective(algoCo11, "");
                    break;
                default:
                    obj=null;
            }
            bucketOfCO.add(obj);
        }
    }
}


