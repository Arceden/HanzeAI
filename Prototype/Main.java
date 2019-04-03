import Algorithms.*;
import Controllers.TicTacToeController;
import GameModes.*;
import Players.*;
import Views.TicTacToeView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        /**
         * Setting MVC components
         *
         * */

        TicTacToeView ticTacToeView = new TicTacToeView();
        TicTacToe ticTacToeModel = new TicTacToe();
        TicTacToeController ticTacToeController = new TicTacToeController(ticTacToeModel, ticTacToeView);
        ticTacToeController.setTestValue();


        BorderPane mainPane = new BorderPane();
        GridPane gamePane = new GridPane();
        TextArea taLog = new TextArea();
        Label lStatus = new Label();

        mainPane.setRight(taLog);
        mainPane.setBottom(lStatus);
        mainPane.setCenter(gamePane);

        //Create a scene and place it in the stage
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Client");
        primaryStage.show();


        /**
         * Test the players and the game
         */

        //Create buttons for game
        for(int i=1;i<4;i++)
            for(int x=1;x<4;x++)
                gamePane.add(new Button(""+(x+(i))), i, x);

        //Create game and players
        Player arnold = new InputPlayer("Arnolditto");
        Player system = new AIPlayer("System", new Minimax());
        Game game = new TicTacToe(arnold, system);

        Platform.runLater(() -> {
            taLog.appendText("New player added: "+arnold.getUsername()+"\n");
            taLog.appendText("New player added: "+system.getUsername()+"\n");
            taLog.appendText("Game selected: "+game.getName()+"\n");
        });

        new Thread(()->{
            game.start();

            while (!game.hasEnded()){
                Platform.runLater(()-> lStatus.setText(game.getCurrentPlayer()+"'s turn!"));
                int move = game.getNextMove();
                game.move(move);
            }
        }).start();




        //Handle the server connection
//        ServerConnection server = new ServerConnection("localhost", 7789);
//        server.connect();
//
//        System.out.println(server.isConnected());

    }
}
