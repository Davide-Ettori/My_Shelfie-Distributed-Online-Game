package app.model;

import app.view.CLI.CLIHelper;

import static app.model.Color.*;
import static app.view.CLI.CLIHelper.*;

/**
 * class which represent the private objective for each player
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
     * count the points accumulated by the player
     * @author Ettori Giammusso
     * @param cards matrix of the player's library
     * @return number of total actual points
     */
    public int countPoints(Card[][] cards) {
        return arrayOfPoints[countMatch(cards)];
    }
    /**
     * count how many cards the player matched with the objective
     * @author Ettori Giammusso
     * @param cards matrix of the player's library
     * @return number of match found
     */
    private int countMatch(Card[][] cards) {
        int count = 0;
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(matrix[i][j].color != EMPTY && matrix[i][j].color == cards[i][j].color)
                    count++;
            }
        }
        return count;
    }
    public void draw() {
        drawMatrix(matrix,matrix.length,matrix[0].length,"Your private objective:\n");
    }
}