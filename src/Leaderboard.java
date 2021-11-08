import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/** Keeps track of and displays leaderboard stats. */
public class Leaderboard extends JPanel {
    private JLabel label;
    private ArrayList<LeaderboardScore> scores;

    public Leaderboard() {
        label = new JLabel("Leaderboard:");
        label.setPreferredSize(
                new Dimension(Game.LEADERBOARD_WIDTH, 20)
        );
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        this.add(label);

        scores = new ArrayList<>();

        // Try to load the scores we already had
        try {
            Scanner scanner = new Scanner(new File("Leaderboard.txt"));

            while (scanner.hasNext()) {
                String[] leaderboardScore = scanner.nextLine().split(",");

                // Get values from string
                int ranking = Integer.parseInt(leaderboardScore[0]);
                String name = leaderboardScore[1];
                int timesWon = Integer.parseInt(leaderboardScore[2]);

                scores.add(new LeaderboardScore(ranking, name, timesWon, this));
            }

            scanner.close();
        } catch (Exception e) {
            // Create a new file to save scores to
            try {
                //noinspection ResultOfMethodCallIgnored
                new File("Leaderboard.txt").createNewFile();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
