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
     */
    public void setX(int posX){x = posX;}
    /**
     * setter of the y coordinate
     * @author Ettori Giammusso
     * @param posY position that needs to be set
     */
    public void setY(int posY){y = posY;}
    /**
     * setter of the x and y coordinate
     * @author Ettori Giammusso
     * @param x position on x-axis that needs to be set
     * @param y position on y-axis that needs to be set
     */
    public void setCoords(int x, int y){setX(x); setY(y);}

    public void draw(Card card){System.out.print(card.color);}
}