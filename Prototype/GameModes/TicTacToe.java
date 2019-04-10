package GameModes;

import Observer.Observer;
import Players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TicTacToe implements Game {

    private Player player1;
    private Player player2;
    public Player playerTurn;

    boolean running;

    public final String name = "TicTacToe";

    private static int n = 3;
    private Integer[][] board = new Integer[3][3];

    public TicTacToe(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        playerTurn=player1;
        running=true;
        this.board = initBoard(board);
    }

    @Override
    public boolean move(int coordinate) {
        System.out.println("Moving "+playerTurn.getUsername()+" to "+coordinate+"!");

        int x = (int) Math.floor(coordinate / 3);
        int y = (coordinate % 3);

        int playerNum=0;
        if(playerTurn==player1) playerNum=1;
        if(playerTurn==player2) playerNum=2;

        board[x][y] = playerNum;

        if(gameLogic(playerNum, x, y))
        {
            running=false;
            System.out.println("We have a winner");
        }
        else {
            System.out.println("x : " + x);
            System.out.println("y : " + y);
        }

        switchTurns();
        return true;
    }

    @Override
    public boolean moveIsValid(int coordinate)
    {
        int x = (int) Math.floor(coordinate / 3);
        int y = (coordinate % 3);
        return board[x][y]==0;
    }

    public void switchTurns(){
        printBoard();
        if(playerTurn.getUsername().equalsIgnoreCase(player1.getUsername()))
            playerTurn=player2;
        else
            playerTurn=player1;

    }

    @Override
    public boolean hasEnded() {
        return !running;
    }

    private boolean gameLogic(int input, int row, int col)
    {
        return ((board[row][0] == input &&
                board[row][1] == input &&
                board[row][2] == input) ||

                (board[0][col] == input &&
                board[1][col] == input &&
                board[2][col] == input) ||

                (row == col && board[0][0] == input &&
                board[1][1] == input &&
                board[2][2] == input) ||

                (row + col == 2 && board[0][2] == input &&
                board[1][1] == input &&
                board[2][0] == input));
    }

    @Override
    public void start() {

    }

    @Override
    public Player getCurrentPlayer() {
        return playerTurn;
    }

    @Override
    public void setPlayer1(Player player1) {

    }

    @Override
    public void setPlayer2(Player player2) {

    }

    @Override
    public String getName() {
        return name;
    }

    public void update(String message) {
        //Handle server observations
        System.err.println("[TicTacToe] "+message);
    }


    private Integer[][] initBoard(Integer[][] board)
    {
        // setup the board
        for(int x = 0; x < 3; x++){
            for(int y =0; y < 3; y++){
                board[x][y] = 0;
            }
        }

        return board;
    }

    private void printBoard()
    {
        for(Integer[] arr : board){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Override
    public int getNextMove() {
        return playerTurn.requestMove();
    }

    @Override
    public boolean validMovesLeft() {
        return false;
    }

    @Override
    public Player getPlayer1() {
        return player1;
    }

    @Override
    public Player getPlayer2() {
        return player2;
    }

    @Override
    public String getCurrentStatus() {
        return null;
    }

    @Override
    public Integer[][] getBoard() {
        return board;
    }
}
