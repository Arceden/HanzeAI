import Controllers.LobbyController;
import Controllers.LoginController;
import Controllers.ReversiController;
import Controllers.TicTacToeController;
import GameModes.TicTacToe;
import Network.ServerHandler;
import Observer.Observer;
import Players.InputPlayer;
import Players.ViewPlayer;
import States.GameManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;

public class Main extends Application {

    LoginController loginController;
    LobbyController lobbyController;
    ReversiController reversiController;
    TicTacToeController ticTacToeController;

    Pane loginPane;
    Pane lobbyPane;
    Pane ticTacToePane;
    Pane reversiPane;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Create the root pane
        BorderPane root = new BorderPane();
        Label lStatus = new Label();
        root.setBottom(lStatus);

        //Create the main variables
        ServerHandler server = new ServerHandler();
        GameManager gameManager = new GameManager();
        ViewHandler viewHandler = new ViewHandler(root);
        ServerStatus serverStatus = new ServerStatus(lStatus);

        //Config
        server.registerObserver(serverStatus);
        gameManager.setServer(server);

        /* Login Screen */
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("Views/login.fxml"));
        loginPane = loginLoader.load();
        loginController = loginLoader.getController();

        /* Lobby Screen */
        FXMLLoader lobbyLoader = new FXMLLoader(getClass().getResource("Views/lobby.fxml"));
        lobbyPane = lobbyLoader.load();
        lobbyController = lobbyLoader.getController();

        /* Reversi Screen */
        FXMLLoader reversiLoader = new FXMLLoader(getClass().getResource("Views/reversi.fxml"));
        reversiPane = reversiLoader.load();
        reversiController = reversiLoader.getController();

        /* Tic Tac Toe Screen */
        FXMLLoader ticTacToeLoader = new FXMLLoader(getClass().getResource("Views/ticTacToeView.fxml"));
        ticTacToePane = ticTacToeLoader.load();
        ticTacToeController = ticTacToeLoader.getController();

        //Assign the models to the game managers
        loginController.setGameManager(gameManager);
        lobbyController.setGameManager(gameManager);
        reversiController.setGameManager(gameManager);
        ticTacToeController.setGameManager(gameManager);


        //Assign the viewHandler as the observer for the views
        loginController.registerObserver(viewHandler);
        lobbyController.registerObserver(viewHandler);

        //Assign the controller to the server handler
        server.registerObserver(lobbyController);
        server.registerObserver(reversiController);
        server.registerObserver(ticTacToeController);

//        server.connect("localhost", 7789);
//        server.login("Arnold");
//        gameManager.setGame(new Reversi(new ViewPlayer("Barry"), new ViewPlayer("Arnold")));
//        root.setCenter(reversiPane);
//        reversiController.refresh();

//        gameManager.setGame(new TicTacToe(new ViewPlayer("Rick"), new ViewPlayer("Arnold")));
        //Set the first pane

//        ticTacToeController.goGame();
        root.setCenter(loginPane);

        //Create a scene and place it in the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Client");
        primaryStage.show();


        //Handle window closing
        primaryStage.setOnCloseRequest(e->{
            lobbyController.stopThreads();
            reversiController.stopThreads();
            server.send("forfeit");
            server.disconnect();

            System.exit(1);
        });
    }

    private class ViewHandler implements Observer {
        BorderPane root=null;

        ViewHandler(BorderPane root){
            this.root=root;
        }

        @Override
        public void update(String message) {
            switch (message) {
                case "LOGIN":
                    Platform.runLater(()->root.setCenter(loginPane));
                    break;
                case "LOBBY":
                    Platform.runLater(()->root.setCenter(lobbyPane));
                    lobbyController.refresh();
                    break;
                case "Reversi":
                    Platform.runLater(()->root.setCenter(reversiPane));
                    reversiController.refresh();
                    break;
                case "Tic-tac-toe":
                    Platform.runLater(()->root.setCenter(ticTacToePane));
                    ticTacToeController.goGame();
                    break;
                default:
                    System.err.println("Unknown view: "+message);
                    break;
            }
        }
    }

    private class ServerStatus implements Observer {
        Label lStatus;
        ServerStatus(Label lStatus){
            this.lStatus=lStatus;
        }

        @Override
        public void update(String message) {
            String[] args = message.split(" ");
            if(args[0].equalsIgnoreCase("STATUS")) {
                Platform.runLater(()->lStatus.setText("Server Status: "+message.replace("STATUS ","")));
            }
        }
    }

}