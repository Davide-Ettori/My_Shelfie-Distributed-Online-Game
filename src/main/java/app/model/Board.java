package app.model;

import java.util.LinkedList;

public interface Board {
    int DIM = 9;
    private void createBoard(){}
    void updateBoard(Board board);
    void sendCurrentBoard();
    private void draw() {}
    Card[][] getGameBoard(); // ho definito alcuni getter che saranno necessari per alcune variabili non costanti
    CommonObjective getCO_1();
    CommonObjective getCO_2();
    LinkedList<Integer> getPCO_1();
    LinkedList<Integer> getPCO_2();
}
