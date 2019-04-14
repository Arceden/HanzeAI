package GameModes;

import Observer.Observer;
import Players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class TicTacToe extends AbstractGame {

    private boolean running;

    private int gridSize = 3;

    public TicTacToe(Player player1, Player player2){
        super(player1, player2);
        this.name = "Tic-tac-toe";
        playerTurn=player1;
        running=true;

        this.board = new Integer[gridSize][gridSize];
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

    private Integer[][] initBoard(Integer[][] board) {
        // setup the board
        for(int x = 0; x < gridSize; x++){
            for(int y =0; y < gridSize; y++){
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
    public boolean validMovesLeft() {
        return false;
    }

}
