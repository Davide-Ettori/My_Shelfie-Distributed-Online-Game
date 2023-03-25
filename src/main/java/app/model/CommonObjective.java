package app.model;

/**
 * class which represent the common objectives which all the players must try to achieve
 * @author Ettori Faccincani
 * immutable
 */
public class CommonObjective extends Objective {
    public final Strategy algorithm;
    public CommonObjective(Strategy algorithm, String image){
        this.imagePath = image;
        this.algorithm = algorithm;
    }
    public void draw(){return;}
}

/**
 * helper class for the Depth First Search algorithm
 * @author Ettori Faccincani
 * immutable
 */
class DFSHelper{
    /**
     * resetta la matrice dei nodi visitati in DFS
     * @author Ettori Giammusso
     * @param: la matrice
     * @param: numero righe
     * @param: numero colonne
     * @return: void
     */
    public static void resetVisitedMatrix(int[][] mat, int ROWS, int COLS){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                mat[i][j] = 0;
            }
        }
    }
    /**
     * controlla che l'indice stia nella matrice
     * @author Ettori Giammusso
     * @param: pos x
     * @param: pos y
     * @param: numero righe
     * @param: numero colonne
     * @return: true sse l'indice sta nella matrice
     */
    public static boolean isIndexValid(int x, int y, int ROWS, int COLS){
        return x >= 0 && x < ROWS && y >= 0 && y < COLS;
    }
    /**
     * controlla se il nodo è visitato, nel caso non lo sia lo visita
     * @author Ettori Giammusso
     * @param: pos x
     * @param: pos y
     * @param: matrice dei nodi visitati
     * @return: true sse il nodo visitato
     */
    public static boolean isVisited(int x, int y, int[][] mat){
        int temp = mat[x][y];
        mat[x][y] = 1;
        return temp == 1;
    }
}

