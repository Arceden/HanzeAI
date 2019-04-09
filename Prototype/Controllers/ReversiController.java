package Controllers;

import GameModes.Game;
import Observer.Observer;
import Players.AIPlayer;
import Players.InputPlayer;
import Players.NetworkPlayer;
import Players.ViewPlayer;
import States.GameManager;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Map;


public class ReversiController implements Observer {

    private GameManager gameManager;
    private Game game;
    private int pressedCell;
    private int networkCell;
    private Thread gameThread=null;
    private Button[][] cells = new Button[8][8];

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

        gameThread = new Thread(()->{

            //Get the game from the gameManager
            game = gameManager.getGame();
            game.start();

            Platform.runLater(()->{
                lPlayer1.setText(game.getPlayer1().getUsername());
                lPlayer2.setText(game.getPlayer2().getUsername());
            });

            updateBoard();

            //Perform the game loop until the game has concluded
            while (!game.hasEnded()) {

                //Set the cell watcher back to 0
                pressedCell = -1;
                networkCell = -1;

                //Update the current player's name
                Platform.runLater(()->{
                    lPlayerTurn.setText(game.getCurrentPlayer().getUsername());
                });

                //Receive the move from the player
                int move;
                if (game.getCurrentPlayer() instanceof ViewPlayer){
                    //Wait for button input
                    while (pressedCell<0){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex){
                            //ignore
                        }
                    }
                    move=pressedCell;
                } else if (game.getCurrentPlayer() instanceof NetworkPlayer){
                    //Get move from the network
                    while (networkCell<0){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex){
                            //ignore
                        }
                    }
                    move=networkCell;

                } else {
                    move = game.getNextMove();
                }


                //Check if its you
                if(game.getCurrentPlayer().getUsername().equalsIgnoreCase(gameManager.getPlayer().getUsername())){
                    //Check move
                    if(game.moveIsValid(move)) {
                        game.move(move);
                        gameManager.server.move(move);

                        //Switch the player turns
                        game.switchTurns();
                    }
                } else {
                    game.move(move);
                    game.switchTurns();
                }


                //Update the actual view with the new data
                updateBoard();
            }

            gameThread.interrupt();
        });

        gameThread.start();

    }

    private EventHandler<ActionEvent> createCellHandler(int i) {
        return event -> tileHandler(i);
    }

    private void tileHandler(int i) {
        pressedCell=i;
    }

    private void initViewCells() {

        Platform.runLater(()-> {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    int i = (x % 8) + 1;
                    i += y * 8;
                    i--;

                    Button cell = new Button();
                    cell.setOnAction(createCellHandler(i));
                    cells[y][x] = cell;
                    gamePane.add(cell, x, y);
                }
            }
        });
    }

    private void updateBoard(){
        Integer[][] board = game.getBoard();

        Platform.runLater(()->{
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    Button cell = cells[y][x];
                    Circle circle = new Circle(15);

                    switch (board[y][x]){
                        case 0:
                            circle = null;
                            break;
                        case 1:
                            circle.setFill(Color.WHITE);
                            break;
                        case 2:
                            circle.setFill(Color.BLACK);
                    }

                    cell.setGraphic(circle);
                }
            }
        });
    }

    @Override
    public void update(String message) {

        String[] args = message.split(" ");
        if(args[0].equalsIgnoreCase("SVR")) {
            switch (args[1]) {
                case "GAME":
                    switch (args[2]) {
                        case "MOVE":
                            //Received a new move
                            Map<String, String> data = gameManager.server.parseData("SVR GAME MOVE ", message);
                            networkCell = Integer.parseInt(data.get("MOVE"));
                            System.out.println("Received move: "+networkCell);
                            break;
                        case "YOURTURN":
                            //Your turn!
                            break;
                        default:
                            System.out.println(message);
                            break;
                    }
                    break;
            }
        }

    }
}