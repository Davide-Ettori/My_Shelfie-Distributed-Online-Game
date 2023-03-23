package common_objective;

import app.model.Algo_CO_12;
import app.model.Card;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static app.model.Color.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Algo12Test {
    Algo_CO_12 algoCo12 = null;

    Card[][] mat = new Card[6][5];

    @Before // eseguita prima dei test
    public void setUp() {
        this.algoCo12 = new Algo_CO_12();
    }
    @After // eseguita dopo i test
    public void tearDown() {
        return;
    }

    @Test // test 1
    public void algo12_test1_general_decrescent() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card(PINK);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card();
        mat[2][3] = new Card(PINK);
        mat[2][4] = new Card(PINK);

        mat[3][0] = new Card();
        mat[3][1] = new Card();
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card();
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo12.checkMatch(mat));
    }
    @Test // test 1
    public void algo12_test1_general_F_decrescent() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card();

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card(PINK);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card();
        mat[2][3] = new Card(PINK);
        mat[2][4] = new Card(PINK);

        mat[3][0] = new Card();
        mat[3][1] = new Card();
        mat[3][2] = new Card();
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card();
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo12.checkMatch(mat));
    }
    @Test // test 1
    public void algo12_test1_general_digdecrescent() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card(WHITE);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card(WHITE);
        mat[1][4] = new Card(PINK);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card(WHITE);
        mat[2][3] = new Card(PINK);
        mat[2][4] = new Card(PINK);

        mat[3][0] = new Card();
        mat[3][1] = new Card(WHITE);
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(WHITE);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo12.checkMatch(mat));
    }
    @Test // test 1
    public void algo12_test1_general_F_digdecrescent() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card(PINK);
        mat[0][4] = new Card(WHITE);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card(WHITE);
        mat[1][4] = new Card(PINK);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card(WHITE);
        mat[2][3] = new Card(PINK);
        mat[2][4] = new Card(PINK);

        mat[3][0] = new Card();
        mat[3][1] = new Card(WHITE);
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(WHITE);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo12.checkMatch(mat));
    }
    @Test // test 1
    public void algo12_test1_general_crescent() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card(WHITE);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card(WHITE);
        mat[1][4] = new Card(BLUE);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card(WHITE);
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card(PINK);

        mat[3][0] = new Card();
        mat[3][1] = new Card(WHITE);
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(YELLOW);

        mat[4][0] = new Card(WHITE);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo12.checkMatch(mat));
    }
    @Test // test 1
    public void algo12_test1_general_F_rescent() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card(WHITE);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card(WHITE);
        mat[1][4] = new Card(BLUE);

        mat[2][0] = new Card();
        mat[2][1] = new Card(GREEN);
        mat[2][2] = new Card(WHITE);
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card(PINK);

        mat[3][0] = new Card();
        mat[3][1] = new Card(WHITE);
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(YELLOW);

        mat[4][0] = new Card(WHITE);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo12.checkMatch(mat));
    }
    @Test // test 1
    public void algo12_test1_general_digcrescent() {
        mat[0][0] = new Card();
        mat[0][1] = new Card();
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card(PINK);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card(PINK);
        mat[1][4] = new Card(BLUE);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card(PINK);
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card(PINK);

        mat[3][0] = new Card();
        mat[3][1] = new Card(PINK);
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(YELLOW);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo12.checkMatch(mat));
    }
    @Test // test 1
    public void algo12_test1_general_F_digcrescent(){
        mat[0][0] = new Card();
        mat[0][1] = new Card(GREEN);
        mat[0][2] = new Card();
        mat[0][3] = new Card();
        mat[0][4] = new Card(PINK);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card(PINK);
        mat[1][4] = new Card(BLUE);

        mat[2][0] = new Card();
        mat[2][1] = new Card();
        mat[2][2] = new Card(PINK);
        mat[2][3] = new Card(WHITE);
        mat[2][4] = new Card(PINK);

        mat[3][0] = new Card();
        mat[3][1] = new Card(PINK);
        mat[3][2] = new Card(PINK);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(YELLOW);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card(PINK);
        mat[4][3] = new Card(PINK);
        mat[4][4] = new Card(PINK);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(PINK);
        mat[5][2] = new Card(PINK);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo12.checkMatch(mat));
    }
}
