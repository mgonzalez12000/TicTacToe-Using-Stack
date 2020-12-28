package hw3;

public class Umpire {

	public boolean gameOver(Character[][] board) {
		if (checkForVictory(Game.Player.X, board))
			return true;
		if (checkForVictory(Game.Player.O, board))
			return true;
		if (isStalemated(board))
			return true;
		return false;
	}

	public boolean checkForVictory(Game.Player p, Character[][] board) {
		
		if (rowVictory(p, board))
			return true;
		if (colVictory(p, board))
			return true;
		if (diagonalVictory(p, board))
			return true;
		return false;
	}

	private boolean rowVictory(Game.Player p, Character[][] board) {
		char curr = String.valueOf(p).charAt(0);
		for (int row = 0; row < 3; row++) {
			if (curr == board[row][0] && curr == board[row][1] && curr == board[row][2]) {
				return true;
			}
			
		}
		return false;
	}

	private boolean colVictory(Game.Player p, Character[][] board) {
		char curr = String.valueOf(p).charAt(0);
		for (int col = 0; col < 3; col++) {
			if (curr == board[0][col] && curr == board[1][col] && curr == board[2][col]) {
				return true;
			}
		}
		return false;
	}

	private boolean diagonalVictory(Game.Player p, Character[][] board) {
		char curr = String.valueOf(p).charAt(0);
		if (board[0][0] == curr && board[1][1] == curr & board[2][2] == curr) {
			return true;
		}
		if (board[0][2] == curr && board[1][1] == curr & board[2][0] == curr) {

			return true;
		}

		return false;
	}

	public boolean isStalemated(Character[][] board) {
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++)
				if (board[row][col] == ' ')
					return false;
		return true;
	}

}

