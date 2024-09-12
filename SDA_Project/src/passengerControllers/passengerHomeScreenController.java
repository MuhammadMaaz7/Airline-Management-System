package passengerControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class passengerHomeScreenController {

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
    
    int passengerid;
    
    public void setpassenger(int passenger_id) {
    	passengerid=passenger_id;
    }

    @FXML
    private AnchorPane rootPane; // AnchorPane from your FXML

    @FXML
    public void initialize() {
    	menuItemGoToMainPage.setOnAction(event -> goToMainPage());
        menuItemGoToPassengerHomeScreen.setOnAction(event -> goToPassengerHomeScreen());
        menuItemAddNewFlight.setOnAction(event -> bookTicket());
        menuItemUpdateFlightSchedule.setOnAction(event -> checkIn());
        menuItemCancelFlight.setOnAction(event -> claimPoints());
        menuItemViewPassengerList.setOnAction(event -> redeemPoints());
        menuItemViewPassengerList1.setOnAction(event -> viewFlightHistory());
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/passengerViews/RedeemPoints.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) rootPane.getScene().getWindow();
            System.out.println("passenger id : "+ passengerid);
            RedeemPointsController RedeemPointsController = loader.getController();
            RedeemPointsController.setcurrentPassenger(passengerid);
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error loading screen: " + "/passengerViews/RedeemPoints.fxml");
        }
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
