package GameModes;

import Players.Player;

import java.util.ArrayList;


public interface Game {
    /** Request the next player to move */
    public int getNextMove();

    /** Execute the move and return true if it has been successfully executed */
    public boolean move(int coordinate);

    public int MakeMove(int x, int y);

    public void switchTurns();

    public boolean validMovesLeft();

    /** Check if the specified move is valid */
    public boolean moveIsValid(int coordinate);

    /** Start the game by preparing the board and choose who starts (or retreive the starting player if online) */
    public void start();

    /** Check the current status of the board and detemine if the game has ended */
    public boolean hasEnded();

    /** Place the player in the game */
    public void setPlayer1(Player player1);
    public void setPlayer2(Player player2);

    public String getName();
    public Player getCurrentPlayer();
    public String getCurrentStatus();

    public Integer[][] getBoard();
}
