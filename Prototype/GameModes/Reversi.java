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
//            System.out.println(n);
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
//        System.out.println("First to play: "+playerTurn.getUsername());
//        System.out.println("First to play: "+getCurrentPlayer().getUsername());
    }

    public int getNextMove(){
        return playerTurn.requestMove();
    }

    @Override
    /** TODO: Execute move */
    public boolean move(int coordinate) {

        int x = (int) Math.floor(coordinate / n);
        int y = (coordinate % n);

        System.out.println("X:"+x+"\tY:"+y);

        if(moveIsValid(coordinate)) {
            if(playerTurn == player1) {
                board[x][y] = 1;
                switchTurns();
            }
            else{
                board[x][y] = 2;
                switchTurns();
            }
        }else{
            board[x][y] = 0;
        }

        printBoard();

        return true;
    }

    /** -- */
    public ArrayList BestMove(Integer[][] board, int player) {
        int maxPoints = 0;
        int mx = -1;
        int my = -1;
        int depth = 4;
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                int coordinate = (x*n)+y;
                if (moveIsValid(coordinate)) {
                    Integer[][] tempBoard = new Integer[n][n];
                    for (int f = 0; f < n; f++) {
                        tempBoard[f] = board[f];
                    }
                    int points = minimax.calculateMove(tempBoard, player, depth, true);
                    if(points > maxPoints) {
                        maxPoints = points;
                        mx = x;
                        my = y;
                    }
                }
            }
        }
        ArrayList<Integer> coords = new ArrayList<Integer>();
        coords.add(mx);
        coords.add(my);
        return coords;
    }

    /** NOTE: AI */
    public int MakeMove(int x, int y) {
        int player=0;
        if(playerTurn==player1)
            player=1;
        else
            player=2;

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
                } else if (board[dx][dy] == player) {
                    break;
                } else if (board[dx][dy] == 0) {
                    ctr = 0;
                    break;
                } else {
                    ctr += 1;
                }
            }
            for (int i = 0; i < ctr; i++) {
                int dx = x + dirx[d] * (i + 1);
                int dy = y + diry[d] * (i + 1);
                board[dx][dy] = player;
            }
            totctr += ctr;
        }

        return totctr;
    }

    /** -- */
    public boolean moveIsValid(int coordinate) {
        int x = (int) Math.floor(coordinate / n);
        int y = (coordinate % n);
        if (x < 0 || x > n - 1 || y < 0 || y > n - 1) {
            //System.out.println("Eén");
            return false;
        }
        if(board[x][y] != 0) {
            //System.out.println("Twee");
            return false;
        }

        int totctr = MakeMove(x, y);

        if(totctr == 0) {
            //System.out.println("Drie");
            return false;
        }
        return true;
    }




//    public boolean calculateValidMoves(Integer[][] board){
//        Integer[][] valid = new Integer[8][8];
//        for(int x = 0; x < n; x++){
//            for(int y = 0; y < n; y++){
//                valid[x][y] = 0;
//            }
//        }
//        for(int row = 0; row < n; row++){
//            for(int column = 0; column < n; column++){
//                if(valid[row][column] == 0){
//                    for(int dx : dirx){
//                        for(int dy : diry){
//                            boolean v = valid_move(dirx, diry, row, column, board);
//                            if(v == true){
//                                valid[row][column] = 1;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//
//
//        return true;
//    }







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
            switchTurns();
        }
        else{
            player2Pass = true;
            switchTurns();
        }
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
    public static int EvalBoard(Integer[][] board, int player) {
        int tot = 0;
        for(int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if(board[y][x] == player) {
                    if (x == 0 || x == n - 1 && y == 0 || y == n - 1) {
                        tot += 4;
                    }
                    else if(x == 0 || x == n - 1 || y == 0 || y == n - 1) {
                        tot += 2;
                    }
                    else {
                        tot += 1;
                    }
                }
            }
        }
        return tot;
    }

    @Override
    public Player getPlayer1() {
        return player1;
    }

    @Override
    public Player getPlayer2() {
        return player2;
    }
}