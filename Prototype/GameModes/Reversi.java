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
    public Player playerTurn;

    private static int n = 8;
    private Integer[] dirx = {-1, 0, 1, -1, 1, -1, 0, 1};
    private Integer[] diry = {-1, -1, -1, 0, 0, 1, 1, 1};

    public final String name = "Reversi";
    private Integer[][] board = new Integer[8][8];
    private AI minimax = new Minimax(this);

    public Reversi(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        System.out.print(player2.getClass().getSimpleName());
        this.board = InitBoard(board);
    }



    /** -- */
    public Integer[][] InitBoard(Integer[][] board) {
        for(int x = 0; x < 8; x++){
            for(int y =0; y < 8; y++){
                board[x][y] = 0;
            }
        }
        if(n % 2 == 0){
            System.out.println(n);
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
        System.out.println(playerTurn.getUsername());
        return playerTurn.requestMove();
    }

    @Override
    /** TODO: Execute move */
    public boolean move(int coordinate) {

        int x = (int) Math.floor(coordinate / 8);
        int y = (coordinate % 8);

        System.out.println("X:"+x+"\tY:"+y);

        if(moveIsValid(coordinate)) {
            if(playerTurn == player1) {
                board[x][y] = 1;
            }
            else{
                board[x][y] = 2;
            }
        }else{
            board[x][y] = 0;
        }

        printBoard();

        return true;
    }

    /** -- */
    public ArrayList BestMove(Integer[][] board, int player) {
//        Integer[][] tempBoard = board;
        int maxPoints = 0;
        int mx = -1;
        int my = -1;
        int depth = 4;
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                int coordinate = (x*8)+y;
                if (moveIsValid(coordinate)) {
                    Integer[][] tempBoard = new Integer[8][8];
                    for (int f = 0; f < 8; f++) {
                        tempBoard[f] = board[f];
                    }
                    ArrayList l = MakeMove(x, y);
                    tempBoard = (Integer[][]) l.get(0);
                    int totctr = (int) l.get(1);
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
    public ArrayList MakeMove(int x, int y) {
        int player=0;
        if(playerTurn==player1)
            player=1;
        else
            player=2;


        Integer[][] tempBoard = new Integer[8][8];
        for(int i = 0; i < 8; i++){
            tempBoard[i] = board[i];
        }


        int totctr = 0;
        tempBoard[x][y] = player;
        for (int d = 0; d < 8; d++) {
            int ctr = 0;
            for (int i = 0; i < 8; i++) {
                int dx = x + dirx[d] * (i + 1);
                int dy = y + diry[d] * (i + 1);
                if (dx < 0 || dx > n - 1 || dy < 0 || dy > n - 1) {
                    ctr = 0;
                    break;
                } else if (tempBoard[dx][dy] == player) {
                    break;
                } else if (tempBoard[dx][dy] == 0) {
                    ctr = 0;
                    break;
                } else {
                    ctr += 1;
                }
            }
            for (int i = 0; i < ctr; i++) {
                int dx = x + dirx[d] * (i + 1);
                int dy = y + diry[d] * (i + 1);
                tempBoard[dx][dy] = player;
            }
            totctr += ctr;
        }
        ArrayList l = new ArrayList();
       // System.out.println(tempBoard.getClass());
        l.add(tempBoard);
        //System.out.print(totctr);
        l.add(totctr);
        return l;
    }

    /** -- */
    public boolean moveIsValid(int coordinate) {
        int x = (int) Math.floor(coordinate / 8);
        int y = (coordinate % 8);
        if (x < 0 || x > n - 1 || y < 0 || y > n - 1) {
            System.out.println("Eén");
            return false;
        }
        if(board[x][y] != 0) {
            System.out.println("Twee");
            return false;
        }
        //(Integer[][] boardTemp, int totctr)
        Integer[][] tempBoard = new Integer[8][8];
        for (int f = 0; f < n; f++){
            tempBoard[f] = board[f];
        }
        ArrayList l = MakeMove(x, y);
        //Integer[][] boardTemp = (Integer[][]) l.get(0);
        int totctr = (int) l.get(1);
        if(totctr == 0) {
            System.out.println("Drie");
            return false;
        }
        return true;
    }

    public void printBoard(){
        for(Integer[] arr : board){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Override
    /** TODO: Check if game field is full */
    public boolean hasEnded() {
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

}