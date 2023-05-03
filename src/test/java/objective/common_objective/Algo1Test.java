package objective.common_objective;

import app.model.*;

import org.junit.*;

import static app.model.Color.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * class that test the first algorithm, six group of a couple of cards of the same type
 * <p>
 * test which are false are #2 #4 #6
 * <p>
 * test which are true are #1 #3 #5
 * <p>
 * test which check corner case: #8 #9 #10 #11 #12 #13
 * @author Faccincani, Ettori, Gumus
 */

public class Algo1Test {
    Algo_CO_1 algoCo1 = null;
    Card[][] mat = new Card[6][5];

    @Before // eseguita prima dei test
    public void setUp() {
        this.algoCo1 = new Algo_CO_1();
    }
    @After // eseguita dopo i test
    public void tearDown() {
        return;
    }


    /**
     *this test is used to test the following library:
     *<table border="1">
     * <tr><td>P</td><td>P</td><td>G</td><td>G</td><td>Y</td></tr>
     * <tr><td>B</td><td>B</td><td> </td><td>Y</td><td> </td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td> </td><td>C</td><td>C</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     */
    @Test
    public void algo1_test1_T_general() {
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
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo1.checkMatch(mat));
    }

    /**
     * this test is used to test the following library:
     * <table border="1">
     * <tr><td>P</td><td> </td><td> </td><td>G</td><td>Y</td></tr>
     * <tr><td>B</td><td> </td><td> </td><td>Y</td><td> </td></tr>
     * <tr><td> </td><td>C</td><td>Y</td><td> </td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td> </td><td>C</td><td>C</td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     */
    @Test // test generale, 5 coppie
    public void algo1_test2_F_general() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(BLUE);
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card();

        mat[2][0] = new Card();
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card();
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
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo1.checkMatch(mat));
    }

    /**
     * this test is used to test the following library:
     * <table border="1">
     * <tr><td>P</td><td> </td><td>G</td><td>G</td><td>Y</td></tr>
     * <tr><td>B</td><td>B</td><td>B</td><td>Y</td><td> </td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td> </td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td> </td><td>C</td><td> </td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td> </td><td>P</td></tr>
     * </table>
     */
    @Test // 3 in fila, vero
    public void algo1_test3_T_three() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);
        mat[1][0] = new Card(BLUE);
        mat[1][1] = new Card(BLUE);
        mat[1][2] = new Card(BLUE);
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card();
        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card();
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
        mat[4][4] = new Card();
        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card();
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo1.checkMatch(mat));
    }

    /**
     * this test is used to test the following library:
     * <table border="1">
     * <tr><td>P</td><td> </td><td> </td><td>G</td><td>Y</td></tr>
     * <tr><td>B</td><td>B</td><td>B</td><td>Y</td><td> </td></tr>
     * <tr><td>C</td><td>C</td><td>Y</td><td> </td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td> </td><td>C</td><td> </td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td> </td><td>P</td></tr>
     * </table>
     */
    @Test // 3 in fila, false
    public void algo1_test4_F_three() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(BLUE);
        mat[1][1] = new Card(BLUE);
        mat[1][2] = new Card(BLUE);
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card();
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
        mat[4][4] = new Card();

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card();
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo1.checkMatch(mat));
    }

    /**
     * this test is used to test the following library:
     * <table border="1">
     * <tr><td>P</td><td> </td><td>G</td><td>G</td><td>Y</td></tr>
     * <tr><td>B</td><td>B</td><td> </td><td>Y</td><td> </td></tr>
     * <tr><td>C</td><td> </td><td>Y</td><td>Y</td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td> </td><td>C</td><td> </td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td> </td><td>P</td></tr>
     * </table>
     */
    @Test // 3 in fila, vero
    public void algo1_test5_T_L() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(BLUE);
        mat[1][1] = new Card(BLUE);
        mat[1][2] = new Card();
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card();
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card(YELLOW);
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
        mat[4][4] = new Card();

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card();
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo1.checkMatch(mat));
    }

    /**
     * this test is used to test the following library:
     * <table border="1">
     * <tr><td>P</td><td> </td><td>G</td><td>G</td><td>Y</td></tr>
     * <tr><td>B</td><td> </td><td>B</td><td>Y</td><td> </td></tr>
     * <tr><td>C</td><td> </td><td>Y</td><td>Y</td><td>B</td></tr>
     * <tr><td>P</td><td>Y</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td> </td><td>C</td><td> </td></tr>
     * <tr><td>P</td><td>G</td><td>G</td><td> </td><td>P</td></tr>
     * </table>
     */
    @Test // L, falso
    public void algo1_test6_F_L() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(BLUE);
        mat[1][1] = new Card();
        mat[1][2] = new Card(BLUE);
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card();
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card(YELLOW);
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
        mat[4][4] = new Card();

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card();
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo1.checkMatch(mat));
    }

    /**
     * this test is used to test the following library:
     * <table border="1">
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td> </td><td> </td><td> </td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>B</td><td>Y</td><td>C</td><td>G</td></tr>
     * <tr><td>P</td><td>B</td><td>Y</td><td>C</td><td>G</td></tr>
     * </table>
     */
    @Test // 6 couple of 2 cards
    public void algo1_test7_6couple() {
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
        mat[3][0] = new Card();
        mat[3][1] = new Card();
        mat[3][2] = new Card();
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);
        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(BLUE);
        mat[4][2] = new Card(YELLOW);
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(GREEN);
        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(BLUE);
        mat[5][2] = new Card(YELLOW);
        mat[5][3] = new Card(CYAN);
        mat[5][4] = new Card(GREEN);

        assertTrue(algoCo1.checkMatch(mat));
    }

    /**
     * this test is used to test the following library:
     * <table border="1">
     * <tr><td>B</td><td>B</td><td> </td><td>B</td><td>B</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td> </td><td>B</td><td>B</td><td> </td><td> </td></tr>
     * <tr><td>B</td><td> </td><td> </td><td> </td><td> </td></tr>
     * <tr><td>B</td><td> </td><td>B</td><td> </td><td>B</td></tr>
     * <tr><td>B</td><td> </td><td>B</td><td> </td><td>B</td></tr>
     * </table>
     */
    @Test // 6 couple of 2 cards
    public void algo1_test8_6couple2() {
        mat[0][0] = new Card(BLUE);
        mat[0][1] = new Card(BLUE);
        mat[0][2] = new Card();
        mat[0][3] = new Card(BLUE);
        mat[0][4] = new Card(BLUE);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card();
        mat[2][1] = new Card(BLUE);
        mat[2][2] = new Card(BLUE);
        mat[2][3] = new Card();
        mat[2][4] = new Card();

        mat[3][0] = new Card(BLUE);
        mat[3][1] = new Card();
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card(BLUE);
        mat[4][1] = new Card();
        mat[4][2] = new Card(BLUE);
        mat[4][3] = new Card();
        mat[4][4] = new Card(BLUE);

        mat[5][0] = new Card(BLUE);
        mat[5][1] = new Card();
        mat[5][2] = new Card(BLUE);
        mat[5][3] = new Card();
        mat[5][4] = new Card(BLUE);

        assertTrue(algoCo1.checkMatch(mat));
    }

    /**
     * this test is used to test the following library:
     *<table border="1">
     * <tr><td>Y</td><td> </td><td> </td><td> </td><td> </td></tr>
     * <tr><td>Y</td><td>B</td><td>G</td><td>Y</td><td>B</td></tr>
     * <tr><td>G</td><td>Y</td><td>B</td><td>G</td><td>Y</td></tr>
     * <tr><td>B</td><td>G</td><td>Y</td><td>B</td><td>G</td></tr>
     * <tr><td>P</td><td>B</td><td>Y</td><td>C</td><td>C</td></tr>
     * <tr><td>P</td><td>B</td><td>Y</td><td>G</td><td>G</td></tr>
     * </table>
     */
    @Test // 6 couple of 3 cards
    public void algo1_test9_6couple3() {
        mat[0][0] = new Card(YELLOW);
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card(YELLOW);
        mat[1][1] = new Card(BLUE);
        mat[1][2] = new Card(GREEN);
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card(BLUE);

        mat[2][0] = new Card(GREEN);
        mat[2][1] = new Card(YELLOW);
        mat[2][2] = new Card(BLUE);
        mat[2][3] = new Card(GREEN);
        mat[2][4] = new Card(YELLOW);

        mat[3][0] = new Card(BLUE);
        mat[3][1] = new Card(GREEN);
        mat[3][2] = new Card(YELLOW);
        mat[3][3] = new Card(BLUE);
        mat[3][4] = new Card(GREEN);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(BLUE);
        mat[4][2] = new Card(YELLOW);
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(BLUE);
        mat[5][2] = new Card(YELLOW);
        mat[5][3] = new Card(GREEN);
        mat[5][4] = new Card(GREEN);

        assertTrue(algoCo1.checkMatch(mat));
    }

    /**
     * this test is used to test the following library:
     * <table border="1">
     * <tr><td>P</td><td> </td><td> </td><td> </td><td> </td></tr>
     * <tr><td>P</td><td>B</td><td>B</td><td>B</td><td> </td></tr>
     * <tr><td>P</td><td>B</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>B</td><td>Y</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>B</td><td>Y</td><td>C</td><td>G</td></tr>
     * <tr><td>P</td><td>B</td><td>Y</td><td>C</td><td>G</td></tr>
     * </table>
     */
    @Test // 6 couple of 2 or more cards
    public void algo1_test10_6couple4() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card(PINK);
        mat[1][1] = new Card(BLUE);
        mat[1][2] = new Card(BLUE);
        mat[1][3] = new Card(BLUE);
        mat[1][4] = new Card();

        mat[2][0] = new Card(PINK);
        mat[2][1] = new Card(BLUE);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card(BLUE);
        mat[3][2] = new Card(YELLOW);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(BLUE);
        mat[4][2] = new Card(YELLOW);
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(GREEN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(BLUE);
        mat[5][2] = new Card(YELLOW);
        mat[5][3] = new Card(CYAN);
        mat[5][4] = new Card(GREEN);

        assertTrue(algoCo1.checkMatch(mat));
    }

    /**
     * this test is used to test the following library:
     * <table border="1">
     * <tr><td>P</td><td> </td><td> </td><td> </td><td>P</td></tr>
     * <tr><td> </td><td>B</td><td>B</td><td>B</td><td> </td></tr>
     * <tr><td> </td><td>B</td><td>Y</td><td>B</td><td>B</td></tr>
     * <tr><td> </td><td>B</td><td>Y</td><td>P</td><td>P</td></tr>
     * <tr><td> </td><td>B</td><td>Y</td><td>C</td><td>G</td></tr>
     * <tr><td>P</td><td>B</td><td>Y</td><td>C</td><td>G</td></tr>
     * </table>
     */
    @Test // 5 couple of 2 or more cards
    public void algo1_test11_5couple2() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card(PINK);

        mat[1][0] = new Card();
        mat[1][1] = new Card(BLUE);
        mat[1][2] = new Card(BLUE);
        mat[1][3] = new Card(BLUE);
        mat[1][4] = new Card();

        mat[2][0] = new Card();
        mat[2][1] = new Card(BLUE);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card();
        mat[3][1] = new Card(BLUE);
        mat[3][2] = new Card(YELLOW);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card();
        mat[4][1] = new Card(BLUE);
        mat[4][2] = new Card(YELLOW);
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(GREEN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(BLUE);
        mat[5][2] = new Card(YELLOW);
        mat[5][3] = new Card(CYAN);
        mat[5][4] = new Card(GREEN);

        assertFalse(algoCo1.checkMatch(mat));
    }

    /**
     * this test is used to test the following library:
     * <table border="1">
     * <tr><td>C</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>B</td><td>C</td><td> </td><td> </td><td> </td></tr>
     * <tr><td>Y</td><td>G</td><td> </td><td> </td><td> </td></tr>
     * <tr><td>G</td><td>Y</td><td> </td><td> </td><td> </td></tr>
     * <tr><td>B</td><td>P</td><td> </td><td> </td><td> </td></tr>
     * <tr><td>P</td><td>B</td><td> </td><td> </td><td> </td></tr>
     * </table>
     */
    @Test // 6 diagonal couples of 2 cards
    public void algo1_test12_6diagonalcouple() {
        mat[0][0] = new Card(CYAN);
        mat[0][1] = new Card(BLUE);
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card(BLUE);
        mat[1][1] = new Card(CYAN);
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(YELLOW);
        mat[2][1] = new Card(GREEN);
        mat[2][2] = new Card();
        mat[2][3] = new Card();
        mat[2][4] = new Card();

        mat[3][0] = new Card(GREEN);
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card(BLUE);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card();
        mat[4][4] = new Card();

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(BLUE);
        mat[5][2] = new Card();
        mat[5][3] = new Card();
        mat[5][4] = new Card();

        assertFalse(algoCo1.checkMatch(mat));
    }

    /**
     * this test is used to test the following library:
     * <table border="1">
     * <tr><td>B</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>C</td><td>C</td><td> </td><td> </td><td> </td></tr>
     * <tr><td>G</td><td>G</td><td> </td><td> </td><td> </td></tr>
     * <tr><td>Y</td><td>Y</td><td> </td><td> </td><td> </td></tr>
     * <tr><td>C</td><td>C</td><td> </td><td> </td><td> </td></tr>
     * <tr><td>B</td><td>B</td><td> </td><td> </td><td> </td></tr>
     * </table>
     */
    @Test // 6 right couples
    public void algo1_test13_6rightcouple() {
        mat[0][0] = new Card(BLUE);
        mat[0][1] = new Card(BLUE);
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card(CYAN);
        mat[1][1] = new Card(CYAN);
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(GREEN);
        mat[2][1] = new Card(GREEN);
        mat[2][2] = new Card();
        mat[2][3] = new Card();
        mat[2][4] = new Card();

        mat[3][0] = new Card(YELLOW);
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card(CYAN);
        mat[4][1] = new Card(CYAN);
        mat[4][2] = new Card();
        mat[4][3] = new Card();
        mat[4][4] = new Card();

        mat[5][0] = new Card(BLUE);
        mat[5][1] = new Card(BLUE);
        mat[5][2] = new Card();
        mat[5][3] = new Card();
        mat[5][4] = new Card();

        assertTrue(algoCo1.checkMatch(mat));
    }

}