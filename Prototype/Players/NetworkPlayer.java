package Players;

public class NetworkPlayer extends AbstractPlayer {

    /** Initialize the Player by storing the username in the super class. */
    public NetworkPlayer(String username){
        super(username);
    }

    @Override
    /** Execute the move on behalf of the current user */
    public boolean move(int coordinate) {
        return false;
    }

    @Override
    /** Request a move from the player and wait until it has been done */
    public int requestMove() {
        //Return the move
        return 0;
    }

}
