package app.model2;

public class Card {
    private Color color;
    private String imagePath;
    private int x;
    private int y;
    public Card(Color color,String imagePath,int x,int y){
        this.color=color;
        this.imagePath=imagePath;
        this.x=x;
        this.y=y;
    }
}
