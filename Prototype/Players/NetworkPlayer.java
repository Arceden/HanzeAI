package Players;

import GameModes.Game;

public class NetworkPlayer implements Player {

    private String username;

    public NetworkPlayer(String username){
        this.username=username;
    }

    @Override
    public boolean move(int coordinate) {
        return false;
    }

    @Override
    public int requestMove(Game game) {
        return 0;
    }


    @Override
    public String getUsername() {
        return username;
    }
}
