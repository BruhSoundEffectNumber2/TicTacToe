import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

/** Holds all game data and logic. Controls the flow of the game. */
public class Game implements Runnable {
    public static final int SPACE_SIZE = 200;
    public static final int INFO_BUTTON_HEIGHT = 20;
    public static final int LEADERBOARD_WIDTH = 180;

    private final Board board;
    private final Player[] players;

    public Leaderboard leaderboard;
    public JPanel gamePanel;
    public JPanel spacePanel;
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
            case Empty -> space.setText("");
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
                leaderboard.updateScores(players);
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
        // TODO: Put the creation of the various components into separate functions
        // Create mainFrame to serve as base
        mainFrame = new JFrame("TicTacToe");
        mainFrame.getContentPane().setPreferredSize(
                new Dimension(
                        SPACE_SIZE * Board.SIZE + LEADERBOARD_WIDTH,
                        SPACE_SIZE * Board.SIZE + INFO_BUTTON_HEIGHT
                )
        );
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        // Create gamePanel to hold game panels
        gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setPreferredSize(
                new Dimension(SPACE_SIZE * Board.SIZE, SPACE_SIZE * Board.SIZE + INFO_BUTTON_HEIGHT)
        );
        mainFrame.add(gamePanel, BorderLayout.LINE_START);

        // Create spaceFrame to hold spaces
        spacePanel = new JPanel();
        spacePanel.setPreferredSize(
                new Dimension(SPACE_SIZE * Board.SIZE, SPACE_SIZE * Board.SIZE)
        );
        spacePanel.setLayout(new GridLayout(3, 3));
        gamePanel.add(spacePanel, BorderLayout.PAGE_START);

        // Create infoButton to show information to player,
        // as well as to allow them to have more than 1 game
        infoButton = new JButton();
        infoButton.setPreferredSize(
                new Dimension(SPACE_SIZE * Board.SIZE, INFO_BUTTON_HEIGHT)
        );
        infoButton.addActionListener(this::onInfoButtonPressed);
        gamePanel.add(infoButton, BorderLayout.PAGE_END);

        // Create leaderboard
        leaderboard = new Leaderboard();
        leaderboard.setLayout(new BoxLayout(leaderboard, BoxLayout.PAGE_AXIS));
        leaderboard.setPreferredSize(
                new Dimension(
                        LEADERBOARD_WIDTH,
                        mainFrame.getPreferredSize().height
                )
        );
        mainFrame.add(leaderboard, BorderLayout.LINE_END);

        resetGame();
        updateInfoButtonCurrentPlayer();

        mainFrame.pack();
        mainFrame.setVisible(true);

        getPlayerName(players[0]);
        getPlayerName(players[1]);

        // Update the player's timesWon to match the leaderboard
        for (Player player: players) {
            for (LeaderboardScore score : leaderboard.getScores()) {
                if (Objects.equals(player.name, score.name)) {
                    player.timesWon = score.timesWon;
                }
            }
        }

        isGameRunning = true;
    }

    private void getPlayerName(Player p) {
        while (true) {
            String input = (String)JOptionPane.showInputDialog(
                mainFrame, 
                "Player " + p.visualID + ". What is your name? (Max 6 letters)",
                "Enter Your Name"
            );

            if (input == null) {
                System.exit(1);
            }

            if (input.length() > 0 && input.length() <= 6) {
                p.name = input;
                break;
            }
        }
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
