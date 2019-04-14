package Players;

public abstract class AbstractPlayer implements Player {
    private String username;

    /** Initialize the Player with a username */
    public AbstractPlayer(String username){
        this.username = username;
    }

    @Override
    /** Return the player's username */
    public String getUsername() {
        return username;
    }
}
