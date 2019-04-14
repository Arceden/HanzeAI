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

    @Override
    /** Request a move from the player and wait until it has been done */
    public int requestMove() {
        //return algorithm.calculateMove();
        return algorithm.calculateMove(game.getBoard(),1,4,true);
    }

}
