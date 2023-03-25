package app.model;

import static app.model.Color.*;

/**
 * classe che modellizza gli obbiettivi privati di ogni giocatore
 * @author Ettori Giammusso
 * immutable
 */
public class PrivateObjective extends Objective {
    private int[] arrayOfPoints;
    private Card[][] matrix;
    public int objectiveId; // non sono sicuro che serva, per ora lo teniamo

    public PrivateObjective(Card[][] mat, int id, String image){
        arrayOfPoints = new int[]{0,1,2,4,6,9,12};
        matrix = mat;
        objectiveId = id;
        imagePath = image;
    }
    /**
     * conta i punti che il giocatore ha fatto
     * @author Ettori Giammusso
     * @param: matrice della library del giocatore
     * @return: il numero di punti fatti effettivamente
     */
    public int countPoints(Card[][] cards) {
        return arrayOfPoints[countMatch(cards)];
    }
    /**
     * conta quante carte il giocatore ha posizionato come nell'obbiettivo
     * @author Ettori Giammusso
     * @param: matrice della libraria del giocatore
     * @return: numero di match trovati
     */
    private  int countMatch(Card[][] cards) {
        int count = 0;
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(matrix[i][j].color != EMPTY && matrix[i][j].color == cards[i][j].color)
                    count++;
            }
        }
        return count;
    }
    public void draw() {return;}
}