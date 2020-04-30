package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root,700,900);
		scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
		primaryStage.setTitle("Flavortown");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}