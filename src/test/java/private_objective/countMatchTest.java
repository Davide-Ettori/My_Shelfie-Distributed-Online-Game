package private_objective;

import app.model.Card;
import app.model.PrivateObjective;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static app.model.Color.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class countMatchTest {
    PrivateObjective privateObjective = null;
    Card[][] lib = null;
    @Before // eseguita prima dei test
    public void setUp(){
        this.privateObjective =new PrivateObjective(null,0,"");
        this.lib=new Card[6][5];
    }
    @After // eseguita dopo i test
    public void tearDown() {
        return;
    }
    @Test
    public void countMatch_personalGoal_2match(){
        Card[][] objMat = new Card[6][5];
        objMat[0][0] = new Card(PINK);
        objMat[0][1] = new Card();
        objMat[0][2] = new Card(BLUE);
        objMat[0][3] = new Card();
        objMat[0][4] = new Card();

        objMat[1][0] = new Card();
        objMat[1][1] = new Card();
        objMat[1][2] = new Card();
        objMat[1][3] = new Card();
        objMat[1][4] = new Card(GREEN);

        objMat[2][0] = new Card();
        objMat[2][1] = new Card();
        objMat[2][2] = new Card();
        objMat[2][3] = new Card(WHITE);
        objMat[2][4] = new Card();

        objMat[3][0] = new Card();
        objMat[3][1] = new Card(YELLOW);
        objMat[3][2] = new Card();
        objMat[3][3] = new Card();
        objMat[3][4] = new Card();

        objMat[4][0] = new Card();
        objMat[4][1] = new Card();
        objMat[4][2] = new Card();
        objMat[4][3] = new Card();
        objMat[4][4] = new Card();

        objMat[5][0] = new Card();
        objMat[5][1] = new Card();
        objMat[5][2] = new Card(CYAN);
        objMat[5][3] = new Card();
        objMat[5][4] = new Card();


        lib[0][0] = new Card();
        lib[0][1] = new Card();
        lib[0][2] = new Card();
        lib[0][3] = new Card();
        lib[0][4] = new Card();

        lib[1][0] = new Card();
        lib[1][1] = new Card();
        lib[1][2] = new Card();
        lib[1][3] = new Card();
        lib[1][4] = new Card();

        lib[2][0] = new Card();
        lib[2][1] = new Card();
        lib[2][2] = new Card();
        lib[2][3] = new Card();
        lib[2][4] = new Card();

        lib[3][0] = new Card(PINK);
        lib[3][1] = new Card(YELLOW);
        lib[3][2] = new Card(GREEN);
        lib[3][3] = new Card(PINK);
        lib[3][4] = new Card(PINK);

        lib[4][0] = new Card(PINK);
        lib[4][1] = new Card(PINK);
        lib[4][2] = new Card(WHITE);
        lib[4][3] = new Card(CYAN);
        lib[4][4] = new Card(CYAN);

        lib[5][0] = new Card(PINK);
        lib[5][1] = new Card(GREEN);
        lib[5][2] = new Card(CYAN);
        lib[5][3] = new Card(PINK);
        lib[5][4] = new Card(PINK);

        privateObjective.setObjMatrix(objMat);
        assertEquals(privateObjective.countPoints(lib), 2);
    }
    @Test
    public void countMatch_personalgoal_5match(){

    }
}
