package app.model;

import static app.model.Color.*;
/**
 * classe che rappresenta le carte, sia della libreria che della board
 * @author Ettori Giammusso
 */
public class Card {
    public Color color;
    private String imagePath;
    private int x;
    private int y;

    public Card(Color col, String image, int posX, int posY){
        color = col;
        imagePath = image;
        x = posX;
        y = posY;
    }
    public Card(int posX, int posY){
        color = EMPTY;
        imagePath = "path della carta vuota"; // va inserito quello vero
        x = posX;
        y = posY;
    }
    public Card(Color col){
        color = col;
    }
    public Card(){
        color = EMPTY;
    }
    /**
     * setter della coordinata x
     * @author Ettori Giammusso
     * @param: posizione da settare
     * @return: void
     */
    public void setX(int posX){x = posX;}
    /**
     * setter della coordinata y
     * @author Ettori Giammusso
     * @param: posizione da settare
     * @return: void
     */
    public void setY(int posY){y = posY;}

    public void draw(){return;}
}