/** Represents one of the players of the game, and the data that makes them up. */
public class Player {
    public int id;
    public String name;
    public int timesWon;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        timesWon = 0;
    }
}
