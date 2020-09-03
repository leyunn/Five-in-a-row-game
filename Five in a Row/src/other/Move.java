package other;

/*
 * This is a class responsible of storing both x coordinate and y coordinate in one object
 * */

public class Move {
	private int x;
	private int y;

	public Move(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	//setter in not required because in this game stone can't change position
}
