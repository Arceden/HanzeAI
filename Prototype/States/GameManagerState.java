package States;

import javafx.scene.Scene;

public interface GameManagerState {
    boolean connect(String address, int port);
    boolean login(String username);
    void disconnect();
    void matchStart();
    void subscribeToGame();
}
