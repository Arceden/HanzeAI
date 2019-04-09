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
    private boolean running;

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

        running=true;

        game = gameManager.getGame();
        game.start();

        initViewCells();
        updateBoard();

        Platform.runLater(()->{
            lPlayer1.setText(game.getPlayer1().getUsername());
            lPlayer2.setText(game.getPlayer2().getUsername());
        });

    }

    public void moveHandler(){

        gameThread = new Thread(()->{

            //Make sure the client is the current player
            if(!game.getCurrentPlayer().getUsername().equalsIgnoreCase(gameManager.getPlayer().getUsername()))
                game.switchTurns();

            //Show the current player
            Platform.runLater(()->{
                lPlayerTurn.setText(game.getCurrentPlayer().getUsername());
            });

            int move;

            if(game.getCurrentPlayer() instanceof ViewPlayer){
                pressedCell=-1;
                while (pressedCell<0&&running){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex){
                        //ignore
                    }
                }
                move=pressedCell;
            } else {
                move = game.getNextMove();
            }

            if (game.moveIsValid(move)){

                game.move(move);
                gameManager.server.move(move);
                game.switchTurns();

                Platform.runLater(()->{
                    lPlayerTurn.setText(game.getCurrentPlayer().getUsername());
                });

            } else {
                clientMove();
            }

            updateBoard();
            Thread.currentThread().interrupt();

        });

        gameThread.start();

    }

    /**
     * The server gave the "YOURTURN" message.
     */
    private void clientMove(){

        moveHandler();

    }

    /**
     * Received a "MOVE" message from the network
     */
    private void networkBoiMove(int move){

        game.move(move);
        updateBoard();

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
                            circle.setFill(Color.BLACK);
                            break;
                        case 2:
                            circle.setFill(Color.WHITE);
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
                            networkBoiMove(networkCell);
                            break;
                        case "YOURTURN":
                            //Your turn!
                            clientMove();
                            break;
                        default:
                            System.out.println(message);
                            break;
                    }
                    break;
            }
        }
    }

    public void stopThreads(){
        gameThread.interrupt();
    }
}