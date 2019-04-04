package Controllers;

import States.GameManager;
import Views.ViewSubject;

public class MatchController extends ViewSubject {

    GameManager gameManager;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
}
