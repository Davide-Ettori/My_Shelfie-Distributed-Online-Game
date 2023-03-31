package app.model;
/**
 * class which represent the parent of all the objective (private and common) of the game. Immutable
 * @author Ettori Faccincani
 *
 */
abstract class Objective {
    protected String imagePath;
    // public int index; // probabilmente non serve, vedremo in futuro
    public int COLS = 5;
    public int ROWS = 6;

    public void draw(){}
}