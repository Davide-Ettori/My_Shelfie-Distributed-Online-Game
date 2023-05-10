package objective.common_objective;


import it.polimi.ingsw.model.Algo_CO_2;
import it.polimi.ingsw.model.Card;
import org.junit.*;
import static it.polimi.ingsw.model.Color.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * class that test the second algorithm, five cards of the same type that form a diagonal.
 * @author Faccincani, Ettori , Gumus
 */

public class Algo2Test {
    Algo_CO_2 algoCo2 = null;
    Card[][] mat = new Card[6][5];
    /**
     * method executed before every test that create a new algorithm
     * @author Ettori
     */
    @Before
    public void setUp() {
        this.algoCo2 = new Algo_CO_2();

    }

    /**
     * <p>Diagonal start on the left bottom of the library</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>G</td><td>G</td><td>G</td><td>G</td><td>Y</td></tr>
     * <tr><td>P</td><td>B</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td></tr>
     * <tr><td>C</td><td>C</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>C</td></tr>
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo2_test1_bassosx() {
        mat[0][0] = new Card(GREEN);
        mat[0][1] = new Card(GREEN);
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(PINK);
        mat[1][1] = new Card(BLUE);
        mat[1][2] = new Card(YELLOW);
        mat[1][3] = new Card();
        mat[1][4] = new Card(PINK);

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card();
        mat[2][3] = new Card(PINK);
        mat[2][4] = new Card();

        mat[3][0] = new Card(YELLOW);
        mat[3][1] = new Card();
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card();
        mat[3][4] = new Card(YELLOW);

        mat[4][0] = new Card();
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card();
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo2.checkMatch(mat));

    }

    /**
     * <p>Diagonal start on the right bottom of the library</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>G</td><td>G</td><td>P</td></tr>
     * <tr><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td><td>G</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo2_test2_bassodx() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(PINK);

        mat[1][0] = new Card(YELLOW);
        mat[1][1] = new Card();
        mat[1][2] = new Card(GREEN);
        mat[1][3] = new Card(GREEN);
        mat[1][4] = new Card(PINK);

        mat[2][0] = new Card();
        mat[2][1] = new Card(YELLOW);
        mat[2][2] = new Card();
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card();
        mat[3][2] = new Card(YELLOW);
        mat[3][3] = new Card();
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(YELLOW);
        mat[4][4] = new Card();

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card();
        mat[5][4] = new Card(YELLOW);

        assertTrue(algoCo2.checkMatch(mat));

    }

    /**
     * <p>Diagonal start on the right angle of the second row of the library</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>G</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td></tr>
     * <tr><td>G</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td></tr>
     * <tr><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td><td>G</td><td>P</td></tr>
     * <tr><td>G</td><td>G</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo2_test3_unodx() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card();
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(GREEN);
        mat[1][1] = new Card(PINK);
        mat[1][2] = new Card();
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card();
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card();
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card();
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(YELLOW);
        mat[4][1] = new Card();
        mat[4][2] = new Card(GREEN);
        mat[4][3] = new Card(GREEN);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(GREEN);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo2.checkMatch(mat));

    }

    /**
     * <p>Diagonal start on the left angle of the second row of the library</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td><td>G</td><td>Y</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>Y</td></tr>
     * <tr><td>C</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td></tr>
     * <tr><td>G</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>Y</td><td>Y</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td></tr>
     * <tr><td>Y</td><td>G</td><td>G</td><td>Y</td><td>Y</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo2_test4_unosx() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card();
        mat[1][1] = new Card(PINK);
        mat[1][2] = new Card();
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card(YELLOW);

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card();
        mat[2][2] = new Card(PINK);
        mat[2][3] = new Card();
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(GREEN);
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card();

        mat[4][0] = new Card(YELLOW);
        mat[4][1] = new Card(YELLOW);
        mat[4][2] = new Card(YELLOW);
        mat[4][3] = new Card();
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(YELLOW);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(YELLOW);
        mat[5][4] = new Card(YELLOW);

        assertTrue(algoCo2.checkMatch(mat));

    }

    //false

    /**
     * <p>Diagonal start on the left bottom of the library (False)</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>G</td><td>G</td><td>G</td><td>G</td><td>Y</td></tr>
     * <tr><td>P</td><td>B</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td></tr>
     * <tr><td>C</td><td>C</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>C</td></tr>
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo2_test5_F_bassosx() {
        mat[0][0] = new Card(GREEN);
        mat[0][1] = new Card(GREEN);
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(PINK);
        mat[1][1] = new Card(BLUE);
        mat[1][2] = new Card(YELLOW);
        mat[1][3] = new Card();
        mat[1][4] = new Card(PINK);

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card();
        mat[2][3] = new Card(PINK);
        mat[2][4] = new Card();

        mat[3][0] = new Card(YELLOW);
        mat[3][1] = new Card();
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card();
        mat[3][4] = new Card(YELLOW);

        mat[4][0] = new Card();
        mat[4][1] = new Card();
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card();
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo2.checkMatch(mat));

    }

    /**
     * <p>Diagonal start on the right bottom of the library (False)</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>G</td><td>G</td><td>P</td></tr>
     * <tr><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td><td>G</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo2_test6_F_bassodx() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(PINK);

        mat[1][0] = new Card(YELLOW);
        mat[1][1] = new Card();
        mat[1][2] = new Card(GREEN);
        mat[1][3] = new Card(GREEN);
        mat[1][4] = new Card(PINK);

        mat[2][0] = new Card();
        mat[2][1] = new Card(YELLOW);
        mat[2][2] = new Card();
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card();
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(YELLOW);
        mat[4][4] = new Card();

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card();
        mat[5][4] = new Card(YELLOW);

        assertFalse(algoCo2.checkMatch(mat));

    }

    /**
     * <p>Diagonal start on the right angle of the second row of the library (False)</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>G</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td></tr>
     * <tr><td>G</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td></tr>
     * <tr><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td><td>G</td><td>P</td></tr>
     * <tr><td>G</td><td>G</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo2_test7_F_unodx() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card();
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(GREEN);
        mat[1][1] = new Card(PINK);
        mat[1][2] = new Card();
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card();
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card();
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card();
        mat[3][1] = new Card();
        mat[3][2] = new Card();
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(YELLOW);
        mat[4][1] = new Card();
        mat[4][2] = new Card(GREEN);
        mat[4][3] = new Card(GREEN);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(GREEN);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo2.checkMatch(mat));

    }

    /**
     * <p>Diagonal start on the left angle of the second row of the library (False)</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td><td>G</td><td>Y</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>Y</td></tr>
     * <tr><td>C</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td></tr>
     * <tr><td>G</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>Y</td><td>Y</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td></tr>
     * <tr><td>Y</td><td>G</td><td>G</td><td>Y</td><td>Y</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo2_test8_F_unosx() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card();
        mat[1][1] = new Card(PINK);
        mat[1][2] = new Card();
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card(YELLOW);

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card();
        mat[2][2] = new Card(PINK);
        mat[2][3] = new Card();
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(GREEN);
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card(YELLOW);
        mat[4][1] = new Card(YELLOW);
        mat[4][2] = new Card(YELLOW);
        mat[4][3] = new Card();
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(YELLOW);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(YELLOW);
        mat[5][4] = new Card(YELLOW);

        assertFalse(algoCo2.checkMatch(mat));

    }
}