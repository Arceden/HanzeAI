package Players;

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
    public int requestMove() {
        //Observe the server messages for a move

        //Return the move
        return 0;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
