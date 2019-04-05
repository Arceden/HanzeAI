package Controllers;

import GameModes.Reversi;
import Views.ReversiView;

public class ReversiController {

    private Reversi reversiModel;
    private ReversiView reversiView;


    public ReversiController(Reversi model, ReversiView view)
    {
        this.reversiModel = model;
        this.reversiView = view;
    }

}
