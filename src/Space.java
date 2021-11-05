
public class Space {
	/** What state we are in. (Are we empty or taken by a player?) */
	public SpaceState state;
	public int x;
	public int y;
	
	public Space(int x, int y) {
		state = SpaceState.Empty;
		this.x = x;
		this.y = y;
	}
}
