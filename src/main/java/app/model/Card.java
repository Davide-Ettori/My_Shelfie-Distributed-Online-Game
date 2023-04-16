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
    public String imagePath = "";

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
    }
    /**
     * copy constructor for this class, used for deep copying objects
     * @param c the object to copy
     */
    public Card(Card c){ // copy constructor
        color = c.color;
        imagePath = c.imagePath;
    }
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
     * method used to draw the card itself (TUI)
     */
    public void draw(){System.out.print(mapColor(color));}
}