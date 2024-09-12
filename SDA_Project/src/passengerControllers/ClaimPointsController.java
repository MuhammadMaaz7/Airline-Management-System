package passengerControllers;

import java.io.IOException;

import classes.booking;
import classes.claimPoints;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClaimPointsController {
	  @FXML
	    private TextField enterbookingPNRTextField;
	  
	  @FXML
	    private Button ClaimPointsButton;
	  

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



	  
	  @FXML
	    public void initialize() {
		  if (menuItemGoToMainPage != null) {
	            menuItemGoToMainPage.setOnAction(event -> goToMainPage());
	        } else {
	            System.out.println("menuItemGoToMainPage is null");
	        }
		  menuItemGoToPassengerHomeScreen.setOnAction(event -> goToPassengerHomeScreen());
	    	    menuItemAddNewFlight.setOnAction(event -> bookTicket());
	    	   menuItemUpdateFlightSchedule.setOnAction(event -> checkIn());
	    	menuItemCancelFlight.setOnAction(event -> claimPoints());
	    	menuItemViewPassengerList.setOnAction(event -> redeemPoints());
	    	menuItemViewPassengerList1.setOnAction(event -> viewFlightHistory());
	   

		  ClaimPointsButton.setOnAction(event -> handleClaimPoints());
	    }
	  
	  @FXML
	    private void handleClaimPoints() {
	        String bookingPNR = enterbookingPNRTextField.getText();
	        
	        if (bookingPNR.isEmpty()) {
	        	Alert alert = new Alert(Alert.AlertType.ERROR);
	            alert.setTitle("Error");
	            alert.setHeaderText(null);
	            alert.setContentText("Booking PNR cannot be empty");
	            alert.showAndWait();
	           return;
	        }
	        
	        try {
	        	 int bookingId = booking.getBookingIdFromPNR(bookingPNR); // Get the booking ID
	            
	            if (bookingId == -1) {
	               Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Error");
	                alert.setHeaderText(null);
	                alert.setContentText( "Booking PNR not found");
	                alert.showAndWait();
	                return;
	            }
	            boolean claimed = claimPoints.isPointsClaimed(bookingId);
	            if (claimed) {
	                
	            	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("Info");
	                alert.setHeaderText(null);
	                alert.setContentText( "Points already claimed");
	                alert.showAndWait();
	                
	            } else {
	                if (claimPoints.claimPoints(bookingId)) {
	                   
	                	Alert alert = new Alert(Alert.AlertType.INFORMATION);
		                alert.setTitle( "Success");
		                alert.setHeaderText(null);
		                alert.setContentText( "Your request has been approved, points added to your account");
		                alert.showAndWait();
	                } else {
	                	  Alert alert = new Alert(Alert.AlertType.ERROR);
	  	                alert.setTitle("Error");
	  	                alert.setHeaderText(null);
	  	                alert.setContentText("Failed to claim points");
	  	                alert.showAndWait();
	                }
	            }
	            // Continue with the rest of your logic using the retrieved bookingId
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Handle any exceptions
	        }
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