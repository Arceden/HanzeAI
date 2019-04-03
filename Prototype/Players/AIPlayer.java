package Players;

import Algorithms.AI;

public class AIPlayer implements Player {
    private String username;
    private AI algorithm;

    public AIPlayer(String username){
        this.username = username;
    }

    public AIPlayer(String username, AI algorithm){
        this(username);
        this.algorithm = algorithm;
    }

    @Override
    public boolean move(int coordinate) {
        return false;
    }

    @Override
    public int requestMove() {
        return algorithm.calculateMove();
    }

    public String getUsername() {
        return username;
    }
}
