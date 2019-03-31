package Players;

import java.util.Scanner;

public class InputPlayer implements Player {
    private String username;
    Scanner scanner;

    public InputPlayer(String username){
        this.username = username;

        scanner = new Scanner(System.in);
    }

    @Override
    public boolean move(int coordinate) {
        return false;
    }

    @Override
    public int requestMove() {
        int input = scanner.nextInt();
        //perform check(s) on the input
        return input;
    }

    public String getUsername() {
        return username;
    }
}
