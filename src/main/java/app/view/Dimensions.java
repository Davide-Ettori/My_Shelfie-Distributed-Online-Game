package app.view;

public class Dimensions {
    public static final int CO_w = 144;
    public static final int CO_h = 100;
    public static final int PO_h = 100;
    public static final int PO_w = 75;
    public static final int pointsDim = 40;
    public static final int chairmanDim = 100;
    public static final int libSecondaryDim = 250;
    public static final int libPrimaryDim = 300;
    public static final int boardDim = 400; // every card is 400 / 9 = 44.44, approximately 44 pixels
    public static final int cardDimBoard = 400 / 10 - 2;
    public static final int cardDimLibPrimary = cardDimBoard;
    public static final int cardDimLibSeconday = cardDimBoard * (libSecondaryDim/5) / (libPrimaryDim/5); //should be circa 35 pixels
    public static final int textCols = 30;
    public static final int btnW = 200;
    public static final int btnH = 50;
    public static final int COPointsPadding_x = 30;
    public static final int generalBorder = 5;
    public static final String pathPointsCO = "assets/scoring tokens/scoring";
}
