package hw3;

import hw3.Game.Player;

public class SolverImpl implements TicTacToeSolver {
	private Umpire ump = null;

	public SolverImpl(Umpire umpIn) {
		ump = umpIn;

	}

	private Move checkForWinningMove(Game.Player p, Character[][] board) {
		Character[][] newBoard;
		Character currChar = (p == Player.X ? 'X' : 'O');
	
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				newBoard = copyBoard(board);
				if (newBoard[row][col] == ' ') {
					newBoard[row][col] = currChar;

					if (ump.checkForVictory(p, newBoard)) {

						System.out.println("potential victory for " + currChar);
						return new MoveImpl(p, row, col);
					}
				}
			}
		}
		return null;

	}

	@Override
	public Move getMove(Game.Player turn, Character[][] board) {
		Move m = checkForWinningMove(turn, board);
		if (m != null)
			return m;

		Player opponent = null;
		if (turn == Game.Player.X) opponent = Player.O;
		else opponent = Player.X;

		m = checkForWinningMove(opponent, board);
		if (m != null) return new MoveImpl(turn, m.getRow(), m.getCol());

		// occupy center square
		if (board[1][1] == ' ')
			return new MoveImpl(turn, 1, 1);

		// occupy corner squares
		if (board[0][0] == ' ')
			return new MoveImpl(turn, 0, 0);
		if (board[0][2] == ' ')
			return new MoveImpl(turn, 0, 2);
		if (board[2][0] == ' ')
			return new MoveImpl(turn, 2, 0);
		if (board[2][2] == ' ')
			return new MoveImpl(turn, 2, 2);

		// occupy whatever is left
		if (board[0][1] == ' ')
			return new MoveImpl(turn, 0, 1);
		if (board[1][0] == ' ')
			return new MoveImpl(turn, 1, 0);
		if (board[1][2] == ' ')
			return new MoveImpl(turn, 1, 2);
		else
			return new MoveImpl(turn, 2, 1);
	}

	private Character[][] copyBoard(Character[][] board) {
		int numRows = board.length;
		int numCols = board[0].length;
		Character[][] newBoard = new Character[numRows][numCols];
		for (int row = 0; row < numRows; row++)
			for (int col = 0; col < numCols; col++)
				newBoard[row][col] = new Character(board[row][col]);
		return newBoard;
	}

}