package Controllers;

import States.GameManager;
import Views.ViewSubject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController extends ViewSubject {

    GameManager gameManager;

    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfPort;
    @FXML
    private TextField tfUsername;
    @FXML
    private Button bLogin;

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
            System.out.println("Logged in as "+username);
            notifyObservers("LOBBY");
            gameManager.setUsername(username);
        } else {
            System.out.println("Could not login.");
        }
    }

}
