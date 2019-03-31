import Algorithms.*;
import GameModes.*;
import Players.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane mainPane = new BorderPane();
        TextArea taLog = new TextArea();
        Label lStatus = new Label();

        mainPane.setCenter(taLog);
        mainPane.setBottom(lStatus);


        //Create a scene and place it in the stage
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Client");
        primaryStage.show();


        /**
         * Test the players and the game
         */

        Player arnold = new InputPlayer("Arnolditto");
        Player system = new AIPlayer("System", new Minimax());
        Game game = new TicTacToe(arnold, system);

        new Thread(()->{
            game.start();

            while (!game.hasEnded()){
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
