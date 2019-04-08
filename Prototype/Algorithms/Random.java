package Algorithms;

public class Random implements AI {
    public static final String name = "Random";


    public int calculateMove() {
        return 5;
    }

    @Override
    public int calculateMove(Integer[][] board, int player, int depth, boolean maximizingPlayer) {
        return 0;
    }
}
