package app.model;

interface Board {
    int DIM = 9;
    private void createBoard(){};
    void updateBoard(Board board);
    void sendCurrentBoard();
    private void Draw() {}
}
