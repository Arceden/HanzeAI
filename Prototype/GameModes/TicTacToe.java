package GameModes;

import Players.Player;

public class TicTacToe extends AbstractGame {

    private boolean running;

    private int gridSize = 3;

    /** Store the initialized players and initialize the game board. */
    public TicTacToe(Player player1, Player player2){
        super(player1, player2);

        // Set the game name
        this.name = "Tic-tac-toe";

        // Initialize the board
        this.board = new Integer[gridSize][gridSize];
        this.board = initBoard(board);
    }

    /** Execute the move on behalf of the current player. */
    public boolean move(int coordinate) {
        System.out.println("Moving "+playerTurn.getUsername()+" to "+coordinate+"!");

        int x = (int) Math.floor(coordinate / 3);
        int y = (coordinate % 3);

        int playerNum=0;
        if(playerTurn==player1) playerNum=1;
        if(playerTurn==player2) playerNum=2;

        board[x][y] = playerNum;

        if(gameLogic(playerNum, x, y))
        {
            running=false;
            System.out.println("We have a winner");
        }
        else {
            System.out.println("x : " + x);
            System.out.println("y : " + y);
        }

        switchTurns();
        return true;
    }

    /** Check if the specified move is valid */
    public boolean moveIsValid(int coordinate)
    {
        int x = (int) Math.floor(coordinate / 3);
        int y = (coordinate % 3);
        return board[x][y]==0;
    }

    /** Check the current status of the board and detemine if the game has ended */
    public boolean hasEnded() {
        return !running;
    }

    /** Check the current state of the board by checking the game rules. */
    private boolean gameLogic(int input, int row, int col)
    {
        return ((board[row][0] == input &&
                board[row][1] == input &&
                board[row][2] == input) ||

                (board[0][col] == input &&
                board[1][col] == input &&
                board[2][col] == input) ||

                (row == col && board[0][0] == input &&
                board[1][1] == input &&
                board[2][2] == input) ||

                (row + col == 2 && board[0][2] == input &&
                board[1][1] == input &&
                board[2][0] == input));
    }

    /** Start the game by preparing the board and choose who starts
     *  (or retreive the starting player if online) */
    public void start() {
        playerTurn=player1;
        running=true;
    }

    /** Generate a new game board with the default starting positions. */
    private Integer[][] initBoard(Integer[][] board) {
        // setup the board
        for(int x = 0; x < gridSize; x++){
            for(int y =0; y < gridSize; y++){
                board[x][y] = 0;
            }
        }

        return board;
    }

    /** Get the amount of valid moves that are left. */
    @Override
    public boolean validMovesLeft() {
        return false;
    }

    /** Get the amount of flippable stones */
    @Override
    public int flippableCount(int x, int y) {
        return 0;
    }

    /** Calculate the amount of valid moves */
    @Override
    public Integer[][] calculateValidMoves() {
        return new Integer[0][];
    }
}
