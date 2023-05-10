package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * class which represent the private objective for each player. Immutable
 * @author Ettori Giammusso
 *
 */
public class PrivateObjective extends Objective implements Serializable {
    private int[] arrayOfPoints;
    private Card[][] matrix;
    public final String imagePath;
    /** the unique identifier of the private objective */
    public int objectiveId;

    /**
     * normal constructor for this type of objects
     * @param mat the matrix which identify the objective
     * @param id the unique identifier of the objective
     */
    public PrivateObjective(Card[][] mat, int id){
        imagePath = "assets/personal goal cards/Personal_Goals" + (id == 1 ? "" : id) + ".png";
        arrayOfPoints = new int[]{0,1,2,4,6,9,12};
        matrix = mat;
        objectiveId = id;
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
                if(matrix[i][j].color != Color.EMPTY && matrix[i][j].color == cards[i][j].color)
                    count++;
            }
        }
        return count;
    }
    /**
     * method that draws the objective itself (the matrix of cards)
     * @author Gumus
     */
    public void draw() {
        System.out.println("\nYour own private objective");
        System.out.print("   ");
        for (int i = 0; i < COLS; i++)
            System.out.print((i + 1) + "   ");
        System.out.println();
        for (int i = 0; i < ROWS; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < COLS; j++) {
                matrix[i][j].draw();
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}