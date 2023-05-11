package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * class which represent the parent of all the objective (private and common) of the game. Immutable
 * @author Ettori Faccincani
 *
 */
abstract class Objective implements Serializable {
    /** the rows of the library that will be checked by the objective */
    public int ROWS = 6;
    /** the columns of the library that will be checked by the objective */
    public int COLS = 5;

    /**
     * placeholder function, which will be overwritten by the subclasses, it draws itself
     */
    public void draw(){}
}