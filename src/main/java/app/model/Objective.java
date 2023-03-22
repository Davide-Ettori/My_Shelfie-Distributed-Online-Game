package app.model;

abstract class Objective {
    protected String imagePath;
    // public int index; // probabilmente non serve, vedremo in futuro
    public int COLS = 5;
    public int ROWS = 6;

    public void draw(){}
}