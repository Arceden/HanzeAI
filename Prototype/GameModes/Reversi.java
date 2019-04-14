package GameModes;

import Algorithms.AI;
import Players.AIPlayer;
import Players.AbstractPlayer;
import Players.Player;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Algorithms.*;

public class Reversi extends AbstractGame {

    private boolean player1Pass;
    private boolean player2Pass;

    /* Store Reversi board information */
    private static int gridSize = 8;
    private Integer[] dirx = {-1, 0, 1, -1, 1, -1, 0, 1};
    private Integer[] diry = {-1, -1, -1, 0, 0, 1, 1, 1};

    /* Store the AI Algorithm */
    private AI minimax = new Minimax(this);

    /** Store the initialized players and initialize the game board. */
    public Reversi(Player player1, Player player2){
        super(player1, player2);

        //Set the game name
        this.name = "Reversi";

        //Initialize the board
        this.board = new Integer[gridSize][gridSize];
        this.board = InitBoard(board);
    }

    /** Generate a new game board with the default starting positions. */
    private Integer[][] InitBoard(Integer[][] board) {
        for(int x = 0; x < gridSize; x++){
            for(int y =0; y < gridSize; y++){
                board[x][y] = 0;
            }
        }
        if(gridSize % 2 == 0){
            int z = (gridSize - 2) / 2;
            board[z][z] = 2;
            board[gridSize - 1 - z][z] = 1;
            board[z][gridSize - 1 - z] = 1;
            board[gridSize - 1 - z][gridSize - 1 - z] = 2;
            return board;
        }else {
            return null;
        }
    }

    /** Execute the move on behalf of the current player. */
    public boolean move(int coordinate) {

        int x = (int) Math.floor(coordinate / gridSize);
        int y = (coordinate % gridSize);

        int playerNum=0;
        if(playerTurn==player1) playerNum=1;
        if(playerTurn==player2) playerNum=2;

        board[x][y] = playerNum;

        return true;
    }

    /** -- */
    public ArrayList BestMove(Integer[][] board, int player) {
        int maxPoints = 0;
        int mx = -1;
        int my = -1;
        int depth = 4;
        for(int y = 0; y < gridSize; y++) {
            for(int x = 0; x < gridSize; x++) {
                int coordinate = (x*gridSize)+y;
                if (moveIsValid(coordinate)) {
                    Integer[][] tempBoard = new Integer[gridSize][gridSize];
                    for (int f = 0; f < gridSize; f++) {
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

    /** Flip the stones. totctr = amount of stones flipped */
    private int flippableCount(int x, int y) {
        int playerNum=0;
        if(playerTurn==player1) playerNum=1;
        if(playerTurn==player2) playerNum=2;

        int totctr = 0;
        Integer[][] tempboard = new Integer[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++){
            tempboard[i] = board[i];
        }
        for (int d = 0; d < gridSize; d++) {
            int ctr = 0;
            for (int i = 0; i < gridSize; i++) {
                int dx = x + dirx[d] * (i + 1);
                int dy = y + diry[d] * (i + 1);
                if (dx < 0 || dx > gridSize - 1 || dy < 0 || dy > gridSize - 1) {
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

    /** Check if the specified move is valid */
    public boolean moveIsValid(int coordinate) {
        int x = (int) Math.floor(coordinate / gridSize);
        int y = (coordinate % gridSize);
        if (x < 0 || x > gridSize - 1 || y < 0 || y > gridSize - 1) {
            System.err.println("Out of bounds");
            return false;
        }
        if(board[x][y] != 0) {
            System.err.println("Space already occupied");
            return false;
        }

        //Check if stones are flippable
        int flipCount = flippableCount(x, y);
        if(flipCount<1){
            System.err.println("No flippable stones with this move.");
            return false;
        }

        return true;
    }

    /** Check wether or not the game has ended.
     *  Return true if it has ended. */
    @Override
    public boolean hasEnded() {
        if(player1Pass && player2Pass){
            return true;
        }
        return false;
    }

    public boolean validMovesLeft(){
        for(int x = 0; x < gridSize; x++){
            for(int y = 0; y < gridSize; y++){
                if(moveIsValid((x*gridSize)+y)){
                    player1Pass = false;
                    player2Pass = false;
                    return true;
                }
            }
        }
        System.out.println("No possible moves left for this player!");
        if (playerTurn == player1) {
            player1Pass = true;
        }
        else{
            player2Pass = true;
        }
        return false;
    }

    /** Evaluate the current board's status for the specified player number.
     *  Returns the amount of colored stones for player #. */
    public int evalBoard(int player) {
        int tot = 0;
        for(int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                if(board[y][x] == player) {
                    if (x == 0 || x == gridSize - 1 && y == 0 || y == gridSize - 1) {
                        tot += 4;
                    }
                    else if(x == 0 || x == gridSize - 1 || y == 0 || y == gridSize - 1) {
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