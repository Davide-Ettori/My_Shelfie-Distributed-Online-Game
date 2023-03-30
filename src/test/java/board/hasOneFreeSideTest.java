package board;

import app.model.*;
import org.junit.Before;
import org.junit.Test;

import static app.model.Color.*;
import static org.junit.Assert.assertEquals;

public class hasOneFreeSideTest {
    int DIM=9;
    Board board1 = null;
    Card[][] gameBoard1 = null;
    @Before
    public void setUp(){
        board1 = new Board(4,null,null);
        gameBoard1 = new Card[DIM][DIM];
    }
    @Test
    public void board_nofreeside(){
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        gameBoard1[2][3].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[3][4].color = WHITE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[4][5].color = BLUE;
        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);
        assertEquals(board1.hasOneFreeSide(3,4), false);
    }
    @Test
    public void board_haveonefreeside(){
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        gameBoard1[2][3].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[3][4].color = WHITE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[4][5].color = BLUE;
        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);
        assertEquals(board1.hasOneFreeSide(4,5), true);
    }
    @Test
    public void board_havetwofreeside(){
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        gameBoard1[2][3].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[3][4].color = WHITE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[4][5].color = BLUE;
        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);
        assertEquals(board1.hasOneFreeSide(4,4), true);
    }
}
