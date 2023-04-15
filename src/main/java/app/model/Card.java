package app.model;

import java.io.Serializable;

import static app.model.Color.*;
/**
 * class that represent the cards, both in the library and in the board. Mutable
 * @author Ettori Giammusso
 */
public class Card implements Serializable {
    /** the color of the card, 6 types + EMPTY */
    public Color color;
    public String imagePath;
    private int x;
    private int y;

    /**
     * the full constructor, used to set all the possibile attributes of this class
     * @param col the color to set
     * @param image the image icon of the card
     * @param posX the x position in matrix
     * @param posY tht y position in the matrix
     */
    public Card(Color col, String image, int posX, int posY){
        color = col;
        imagePath = image;
        x = posX;
        y = posY;
    }
    /**
     * constructor that initialize both the color and the image
     * @param col the color of the card
     * @param image the path of the image file
     * @author Ettori
     */
    public Card(Color col, String image){
        color = col;
        imagePath = image;
    }
    /**
     * standard constructor to initialize card with the right color
     * @param col the color to set
     */
    public Card(Color col){
        color = col;
    }

    /**
     * the most simple constructor, used to initialize the empty cards
     */
    public Card(){
        color = EMPTY;
        imagePath = "assets/item tiles/Gatti1.1.png"; // carta generica come placeholder
    }
    /**
     * copy constructor for this class, used for deep copying objects
     * @param c the object to copy
     */
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
    private char mapColor(Color c) {
        if (c == PINK)
            return 'P';
        if (c == CYAN)
            return 'C';
        if (c == BLUE)
            return 'B';
        if (c == GREEN)
            return 'G';
        if (c == YELLOW)
            return 'Y';
        if (c == WHITE)
            return 'W';
        if (c == EMPTY)
            return '#';
        return '?';
    }

    /**
     * method used to draw the card itself (TUI or GUI)
     */
    public void draw(){System.out.print(mapColor(color));}
}