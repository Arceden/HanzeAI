package Algorithms;

import GameModes.Game;
import GameModes.Reversi;

import java.util.ArrayList;

import static java.lang.Integer.min;
import static java.lang.StrictMath.max;

public class Minimax implements AI {
    public static final String name = "Minimax";
    private Game game;

    private int minEvalBoard = -1;
    private int maxEvalBoard = 8 * 8 + 4 * 8 + 4 + 1;

    public Minimax(Game newGame) {
        this.game = newGame;

    }

    @Override
    public int calculateMove(Integer[][] board, int player, int depth, boolean maximizingPlayer) {
        return 0;
    }

    //
//    public int calculateMove(Integer[][] board, int player, int depth, boolean maximizingPlayer) {
//        if (depth == 0) {
//            return Reversi.EvalBoard(board, player);
//        }
//        if (maximizingPlayer) {
//            int bestValue = minEvalBoard;
//            for (int y = 0; y < 8; y++) {
//                for (int x = 0; x < 8; x++) {
//                    if (game.moveIsValid(board, x, y, player)) {
//                        Integer[][] tempBoard = new Integer[8][8];
//                        for (int f = 0; f < 8; f++) {
//                            tempBoard[f] = board[f];
//                        }
//                        ArrayList l = game.MakeMove(tempBoard, x, y, player);
//                        tempBoard = (Integer[][]) l.get(0);
//                        int totctr = (int) l.get(1);
//                        Integer v = calculateMove(tempBoard, player, depth - 1, false);
//                        bestValue = max(bestValue, v);
//                        return bestValue;
//                    } else {
//                        return 0;
//                    }
//                }
//            }
//        } else {
//            int bestValue = maxEvalBoard;
//            for (int y = 0; y < 8; y++) {
//                for (int x = 0; x < 8; x++) {
//                    if(game.moveIsValid(board, x, y, player)) {
//                        Integer[][] tempBoard = new Integer[8][8];
//                        for (int f = 0; f < 8; f++) {
//                            tempBoard[f] = board[f];
//                        }
//                        ArrayList l = game.MakeMove(tempBoard, x, y, player);
//                        tempBoard = (Integer[][]) l.get(0);
//                        int totctr = (int) l.get(1);
//                        Integer v = calculateMove(tempBoard, player, depth - 1, true);
//                        bestValue = min(bestValue, v);
//                        return bestValue;
//                    }else{
//                        return 0;
//                    }
//                }
//            }
//        }
//        return 0;
//    }



}