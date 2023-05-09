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
        if(System.getProperty("os.name").contains("Windows"))
            return mapColorWindows(c);
        String pink = "\u001B[45m";
        String cyan = "\u001B[46m";
        String blue = "\u001B[44m";
        String green = "\u001B[42m";
        String yellow = "\u001B[43m";
        String white= "\u001B[47m";
        String black = "\u001B[40m";
        String ansi_Reset = "\u001B[0m";
        if (c == PINK)
            return pink+" P "+ansi_Reset;
        if (c == CYAN)
            return cyan+" C "+ansi_Reset;
        if (c == BLUE)
            return blue+" B "+ansi_Reset;
        if (c == GREEN)
            return green+" G "+ansi_Reset;
        if (c == YELLOW)
            return yellow+ " Y "+ansi_Reset;
        if (c == WHITE)
            return white+ " W "+ansi_Reset;
        if (c == EMPTY)
            return black+" # "+ansi_Reset;
        return " ? ";
    }

    private String mapColorWindows(Color c){
        if (c == PINK)
            return " P ";
        if (c == CYAN)
            return " C ";
        if (c == BLUE)
            return " B ";
        if (c == GREEN)
            return " G ";
        if (c == YELLOW)
            return " Y ";
        if (c == WHITE)
            return " W ";
        if (c == EMPTY)
            return " # ";
        return " ? ";
    }

    /**
     * method used to draw the card itself (TUI)
     * @author Ettori
     */
    public void draw(){System.out.print(mapColor(color));}
}