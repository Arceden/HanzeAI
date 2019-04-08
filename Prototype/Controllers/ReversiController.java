package Controllers;

import GameModes.Game;
import States.GameManager;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class ReversiController {

    GameManager gameManager;
    Game game;

    @FXML
    GridPane gamePane;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void goGame(){

        //Get the game from the gameManager
        game = gameManager.getGame();
        game.start();

        //Perform the game loop until the game has concluded
        while (!game.hasEnded()){

            //Receive the move from the player
            int move = game.getNextMove();
            game.move(move);

            //Switch the player turns
            game.switchTurns();
        }

    }

    public void initViewCells(){
        for(int i=0;i<8*8;i++){
            Button cell = new Button();
            cell.setStyle("-fx-background-color: green; -fx-border-color: black");
            cell.setMinSize(100,100);
            cell.setPadding(new Insets(2));

            int x = i%8;
            int y = i/8;

            cell.setUserData(x);
            cell.setOnAction(e->{
                System.out.println(cell.getUserData());
            });

            gamePane.add(cell, x, y);
        }
    }

}
