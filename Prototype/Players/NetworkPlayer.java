package Players;

import GameModes.Game;

public class NetworkPlayer extends AbstractPlayer {

    /** Initialize the Player by storing the username in the super class. */
    public NetworkPlayer(String username){
        super(username);
    }

    /** Execute the move on behalf of the current user */
    @Override
    public boolean move(int coordinate) {
        return false;
    }

    /** Request a move from the player and wait until it has been done */
    @Override
    public int requestMove(Game game) {
        return 0;
    }

}
