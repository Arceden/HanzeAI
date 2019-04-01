package GameModes;

import Players.Player;

import java.util.Random;

public class TicTacToe implements Game {

    private Player player1;
    private Player player2;
    public Player playerTurn;

    public final String name = "TicTacToe";
    private final int[] cells = new int[9];

    public TicTacToe(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    public TicTacToe(){}

    @Override
    public void start() {
        Random random = new Random();
        switch (random.nextInt(1)){
            case 0: playerTurn = player1; break;
            case 1: playerTurn = player2; break;
        }
        System.out.println(this.playerTurn.getUsername());
    }

    public int getNextMove(){
        return playerTurn.requestMove();
    }

    @Override
    public boolean move(int coordinate) {
        System.out.println("Moving "+playerTurn.getUsername()+" to "+coordinate+"!");
        if(playerTurn==player1)
            playerTurn=player2;
        else
            playerTurn=player1;
        return false;
    }

    @Override
    public boolean moveIsValid(int coordinate) {
        return false;
    }

    @Override
    public boolean hasEnded() {
        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCurrentPlayer() {
        return playerTurn.getUsername();
    }

    @Override
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    @Override
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}