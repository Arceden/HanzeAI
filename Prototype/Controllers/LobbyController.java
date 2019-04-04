package Controllers;

import Network.Observer;
import Players.AIPlayer;
import Players.InputPlayer;
import States.GameManager;
import Views.ViewSubject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class LobbyController extends ViewSubject implements Observer {

    GameManager gameManager;
    ObservableList<String> playerList = FXCollections.observableArrayList();
    ObservableList<String> challengerList = FXCollections.observableArrayList();

    @FXML private Button asPlayer;
    @FXML private Button asAI;
    @FXML private Label displayUsername;
    @FXML private ListView<String> playerView;
    @FXML private ListView<String> challengerView;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void refresh(){
        //Set default play style
        gameManager.setPlayer(new AIPlayer(gameManager.getUsername()));

        //Retreive playerlist
        getPlayers();

        challengerView.setItems(challengerList);

        //Update the welcome text
        Platform.runLater(()->{
            displayUsername.setText("Welcome, "+gameManager.getUsername()+"!");
        });
    }

    public void getPlayers(){
        //Create a list for the players
        playerList.clear();

        //Request playerlist
        String message = gameManager.server.getPlayers();

        //Format the playernames
        message = message.replace("SVR PLAYERLIST ", "");
        String[] players = message.substring(1, message.length()-1).split(", ");
        for(int i=0;i<players.length;i++)
            players[i] = players[i].substring(1, players[i].length()-1);

        //Add the list to the listview
        playerList.addAll(players);
        playerView.setItems(playerList);
    }

    @FXML
    void asPlayer(){
        gameManager.setPlayer(new InputPlayer(gameManager.getUsername()));
        asPlayer.setDisable(true);
        asAI.setDisable(false);
        System.out.println("Playing as Player!");
    }

    @FXML
    void asAI(){
        gameManager.setPlayer(new AIPlayer(gameManager.getUsername()));
        asPlayer.setDisable(false);
        asAI.setDisable(true);
        System.out.println("Playing as AI!");
    }

    @FXML
    public void challengePlayer(){
        String selectedPlayer = playerView.getSelectionModel().getSelectedItem();
        if(selectedPlayer.equalsIgnoreCase(gameManager.getUsername())){
            System.out.println("You can't challenge yourself!");
            return;
        }

        System.out.println("Challenging "+selectedPlayer+" for a game of Tic-tac-toe!");
        gameManager.server.send("challenge \""+selectedPlayer+"\" \"Tic-tac-toe\"");
    }

    @FXML
    void acceptChallenge(){
        String selectedPlayer = challengerView.getSelectionModel().getSelectedItem();
        String challengeNumber = selectedPlayer.split("%")[1];
        gameManager.server.send("challenge accept "+challengeNumber);
    }

    @FXML
    public void disconnect(){
        gameManager.disconnect();
        notifyObservers("LOGIN");
    }

    /**
     * Start the match
     * This method initializes the Game variable in the GameManager.
     * @param message
     */
    private void startMatch(String message){
        String opponentName;
        System.out.println(message);

        //Change the gameManager state to InMatchState
        gameManager.matchStart();

        //Change view
        notifyObservers("MATCH");
    }

    private void challengerApproaches(String message){
        String args[] = message.split(" ");
        challengerList.add(args[4].substring(1, args[4].length()-2)+"%"+args[6].substring(1, args[6].length()-2));
    }

    /* TODO: Remove the challenger who left from the list */
    private void challengerCancellled(String message){

    }

    /**
     * Receive server messages
     * Tasks:
     *  Manage incoming challenges
     *  Manage canceled challenges
     *  Receive start match details
     */
    @Override
    public void update(String message) {
        //Receive challenges and such
        String[] args = message.split(" ");
        if(args[0].equalsIgnoreCase("SVR")) {
            switch (args[1]) {
                case "GAME":
                    switch (args[2]) {
                        case "MATCH":
                            //Starting a match!
                            startMatch(message);
                            break;
                        case "CHALLENGE":
                            //Append the challenger to the list
                            challengerApproaches(message);
                            break;
                    }
            }
        }
    }
}
