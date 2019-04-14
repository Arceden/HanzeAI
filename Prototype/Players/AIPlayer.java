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
    public int requestMove(Game game) {
        //System.out.println(game.getCurrentPlayer().getUsername());
        int player;
        if(game.getCurrentPlayer() == game.getPlayer1()) player = 1;
        else player = 2;
        return algorithm.bestMove(game.getBoard(), player, game);
    }

    public String getUsername() {
        return username;
    }
}
