package States;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class StartState implements GameManagerState {
    GameManager gameManager;

    StartState(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public void connect(String address, int port) {
        System.out.println("Connecting to server..");
        if(gameManager.server.connect(address, port)){
            System.out.println("Connection successful");
            gameManager.setState(gameManager.getHasConnectedState());
        } else {
            System.out.println("Connection failed");
        }
    }

    @Override
    public void login(String username) {
        System.err.println("Invalid event");
    }

    @Override
    public void disconnect() {
        System.err.println("Invalid event");
    }

    @Override
    public void matchStart() {
        System.err.println("Invalid event");
    }

    @Override
    public void subscribeToGame() {
        System.err.println("Invalid event");
    }

}
