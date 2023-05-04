package objective.private_objective;

import app.model.Card;
import app.model.PrivateObjective;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static app.model.Color.*;
import static org.junit.Assert.assertEquals;
/**
 * Test the different number of goals and the different private objective
 * @author Furkan
 */

public class countMatchTest {
    Card[][] lib = null;
    @Before // eseguita prima dei test
    public void setUp(){
        this.lib = new Card[6][5];
    }
    @After // eseguita dopo i test
    public void tearDown() {
        return;
    }

    /**
     * 0 match with personalobjective<br>
     * testlibrary:<br>
     * <table border="1">
     * <caption>matrix</caption>
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>W</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table>
     * <br>
     * TestPrivateObjective:<br>
     * <table border="1">
     * <caption>matrix</caption>
     * <tr><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td></tr>
     * <tr><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td></tr>
     * <tr><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td></tr>
     * <tr><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td></tr>
     * <tr><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td></tr>
     * <tr><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td></tr>
     * </table>
     *
     */
    @Test
    public void countMatch_personalGoal_0match(){
        Card[][] mat = new Card[6][5];
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card(BLUE);
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card(GREEN);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card();
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card();

        mat[3][0] = new Card();
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card();
        mat[4][1] = new Card();
        mat[4][2] = new Card();
        mat[4][3] = new Card();
        mat[4][4] = new Card();

        mat[5][0] = new Card();
        mat[5][1] = new Card();
        mat[5][2] = new Card(CYAN);
        mat[5][3] = new Card();
        mat[5][4] = new Card();


        lib[0][0] = new Card(BLUE);
        lib[0][1] = new Card();
        lib[0][2] = new Card();
        lib[0][3] = new Card();
        lib[0][4] = new Card(YELLOW);

        lib[1][0] = new Card(BLUE);
        lib[1][1] = new Card();
        lib[1][2] = new Card();
        lib[1][3] = new Card();
        lib[1][4] = new Card(YELLOW);

        lib[2][0] = new Card(BLUE);
        lib[2][1] = new Card();
        lib[2][2] = new Card();
        lib[2][3] = new Card();
        lib[2][4] = new Card(YELLOW);

        lib[3][0] = new Card(BLUE);
        lib[3][1] = new Card();
        lib[3][2] = new Card();
        lib[3][3] = new Card();
        lib[3][4] = new Card(YELLOW);

        lib[4][0] = new Card(BLUE);
        lib[4][1] = new Card();
        lib[4][2] = new Card();
        lib[4][3] = new Card();
        lib[4][4] = new Card(YELLOW);

        lib[5][0] = new Card(BLUE);
        lib[5][1] = new Card();
        lib[5][2] = new Card();
        lib[5][3] = new Card();
        lib[5][4] = new Card(YELLOW);

        assertEquals(new PrivateObjective(mat, 1).countPoints(lib), 0);
    }

    /**2 match<br>
     * testlibrary: <br>
     * <table border="1">
     * <caption>matrix</caption>
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>W</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table>
     * <br>
     * testpersonalobjective <br>
     * <table border="1">
     * <caption>matrix</caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>Y</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>W</td><td>C</td><td>C</td></tr>
     * <tr><td>P</td><td>G</td><td>C</td><td>P</td><td>P</td></tr>
     * </table>
     *
     */
    @Test
    public void countMatch_personalGoal_2match(){
        Card[][] mat = new Card[6][5];
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card(BLUE);
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card(GREEN);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card();
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card();

        mat[3][0] = new Card();
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card();
        mat[4][1] = new Card();
        mat[4][2] = new Card();
        mat[4][3] = new Card();
        mat[4][4] = new Card();

        mat[5][0] = new Card();
        mat[5][1] = new Card();
        mat[5][2] = new Card(CYAN);
        mat[5][3] = new Card();
        mat[5][4] = new Card();


        lib[0][0] = new Card();
        lib[0][1] = new Card();
        lib[0][2] = new Card();
        lib[0][3] = new Card();
        lib[0][4] = new Card();

        lib[1][0] = new Card();
        lib[1][1] = new Card();
        lib[1][2] = new Card();
        lib[1][3] = new Card();
        lib[1][4] = new Card();

        lib[2][0] = new Card();
        lib[2][1] = new Card();
        lib[2][2] = new Card();
        lib[2][3] = new Card();
        lib[2][4] = new Card();

        lib[3][0] = new Card(PINK);
        lib[3][1] = new Card(YELLOW);
        lib[3][2] = new Card(GREEN);
        lib[3][3] = new Card(PINK);
        lib[3][4] = new Card(PINK);

        lib[4][0] = new Card(PINK);
        lib[4][1] = new Card(PINK);
        lib[4][2] = new Card(WHITE);
        lib[4][3] = new Card(CYAN);
        lib[4][4] = new Card(CYAN);

        lib[5][0] = new Card(PINK);
        lib[5][1] = new Card(GREEN);
        lib[5][2] = new Card(CYAN);
        lib[5][3] = new Card(PINK);
        lib[5][4] = new Card(PINK);

        assertEquals(new PrivateObjective(mat, 1).countPoints(lib), 2);
    }

    /**
     * 5 match<br>
     * testlibrary:
     * <table border="1">
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>W</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table>
     * <br>testpersonalobjective:<br>
     *<table border="1">
     * <tr><td>P</td><td>Y</td><td>B</td><td>Y</td><td>Y</td></tr>
     * <tr><td>B</td><td>Y</td><td>G</td><td>G</td><td>G</td></tr>
     * <tr><td>B</td><td>Y</td><td>G</td><td>W</td><td>W</td></tr>
     * <tr><td>B</td><td>B</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>B</td><td>Y</td><td>W</td><td>C</td><td>C</td></tr>
     * <tr><td>B</td><td>Y</td><td>C</td><td>C</td><td>P</td></tr>
     * </table>
     */
    @Test
    public void countMatch_personalGoal_5match(){
        Card[][] mat = new Card[6][5];
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card(BLUE);
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card(GREEN);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card();
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card();

        mat[3][0] = new Card();
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card();
        mat[4][1] = new Card();
        mat[4][2] = new Card();
        mat[4][3] = new Card();
        mat[4][4] = new Card();

        mat[5][0] = new Card();
        mat[5][1] = new Card();
        mat[5][2] = new Card(CYAN);
        mat[5][3] = new Card();
        mat[5][4] = new Card();


        lib[0][0] = new Card(PINK);
        lib[0][1] = new Card(YELLOW);
        lib[0][2] = new Card(BLUE);
        lib[0][3] = new Card(YELLOW);
        lib[0][4] = new Card(YELLOW);

        lib[1][0] = new Card(BLUE);
        lib[1][1] = new Card(YELLOW);
        lib[1][2] = new Card(GREEN);
        lib[1][3] = new Card(GREEN);
        lib[1][4] = new Card(GREEN);

        lib[2][0] = new Card(BLUE);
        lib[2][1] = new Card(YELLOW);
        lib[2][2] = new Card(GREEN);
        lib[2][3] = new Card(WHITE);
        lib[2][4] = new Card(WHITE);

        lib[3][0] = new Card(BLUE);
        lib[3][1] = new Card(BLUE);
        lib[3][2] = new Card(GREEN);
        lib[3][3] = new Card(PINK);
        lib[3][4] = new Card(PINK);

        lib[4][0] = new Card(BLUE);
        lib[4][1] = new Card(YELLOW);
        lib[4][2] = new Card(WHITE);
        lib[4][3] = new Card(CYAN);
        lib[4][4] = new Card(CYAN);

        lib[5][0] = new Card(BLUE);
        lib[5][1] = new Card(YELLOW);
        lib[5][2] = new Card(CYAN);
        lib[5][3] = new Card(CYAN);
        lib[5][4] = new Card(PINK);

        assertEquals(new PrivateObjective(mat, 1).countPoints(lib), 9);
    }

    /**
     * 6 match:<br>
     * testlibrary:<br>
     *<table border="1">
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>W</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table>
     * <br>testpersonalobjective:<br>
     * <table border="1">
     * <tr><td>P</td><td>Y</td><td>B</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>B</td><td>Y</td><td>G</td><td>G</td><td>G</td></tr>
     * <tr><td>B</td><td>Y</td><td>G</td><td>W</td><td>G</td></tr>
     * <tr><td>B</td><td>Y</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>B</td><td>Y</td><td>W</td><td>C</td><td>C</td></tr>
     * <tr><td>B</td><td>Y</td><td>C</td><td>C</td><td>P</td></tr>
     * </table>
     */
    @Test
    public void countMatch_personalGoal_6match(){
        Card[][] mat = new Card[6][5];
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card(BLUE);
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card(GREEN);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card();
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card();

        mat[3][0] = new Card();
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card();
        mat[4][1] = new Card();
        mat[4][2] = new Card();
        mat[4][3] = new Card();
        mat[4][4] = new Card();

        mat[5][0] = new Card();
        mat[5][1] = new Card();
        mat[5][2] = new Card(CYAN);
        mat[5][3] = new Card();
        mat[5][4] = new Card();


        lib[0][0] = new Card(PINK);
        lib[0][1] = new Card(YELLOW);
        lib[0][2] = new Card(BLUE);
        lib[0][3] = new Card(YELLOW);
        lib[0][4] = new Card();

        lib[1][0] = new Card(BLUE);
        lib[1][1] = new Card(YELLOW);
        lib[1][2] = new Card(GREEN);
        lib[1][3] = new Card(GREEN);
        lib[1][4] = new Card(GREEN);

        lib[2][0] = new Card(BLUE);
        lib[2][1] = new Card(YELLOW);
        lib[2][2] = new Card(GREEN);
        lib[2][3] = new Card(WHITE);
        lib[2][4] = new Card(GREEN);

        lib[3][0] = new Card(BLUE);
        lib[3][1] = new Card(YELLOW);
        lib[3][2] = new Card(GREEN);
        lib[3][3] = new Card(PINK);
        lib[3][4] = new Card(PINK);

        lib[4][0] = new Card(BLUE);
        lib[4][1] = new Card(YELLOW);
        lib[4][2] = new Card(WHITE);
        lib[4][3] = new Card(CYAN);
        lib[4][4] = new Card(CYAN);

        lib[5][0] = new Card(BLUE);
        lib[5][1] = new Card(YELLOW);
        lib[5][2] = new Card(CYAN);
        lib[5][3] = new Card(CYAN);
        lib[5][4] = new Card(PINK);

        assertEquals(new PrivateObjective(mat, 1).countPoints(lib), 12);
    }

    /**
     * Personalgoalempty:<br>
     * testlibrary:<br>
     * <table border="1">
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>W</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table>
     * <br>testpersonalgoal:<br>
     * <table border="1">
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table>
     */
    @Test
    public void countMatch_personalGoal_all_empty_1(){
        Card[][] mat = new Card[6][5];
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card(BLUE);
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card(GREEN);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card();
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card();

        mat[3][0] = new Card();
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card();
        mat[4][1] = new Card();
        mat[4][2] = new Card();
        mat[4][3] = new Card();
        mat[4][4] = new Card();

        mat[5][0] = new Card();
        mat[5][1] = new Card();
        mat[5][2] = new Card(CYAN);
        mat[5][3] = new Card();
        mat[5][4] = new Card();


        lib[0][0] = new Card();
        lib[0][1] = new Card();
        lib[0][2] = new Card();
        lib[0][3] = new Card();
        lib[0][4] = new Card();

        lib[1][0] = new Card();
        lib[1][1] = new Card();
        lib[1][2] = new Card();
        lib[1][3] = new Card();
        lib[1][4] = new Card();

        lib[2][0] = new Card();
        lib[2][1] = new Card();
        lib[2][2] = new Card();
        lib[2][3] = new Card();
        lib[2][4] = new Card();

        lib[3][0] = new Card();
        lib[3][1] = new Card();
        lib[3][2] = new Card();
        lib[3][3] = new Card();
        lib[3][4] = new Card();

        lib[4][0] = new Card();
        lib[4][1] = new Card();
        lib[4][2] = new Card();
        lib[4][3] = new Card();
        lib[4][4] = new Card();

        lib[5][0] = new Card();
        lib[5][1] = new Card();
        lib[5][2] = new Card();
        lib[5][3] = new Card();
        lib[5][4] = new Card();

        assertEquals(new PrivateObjective(mat, 1).countPoints(lib), 0);
    }

    /**
     * testlibrary:<br>
     *<table border="1">
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>W</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>C</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table><br>
     * testpersonalobject:<br>
     *<table border="1">
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table>
     */
    @Test
    public void countMatch_personalGoal_all_empty_2(){
        Card[][] mat = new Card[6][5];
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card(BLUE);
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card(GREEN);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card();
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card();

        mat[3][0] = new Card();
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card();
        mat[4][1] = new Card();
        mat[4][2] = new Card();
        mat[4][3] = new Card();
        mat[4][4] = new Card();

        mat[5][0] = new Card();
        mat[5][1] = new Card();
        mat[5][2] = new Card(CYAN);
        mat[5][3] = new Card();
        mat[5][4] = new Card();


        lib[0][0] = new Card();
        lib[0][1] = new Card();
        lib[0][2] = new Card();
        lib[0][3] = new Card();
        lib[0][4] = new Card();

        lib[1][0] = new Card();
        lib[1][1] = new Card();
        lib[1][2] = new Card();
        lib[1][3] = new Card();
        lib[1][4] = new Card();

        lib[2][0] = new Card();
        lib[2][1] = new Card();
        lib[2][2] = new Card();
        lib[2][3] = new Card();
        lib[2][4] = new Card();

        lib[3][0] = new Card();
        lib[3][1] = new Card();
        lib[3][2] = new Card();
        lib[3][3] = new Card();
        lib[3][4] = new Card();

        lib[4][0] = new Card();
        lib[4][1] = new Card();
        lib[4][2] = new Card();
        lib[4][3] = new Card();
        lib[4][4] = new Card();

        lib[5][0] = new Card();
        lib[5][1] = new Card();
        lib[5][2] = new Card();
        lib[5][3] = new Card();
        lib[5][4] = new Card();

        assertEquals(new PrivateObjective(mat, 1).countPoints(lib), 0);
    }

    /**
     * testlibrary: <br>
     *<table border="1">
     * <tr><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>W</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table><br>
     * testpersonalobjective:<br>
     * <table border="1">
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>Y</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table>
     */
    @Test
    public void countMatch_personalGoal_all_empty_3(){
        Card[][] mat = new Card[6][5];
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card(BLUE);
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card(GREEN);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card();
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card();

        mat[3][0] = new Card();
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card();
        mat[3][4] = new Card();

        mat[4][0] = new Card();
        mat[4][1] = new Card();
        mat[4][2] = new Card();
        mat[4][3] = new Card();
        mat[4][4] = new Card();

        mat[5][0] = new Card();
        mat[5][1] = new Card();
        mat[5][2] = new Card();
        mat[5][3] = new Card();
        mat[5][4] = new Card();


        lib[0][0] = new Card();
        lib[0][1] = new Card();
        lib[0][2] = new Card();
        lib[0][3] = new Card();
        lib[0][4] = new Card();

        lib[1][0] = new Card();
        lib[1][1] = new Card();
        lib[1][2] = new Card();
        lib[1][3] = new Card();
        lib[1][4] = new Card();

        lib[2][0] = new Card(YELLOW);
        lib[2][1] = new Card();
        lib[2][2] = new Card();
        lib[2][3] = new Card();
        lib[2][4] = new Card();

        lib[3][0] = new Card();
        lib[3][1] = new Card();
        lib[3][2] = new Card();
        lib[3][3] = new Card();
        lib[3][4] = new Card();

        lib[4][0] = new Card();
        lib[4][1] = new Card();
        lib[4][2] = new Card();
        lib[4][3] = new Card();
        lib[4][4] = new Card();

        lib[5][0] = new Card();
        lib[5][1] = new Card();
        lib[5][2] = new Card();
        lib[5][3] = new Card();
        lib[5][4] = new Card();

        assertEquals(new PrivateObjective(mat, 1).countPoints(lib), 0);
    }
}
