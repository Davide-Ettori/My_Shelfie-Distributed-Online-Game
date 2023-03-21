package app.model_1;

import static app.model_1.Color.EMPTY;

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
}