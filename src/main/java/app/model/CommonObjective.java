package app.model;

import java.io.Serializable;

/**
 * class which represent the common objectives which all the players must try to achieve. Immutable
 * @author Ettori Faccincani
 */
public class CommonObjective extends Objective implements Serializable {
    public final Strategy algorithm;
    public final int id;

    /**
     * normal constructor for this type of objects
     * @param algo the algorithm used, depends on the objective chosen
     * @param identify the unique identifier of this objective
     */
    public CommonObjective(Strategy algo, int identify){
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
    }
}