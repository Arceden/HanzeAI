package States;

import Network.ConnectionHandler;
import Network.Observer;
import javafx.scene.Scene;

public class GameManager implements GameManagerState {

    //Define all states
    GameManagerState startState;
    GameManagerState hasConnectedState;
    GameManagerState inLobbyState;
    GameManagerState inMatchState;

    //Set the current state
    GameManagerState state;

    //Connection Management
    public ConnectionHandler server;

    //Constructor
    public GameManager(){
        startState = new StartState(this);
        hasConnectedState = new HasConnectedState(this);
        inLobbyState = new InLobbyState(this);
        inMatchState = new InMatchState(this);

        setState(getStartState());

        server = new ConnectionHandler();
    }

    void setState(GameManagerState state){
        this.state=state;
    }

    public void connect(String address, int port) {
        state.connect(address, port);
    }

    public void login(String username) {
        state.login(username);
    }

    public void disconnect() {
        state.disconnect();
    }

    public void matchStart() {
        state.matchStart();
    }

    public void subscribeToGame() {
        state.subscribeToGame();
    }

    /** State Getters */
    public GameManagerState getStartState() {
        return startState;
    }

    public GameManagerState getHasConnectedState() {
        return hasConnectedState;
    }

    public GameManagerState getInLobbyState() {
        return inLobbyState;
    }

    public GameManagerState getInMatchState() {
        return inMatchState;
    }
}
