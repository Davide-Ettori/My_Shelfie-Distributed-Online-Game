package app.model;

import java.io.Serializable;

/**
 * class which formalize the Strategy Pattern. Immutable
 * @author Ettori Faccincani
 *
 */
public class Strategy implements Serializable {
    /** the rows of the library that will be checked by the objective */
    public int ROWS = 6;
    /** the columns of the library that will be checked by the objective */
    public int COLS = 5;
    /**
     * check if the objective is achieved by the player library
     * @author Ettori
     * @param board the state of the library
     * @return true iff the objective is achieved
     */
    public boolean checkMatch(Card[][] board){return true;};
}
