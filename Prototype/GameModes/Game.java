package GameModes;

import Players.Player;

public interface Game {
    /** Request the next player to move */
    public int getNextMove();

    /** Execute the move on behalf of the current player. */
    public boolean move(int coordinate);

    public void switchTurns();

    public boolean validMovesLeft();

    /** Check if the specified move is valid */
    public boolean moveIsValid(int coordinate);

    /** Start the game by preparing the board and choose who starts (or retreive the starting player if online) */
    public void start();

    /** Check the current status of the board and detemine if the game has ended */
    public boolean hasEnded();

    /** Get player 1 */
    public Player getPlayer1();

    /** Get player 2 */
    public Player getPlayer2();

    /** Get the game name */
    public String getName();

    /** Get the player who is playing right now */
    public Player getCurrentPlayer();

    /** Get current state of the board */
    public Integer[][] getBoard();
}
