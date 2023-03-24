package app;

import app.model.Card;
import app.model.Color;
import app.model.CommonObjective;
import app.model.PrivateObjective;

import java.util.ArrayList;

import static app.model.Color;
import static app.model.Color.*;


public class Initaliazer { // Inizializza lo stato iniziale (casuale) del gioco
    // solo classi statiche

    public final int ROWS = 6;
    public final int COLS = 5;
    //bucket of po, same order of the directory
    private PrivateObjective obj0p;
    private PrivateObjective obj1p;
    private PrivateObjective obj2p;
    private PrivateObjective obj3p;
    private PrivateObjective obj4p;
    private PrivateObjective obj5p;
    private PrivateObjective obj6p;
    private PrivateObjective obj7p;
    private PrivateObjective obj8p;
    private PrivateObjective obj9p;
    private PrivateObjective obj10p;
    private PrivateObjective obj11p;
    private PrivateObjective obj12p;
    private ArrayList<PrivateObjective> bucketOfPO;
    private ArrayList<Card[][]> arMat = null;

    //bucket of co
    private ArrayList<CommonObjective> bucketOfCO;

    public void setEmpty (Card[][] mat) {
        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLS; j++) {
                for (int r = 0; r < 5; r++){
                    if (mat[j][i].color != Color[r]) { //voglio accedere all'enum per ogni caso senza fare mille if o or

                    }
                }
            }
        }
    }
    public void setArMat(int i) {
        Card[][] mat = null;
        mat = arMat.get(i);
        if (i == 1){
            mat[0][0] = new Card(PINK);
            mat[0][2] = new Card(BLUE);
            mat[1][5] = new Card(GREEN);
            mat[2][4] = new Card(WHITE);
            mat[3][1] = new Card(YELLOW);
            mat[6][2] = new Card(BLUE);
            //other empty (metodo apposito)

        }
        if (i == 2){
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);

            //other empty (metodo apposito)

        }
        if (i == 3){
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);

            //other empty (metodo apposito)

        }
        if (i == 4){
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);

            //other empty (metodo apposito)

        }
        if (i == 5){
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);

            //other empty (metodo apposito)

        }
        if (i == 6){
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);

            //other empty (metodo apposito)

        }
        if (i == 7){
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);

            //other empty (metodo apposito)

        }
        if (i == 8){
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);

            //other empty (metodo apposito)

        }
        if (i == 9){
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);

            //other empty (metodo apposito)

        }
        if (i == 10){
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);

            //other empty (metodo apposito)

        }
        if (i == 11){
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);

            //other empty (metodo apposito)

        }
        if (i == 12){
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);
            mat[0][0] = new Card(PINK);

            //other empty (metodo apposito)

        }
    }


    private void createBucketPO () {

        for (int i = 0; i < 12; i++) {
            setArMat(i);
            if (arMat.get(i) != null) {
                obj1p.setObjMatrix(arMat.get(i));
            }
        }
    }




}
