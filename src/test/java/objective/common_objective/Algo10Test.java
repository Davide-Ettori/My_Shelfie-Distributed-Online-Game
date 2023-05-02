package objective.common_objective;

import app.model.Algo_CO_10;
import app.model.Card;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static app.model.Color.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * <p>
 * class that test the tenth algorithm, five cards of the same type that made an X
 * <p>
 * test which are true are #1
 * <p>
 * test which are false are #2
 * @author Faccincani, Ettori
 */
public class Algo10Test {
    Algo_CO_10 algoCo10 = null;

    Card[][] mat = new Card[6][5];

    @Before // eseguita prima dei test
    public void setUp() {
        this.algoCo10 = new Algo_CO_10();
    }
    @After // eseguita dopo i test
    public void tearDown() {
        return;
    }

    @Test
    public void algo10_test1_T() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(GREEN);
        mat[0][2] = new Card(PINK);
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(BLUE);
        mat[1][1] = new Card(PINK);
        mat[1][2] = new Card(YELLOW);
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card();

        mat[2][0] = new Card(PINK);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card(PINK);
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card();
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card(GREEN);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertTrue(algoCo10.checkMatch(mat));
    }
    @Test
    public void algo10_test2_F() {
        mat[0][0] = new Card(PINK);
        mat[0][1] = new Card(GREEN);
        mat[0][2] = new Card();
        mat[0][3] = new Card(GREEN);
        mat[0][4] = new Card(YELLOW);

        mat[1][0] = new Card(BLUE);
        mat[1][1] = new Card(PINK);
        mat[1][2] = new Card(YELLOW);
        mat[1][3] = new Card(YELLOW);
        mat[1][4] = new Card();

        mat[2][0] = new Card(PINK);
        mat[2][1] = new Card(CYAN);
        mat[2][2] = new Card(PINK);
        mat[2][3] = new Card(BLUE);
        mat[2][4] = new Card(BLUE);

        mat[3][0] = new Card();
        mat[3][1] = new Card(YELLOW);
        mat[3][2] = new Card(GREEN);
        mat[3][3] = new Card(PINK);
        mat[3][4] = new Card(PINK);

        mat[4][0] = new Card(PINK);
        mat[4][1] = new Card(PINK);
        mat[4][2] = new Card();
        mat[4][3] = new Card(CYAN);
        mat[4][4] = new Card(CYAN);

        mat[5][0] = new Card(PINK);
        mat[5][1] = new Card(GREEN);
        mat[5][2] = new Card(GREEN);
        mat[5][3] = new Card(PINK);
        mat[5][4] = new Card(PINK);

        assertFalse(algoCo10.checkMatch(mat));
    }
}
