package Players;

import GameModes.Game;

public class ViewPlayer extends AbstractPlayer {

    /** Initialize the Player by storing the username in the super class. */
    public ViewPlayer(String username){
        super(username);
    }

    @Override
    /** Execute the move on behalf of the current user */
    public boolean move(int coordinate) {
        return false;
    }

    /** Request a move from the player and wait until it has been done */
    @Override
    public int requestMove(Game game) {
        System.err.println("Do not use the "+getClass().getSimpleName()+" requestMove method." +
                "The move is being requested from the game controller class.");
        return 0;
    }

}
