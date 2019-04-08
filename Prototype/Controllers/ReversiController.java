package Controllers;

import GameModes.Game;
import States.GameManager;
import Views.ReversiView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import Algorithms.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ReversiController {

    GameManager gameManager;

    @FXML
    AnchorPane gamePane;

    private ReversiView view;
    private int player = 1;

    //private Integer[][] board = new Integer[8][8];
//    private ArrayList<Button> cells = new ArrayList<>();

//    Image X = new Image("file:C:/Users/joost/OneDrive/Afbeeldingen/wit.png");
//    Image O = new Image("file:C:/Users/joost/OneDrive/Afbeeldingen/zwart.png");

    Game game;

    public void goGame(){

        game = gameManager.getGame();
        game.start();
        while (!game.hasEnded()){
            int move = game.getNextMove();
            game.move(move);
//            game.MakeMove(move);
            game.switchTurns();
        }

    }


//    Integer[][] board;
//
//    public void gTest(){
//
//        game = gameManager.getGame();
////        board = game.getBoard();
//
//
//
//
//        int gridSize = 8;
//        BorderPane mainPane = new BorderPane();
//
//        GridPane gameGrid = new GridPane();
//        gameGrid.setAlignment(Pos.CENTER);
//
//        GridPane scorePane = new GridPane();
//        scorePane.setPadding(new Insets(20, 70, 0, 20));
//        Label labelZwart = new Label("Zwart: ");
//        Label scoreZwart = new Label("2");
//        Label labelWit = new Label("Wit");
//        Label scoreWit = new Label("2");
//        scorePane.add(labelZwart, 0,0);
//        scorePane.add(scoreZwart, 1,0);
//        scorePane.add(labelWit, 0,1);
//        scorePane.add(scoreWit, 1,1);
//
//        mainPane.setCenter(gameGrid);
//        mainPane.setLeft(scorePane);
//
//
//
//
//        for (int i = 0; i < gridSize*gridSize; i++){
//
//            Button cell = new Button();
//            cell.setStyle("-fx-background-color: green; -fx-border-color: black");
//            cell.setMinSize(100,100);
//            cell.setPadding(new Insets(2));
//            cell.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//
//                    if (player == 1) {
//                        //cell.setGraphic(new ImageView(X));
//                        if(cells.indexOf(cell) < 0){
//
//                            int x = 0;
//                            int y = cells.indexOf(cell);
//                            //System.out.println(ValidMove(board, x, y, player));
//                            if(game.moveIsValid(board, x, y, player)) {
//                                System.out.println("Valid");
//                                board[x][y] = 1;
//                                update_board(scoreZwart, scoreWit);
//                                cell.setMouseTransparent(true);
//                                cell.setFocusTraversable(false);
//                                game.switchTurns();
//                                player = 2;
//                            }else{
//                                System.out.println("Ongeldige set!");
//                                board[x][y] = 0;
//                            }
//                        }
//                        else{
//
//                            int x = (int) Math.floor(cells.indexOf(cell) / 8);
//                            int y = (cells.indexOf(cell) % 8);
//                            //System.out.println(ValidMove(board, x, y, player));
//
//                            if(game.moveIsValid(board, x, y, player)) {
//                                System.out.println("Valid");
//                                board[x][y] = 1;
//                                update_board(scoreZwart, scoreWit);
//                                cell.setMouseTransparent(true);
//                                cell.setFocusTraversable(false);
//                                game.switchTurns();
//                                //player = 2;
//                            }
//                            else{
//                                System.out.println("Ongeldige set!");
//                                board[x][y] = 0;
//                            }
//                        }
//
//                    }
//                    else{
//                        //cell.setGraphic(new ImageView(O));
//                        if(cells.indexOf(cell) < 0){
//                            int x = 0;
//                            int y = cells.indexOf(cell);
//                            //System.out.println(ValidMove(board, x, y, player));
//                            if(game.moveIsValid(board, x, y, player)) {
//                                System.out.println("Valid");
//                                board[x][y] = 2;
//                                update_board(scoreZwart, scoreWit);
//                                cell.setMouseTransparent(true);
//                                cell.setFocusTraversable(false);
//                                game.switchTurns();
//                                //player = 1;
//                            }else{
//                                System.out.println("Ongeldige set!");
//                                board[x][y] = 0;
//                            }
//                        }
//                        else{
//                            int x = (int) Math.floor(cells.indexOf(cell) / 8);
//                            int y = (cells.indexOf(cell) % 8);
//                            //System.out.println(ValidMove(board, x, y, player));
//                            if(game.moveIsValid(board, x, y, player)) {
//                                System.out.println("Valid");
//                                board[x][y] = 2;
//                                update_board(scoreZwart, scoreWit);
//                                cell.setMouseTransparent(true);
//                                cell.setFocusTraversable(false);
//                                game.switchTurns();
//                                //player = 1;
//                            }else{
//                                System.out.println("Ongeldige set!");
//                                board[x][y] = 0;
//
//                            }
//                        }
//
//                    }
//
//
//                }
//            });
//
//            cells.add(cell);
//            gameGrid.add(cell, i%gridSize, i/gridSize);
//        }
//
//        //board = game.(this.board);
//        update_board(scoreZwart, scoreWit);
//
//        gamePane.getChildren().add(mainPane);
//    }
//
//
//
//    public void update_board(Label zwart, Label wit){
//        int z = 0;
//        int w = 0;
//        for(int x = 0; x < 8; x++){
//            for(int y = 0; y < 8; y++){
//                if(board[x][y] == 1){
//                    cells.get( (x*8)+y).setGraphic(new ImageView(X));
//                    w++;
//                }
//                if(board[x][y] == 2){
//                    cells.get( (x*8)+y).setGraphic(new ImageView(O));
//                    z++;
//                }
//            }
//        }
//        zwart.setText(String.valueOf(z));
//        wit.setText(String.valueOf(w));
//    }
//
//

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
}
