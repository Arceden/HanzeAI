package GameModes;

import Algorithms.AI;
import Players.AIPlayer;
import Players.Player;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Algorithms.*;

public class Reversi implements Game {

    private Player player1;
    private Player player2;
    public Player playerTurn = player2;

    private boolean player1Pass;
    private boolean player2Pass;

    private static int n = 8;
    private Integer[] dirx = {-1, 0, 1, -1, 1, -1, 0, 1};
    private Integer[] diry = {-1, -1, -1, 0, 0, 1, 1, 1};

    public final String name = "Reversi";
    private Integer[][] board = new Integer[n][n];
    private AI minimax = new Minimax(this);

    public Reversi(Player player1, Player player2){
        this();
        this.player1 = player1;
        this.player2 = player2;
    }

    public Reversi(){
        this.board = InitBoard(board);
    }



    /** -- */
    public Integer[][] InitBoard(Integer[][] board) {
        for(int x = 0; x < n; x++){
            for(int y =0; y < n; y++){
                board[x][y] = 0;
            }
        }
        if(n % 2 == 0){
            int z = (n - 2) / 2;
            board[z][z] = 2;
            board[n - 1 - z][z] = 1;
            board[z][n - 1 - z] = 1;
            board[n - 1 - z][n - 1 - z] = 2;
            return board;
        }else {
            return null;
        }
    }

    /** -- */
    public void switchTurns(){

        if(playerTurn.getUsername().equalsIgnoreCase(player1.getUsername()))
            playerTurn=player2;
        else
            playerTurn=player1;

    }

    /** Get current state of the board */
    public Integer[][] getBoard(){
        return board;
    }

    @Override
    /** Player 1 is first to play */
    public void start() {
        playerTurn=player1;
    }

    public int getNextMove(){
        return playerTurn.requestMove(this);
    }

    @Override
    public boolean move(int coordinate) {

        int x = (int) Math.floor(coordinate / n);
        int y = (coordinate % n);

        int playerNum=0;
        if(playerTurn==player1) playerNum=1;
        if(playerTurn==player2) playerNum=2;

        //System.out.println("Coordinaten: " + x + "    "  + y);
        board[x][y] = playerNum;

        flippableCount(x,y);

        return true;
    }





    public Integer[][] calculateValidMoves(){
        Integer[][] valid = new Integer[8][8];
        for(int x = 0; x < n; x++){
            for(int y = 0; y < n; y++){
                valid[x][y] = 0;
            }
        }
        for(int row = 0; row < n; row++){
            for(int column = 0; column < n; column++){
                if(valid[row][column] == 0){
                    if(board[row][column] == 0) {
                        for(int i = 0; i < 8; i++){
                                boolean v = valid_move(dirx[i], diry[i], row, column);
                                if (v == true) {
                                    valid[row][column] = 1;
                                }
                            }
                    }
                }
            }
        }
//        System.out.println("Valid moves:");
//        for(Integer[] arr : valid){
//            System.out.println(Arrays.toString(arr));
//        }
        return valid;
    }

    public boolean valid_move(int dirx, int diry, int x, int y) {
        int playerNum = 0;
        if (playerTurn == player1) playerNum = 1;
        if (playerTurn == player2) playerNum = 2;
        int totctr = 0;
        Integer[][] tempboard = new Integer[n][n];
//        for (int i = 0; i < n; i++) {
//            tempboard[i] = board[i];
//        }
        for (int d = 0; d < n; d++) {
            int ctr = 0;
            for (int i = 0; i < n; i++) {
                int dx = x + dirx * (i + 1);
                int dy = y + diry * (i + 1);
                if (dx < 0 || dx > n - 1 || dy < 0 || dy > n - 1) {
                    ctr = 0;
                    break;
                } else if (board[dx][dy] == playerNum) {
                    break;
                } else if (board[dx][dy] == 0) {
                    ctr = 0;
                    break;
                } else {
                    ctr += 1;
                }
            }
            totctr += ctr;
        }
        if(totctr < 1) return false;
        else return true;
    }







    /** Flip the stones. totctr = amount of stones flipped */
    public int flippableCount(int x, int y) {
        int playerNum=0;
        if(playerTurn==player1) playerNum=1;
        if(playerTurn==player2) playerNum=2;

        int totctr = 0;
        Integer[][] tempboard = new Integer[n][n];
        for(int i = 0; i < n; i++){
            tempboard[i] = board[i];
        }
        for (int d = 0; d < n; d++) {
            int ctr = 0;
            for (int i = 0; i < n; i++) {
                int dx = x + dirx[d] * (i + 1);
                int dy = y + diry[d] * (i + 1);
                if (dx < 0 || dx > n - 1 || dy < 0 || dy > n - 1) {
                    ctr = 0;
                    break;
                } else if (tempboard[dx][dy] == playerNum) {
                    break;
                } else if (tempboard[dx][dy] == 0) {
                    ctr = 0;
                    break;
                } else {
                    ctr += 1;
                }
            }
            for (int i = 0; i < ctr; i++) {
                int dx = x + dirx[d] * (i + 1);
                int dy = y + diry[d] * (i + 1);
                tempboard[dx][dy] = playerNum;
            }
            totctr += ctr;
        }

        return totctr;
    }

    /** -- */
//    public boolean moveIsValid(int coordinate) {
//        int x = (int) Math.floor(coordinate / n);
//        int y = (coordinate % n);
//        if (x < 0 || x > n - 1 || y < 0 || y > n - 1) {
//            System.err.println("Out of bounds");
//            return false;
//        }
//        if(board[x][y] != 0) {
//            System.err.println("Space already occupied");
//            return false;
//        }
//
//        //Check if stones are flippable
//        int flipCount = flippableCount(x, y);
//        if(flipCount<1){
//            System.err.println("No flippable stones with this move.");
//            return false;
//        }
//
//        return true;
//    }

    /** Print the CLI board of the game
    public void printBoard(){
        for(Integer[] arr : board){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Override
    /** TODO: Check if game field is full */
    public boolean hasEnded() {
        if(player1Pass && player2Pass){
            return true;
        }
        return false;
    }

    public boolean validMovesLeft(){
        for(int x = 0; x < n; x++){
            for(int y = 0; y < n; y++){
                if(moveIsValid((x*n)+y)){
                    player1Pass = false;
                    player2Pass = false;
                    return true;
                }
            }
        }
        System.out.println("No possible moves left for this player!");
        if (playerTurn == player1) {
            player1Pass = true;
//            switchTurns();
        }
        else{
            player2Pass = true;
//            switchTurns();
        }
        return false;
    }

    @Override
    public boolean moveIsValid(int coordinate) {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Player getCurrentPlayer() {
        return playerTurn;
    }



    @Override
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    @Override
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    @Override
    public String getCurrentStatus()
    {
        return "";
    }

    /** -- */
//    public int evalBoard(int player) {
//
//        //if(playerTurn == player1)
//        int tot = 0;
//        for(int y = 0; y < n; y++) {
//            for (int x = 0; x < n; x++) {
//                if(board[y][x] == player) {
//                    if (x == 0 || x == n - 1 && y == 0 || y == n - 1) {
//                        tot += 4;
//                    }
//                    else if(x == 0 || x == n - 1 || y == 0 || y == n - 1) {
//                        tot += 2;
//                    }
//                    else {
//                        tot += 1;
//                    }
//                }
//            }
//        }
//        return tot;
//    }

    @Override
    public Player getPlayer1() {
        return player1;
    }

    @Override
    public Player getPlayer2() {
        return player2;
    }
}