package board;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Card;
import org.junit.Before;
import org.junit.Test;
import static it.polimi.ingsw.model.Color.*;

import static org.junit.Assert.assertEquals;
/**
 * <p>
 * class that test if a card on the board has a freeside
 * <p>
 * this class utilize the setGameBoard method of the Board class, which was specifically created to test the hasOneFreeSideTest method
 * @author Gumus
 */

public class hasOneFreeSideTest {
    int DIM=9;
    Board board1 = null;
    Card[][] gameBoard1 = null;
    @Before
    public void setUp(){
        board1 = new Board(4,null,null);
        gameBoard1 = new Card[DIM][DIM];

        Board boardTest = new Board(4, null, null);
        boardTest.initBoard(4);
        boardTest.hasOneFreeSide(0, 5);
        boardTest.hasOneFreeSide(5, DIM);
        boardTest.hasOneFreeSide(5, 0);
    }

    /**
     * this test check if the selected card(which don't have a freeside) has a freeside
     * @author Gumus
     */
    @Test
    public void board_nofreeside(){
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        gameBoard1[2][3].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[3][4].color = WHITE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[4][5].color = BLUE;

        board1.setGameBoard(gameBoard1);
        assertEquals(board1.hasOneFreeSide(3,4), false);
    }

    /**
     * this test check if the selected card(which have a single freeside) has a freeside
     * @author Gumus
     */
    @Test
    public void board_haveonefreeside(){
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        gameBoard1[2][3].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[3][4].color = WHITE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[4][5].color = BLUE;

        board1.setGameBoard(gameBoard1);
        assertEquals(board1.hasOneFreeSide(4,5), true);
    }
    /**
     * this test check if the selected card(which have 2 freeside) has a freeside
     * @author Gumus
     */
    @Test
    public void board_havetwofreeside(){
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        gameBoard1[2][3].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[3][4].color = WHITE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[4][5].color = BLUE;

        board1.setGameBoard(gameBoard1);
        assertEquals(board1.hasOneFreeSide(4,4), true);
    }
}
