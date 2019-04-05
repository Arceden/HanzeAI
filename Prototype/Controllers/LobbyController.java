package Controllers;

import Observer.*;
import Players.AIPlayer;
import Players.InputPlayer;
import States.GameManager;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class LobbyController extends ObservationSubject implements Observer {

    GameManager gameManager;
    ObservableList<String> playerList = FXCollections.observableArrayList();
    ObservableList<String> challengerList = FXCollections.observableArrayList();

    //Threads
    Thread playerlistThread;

    @FXML private Button asPlayer;
    @FXML private Button asAI;
    @FXML private Label displayUsername;
    @FXML private ListView<String> playerView;
    @FXML private ListView<String> challengerView;
    @FXML private HBox subscribeTo;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void refresh(){
        //Set default play style
        gameManager.setPlayer(new AIPlayer(gameManager.getUsername()));

        //Retreive gamelist and playerlist
        gameManager.server.getGamelist();
        playerlistUpToDate();

        //Initialize the listViews
        playerView.setItems(playerList);
        challengerView.setItems(challengerList);

        //Update the welcome text
        Platform.runLater(()->{
            displayUsername.setText("Welcome, "+gameManager.getUsername()+"!");
        });
    }

    private void playerlistUpToDate(){

        playerlistThread = new Thread(()->{
            try {
                while (true) {
                    gameManager.server.getPlayers();
                    Thread.sleep(2000);
                }
            } catch (InterruptedException ex){
                //ignore
            }
        });

        playerlistThread.start();
    }

    private void playerlistHandler(String message){
        //Format the playernames
        message = message.replace("SVR PLAYERLIST ", "");
        String[] players = message.substring(1, message.length()-1).split(", ");
        for(int i=0;i<players.length;i++)
            players[i] = players[i].substring(1, players[i].length()-1);

        //Add the list to the listview

        Platform.runLater(()->{
            playerList.clear();
            playerList.addAll(players);
        });
    }

    private void gamelistHandler(String message){
        //Clear the list, incase it has already been filled
        Platform.runLater(()->{
            subscribeTo.getChildren().clear();
        });

        //Format the items
        message = message.replace("SVR GAMELIST ", "");
        String[] gametypes = message.substring(1, message.length()-1).split(", ");
        for(int i=0;i<gametypes.length;i++)
            gametypes[i] = gametypes[i].substring(1, gametypes[i].length()-1);

        //Add a event handler and bring it to the view
        for(int i=0;i<gametypes.length;i++){
            String name = gametypes[i];
            Button subscribe = new Button("Subscribe to "+name);
            subscribe.setOnAction(e->{
                gameManager.server.subscribe(name);
            });
            Platform.runLater(()->subscribeTo.getChildren().add(subscribe));
        }
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
        playerlistThread.interrupt();

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
        playerlistThread.interrupt();
        gameManager.matchStart();

        //Change view
        notifyObservers("MATCH");
    }

    private void challengerApproaches(String message){
        // TODO: FIX THIS GARBAGE
//        String args[] = message.split(" ");
//        challengerList.add(args[4].substring(1, args[4].length()-2)+"%"+args[6].substring(1, args[6].length()-2));
    }

    /* TODO: Remove the challenger who left from the list */
    private void challengerCancellled(String message){

    }

    public void stopThreads(){
        try {
            playerlistThread.interrupt();
        } catch (NullPointerException ex){
            //ignore
        }
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
                    break;

                case "PLAYERLIST":
                    playerlistHandler(message);
                    break;
                case "GAMELIST":
                    gamelistHandler(message);
                    break;
            }
        }
    }
}
