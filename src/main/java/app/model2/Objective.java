package app.model2;

class PrivateObjective extends Objective {
    private int[] arrayofpoints = new int[7];
    private Color[][] matrix = new Color[this.rows][this.colls];
    public int objectiveid;

    public PrivateObjective(int[] arrayofpoints,Color[][] matrix,int objectiveid){
        this.arrayofpoints=arrayofpoints;
        this.matrix=matrix;
        this.objectiveid=objectiveid;
    }
    public /*int*/ void countpoints(Card[][] cards) {

        //return intero
    }

    ;

    private  /*int*/ void countmatch(Card[][] cards) {

    }

    ;

    public void draw() {

    }


}
abstract class Objective {
    private int imagepath;
    public int index;
    protected int colls;
    protected int rows;
}

interface Strategy {
    int rows = 5;
    int colls = 6;

    boolean checkMatch(Card[][] board);

}

public class CommonObjective extends Objective {
    private Strategy algorithm;
    public CommonObjective(Strategy algorithm){
        this.algorithm = algorithm;
    }
    public void choose(){
        algorithm.checkMatch(serverBoard.board);
    }

    public void draw() {

    };
}


// ---------------------------12 algoritmi------------------------------------------------//
public class Algo_CO_1 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

    };
}

public class Algo_CO_2 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

    };
}

public class Algo_CO_3 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

    };
}

public class Algo_CO_4 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

    };
}

public class Algo_CO_5 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

    };
}

public class Algo_CO_6 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

    };
}

public class Algo_CO_7 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

    };
}

public class Algo_CO_8 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

    };
}

public class Algo_CO_9 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

    };
}

public class Algo_CO_10 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

    };
}

public class Algo_CO_11 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

    };
}

public class Algo_CO_12 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

    };
}
