package hu.bme.aut.android.tictactoe.model;

public class TicTacToeModel {

    private static TicTacToeModel instance = null;

    public static TicTacToeModel getInstance()  {
        if (instance == null) {
            instance = new TicTacToeModel();
        }

        return instance;
    }

    private TicTacToeModel() {
    }

    public static final short EMPTY = 0;
    public static final short CROSS = 1;
    public static final short CIRCLE = 2;

    private short[][] model = {
        {EMPTY, EMPTY, EMPTY},
        {EMPTY, CIRCLE, EMPTY},
        {EMPTY, CROSS, EMPTY}
    };

    private short nextPlayer = CIRCLE;

    public void setFieldContent(short x, short y,
                                short player) {
        model[x][y] = player;
    }

    public short getFieldContent(short x, short y) {
        return model[x][y];
    }


}
