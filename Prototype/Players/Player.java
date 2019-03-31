package Players;

public interface Player {
    /** Execute the move on behalf of the current user */
    public boolean move(int coordinate);

    /** Request a move from the player and wait until it has been done */
    public int requestMove();

    public String getUsername();
}
