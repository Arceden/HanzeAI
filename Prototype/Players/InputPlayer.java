package Players;

import java.util.Scanner;

public class InputPlayer extends AbstractPlayer {
    private Scanner scanner;

    /** Initialize the Player by storing the username in the super
     *  class and initializing the scanner for CLI input. */
    public InputPlayer(String username){
        super(username);
        scanner = new Scanner(System.in);
    }

    @Override
    /** Execute the move on behalf of the current user */
    public boolean move(int coordinate) {
        return false;
    }

    @Override
    /** Request a move from the player and wait until it has been done */
    public int requestMove() {
        System.out.println("Please enter a coordinate: ");
        int input = scanner.nextInt();
        //perform check(s) on the input
        return input;
    }

}
