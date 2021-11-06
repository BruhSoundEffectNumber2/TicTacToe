/** Represents one of the players of the game, and the data that makes them up. */
public class Player {
    public int id;
    public int visualID;
    public int timesWon;

    public Player(int id) {
        this.id = id;
        this.visualID = id + 1;
        timesWon = 0;
    }
}
