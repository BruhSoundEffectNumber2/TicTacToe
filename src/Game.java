import java.util.Scanner;

/** Holds all game data and logic. Controls the flow of the game. */
public class Game {
    private Scanner scanner;
    private Board board;
    private Player[] players;
    private Player currentPlayer;

    public Game() {
        scanner = new Scanner(System.in);
        board = new Board();
        players = new Player[] {new Player(0, "Tim"), new Player(1, "Sue")};
        resetGame();
    }

    /** Runs the game. */
    public void run() {
        DisplayManager.clearScreen();

        while (true) {
            DisplayManager.displayBoard(board);
            System.out.println("It is " + currentPlayer.name + "'s turn.");

            Space modifiedSpace = DisplayManager.getPlayerSpaceInput(board, scanner);
            modifiedSpace.state = SpaceState.values()[currentPlayer.id + 1];

            int gameOverState = board.isGameOver();

            switch (gameOverState) {
                case 1, 2 -> {
                    players[gameOverState - 1].timesWon++;
                    System.out.print(players[gameOverState - 1].name + " won. They have won ");
                    System.out.println(players[gameOverState - 1].timesWon + " times.");
                    if (!checkContinuePlaying()) {
                        return;
                    }
                }
                case 3 -> {
                    System.out.println("The game is a draw!");
                    if (!checkContinuePlaying()) {
                        return;
                    }
                }
            }

            // Go on to the next player
            if (currentPlayer.id == 0) {
                currentPlayer = players[1];
            } else {
                currentPlayer = players[0];
            }

            DisplayManager.clearScreen();
        }
    }

    /** Checks if the player wants to continue playing. Resets the game if yes. */
    private boolean checkContinuePlaying() {
        if (DisplayManager.getContinuePlaying(scanner)) {
            resetGame();
            return true;
        }

        DisplayManager.clearScreen();
        System.out.println("Goodbye!");
        return false;
    }

    /** Resets the game. Allows a new game to begin. */
    private void resetGame() {
        currentPlayer = players[0];
        board.reset();
    }
}
