package app.model;

public class CommonObjective extends Objective {
    private Strategy algorithm;
    public CommonObjective(Strategy algorithm, String image){
        this.imagePath = image;
        this.algorithm = algorithm;
    }
    public void draw(){return;}
}

interface Strategy {
    int ROWS = 5;
    int COLS = 6;

    boolean checkMatch(Card[][] board);
}

// ---------------------------12 algoritmi------------------------------------------------//

class Algo_CO_1 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    };
}

class Algo_CO_2 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    };
}

class Algo_CO_3 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    };
}

class Algo_CO_4 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    };
}

class Algo_CO_5 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    };
}

class Algo_CO_6 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    };
}

class Algo_CO_7 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    };
}

class Algo_CO_8 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    };
}

class Algo_CO_9 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    };
}

class Algo_CO_10 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    };
}

class Algo_CO_11 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    };
}

class Algo_CO_12 implements Strategy{
    @Override
    public boolean checkMatch(Card[][] board) {

        return true;
    };
}
