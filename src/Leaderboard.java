import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        } catch (Exception ignored) {}
    }

    public void updateScores(Player[] players) {
        // Update the players score with the leaderboard based on name matching
        for (Player player : players) {
            boolean foundMatch = false;
            for (LeaderboardScore score : scores) {
                if (player.name == score.name) {
                    foundMatch = true;
                    score.timesWon = player.timesWon;
                }
            }
            
            // If there is no match in our existing leaderboard, then create a new score
            if (!foundMatch) {
                scores.add(new LeaderboardScore(0, player.name, player.timesWon, this));
            }
        }

        // Sort the list and change the ranking's to match correctly
        Collections.sort(scores, new Comparator<LeaderboardScore>() {
            // Sort list with custom comparator to ensure that the timesWon determines ranking
            @Override
            public int compare(LeaderboardScore o1, LeaderboardScore o2) {
                return Integer.compare(o1.timesWon, o2.timesWon);
            }
        });

        // Reverse the list so the highest ranking will be first in the list
        Collections.reverse(scores);

        // Correct the scores ranking value to be inline with the list index
        for (int i = 0; i < scores.size(); i++) {
            scores.get(i).ranking = i + 1;
        }

        // Remove and then re-add the scores to the leaderboard so it will display correctly
        for (LeaderboardScore score : scores) {
            remove(score);
        }
        for (LeaderboardScore score : scores) {
            add(score);
            score.updateText();
        }

        // Write our scores to the leaderboard file
        File file = new File("Leaderboard.txt");

        if (file.exists()) {
            try {
                PrintWriter writer = new PrintWriter("Leaderboard.txt");
                
                for (LeaderboardScore score : scores) {
                    writer.print("");
                }
            } catch (Exception ignored) {}
        }
    }
}