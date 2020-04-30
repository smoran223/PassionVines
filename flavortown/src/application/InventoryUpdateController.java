 package application;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;

public class InventoryUpdateController {

	@FXML
	private ChoiceBox<String> typeScroll;
	@FXML
	private TextField nameField;
	@FXML
	private TextField priceField;
	@FXML
	private TextField varietalField;
	@FXML
	private TextField regionField;
	@FXML
	private TextField vintageField;
	@FXML
	private TextArea descriptionField;
	@FXML
	private ChoiceBox<String> typeScroll1;
	@FXML
	private ChoiceBox<String> itemScroll;
	@FXML
	private TextField numField;
	
	public void initialize() {
		typeScroll.setItems(FXCollections.observableArrayList("Wine","Beer","Spirits","Food"));
		typeScroll.setValue("Wine");
		typeScroll1.setItems(FXCollections.observableArrayList("Wine","Beer","Spirits","Food"));
		typeScroll1.setValue("Wine");
		popList();
	}

	public void openInventoryMain(ActionEvent event) throws Exception{
		Parent inventoryParent = FXMLLoader.load(getClass().getResource("InventoryMain.fxml"));
		Scene inventoryScene = new Scene(inventoryParent,700,900);
		inventoryScene.getStylesheets().add(getClass().getResource("inventoryMain.css").toExternalForm());
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
	
	public void popList(){
		itemScroll.getItems().clear();
		try {
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://localhost:3306/passion";
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
			String query = "SELECT name from product where type = '" + typeScroll1.getValue().toLowerCase() + "'";
			PreparedStatement pre = conn.prepareStatement(query);
			ResultSet rs = (pre.executeQuery());
			while(rs.next()) {
				itemScroll.getItems().addAll(rs.getString(1));
			}
		}
		catch(Exception e) {
		}
	}
	
	public void updateQuantity() {
		try{
			String type = typeScroll1.getValue();
			String item = itemScroll.getValue();
			int num = Integer.parseInt(numField.getText());
			// create a mysql database connection
			String myDriver = "com.mysql.jdbc.Driver";
			String myUrl = "jdbc:mysql://localhost:3306/passion";
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
			String query ="SELECT stock from product where type = '" + type + "' && name = '" + item + "'";
			PreparedStatement pre = conn.prepareStatement(query);
			ResultSet rs = (pre.executeQuery());
			int stock = 0;
			while(rs.next()) {stock = rs.getInt(1);}
			stock=stock+num;
			System.out.println(stock);
			query = "update product set stock = " + stock + " where type = '" + type + "' && name = '" + item + "'";
			PreparedStatement pst = conn.prepareStatement(query);
			pst.executeUpdate();

			conn.close();
		}	
		catch (Exception e){
			e.printStackTrace();
		}
		numField.clear();
	}
	
	public void addToDataBase() throws ClassNotFoundException, SQLException {
		String type = typeScroll.getValue();
		String name = nameField.getText();
		float price = Float.parseFloat(priceField.getText());
		String desc = descriptionField.getText();
		String varietal = "";
		String region = "";
		int vintage = 0;
		if(!type.equals("Spirits")) {
			varietal = varietalField.getText();
			if(!type.equals("Food")) {
				region = regionField.getText();
				if(type.equals("wine")) {
					vintage = Integer.parseInt(vintageField.getText());
				}
			}
		}
		
		// create a mysql database connection
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost:3306/passion";
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
		PreparedStatement pre = conn.prepareStatement("SELECT COUNT(*) from product");
		ResultSet rs = (pre.executeQuery());
		
		int id = 0;
		while(rs.next()) {id = rs.getInt(1) + 1;}
		
		String query;
		query = "INSERT into `product` VALUES(" + id + ",'" + type.toLowerCase() + "','" + name + "','" + price + "','" + varietal + "','" + region + "','" + vintage + "','" + desc + "','0')";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.executeUpdate();
		conn.close();
		
		nameField.clear();
		priceField.clear();
		varietalField.clear();
		regionField.clear();
		vintageField.clear();
		descriptionField.clear();
		
	}
}
