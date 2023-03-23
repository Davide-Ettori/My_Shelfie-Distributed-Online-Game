package algo;


import app.model.*;

import org.junit.*;

import static app.model.Color.*;
import static org.junit.Assert.assertTrue;


//al posto di fare 12 test diversi, creo diverse shelfie e testo tutti gli obbiettivi comuni su ognuna
public class Algo2Test {
    Algo_CO_2 algoCo2 = null;
    Card[][] mat = new Card[6][5];

    @Before // eseguita prima dei test
    public void setUp() {
        this.algoCo2 = new Algo_CO_2();

    }

    @After // eseguita dopo i test
    public void tearDown() {
        return;
    }

    @Test
    public void algo2_test1_bassosx() {
        mat[0][0] = new Card(GREEN);
        mat[0][1] = new Card(GREEN);
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(PINK);
        mat[1][1] = new Card(BLUE);
        mat[1][2] = new Card(YELLOW);
        mat[1][3] = new Card();
        mat[1][4] = new Card(PINK);

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card();
        mat[2][3] = new Card(PINK);
        mat[2][4] = new Card();

        mat[3][0] = new Card(YELLOW);
        mat[3][1] = new Card();
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card();
        mat[3][4] = new Card(YELLOW);

        mat[4][0] = new Card();
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card();
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo2.checkMatch(mat));

    }
    @Test
    public void algo2_test1_bassodx() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(PINK);

        mat[1][0] = new Card(YELLOW);
        mat[1][1] = new Card();
        mat[1][2] = new Card(GREEN);
        mat[1][3] = new Card(GREEN);
        mat[1][4] = new Card(PINK);

        mat[2][0] = new Card();
        mat[2][1] = new Card(YELLOW);
        mat[2][2] = new Card();
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(PINK);
        mat[3][1] = new Card();
        mat[3][2] = new Card(YELLOW);
        mat[3][3] = new Card();
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(YELLOW);
        mat[4][4] = new Card();

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card();
        mat[5][4] = new Card(YELLOW);

        assertTrue(algoCo2.checkMatch(mat));

    }
    @Test
    public void algo2_test1_unodx() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card();
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(GREEN);
        mat[1][1] = new Card(PINK);
        mat[1][2] = new Card();
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card();
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card();
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card();
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(YELLOW);
        mat[4][1] = new Card();
        mat[4][2] = new Card(GREEN);
        mat[4][3] = new Card(GREEN);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(GREEN);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo2.checkMatch(mat));

    }
    @Test
    public void algo2_test1_unosx() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card();
        mat[0][2] = new Card(GREEN);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card();
        mat[1][1] = new Card(PINK);
        mat[1][2] = new Card();
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card(YELLOW);

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card();
        mat[2][2] = new Card(PINK);
        mat[2][3] = new Card();
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(GREEN);
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card();
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card();

        mat[4][0] = new Card(YELLOW);
        mat[4][1] = new Card(YELLOW);
        mat[4][2] = new Card(YELLOW);
        mat[4][3] = new Card();
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(YELLOW);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(YELLOW);
        mat[5][4] = new Card(YELLOW);

        assertTrue(algoCo2.checkMatch(mat));

    }
}