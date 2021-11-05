import java.util.*;

public class DisplayManager {
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static boolean getContinuePlaying(Scanner scanner) {
		System.out.println("Would you like to keep playing? (Y)es or (N)o");
		while (true) {
			String rawInput = scanner.nextLine();

			// If the player followed the directions
			if (rawInput.equalsIgnoreCase("y")) {
				return true;
			} else if (rawInput.equalsIgnoreCase("n")) {
				return false;
			}

			// We have to ask again
			System.out.println("Input was incorrect. Please try again.");
		}
	}
	
	public static Space getPlayerSpaceInput(Board board, Scanner scanner) {
		int xInput;
		int yInput;

		System.out.println("What space would you like to take? (X, Y)");

		while (true) {
			// Get the input of the player
			String input = scanner.nextLine();
			String[] splitInput = input.split(",");

			// Make sure the input is parsed correctly
			try {
				xInput = Integer.parseInt(splitInput[0].trim());
				yInput = Integer.parseInt(splitInput[1].trim());
			} catch (Exception e) {
				System.out.print("Please only enter numbers and the ',' character.");
				System.out.println("Please try again.");

				continue;
			}

			// The input must be in the board
			if (xInput < 0 || xInput >= Board.SIZE || yInput < 0 || yInput >= Board.SIZE) {
				System.out.println("The input must be within the board. Please try again.");
				continue;
			}

			// The player cannot overwrite another player's move
			if (board.spaces[xInput][yInput].state != SpaceState.Empty) {
				System.out.println("That space is already taken. Please try again.");
				continue;
			}

			// We have a valid input, so allow the while loop to exit
			break;
		}
		
		return board.spaces[xInput][yInput];
	}
	
	public static void displayBoard(Board board) {
		for (int y = 0; y < Board.SIZE; y++) {
			for (int x = 0; x < Board.SIZE; x++) {				
				System.out.print("| ");
				
				switch (board.spaces[x][y].state) {
					case Empty:
						System.out.print(" ");
						break;
					case Player1:
						System.out.print("O");
						break;
					case Player2:
						System.out.print("X");
						break;
				}
				
				System.out.print(" |");
			}
			System.out.println();
		}
	}
}
