package Algorithms;

public interface AI {
    /** Calculate the next move */
    public int calculateMove(Integer[][] board, int player, int depth, boolean maximizingPlayer);
}
