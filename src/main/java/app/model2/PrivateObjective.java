package app.model2;

public class PrivateObjective extends Objective {
    private int[] arrayofpoints = new int[7];
    private Color[][] matrix = new Color[this.rows][this.colls];
    public int objectiveid;

    public PrivateObjective(int[] arrayofpoints,Color[][] matrix,int objectiveid){
        this.arrayofpoints=arrayofpoints;
        this.matrix=matrix;
        this.objectiveid=objectiveid;
    }
    public int countpoints(Card[][] cards) {

        return 0;
    };

    private  int countmatch(Card[][] cards) {

        return 0;
    };

    public void draw() {

    }

}