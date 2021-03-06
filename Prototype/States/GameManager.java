package States;

import GameModes.Game;
import Network.ConnectionHandler;
import Network.ServerHandler;
import Players.AIPlayer;
import Players.InputPlayer;
import Players.Player;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

public class GameManager implements GameManagerState {

    //Define all states
    GameManagerState hasConnectedState;
    GameManagerState inLobbyState;
    GameManagerState inMatchState;
    GameManagerState startState;

    //Set the current state
    GameManagerState state;

    //Connection Management
    public ServerHandler server;
    String username = "undefined";
    Player player;
    Game game;

    //Constructor
    public GameManager(){
        hasConnectedState = new HasConnectedState(this);
        inLobbyState = new InLobbyState(this);
        inMatchState = new InMatchState(this);
        startState = new StartState(this);

        setState(getStartState());
    }

    public void setServer(ServerHandler server) {
        this.server = server;
    }

    public Player getPlayer() {
        return player;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getUsername() {
        return username;
    }

    public void setGame(Game game) { this.game = game; }

    public Game getGame() { return game; }

    /**
     * State functions
     */

    public boolean connect(String address, int port) {
        return state.connect(address, port);
    }

    public boolean login(String username) {
        return state.login(username);
    }

    void setState(GameManagerState state){
        this.state=state;
    }

    public void subscribeToGame() {
        state.subscribeToGame();
    }

    public void disconnect() {
        state.disconnect();
    }

    public void matchStart() {
        state.matchStart();
    }

    /** State Getters */
    public GameManagerState getHasConnectedState() {
        return hasConnectedState;
    }

    public GameManagerState getInLobbyState() {
        return inLobbyState;
    }

    public GameManagerState getInMatchState() {
        return inMatchState;
    }

    public GameManagerState getStartState() {
        return startState;
    }
}
