package app.model;

/**
 * class which formalize the Strategy Pattern
 * @author Ettori Faccincani
 * immutable
 */
public class Strategy {
    int ROWS = 6;
    int COLS = 5;
    /**
     * check if the objective is achieved by the player library
     * @author Ettori
     * @param board the state of the library
     * @return true iif the objective is achieved
     */
    public boolean checkMatch(Card[][] board){return true;};
}
