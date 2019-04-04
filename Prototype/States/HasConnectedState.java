package States;

import javafx.scene.Scene;

public class HasConnectedState implements GameManagerState {
    GameManager gameManager;
    Scene scene;

    public HasConnectedState(GameManager gameManager){ this.gameManager=gameManager; }

    @Override
    public boolean connect(String address, int port) {
        System.err.println("Invalid event");
        return false;
    }

    /* Login to the server with a specified name.
     * Possible errors:
     *  Tournament in progress
     *  Duplicate name exists*/
    public boolean login(String username) {
        System.out.println("Attempting to login as "+username);
        gameManager.server.send("login "+username);
        gameManager.setState(gameManager.getInLobbyState());
        return false;
    }

    @Override
    public void disconnect() {
        System.out.println("Disconnecting from server..");
        gameManager.server.send("disconnect");
        gameManager.setState(gameManager.getStartState());
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
