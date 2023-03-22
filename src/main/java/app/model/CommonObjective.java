package app.model;

import java.util.ArrayList;
import java.util.Arrays;

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
    public static boolean isVisited(int x, int y, int[][] mat){
        int temp = mat[x][y];
        mat[x][y] = 1;
        return temp == 1;
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
    private final int[][] visitedMatrix = new int[ROWS][COLS];
    private void dfs(int i, int j, Color color, Card[][] board){
        if (!DFSHelper.isIndexValid(i, j, ROWS, COLS) || board[i][j].color != color || DFSHelper.isVisited(i, j, visitedMatrix))
            return;
        dfs(i + 1, j, color, board);
        dfs(i - 1, j, color, board);
        dfs(i, j + 1, color, board);
        dfs(i, j - 1, color, board);
    }
    private boolean checkForSquare(int x, int y, Color color, Card[][] board){
        ArrayList<Integer> cells = new ArrayList<>(Arrays.asList(x, y, x + 1, y, x, y + 1, x + 1, y + 1));
        for(int i = 0; i < cells.size(); i += 2){
            if(!DFSHelper.isIndexValid(cells.get(i), cells.get(i + 1), ROWS, COLS))
                return false;
            if(visitedMatrix[cells.get(i)][cells.get(i + 1)] == 1)
                return false;
            if(board[cells.get(i)][cells.get(i + 1)].color != color)
                return false;
        }
        return true;
    }
    @Override
    public boolean checkMatch(Card[][] board) {
        int count = 0;
        boolean foundSquare;
        DFSHelper.resetVisitedMatrix(visitedMatrix, ROWS, COLS);
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(board[i][j].color == EMPTY)
                    continue;
                foundSquare = checkForSquare(i, j, board[i][j].color, board);
                dfs(i, j, board[i][j].color, board);
                if(foundSquare)
                    count++;
            }
        }
        return count >= 2;
    }
}

class Algo_CO_8 implements Strategy{ // quarto seconda colonna
    @Override
    public boolean checkMatch(Card[][] board) {
        int count = 0;
        ArrayList<Color> colors;
        for(int i = 0; i < ROWS; i++){
            colors = new ArrayList<>();
            for(int j = 0;  j < COLS; j++){
                if(!colors.contains(board[i][j].color))
                    colors.add(board[i][j].color);
            }
            if(colors.size() == 5)
                count++;
        }
        return count >= 2;
    }
}

class Algo_CO_9 implements Strategy{ // quinto prima colonna
    @Override
    public boolean checkMatch(Card[][] board) {
        int count = 0;
        ArrayList<Color> colors;
        for(int j = 0;  j < COLS; j++){
            colors = new ArrayList<>();
            for(int i = 0; i < ROWS; i++){
                if(!colors.contains(board[i][j].color))
                    colors.add(board[i][j].color);
            }
            if(colors.size() <= 3)
                count++;
        }
        return count >= 3;
    }
}

class Algo_CO_10 implements Strategy{ // quinto seconda colonna
    @Override
    public boolean checkMatch(Card[][] board) {
        Color color;
        ArrayList<Integer> cells;
        boolean flag;
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(board[i][j].color == EMPTY)
                    continue;
                color = board[i][j].color;
                cells = new ArrayList<>(Arrays.asList(i, j, i - 1, j - 1, i + 1, j + 1, i - 1, j + 1, i + 1, j - 1));
                flag = true;
                for(int k = 0; k < cells.size(); k += 2){
                    if(!DFSHelper.isIndexValid(cells.get(k), cells.get(k + 1), ROWS, COLS))
                        flag = false;
                    if(board[cells.get(k)][cells.get(k + 1)].color != color)
                        flag = false;
                }
                if(flag)
                    return true;
            }
        }
        return false;
    }
}

class Algo_CO_11 implements Strategy{ // sesto prima colonna
    private int map(Color color){
        if(color == GREEN)
            return 0;
        if(color == CYAN)
            return 1;
        if(color == BLUE)
            return 2;
        if(color == YELLOW)
            return 3;
        if(color == WHITE)
            return 4;
        if(color == PINK)
            return 5;
        return -1;
    }
    @Override
    public boolean checkMatch(Card[][] board) {
        int[] colorsCounter = new int[]{0,0,0,0,0,0};
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(board[i][j].color == EMPTY)
                    continue;
                colorsCounter[map(board[i][j].color)]++;
            }
        }
        for(int i = 0; i < 6; i++){
            if(colorsCounter[i] >= 8)
                return true;
        }
        return false;
    }
}

class Algo_CO_12 implements Strategy{ // sesto seconda colonna
    private boolean checkLowTriangle(Card[][] board){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(i >= j && board[i][j].color == EMPTY)
                    return false;
                if(i < j && board[i][j].color != EMPTY)
                    return false;
            }
        }
        return true;
    }
    private boolean checkHighTriangle(Card[][] board){
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(i <= j && board[i][j].color == EMPTY)
                    return false;
                if(i > j && board[i][j].color != EMPTY)
                    return false;
            }
        }
        return true;
    }
    @Override
    public boolean checkMatch(Card[][] board) {
        return checkLowTriangle(board) || checkHighTriangle(board);
    }
}
