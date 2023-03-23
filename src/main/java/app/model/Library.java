package app.model;

import java.util.ArrayList;

import static app.model.Color.EMPTY;
/**
 * classe che rappresenta la libreria privata di ogni giocatore
 * @author Ettori Giammusso
 * */
public class Library {
    public final int ROWS = 6;
    public final int COLS = 5;
    public Card[][] library = new Card[ROWS][COLS];
    private final int[][] visitedMatrix = new int[ROWS][COLS];
    private int countVisitedCards;

    public Library(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                library[i][j] = new Card();
            }
        }
    }

    /**
     * controlla se la library è piena
     * @author Ettori Giammusso
     * @param: void
     * @return true o false a seconda se la libreria è piena o no
     */
    public boolean isFull(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(library[i][j].color == EMPTY)
                    return false;
            }
        }
        return true;
    }
    /**
     * controlla se la colonna scelta ha spazio sufficiente per le carte
     * @author Ettori Giammusso
     * @param: indice di colonna
     * @param: numero carte
     * @return true sse le carte ci stanno
     */
    public boolean checkCol(int col, int numCards){
        int freeCard = getFirstFreeCard(col) + 1;
        return freeCard >= numCards;
    }
    /**
     * prende l'indice della prima cella libera della colonna, parti dal basso e vai verso l'alto
     * @author Ettori Giammusso
     * @param: l'indice della colonna
     * @return l'indice della prima cella (riga) libera
     */
    private int getFirstFreeCard(int col){
        for(int i = ROWS - 1; i >= 0; i--){
            if(library[i][col].color == EMPTY){
                return i;
            }
        }
        return -1;
    }
    /**
     * inserisce le carte nella libreria del player corrente
     * @author Ettori Giammusso
     * @param: indice della colonna in cui inserire
     * @param: carte da inserire fisicamente in libreria
     * @return: void
     */
    public void insertCards(int col, ArrayList<Card> cards){
        int place = getFirstFreeCard(col);
        for(int i = 0; i < cards.size(); i++){
            library[place - i][col] = cards.get(i);
        }
        return;
    }
    /**
     * resetta la matrice usata nella dfs per memorizzare i nodi visitati
     * @author Ettori Giammusso
     * @param: void
     * @return: void
     */
    private void resetVisitedMatrix(){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                visitedMatrix[i][j] = 0;
            }
        }
    }
    /**
     * controlla se la posizione è valida nella matrice
     * @author Ettori Giammusso
     * @param: posizione X
     * @param: posizione y
     * @return: true sse la posizione è valida
     */
    private boolean indexNotValid(int x, int y){
        return x < 0 || x >= ROWS || y < 0 || y >= COLS;
    }
    /**
     * classico algoritmo di ricerca in profondità
     * @author Ettori Giammusso
     * @param: posizione x iniziale
     * @param: posizione y finale
     * @param: colore da seguire
     * @return: void
     */
    private void dfs(int i, int j, Color color) {
        if (indexNotValid(i, j) || library[i][j].color != color || visitedMatrix[i][j] == 1)
            return;
        countVisitedCards++;
        visitedMatrix[i][j] = 1;
        dfs(i + 1, j, color);
        dfs(i - 1, j, color);
        dfs(i, j + 1, color);
        dfs(i, j - 1, color);
    }
    /**
     * conta i punti che vengono dati per la tessere adiacenti
     * @author Ettori Giammusso
     * @param: void
     * @return: il numero di punti fatti da questo player
     */
    public int countGroupedPoints(){
        int points = 0;
        resetVisitedMatrix();
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(library[i][j].color != EMPTY) {
                    countVisitedCards = 0;
                    dfs(i, j, library[i][j].color);
                    if(countVisitedCards == 3){
                        points += 2;
                    }
                    else if(countVisitedCards == 4){
                        points += 3;
                    }
                    else if(countVisitedCards == 5){
                        points += 5;
                    }
                    else if(countVisitedCards >= 6){
                        points += 8;
                    }
                }
            }
        }
        return points;
    }
    public void draw(){return;}
    /**
     * controlla che le due librerie abbiano le carte con lo stesso colore
     * @author Ettori Giammusso
     * @param: libreria da confrontare
     * @return: true sse le librerie sono uguali
     */
    public boolean sameLibraryColor(Library lib){
        for(int i=0; i<ROWS; i++){
            for(int j=0; j<COLS; j++){
                if(library[i][j].color != lib.library[i][j].color){
                    return false;
                }
            }
        }
        return true;
    }
}