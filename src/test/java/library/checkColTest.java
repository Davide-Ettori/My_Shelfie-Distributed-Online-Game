package library;

import it.polimi.ingsw.model.Card;
import org.junit.*;
import static it.polimi.ingsw.model.Color.*;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.model.Library;

/**
 * <p>
 * class that test the checkCol method of the library class
 * The checkCol method checks if the selected column has enough space to insert a specific number of cards
 * <p>
 * The False tests are:1,3
 * <p>
 * The True tests are: 2
 * @author Giammusso, Ettori
 */
public class checkColTest {

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
     * in this test all the columns are filled so that we can't insert any other card in the library<br>
     * <table border="1">
     * <caption> matrix </caption>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>B</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>B</td><td>B</td><td>B</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>B</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Giammusso
     */
    @Test // test 1
    public void checkCol_allFull(){
        lib.gameLibrary[0][0] = new Card(PINK);
        lib.gameLibrary[0][1] = new Card(PINK);
        lib.gameLibrary[0][2] = new Card(PINK);
        lib.gameLibrary[0][3] = new Card(PINK);
        lib.gameLibrary[0][4] = new Card(PINK);

        lib.gameLibrary[1][0] = new Card(PINK);
        lib.gameLibrary[1][1] = new Card(BLUE);
        lib.gameLibrary[1][2] = new Card(PINK);
        lib.gameLibrary[1][3] = new Card(PINK);
        lib.gameLibrary[1][4] = new Card(PINK);

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

        assertEquals(lib.checkCol(0, 3), false);
    }

    /**
     * in this test colomn 0 is empty so we can insert cards<br>
     * <table border="1">
     * <caption> matrix </caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>B</td><td>B</td><td>B</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Giammusso
     */
    @Test // test 2
    public void checkCol_emptyColumn_correct(){
        lib.gameLibrary[0][0] = new Card();
        lib.gameLibrary[0][1] = new Card(PINK);
        lib.gameLibrary[0][2] = new Card(PINK);
        lib.gameLibrary[0][3] = new Card(PINK);
        lib.gameLibrary[0][4] = new Card(PINK);

        lib.gameLibrary[1][0] = new Card();
        lib.gameLibrary[1][1] = new Card(BLUE);
        lib.gameLibrary[1][2] = new Card(PINK);
        lib.gameLibrary[1][3] = new Card(PINK);
        lib.gameLibrary[1][4] = new Card(PINK);

        lib.gameLibrary[2][0] = new Card();
        lib.gameLibrary[2][1] = new Card(BLUE);
        lib.gameLibrary[2][2] = new Card(BLUE);
        lib.gameLibrary[2][3] = new Card(BLUE);
        lib.gameLibrary[2][4] = new Card(BLUE);

        lib.gameLibrary[3][0] = new Card();
        lib.gameLibrary[3][1] = new Card(BLUE);
        lib.gameLibrary[3][2] = new Card(GREEN);
        lib.gameLibrary[3][3] = new Card(PINK);
        lib.gameLibrary[3][4] = new Card(PINK);

        lib.gameLibrary[4][0] = new Card();
        lib.gameLibrary[4][1] = new Card(PINK);
        lib.gameLibrary[4][2] = new Card(GREEN);
        lib.gameLibrary[4][3] = new Card(PINK);
        lib.gameLibrary[4][4] = new Card(PINK);

        lib.gameLibrary[5][0] = new Card();
        lib.gameLibrary[5][1] = new Card(PINK);
        lib.gameLibrary[5][2] = new Card(GREEN);
        lib.gameLibrary[5][3] = new Card(PINK);
        lib.gameLibrary[5][4] = new Card(PINK);

        assertEquals(lib.checkCol(0, 3), true);
    }

    /**
     * in this test column 0 has 2 free spaces but there are 3 cards to put in the same column<br>
     * <table border="1">
     * <caption> matrix </caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>G</td><td>B</td><td>B</td><td>B</td><td>B</td></tr>
     * <tr><td>G</td><td>B</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>G</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>G</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     * @author Giammusso
     */
    @Test // test 3
    public void checkCol_tooManyCards(){
        lib.gameLibrary[0][0] = new Card();
        lib.gameLibrary[0][1] = new Card(PINK);
        lib.gameLibrary[0][2] = new Card(PINK);
        lib.gameLibrary[0][3] = new Card(PINK);
        lib.gameLibrary[0][4] = new Card(PINK);

        lib.gameLibrary[1][0] = new Card();
        lib.gameLibrary[1][1] = new Card(BLUE);
        lib.gameLibrary[1][2] = new Card(PINK);
        lib.gameLibrary[1][3] = new Card(PINK);
        lib.gameLibrary[1][4] = new Card(PINK);

        lib.gameLibrary[2][0] = new Card(GREEN);
        lib.gameLibrary[2][1] = new Card(BLUE);
        lib.gameLibrary[2][2] = new Card(BLUE);
        lib.gameLibrary[2][3] = new Card(BLUE);
        lib.gameLibrary[2][4] = new Card(BLUE);

        lib.gameLibrary[3][0] = new Card(GREEN);
        lib.gameLibrary[3][1] = new Card(BLUE);
        lib.gameLibrary[3][2] = new Card(GREEN);
        lib.gameLibrary[3][3] = new Card(PINK);
        lib.gameLibrary[3][4] = new Card(PINK);

        lib.gameLibrary[4][0] = new Card(GREEN);
        lib.gameLibrary[4][1] = new Card(PINK);
        lib.gameLibrary[4][2] = new Card(GREEN);
        lib.gameLibrary[4][3] = new Card(PINK);
        lib.gameLibrary[4][4] = new Card(PINK);

        lib.gameLibrary[5][0] = new Card(GREEN);
        lib.gameLibrary[5][1] = new Card(PINK);
        lib.gameLibrary[5][2] = new Card(GREEN);
        lib.gameLibrary[5][3] = new Card(PINK);
        lib.gameLibrary[5][4] = new Card(PINK);

        assertEquals(lib.checkCol(0, 3), false);
    }
}
