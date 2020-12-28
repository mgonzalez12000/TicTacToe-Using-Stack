package hw3;

import java.util.Scanner;
import java.util.Stack;

public class Game {
	public enum Player {
		X, O
	};

	private Scanner sc = new Scanner(System.in);
	private Player player;
	private Player turn;
	private Character[][] board;
	private Umpire ump = new Umpire();
	private TicTacToeSolver autoPlayer = new SolverImpl(ump);
	//You will need two stacks of Moves, one for moves that have been made and one for moves that have been undone.
	//The objects that go on the stacks will be MoveImpls, but use the interface (NOTE: The interface is Move) as the parameterizing type for the stacks. 

	/* NOTE: I was confused of how many stacks we needed because of the instructions on Canvas and the comment
	   shown bellow differed. Please consider updating the instructions for future classes */

	// declare and instantiate undo and redo stacks here	

	//Created movesMade stack per instructions on Canvas
	Stack <Move> movesMade = new Stack<Move>();
	//Created movesUndone stack per instructions on Canvas
	Stack <Move> movesUndone = new Stack<Move>();

	public static void main(String[] args) {
		Game g = new Game(Player.X);
		g.play();
	}

	public Game(Player playerIn) {
		player = playerIn;
		turn = Player.X;

		board = new Character[3][3];
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 3; col++) {
				board[row][col] = ' ';
			}
	}

	public void play() {
		//  this method will need revision in the lab
		while (!ump.gameOver(board)) {
			Move m = null;
			if (turn == Player.X) {
				while (m == null) {
					displayBoard();
					int option = getPlayerOption();
					switch (option) {
					case 0:
						System.exit(0);
						break;
					case 1:
						m = playerMove();
						break;
					case 2:
						undo();
						break;
					case 3:
						redo();
						break;
					}

				}
			} else {
				m = autoMove();
			}
			updateBoard(m);
			switchTurns();
			//add it to the stack of movesMade
			movesMade.push(m);
		}
		displayBoard();
		if(ump.isStalemated(board)) System.out.println("Stalemate!");
		if(ump.checkForVictory(Player.X, board)) System.out.println("X Wins!");
		if(ump.checkForVictory(Player.O, board)) System.out.println("O Wins!");

	}

	private int getPlayerOption() {
		System.out.println("Enter 0 to quit game, 1 to make a move, 2 to undo last move, 3 to redo last undone move\n");
		return sc.nextInt();
	}

	//Replaces user and comp moves but does NOT undo, therefore, consider doing a new method
	private void updateBoard(Move m) {
		board[m.getRow()][m.getCol()] = m.getPlayer().toString().charAt(0);
	}

	//Create a new method that updates board by undoing move. Method returns an empty space
	//Used updateBoard(), as shown above, as a guide
	public void undoMoveOnBoard (Move m) {
		board[m.getRow()][m.getCol()] = ' ';
	}

	private void displayBoard() {
		System.out.println();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				System.out.print(" " + board[row][col] + " ");
				if (col < 2)
					System.out.print("|");
			}
			if (row < 2)
				System.out.println("\n___|___|___");
			else
				System.out.println("\n   |   |   ");
		}
		System.out.println();
	}

	public void remove() {

	}

	public Move autoMove() {
		Move m = autoPlayer.getMove(turn, board);
		return m;
	}

	public Move playerMove() {
		int row = 0, col = 0;
		System.out.println("\nEnter row, then a blank, then column, eg 1 2 for row 1, col 2");
		if (sc.hasNextInt()) {
			row = sc.nextInt();
			col = sc.nextInt();
		}

		//Every time a move is made, create a MoveImpl
		Move m = new MoveImpl(player, row, col);
		return m;

	}

	public void undo() {
		/* implement this method and delete the throws declaration in the method signature as well as the line that throws the exception*/

		/* When the user chooses to undo a move, pop the top *two* moves from the moves made stack 
		(because both a user move and an automatic move will be there) */
		Move user = movesMade.pop();
		Move comp = movesMade.pop();

		//Update the board and undo the moves for both comp and user
		undoMoveOnBoard(user);
		undoMoveOnBoard(comp);

		//and push the moves onto the stack of moves that have been undone. 
		movesUndone.push(user);
		movesUndone.push(comp);

	}

	public Move redo() {
		/* implement this method and delete the throws declaration in the method signature as well as the line that throws the exception*/

		// When the user chooses to redo a move, pop the two top moves from the undone stack
		//Create variables for user and comp to pop both respectively 
		Move user = movesUndone.pop();
		Move comp = movesUndone.pop();

		// Update the board  
		updateBoard(user);
		updateBoard(comp);

		// And push the moves onto the movesMade stack
		movesMade.push(user);
		movesMade.push(comp);

		return comp;

	}

	private void switchTurns() {
		if (turn == Player.X)
			turn = Player.O;
		else
			turn = Player.X;
	}

	private class GameException extends Exception{

		public GameException(String msg) {
			super(msg);
		}
	}
}




