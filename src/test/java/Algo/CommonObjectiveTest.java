package Algo;

import app.model.Algo_CO_1;
import app.model.CommonObjective;
import app.model.Card;
import org.junit.*;

import static app.model.Color.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommonObjectiveTest {
    CommonObjective obj1 = null;
    Card[][] mat = new Card[6][5];

    @Before // eseguita prima dei test
    public void setUp() {
        this.obj1 = new CommonObjective(new Algo_CO_1(), " ");

    }

    @After // eseguita dopo i test
    public void tearDown() {
        return;
    }

    @Test // test 1
    public void algo1_test1_general() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card();
        mat[0][3] = new Card(YELLOW);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(PINK);
        mat[1][1] = new Card(BLUE);
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card(BLUE);
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card(BLUE);
        mat[3][2] = new Card(GREEN);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(obj1.algorithm.checkMatch(mat));
    }

}
    /*
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
*/