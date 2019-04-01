import Algorithms.*;
import GameModes.*;
import Players.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    TextArea taLog;
    Label lStatus;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane mainPane = new BorderPane();
        GridPane menuPane = new GridPane();
        taLog = new TextArea();
        lStatus = new Label();

        mainPane.setRight(taLog);
        mainPane.setBottom(lStatus);
        mainPane.setCenter(menuPane);

        //Create a scene and place it in the stage
//        Scene scene = new Scene(mainPane);
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Game Client");
//        primaryStage.show();


        /**
         * Test the players and the game
         */
//
//        ComboBox<String> player1Type = new ComboBox<>();
//        ComboBox<String> player2Type = new ComboBox<>();
//        player1Type.getItems().addAll("Player", "Computer");
//        player2Type.getItems().addAll("Player", "Computer", "Network");
//        TextField player1Username = new TextField();
//        TextField player2Username = new TextField();
//        Button submit = new Button("Start");
//
//        menuPane.add(new Label("Player 1"), 0, 0);
//        menuPane.add(player1Type, 1, 0);
//        menuPane.add(player1Username, 2, 0);
//
//        menuPane.add(new Label("Player 2"), 0, 1);
//        menuPane.add(player2Type, 1, 1);
//        menuPane.add(player2Username, 2, 1);
//
//        menuPane.add(submit, 0, 3);
//
//        submit.setOnAction(e->{
//            Player player1 = null;
//            Player player2 = null;
//
//            switch (player1Type.getValue()){
//                case "Player":
//                    player1 = new InputPlayer(player1Username.getText());
//                    break;
//                case "Computer":
//                    player1 = new AIPlayer(player1Username.getText(), new Minimax());
//                    break;
//            }
//
//            switch (player2Type.getValue()){
//                case "Player":
//                    player2 = new InputPlayer(player2Username.getText());
//                    break;
//                case "Computer":
//                    player2 = new AIPlayer(player2Username.getText(), new Minimax());
//                    break;
//            }
//
//            Runnable controller = new GameController(player1, player2);
//            controller.run();
//
//        });

        //Handle the server connection
        ServerConnection server = new ServerConnection("localhost", 7789);
        server.connect();
        server.listen();

        server.login("Arnold");

        while(true) {
            try {
                server.send("test");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class GameController implements Runnable {
        private Game game;
        private Player player1;
        private Player player2;

        public GameController(Player player1, Player player2){
            this.player1=player1;
            this.player2=player2;
            game = new TicTacToe(player1, player2);
        }

        @Override
        public void run() {
            game.start();

            while (!game.hasEnded()){
                Platform.runLater(()-> lStatus.setText(game.getCurrentPlayer()+"'s turn!"));
                int move = game.getNextMove();
                game.move(move);
            }
        }
    }
}