package hw3;

public class MoveImpl implements Move {
	Game.Player player;
	int row;
	int col;
	
	public MoveImpl(Game.Player playerIn, int rowIn, int colIn){
		player = playerIn;
		row = rowIn;
		col = colIn;
	}
	
	@Override
	public int getRow(){
		return row;
	}
	
	@Override
	public int getCol(){
		return col;		
	}
	
	
	@Override
	public Game.Player getPlayer(){
		return player;		
	}
}
