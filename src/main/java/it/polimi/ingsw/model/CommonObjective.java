package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * class which represent the common objectives which all the players must try to achieve. Immutable
 * @author Ettori Faccincani
 */
public class CommonObjective extends Objective implements Serializable {
    /** the algorithm chosen, related to the specific common objective */
    public final Strategy algorithm;
    /** the unique identifier of the objective */
    public final int id;
    public final String imagePath;

    /**
     * normal constructor for this type of objects
     * @param algo the algorithm used, depends on the objective chosen
     * @param identify the unique identifier of this objective
     */
    public CommonObjective(Strategy algo, int identify){
        imagePath = "common goal cards/" + identify + ".jpg";
        id = identify;
        algorithm = algo;
    }

    /**
     * method that draws this objective
     * @author Gumus
     * @param points the points still available for this objective
     */
    public void draw(int points){
        System.out.println("Common objective number: " + id + ", Points available: " + points);
        System.out.print("Description: ");
        if(id == 1)
            System.out.println("Two groups each containing 4 tiles of \n" +
                    "the same type in a 2x2 square. The tiles \n" +
                    "of one square can be different from \n" +
                    "those of the other square.");
        if(id == 2)
            System.out.println("Two columns each formed by 6 \n" +
                    "different types of tiles.");
        if(id == 3)
            System.out.println("Four groups each containing at least \n" +
                    "4 tiles of the same type (not necessarily \n" +
                    "in the depicted shape).  \n" +
                    "The tiles of one group can be different \n" +
                    "from those of another group.");
        if(id == 4)
            System.out.println("Six groups each containing at least \n" +
                    "2 tiles of the same type (not necessarily \n" +
                    "in the depicted shape).  \n" +
                    "The tiles of one group can be different \n" +
                    "from those of another group.");
        if(id == 5)
            System.out.println("Three columns each formed by 6 \n" +
                    "tiles Five tiles of the same type forming an X.\n" +
                    "of maximum three different types. One \n" +
                    "column can show the same or a different \n" +
                    "combination of another column.");
        if(id == 6)
            System.out.println("Two lines each formed by 5 different \n" +
                    "types of tiles. One line can show the \n" +
                    "same or a different combination of the \n" +
                    "other line.");
        if(id == 7)
            System.out.println("Four lines each formed by 5 tiles of \n" +
                    "maximum three different types. One \n" +
                    "line can show the same or a different \n" +
                    "combination of another line.");
        if(id == 8)
            System.out.println("Four tiles of the same type in the four \n" +
                    "corners of the bookshelf.");
        if(id == 9)
            System.out.println("Eight tiles of the same type. There’s no \n" +
                    "restriction about the position of these \n" +
                    "tiles.");
        if(id == 10)
            System.out.println("Five tiles of the same type forming an X.");
        if(id == 11)
            System.out.println("Five tiles of the same type forming a \n" +
                    "diagonal. ");
        if(id == 12)
            System.out.println("Five columns of increasing or decreasing \n" +
                    "height. Starting from the first column on \n" +
                    "the left or on the right, each next column \n" +
                    "must be made of exactly one more tile. \n" +
                    "Tiles can be of any type.");
    }

}