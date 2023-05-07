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
     * @author Ettori
     */
    public Card(Color col){
        color = col;
    }

    /**
     * the most simple constructor, used to initialize the empty cards
     * @author Ettori
     */
    public Card(){
        color = EMPTY;
    }
    /**
     * copy constructor for this class, used for deep copying objects
     * @param c the object to copy
     * @author ettori
     */
    public Card(Card c){ // copy constructor
        color = c.color;
        imagePath = c.imagePath;
    }
    /**
     * assigns ansi code to each color to be printed then return the string
     * @param c the color to be printed
     * @author Gumus
     */
    private String mapColor(Color c) {
        String Pink = "\u001B[45m";
        String Cyan = "\u001B[46m";
        String Blue = "\u001B[44m";
        String Green = "\u001B[42m";
        String Yellow = "\u001B[43m";
        String White= "\u001B[47m";
        String Black = "\u001B[40m";
        String Ansi_Reset = "\u001B[0m";
        if (c == PINK)
            return Pink+" P "+Ansi_Reset;
        if (c == CYAN)
            return Cyan+" C "+Ansi_Reset;
        if (c == BLUE)
            return Blue+" B "+Ansi_Reset;
        if (c == GREEN)
            return Green+" G "+Ansi_Reset;
        if (c == YELLOW)
            return Yellow+ " Y "+Ansi_Reset;
        if (c == WHITE)
            return White+ " W "+Ansi_Reset;
        if (c == EMPTY)
            return Black+" # "+Ansi_Reset;
        return " ? ";
    }

    /**
     * method used to draw the card itself (TUI)
     * @author Ettori
     */
    public void draw(){System.out.print(mapColor(color));}
}