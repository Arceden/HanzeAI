package Algorithms;

import GameModes.Game;

public class Random implements AI {
    public static final String name = "Random";


    public int calculateMove() {
        return 5;
    }


    @Override
    public int calculateMove(Integer[][] board, int player, int depth, boolean maximizingPlayer) {
        return 0;
    }

    @Override
    public int bestMove(Integer[][] board, int player, Game game) {
        return 0;
    }
}
