import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

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


        //Handle the server connection
        ServerConnection server = new ServerConnection("localhost", 7789);
        server.connect();
        server.send("login arnold");


    }
}
