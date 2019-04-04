import Controllers.LoginController;
import Models.PlayerData;
import Network.Observer;
import States.GameManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Create the root pane
        BorderPane root = new BorderPane();

        //Create the main variables
        GameManager gameManager = new GameManager();

        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("Controllers/login.fxml"));
        root.setCenter(loginLoader.load());
        LoginController loginController = loginLoader.getController();


        //Create a scene and place it in the stage
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Client");
        primaryStage.show();

        new Thread(()->{

            PlayerData playerData = new PlayerData();
            loginController.setPlayerData(playerData);

        }).start();

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

}