import Algorithms.*;
import Controllers.MenuController;
import Controllers.TicTacToeController;
import GameModes.*;
import Network.ConnectionHandler;
import Network.Logger;
import Network.Observer;
import Players.*;
import Views.MenuView;
import Views.TicTacToeView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    TextArea taLog;
    Label lStatus;

    @Override
    public void start(Stage primaryStage) throws Exception {

        /**
         * Setting MVC components
         *
         * */

        BorderPane mainPane = new BorderPane();

        TicTacToeView ticTacToeView = new TicTacToeView();
        TicTacToe ticTacToeModel = new TicTacToe();
        TicTacToeController ticTacToeController = new TicTacToeController(ticTacToeModel, ticTacToeView);
        ticTacToeController.setTestValue();

        MenuView menuView = new MenuView(mainPane);
        MenuController menuController = new MenuController(menuView);
        menuController.showLogin();



        //Create a scene and place it in the stage
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Game Client");
        primaryStage.show();

    }

}