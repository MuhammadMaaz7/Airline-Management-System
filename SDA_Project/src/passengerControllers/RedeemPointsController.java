package passengerControllers;


import java.io.IOException;

import classes.passenger;
import classes.UpgradeOption;
import classes.loyaltyProgram;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class RedeemPointsController {

	  @FXML
	    private TableView<UpgradeOption> upgradeOptionsTable;

	    @FXML
	    private TableColumn<UpgradeOption, String> optionColumn;

	    @FXML
	    private TableColumn<UpgradeOption, Integer> pointsRequiredColumn;

	    @FXML
	    private Button upgradeButton;
	    
	    private loyaltyProgram currentPassenger;
	    
	    @FXML
	    private MenuItem menuItemGoToMainPage;
	    @FXML
	    private MenuItem menuItemAddNewFlight;
	    @FXML
	    private MenuItem menuItemUpdateFlightSchedule;
	    @FXML
	    private MenuItem menuItemCancelFlight;
	    @FXML
	    private MenuItem menuItemViewPassengerList;
	    @FXML
	    private MenuItem menuItemViewPassengerList1;
	    @FXML
	    private MenuItem menuItemGoToPassengerHomeScreen;
	    

	    @FXML
	    private BorderPane rootPane; // AnchorPane from your FXML


	    
	    private int passengerid;

	    public void setcurrentPassenger(int passengerid) {
	    
	    	currentPassenger = loyaltyProgram.getPassengerDetails(passengerid);
	    	this.passengerid=passengerid;
	    }
	    
    @FXML
    public void initialize() {
    	      menuItemGoToMainPage.setOnAction(event -> goToMainPage());
    	     menuItemGoToPassengerHomeScreen.setOnAction(event -> goToPassengerHomeScreen());
    	    menuItemAddNewFlight.setOnAction(event -> bookTicket());
    	   menuItemUpdateFlightSchedule.setOnAction(event -> checkIn());
    	menuItemCancelFlight.setOnAction(event -> claimPoints());
    	menuItemViewPassengerList.setOnAction(event -> redeemPoints());
    	menuItemViewPassengerList1.setOnAction(event -> viewFlightHistory());
    	currentPassenger= loyaltyProgram.getPassengerDetails(passengerid);
    	 optionColumn.setCellValueFactory(new PropertyValueFactory<>("option"));
         pointsRequiredColumn.setCellValueFactory(new PropertyValueFactory<>("pointsRequired"));

         // Populate the table with data
         ObservableList<UpgradeOption> upgradeOptions = FXCollections.observableArrayList(
                 new UpgradeOption("Upgrade to Business Class", 2000),
                 new UpgradeOption("Lounge Access", 500),
                 new UpgradeOption("Extra Baggage Allowance", 300)
         );

         upgradeOptionsTable.setItems(upgradeOptions);
         upgradeButton.setOnAction(event -> handleRedeemPoints());
    }

    @FXML
    private void handleRedeemPoints() {
    	 UpgradeOption selectedOption = upgradeOptionsTable.getSelectionModel().getSelectedItem();

         if (selectedOption != null) {
             int pointsRequired = selectedOption.getPointsRequired();
             if (currentPassenger.getPointsEarned() >= pointsRequired) {
                 int newPoints = currentPassenger.getPointsEarned() - pointsRequired;
                 currentPassenger.setPointsEarned(newPoints);
                 // Update passenger points in the data source

                 showAlert("Redeem Points", "Success", "Points redeemed for " + selectedOption.getOption() +
                         ". Points Required: " + pointsRequired + ". Remaining Points: " + newPoints);
             } else {
                 showAlert("Redeem Points", "Error", "Insufficient points for this upgrade option.");
             }
         } else {
             showAlert("Redeem Points", "Error", "Please select an upgrade option.");
         }
     }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @FXML
    private void goToMainPage() {
        switchToAnyScreen("/managerViews/mainPage.fxml");
    }
    
    @FXML
    private void goToPassengerHomeScreen() {
        switchToAnyScreen("/passengerViews/passengerHomeScreen.fxml");
    }

    @FXML
    private void bookTicket() {
        switchToAnyScreen("/passengerViews/searchFlights.fxml");
    }

    @FXML
    private void checkIn() {
        switchToAnyScreen("/passengerViews/checkIn.fxml");
    }

     @FXML
	    private void claimPoints() {
	        switchToAnyScreen("/passengerViews/ClaimPoints.fxml");
	    }

	    @FXML
	    private void redeemPoints() {
	        switchToAnyScreen("/passengerViews/RedeemPoints.fxml");
	    }

	    @FXML
	    private void viewFlightHistory() {
	        switchToAnyScreen("/passengerViews/LoyaltyProgramHomeScreen.fxml");
	    }

    private void switchToAnyScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading screen: " + fxmlPath);
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.name());
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}