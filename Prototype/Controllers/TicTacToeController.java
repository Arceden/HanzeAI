package Controllers;

import GameModes.Game;
import Observer.ObservationSubject;
import Observer.Observer;
import Players.AIPlayer;
import Players.Player;
import Players.ViewPlayer;
import States.GameManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Map;

public class TicTacToeController implements Observer {

    GameManager gameManager;
    Game game;
    String player1;
    String player2;

    private Button[][] cells = new Button[8][8];
    boolean running = false;
    Thread gameThread;
    int networkCell;
    int clientCell;

    @FXML GridPane gamePane;
    @FXML Label lPlayer1;
    @FXML Label lPlayer2;

    private boolean isFirstPlayer;

    public TicTacToeController()
    {
        System.out.println("TicTacToeController");
        //this.game = gameManager.getGame();
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void goGame(){

        game = gameManager.getGame();
        initViewCells();
        running=true;

        Platform.runLater(()->{
            if(game.getCurrentPlayer().getUsername().equalsIgnoreCase(gameManager.getPlayer().getUsername()))
                lPlayer1.getStyleClass().add("underline");
            else
                lPlayer2.getStyleClass().add("underline");
        });
        game.start();

    }

    @FXML
    public void methodClickHandler()
    {

    }

    @FXML
    /**
     * @param event
     */
    public void buttonClickHandler(ActionEvent event)
    {
//
//        Button clickedButton = (Button) event.getTarget();
//        String buttonLabel = clickedButton.getText();
//
//        if("".equals(buttonLabel) && isFirstPlayer)
//        {
//            player1 = gameManager.getUsername();
//            //game.setPlayer1(new Play);
//
//            clickedButton.setText("X");
//            isFirstPlayer = false;
//        }
//        else if("".equals(buttonLabel))
//        {
//            player2 = gameManager.getUsername();
//            clickedButton.setText("O");
//            isFirstPlayer = true;
//        }
//
//        if(gameLogic())
//        {
//            if(isFirstPlayer)
//            {
//                System.out.println("The winner is " + player1);
//            }
//            else
//            {
//                System.out.println("The winner is " + player2);
//            }
//            System.out.println("game over");
//        }
    }

//    private void highlightWinningCombo(Button first, Button second, Button third)
//    {
//        first.getStyleClass().add("winning-button");
//        second.getStyleClass().add("winning-button");
//        third.getStyleClass().add("winning-button");
//    }


    public void moveHandler(){

        gameThread = new Thread(()->{

            int move;

            //Make sure the client is the current player
            if(!game.getCurrentPlayer().getUsername().equalsIgnoreCase(gameManager.getPlayer().getUsername()))
                game.switchTurns();

            Platform.runLater(()->{
                lPlayer2.getStyleClass().remove("underline");
                lPlayer1.getStyleClass().add("underline");
            });

            //What kind of player are you?
            if(game.getCurrentPlayer() instanceof ViewPlayer){
                clientCell=-1;
                System.out.println("Waiting for input..");
                while (clientCell<0&&running){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex){
                        //ignore
                    }
                }
                System.out.println("Got input");
                move=clientCell;
            } else {
                move = game.getNextMove();
            }

            if (game.moveIsValid(move)){

                game.move(move);
                gameManager.server.move(move);
                game.switchTurns();

            } else {
                clientMove();
            }


            Platform.runLater(()->{
                lPlayer1.getStyleClass().remove("underline");
                lPlayer2.getStyleClass().add("underline");
            });

            updateBoard();
            Thread.currentThread().interrupt();

        });

        gameThread.start();

    }


    public void clientMove(){
        moveHandler();
    }

    private void networkBoiMove(int move){

        game.move(move);
        updateBoard();

    }

    private EventHandler<ActionEvent> createCellHandler(int i) {
        return event -> tileHandler(i);
    }

    private void tileHandler(int i) {
        clientCell=i;
    }

    private void initViewCells() {

        Platform.runLater(()-> {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    int i = (x % 3) + 1;
                    i += y * 3;
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
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    Button cell = cells[y][x];

                    switch (board[y][x]){
                        case 0:
                            break;
                        case 1:
                            cell.setText("X");
                            break;
                        case 2:
                            cell.setText("O");
                    }

                }
            }
        });
    }


    @Override
    public void update(String message) {

        if(!running)
            return;

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
                            System.out.println(message);
                            break;
                        case "YOURTURN":
                            //Your turn!
                            System.out.println(message);
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

}
