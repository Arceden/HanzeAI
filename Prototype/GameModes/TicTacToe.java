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

    public final String name = "TicTacToe";

    private static int n = 3;
    private Integer[][] board = new Integer[3][3];

    public TicTacToe(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        this.board = initBoard(board);
    }

    @Override
    public int MakeMove(int x, int y) {
        return 0;
    }

    @Override
    public boolean move(int coordinate) {
        System.out.println("Moving "+playerTurn.getUsername()+" to "+coordinate+"!");
        //printBoard();

        int x = (int) Math.floor(coordinate / 3);
        int y = (coordinate % 3);
        //moveIsValid(x, y);

        if(gameLogic(coordinate, x, y))
        {
            System.out.println("We have a winner");
        }
        else
        {
            System.out.println("x : " + x);
            System.out.println("y : " + y);
        }

        if(playerTurn == player1)
        {
            board[x][y] = 1;
            switchTurns();
        }
        else
        {
            board[x][y] = 2;
            switchTurns();
        }

        return true;
    }

    @Override
//    public boolean moveIsValid(int x, int y)
    public boolean moveIsValid(int coordinate)
    {
        int x=0;
        int y=0;
        if(board[x][y] == 0){
            // move niet geldig.
            return false;
        }

        return true;
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
        return false;
    }

    private boolean gameLogic(int input, int x, int y)
    {
        return (board[x][0] == input &&
                board[x][1] == input &&
                board[x][2] == input ||

                board[0][y] == input &&
                board[1][y] == input &&
                board[2][y] == input ||

                x == y && board[0][0] == input &&
                board[1][1] == input &&
                board[2][2] == input ||

                x + y == 2 && board[0][2] == input &&
                board[1][1] == input &&
                board[2][0] == input);
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
