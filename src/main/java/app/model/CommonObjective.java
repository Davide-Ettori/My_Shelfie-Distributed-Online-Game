package app.model;

import java.util.ArrayList;

import static app.model.Color.*;

public class CommonObjective extends Objective {
    private final Strategy algorithm;
    public CommonObjective(Strategy algorithm, String image){
        this.imagePath = image;
        this.algorithm = algorithm;
    }
    public void draw(){return;}
}

interface Strategy {
    int ROWS = 5;
    int COLS = 6;

    boolean checkMatch(Card[][] board);
}

// ---------------------------12 algoritmi------------------------------------------------//

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
}

class Algo_CO_1 implements Strategy{ // primo della prima colonna
    private final int[][] visitedMatrix = new int[ROWS][COLS];
    private int countVisitedCards;
    private void dfs(int i, int j, Color color, Card[][] board){
        if (!DFSHelper.isIndexValid(i, j, ROWS, COLS) || board[i][j].color != color || visitedMatrix[i][j] == 1)
            return;
        countVisitedCards++;
        visitedMatrix[i][j] = 1;
        dfs(i + 1, j, color, board);
        dfs(i - 1, j, color, board);
        dfs(i, j + 1, color, board);
        dfs(i, j - 1, color, board);
    }
    @Override
    public boolean checkMatch(Card[][] board) {
        int match = 0;
        DFSHelper.resetVisitedMatrix(visitedMatrix, ROWS, COLS);
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(board[i][j].color == EMPTY)
                    continue;
                countVisitedCards = 0;
                dfs(i, j, board[i][j].color, board);
                if(countVisitedCards >= 2)
                    match++;
            }
        }
        return match >= 6;
    };
}

class Algo_CO_2 implements Strategy{ // primo seconda colonna
    private boolean checkDiagonal(int x, int y, Color color, Card[][] board){
        for(int i = 0; i < 5; i++){
            if(!DFSHelper.isIndexValid(x + i, y + i, ROWS, COLS))
                return false;
            if(board[x + i][y + i].color != color)
                return false;
        }
        return true;
    }
    @Override
    public boolean checkMatch(Card[][] board) {
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(board[i][j].color == EMPTY)
                    continue;
                if(checkDiagonal(i, j, board[i][j].color, board))
                    return true;
            }
        }
        return false;
    }
}

class Algo_CO_3 implements Strategy{ // secondo prima colonna
    @Override
    public boolean checkMatch(Card[][] board) {
        Color color = board[0][0].color;
        if(color == EMPTY)
            return false;
        return (board[ROWS - 1][COLS - 1].color == color) && (board[0][COLS - 1].color == color) && (board[ROWS - 1][0].color == color);
    }
}

class Algo_CO_4 implements Strategy{ // secondo seconda colonna
    @Override
    public boolean checkMatch(Card[][] board) {
        int count = 0;
        ArrayList<Color> colors;
        for(int i = 0; i < ROWS; i++){
            colors = new ArrayList<>();
            for(int j = 0; j < COLS; j++){
                if(!colors.contains(board[i][j].color))
                    colors.add(board[i][j].color);
            }
            if(colors.size() <= 3)
                count++;
        }
        return count >= 4;
    }
}

class Algo_CO_5 implements Strategy{ // terzo prima colonna
    private final int[][] visitedMatrix = new int[ROWS][COLS];
    private int countVisitedCards;
    private void dfs(int i, int j, Color color, Card[][] board){
        if (!DFSHelper.isIndexValid(i, j, ROWS, COLS) || board[i][j].color != color || visitedMatrix[i][j] == 1)
            return;
        countVisitedCards++;
        visitedMatrix[i][j] = 1;
        dfs(i + 1, j, color, board);
        dfs(i - 1, j, color, board);
        dfs(i, j + 1, color, board);
        dfs(i, j - 1, color, board);
    }
    @Override
    public boolean checkMatch(Card[][] board) {
        int match = 0;
        DFSHelper.resetVisitedMatrix(visitedMatrix, ROWS, COLS);
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(board[i][j].color == EMPTY)
                    continue;
                countVisitedCards = 0;
                dfs(i, j, board[i][j].color, board);
                if(countVisitedCards >= 4)
                    match++;
            }
        }
        return match >= 4;
    };
}

class Algo_CO_6 implements Strategy{ // terzo seconda colonna
    @Override
    public boolean checkMatch(Card[][] board) {
        int count = 0;
        ArrayList<Color> colors;
        for(int j = 0; j < COLS; j++){
            colors = new ArrayList<>();
            for(int i = 0; i < ROWS; i++){
                if(!colors.contains(board[i][j].color))
                    colors.add(board[i][j].color);
            }
            if(colors.size() == 6)
                count++;
        }
        return count >= 2;
    }
}

class Algo_CO_7 implements Strategy{ // quarto prima colonna
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    }
}

class Algo_CO_8 implements Strategy{ // quarto seconda colonna
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    }
}

class Algo_CO_9 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    }
}

class Algo_CO_10 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    }
}

class Algo_CO_11 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    }
}

class Algo_CO_12 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    }
}
