package app.model;

class PrivateObjective extends Objective {
    private int[] arrayOfPoints;
    private Color[][] matrix;
    public int objectiveId; // non sono sicuro che serva, per ora lo teniamo

    public PrivateObjective(Color[][] matrix, int objectiveId){
        this.arrayOfPoints = new int[]{0,1,2,4,6,9,12};
        this.matrix = matrix;
        this.objectiveId = objectiveId;
    }
    public int countpoints(Card[][] cards) {

        return 0;
    };

    private  int countmatch(Card[][] cards) {

        return 0;
    };

    public void draw() {return;}
}