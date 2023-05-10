package board;

import it.polimi.ingsw.model.Card;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.CommonObjective;
import it.polimi.ingsw.model.Strategy;
import it.polimi.ingsw.model.Algo_CO_1;
import it.polimi.ingsw.model.Algo_CO_2;
import static it.polimi.ingsw.model.Color.*;

/**
 * <p>
 * class that test the isBoardUnplayable method of the Board class
 * <p>
 * The False tests are: 1, 4
 * <p>
 * The True tests are: 2, 3
 * @author Giammusso Ettori
 */
public class isBoardUnplayableTest {
    int DIM = 9;
    Strategy strategy1 = null;
    Strategy strategy2 = null;
    CommonObjective CO1 = null;
    CommonObjective CO2 = null;
    Board board1 = null;
    Card[][] gameBoard1 = null;
    /**
     * The Before method start before every test and is used to create the new objects on which perform the testing
     * @author Giammusso
     */
    @Before
    public void setUp(){
        strategy1 = new Algo_CO_1();
        strategy2 = new Algo_CO_2();

        CO1 = new CommonObjective(strategy1,0);
        CO2 = new CommonObjective(strategy2, 0);

        board1 = new Board(4,CO1,CO2);
        gameBoard1 = new Card[DIM][DIM];
    }

    /**
     * Test the case in which there are only 4 cards isolated in the board
     * @author Giammusso
     */
    @Test //test 1
    public void board_fourIsolatedCards(){
        //initialize the matrix with EMPTY cards
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //put the coloured cards specifically where I want them to be
        gameBoard1[1][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[5][3].color = BLUE;
        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.isBoardUnplayable(), true);
    }

    /**
     * Test the case in which there are 4 near cards in the board (not diagonally)
     * @author Giammusso
     */
    @Test //test 2
    public void board_fourNearCards(){
        //initialize the matrix with EMPTY cards
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //put the coloured cards specifically where I want them to be
        gameBoard1[1][3].color = BLUE;
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.isBoardUnplayable(), false);
    }

    /**
     * Test the case in which there are 6 near cards in the board (not diagonally)
     * @author Ettori
     */
    @Test //test 3
    public void board_sixNearCards(){
        //initialize the matrix with EMPTY cards
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //put the coloured cards specifically where I want them to be
        gameBoard1[0][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;
        gameBoard1[1][3].color = BLUE;
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.isBoardUnplayable(), false);
    }

    /**
     * Test the case in which there are zero cards in the board
     * @author Ettori
     */
    @Test //test 4
    public void board_ZeroCards(){
        //initialize the matrix with EMPTY cards
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.isBoardUnplayable(), true);
    }
}
