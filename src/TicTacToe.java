
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

		do {
			System.out.println(game.toString());
			System.out.println(game.getTurn() + ": Where do you want to mark? Enter row column");
			int row = scanner.nextInt();
			int column = scanner.nextInt();
			scanner.nextLine();
			game.takeTurn(row, column);

		} while (game.getGameState() == TicTacToeEnum.IN_PROGRESS);
		System.out.println(game.getGameState());
		scanner.close();
	}

	/**
	 * Main constructor generators a 3x3 TicTacToe grid.
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
	 * Secondary constructor generators a TicTacToe grid of given size.
	 * 
	 * @param nRows      The number of rows in the TicTacToe grid to be generated.
	 * @param nColumns   The number of rows in the TicTacToe grid to be generated.
	 * @param numToWin   The number connected of cells needed to win.
	 * @param initalTurn The char representing the first player.
	 */
	public TicTacToe(int nRows, int nColumns, int numToWin, char initalTurn) {
		if (nRows < 0)
			throw new IllegalArgumentException("Nice msg");
		if (nColumns < 0)
			throw new IllegalArgumentException("Nice msg");
		if (numToWin < 0)
			throw new IllegalArgumentException("Nice msg");
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
				if (turn == 'X')
					turn = 'O';
				else
					turn = 'X';
			}
			nMarks++;
			gameState = findWinner();
		}
	}

	private TicTacToeEnum findWinner() {
		if (nMarks == nRows * nColumns)
			return TicTacToeEnum.DRAW;
		int i;
		int j;

		for (i = 0; i < nRows; ++i) {
			for (j = 0; j < nColumns; ++j) {
				if (grid[i][j] != ' ') {
					if (isVictory(i, j)) {
						return charToEnum(grid[i][j]);
					}
				}
			}
		}
		return TicTacToeEnum.IN_PROGRESS;
	}

	private boolean isVictory(int x, int y) {
		int i;
		int j;
		int numHorizontal = 1;
		int numVertical = 1;
		int numDiagonal = 1;
		char testChar = grid[x][y];
		for (i = x - 1; i > x - numToWin; --i) {
			if (i >= 0) {
				if (grid[i][y] == testChar) {
					numHorizontal++;
				} else {
					break;
				}
			}
		}
		for (i = y - 1; i > y - numToWin; --i) {
			if (i >= 0) {
				if (grid[x][i] == testChar) {
					numVertical++;
				} else {
					break;
				}
			}
		}
		for (i = x + 1; i < x + numToWin; ++i) {
			if (i < nRows) {
				if (grid[i][y] == testChar) {
					numHorizontal++;
				} else {
					break;
				}
			}
		}
		for (i = y + 1; i < y + numToWin; ++i) {
			if (i < nColumns) {
				if (grid[x][i] == testChar) {
					numVertical++;
				} else {
					break;
				}
			}
		}
		for (i = x + 1; i < x + numToWin; ++i) {
			for (j = y + 1; j < y + numToWin; ++j) {
				if (i < nRows) {
					if (j < nColumns) {
						if (grid[i][j] == testChar) {
							numDiagonal++;
						} else {
							break;
						}
					}
				}
			}
		}
		for (i = x - 1; i > x - numToWin; --i) {
			for (j = y - 1; j > y - numToWin; --j) {
				if (i >= 0) {
					if (j >= 0) {
						if (grid[i][j] == testChar) {
							numDiagonal++;
						} else {
							break;
						}
					}
				}
			}
		}
		if (numHorizontal >= numToWin || numVertical >= numToWin || numDiagonal >= numToWin)
			return true;
		return false;
	}

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
