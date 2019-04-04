import Controllers.LobbyController;
import Controllers.LoginController;
import Network.ConnectionHandler;
import Network.Observer;
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

public class Main extends Application {

    LoginController loginController;
    LobbyController lobbyController;

    Pane loginPane;
    Pane lobbyPane;

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

        //Assign the models to the game managers
        loginController.setGameManager(gameManager);
        lobbyController.setGameManager(gameManager);

        //Assign the viewHandler as the observer for the views
        loginController.registerObserver(viewHandler);
        lobbyController.registerObserver(viewHandler);


        root.setCenter(loginPane);

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
                    root.setCenter(loginPane);
                    break;
                case "LOBBY":
                    lobbyController.refresh();
                    root.setCenter(lobbyPane);
                    break;
            }
        }
    }

    private class Tester implements Observer {
        BorderPane mainPane;
        VBox challengeList;
        GameManager gameManager;
        Tester(BorderPane mainPane, GameManager gameManager){
            this.gameManager=gameManager;
            this.mainPane=mainPane;
            this.challengeList=new VBox();
            Platform.runLater(()->mainPane.setRight(challengeList));
        }

        @Override
        public void update(String message) {
            String[] args = message.split(" ");
            System.out.println("[LobbyState]\t"+message);
            switch (args[1]){
                case "GAME":
                    switch (args[2]){
                        case "MATCH":
                            System.out.println("[LobbyState]\tStarting match!");
                            break;
                        case "CHALLENGE":
                            System.out.println("[LobbyState]\tReceived challenge!");
                            Button bChallenger = new Button(args[4]);
                            bChallenger.setOnAction(e->{
                                gameManager.server.send("challenge accept "+args[6].replace("\"", "").replace(",",""));
                            });

                            Platform.runLater(()->{
                                challengeList.getChildren().add(bChallenger);
                            });
                            break;
                    }
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