package application;
import other.Move;
import other.popOutWindow;

/*
 * This class is responsible for all the logic of the game, and it keep track of the state of the game.
 * Other than the physical board that appears in the UI, a virtual game board is used in this class,
 * so the algorithm for checking if there is five in a row can run on it.
 * 
 * */

public class GameCore {
	
	//define two stone types that could be on the board
	public enum stoneType{
		BLACK,WHITE;
	}
	
	//define four type of game states each with a winning message and a stone type
	public enum gameState{
		BLACK_TURN("black win!", stoneType.BLACK),
		WHITE_TURN("white win!", stoneType.WHITE),
		DRAW("It's a draw.", null),
		GAMEOVER("", null);
		
		private String word;
		private stoneType stone;
		
		gameState(String word, stoneType stone){
			this.word = word;
			this.stone = stone;
		}
		
		public String getWord() {
			return word;
		}
		
		public stoneType getStone() {
			return stone;
		}
	}
	
	//count the numbers of move
	private int moveCount;
	// the current game state
	private gameState state;
	//store every move in a (x,y) format array
	private Move[] moves;
	//store the whole board in a 2D array, keep track of the state of each grid
	private stoneType[][] boardArray;
	public static final int BOARDSIZE = 15;
		
	
	//initialize all the game details
	GameCore(){
		moveCount = 0;
		state = gameState.BLACK_TURN;
		boardArray = new stoneType[BOARDSIZE][BOARDSIZE];
		moves = new Move[BOARDSIZE * BOARDSIZE];
	}
	
	//putting the stone on the virtual board (the boardArray)
	//for every move, check for winning or draw condition
	protected void makeMove(int x, int y) {
//		System.out.println("moveCount makeMove: " + moveCount);
		
		//store the new move
		moves[moveCount] = new Move(x, y);
		moveCount++;
		boardArray[x-1][y-1] = state.getStone();
		
		
		//check if any player won
		if(checkWin(x-1, y-1, state.getStone()) == true) {
			gameOver();
		//check if the whole board is filled
		}else if(moveCount == moves.length){
			state = gameState.DRAW;
			gameOver();
		}else{
			switchTurn();
		}
	}
	
	
	//an algorithm to determine whether there is five in a row on the board
	public boolean checkWin(int x, int y, stoneType color) {
		int count = 0;
		// use a number to represent different direction check
		
		/*
		 * 1: vertical check
		 * 2: horizontal check
		 * 3: bottom left to top right check
		 * 4: top left to bottom right check
		 **/
		
		for(int direction = 1; direction < 5; direction ++){

			count = 0;
			int xShift = 0;
			int yShift = 0;
			
			switch(direction){
			//vertical
			case 1: 
				xShift = 0;
				yShift = 1;
				break;
			//horizontal
			case 2: 
				xShift = 1;
				yShift = 0;
				break;
			//bottom left to top right
			case 3:
				xShift = 1;
				yShift = 1;
				break;
			//top left to bottom right
			case 4:
					xShift = 1;
					yShift = -1;
				break;
			}
		//run through the linear girds
		for(int i = -4; i<5; i++){
			
			try {
				if(boardArray[x + (xShift*i)][y + (yShift*i)] == color){
					count++;
				}else{
					//reach a grid with on stone, the count reset
					count = 0;
				}
			}catch(ArrayIndexOutOfBoundsException e) {
				//outside of the board, same situation as grid with no stone
				count = 0;
			}
			if(count>=5) {
//				System.out.println("win");
				
				//reach five in a row
				return true;
			}
		}
			//go to the next direction check

		}
		//no five in a row
				return false;


	}
	
	public void gameOver() {
		String endMessage = state.getWord();
		state = gameState.GAMEOVER;
		//pop out win message according to the state
		popOutWindow.display(endMessage);
	}
	
	public void switchTurn() {
		if(moveCount % 2 == 0) {
				state = gameState.BLACK_TURN;
//				System.out.println("switch to black turn"); 
		}else {
				state = gameState.WHITE_TURN;
//				System.out.println("switch to white turn");
		}
	}
	
	//removing the stone from the virtual board (the boardArray)
	public void undoMove() {
//		System.out.println("moveCount before undo: " + moveCount);
		moveCount--;
		//get position of the move we want to undo from the positions array
		int x = moves[moveCount].getX();
		int y = moves[moveCount].getY();
		//remove them from the virtual board
		boardArray[x-1][y-1] = null;
		moves[moveCount] = null;
		switchTurn();
	}
	
	//check if a particular gird on the board is empty
	public boolean isSpotEmpty(int x, int y) {
		if(boardArray[x-1][y-1] == null) {
			return true;
		}else {
			return false;
		}
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public gameState getState() {
		return state;
	}
}
