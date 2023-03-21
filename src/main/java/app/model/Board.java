package app.model;

interface Board {
    int DIM = 9;
    private void createBoard(){};
    void updateBoard(Board board);
    void sendCurrentBoard();
    private void Draw() {}
    Card[][] getGameBoard(); // ho definito alcuni getter che saranno necessari per alcune variabili non costanti
    CommonObjective getCO_1();
    CommonObjective getCO_2();
}
