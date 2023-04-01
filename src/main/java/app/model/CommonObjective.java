package app.model;

import java.io.Serializable;

/**
 * class which represent the common objectives which all the players must try to achieve. Immutable
 * @author Ettori Faccincani
 */
public class CommonObjective extends Objective implements Serializable {
    public final Strategy algorithm;
    public final String imagePath;
    public CommonObjective(Strategy algo, String image){
        imagePath = image;
        algorithm = algo;
    }
    /**
     * method that call drawMatrix to print the CO index
     * @author Gumus
     */
    public void draw(int points){
        System.out.println("Common objective number: " + imagePath + ", Points available: " + points);
    }
}

