package GameModes;

import Observer.Observer;
import Players.Player;

import java.util.ArrayList;
import java.util.Random;

public class TicTacToe implements Game, Observer {
//
//    private Player player1;
//    private Player player2;
//    public Player playerTurn;
//    private String testValue;
//
//    public final String name = "TicTacToe";
//    private int[][] cells = new int[3][3];
//
//    public TicTacToe(Player player1, Player player2){
//        this.player1 = player1;
//        this.player2 = player2;
//    }
//
//    public TicTacToe(){}
//
//    @Override
//    public void start() {
//        Random random = new Random();
//        switch (random.nextInt(1))
//        {
//            case 0: playerTurn = player1; break;
//            case 1: playerTurn = player2; break;
//        }
//        System.out.println(this.playerTurn.getUsername());
//    }
//
//    public int getNextMove(){
//        return playerTurn.requestMove();
//    }
//
//    @Override
//    public boolean move(int coordinate) {
//        System.out.println("Moving "+playerTurn.getUsername()+" to "+coordinate+"!");
//        if(playerTurn==player1)
//            playerTurn=player2;
//        else
//            playerTurn=player1;
//        return false;
//    }
//
//    @Override
//    public ArrayList MakeMove(Integer[][] tempBoard, int x, int y, int player) {
//        return null;
//    }
//
//    @Override
//    public void switchTurns() {
//
//    }
//
//    @Override
//    public boolean moveIsValid(Integer[][] board, int x, int y, int coordinate) {
//        return false;
//    }
//
//    @Override
//    public boolean hasEnded() {
//        return false;
//    }
//
//    @Override
//    public String getName() {
//        return name;
//    }
//
//    @Override
//    public Player getCurrentPlayer() {
//        return playerTurn;
//    }
//
//    @Override
//    public void setPlayer1(Player player1) {
//        this.player1 = player1;
//    }
//
//    @Override
//    public void setPlayer2(Player player2) {
//        this.player2 = player2;
//    }
//
//    @Override
//    public String getCurrentStatus()
//    {
//        return "";
//    }
//
//    @Override
//    public Integer[][] getBoard() {
//        return new Integer[0][];
//    }
//
//
////    public boolean isFull()
////    {
////        for(int i = 0; i < 3; i++)
////        {
////            for(int j = 0; j < 3; j++)
////            {
////                if(cells[i][j].getToken() == " ");
////                {
////                    return false;
////                }
////                else
////                {
////                    return true;
////                }
////            }
////        }
////    }
//
//
//    public void setTest(String value)
//    {
//        this.testValue = value;
//    }
//
//    public String getTest() {
//        return this.testValue;
//    }
//
//    public void update(String message) {
//        //Handle server observations
//        System.err.println("[TicTacToe] "+message);
//    }


    @Override
    public int getNextMove() {
        return 0;
    }

    @Override
    public boolean move(int coordinate) {
        return false;
    }

    @Override
    public int MakeMove(int x, int y) {
        return 0;
    }

    @Override
    public void switchTurns() {

    }

    @Override
    public boolean validMovesLeft() {
        return false;
    }

    @Override
    public boolean moveIsValid(int coordinate) {
        return false;
    }


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

    @Override
    public Integer[][] getBoard() {
        return new Integer[0][];
    }

    @Override
    public void update(String message) {

    }
}
