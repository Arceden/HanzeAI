import Algorithms.*;
import GameModes.*;
import Players.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    TextArea taLog;
    Label lStatus;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane mainPane = new BorderPane();
        GridPane menuPane = new GridPane();
        taLog = new TextArea();
        lStatus = new Label();

        mainPane.setRight(taLog);
        mainPane.setBottom(lStatus);
        mainPane.setCenter(menuPane);

        //Create a scene and place it in the stage
//        Scene scene = new Scene(mainPane);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Game Client");
//        primaryStage.show();


        //Handle the server connection
        ServerConnection server = new ServerConnection("localhost", 7789);
        server.connect();
        server.listen();

        server.login("Arnold");
        server.getGameList();
        server.getPlayerList();
        server.send("subscribe Tic-tac-toe");

    }
}