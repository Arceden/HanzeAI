package Players;

import GameModes.Game;

import java.util.Scanner;

public class InputPlayer extends AbstractPlayer {
    private Scanner scanner;

    /** Initialize the Player by storing the username in the super
     *  class and initializing the scanner for CLI input. */
    public InputPlayer(String username){
        super(username);
        scanner = new Scanner(System.in);
    }

    /** Execute the move on behalf of the current user */
    @Override
    public boolean move(int coordinate) {
        return false;
    }

    /** Request a move from the player and wait until it has been done */
    @Override
    public int requestMove(Game game) {
        System.out.println("Please enter a coordinate: ");
        int input = scanner.nextInt();
        //perform check(s) on the input
        return input;
    }

}
