package objective.common_objective;

import app.model.Algo_CO_5;
import app.model.Card;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static app.model.Color.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * class that test the fifth algorithm, four column of the same type (different column can have different type)
 * cards
 * <p>
 * test which are true are #1 #3
 * <p>
 * test which are false are #2 #3 #5
 * @author Faccincani, Ettori
 */

public class Algo5Test {
    Algo_CO_5 algoCo5 = null;

    Card[][] mat = new Card[6][5];

    @Before // eseguita prima dei test
    public void setUp() {
        this.algoCo5 = new Algo_CO_5();
    }
    @After // eseguita dopo i test
    public void tearDown() {
        return;
    }

    @Test // test 1
    public void algo5_test1_T_general() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card(PINK);
        mat[0][3] = new Card(PINK);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(CYAN);
        mat[3][1] = new Card(CYAN);
        mat[3][2] = new Card(GREEN);
        mat[3][3] = new Card(BLUE);
        mat[3][4] = new Card(BLUE);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(GREEN);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(GREEN);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo5.checkMatch(mat));
    }
    @Test
    public void algo5_test2_F() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card();
        mat[0][3] = new Card(PINK);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card();
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card(CYAN);
        mat[3][1] = new Card(CYAN);
        mat[3][2] = new Card(GREEN);
        mat[3][3] = new Card(BLUE);
        mat[3][4] = new Card(BLUE);

        mat[4][0] = new Card(GREEN);
        mat[4][1] = new Card(GREEN);
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(GREEN);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(GREEN);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo5.checkMatch(mat));
    }
    @Test //4 gruppi da 4 o piu carte
    public void algo5_test3_4groupstrue() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card();
        mat[0][3] = new Card(PINK);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card(YELLOW);
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(BLUE);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card();
        mat[2][4] = new Card(CYAN);

        mat[3][0] = new Card(BLUE);
        mat[3][1] = new Card(BLUE);
        mat[3][2] = new Card(YELLOW);
        mat[3][3] = new Card(CYAN);
        mat[3][4] = new Card(CYAN);

        mat[4][0] = new Card(BLUE);
        mat[4][1] = new Card(BLUE);
        mat[4][2] = new Card(YELLOW);
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(GREEN);

        mat[5][0] = new Card(GREEN);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(GREEN);
        mat[5][4] = new Card(GREEN);

        assertTrue(algoCo5.checkMatch(mat));
    }
    @Test
    public void algo5_test4_3groupsfalse() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(PINK);
        mat[0][2] = new Card();
        mat[0][3] = new Card(PINK);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card(YELLOW);
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card(CYAN);
        mat[2][1] = new Card(BLUE);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card();
        mat[2][4] = new Card(CYAN);

        mat[3][0] = new Card();
        mat[3][1] = new Card(BLUE);
        mat[3][2] = new Card(YELLOW);
        mat[3][3] = new Card(CYAN);
        mat[3][4] = new Card(CYAN);

        mat[4][0] = new Card();
        mat[4][1] = new Card(BLUE);
        mat[4][2] = new Card(YELLOW);
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(GREEN);

        mat[5][0] = new Card(GREEN);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(GREEN);
        mat[5][4] = new Card(GREEN);

        assertFalse(algoCo5.checkMatch(mat));
    }
    @Test //4 gruppi da 4 o piu carte
    public void algo5_test5_3groupsfalse2() {
        mat[0][0] = new Card(BLUE);
        mat[0][1] = new Card(BLUE);
        mat[0][2] = new Card();
        mat[0][3] = new Card(BLUE);
        mat[0][4] = new Card(BLUE);

        mat[1][0] = new Card();
        mat[1][1] = new Card();
        mat[1][2] = new Card(YELLOW);
        mat[1][3] = new Card();
        mat[1][4] = new Card();

        mat[2][0] = new Card();
        mat[2][1] = new Card(BLUE);
        mat[2][2] = new Card(YELLOW);
        mat[2][3] = new Card();
        mat[2][4] = new Card(CYAN);

        mat[3][0] = new Card();
        mat[3][1] = new Card(BLUE);
        mat[3][2] = new Card(YELLOW);
        mat[3][3] = new Card(CYAN);
        mat[3][4] = new Card(CYAN);

        mat[4][0] = new Card();
        mat[4][1] = new Card(BLUE);
        mat[4][2] = new Card(YELLOW);
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(GREEN);

        mat[5][0] = new Card(GREEN);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(GREEN);
        mat[5][4] = new Card(GREEN);

        assertFalse(algoCo5.checkMatch(mat));
    }
}
