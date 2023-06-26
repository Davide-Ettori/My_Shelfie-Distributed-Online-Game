package library;

import it.polimi.ingsw.model.Card;
import org.junit.*;
import static it.polimi.ingsw.model.Color.*;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.model.Library;

public class drawTest {

    Library lib = null;
    /**
     * method executed before every test that create a new library
     * @author Giammusso
     */
    @Before
    public void setUp(){
        lib = new Library("");
        lib.name = "sam";

        Library libTest = new Library(lib);
        libTest.checkCol(-1, 2);
        libTest.maxCardsInsertable();
    }
    /**
     * <table border="1">
     * <caption> matrix </caption>
     * <tr><td>P</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>B</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>B</td><td>B</td><td>B</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>B</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     */
    @Test // test 1
    public void drawLibrary(){
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

        lib.draw("Library of: " +lib.name);
        lib.draw();
    }

}
