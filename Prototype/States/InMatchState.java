package States;

import javafx.scene.Scene;

public class InMatchState implements GameManagerState {
    GameManager gameManager;

    InMatchState(GameManager gameManager){
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
