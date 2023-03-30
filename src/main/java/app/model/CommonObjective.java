package app.model;

/**
 * class which represent the common objectives which all the players must try to achieve
 * @author Ettori Faccincani
 * immutable
 */
public class CommonObjective extends Objective {
    public final Strategy algorithm;
    public CommonObjective(Strategy algorithm, String image){
        this.imagePath = image;
        this.algorithm = algorithm;
    }
    /**
     * method that call drawMatrix to print the CO index
     * @author Gumus
     */
    public void draw(int points){
        System.out.println("Common objective number: " + this.imagePath + ", Points available: " + points);
    }
}

