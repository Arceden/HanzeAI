package Controllers;

import GameModes.Game;
import States.GameManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Collection;
import java.util.Collections;

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

    private EventHandler<ActionEvent> createCellHandler(int i) {
        return event -> tileHandler(i);
    }
    private void tileHandler (int i){
        System.out.println("User pressed tile "+i);
    }

    public void initViewCells(){

        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                int i = (x%8)+1;
                i+=y*8;
                i--;

                Button cell = new Button();
                cell.setOnAction(createCellHandler(i));

                gamePane.add(cell, x, y);
            }
        }

    }

}
