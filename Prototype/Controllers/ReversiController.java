package Controllers;

import GameModes.Game;
import GameModes.Reversi;
import Observer.Observer;
import Players.ViewPlayer;
import States.GameManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.util.Map;


public class ReversiController implements Observer {

    private GameManager gameManager;
    private Game game;
    private int pressedCell;
    private int networkCell;
    private Thread gameThread=null;
    private Button[][] cells = new Button[8][8];
    private boolean running;

    private Circle cBlack;
    private Circle cWhite;
    private Text tBlack;
    private Text tWhite;
    private Label lPlayer1;
    private Label lPlayer2;

    @FXML
    GridPane gamePane;
    @FXML
    GridPane gameInfo;

    public ReversiController(){
        initViewCells();
        makeInfoBoard();
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void refresh(){

        running=true;

        game = gameManager.getGame();
        game.start();

        makeInfoBoard();

        updateBoard();

        Platform.runLater(()->{
            lPlayer1.setText(game.getPlayer1().getUsername());
            lPlayer2.setText(game.getPlayer2().getUsername());
        });

    }

    private void moveHandler(){

        gameThread = new Thread(()->{

            //Make sure the client is the current player
            if(!game.getCurrentPlayer().getUsername().equalsIgnoreCase(gameManager.getUsername()))
                game.switchTurns();

            //Show the current player
            updateBoard();

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
                //game.switchTurns();
            }

            int x = (int) Math.floor(move / 8);
            int y = (move % 8);
            if (game.calculateValidMoves()[x][y] != 0){

                game.move(move);
                gameManager.server.move(move);
                game.switchTurns();

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

        //Make sure the client is NOT the current player
        if(game.getCurrentPlayer().getUsername().equalsIgnoreCase(gameManager.getUsername()))
            game.switchTurns();

        //game.moveIsValid(move);
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

        //Update infoboard
        Reversi r = (Reversi) game;
        Platform.runLater(()->{
            tBlack.setText(r.evalBoard(1)+"");
            tWhite.setText(r.evalBoard(2)+"");

            lPlayer1.setText(game.getPlayer1().getUsername());
            lPlayer2.setText(game.getPlayer2().getUsername());

            if(game.getCurrentPlayer().getUsername().equalsIgnoreCase(game.getPlayer1().getUsername())){
                //Player 1's turn
                cBlack.setStroke(Color.RED);
                cWhite.setStroke(Color.TRANSPARENT);
            } else {
                //Player 2's turn
                cBlack.setStroke(Color.TRANSPARENT);
                cWhite.setStroke(Color.RED);
            }
        });

        //Update gameboard
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

    void stopGame(){
        running=false;
    }

    private void makeInfoBoard(){

        //Clear the info board
        Platform.runLater(()->gameInfo.getChildren().clear());

        //Black
        cBlack = makeCircle(Color.BLACK);
        tBlack = new Text("0");
        tBlack.setFill(Color.WHITE);
        tBlack.setBoundsType(TextBoundsType.VISUAL);
        lPlayer1 = new Label("username");

        StackPane stack1 = new StackPane();
        stack1.getChildren().addAll(cBlack, tBlack);


        //White
        cWhite = makeCircle(Color.WHITE);
        tWhite = new Text("0");
        tWhite.setFill(Color.BLACK);
        tWhite.setBoundsType(TextBoundsType.VISUAL);
        lPlayer2 = new Label("username");

        StackPane stack2 = new StackPane();
        stack2.getChildren().addAll(cWhite, tWhite);


        Platform.runLater(()->{
            gameInfo.add(stack1, 0, 0);
            gameInfo.add(lPlayer1, 1, 0);
            gameInfo.add(stack2, 0, 1);
            gameInfo.add(lPlayer2, 1, 1);
        });

    }

    private Circle makeCircle(Color color){
        Circle circle = new Circle(20);
        circle.setFill(color);
        return circle;
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
                        case "WIN":
                            stopGame();
                            break;
                        case "LOSS":
                            stopGame();
                            break;
                        case "DRAW":
                            stopGame();
                            break;
                        case "MOVE":
                            //Received a new move
                            Map<String, String> data = gameManager.server.parseData("SVR GAME MOVE ", message);
                            String toMove = data.get("PLAYER");
                            if(gameManager.getUsername().equals(toMove))    //Ignore message if this is yourself
                                return;
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
        try {
            gameThread.interrupt();
        } catch (NullPointerException ex){
            //ignore
        }
    }
}