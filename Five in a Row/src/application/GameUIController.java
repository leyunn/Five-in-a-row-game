package application;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

import application.GameCore.gameState;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import other.Move;

/*
 * This is a class responsible for connecting the UI to the game
 * All the operation of the UI is done in this class.
 * 
 * */

public class GameUIController implements Initializable {
	
	//define all the nodes on the GUI
	@FXML private Canvas boardCanvas;
    @FXML private Button undoButton;
    @FXML private Button restartButton;
    @FXML private Pane pane;
    @FXML private Rectangle blackRectangle;
    @FXML private Rectangle whiteRectangle;
    
    private GameCore gameCore;
    private static double stoneSize;
    private static int gridSize;
    
    private Color blackStoneColor = Color.valueOf("#303539");
    private Color whiteStoneColor = Color.valueOf("#eeeeee");
    
    // initialize the UI details, called when the program start running
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gridSize = (int) boardCanvas.getHeight()/(GameCore.BOARDSIZE - 1);
		stoneSize = gridSize * 0.36;
		drawGridLine();
		start();
	}
	
	//details that have to be reset for a new game
	public void start() {
		gameCore = new GameCore();
		changeFrameWidth(gameCore.getState());
		double boardRightBound = boardCanvas.getLayoutX() + boardCanvas.getHeight() + 50;
		//remove all the circle nodes on the board
		pane.getChildren().removeIf(n -> n instanceof Circle && n.getLayoutX() < boardRightBound);
	}
	
	@FXML private void handleUndoButtonClicked() {
		if(gameCore.getMoveCount() != 0) {
			gameCore.undoMove();
			removeRecentStone();
			changeFrameWidth(gameCore.getState());
		}else {
			//no stone on the board
		}
	}
	
	@FXML private void handleRestartButtonClicked() {
		start();
	}
	
	@FXML private void handleBoardClicked(MouseEvent click){
		//get the x, y coordinate
		Move position = findSpot(click.getX(), click.getY());
		int x = position.getX();
		int y = position.getY();
//		System.out.println(x + " : "+ y);
		//check if this is a valid move, game is ongoing and the spot is available 
		if(gameCore.isSpotEmpty(x,y)&& gameCore.getState()!=gameState.GAMEOVER) {
			//make move on both the physical board and the virtual board
			addStone(x,y);
			gameCore.makeMove(x, y);
			changeFrameWidth(gameCore.getState());
		}
		
	}
   
	private void drawGridLine(){
		GraphicsContext gc = boardCanvas.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		
		for(int i = 1; i < (GameCore.BOARDSIZE - 1);i++) {
			gc.strokeLine((i * gridSize), 0, (i * gridSize), (int)boardCanvas.getHeight());
			gc.strokeLine(0, (i * gridSize), (int)boardCanvas.getHeight(), (i * gridSize));
		}	
	}
	
	//add circle on the physical board on the location specified
	private void addStone(int xGridNumber, int yGridNumber) {
		//convert x, y coordinate to actual position
		int x = (int)boardCanvas.getLayoutX() + ((xGridNumber - 1) * gridSize);
		int y = (int)boardCanvas.getLayoutY() + ((GameCore.BOARDSIZE - yGridNumber) * gridSize);
		Color color;
		//get the stone color
		switch(gameCore.getState()) {
		case BLACK_TURN: color = blackStoneColor;
			break;
		case WHITE_TURN: color = whiteStoneColor;
			break;
		default: color = null;
			break;
		}
		//create a stone with the specified color
		Circle stone = new Circle(x, y, stoneSize);
		stone.setFill(color);
		//add it to the UI
		pane.getChildren().add(stone);
		
		
	}
	
	//remove the most recently added node
	private void removeRecentStone() {
		pane.getChildren().remove(pane.getChildren().size() -1);
		//the method is only called when move count is not zero, 
		//so no need to make sure it won't remove other nodes
	}
	
	//locate the nearest grid of the mouse click
	Move findSpot(double MouseX, double MouseY) {
//		System.out.println(MouseX +" : " + MouseY);
		int x = (int)Math.round(MouseX/gridSize) + 1;
		int y = (GameCore.BOARDSIZE - (int)Math.round(MouseY/gridSize));
		return new Move(x,y);
	}
	
	//change the frame width of the player block as a turn indicator
	public void changeFrameWidth(gameState state){
		switch(state) {
		case BLACK_TURN: 
			blackRectangle.setStrokeWidth(5);
			whiteRectangle.setStrokeWidth(1);
			break;
		case WHITE_TURN: 
			whiteRectangle.setStrokeWidth(5);
			blackRectangle.setStrokeWidth(1);
			break;
		default: 
			
			break;
		}
	}
}















