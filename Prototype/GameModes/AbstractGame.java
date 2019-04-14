package GameModes;

import Players.Player;

import java.util.Arrays;

public abstract class AbstractGame implements Game {

    /* Store players */
    Player player1;
    Player player2;
    Player playerTurn;

    /* Store the game name */
    String name;

    /* Store the board data */
    Integer[][] board;

    /** Store the initialized players */
    AbstractGame(Player player1, Player player2){
        this.player1=player1;
        this.player2=player2;
    }

    /** Switch the player turns. */
    public void switchTurns(){
        if(playerTurn.getUsername().equalsIgnoreCase(player1.getUsername()))
            playerTurn=player2;
        else
            playerTurn=player1;
    }

    /** Print the board in the terminal */
    private void printBoard()
    {
        for(Integer[] arr : board){
            System.out.println(Arrays.toString(arr));
        }
    }

    /** Start the game by setting the correct starting values. */
    public void start() {
        playerTurn=player1;
    }

    /** Get the next move from the current player, if possible. */
    public int getNextMove(){
        return playerTurn.requestMove(this);
    }

    /** Get the game name */
    public String getName() {
        return name;
    }

    /** Get player 1 */
    public Player getPlayer1() {
        return player1;
    }

    /** Get player 2 */
    public Player getPlayer2() {
        return player2;
    }

    /** Get the player who is playing right now */
    public Player getCurrentPlayer() {
        return playerTurn;
    }

    /** Get current state of the board */
    public Integer[][] getBoard(){
        return board;
    }

}
