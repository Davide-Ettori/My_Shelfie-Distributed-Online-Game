package board;

import app.model.Card;
import org.junit.*;

import static app.model.Color.*;
import static org.junit.Assert.assertEquals;
import app.model.Board;
import app.model.CommonObjective;
import app.model.Player;
import app.model.Strategy;
import app.model.Algo_CO_1;
import app.model.Algo_CO_2;

public class isBoardUnplayableTest {
    int DIM = 9;
    Strategy strategy1 = null;
    Strategy strategy2 = null;
    CommonObjective CO1 = null;
    CommonObjective CO2 = null;
    Player player1 = null;
    Player player2 = null;
    Player player3 = null;
    Player player4 = null;
    Board board1 = null;
    Card[][] gameBoard1 = null;
    @Before
    public void setUp(){
        strategy1 = new Algo_CO_1();
        strategy2 = new Algo_CO_2();

        CO1 = new CommonObjective(strategy1,"");
        CO2 = new CommonObjective(strategy2, "");

        player1 = new Player(true,"peppino");
        player2 = new Player(true,"peppone");
        player3 = new Player(true,"peppuzzo");
        player4 = new Player(true,"iopippo");

        board1 = new Board(4,CO1,CO2,player1);
        gameBoard1 = new Card[DIM][DIM];
    }

    @Test
    public void board_fourIsolatedCards(){
        //inizializzo la matrice con degli EMPTY
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //metto le carte colorate dove mi interessano
        gameBoard1[1][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[5][3].color = BLUE;
        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.isBoardUnplayable(), true);
    }

    @Test
    public void board_fourNearCards(){
        //inizializzo la matrice con degli EMPTY
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //metto le carte colorate dove mi interessano
        gameBoard1[1][3].color = BLUE;
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.isBoardUnplayable(), false);
    }



}