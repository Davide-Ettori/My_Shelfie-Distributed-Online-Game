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
     * reset the matrix of the nodes visited in the DFS
     * @author Ettori Giammusso
     * @param mat the matrix
     * @param ROWS number of rows
     * @param COLS number of cols
     * @return void
     */
    public static void resetVisitedMatrix(int[][] mat, int ROWS, int COLS){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                mat[i][j] = 0;
            }
        }
    }
    /**
     * check if the index is in the matrix
     * @author Ettori Giammusso
     * @param x position x
     * @param y posizione y
     * @param ROWS number of rows
     * @param COLS numbers of columns
     * @return true iff the index is in the matrix
     */
    public static boolean isIndexValid(int x, int y, int ROWS, int COLS){
        return x >= 0 && x < ROWS && y >= 0 && y < COLS;
    }
    /**
     * check if the node was already visited, otherwise visit the node
     * @author Ettori Giammusso
     * @param x position x
     * @param y position y
     * @param mat matrix of visited nodes
     * @return true iff the node was visited
     */
    public static boolean isVisited(int x, int y, int[][] mat){
        int temp = mat[x][y];
        mat[x][y] = 1;
        return temp == 1;
    }
}

