package board;

import it.polimi.ingsw.model.Card;
import org.junit.*;

import static it.polimi.ingsw.model.Color.*;
import static org.junit.Assert.*;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.CommonObjective;
import it.polimi.ingsw.model.Strategy;
import it.polimi.ingsw.model.Algo_CO_1;
import it.polimi.ingsw.model.Algo_CO_2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>
 * class that test the areCardsPickable method of the Board class
 * <p>
 * The False tests are: 0, 1, 10
 * <p>
 * The True tests are: 2, 3, 4, 5, 6, 7, 8, 9
 * @author Giammusso
 */
public class areCardsPickableTest {
    int DIM = 9;
    Strategy strategy1 = null;
    Strategy strategy2 = null;
    CommonObjective CO1 = null;
    CommonObjective CO2 = null;
    Board board1 = null;
    Board board2 = null;
    Card[][] gameBoard1 = null;
    ArrayList<Integer> cardXY = null;
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

        board2 = new Board(2,CO1,CO2);
    }
    /**
     * Test the case in which only 2 cards are picked in a board initialized for just 2 players
     * @author Giammusso
     */
    @Test //test 0
    public void twoPlayers_pickOnly_TwoCards(){
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

        gameBoard1[1][4].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;

        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][5].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(1,3,1,4));

        //use the setter defined in the Board Class
        board2.setGameBoard(gameBoard1);

        assertTrue(board2.areCardsPickable(cardXY));
    }

    /** Test the case in which the cards picked are
     * Adjacent
     * Positioned: aligned
     * They have at least one free side
     * @author Giammusso
     */
    @Test //test1
    public void adiacent_Allineated_FreeSide(){
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

        gameBoard1[1][4].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(1,3,2,3));

        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertTrue(board1.areCardsPickable(cardXY));
    }
    /** Test the case in which the cards picked are
     * Adjacent
     * Positioned: not aligned
     * They have at least one free side
     * @author Giammusso
     */
    @Test //test2
    public void adiacent_NotAllineated_FreeSide(){
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

        gameBoard1[1][4].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(1,3,2,3,1,4));

        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertFalse(board1.areCardsPickable(cardXY));
    }
    /** Test the case in which the cards picked are
     * not Adjacent
     * Positioned: aligned
     * They have at least one free side
     * @author Giammusso
     */
    @Test//test3
    public void notAdiacent_Allineated_FreeSide_Vertical(){
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

        gameBoard1[1][4].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(1,3,2,3,4,3));

        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertFalse(board1.areCardsPickable(cardXY));
    }
    /** Test the case in which the cards picked are
     * not Adjacent
     * Positioned: aligned
     * They have at least one free side
     * @author Giammusso
     */
    @Test //test 4
    public void notAdiacent_Allineated_FreeSide_Orizontal(){
        //initialize the matrix with EMPTY cards
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //put the coloured cards specifically where I want them to be
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][5].color = BLUE;
        gameBoard1[5][5].color = BLUE;

        gameBoard1[3][6].color = BLUE;
        gameBoard1[4][6].color = BLUE;
        gameBoard1[5][6].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(3,3,3,4,3,6));

        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.areCardsPickable(cardXY), false);
    }
    /** Test the case in which the cards picked are
     * Adjacent
     * Positioned: aligned
     * They don't have at least one free side
     * @author Giammusso
     */
    @Test //test 5
    public void adiacent_Allineated_NotFreeSide(){
        //initialize the matrix with EMPTY cards
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //put the coloured cards specifically where I want them to be
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][5].color = BLUE;
        gameBoard1[5][5].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(3,4,4,4));

        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertFalse(board1.areCardsPickable(cardXY));
    }
    /** Test the case in which the cards picked are
     * Adjacent
     * Positioned: not aligned
     * They don't have at least one free side
     * @author Giammusso
     */
    @Test //test 6
    public void adiacent_NotAllineated_NotFreeSide(){
        //initialize the matrix with EMPTY cards
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //put the coloured cards specifically where I want them to be
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][5].color = BLUE;
        gameBoard1[5][5].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(2,4,3,4,2,5));

        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertFalse(board1.areCardsPickable(cardXY));
    }
    /** Test the case in which the cards picked are
     * not Adjacent
     * Positioned: aligned
     * They don't have at least one free side
     * @author Giammusso
     */
    @Test //test 7
    public void notAdiacent_Allineated_NotFreeSide(){
        //initialize the matrix with EMPTY cards
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //put the coloured cards specifically where I want them to be
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][5].color = BLUE;
        gameBoard1[5][5].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(2,4,4,4));

        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertFalse(board1.areCardsPickable(cardXY));
    }
    /** Test the case in which the cards picked are
     * not Adjacent
     * Positioned: aligned
     * They have at least one free side
     * @author Giammusso
     */
    @Test //test 8
    public void notAdiacent_NotAllineated_FreeSide(){
        //initialize the matrix with EMPTY cards
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //put the coloured cards specifically where I want them to be
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(2,3,3,4));

        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertFalse(board1.areCardsPickable(cardXY));
    }
    /** Test the case in which the cards picked are
     * not Adjacent
     * Positioned: not aligned
     * They don't have at least one free side
     * @author Giammusso
     */
    @Test //test 9
    public void notAdiacent_NotAllineated_NotFreeSide(){
        //initialize the matrix with EMPTY cards
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //put the coloured cards specifically where I want them to be
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][5].color = BLUE;
        gameBoard1[5][5].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(2,3,3,4,4,5));

        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertFalse(board1.areCardsPickable(cardXY));
    }
    /** Test the case in which the cards picked are
     * Adjacent
     * Positioned: aligned
     * They don't have at least one free side
     * They are picked near but not in consecutive order
     * @author Giammusso
     */
    @Test //test 10
    public void Adiacent_Allineated_FreeSide_ButPickedNotInOrder(){
        //initialize the matrix with EMPTY cards
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //put the coloured cards specifically where I want them to be
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(2,3,4,3,3,3));

        //use the setter defined in the Board Class
        board1.setGameBoard(gameBoard1);

        assertTrue(board1.areCardsPickable(cardXY));
    }
}
