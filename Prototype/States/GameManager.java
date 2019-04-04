package States;

import Network.ConnectionHandler;

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

    //Player data
    String username;

    //Constructor
    public GameManager(){
        startState = new StartState(this);
        hasConnectedState = new HasConnectedState(this);
        inLobbyState = new InLobbyState(this);
        inMatchState = new InMatchState(this);

        setState(getStartState());
    }

    public void setServer(ConnectionHandler server) {
        this.server = server;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    /**
     * State functions
     */
    void setState(GameManagerState state){
        this.state=state;
    }

    public boolean connect(String address, int port) {
        return state.connect(address, port);
    }

    public boolean login(String username) {
        return state.login(username);
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
