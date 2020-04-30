 package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;

public class CustomersUpdateController {

	@FXML
	private TextField nameField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField numberField;

	public void openCustomersMain(ActionEvent event) throws Exception{
		Parent inventoryParent = FXMLLoader.load(getClass().getResource("CustomersMain.fxml"));
		Scene inventoryScene = new Scene(inventoryParent,700,900);
		inventoryScene.getStylesheets().add(getClass().getResource("CustomersMain.css").toExternalForm());
		Stage inventoryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		inventoryStage.setScene(inventoryScene);
		inventoryStage.show();
		
	}
	
	public void returnToMain(ActionEvent event) throws Exception{
		Parent mainParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene mainScene = new Scene(mainParent,700,900);
		mainScene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public void addToDataBase() throws ClassNotFoundException, SQLException {
		String name = nameField.getText();
		String email = emailField.getText();
		String number = numberField.getText();		
		// create a mysql database connection
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost:3306/passion";
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
		PreparedStatement pre = conn.prepareStatement("SELECT COUNT(*) from customer");
		ResultSet rs = (pre.executeQuery());
		int id = 0;
		while(rs.next()) {id = rs.getInt(1) + 1;}
		
		String query;
		query = "INSERT into `customer` VALUES(" + id + ",'" + name + "','" + email+ "','" + number + "')";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.executeUpdate();
		conn.close();
		
		nameField.clear();
		emailField.clear();
		numberField.clear();
		
	}
}
