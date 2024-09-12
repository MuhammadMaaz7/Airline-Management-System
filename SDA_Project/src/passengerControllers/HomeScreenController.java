package passengerControllers;

import classes.booking;
import classes.loyaltyProgram;

import java.io.IOException;
import java.util.List;

import classes.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HomeScreenController {
	 @FXML
	    private TableView<booking> flightTableView;

	    @FXML
	    private TableColumn<booking, String> flightNumberColumn;

	    @FXML
	    private TableColumn<booking, String> dateColumn;

	    @FXML
	    private TableColumn<booking, String> seatColumn;

	    @FXML
	    private TableColumn<booking, String> bookingReferenceColumn;

	    @FXML
	    private TextField nameTextField;

	    @FXML
	    private TextField membershipIdTextField;

	    @FXML
	    private TextField milesTravelledTextField ;

	    @FXML
	    private TextField pointsEarnedTextField;
	    
	    @FXML
	    private TextField flightsbooked;
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



	    private int passengerId;
	    public void setPassengerId(int passengerId) {
	        this.passengerId = passengerId;
	    }
	    
	    @FXML
	    private void initialize() {
	        // Initialize table columns

	      	      menuItemGoToMainPage.setOnAction(event -> goToMainPage());
	        	     menuItemGoToPassengerHomeScreen.setOnAction(event -> goToPassengerHomeScreen());
	        	    menuItemAddNewFlight.setOnAction(event -> bookTicket());
	        	   menuItemUpdateFlightSchedule.setOnAction(event -> checkIn());
	        	menuItemCancelFlight.setOnAction(event -> claimPoints());
	        	menuItemViewPassengerList.setOnAction(event -> redeemPoints());
	        	menuItemViewPassengerList1.setOnAction(event -> viewFlightHistory());

	    	this.passengerId = 1;
	    	 System.out.println("HomeScreenController initialize method called.");
	    	 System.out.println("Fetching details for passengerId: " + passengerId);
	    	    
	    	  String passengerName = loyaltyProgram.getPassengerName(passengerId);
	          String membershipNumber = loyaltyProgram.getMembershipNumber(passengerId);

	          // Display the values in text fields
	          nameTextField.setText(passengerName);
	          membershipIdTextField.setText(membershipNumber);
	          
	          loyaltyProgram lp = new loyaltyProgram();
	          lp= loyaltyProgram.getPassengerDetails(passengerId);

	          if (lp == null) {
	              System.out.println("loyaltyProgram object is null for passengerId: " + passengerId);
	          } else {
	              System.out.println("loyaltyProgram object fetched: " + lp);
	              milesTravelledTextField.setText(String.valueOf(lp.getMilesTravelled()));
	              pointsEarnedTextField.setText(String.valueOf(lp.getPointsEarned()));
	              flightsbooked.setText(String.valueOf(lp.getTotalFlightCount(passengerId)));
	          }
	          
	    	this.passengerId = 1;
	        flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
	        dateColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
	        seatColumn.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));
	        bookingReferenceColumn.setCellValueFactory(new PropertyValueFactory<>("bookingReference"));

	  	  nameTextField.setEditable(false);
    	  membershipIdTextField.setEditable(false);
    	
	        loadFlightsBookedByPassenger();
	    }
	    
	    private void loadFlightsBookedByPassenger() {
	        List<booking> bookings = booking.loadFlightsBookedByPassenger(passengerId);
	        flightTableView.setItems(FXCollections.observableArrayList(bookings));
	   
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
	             System.out.println("passenger id : "+ passengerId);
	             RedeemPointsController RedeemPointsController = loader.getController();
	             RedeemPointsController.setcurrentPassenger(passengerId);
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