package objective.common_objective;


import it.polimi.ingsw.model.Algo_CO_7;
import it.polimi.ingsw.model.Card;
import org.junit.Before;
import org.junit.Test;
import static it.polimi.ingsw.model.Color.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * class that test the seventh algorithm, two groups of four cards of the same color: cards of two groups must be of the same color
 * @author Faccincani, Ettori , Gumus
 */

public class Algo7Test {
    Algo_CO_7 algoCo7 = null;

    Card[][] mat = new Card[6][5];
    /**
     * method executed before every test that create a new algorithm
     * @author Ettori
     */
    @Before
    public void setUp() {
        this.algoCo7 = new Algo_CO_7();
    }

    /**
     * <p>Two groups of four cards with different colors</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>G</td><td>G</td><td>Y</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>C</td><td>C</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo7_test1_F() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(PINK);
        mat[1][1] = new Card(PINK);
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
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(GREEN);
        mat[4][2] = new Card(GREEN);
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo7.checkMatch(mat));
    }

    /**
     * <p>One group of six cards with the same color</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>P</td><td>G</td><td>Y</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>C</td><td>C</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo7_test2_F_3x3() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(PINK);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(PINK);
        mat[1][1] = new Card(PINK);
        mat[1][2] = new Card(PINK);
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
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(GREEN);
        mat[4][2] = new Card(GREEN);
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo7.checkMatch(mat));
    }

    /**
     * <p>Two group of six cards with the same color</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>P</td><td>G</td><td>Y</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test // test 1
    public void algo7_test3_T_3x3_same_colors() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(PINK);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(PINK);
        mat[1][1] = new Card(PINK);
        mat[1][2] = new Card(PINK);
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
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(GREEN);
        mat[4][2] = new Card(GREEN);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo7.checkMatch(mat));
    }

    /**
     * <p>One group of four cards with the same color</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>G</td><td>G</td><td>Y</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>C</td><td>C</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test // test 1
    public void algo7_test4_only_one_square() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(PINK);
        mat[1][1] = new Card(PINK);
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
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(GREEN);
        mat[4][2] = new Card(GREEN);
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo7.checkMatch(mat));
    }

    /**
     * <p>Two group of six cards with the same color (corner case)</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo7_test5_strangeshape() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card();
        mat[2][3] = new Card();
        mat[2][4] = new Card();

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card(PINK);
        mat[3][2] = new Card(GREEN);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(GREEN);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo7.checkMatch(mat));
    }

    /**
     * <p>Two group of six cards with the same color (corner case)</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>G</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo7_test6_strangeshape2() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card();
        mat[2][3] = new Card();
        mat[2][4] = new Card();

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card(GREEN);
        mat[3][2] = new Card();
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card();
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo7.checkMatch(mat));
    }
    /**
     * <p>Two group of six cards with the same color (corner case)</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo7_test7_strangeshape3() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card();
        mat[2][3] = new Card();
        mat[2][4] = new Card();

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card(GREEN);
        mat[3][2] = new Card(GREEN);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(GREEN);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo7.checkMatch(mat));
    }
}
