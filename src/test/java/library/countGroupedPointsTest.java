package library;

import it.polimi.ingsw.model.Card;
import org.junit.*;
import static it.polimi.ingsw.model.Color.*;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.model.Library;

public class countGroupedPointsTest {
    Library lib = null;
    /**
     * method executed before every test that create a new library
     * @author Giammusso
     */
    @Before
    public void setUp(){
        this.lib = new Library("");

    }

    /**
     * <table border="1">
     * <caption> matrix </caption>
     * <tr><td>P</td><td>P</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>P</td><td>B</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>B</td><td>B</td><td>B</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>B</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     */
    @Test // test 1
    public void countPoints_normal_correct(){
        lib.gameLibrary[0][0] = new Card(PINK);
        lib.gameLibrary[0][1] = new Card(PINK);
        lib.gameLibrary[0][2] = new Card();
        lib.gameLibrary[0][3] = new Card();
        lib.gameLibrary[0][4] = new Card();

        lib.gameLibrary[1][0] = new Card(PINK);
        lib.gameLibrary[1][1] = new Card(BLUE);
        lib.gameLibrary[1][2] = new Card();
        lib.gameLibrary[1][3] = new Card();
        lib.gameLibrary[1][4] = new Card();

        lib.gameLibrary[2][0] = new Card(BLUE);
        lib.gameLibrary[2][1] = new Card(BLUE);
        lib.gameLibrary[2][2] = new Card(BLUE);
        lib.gameLibrary[2][3] = new Card(BLUE);
        lib.gameLibrary[2][4] = new Card(BLUE);

        lib.gameLibrary[3][0] = new Card(PINK);
        lib.gameLibrary[3][1] = new Card(BLUE);
        lib.gameLibrary[3][2] = new Card(GREEN);
        lib.gameLibrary[3][3] = new Card(PINK);
        lib.gameLibrary[3][4] = new Card(PINK);

        lib.gameLibrary[4][0] = new Card(PINK);
        lib.gameLibrary[4][1] = new Card(PINK);
        lib.gameLibrary[4][2] = new Card(GREEN);
        lib.gameLibrary[4][3] = new Card(PINK);
        lib.gameLibrary[4][4] = new Card(PINK);

        lib.gameLibrary[5][0] = new Card(PINK);
        lib.gameLibrary[5][1] = new Card(PINK);
        lib.gameLibrary[5][2] = new Card(GREEN);
        lib.gameLibrary[5][3] = new Card(PINK);
        lib.gameLibrary[5][4] = new Card(PINK);

        assertEquals(lib.countGroupedPoints(), 25);
    }

    /**
     * <table border="1">
     * <caption> matrix </caption>
     * <tr><td>B</td><td>B</td><td>G</td><td>B</td><td>G</td></tr>
     * <tr><td>B</td><td>G</td><td>B</td><td>G</td><td>B</td></tr>
     * <tr><td>G</td><td>B</td><td>G</td><td>B</td><td>G</td></tr>
     * <tr><td>B</td><td>G</td><td>B</td><td>G</td><td>P</td></tr>
     * <tr><td>G</td><td>B</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>B</td><td>G</td><td>P</td><td>P</td><td>P</td></tr>
     * </table>
     */
    @Test
    public void countPoints_diagonal_correct(){
        lib.gameLibrary[0][0] = new Card(BLUE);
        lib.gameLibrary[0][1] = new Card(BLUE);
        lib.gameLibrary[0][2] = new Card(GREEN);
        lib.gameLibrary[0][3] = new Card(BLUE);
        lib.gameLibrary[0][4] = new Card(GREEN);

        lib.gameLibrary[1][0] = new Card(BLUE);
        lib.gameLibrary[1][1] = new Card(GREEN);
        lib.gameLibrary[1][2] = new Card(BLUE);
        lib.gameLibrary[1][3] = new Card(GREEN);
        lib.gameLibrary[1][4] = new Card(BLUE);

        lib.gameLibrary[2][0] = new Card(GREEN);
        lib.gameLibrary[2][1] = new Card(BLUE);
        lib.gameLibrary[2][2] = new Card(GREEN);
        lib.gameLibrary[2][3] = new Card(BLUE);
        lib.gameLibrary[2][4] = new Card(GREEN);

        lib.gameLibrary[3][0] = new Card(BLUE);
        lib.gameLibrary[3][1] = new Card(GREEN);
        lib.gameLibrary[3][2] = new Card(BLUE);
        lib.gameLibrary[3][3] = new Card(GREEN);
        lib.gameLibrary[3][4] = new Card(PINK);

        lib.gameLibrary[4][0] = new Card(GREEN);
        lib.gameLibrary[4][1] = new Card(BLUE);
        lib.gameLibrary[4][2] = new Card(GREEN);
        lib.gameLibrary[4][3] = new Card(PINK);
        lib.gameLibrary[4][4] = new Card(PINK);

        lib.gameLibrary[5][0] = new Card(BLUE);
        lib.gameLibrary[5][1] = new Card(GREEN);
        lib.gameLibrary[5][2] = new Card(PINK);
        lib.gameLibrary[5][3] = new Card(PINK);
        lib.gameLibrary[5][4] = new Card(PINK);

        assertEquals(lib.countGroupedPoints(), 10);
    }

    /**
     * <table border="1">
     * <caption> matrix </caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>G</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;</td></tr>
     * </table>
     */
    @Test
    public void countPoints_empty_correct(){
        lib.gameLibrary[0][0] = new Card();
        lib.gameLibrary[0][1] = new Card();
        lib.gameLibrary[0][2] = new Card();
        lib.gameLibrary[0][3] = new Card();
        lib.gameLibrary[0][4] = new Card();

        lib.gameLibrary[1][0] = new Card();
        lib.gameLibrary[1][1] = new Card();
        lib.gameLibrary[1][2] = new Card();
        lib.gameLibrary[1][3] = new Card();
        lib.gameLibrary[1][4] = new Card();

        lib.gameLibrary[2][0] = new Card();
        lib.gameLibrary[2][1] = new Card();
        lib.gameLibrary[2][2] = new Card();
        lib.gameLibrary[2][3] = new Card();
        lib.gameLibrary[2][4] = new Card();

        lib.gameLibrary[3][0] = new Card();
        lib.gameLibrary[3][1] = new Card();
        lib.gameLibrary[3][2] = new Card(GREEN);
        lib.gameLibrary[3][3] = new Card();
        lib.gameLibrary[3][4] = new Card();

        lib.gameLibrary[4][0] = new Card();
        lib.gameLibrary[4][1] = new Card();
        lib.gameLibrary[4][2] = new Card();
        lib.gameLibrary[4][3] = new Card();
        lib.gameLibrary[4][4] = new Card();

        lib.gameLibrary[5][0] = new Card();
        lib.gameLibrary[5][1] = new Card();
        lib.gameLibrary[5][2] = new Card();
        lib.gameLibrary[5][3] = new Card();
        lib.gameLibrary[5][4] = new Card();

        assertEquals(lib.countGroupedPoints(), 0);
    }
}