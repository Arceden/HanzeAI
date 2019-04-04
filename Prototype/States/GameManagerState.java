package States;

import javafx.scene.Scene;

public interface GameManagerState {
    void connect(String address, int port);
    void login(String username);
    void disconnect();
    void matchStart();
    void subscribeToGame();
}
