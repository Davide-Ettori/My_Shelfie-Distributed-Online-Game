package library;

import app.model.Card;
import org.junit.*;

import static app.model.Color.*;
import static org.junit.Assert.assertEquals;
import app.model.Library;

import java.util.ArrayList;
import java.util.Arrays;

public class insertCardsTest {

    Library beforeLib = null;
    Library afterLib = null;
    ArrayList<Card> myCards = new ArrayList<Card>(Arrays.asList(new Card(PINK),new Card(GREEN),new Card(BLUE)));


    @Before // eseguita prima dei test
    public void setUp(){
        this.beforeLib = new Library();
        this.afterLib = new Library();
    }
    @After // eseguita dopo i test
    public void tearDown(){
        return;
    }

    @Test // test 1
    public void insertCards_correct(){
        //First library
        beforeLib.library[0][0] = new Card();//1
        beforeLib.library[0][1] = new Card(PINK);
        beforeLib.library[0][2] = new Card(PINK);
        beforeLib.library[0][3] = new Card(PINK);
        beforeLib.library[0][4] = new Card(PINK);

        beforeLib.library[1][0] = new Card();//2
        beforeLib.library[1][1] = new Card(BLUE);
        beforeLib.library[1][2] = new Card(PINK);
        beforeLib.library[1][3] = new Card(PINK);
        beforeLib.library[1][4] = new Card(PINK);

        beforeLib.library[2][0] = new Card();//3
        beforeLib.library[2][1] = new Card(BLUE);
        beforeLib.library[2][2] = new Card(BLUE);
        beforeLib.library[2][3] = new Card(BLUE);
        beforeLib.library[2][4] = new Card(BLUE);

        beforeLib.library[3][0] = new Card(PINK);//4
        beforeLib.library[3][1] = new Card(BLUE);
        beforeLib.library[3][2] = new Card(GREEN);
        beforeLib.library[3][3] = new Card(PINK);
        beforeLib.library[3][4] = new Card(PINK);

        beforeLib.library[4][0] = new Card(PINK);//5
        beforeLib.library[4][1] = new Card(PINK);
        beforeLib.library[4][2] = new Card(GREEN);
        beforeLib.library[4][3] = new Card(PINK);
        beforeLib.library[4][4] = new Card(PINK);

        beforeLib.library[5][0] = new Card(PINK);//6
        beforeLib.library[5][1] = new Card(PINK);
        beforeLib.library[5][2] = new Card(GREEN);
        beforeLib.library[5][3] = new Card(PINK);
        beforeLib.library[5][4] = new Card(PINK);

        //Second library
        afterLib.library[0][0] = new Card(BLUE);//1
        afterLib.library[0][1] = new Card(PINK);
        afterLib.library[0][2] = new Card(PINK);
        afterLib.library[0][3] = new Card(PINK);
        afterLib.library[0][4] = new Card(PINK);

        afterLib.library[1][0] = new Card(GREEN);//2
        afterLib.library[1][1] = new Card(BLUE);
        afterLib.library[1][2] = new Card(PINK);
        afterLib.library[1][3] = new Card(PINK);
        afterLib.library[1][4] = new Card(PINK);

        afterLib.library[2][0] = new Card(PINK);//3
        afterLib.library[2][1] = new Card(BLUE);
        afterLib.library[2][2] = new Card(BLUE);
        afterLib.library[2][3] = new Card(BLUE);
        afterLib.library[2][4] = new Card(BLUE);

        afterLib.library[3][0] = new Card(PINK);//4
        afterLib.library[3][1] = new Card(BLUE);
        afterLib.library[3][2] = new Card(GREEN);
        afterLib.library[3][3] = new Card(PINK);
        afterLib.library[3][4] = new Card(PINK);

        afterLib.library[4][0] = new Card(PINK);//5
        afterLib.library[4][1] = new Card(PINK);
        afterLib.library[4][2] = new Card(GREEN);
        afterLib.library[4][3] = new Card(PINK);
        afterLib.library[4][4] = new Card(PINK);

        afterLib.library[5][0] = new Card(PINK);//6
        afterLib.library[5][1] = new Card(PINK);
        afterLib.library[5][2] = new Card(GREEN);
        afterLib.library[5][3] = new Card(PINK);
        afterLib.library[5][4] = new Card(PINK);

        beforeLib.insertCards(0, myCards);
        assertEquals(afterLib.sameLibraryColor(beforeLib), true);
    }

    @Test // test 2
    public void insertCards_incorrect(){
        //First library
        beforeLib.library[0][0] = new Card();//1
        beforeLib.library[0][1] = new Card(PINK);
        beforeLib.library[0][2] = new Card(PINK);
        beforeLib.library[0][3] = new Card(PINK);
        beforeLib.library[0][4] = new Card(PINK);

        beforeLib.library[1][0] = new Card();//2
        beforeLib.library[1][1] = new Card(BLUE);
        beforeLib.library[1][2] = new Card(PINK);
        beforeLib.library[1][3] = new Card(PINK);
        beforeLib.library[1][4] = new Card(PINK);

        beforeLib.library[2][0] = new Card();//3
        beforeLib.library[2][1] = new Card(BLUE);
        beforeLib.library[2][2] = new Card(BLUE);
        beforeLib.library[2][3] = new Card(BLUE);
        beforeLib.library[2][4] = new Card(BLUE);

        beforeLib.library[3][0] = new Card(PINK);//4
        beforeLib.library[3][1] = new Card(BLUE);
        beforeLib.library[3][2] = new Card(GREEN);
        beforeLib.library[3][3] = new Card(PINK);
        beforeLib.library[3][4] = new Card(PINK);

        beforeLib.library[4][0] = new Card(PINK);//5
        beforeLib.library[4][1] = new Card(PINK);
        beforeLib.library[4][2] = new Card(GREEN);
        beforeLib.library[4][3] = new Card(PINK);
        beforeLib.library[4][4] = new Card(PINK);

        beforeLib.library[5][0] = new Card(PINK);//6
        beforeLib.library[5][1] = new Card(PINK);
        beforeLib.library[5][2] = new Card(GREEN);
        beforeLib.library[5][3] = new Card(PINK);
        beforeLib.library[5][4] = new Card(PINK);

        //Second library
        afterLib.library[0][0] = new Card(CYAN);//1 - i change only this color to obtain an incorrect result (the correct was BLUE)
        afterLib.library[0][1] = new Card(PINK);
        afterLib.library[0][2] = new Card(PINK);
        afterLib.library[0][3] = new Card(PINK);
        afterLib.library[0][4] = new Card(PINK);

        afterLib.library[1][0] = new Card(GREEN);//2
        afterLib.library[1][1] = new Card(BLUE);
        afterLib.library[1][2] = new Card(PINK);
        afterLib.library[1][3] = new Card(PINK);
        afterLib.library[1][4] = new Card(PINK);

        afterLib.library[2][0] = new Card(PINK);//3
        afterLib.library[2][1] = new Card(BLUE);
        afterLib.library[2][2] = new Card(BLUE);
        afterLib.library[2][3] = new Card(BLUE);
        afterLib.library[2][4] = new Card(BLUE);

        afterLib.library[3][0] = new Card(PINK);//4
        afterLib.library[3][1] = new Card(BLUE);
        afterLib.library[3][2] = new Card(GREEN);
        afterLib.library[3][3] = new Card(PINK);
        afterLib.library[3][4] = new Card(PINK);

        afterLib.library[4][0] = new Card(PINK);//5
        afterLib.library[4][1] = new Card(PINK);
        afterLib.library[4][2] = new Card(GREEN);
        afterLib.library[4][3] = new Card(PINK);
        afterLib.library[4][4] = new Card(PINK);

        afterLib.library[5][0] = new Card(PINK);//6
        afterLib.library[5][1] = new Card(PINK);
        afterLib.library[5][2] = new Card(GREEN);
        afterLib.library[5][3] = new Card(PINK);
        afterLib.library[5][4] = new Card(PINK);

        beforeLib.insertCards(0, myCards);
        assertEquals(afterLib.sameLibraryColor(beforeLib), false);
    }
}
