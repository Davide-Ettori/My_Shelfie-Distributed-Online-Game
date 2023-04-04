package app.model;
import static app.model.Color.EMPTY;
/**
 * class which represent a fake common objective used just for testing purposes
 * Immutable
 * @author Giammusso
 */
public class Algo_CO_13_FAKE extends Strategy {

    /**
     * check if the library have a card in the first element of the first column
     * @author Giammusso
     * @param board the matrix of the board
     * @return true iff it found a match
     */
    @Override
    public boolean checkMatch(Card[][] board){
        return board[5][0].color != EMPTY;
    }

}
