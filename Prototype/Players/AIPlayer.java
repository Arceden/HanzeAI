package Players;

import Algorithms.AI;
import Algorithms.Minimax;
import GameModes.Game;

public class AIPlayer implements Player {
    private String username;
    private AI algorithm;
    private Game game;

    public AIPlayer(String username){
        this.username = username;
        this.algorithm = new Minimax(game);
    }

//    public AIPlayer(String username, AI algorithm){
//        this(username);
//        this.algorithm = algorithm;
//    }

    @Override
    public boolean move(int coordinate) {
        return false;
    }

    @Override
    public int requestMove() {
        //return algorithm.calculateMove();
        return algorithm.calculateMove(game.getBoard(),1,4,true);
    }

    public String getUsername() {
        return username;
    }
}
