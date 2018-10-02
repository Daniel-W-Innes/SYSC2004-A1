
//debug statement: import java.util.Random;
import java.util.Scanner;

/**
 * @author Daniel Innes 101067175
 * 
 */

public class TicTacToe {

	/**
	 * The number of rows in the TicTacToe grid.
	 */
	private int nRows;
	/**
	 * The number of columns in the TicTacToe grid.
	 */
	private int nColumns;
	/**
	 * The number connected of cells needed to win.
	 */
	private int numToWin;
	/**
	 * The TicTacToe grid.
	 */
	private char[][] grid;
	/**
	 * A char represents the players. i.e. 'X' or 'O'
	 */
	private char turn;
	/**
	 * The current state of the game.
	 */
	private TicTacToeEnum gameState;
	/**
	 * The number of turn that have passed.
	 */
	private int nMarks;

	/**
	 * The main client for TicTacToe.
	 */
	public static void main(String args[]) {
		TicTacToe game = new TicTacToe('X');
		Scanner scanner = new Scanner(System.in);
		//debug statement: Random r = new Random();

		do {
			System.out.println(game.toString());
			System.out.println(game.getTurn() + ": Where do you want to mark? Enter row column");

			
			  int row = scanner.nextInt(); int column = scanner.nextInt();
			  scanner.nextLine();
			 
			//debug statement: int row = r.nextInt(10); int column = r.nextInt(10);
			game.takeTurn(row, column);

		} while (game.getGameState() == TicTacToeEnum.IN_PROGRESS);
		System.out.println(game.toString());
		System.out.println(game.getGameState());
		scanner.close();
	}

	/**
	 * Main constructor generates a 3x3 TicTacToe grid.
	 * 
	 * @param initalTurn The char representing the first player.
	 */
	public TicTacToe(char initalTurn) {
		nRows = 3;
		nColumns = 3;
		numToWin = 3;
		reset(initalTurn);
	}

	/**
	 * Secondary constructor generates a TicTacToe grid of given size.
	 * 
	 * @param nRows      The number of rows in the TicTacToe grid to be generated.
	 * @param nColumns   The number of rows in the TicTacToe grid to be generated.
	 * @param numToWin   The number of marks needed to win.
	 * @param initalTurn The char representing the first player.
	 */
	public TicTacToe(int nRows, int nColumns, int numToWin, char initalTurn) {
		if (nRows <= 0 || nColumns <= 0)
			throw new IllegalArgumentException("Please insert positive dimensions.");
		if (numToWin <= 0)
			throw new IllegalArgumentException("The number of marks needed to win must be positive");
		this.nRows = nRows;
		this.nColumns = nColumns;
		this.numToWin = numToWin;
		reset(initalTurn);
	}

	/**
	 * Reset the TicTacToe grid to nRows and nColumns previously provided.
	 * 
	 * @param initialTurn The char representing the first player.
	 */
	public void reset(char initialTurn) {
		grid = new char[nRows][nColumns];
		for (int i = 0; i < nRows; ++i) {
			for (int j = 0; j < nColumns; ++j) {
				grid[i][j] = ' ';
			}
		}
		turn = initialTurn;
		gameState = TicTacToeEnum.IN_PROGRESS;
	}

	/**
	 * Get the char representing the player whose turn it is.
	 * 
	 * @return A char represents the player.
	 */
	public char getTurn() {
		return turn;
	}

	/**
	 * Get the current state of the game.
	 * 
	 * @return The current state of the game.
	 */
	public TicTacToeEnum getGameState() {
		return gameState;
	}

	/**
	 * Get the gameState if the player represented by char player.
	 * 
	 * @param player A char represents the player.
	 * @return The resulting gameState.
	 */
	private TicTacToeEnum charToEnum(char player) {
		switch (player) {
		case 'X':
			return TicTacToeEnum.X_WON;
		case 'O':
			return TicTacToeEnum.O_WON;
		default:
			return TicTacToeEnum.DRAW;
		}
	}

	/**
	 * Insert a char representing the current player in to the grid at prescribed
	 * location.
	 * 
	 * @param row    The row where the char will be placed.
	 * @param column The column where the char will be placed.
	 */
	public void takeTurn(int row, int column) {
		if (row < nRows && column < nColumns) {
			if (grid[row][column] == ' ') {
				grid[row][column] = turn;
				nMarks++;
				gameState = findWinner();
				if (turn == 'X')
					turn = 'O';
				else
					turn = 'X';
			}
		}
	}

	/**
	 * Determine if there is a winner and return the gameState that reflects the new
	 * state of the game.
	 * 
	 * @return The resulting gameState.
	 */
	private TicTacToeEnum findWinner() {

		int i;
		int j;
		// Look through the grid and determine if any of the marks of the player who just went Are part of a victory.
		for (i = 0; i < nRows; ++i) {
			for (j = 0; j < nColumns; ++j) {
				if (grid[i][j] == turn) {
					if (isVictory(i, j)) {
						return charToEnum(grid[i][j]);
					}
				}
			}
		}
		//Check if The grid is a draw.
		if (nMarks == nRows * nColumns) {
			return TicTacToeEnum.DRAW;
		} else {
			return TicTacToeEnum.IN_PROGRESS;
		}

	}

	/**
	 * Determine if the mark at (x,y) is part of of a victory.
	 * 
	 * @param x The x location in the TicTacToe grid
	 * @param y The y location in the TicTacToe grid
	 * @return A boolean answering the question is the mark in a victory
	 */
	private boolean isVictory(int x, int y) {
		int i;
		int numHorizontal = 1;
		int numVertical = 1;
		int numDiagonal = 1;
		char testChar = grid[x][y];
		// Counting the number of marks in a row in the negative X direction.
		for (i = x - 1; i > x - numToWin; --i) {
			if (i >= 0) {
				if (grid[i][y] == testChar) {
					numHorizontal++;
				} else {
					break;
				}
			}
		}
		// Counting the number of marks in a row in the negative Y direction.
		for (i = y - 1; i > y - numToWin; --i) {
			if (i >= 0) {
				if (grid[x][i] == testChar) {
					numVertical++;
				} else {
					break;
				}
			}
		}
		// Counting the number of marks in a row in the negative diagonal direction.
		for (i = -1; i > -numToWin; --i) {
			if (i + x >= 0) {
				if (i + y >= 0) {
					if (grid[i + x][i + y] == testChar) {
						numDiagonal++;
					} else {
						break;
					}
				}
			}
		}
		// Counting the number of marks in a row in the positive X direction.
		for (i = x + 1; i < x + numToWin; ++i) {
			if (i < nRows) {
				if (grid[i][y] == testChar) {
					numHorizontal++;
				} else {
					break;
				}
			}
		}
		// Counting the number of marks in a row in the positive Y direction.
		for (i = y + 1; i < y + numToWin; ++i) {
			if (i < nColumns) {
				if (grid[x][i] == testChar) {
					numVertical++;
				} else {
					break;
				}
			}
		}
		// Counting the number of marks in a row in the positive diagonal direction.
		for (i = 1; i < numToWin; ++i) {
			if (i + x < nRows) {
				if (i + y < nColumns) {
					if (grid[i + x][i + y] == testChar) {
						numDiagonal++;
					} else {
						break;
					}
				}
			}
		}
		if (numHorizontal >= numToWin || numVertical >= numToWin || numDiagonal >= numToWin)
			return true;
		return false;
	}

	/**
	 * return a string representing the TicTacToe grid e.g. X | O | | X | O | | | |
	 * |
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String output = "";
		for (int i = 0; i < nRows; ++i) {
			for (int j = 0; j < nColumns; ++j) {
				output += Character.toUpperCase(grid[i][j]) + " | ";
			}
			output += "\n";
		}
		return output;
	}
}
