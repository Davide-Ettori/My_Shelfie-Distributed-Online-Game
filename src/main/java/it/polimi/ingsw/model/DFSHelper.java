package it.polimi.ingsw.model;

/**
 * helper class for the Depth First Search algorithm. Immutable
 * @author Ettori Faccincani
 *
 */
public class DFSHelper {
    private static final int ROWS = 6;
    private static final int COLS = 5;
    /**
     * reset the matrix of the nodes visited in the DFS
     *
     * @param mat  the matrix
     * @author Ettori Giammusso
     */
    public static void resetVisitedMatrix(int[][] mat) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                mat[i][j] = 0;
            }
        }
    }
    /**
     * check if the index is in the matrix
     *
     * @param x    position x
     * @param y    posizione y
     * @return true iff the index is in the matrix
     * @author Ettori Giammusso
     */
    public static boolean isIndexValid(int x, int y) {
        return x >= 0 && x < ROWS && y >= 0 && y < COLS;
    }
    /**
     * check if the node was already visited, otherwise visit the node
     *
     * @param x   position x
     * @param y   position y
     * @param mat matrix of visited nodes
     * @return true iff the node was visited
     * @author Ettori Giammusso
     */
    public static boolean isVisited(int x, int y, int[][] mat) {
        int temp = mat[x][y];
        mat[x][y] = 1;
        return temp == 1;
    }
}
