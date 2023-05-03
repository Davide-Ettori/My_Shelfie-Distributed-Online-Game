package board;

import app.model.Card;
import org.junit.*;

import static app.model.Color.*;
import static org.junit.Assert.*;

import app.model.Board;
import app.model.CommonObjective;
import app.model.Strategy;
import app.model.Algo_CO_1;
import app.model.Algo_CO_2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>
 * class that test the draw method of the Board class
 * <p>
 * this class utilize the setGameBoard method of the Board class, which was specifically created to test the draw method
 * @author Giammusso
 */
public class drawTest {
    int DIM = 9;
    Strategy strategy1 = null;
    Strategy strategy2 = null;
    CommonObjective CO1 = null;
    CommonObjective CO2 = null;
    Board board1 = null;
    Card[][] gameBoard1 = null;
    ArrayList<Integer> cardXY = null;

    @Before
    public void setUp() {
        strategy1 = new Algo_CO_1();
        strategy2 = new Algo_CO_2();

        CO1 = new CommonObjective(strategy1, 0);
        CO2 = new CommonObjective(strategy2, 0);

        board1 = new Board(4, CO1, CO2);
        gameBoard1 = new Card[DIM][DIM];
    }

    @Test //test1
    public void drawBoard(){
        //initialize the matrix with EMPTY cards
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //put the coloured cards specifically where I want them to be
        gameBoard1[1][3].color = BLUE;
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;

        gameBoard1[1][4].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(1, 3, 2, 3));

        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        board1.draw();
    }
}
