package Controllers;

import States.GameManager;
import Observer.ObservationSubject;

public class MatchController extends ObservationSubject {

    GameManager gameManager;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
}
