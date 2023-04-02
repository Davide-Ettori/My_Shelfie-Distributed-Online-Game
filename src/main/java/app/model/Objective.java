package app.model;
/**
 * class which represent the parent of all the objective (private and common) of the game. Immutable
 * @author Ettori Faccincani
 *
 */
abstract class Objective {
    public int COLS = 5;
    public int ROWS = 6;

    /**
     * placeholder function, which will be overwritten by the subclasses, it draws itself
     */
    public void draw(){}
}