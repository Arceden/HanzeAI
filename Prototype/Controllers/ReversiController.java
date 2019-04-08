package Controllers;

import GameModes.Game;
import Players.ViewPlayer;
import States.GameManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class ReversiController {

    GameManager gameManager;
    Game game;

    @FXML
    GridPane gamePane;
    @FXML
    Label lPlayer1;
    @FXML
    Label lPlayer2;
    @FXML
    Label lPlayerTurn;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void refresh(){
        initViewCells();
        goGame();
    }

    public void goGame() {

        //Get the game from the gameManager
        game = gameManager.getGame();
        game.start();

        Platform.runLater(()->{
            lPlayer1.setText(game.getPlayer1().getUsername());
            lPlayer2.setText(game.getPlayer2().getUsername());
        });

        //Perform the game loop until the game has concluded
        while (!game.hasEnded()) {

            Platform.runLater(()->{
                lPlayerTurn.setText(game.getCurrentPlayer().getUsername());
            });

            //Receive the move from the player
            int move;
            if (game.getCurrentPlayer() instanceof ViewPlayer){
                //Wait for button input
                move = 1;
            } else {
                move = game.getNextMove();
            }
            game.move(move);

            //Switch the player turns
            game.switchTurns();
        }

    }

    private EventHandler<ActionEvent> createCellHandler(int i) {
        return event -> tileHandler(i);
    }

    private void tileHandler(int i) {
        System.out.println("User pressed tile " + i);
    }

    public void initViewCells() {

        Platform.runLater(()-> {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    int i = (x % 8) + 1;
                    i += y * 8;
                    i--;

                    Button cell = new Button();
                    cell.setOnAction(createCellHandler(i));
                    gamePane.add(cell, x, y);
                }
            }
        });
    }

}