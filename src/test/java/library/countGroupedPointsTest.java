package library;

import app.model.Card;
import org.junit.*;

import static app.model.Color.*;
import static org.junit.Assert.assertEquals;
import app.model.Library;

public class countGroupedPointsTest {
    Library lib = null;
    @Before // eseguita prima dei test
    public void setUp(){
        this.lib = new Library("");

    }
    @After // eseguita dopo i test
    public void tearDown(){
        return;
    }
    @Test // test 1
    public void countPoints_normal_correct(){
        lib.library[0][0] = new Card(PINK);
        lib.library[0][1] = new Card(PINK);
        lib.library[0][2] = new Card();
        lib.library[0][3] = new Card();
        lib.library[0][4] = new Card();

        lib.library[1][0] = new Card(PINK);
        lib.library[1][1] = new Card(BLUE);
        lib.library[1][2] = new Card();
        lib.library[1][3] = new Card();
        lib.library[1][4] = new Card();

        lib.library[2][0] = new Card(BLUE);
        lib.library[2][1] = new Card(BLUE);
        lib.library[2][2] = new Card(BLUE);
        lib.library[2][3] = new Card(BLUE);
        lib.library[2][4] = new Card(BLUE);

        lib.library[3][0] = new Card(PINK);
        lib.library[3][1] = new Card(BLUE);
        lib.library[3][2] = new Card(GREEN);
        lib.library[3][3] = new Card(PINK);
        lib.library[3][4] = new Card(PINK);

        lib.library[4][0] = new Card(PINK);
        lib.library[4][1] = new Card(PINK);
        lib.library[4][2] = new Card(GREEN);
        lib.library[4][3] = new Card(PINK);
        lib.library[4][4] = new Card(PINK);

        lib.library[5][0] = new Card(PINK);
        lib.library[5][1] = new Card(PINK);
        lib.library[5][2] = new Card(GREEN);
        lib.library[5][3] = new Card(PINK);
        lib.library[5][4] = new Card(PINK);

        assertEquals(lib.countGroupedPoints(), 25);
    }
    @Test
    public void countPoints_diagonal_correct(){
        lib.library[0][0] = new Card(BLUE);
        lib.library[0][1] = new Card(BLUE);
        lib.library[0][2] = new Card(GREEN);
        lib.library[0][3] = new Card(BLUE);
        lib.library[0][4] = new Card(GREEN);

        lib.library[1][0] = new Card(BLUE);
        lib.library[1][1] = new Card(GREEN);
        lib.library[1][2] = new Card(BLUE);
        lib.library[1][3] = new Card(GREEN);
        lib.library[1][4] = new Card(BLUE);

        lib.library[2][0] = new Card(GREEN);
        lib.library[2][1] = new Card(BLUE);
        lib.library[2][2] = new Card(GREEN);
        lib.library[2][3] = new Card(BLUE);
        lib.library[2][4] = new Card(GREEN);

        lib.library[3][0] = new Card(BLUE);
        lib.library[3][1] = new Card(GREEN);
        lib.library[3][2] = new Card(BLUE);
        lib.library[3][3] = new Card(GREEN);
        lib.library[3][4] = new Card(PINK);

        lib.library[4][0] = new Card(GREEN);
        lib.library[4][1] = new Card(BLUE);
        lib.library[4][2] = new Card(GREEN);
        lib.library[4][3] = new Card(PINK);
        lib.library[4][4] = new Card(PINK);

        lib.library[5][0] = new Card(BLUE);
        lib.library[5][1] = new Card(GREEN);
        lib.library[5][2] = new Card(PINK);
        lib.library[5][3] = new Card(PINK);
        lib.library[5][4] = new Card(PINK);

        assertEquals(lib.countGroupedPoints(), 10);
    }
    @Test
    public void countPoints_empty_correct(){
        lib.library[0][0] = new Card();
        lib.library[0][1] = new Card();
        lib.library[0][2] = new Card();
        lib.library[0][3] = new Card();
        lib.library[0][4] = new Card();

        lib.library[1][0] = new Card();
        lib.library[1][1] = new Card();
        lib.library[1][2] = new Card();
        lib.library[1][3] = new Card();
        lib.library[1][4] = new Card();

        lib.library[2][0] = new Card();
        lib.library[2][1] = new Card();
        lib.library[2][2] = new Card();
        lib.library[2][3] = new Card();
        lib.library[2][4] = new Card();

        lib.library[3][0] = new Card();
        lib.library[3][1] = new Card();
        lib.library[3][2] = new Card(GREEN);
        lib.library[3][3] = new Card();
        lib.library[3][4] = new Card();

        lib.library[4][0] = new Card();
        lib.library[4][1] = new Card();
        lib.library[4][2] = new Card();
        lib.library[4][3] = new Card();
        lib.library[4][4] = new Card();

        lib.library[5][0] = new Card();
        lib.library[5][1] = new Card();
        lib.library[5][2] = new Card();
        lib.library[5][3] = new Card();
        lib.library[5][4] = new Card();

        assertEquals(lib.countGroupedPoints(), 0);
    }
}