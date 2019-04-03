import Algorithms.*;
import Controllers.TicTacToeController;
import GameModes.*;
import Network.ConnectionHandler;
import Network.Logger;
import Network.Observer;
import Players.*;
import Views.TicTacToeView;
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

        /**
         * Setting MVC components
         *
         * */

        TicTacToeView ticTacToeView = new TicTacToeView();
        TicTacToe ticTacToeModel = new TicTacToe();
        TicTacToeController ticTacToeController = new TicTacToeController(ticTacToeModel, ticTacToeView);
        ticTacToeController.setTestValue();


        BorderPane mainPane = new BorderPane();
//        GridPane menuPane = new GridPane();
        taLog = new TextArea();
//        lStatus = new Label();
//
//        mainPane.setRight(taLog);
//        mainPane.setBottom(lStatus);
//        mainPane.setCenter(menuPane);

        GridPane menuPane = new GridPane();
        Button bLocal = new Button("Local Game");
        Button bOnline = new Button("Online");
        TextField tfUsername = new TextField("Arnold");

        menuPane.add(new Label("Username"), 0, 0);
        menuPane.add(tfUsername, 1, 0);
        menuPane.add(bLocal, 1, 1);
        menuPane.add(bOnline, 2, 1);

        mainPane.setCenter(menuPane);

        Game game=null;
        Logger logger = new Logger(taLog);
        ConnectionHandler server = new ConnectionHandler();

        game = new TicTacToe(new InputPlayer("Arnold"), new AIPlayer("Comput0r", new Minimax()));

        //Register observers to the server
        server.registerObserver(logger);
        server.registerObserver((Observer) game);

        bOnline.setOnAction(e->{
            //Start the server connection
            server.connect("localhost", 7789);

            server.send("login "+tfUsername.getText());
            server.send("get playerlist");
            server.send("get gamelist");

            mainPane.setCenter(taLog);
        });

        //Create a scene and place it in the stage
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Client");
        primaryStage.show();

    }

}