package Algorithms;

import GameModes.Game;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.min;
import static java.lang.StrictMath.max;

public class Minimax implements AI, Runnable {
    public static final String name = "Minimax";
    private Game game;

    private int minEvalBoard = -1;
    private int maxEvalBoard = 8 * 8 + 4 * 8 + 4 + 1;

    private boolean timeLeft = true;

    public Minimax(Game newGame) {

    }


    public int calculateMove(Integer[][] tempBoard, int player, int depth, boolean maximizingPlayer) {
        int bestValue;
        if (!timeLeft) {
            return evalBoard(tempBoard);
        }
        if (maximizingPlayer) {
            bestValue = minEvalBoard;
            if(timeLeft){
                for (int y = 0; y < 8; y++) {
                    for (int x = 0; x < 8; x++) {
                        if (game.calculateValidMoves()[x][y] != 0) {
                            //                        int ctr = game.flippableCount(x,y);
                            Integer v = calculateMove(tempBoard, player, depth - 1, false);
                            bestValue = max(bestValue, v);
                        }
                    }
                }
            }
        } else {
            bestValue = maxEvalBoard;
            if(timeLeft){
                for (int y = 0; y < 8; y++) {
                    for (int x = 0; x < 8; x++) {
                        if (game.calculateValidMoves()[x][y] != 0) {
                            //                        int ctr = game.flippableCount(x, y);
                            int v = calculateMove(tempBoard, player, depth - 1, true);
                            bestValue = min(bestValue, v);
                        }
                    }
                }
            }
        }
        //System.out.println("Best value: " + bestValue);
        return bestValue;
    }

    public int bestMove(Integer[][] board, int player, Game newGame) {
        this.game = newGame;
        int maxPoints = 0;
        int mx = -1;
        int my = -1;
        int depth = 2;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int coordinate = (x * 8) + y;
                if (game.calculateValidMoves()[x][y] != 0) {
                    Integer[][] tempBoard = new Integer[8][8];
                    for (int i = 0; i < 8; i++) {
                        tempBoard[i] = game.getBoard()[i];
                    }

                    int points = calculateMove(tempBoard, player, depth, true);
                    if (points > maxPoints) {
                        maxPoints = points;
                        mx = x;
                        my = y;
                    }
                }
            }
        }
        //System.out.println(mx + "  " + my);
        //game.move((mx*8)+my);
        int coord = (mx * 8) + my;
        return coord;
    }

    public int evalBoard(Integer[][] tempboard) {
        int player;
        if (game.getCurrentPlayer() == game.getPlayer1()) player = 1;
        else player = 2;

        int tot = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (tempboard[y][x] == player) {
                    if (x == 0 || x == 8 - 1 && y == 0 || y == 8 - 1) {
                        tot += 4;
                    } else if (x == 0 || x == 8 - 1 || y == 0 || y == 8 - 1) {
                        tot += 2;
                    } else {
                        tot += 1;
                    }
                }
            }
        }
        //System.out.println(tot);
        return tot;
    }

    @Override
    public void run() {
        this.timeLeft = true;
        try {
            Thread.sleep(3000);
            this.timeLeft = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

