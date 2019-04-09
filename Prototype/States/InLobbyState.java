package States;

import javafx.scene.Scene;

public class InLobbyState implements GameManagerState {
    GameManager gameManager;
    Scene scene;

    InLobbyState(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public boolean connect(String address, int port) {
        System.err.println("Invalid event");
        return false;
    }

    @Override
    public boolean login(String username) {
        System.err.println("Invalid event");
        return false;
    }

    @Override
    public void disconnect() {
        gameManager.server.logout();
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
