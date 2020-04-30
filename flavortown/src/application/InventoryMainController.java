package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class InventoryMainController{
	
	@FXML private ListView<HBox> scrollPaneListView;
	//Sorting
	@FXML private ChoiceBox<String> sort;
	//Type filter
	@FXML private ChoiceBox<String> type;
	//Price filter
	@FXML private TextField minPrice;
	@FXML private TextField maxPrice;
	//Varietal filter
	@FXML private CheckBox cabernet;
	@FXML private CheckBox chardonnay;
	@FXML private CheckBox pinot;
	@FXML private CheckBox sauvignon;
	//Region filter
	@FXML private CheckBox cali;
	@FXML private CheckBox maine;
	@FXML private CheckBox newjersey;
	@FXML private CheckBox oregon;
	@FXML private CheckBox washington;
	//Vintage filter
	@FXML private TextField minVin;
	@FXML private TextField maxVin;
	
	public void initialize() throws ClassNotFoundException, SQLException{
		sort.setItems(FXCollections.observableArrayList("Default","Name Ascending","Name Descending","Price Ascending","Price Decending"));
		sort.setValue("Default");
		type.setItems(FXCollections.observableArrayList("Any","Wine","Beer","Spirits","Food"));
		type.setValue("Any");
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
	
	public void updateInventory(ActionEvent event) throws Exception{
		Parent mainParent = FXMLLoader.load(getClass().getResource("InventoryUpdate.fxml"));
		Scene mainScene = new Scene(mainParent,700,900);
		mainScene.getStylesheets().add(getClass().getResource("InventoryMain.css").toExternalForm());
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public void details(ActionEvent event, String str) throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("InventoryDetails.fxml"));
		Parent mainParent = loader.load();
		
		InventoryDetailsController controller = loader.getController();
		controller.initData(str);
		
		Scene mainScene = new Scene(mainParent,700,900);
		mainScene.getStylesheets().add(getClass().getResource("InventoryDetails.css").toExternalForm());
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		mainStage.setScene(mainScene);
		mainStage.show();
	}
	
	public void display() throws SQLException, ClassNotFoundException {
		// create a mysql database connection
		String myDriver = "com.mysql.jdbc.Driver";
		String myUrl = "jdbc:mysql://localhost:3306/passion";
		Class.forName(myDriver);
		Connection conn = DriverManager.getConnection(myUrl, "root", "mysql");
		scrollPaneListView.getItems().clear();
		String query = "SELECT * from product";
		/*The following section applies rules to the query statement in order
		 * to properly filter the results of the search*/
		//Checks for any filters
		if(!(minPrice.getText().equals("") && maxPrice.getText().equals("")
		&& !(cabernet.isSelected() || chardonnay.isSelected() || pinot.isSelected() || sauvignon.isSelected()) 
		&& !(cali.isSelected() || maine.isSelected() || newjersey.isSelected() || oregon.isSelected() || washington.isSelected())
		&& minVin.getText().equals("") && maxVin.getText().equals("")
		&& (type.getValue().equals("Any"))
		)) {
			query = query + " where ";
			//checks minPrice filter
			if(!minPrice.getText().equals("")) {
				query = query + "price > " + (Float.parseFloat(minPrice.getText()) - .01) + " AND ";
			}
			//checks maxPrice filter
			if(!maxPrice.getText().equals("")) {
				query = query + "price < " + (Float.parseFloat(maxPrice.getText()) + .01) + " AND "; 
			}
			//checks varietal filters
			if(cabernet.isSelected() ||  chardonnay.isSelected() || pinot.isSelected() || sauvignon.isSelected()) {
				CheckBox[] var = {cabernet, chardonnay, pinot, sauvignon};
				String str = "(";
				for(CheckBox x:var) {
					if(x.isSelected()) {
						str = str + "varietal = '" + x.getText() + "' OR ";}	
				}
				str = str.substring(0, str.length()-4) + ")";
				query = query + str + " AND ";
			}
			//checks region filters
			if(cali.isSelected() || maine.isSelected() || newjersey.isSelected() || oregon.isSelected() || washington.isSelected()) {
				CheckBox[] var = {cali, maine, newjersey, oregon, washington};
				String str = "(";
				for(CheckBox x:var) {
					if(x.isSelected()) {
						str = str + "region = '" + x.getText() + "' OR ";}
				}
				str = str.substring(0, str.length()-4) + ")";
				query = query + str + " AND ";
			}
			//checks minVintage filter
			if(!minVin.getText().equals("")) {
				query = query + "vintage > " + (Integer.parseInt(minVin.getText()) - 1) + " AND ";
			}
			//checks maxVintage filter
			if(!maxVin.getText().equals("")) {
				query = query + "vintage < " + (Integer.parseInt(maxVin.getText()) + 1) + " AND "; 
			}
			//checks type filter
			if(!type.getValue().equals("Any")) {
				query = query + "type = '" + type.getValue() + "' AND ";
			}
		}
		//removes " AND " at end of query when done filtering
		String and = " AND ";
		if(query.substring(query.length()-5, query.length()).equals(and)){
			query = query.substring(0,query.length()-5);
		}
		/*The following section applies rules to the query statement in order
		 * to properly sort the results of the search*/
		if(!sort.getValue().equals("Default")) {
			query = query + " ORDER BY ";
			if(sort.getValue().equals("Name Ascending") || sort.getValue().equals("Name Descending")) {
				query = query + "name ";
			}
			else {
				query = query + "price ";
			}
			String asc = sort.getValue().substring(sort.getValue().length()-9, sort.getValue().length());
			if(asc.equals("Ascending")) {
				query = query + "ASC";
			}
			else {
				query = query + "DESC";
			}
		}
		PreparedStatement pre = conn.prepareStatement(query);
		ResultSet rs = (pre.executeQuery());
		while(rs.next()) {
			HBox temp = new HBox();
			temp.getChildren().addAll(new Label(rs.getString("name") + " " + rs.getString("varietal")));
			temp.getChildren().addAll(new Label(rs.getString("price")));
			temp.getChildren().addAll(new Label(rs.getString("id")));
			String str1 = (rs.getString("id"));
			temp.setOnMouseClicked(e -> {
				try {details(new ActionEvent(temp, null), str1);} 
				catch (Exception e1) {}});
			scrollPaneListView.getItems().add(temp);
		}	
		conn.close();	
	}
}