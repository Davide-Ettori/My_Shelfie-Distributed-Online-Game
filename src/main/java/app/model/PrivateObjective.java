package app.model;

import static app.model.Color.*;

class PrivateObjective extends Objective {
    private int[] arrayOfPoints;
    private Color[][] matrix;
    public int objectiveId; // non sono sicuro che serva, per ora lo teniamo

    public PrivateObjective(Color[][] mat, int id, String image){
        arrayOfPoints = new int[]{0,1,2,4,6,9,12};
        matrix = mat;
        objectiveId = id;
        imagePath = image;
    }
    public int countPoints(Card[][] cards) {
        return arrayOfPoints[countMatch(cards)];
    };

    private  int countMatch(Card[][] cards) {
        int count = 0;
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(matrix[i][j] != EMPTY && matrix[i][j] == cards[i][j].color)
                    count++;
            }
        }
        return count;
    }

    public void draw() {return;}
}