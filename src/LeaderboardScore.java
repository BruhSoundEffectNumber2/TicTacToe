import javax.swing.*;
import java.awt.*;

/**
 * Represents a high score for the Leaderboard.
 * Responsible for displaying the score within the leaderboard.
 */
public class LeaderboardScore extends JLabel {
    public static final int LEADERBOARD_SCORE_HEIGHT = 20;

    public int ranking;
    public String name;
    public int timesWon;

    public LeaderboardScore(int ranking, String name, int timesWon, Leaderboard parent) {
        this.ranking = ranking;
        this.name = name;
        this.timesWon = timesWon;

        setPreferredSize(
                new Dimension(Game.LEADERBOARD_WIDTH, LEADERBOARD_SCORE_HEIGHT)
        );
        setFont(new Font("Arial", Font.PLAIN, 18));
        parent.add(this);
        updateText();
    }

    /** Updates the text of our label to show our information. */
    public void updateText() {
        setText(ranking + ". " + name + ": " + timesWon + " wins");
    }
}
