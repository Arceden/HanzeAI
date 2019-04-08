import Controllers.LobbyController;
import Controllers.LoginController;
import Controllers.MatchController;
import Controllers.ReversiController;
import GameModes.Reversi;
import Network.ConnectionHandler;
import Network.ServerHandler;
import Observer.Observer;
import Players.AIPlayer;
import Players.InputPlayer;
import States.GameManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    LoginController loginController;
    LobbyController lobbyController;
    MatchController matchController;
    ReversiController reversiController;

    Pane loginPane;
    Pane lobbyPane;
    Pane matchPane;
    Pane reversiPane;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Create the root pane
        BorderPane root = new BorderPane();
        Label lStatus = new Label();
        root.setBottom(lStatus);

        //Create the main variables
        ConnectionHandler server = new ConnectionHandler();
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

        /* Match Screen */
        FXMLLoader matchLoader = new FXMLLoader(getClass().getResource("Views/match.fxml"));
        matchPane = matchLoader.load();
        matchController = matchLoader.getController();

        FXMLLoader reversiLoader = new FXMLLoader(getClass().getResource("Views/reversi.fxml"));
        reversiPane = reversiLoader.load();
        reversiController = reversiLoader.getController();


        //Assign the models to the game managers
        loginController.setGameManager(gameManager);
        lobbyController.setGameManager(gameManager);
        matchController.setGameManager(gameManager);
        reversiController.setGameManager(gameManager);

        //Assign the viewHandler as the observer for the views
        loginController.registerObserver(viewHandler);
        lobbyController.registerObserver(viewHandler);
        matchController.registerObserver(viewHandler);

        //Assign the controller to the server handler
        server.registerObserver(lobbyController);


//        root.setCenter(loginPane);

        gameManager.setGame(new Reversi(new InputPlayer("Barry"), new InputPlayer("HEkn")));
//        root.setCenter(reversiPane);
//        reversiController.gTest();

        reversiController.goGame();

        //Create a scene and place it in the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Client");
        primaryStage.show();

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
                case "MATCH":
                    Platform.runLater(()->root.setCenter(matchPane));
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