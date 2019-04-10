package GameModes;

import Players.Player;

public class Reversi implements Game {
    @Override
    public int getNextMove() {
        return 0;
    }

    @Override
    public boolean move(int coordinate) {
        return false;
    }

    @Override
    public boolean moveIsValid(int x, int y) {
        return false;
    }

    @Override
    public void start() {

    }

    @Override
    public boolean hasEnded() {
        return false;
    }

    @Override
    public void setPlayer1(Player player1) {

    }

    @Override
    public void setPlayer2(Player player2) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Player getCurrentPlayer() {
        return null;
    }

    @Override
    public String getCurrentStatus() {
        return null;
    }
}
