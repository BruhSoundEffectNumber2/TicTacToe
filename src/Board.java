public class Board {
	public static final int SIZE = 3;
	
	/** The spaces that make up our game board. */
	public Space[][] spaces;

	public void reset() {
		spaces = new Space[SIZE][SIZE];
		
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				Space space = new Space(x, y);
				spaces[x][y] = space;
			}
		}
	}

	/**
	 * Called when the game want's to check to see if there is a winner.
	 * @return 0 if the game is not over, 1 if player 1 won, 2 if player 2 won, and 3 if it is a draw.
	 */
	public int isGameOver() {
		// Check rows
		for (int i = 0; i < SIZE; i++) {
			if (isRowControlled(i)) {
				return spaces[1][i].state.ordinal();
			}
		}

		// Check columns
		for (int i = 0; i < SIZE; i++) {
			if (isColumnControlled(i)) {
				return spaces[i][1].state.ordinal();
			}
		}

		// Check diagonals
		if (isUpDiagonalControlled() || isDownDiagonalControlled()) {
			return spaces[1][1].state.ordinal();
		}

		// If the board is not completely full, then the game is not over
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				if (spaces[x][y].state == SpaceState.Empty) {
					return 0;
				}
			}
		}

		// If none of the above conditions have been satisfied, then the game is a draw
		return 3;
	}

	private boolean isRowControlled(int row) {
		if (spaces[0][row].state == spaces[1][row].state) {
			if (spaces[2][row].state == spaces[1][row].state) {
				if (spaces[1][row].state != SpaceState.Empty) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean isColumnControlled(int column) {
		if (spaces[column][0].state == spaces[column][1].state) {
			if (spaces[column][2].state == spaces[column][1].state) {
				if (spaces[column][1].state != SpaceState.Empty) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean isUpDiagonalControlled() {
		if (spaces[1][1].state == spaces[0][2].state) {
			if (spaces[1][1].state == spaces[2][0].state) {
				if (spaces[1][1].state != SpaceState.Empty) {
					return true;
				}
			}
		}

		return false;
	}

	private boolean isDownDiagonalControlled() {
		if (spaces[1][1].state == spaces[0][0].state) {
			if (spaces[1][1].state == spaces[2][2].state) {
				if (spaces[1][1].state != SpaceState.Empty) {
					return true;
				}
			}
		}

		return false;
	}
}
