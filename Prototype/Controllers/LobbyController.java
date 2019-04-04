package Controllers;

import States.GameManager;
import Views.ViewSubject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class LobbyController extends ViewSubject {

    GameManager gameManager;

    @FXML
    private Label displayUsername;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void refresh(){
        Platform.runLater(()->{
            displayUsername.setText("Welcome, "+gameManager.getUsername()+"!");
        });
    }

    @FXML
    public void disconnect(){
        gameManager.disconnect();
        notifyObservers("LOGIN");
    }

}
