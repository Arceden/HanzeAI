package Players;

import Algorithms.AI;
import Algorithms.Minimax;
import GameModes.Game;

public class AIPlayer extends AbstractPlayer {
    private AI algorithm;
    private Game game;

    /** Initialize the Player by storing the username in the super class. */
    public AIPlayer(String username){
        super(username);
        this.algorithm = new Minimax(game);
    }

    @Override
    /** Execute the move on behalf of the current user */
    public boolean move(int coordinate) {
        return false;
    }

    /** Request a move from the player and wait until it has been done */
    @Override
    public int requestMove(Game game) {
        //System.out.println(game.getCurrentPlayer().getUsername());
        int player;
        if(game.getCurrentPlayer() == game.getPlayer1()) player = 1;
        else player = 2;
        return algorithm.bestMove(game.getBoard(), player, game);
    }

}
