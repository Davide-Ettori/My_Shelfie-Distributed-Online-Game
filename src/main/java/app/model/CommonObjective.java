package app.model;

import java.io.Serializable;

/**
 * class which represent the common objectives which all the players must try to achieve. Immutable
 * @author Ettori Faccincani
 */
public class CommonObjective extends Objective implements Serializable {
    public final Strategy algorithm;
    public final int id;
    public CommonObjective(Strategy algo, int identify){
        id = identify;
        algorithm = algo;
    }
    /**
     * method that call drawMatrix to print the CO index
     * @author Gumus
     */
    public void draw(int points){
        System.out.println("Common objective number: " + id + ", Points available: " + points);
    }
}

