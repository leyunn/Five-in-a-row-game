package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/*
 * This class is responsible for launching the GUI.
 * 
 * */

public class GameLauncher extends Application {
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		try {
			stage.setTitle("Five in a Row");
			Parent gameUI = FXMLLoader.load(getClass().getResource("GameGUI.fxml"));
			Scene gameScene = new Scene(gameUI,780,550);
	        stage.setScene(gameScene);
	        stage.show();
		} catch(Exception e) {
		}
	}
	
	
}
