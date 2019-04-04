package States;

import Network.Observer;
import javafx.scene.Scene;

public class InLobbyState implements GameManagerState {
    GameManager gameManager;
    Scene scene;

    InLobbyState(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public void connect(String address, int port) {
        System.err.println("Invalid event");
    }

    @Override
    public void login(String username) {
        System.err.println("Invalid event");
    }

    @Override
    public void disconnect() {
        System.out.println("Attempting to logout..");
        gameManager.server.send("logout");
        gameManager.setState(gameManager.getStartState());
    }

    @Override
    public void matchStart() {
        System.out.println("Starting match..");
    }

    @Override
    public void subscribeToGame() {
        System.out.println("Subscribing to whatever game");
    }

}
