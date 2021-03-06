package Controllers;

import GameModes.Game;
import GameModes.Reversi;
import GameModes.TicTacToe;
import Observer.*;
import Players.*;
import States.GameManager;
import com.sun.xml.internal.ws.util.StringUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.Map;

public class LobbyController extends ObservationSubject implements Observer {

    Thread playerlistThread;
    GameManager gameManager;
    ObservableList<String> playerList = FXCollections.observableArrayList();
    ObservableList<Text> challengerList = FXCollections.observableArrayList();
    ObservableList<String> scoreList = FXCollections.observableArrayList();

    String selectedPlayer;
    boolean beenHereBefore = false;

    @FXML private Button asAI;
    @FXML private Button asPlayer;
    @FXML private HBox subscribeTo;
    @FXML private Label displayUsername;
    @FXML private ListView<String> playerView;
    @FXML private ListView<Text> challengerView;
    @FXML private ListView<String> scoreView;

    public LobbyController(){

    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void refresh(){
        //Default playstyle
        //If the player has not been defined
        if(gameManager.getPlayer()==null)
            asPlayer();
        //If the player is going under a new username
        if(!gameManager.getUsername().equalsIgnoreCase(gameManager.getPlayer().getUsername()))
            asPlayer();

        //Retreive gamelist and playerlist
        gameManager.server.getGamelist();
        playerlistUpToDate();

        //Initialize the listViews
        playerView.setItems(playerList);
        challengerView.setItems(challengerList);
        scoreView.setItems(scoreList);

        //CONTEXT MENU
        ContextMenu contextMenu = new ContextMenu();
        MenuItem rev = new MenuItem("Challenge for Reversi");
        MenuItem tic = new MenuItem("Challenge for Tic-tac-toe");
        rev.setOnAction(e->{
            if(selectedPlayer==null)
                return;
            System.out.println("Challenging "+selectedPlayer+" for a game of Reversi");
            gameManager.server.send("challenge \"" + selectedPlayer + "\" \"Reversi\"");
        });
        tic.setOnAction(e->{
            if(selectedPlayer==null)
                return;
            System.out.println("Challenging "+selectedPlayer+" for a game of Tic-tac-toe");
            gameManager.server.send("challenge \"" + selectedPlayer + "\" \"Tic-tac-toe\"");
        });

        contextMenu.getItems().addAll(rev, tic);

        playerView.setOnContextMenuRequested(e->{
            contextMenu.show(playerView, e.getScreenX(), e.getScreenY());
        });


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
                    Thread.sleep(1000);
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
        for(int i=0;i<players.length;i++){
            players[i] = players[i].substring(1, players[i].length()-1);

            //Create combo boxes

        }


        Platform.runLater(()->{
            playerList.clear();
            playerList.addAll(players);
        });
    }

    private void gameScoreHandler(String message){
        String[] args = message.split(" ");
        message = message.replace("SVR GAME LOSS", "SVR GAME ");
        message = message.replace("SVR GAME DRAW","SVR GAME ");
        message = message.replace("SVR GAME WIN", "SVR GAME ");
        Map<String, String> data = gameManager.server.parseData("SVR GAME ", message);

        Platform.runLater(()->{
            String score = "";
            score+=args[2]+" ";
            score+=gameManager.getGame().getName();
            score+=": ";
            score+=gameManager.getGame().getPlayer1().getUsername();
            score+=" VS ";
            score+=gameManager.getGame().getPlayer2().getUsername();

            scoreList.add(score);
        });

    }

    @FXML
    private void subscribeToTic(){
        gameManager.server.subscribe("Tic-tac-toe");
    }

    @FXML
    private void subscribeToRev(){
        gameManager.server.subscribe("Reversi");
    }

    /** Switch the current playstyle to Human */
    @FXML
    void asPlayer(){
        gameManager.setPlayer(new ViewPlayer(gameManager.getUsername()));
        asPlayer.setDisable(true);
        asAI.setDisable(false);
        System.out.println("Playing as human!");
    }

    /** Switch the current playstyle to AI */
    @FXML
    void asAI(){
        gameManager.setPlayer(new AIPlayer(gameManager.getUsername()));
        asPlayer.setDisable(false);
        asAI.setDisable(true);
        System.out.println("Playing as AI!");
    }

    /** Challenge the selected player from the playerView
     *  This function only stores the selectedPlayer into the
     *  selectedPlayer variable. The rest is being taken care of
     *  by the context menu. */
    @FXML
    public void challengePlayer(){
        try {
            // Get the selected player from the playerView
            // Throws a NullPointerException if no one was selected
            selectedPlayer = playerView.getSelectionModel().getSelectedItem();

            // Check if the challenged player is yourself. If so, set to null.
            // You cannot challenge yourself
            if (selectedPlayer.equalsIgnoreCase(gameManager.getUsername())) {
                selectedPlayer=null;
            }
        } catch (NullPointerException ex){
            //This happens when the listview is clicked but noone was selected
            //ignore
            selectedPlayer=null;
        }
    }

    /** Send a accepted challenge to the server */
    @FXML
    void acceptChallenge(){
        try {
            // Get the selected player from the challengerView
            // Throws a NullPointerException if no one was selected
            Text selectedPlayer = challengerView.getSelectionModel().getSelectedItem();
            String challengeNumber = (String) selectedPlayer.getUserData();

            //Send the challenge number to the server
            gameManager.server.send("challenge accept " + challengeNumber);

            //Remove the challenger from the challenge view
            Platform.runLater(()->{
                int i = challengerList.indexOf(selectedPlayer);
                challengerList.remove(i);
            });
        } catch (NullPointerException ex){
            //This happens when the listview is clicked but noone was selected
            //ignore
        }
    }

    /** Disconnect from the server and go back to the login screen */
    @FXML
    public void disconnect(){
        playerlistThread.interrupt();

        gameManager.disconnect();
        notifyObservers("LOGIN");
    }

    /**
     * Start the match
     * This method initializes the Game variable in the GameManager along with both players
     * @param message - String from the server
     */
    private void startMatch(String message){
        //Parse the server data into a map
        Map<String, String> data = gameManager.server.parseData("SVR GAME MATCH ", message);

        // Declare an empty game object
        Game game=null;

        // Declare empty player objects
        // These can become either an AIPlayer, InputPlayer, NetworkPlayer or a ViewPlayer.
        Player player1;
        Player player2;

        // Get the server data to start a game
        String GAMETYPE = data.get("GAMETYPE");
        String FIRSTPLAYER = data.get("PLAYERTOMOVE");
        String OPPONENT = data.get("OPPONENT");

        //Set the players
        if(FIRSTPLAYER.equalsIgnoreCase(gameManager.getUsername())) {
            //You're first!
            player1=gameManager.getPlayer();
            player2=new NetworkPlayer(OPPONENT);
        } else {
            //Network player goes first
            player1=new NetworkPlayer(OPPONENT);
            player2=gameManager.getPlayer();
        }

        //Set the game type
        if(GAMETYPE.equalsIgnoreCase("Reversi"))
            game = new Reversi(player1, player2);
        else if(GAMETYPE.equalsIgnoreCase("Tic-tac-toe"))
            game = new TicTacToe(player1, player2);

        //Place the game in the game manager
        gameManager.setGame(game);

        //Change the gameManager state to InMatchState
        playerlistThread.interrupt();
        gameManager.matchStart();

        //Change view
        notifyObservers(GAMETYPE);
    }

    /** Another player has challenged you. Show this on screen */
    private void challengerApproaches(String message){
        //Parse the server message into a usable Map
        Map<String, String> data = gameManager.server.parseData("SVR GAME CHALLENGE ", message);
        String challenger = data.get("CHALLENGER");
        String challengeNumber = data.get("CHALLENGENUMBER");
        String gameType = data.get("GAMETYPE");

        //Update the view with the new challenger
        Platform.runLater(()->{
            Text tChallenger = new Text(gameType+": "+challenger);
            tChallenger.setUserData(challengeNumber);
            challengerList.add(tChallenger);
        });
    }

    /** Remove the cancelled challenge from the list */
    private void challengerCancellled(String message){
        //Parse the server message into a usable Map
        Map<String, String> data = gameManager.server.parseData("SVR GAME CHALLENGE CANCELLED ", message);

        //Loop through the challengers until the correct challenger has been found
        for(Text challenger: challengerList){
            if(data.get("CHALLENGENUMBER").equalsIgnoreCase((String)challenger.getUserData())){
                //Remove the challenger from the view
                Platform.runLater(()->{
                    int i = challengerList.indexOf(challenger);
                    challengerList.remove(i);
                });
                break;
            }
        }
    }

    /**
     * Receive server messages
     * Tasks:
     *  Manage incoming challenges
     *  Manage canceled challenges
     *  Receive start match details
     *
     *  This method is being called from the ServerHandler class and is
     *  being triggered by the listen() method.
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
                        case "WIN":
                            System.out.println(message);
                            gameScoreHandler(message);
                            notifyObservers("LOBBY");
                            break;
                        case "LOSS":
                            gameScoreHandler(message);
                            notifyObservers("LOBBY");
                            break;
                        case "DRAW":
                            gameScoreHandler(message);
                            notifyObservers("LOBBY");
                            break;
                        case "CHALLENGE":
                            if(args[3].equalsIgnoreCase("CANCELLED"))
                                //Remove from list
                                challengerCancellled(message);
                            else
                                //Append the challenger to the list
                                challengerApproaches(message);
                            break;
                    }
                    break;

                case "PLAYERLIST":
                    playerlistHandler(message);
                    break;
                case "GAMELIST":
//                    gamelistHandler(message);
                    break;
            }
        }
    }

    /** Stop the threads. This command has to be executed when the window is being closed */
    public void stopThreads(){
        try {
            playerlistThread.interrupt();
        } catch (NullPointerException ex){
            //ignore
        }
    }

}
