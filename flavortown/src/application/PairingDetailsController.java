package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

public class PairingDetailsController {

	@FXML
	private Label nameLabel;
	@FXML
	private Label priceLabel;
	@FXML 
	private Label idLabel;
	@FXML 
	private Label stockLabel;
	@FXML 
	private Label descriptionLabel; 
	@FXML 
	private ListView<HBox> scrollPaneListView;

	private String id;
	private String varietal;
	private String type;
	
	public void initData(String str) throws ClassNotFoundException, SQLException {
		id = str;
		// create a mysql database connection
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost:3306/passion";
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
		
		//establishes String query
		String query;query = "SELECT type, name, price, stock, varietal, description from product where id=" + id + "";
		PreparedStatement pre = conn.prepareStatement(query);
		ResultSet rs = (pre.executeQuery());
		
		//sets label values
		rs.next();
		idLabel.setText("ID: " + id);
		type = rs.getString("type");
		nameLabel.setText(rs.getString("name") + " " + rs.getString("varietal"));
		priceLabel.setText("$" + rs.getString("price"));
		stockLabel.setText("Stock: " + rs.getString("stock"));
		varietal = rs.getString("varietal");
		descriptionLabel.setText(rs.getString("description"));
		descriptionLabel.setWrapText(true);
		conn.close();
	}
	
	public void details(ActionEvent event, String str) throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("PairingDetails.fxml"));
		Parent mainParent = loader.load();
		
		PairingDetailsController controller = loader.getController();
		controller.initData(str);
		
		Scene mainScene = new Scene(mainParent,700,900);
		mainScene.getStylesheets().add(getClass().getResource("PairingDetails.css").toExternalForm());
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public void display(){
		try {
		// create a mysql database connection
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost:3306/passion";
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
		scrollPaneListView.getItems().clear();
		String query = "SELECT * from product where (type = 'wine' || type = 'food') AND type != '" + type + "'";
		if(type.equals("food")) {
			if(varietal.equals("Cheese")) {
				query = query + " AND (varietal = 'Chardonnay' || varietal = 'Sauvignon Blanc')";
			}
			else {
				query = query + " AND (varietal = 'Pinot Noir' || varietal = 'Cabernet')";
			}
		}
		else {
			if(varietal.equals("Chardonnay") || varietal.equals("Sauvignon Blanc")) {
				query = query + " AND varietal = 'Cheese'";
			}
			else {
				query = query + " AND varietal = 'Chocolate'";
			}
		}
		PreparedStatement pre = conn.prepareStatement(query);
		ResultSet rs = (pre.executeQuery());
		while(rs.next()) {
			HBox temp = new HBox();
			temp.getChildren().addAll(new Label(rs.getString("name")));
			temp.getChildren().addAll(new Label(rs.getString("price")));
			String str1 = (rs.getString("id"));
			temp.setOnMouseClicked(e -> {
				try {details(new ActionEvent(temp, null), str1);} 
				catch (Exception e1) {}});
			scrollPaneListView.getItems().add(temp);
		}

		conn.close();
		}
		catch(Exception e) {
		}
	}
	
	public void returnToMain(ActionEvent event) throws Exception{
		Parent mainParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene mainScene = new Scene(mainParent,700,900);
		mainScene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public void openPairingMain(ActionEvent event) throws Exception{
		Parent inventoryParent = FXMLLoader.load(getClass().getResource("PairingMain.fxml"));
		Scene inventoryScene = new Scene(inventoryParent,700,900);
		inventoryScene.getStylesheets().add(getClass().getResource("PairingMain.css").toExternalForm());
		Stage inventoryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		inventoryStage.setScene(inventoryScene);
		inventoryStage.show();
		
	}
}
