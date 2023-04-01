package app.model;

import app.view.TUI.TUIHelper;

import java.io.Serializable;

import static app.model.Color.*;
/**
 * class that represent the cards, both in the library and in the board. Mutable
 * @author Ettori Giammusso
 */
public class Card implements Serializable {
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

    public void draw(){System.out.print(mapColor(color));}
}