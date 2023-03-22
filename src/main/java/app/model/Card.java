package app.model;

import static app.model.Color.EMPTY;

public class Card {
    public Color color;
    private String imagePath;
    private int x;
    private int y;

    public Card(Color col){
        color = col;
    }
    public Card(){
        color = EMPTY;
    }
    private void draw(){}
}