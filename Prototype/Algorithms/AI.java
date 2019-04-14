package Algorithms;

import GameModes.Game;

import java.util.ArrayList;

public interface AI {
    /** Calculate the next move */
    public int calculateMove(Integer[][] board, int player, int depth, boolean maximizingPlayer);

    int bestMove(Integer[][] board, int player, Game game);
}
