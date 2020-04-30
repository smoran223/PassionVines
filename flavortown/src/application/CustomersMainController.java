package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CustomersMainController {
	
	@FXML private ListView<HBox> scrollPaneListView;
	
	public void initialize() {
		display();
	}
	
	public void returnToMain(ActionEvent event) throws Exception{
		Parent mainParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene mainScene = new Scene(mainParent,700,900);
		mainScene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public void details(ActionEvent event, String str) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("CustomersDetails.fxml"));
		Parent mainParent = loader.load();
		
		CustomersDetailsController controller = loader.getController();
		controller.init(str);

		Scene mainScene = new Scene(mainParent,700,900);
		mainScene.getStylesheets().add(getClass().getResource("CustomersDetails.css").toExternalForm());
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public void display() {
		try{
			// create a mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://localhost:3306/passion";
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
			scrollPaneListView.getItems().clear();
			PreparedStatement pre = conn.prepareStatement("SELECT id, name, email, number from customer");
			ResultSet rs = (pre.executeQuery());
			while(rs.next()) {
				HBox temp = new HBox();
				temp.getChildren().addAll(new Label(rs.getString("name")));
				temp.getChildren().addAll(new Label(rs.getString("email")));
				temp.getChildren().addAll(new Label(rs.getString("number")));
				String str1 = (rs.getString("id"));
				temp.setOnMouseClicked(e -> {
					try {
						details(new ActionEvent(temp, null), str1);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
				scrollPaneListView.getItems().add(temp);
			}
			
			conn.close();
			
		}
		catch (Exception e){
			System.err.println("Got an exception!");
			System.err.println(e.getMessage());
		}
		
	}
	
}