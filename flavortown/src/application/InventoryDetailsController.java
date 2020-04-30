package application;

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
import javafx.stage.Stage;

public class InventoryDetailsController {
	
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
	
	public void initData(String str) throws ClassNotFoundException, SQLException {
		// create a mysql database connection
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost:3306/passion";
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
		
		//establishes String query
		String query;query = "SELECT name, price, varietal, description, stock from product where id = " + str + "";
		PreparedStatement pre = conn.prepareStatement(query);
		ResultSet rs = (pre.executeQuery());
		
		//sets label values
		rs.next();
		idLabel.setText("ID: " + str);
		nameLabel.setText(rs.getString("name") + " " + rs.getString("varietal"));
		priceLabel.setText("$" + rs.getString("price"));
		stockLabel.setText("Stock: " + rs.getString("stock"));
		descriptionLabel.setText(rs.getString("description"));
		descriptionLabel.setWrapText(true);
		conn.close();
		
		
	}

	public void returnToMain(ActionEvent event) throws Exception{
		Parent mainParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene mainScene = new Scene(mainParent,700,900);
		mainScene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public void openInventoryMain(ActionEvent event) throws Exception{
		Parent inventoryParent = FXMLLoader.load(getClass().getResource("InventoryMain.fxml"));
		Scene inventoryScene = new Scene(inventoryParent,700,900);
		inventoryScene.getStylesheets().add(getClass().getResource("InventoryMain.css").toExternalForm());
		Stage inventoryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		inventoryStage.setScene(inventoryScene);
		inventoryStage.show();
	}
}
