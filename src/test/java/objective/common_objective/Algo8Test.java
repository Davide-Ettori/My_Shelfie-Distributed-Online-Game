package objective.common_objective;

import app.model.Algo_CO_8;
import app.model.Card;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static app.model.Color.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * <p>
 * class that test the eighth algorithm, two rows of 5 different types of cards
 * <p>
 * test which are true are #1
 * <p>
 * test which are false are #2 #3
 * @author Faccincani, Ettori , Gumus
 */
public class Algo8Test {
    Algo_CO_8 algoCo8 = null;

    Card[][] mat = new Card[6][5];

    @Before // eseguita prima dei test
    public void setUp() {
        this.algoCo8 = new Algo_CO_8();
    }
    @After // eseguita dopo i test
    public void tearDown() {
        return;
    }

    /**
     * <table border="1">
     * <tr><td>P</td><td>W</td><td>B</td><td>C</td><td>Y</td></tr>
     * <tr><td>B</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>G</td><td>W</td><td>B</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>C</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     */
    @Test // test 1
    public void algo8_test1_T() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(WHITE);
        mat[0][2] = new Card(BLUE);
        mat[0][3] = new Card(CYAN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(BLUE);
        mat[1][1] = new Card(BLUE);
        mat[1][2] = new Card();
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card(GREEN);
        mat[3][3] = new Card(WHITE);
        mat[3][4] = new Card(BLUE);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo8.checkMatch(mat));
    }

    /**
     * <table border="1">
     * <tr><td>P</td><td>W</td><td>B</td><td>C</td><td>Y</td></tr>
     * <tr><td>B</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>W</td><td>B</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>C</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     */
    @Test // test 1
    public void algo8_test2_F_one_row() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(WHITE);
        mat[0][2] = new Card(BLUE);
        mat[0][3] = new Card(CYAN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(BLUE);
        mat[1][1] = new Card(BLUE);
        mat[1][2] = new Card();
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card(WHITE);
        mat[3][4] = new Card(BLUE);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo8.checkMatch(mat));
    }

    /**
     * <table border="1">
     * <tr><td>P</td><td>W</td><td>B</td><td>C</td><td>Y</td></tr>
     * <tr><td>B</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>W</td><td>W</td><td>B</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>C</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     */
    @Test // test 1
    public void algo8_test3_F_same_type() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(WHITE);
        mat[0][2] = new Card(BLUE);
        mat[0][3] = new Card(CYAN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(BLUE);
        mat[1][1] = new Card(BLUE);
        mat[1][2] = new Card();
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card(WHITE);
        mat[3][3] = new Card(WHITE);
        mat[3][4] = new Card(BLUE);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo8.checkMatch(mat));
    }
}
