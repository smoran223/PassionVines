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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CustomersDetailsController {
	
	@FXML private Label idLabel;
	@FXML private Label nameLabel;
	@FXML private Label emailLabel;
	@FXML private Label phoneLabel;
	
	@FXML private TextField nameField;
	@FXML private TextField emailField;
	@FXML private TextField numberField;
	
	public void init(String id) throws ClassNotFoundException, SQLException {
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost:3306/passion";
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
		String query;
		query = "SELECT name, email, number from customer where id = '" + id + "'";
		PreparedStatement pre = conn.prepareStatement(query);
		ResultSet rs = (pre.executeQuery());
		rs.next();
		idLabel.setText(id);
		nameLabel.setText(rs.getString("name"));
		emailLabel.setText(rs.getString("email"));
		phoneLabel.setText(rs.getString("number"));
	}
	
	public void updateName(ActionEvent event) throws ClassNotFoundException, SQLException {
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost:3306/passion";
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
		String query;
		query = "UPDATE customer set name = '" + nameField.getText() + "' where id = " + idLabel.getText();
		PreparedStatement pre = conn.prepareStatement(query);
		pre.execute(query);
		nameLabel.setText(nameField.getText());
		nameField.clear();
	}
	public void updateEmail(ActionEvent event) throws ClassNotFoundException, SQLException {
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost:3306/passion";
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
		String query;
		query = "UPDATE customer set email = '" + emailField.getText() + "' where id = " + idLabel.getText();
		PreparedStatement pre = conn.prepareStatement(query);
		pre.execute(query);
		emailLabel.setText(emailField.getText());
		emailField.clear();
	}
	public void updatePhone(ActionEvent event) throws ClassNotFoundException, SQLException {
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost:3306/passion";
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
		String query;
		query = "UPDATE customer set number = '" + numberField.getText() + "' where id = " + idLabel.getText();
		PreparedStatement pre = conn.prepareStatement(query);
		pre.execute(query);
		phoneLabel.setText(numberField.getText());
		numberField.clear();
	}
	
	public void delete(ActionEvent event) throws ClassNotFoundException, SQLException {
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost:3306/passion";
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
		String query;
		query = "DELETE FROM customer WHERE email=" + emailLabel.getText();
		PreparedStatement pre = conn.prepareStatement(query);
		pre.executeQuery(query);
	}

	public void returnToMain(ActionEvent event) throws Exception{
		Parent mainParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene mainScene = new Scene(mainParent,700,900);
		mainScene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public void openCustomersMain(ActionEvent event) throws Exception{
		Parent inventoryParent = FXMLLoader.load(getClass().getResource("CustomersMain.fxml"));
		Scene inventoryScene = new Scene(inventoryParent,700,900);
		inventoryScene.getStylesheets().add(getClass().getResource("CustomersMain.css").toExternalForm());
		Stage inventoryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		inventoryStage.setScene(inventoryScene);
		inventoryStage.show();
		
	}
}
