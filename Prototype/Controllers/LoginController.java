package Controllers;

import States.GameManager;
import Observer.ObservationSubject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class LoginController extends ObservationSubject {

    GameManager gameManager;

    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPort;
    @FXML
    private TextField tfUsername;
    @FXML
    private Button bLogin;

    public LoginController(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Prototype/usernames.txt"));
            ArrayList<String> usernames = new ArrayList<>();

            String name;
            while ((name = reader.readLine())!=null){
                usernames.add(name);
            }

            Random random = new Random();
            Platform.runLater(()->{
                tfUsername.setText(usernames.get(random.nextInt(usernames.size())));
            });

            reader.close();

        } catch (FileNotFoundException ex){
            System.err.println("Could not load the usernames file.");
        } catch (IOException ex){
            //ignore
        }
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @FXML
    void connect(){
        String address = tfAddress.getText();
        int port = Integer.parseInt(tfPort.getText());

        if(gameManager.connect(address, port))
            bLogin.setDisable(false);
        else
            bLogin.setDisable(true);
    }

    @FXML
    void login(){
        String username = tfUsername.getText();

        if(gameManager.login(username)){
            gameManager.setUsername(username);
            System.out.println("Logged in as "+username);
            notifyObservers("LOBBY");
        } else {
            System.out.println("Could not login.");
        }
    }

}
