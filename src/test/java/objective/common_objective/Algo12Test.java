package objective.common_objective;

import it.polimi.ingsw.model.Algo_CO_12;
import it.polimi.ingsw.model.Card;
import org.junit.Before;
import org.junit.Test;
import static it.polimi.ingsw.model.Color.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * class that test the twelfth algorithm, five columns of increasing height or descending:
 * starting from the first column left or right, each successive column it must be formed by an extra tile.
 * Tiles can be of any type.
 * @author Faccincani, Ettori , Gumus
 */
public class Algo12Test {
    Algo_CO_12 algoCo12 = null;

    Card[][] mat = new Card[6][5];
    /**
     * method executed before every test that create a new algorithm
     * @author Ettori
     */
    @Before
    public void setUp() {
        this.algoCo12 = new Algo_CO_12();
    }

    /**
     * <p>Five columns descending</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo12_test1_general_decrescent() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card(PINK);
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(PINK);
        mat[2][1] = new Card(PINK);
        mat[2][2] = new Card();
        mat[2][3] = new Card();
        mat[2][4] = new Card();

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card(PINK);
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card();

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo12.checkMatch(mat));
    }

    /**
     * <p>Five columns descending: not decrease by one cards</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test // test 1
    public void algo12_test2_general_F_decrescent() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card(PINK);
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(PINK);
        mat[2][1] = new Card(PINK);
        mat[2][2] = new Card();
        mat[2][3] = new Card();
        mat[2][4] = new Card();

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card(PINK);
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card();

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo12.checkMatch(mat));
    }

    /**
     * <p>Five columns descending whit different colors</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>W</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>W</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>P</td><td>W</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>W</td><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>W</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo12_test3_general_digdecrescent() {
        mat[0][0] = new Card(WHITE);
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card(WHITE);
        mat[1][1] = new Card(PINK);
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(PINK);
        mat[2][1] = new Card(PINK);
        mat[2][2] = new Card(WHITE);
        mat[2][3] = new Card();
        mat[2][4] = new Card();

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card(WHITE);
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card();

        mat[4][0] = new Card(WHITE);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo12.checkMatch(mat));
    }

    /**
     * <p>Five columns descending: not decrease correctly</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>W</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>W</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>P</td><td>W</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>W</td><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>W</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test // test 1
    public void algo12_test4_general_F_digdecrescent() {
        mat[0][0] = new Card(WHITE);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card(WHITE);
        mat[1][1] = new Card(PINK);
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(PINK);
        mat[2][1] = new Card(PINK);
        mat[2][2] = new Card(WHITE);
        mat[2][3] = new Card();
        mat[2][4] = new Card();

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card(WHITE);
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card();

        mat[4][0] = new Card(WHITE);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo12.checkMatch(mat));
    }

    /**
     * <p>Five columns crescent with different colors</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>W</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>W</td><td>B</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>W</td><td>W</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>W</td><td>P</td><td>P</td><td>Y</td></tr>
     * <tr><td>W</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo12_test5_general_crescent() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card(WHITE);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card(WHITE);
        mat[1][4] = new Card(BLUE);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card(WHITE);
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card(PINK);

        mat[3][0] = new Card();
        mat[3][1] = new Card(WHITE);
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(YELLOW);

        mat[4][0] = new Card(WHITE);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo12.checkMatch(mat));
    }

    /**
     * <p>Five columns crescent: grows wrongly</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>W</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>W</td><td>B</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>G</td><td>W</td><td>W</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>W</td><td>P</td><td>P</td><td>Y</td></tr>
     * <tr><td>W</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test // test 1
    public void algo12_test6_general_F_crescent() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card(WHITE);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card(WHITE);
        mat[1][4] = new Card(BLUE);

        mat[2][0] = new Card();
        mat[2][1] = new Card(GREEN);
        mat[2][2] = new Card(WHITE);
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card(PINK);

        mat[3][0] = new Card();
        mat[3][1] = new Card(WHITE);
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(YELLOW);

        mat[4][0] = new Card(WHITE);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo12.checkMatch(mat));
    }

    /**
     * <p>Five columns crescent from row 1 (True)</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>B</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>W</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td><td>P</td><td>Y</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test // test 1
    public void algo12_test7_general_digcrescent() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card(PINK);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card(PINK);
        mat[1][4] = new Card(BLUE);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card(PINK);
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card(PINK);

        mat[3][0] = new Card();
        mat[3][1] = new Card(PINK);
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(YELLOW);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo12.checkMatch(mat));
    }

    /**
     * <p>Five columns crescent from row 1 (False)</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>G</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>B</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>W</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td><td>P</td><td>Y</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test // test 1
    public void algo12_test8_general_F_digcrescent(){
        mat[0][0] = new Card();
        mat[0][1] = new Card(GREEN);
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card(PINK);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card(PINK);
        mat[1][4] = new Card(BLUE);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card(PINK);
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card(PINK);

        mat[3][0] = new Card();
        mat[3][1] = new Card(PINK);
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(YELLOW);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo12.checkMatch(mat));
    }

    /**
     * <p>?</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>W</td><td>W</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>B</td><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo12_test9_general_dicrescent2(){
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

        mat[2][0] = new Card(BLUE);
        mat[2][1] = new Card();
        mat[2][2] = new Card();
        mat[2][3] = new Card();
        mat[2][4] = new Card();

        mat[3][0] = new Card(WHITE);
        mat[3][1] = new Card(WHITE);
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card(BLUE);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card();
        mat[4][4] = new Card();

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card();

        assertTrue(algoCo12.checkMatch(mat));
    }

    /**
     * <p>Less than five columns</p>
     * <p></p>
     * <p> testing library:
     * <table border="1">
     *     <caption>matrix</caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>W</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>B</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table>
     * @author Faccincani, Ettori , Gumus
     */
    @Test
    public void algo12_test10_general_dicrescent3(){
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

        mat[3][0] = new Card(WHITE);
        mat[3][1] = new Card();
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card(BLUE);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card();
        mat[4][4] = new Card();

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card();
        mat[5][4] = new Card();

        assertFalse(algoCo12.checkMatch(mat));
    }
}
