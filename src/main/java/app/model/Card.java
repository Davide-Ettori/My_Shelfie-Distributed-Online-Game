package app.model;

import static app.model.Color.*;
/**
 * class that represent the cards, both in the library and in the board
 * @author Ettori Giammusso
 * mutable
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
    public Card(Card c){ // copy constructor
        color = c.color;
        imagePath = c.imagePath;
        x = c.x;
        y = c.y;
    }
    /**
     * setter of the x coordinate
     * @author Ettori Giammusso
     * @param posX position that needs to be set
     * @return void
     */
    public void setX(int posX){x = posX;}
    /**
     * setter of the y coordinate
     * @author Ettori Giammusso
     * @param posY position that needs to be set
     * @return void
     */
    public void setY(int posY){y = posY;}

    public void draw(){return;}
}