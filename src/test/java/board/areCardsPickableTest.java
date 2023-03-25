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

import java.util.ArrayList;
import java.util.Arrays;

public class areCardsPickableTest {
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
    ArrayList<Integer> cardXY = null;
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

    @Test //test1
    public void adiacent_Allineated_FreeSide(){ //Significa: che le tessere sono 1)Adiacenti, 2)Su una linea retta, 3)Hanno almeno 1 lato libero
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

        gameBoard1[1][4].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(1,3,2,3));

        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.areCardsPickable(cardXY), true);
    }

    @Test //test2
    public void adiacent_NotAllineated_FreeSide(){
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

        gameBoard1[1][4].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(1,3,2,3,1,4));

        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.areCardsPickable(cardXY), false);
    }

    @Test//test3 - non passa
    public void notAdiacent_Allineated_FreeSide_Vertical(){
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

        gameBoard1[1][4].color = BLUE;
        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(1,3,2,3,4,3));

        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.areCardsPickable(cardXY), false);
    }

    @Test //test 3 - BIS
    public void notAdiacent_Allineated_FreeSide_Orizontal(){
        //inizializzo la matrice con degli EMPTY
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //metto le carte colorate dove mi interessano
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][5].color = BLUE;
        gameBoard1[5][5].color = BLUE;

        gameBoard1[3][6].color = BLUE;
        gameBoard1[4][6].color = BLUE;
        gameBoard1[5][6].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(3,3,3,4,3,6));

        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.areCardsPickable(cardXY), false);
    }

    @Test //test 4
    public void adiacent_Allineated_NotFreeSide(){
        //inizializzo la matrice con degli EMPTY
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //metto le carte colorate dove mi interessano
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][5].color = BLUE;
        gameBoard1[5][5].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(3,4,4,4));

        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.areCardsPickable(cardXY), false);
    }

    @Test //test 5
    public void adiacent_NotAllineated_NotFreeSide(){
        //inizializzo la matrice con degli EMPTY
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //metto le carte colorate dove mi interessano
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][5].color = BLUE;
        gameBoard1[5][5].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(2,4,3,4,2,5));

        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.areCardsPickable(cardXY), false);
    }

    @Test //test 6
    public void notAdiacent_Allineated_NotFreeSide(){
        //inizializzo la matrice con degli EMPTY
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //metto le carte colorate dove mi interessano
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][5].color = BLUE;
        gameBoard1[5][5].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(2,4,4,4));

        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.areCardsPickable(cardXY), false);
    }

    @Test //test 7
    public void notAdiacent_NotAllineated_FreeSide(){
        //inizializzo la matrice con degli EMPTY
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //metto le carte colorate dove mi interessano
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(2,3,3,4));

        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.areCardsPickable(cardXY), false);
    }

    @Test //test 8
    public void notAdiacent_NotAllineated_NotFreeSide(){
        //inizializzo la matrice con degli EMPTY
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //metto le carte colorate dove mi interessano
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        gameBoard1[2][5].color = BLUE;
        gameBoard1[3][5].color = BLUE;
        gameBoard1[4][5].color = BLUE;
        gameBoard1[5][5].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(2,3,3,4,4,5));

        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.areCardsPickable(cardXY), false);
    }

    @Test //test 9
    public void Adiacent_Allineated_FreeSide_ButPickedNotInOrder(){//cioè le "picca" vicine ma non in ordine consecutivo
        //inizializzo la matrice con degli EMPTY
        for(int i=0; i<DIM; i++){
            for(int j=0; j<DIM; j++){
                gameBoard1[i][j] = new Card(EMPTY);
            }
        }
        //metto le carte colorate dove mi interessano
        gameBoard1[2][3].color = BLUE;
        gameBoard1[3][3].color = BLUE;
        gameBoard1[4][3].color = BLUE;
        gameBoard1[5][3].color = BLUE;

        gameBoard1[2][4].color = BLUE;
        gameBoard1[3][4].color = BLUE;
        gameBoard1[4][4].color = BLUE;
        gameBoard1[5][4].color = BLUE;

        cardXY = new ArrayList<>(Arrays.asList(2,3,4,3,3,3));

        //uso il setter definito in Board
        board1.setGameBoard(gameBoard1);

        assertEquals(board1.areCardsPickable(cardXY), false);
    }

}
