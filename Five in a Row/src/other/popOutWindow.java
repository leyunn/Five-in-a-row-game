package other;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class popOutWindow {
	
	
	
	//display a window with the given message
	
	public static void display(String message) {
		Stage stage = new Stage();
		//user can't interact with other window until they close this
		stage.initModality(Modality.APPLICATION_MODAL);
//		stage.setMinWidth(250);
		
		Label label = new Label(message);
		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> stage.close());
		
		VBox layout = new VBox(20);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout, 190, 100);
		stage.setScene(scene);
		stage.showAndWait();
		
	}
	
}
