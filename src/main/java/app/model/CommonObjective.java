package app.model;

public class CommonObjective extends Objective {
    public final Strategy algorithm;
    public CommonObjective(Strategy algorithm, String image){
        this.imagePath = image;
        this.algorithm = algorithm;
    }
    public void draw(){return;}
}

// --------------------------- 12 algoritmi ------------------------------------------------ //

class DFSHelper{
    public static void resetVisitedMatrix(int[][] mat, int ROWS, int COLS){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                mat[i][j] = 0;
            }
        }
    }
    public static boolean isIndexValid(int x, int y, int ROWS, int COLS){
        return x >= 0 && x < ROWS && y >= 0 && y < COLS;
    }
    public static boolean isVisited(int x, int y, int[][] mat){
        int temp = mat[x][y];
        mat[x][y] = 1;
        return temp == 1;
    }
}

