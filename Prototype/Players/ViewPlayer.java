package Players;

import GameModes.Game;

public class ViewPlayer implements Player {

    private String username;

    public ViewPlayer(String username){
        this.username=username;
    }

    @Override
    public boolean move(int coordinate) {
        return false;
    }

    @Override
    /** Dont use this method. The move is being received at the controller */
    public int requestMove(Game game) {
        return 0;
    }

    public String getUsername() {
        return username;
    }

}
