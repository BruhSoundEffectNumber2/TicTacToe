import javax.swing.*;
import java.awt.*;

/** A space that can be taken by a player. */
public class Space extends JButton {
	/** What state we are in. (Are we empty or taken by a player?) */
	public SpaceState state;
	public int x;
	public int y;
	
	public Space(int x, int y) {
		state = SpaceState.Empty;
		this.x = x;
		this.y = y;

		setPreferredSize(new Dimension(Game.SPACE_SIZE, Game.SPACE_SIZE));
		setRequestFocusEnabled(false);
		setFont(new Font("Arial", Font.PLAIN, 128));
	}
}
