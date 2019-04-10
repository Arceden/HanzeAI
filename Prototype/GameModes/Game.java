package GameModes;

import Players.Player;

public interface Game {
    /** Request the next player to move */
    public int getNextMove();

    /** Execute the move and return true if it has been successfully executed */
    public boolean move(int coordinate);

    /** Check if the specified move is valid */
    public boolean moveIsValid(int x, int y);

    /** Start the game by preparing the board and choose who starts (or retreive the starting player if online) */
    public void start();

    /** Check the current status of the board and detemine if the game has ended */
    public boolean hasEnded();

    /** Place the player in the game */
    public void setPlayer1(Player player1);
    public void setPlayer2(Player player2);

    public String getName();
    public String getCurrentPlayer();
    public String getCurrentStatus();
}
