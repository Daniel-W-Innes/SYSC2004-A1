

import java.util.Scanner;

/**
 * @author Daniel Innes 101067175
 * 
 */
public class TicTacToe {

	private int nRows;
	private int nColumns;
	private int numToWin;
	private char[][] grid;
	private char turn;
	private TicTacToeEnum gameState;
	private int nMarks;

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

	public TicTacToe(char initalTurn) {
		nRows = 3;
		nColumns = 3;
		numToWin = 3;
		reset(initalTurn);
	}

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

	public char getTurn() {
		return turn;
	}

	public TicTacToeEnum getGameState() {
		return gameState;
	}

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
