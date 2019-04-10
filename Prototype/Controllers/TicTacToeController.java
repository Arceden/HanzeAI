package Controllers;

import GameModes.Game;
import Observer.ObservationSubject;
import Players.AIPlayer;
import Players.Player;
import States.GameManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class TicTacToeController extends ObservationSubject {


    /**
     * Declaring variables
     *
     * */

    GameManager gameManager;
    Game game;
    String player1;
    String player2;

    @FXML Button b1;
    @FXML Button b2;
    @FXML Button b3;
    @FXML Button b4;
    @FXML Button b5;
    @FXML Button b6;
    @FXML Button b7;
    @FXML Button b8;
    @FXML Button b9;

    @FXML GridPane gameBoard;

    private boolean isFirstPlayer;

    public TicTacToeController()
    {
        System.out.println("TicTacToeController");
        //this.game = gameManager.getGame();
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public void goGame(){

        game = gameManager.getGame();
        game.start();
        while (!game.hasEnded()){
            int move = game.getNextMove();
            game.move(move);
//            game.MakeMove(move);
            //game.switchTurns();
        }
    }

    @FXML
    public void methodClickHandler()
    {

    }

    @FXML
    /**
     * @param event
     */
    public void buttonClickHandler(ActionEvent event)
    {

        Button clickedButton = (Button) event.getTarget();
        String buttonLabel = clickedButton.getText();

        if("".equals(buttonLabel) && isFirstPlayer)
        {
            player1 = gameManager.getUsername();
            //game.setPlayer1(new Play);

            clickedButton.setText("X");
            isFirstPlayer = false;
        }
        else if("".equals(buttonLabel))
        {
            player2 = gameManager.getUsername();
            clickedButton.setText("O");
            isFirstPlayer = true;
        }

        if(gameLogic())
        {
            if(isFirstPlayer)
            {
                System.out.println("The winner is " + player1);
            }
            else
            {
                System.out.println("The winner is " + player2);
            }
            System.out.println("game over");
        }
    }

    private boolean gameLogic()
    {
        //Row 1
        if ("" != b1.getText() && b1.getText() == b2.getText() && b2.getText() == b3.getText())
        {
            highlightWinningCombo(b1,b2,b3);
            return true;
        }

        //Row 2
        if (""!=b4.getText() && b4.getText() == b5.getText() && b5.getText() == b6.getText())
        {
            highlightWinningCombo(b4,b5,b6);
            return true;
        }

        //Row 3
        if (""!=b7.getText() && b7.getText() == b8.getText() && b8.getText() == b9.getText())
        {
            highlightWinningCombo(b7,b8,b9);
            return true;
        }

        //Column 1
        if (""!=b1.getText() && b1.getText() == b4.getText() && b4.getText() == b7.getText())
        {
            highlightWinningCombo(b1,b4,b7);
            return true;
        }

        //Column 2
        if (""!=b2.getText() && b2.getText() == b5.getText() && b5.getText() == b8.getText())
        {
            highlightWinningCombo(b2,b5,b8);
            return true;
        }

        //Column 3
        if (""!=b3.getText() && b3.getText() == b6.getText() && b6.getText() == b9.getText())
        {
            highlightWinningCombo(b3,b6,b9);
            return true;
        }

        //Diagonal 1
        if (""!=b1.getText() && b1.getText() == b5.getText() && b5.getText() == b9.getText())
        {
            highlightWinningCombo(b1,b5,b9);
            return true;
        }
        //Diagonal 2
        if (""!=b3.getText() && b3.getText() == b5.getText() && b5.getText() == b7.getText())
        {
            highlightWinningCombo(b3,b5,b7);
            return true;
        }
        return false;
    }

    private void highlightWinningCombo(Button first, Button second, Button third)
    {
        first.getStyleClass().add("winning-button");
        second.getStyleClass().add("winning-button");
        third.getStyleClass().add("winning-button");
    }
}
