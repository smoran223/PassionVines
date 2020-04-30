package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {
	
	public void openPairingMain(ActionEvent event) throws Exception{
		Parent pairingParent = FXMLLoader.load(getClass().getResource("PairingMain.fxml"));
		Scene pairingScene = new Scene(pairingParent,700,900);
		pairingScene.getStylesheets().add(getClass().getResource("PairingMain.css").toExternalForm());
		Stage pairingStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		pairingStage.setScene(pairingScene);
		pairingStage.show();
	}
	
	public void openCustomerMain(ActionEvent event) throws Exception{
		Parent customerParent = FXMLLoader.load(getClass().getResource("CustomersMain.fxml"));
		Scene customerScene = new Scene(customerParent,700,900);
		customerScene.getStylesheets().add(getClass().getResource("CustomersMain.css").toExternalForm());
		Stage customerStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		customerStage.setScene(customerScene);
		customerStage.show();
	}
	
	public void openInventoryMain(ActionEvent event) throws Exception{
		Parent inventoryParent = FXMLLoader.load(getClass().getResource("InventoryMain.fxml"));
		Scene inventoryScene = new Scene(inventoryParent,700,900);
		inventoryScene.getStylesheets().add(getClass().getResource("InventoryMain.css").toExternalForm());
		Stage inventoryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		inventoryStage.setScene(inventoryScene);		
		inventoryStage.show();
	}
	
	public void exit() {
		System.exit(0);
	}
	
}
