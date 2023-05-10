package objective.common_objective;

import it.polimi.ingsw.model.Algo_CO_5;
import it.polimi.ingsw.model.Card;
import org.junit.Before;
import org.junit.Test;
import static it.polimi.ingsw.model.Color.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * class that test the fifth algorithm, four column have four element of the same color (different column can have different colors) cards
 * @author Faccincani, Ettori , Gumus
 */

public class Algo5Test {
    Algo_CO_5 algoCo5 = null;

    Card[][] mat = new Card[6][5];
    /**
     * method executed before every test that create a new algorithm
     * @author Ettori
     */
    @Before
    public void setUp() {
        this.algoCo5 = new Algo_CO_5();
    }

    /**
     * <p>Four columns with four element of the same color of cards</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>Y</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td>C</td><td>C</td><td>G</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>C</td></tr>
     * <tr><td>G</td><td>G</td><td>G</td><td>G</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo5_test1_T_general() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(PINK);
        mat[0][3] = new Card(PINK);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(CYAN);
        mat[3][1] = new Card(CYAN);
        mat[3][2] = new Card(GREEN);
        mat[3][3] = new Card(BLUE);
        mat[3][4] = new Card(BLUE);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(GREEN);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(GREEN);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo5.checkMatch(mat));
    }

    /**
     * <p>Four columns with four element with different colors of cards</p>
     * <p></p>
     * <p> testing library:
     *<table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>Y</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td>C</td><td>C</td><td>G</td><td>B</td><td>B</td></tr>
     * <tr><td>G</td><td>G</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>C</td></tr>
     * <tr><td>G</td><td>G</td><td>G</td><td>G</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo5_test2_F() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card();
        mat[0][3] = new Card(PINK);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(CYAN);
        mat[3][1] = new Card(CYAN);
        mat[3][2] = new Card(GREEN);
        mat[3][3] = new Card(BLUE);
        mat[3][4] = new Card(BLUE);

        mat[4][0] = new Card(GREEN);
        mat[4][1] = new Card(GREEN);
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(GREEN);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(GREEN);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo5.checkMatch(mat));
    }

    /**
     * <p>Four columns with more than four element with the same color of cards</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>Y</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>B</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td></tr>
     * <tr><td>B</td><td>B</td><td>Y</td><td>C</td><td>C</td></tr>
     * <tr><td>B</td><td>B</td><td>Y</td><td>C</td><td>G</td></tr>
     * <tr><td>G</td><td>G</td><td>G</td><td>G</td><td>G</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo5_test3_4groupstrue() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card();
        mat[0][3] = new Card(PINK);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card(YELLOW);
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(BLUE);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card();
        mat[2][4] = new Card(CYAN);

        mat[3][0] = new Card(BLUE);
        mat[3][1] = new Card(BLUE);
        mat[3][2] = new Card(YELLOW);
        mat[3][3] = new Card(CYAN);
        mat[3][4] = new Card(CYAN);

        mat[4][0] = new Card(BLUE);
        mat[4][1] = new Card(BLUE);
        mat[4][2] = new Card(YELLOW);
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(GREEN);

        mat[5][0] = new Card(GREEN);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(GREEN);
        mat[5][4] = new Card(GREEN);

        assertTrue(algoCo5.checkMatch(mat));
    }

    /**
     * <p>Threee columns with four element with the same color of cards</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>Y</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>B</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>Y</td><td>C</td><td>C</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>Y</td><td>C</td><td>G</td></tr>
     * <tr><td>G</td><td>G</td><td>G</td><td>G</td><td>G</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo5_test4_3groupsfalse() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card();
        mat[0][3] = new Card(PINK);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card(YELLOW);
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(BLUE);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card();
        mat[2][4] = new Card(CYAN);

        mat[3][0] = new Card();
        mat[3][1] = new Card(BLUE);
        mat[3][2] = new Card(YELLOW);
        mat[3][3] = new Card(CYAN);
        mat[3][4] = new Card(CYAN);

        mat[4][0] = new Card();
        mat[4][1] = new Card(BLUE);
        mat[4][2] = new Card(YELLOW);
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(GREEN);

        mat[5][0] = new Card(GREEN);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(GREEN);
        mat[5][4] = new Card(GREEN);

        assertFalse(algoCo5.checkMatch(mat));
    }

    /**
     * <p>Three columns with more than four element with the same color of cards</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>B</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>B</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>Y</td><td>C</td><td>C</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>Y</td><td>C</td><td>G</td></tr>
     * <tr><td>G</td><td>G</td><td>G</td><td>G</td><td>G</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo5_test5_3groupsfalse2() {
        mat[0][0] = new Card(BLUE);
        mat[0][1] = new Card(BLUE);
        mat[0][2] = new Card();
        mat[0][3] = new Card(BLUE);
        mat[0][4] = new Card(BLUE);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card(YELLOW);
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card();
        mat[2][1] = new Card(BLUE);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card();
        mat[2][4] = new Card(CYAN);

        mat[3][0] = new Card();
        mat[3][1] = new Card(BLUE);
        mat[3][2] = new Card(YELLOW);
        mat[3][3] = new Card(CYAN);
        mat[3][4] = new Card(CYAN);

        mat[4][0] = new Card();
        mat[4][1] = new Card(BLUE);
        mat[4][2] = new Card(YELLOW);
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(GREEN);

        mat[5][0] = new Card(GREEN);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(GREEN);
        mat[5][4] = new Card(GREEN);

        assertFalse(algoCo5.checkMatch(mat));
    }
}
