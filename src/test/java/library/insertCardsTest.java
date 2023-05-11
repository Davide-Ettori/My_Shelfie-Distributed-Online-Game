package library;

import it.polimi.ingsw.model.Card;
import org.junit.*;
import static it.polimi.ingsw.model.Color.*;
import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.model.Library;

import java.util.ArrayList;
import java.util.Arrays;

public class insertCardsTest {

    Library beforeLib = null;
    Library afterLib = null;
    ArrayList<Card> myCards = new ArrayList<Card>(Arrays.asList(new Card(PINK),new Card(GREEN),new Card(BLUE)));
    /**
     * method executed before every test that create a new library
     * @author Giammusso
     */
    @Before // eseguita prima dei test
    public void setUp(){
        this.beforeLib = new Library("");
        this.afterLib = new Library("");
    }

    /**
     * First Testlibrary:<br>
     * <table border="1">
     * <caption> matrix </caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>B</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>B</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * </table><br>
     * Second Testlibrary:<br>
     * <table border="1">
     * <caption> matrix </caption>
     * <tr><td>B</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>G</td><td>B</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>B</td><td>B</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>B</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     */
    @Test // test 1
    public void insertCards_correct(){
        //First library
        beforeLib.gameLibrary[0][0] = new Card();//1
        beforeLib.gameLibrary[0][1] = new Card(PINK);
        beforeLib.gameLibrary[0][2] = new Card(PINK);
        beforeLib.gameLibrary[0][3] = new Card(PINK);
        beforeLib.gameLibrary[0][4] = new Card(PINK);

        beforeLib.gameLibrary[1][0] = new Card();//2
        beforeLib.gameLibrary[1][1] = new Card(BLUE);
        beforeLib.gameLibrary[1][2] = new Card(PINK);
        beforeLib.gameLibrary[1][3] = new Card(PINK);
        beforeLib.gameLibrary[1][4] = new Card(PINK);

        beforeLib.gameLibrary[2][0] = new Card();//3
        beforeLib.gameLibrary[2][1] = new Card(BLUE);
        beforeLib.gameLibrary[2][2] = new Card(BLUE);
        beforeLib.gameLibrary[2][3] = new Card(BLUE);
        beforeLib.gameLibrary[2][4] = new Card(BLUE);

        beforeLib.gameLibrary[3][0] = new Card(PINK);//4
        beforeLib.gameLibrary[3][1] = new Card(BLUE);
        beforeLib.gameLibrary[3][2] = new Card(GREEN);
        beforeLib.gameLibrary[3][3] = new Card(PINK);
        beforeLib.gameLibrary[3][4] = new Card(PINK);

        beforeLib.gameLibrary[4][0] = new Card(PINK);//5
        beforeLib.gameLibrary[4][1] = new Card(PINK);
        beforeLib.gameLibrary[4][2] = new Card(GREEN);
        beforeLib.gameLibrary[4][3] = new Card(PINK);
        beforeLib.gameLibrary[4][4] = new Card(PINK);

        beforeLib.gameLibrary[5][0] = new Card(PINK);//6
        beforeLib.gameLibrary[5][1] = new Card(PINK);
        beforeLib.gameLibrary[5][2] = new Card(GREEN);
        beforeLib.gameLibrary[5][3] = new Card(PINK);
        beforeLib.gameLibrary[5][4] = new Card(PINK);

        //Second library
        afterLib.gameLibrary[0][0] = new Card(BLUE);//1
        afterLib.gameLibrary[0][1] = new Card(PINK);
        afterLib.gameLibrary[0][2] = new Card(PINK);
        afterLib.gameLibrary[0][3] = new Card(PINK);
        afterLib.gameLibrary[0][4] = new Card(PINK);

        afterLib.gameLibrary[1][0] = new Card(GREEN);//2
        afterLib.gameLibrary[1][1] = new Card(BLUE);
        afterLib.gameLibrary[1][2] = new Card(PINK);
        afterLib.gameLibrary[1][3] = new Card(PINK);
        afterLib.gameLibrary[1][4] = new Card(PINK);

        afterLib.gameLibrary[2][0] = new Card(PINK);//3
        afterLib.gameLibrary[2][1] = new Card(BLUE);
        afterLib.gameLibrary[2][2] = new Card(BLUE);
        afterLib.gameLibrary[2][3] = new Card(BLUE);
        afterLib.gameLibrary[2][4] = new Card(BLUE);

        afterLib.gameLibrary[3][0] = new Card(PINK);//4
        afterLib.gameLibrary[3][1] = new Card(BLUE);
        afterLib.gameLibrary[3][2] = new Card(GREEN);
        afterLib.gameLibrary[3][3] = new Card(PINK);
        afterLib.gameLibrary[3][4] = new Card(PINK);

        afterLib.gameLibrary[4][0] = new Card(PINK);//5
        afterLib.gameLibrary[4][1] = new Card(PINK);
        afterLib.gameLibrary[4][2] = new Card(GREEN);
        afterLib.gameLibrary[4][3] = new Card(PINK);
        afterLib.gameLibrary[4][4] = new Card(PINK);

        afterLib.gameLibrary[5][0] = new Card(PINK);//6
        afterLib.gameLibrary[5][1] = new Card(PINK);
        afterLib.gameLibrary[5][2] = new Card(GREEN);
        afterLib.gameLibrary[5][3] = new Card(PINK);
        afterLib.gameLibrary[5][4] = new Card(PINK);

        beforeLib.insertCards(0, myCards);
        assertEquals(afterLib.sameLibraryColor(beforeLib), true);
    }

    /**
     * First TestLibrary:<br>
     * <table border="1">
     * <caption> matrix </caption>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>&nbsp;&nbsp;&nbsp;</td><td>B</td><td>B</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>B</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * </table><br>
     * Second Library:<br>
     *<table border="1">
     * <caption> matrix </caption>
     * <tr><td>C</td><td>P</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>G</td><td>B</td><td>P</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>B</td><td>B</td><td>B</td><td>B</td></tr>
     * <tr><td>P</td><td>B</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * <tr><td>P</td><td>P</td><td>G</td><td>P</td><td>P</td></tr>
     * </table>
     */
    @Test // test 2
    public void insertCards_incorrect(){
        //First library
        beforeLib.gameLibrary[0][0] = new Card();//1
        beforeLib.gameLibrary[0][1] = new Card(PINK);
        beforeLib.gameLibrary[0][2] = new Card(PINK);
        beforeLib.gameLibrary[0][3] = new Card(PINK);
        beforeLib.gameLibrary[0][4] = new Card(PINK);

        beforeLib.gameLibrary[1][0] = new Card();//2
        beforeLib.gameLibrary[1][1] = new Card(BLUE);
        beforeLib.gameLibrary[1][2] = new Card(PINK);
        beforeLib.gameLibrary[1][3] = new Card(PINK);
        beforeLib.gameLibrary[1][4] = new Card(PINK);

        beforeLib.gameLibrary[2][0] = new Card();//3
        beforeLib.gameLibrary[2][1] = new Card(BLUE);
        beforeLib.gameLibrary[2][2] = new Card(BLUE);
        beforeLib.gameLibrary[2][3] = new Card(BLUE);
        beforeLib.gameLibrary[2][4] = new Card(BLUE);

        beforeLib.gameLibrary[3][0] = new Card(PINK);//4
        beforeLib.gameLibrary[3][1] = new Card(BLUE);
        beforeLib.gameLibrary[3][2] = new Card(GREEN);
        beforeLib.gameLibrary[3][3] = new Card(PINK);
        beforeLib.gameLibrary[3][4] = new Card(PINK);

        beforeLib.gameLibrary[4][0] = new Card(PINK);//5
        beforeLib.gameLibrary[4][1] = new Card(PINK);
        beforeLib.gameLibrary[4][2] = new Card(GREEN);
        beforeLib.gameLibrary[4][3] = new Card(PINK);
        beforeLib.gameLibrary[4][4] = new Card(PINK);

        beforeLib.gameLibrary[5][0] = new Card(PINK);//6
        beforeLib.gameLibrary[5][1] = new Card(PINK);
        beforeLib.gameLibrary[5][2] = new Card(GREEN);
        beforeLib.gameLibrary[5][3] = new Card(PINK);
        beforeLib.gameLibrary[5][4] = new Card(PINK);

        //Second library
        afterLib.gameLibrary[0][0] = new Card(CYAN);//1 - I changed only this color to obtain an incorrect result (the correct was BLUE)
        afterLib.gameLibrary[0][1] = new Card(PINK);
        afterLib.gameLibrary[0][2] = new Card(PINK);
        afterLib.gameLibrary[0][3] = new Card(PINK);
        afterLib.gameLibrary[0][4] = new Card(PINK);

        afterLib.gameLibrary[1][0] = new Card(GREEN);//2
        afterLib.gameLibrary[1][1] = new Card(BLUE);
        afterLib.gameLibrary[1][2] = new Card(PINK);
        afterLib.gameLibrary[1][3] = new Card(PINK);
        afterLib.gameLibrary[1][4] = new Card(PINK);

        afterLib.gameLibrary[2][0] = new Card(PINK);//3
        afterLib.gameLibrary[2][1] = new Card(BLUE);
        afterLib.gameLibrary[2][2] = new Card(BLUE);
        afterLib.gameLibrary[2][3] = new Card(BLUE);
        afterLib.gameLibrary[2][4] = new Card(BLUE);

        afterLib.gameLibrary[3][0] = new Card(PINK);//4
        afterLib.gameLibrary[3][1] = new Card(BLUE);
        afterLib.gameLibrary[3][2] = new Card(GREEN);
        afterLib.gameLibrary[3][3] = new Card(PINK);
        afterLib.gameLibrary[3][4] = new Card(PINK);

        afterLib.gameLibrary[4][0] = new Card(PINK);//5
        afterLib.gameLibrary[4][1] = new Card(PINK);
        afterLib.gameLibrary[4][2] = new Card(GREEN);
        afterLib.gameLibrary[4][3] = new Card(PINK);
        afterLib.gameLibrary[4][4] = new Card(PINK);

        afterLib.gameLibrary[5][0] = new Card(PINK);//6
        afterLib.gameLibrary[5][1] = new Card(PINK);
        afterLib.gameLibrary[5][2] = new Card(GREEN);
        afterLib.gameLibrary[5][3] = new Card(PINK);
        afterLib.gameLibrary[5][4] = new Card(PINK);

        beforeLib.insertCards(0, myCards);
        assertEquals(afterLib.sameLibraryColor(beforeLib), false);
    }
}
