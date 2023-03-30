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
    public void draw(CommonObjective commonObjective){
        System.out.println("Common objective number:"+ commonObjective.imagePath+"\n");
    }
}

