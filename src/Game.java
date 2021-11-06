import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/** Holds all game data and logic. Controls the flow of the game. */
public class Game implements Runnable {
    public static final int SPACE_SIZE = 200;
    public static final int INFO_BUTTON_HEIGHT = 20;

    private final Board board;
    private final Player[] players;

    public JLayeredPane spacePanel;
    public JFrame mainFrame;
    public JButton infoButton;
    private Player currentPlayer;
    private boolean isGameRunning;

    public Game() {
        board = new Board();
        players = new Player[] {new Player(0), new Player(1)};
    }

    public void onInfoButtonPressed(ActionEvent event) {
        // Only restart the game when the game is over
        if (isGameRunning) {
            return;
        }

        isGameRunning = true;
        resetGame();
        updateInfoButtonCurrentPlayer();
    }

    public void onSpacePressed(ActionEvent event) {
        Space space = (Space)event.getSource();

        // Only accept input when the game is running
        if (!isGameRunning) {
            return;
        }

        // The space must be empty
        if (space.state != SpaceState.Empty) {
            return;
        }

        // Assign the new state to the space
        space.state = SpaceState.values()[currentPlayer.id + 1];

        // Update display
        switch (space.state) {
            case Player1 -> space.setText("X");
            case Player2 -> space.setText("O");
        }

        // Check if won
        int gameOverState = board.isGameOver();

        switch (gameOverState) {
            case 1, 2 -> {
                currentPlayer.timesWon++;
                infoButton.setText("Player " + currentPlayer.visualID + " won! " +
                        "They have won " + currentPlayer.timesWon + " times." +
                        " Click me to have another game!"
                );
            }
            case 3 -> infoButton.setText("The game is a draw!" +
                    " Click me to have another game!"
            );
        }

        if (gameOverState != 0) {
            isGameRunning = false;
            return;
        }

        // Go on to the next player
        if (currentPlayer.id == 0) {
            currentPlayer = players[1];
        } else {
            currentPlayer = players[0];
        }

        updateInfoButtonCurrentPlayer();
    }

    @Override
    public void run() {
        // Create mainFrame to serve as base
        mainFrame = new JFrame("TicTacToe");
        mainFrame.getContentPane().setPreferredSize(
                new Dimension(SPACE_SIZE * Board.SIZE, (SPACE_SIZE * Board.SIZE) + INFO_BUTTON_HEIGHT)
        );
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        // Create spaceFrame to hold spaces
        spacePanel = new JLayeredPane();
        spacePanel.setPreferredSize(
                new Dimension(SPACE_SIZE * Board.SIZE, SPACE_SIZE * Board.SIZE)
        );
        spacePanel.setLayout(new GridLayout(3, 3));
        mainFrame.add(spacePanel, BorderLayout.PAGE_START);

        // Create infoButton to show information to player,
        // as well as to allow them to have more than 1 game
        infoButton = new JButton();
        infoButton.setPreferredSize(
                new Dimension(mainFrame.getPreferredSize().width, INFO_BUTTON_HEIGHT)
        );
        infoButton.addActionListener(this::onInfoButtonPressed);
        mainFrame.add(infoButton, BorderLayout.PAGE_END);

        isGameRunning = true;
        resetGame();
        updateInfoButtonCurrentPlayer();

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    /** Causes the infoButton to change its text to show which player is currently acting. */
    private void updateInfoButtonCurrentPlayer() {
        infoButton.setText("It is player " + currentPlayer.visualID + "'s turn to act.");
    }

    /** Resets the game. Allows a new game to begin. */
    private void resetGame() {
        currentPlayer = players[0];
        board.reset(this);
    }
}
