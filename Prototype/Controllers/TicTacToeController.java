package Controllers;

import GameModes.TicTacToe;
import Views.TicTacToeView;

public class TicTacToeController {

    private TicTacToe ticTacToeModel;
    private TicTacToeView ticTacToeView;


    public TicTacToeController(TicTacToe model, TicTacToeView view)
    {
        this.ticTacToeModel = model;
        this.ticTacToeView = view;
        //setTestValue();
    }

    public void setTestValue()
    {
        this.ticTacToeModel.setTest("test");
        this.ticTacToeView.setTestValue(ticTacToeModel.getTest());
    }


}
