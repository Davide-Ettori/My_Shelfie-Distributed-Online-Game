package objective.common_objective;

import app.model.Algo_CO_11;
import app.model.Card;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static app.model.Color.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * class that test the eleventh algorithm, eight cards of the same color everywhere in the library
 * @author Faccincani, Ettori , Gumus
 */
public class Algo11Test {
    Algo_CO_11 algoCo11 = null;

    Card[][] mat = new Card[6][5];
    /**
     * method executed before every test that create a new algorithm
     * @author Ettori
     */
    @Before
    public void setUp() {
        this.algoCo11 = new Algo_CO_11();
    }

    /**
     * <p>Eight cards of the same color in the library</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>G</td><td>G</td><td>Y</td></tr>
     * <tr><td>B</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>C</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test // test 1
    public void algo11_test1_T() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
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
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card();
        mat[5][4] = new Card();

        assertTrue(algoCo11.checkMatch(mat));
    }

    /**
     * <p>Less than eight cards of the same color in the library</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>G</td><td>G</td><td>Y</td></tr>
     * <tr><td>B</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>C</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>G</td><td>G</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test // test 1
    public void algo11_test2_F() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
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
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card();
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card();
        mat[5][4] = new Card();

        assertFalse(algoCo11.checkMatch(mat));
    }
}
