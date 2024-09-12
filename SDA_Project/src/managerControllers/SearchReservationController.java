package managerControllers;

import java.io.IOException;

import classes.Flight;
import classes.booking;
import classes.passenger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

public class SearchReservationController {
	 @FXML
	    private TextField passengerNameTextField;

	    @FXML
	    private TextField flightNoTextField;
	    @FXML
        private Button searchbutton;
	    @FXML
	    private TextField bookingIdTextField;
	    @FXML
	    private BorderPane rootPane;
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
	    private MenuItem menuItemAddPassenger;
	    @FXML
	    private MenuItem menuItemRemovePassenger;
	    @FXML
	    private MenuItem menuItemViewEmployeeList;
	    @FXML
	    private MenuItem menuItemAddEmployee;
	    @FXML
	    private MenuItem menuItemRemoveEmployee;
	    @FXML
	    private MenuItem menuItemManageCrewSchedules;
	    @FXML
	    private MenuItem menuItemSearchReservation;
	    @FXML
	    private MenuItem menuItemModifyReservationDetails;
	    @FXML
	    private MenuItem menuItemAccessInFlightServices;
	    @FXML
	    private MenuItem menuItemAddMaintenanceSchedule;
	    @FXML
	    private MenuItem menuItemUpdateBaggageLimit;
	    @FXML
	    private MenuItem menuItemGoToManagerHomeScreen;
	    @FXML
	    public void initialize() {
	    	menuItemGoToMainPage.setOnAction(event -> goToMainPage());
	        menuItemGoToManagerHomeScreen.setOnAction(event -> goToManagerHomeScreen());
	        menuItemAddNewFlight.setOnAction(event -> addNewFlight());
	        menuItemUpdateFlightSchedule.setOnAction(event -> updateFlightSchedule());
	        menuItemCancelFlight.setOnAction(event -> cancelFlight());
	        //menuItemViewPassengerList.setOnAction(event -> viewPassengerList());
//	        menuItemAddPassenger.setOnAction(event -> addPassenger());
//	        menuItemRemovePassenger.setOnAction(event -> removePassenger());
	        menuItemViewEmployeeList.setOnAction(event -> viewEmployeeList());
//	        menuItemAddEmployee.setOnAction(event -> addEmployee());
	        menuItemRemoveEmployee.setOnAction(event -> removeEmployee());
	        menuItemManageCrewSchedules.setOnAction(event -> manageCrewSchedules());
	        menuItemSearchReservation.setOnAction(event -> searchReservation());
	        menuItemModifyReservationDetails.setOnAction(event -> modifyReservationDetails());
	        menuItemAccessInFlightServices.setOnAction(event -> accessInFlightServices());
	        menuItemAddMaintenanceSchedule.setOnAction(event -> addMaintenanceSchedule());
	        menuItemGoToManagerHomeScreen.setOnAction(event -> goToManagerHomeScreen());
	        menuItemUpdateBaggageLimit.setOnAction(event -> updateBaggageLimit());
	    }
	    
	    @FXML
	    private void goToMainPage() {
	        switchToAnyScreen("/managerViews/mainPage.fxml");
	    }
	    
	    @FXML
	    private void goToManagerHomeScreen() {
	        switchToAnyScreen("/managerViews/managerHomeScreen.fxml");
	    }

	    @FXML
	    private void addNewFlight() {
	        switchToAnyScreen("/managerViews/addNewFlight.fxml");
	    }

	    @FXML
	    private void updateFlightSchedule() {
	        switchToAnyScreen("/managerViews/UpdateOrCancelFlight.fxml");
	    }

	    @FXML
	    private void cancelFlight() {
	        switchToAnyScreen("/managerViews/cancelFlight.fxml");
	    }

	    @FXML
	    private void viewPassengerList() {
	        switchToAnyScreen("/managerViews/viewPassengerList.fxml");
	    }

	    @FXML
	    private void addPassenger() {
	        switchToAnyScreen("/managerViews/addPassenger.fxml");
	    }

	    @FXML
	    private void removePassenger() {
	        switchToAnyScreen("/managerViews/removePassenger.fxml");
	    }

	    @FXML
	    private void viewEmployeeList() {
	        switchToAnyScreen("/managerViews/viewEmployeeList.fxml");
	    }

	    @FXML
	    private void addEmployee() {
	        switchToAnyScreen("/managerViews/addEmployee.fxml");
	    }

	    @FXML
	    private void removeEmployee() {
	        switchToAnyScreen("/managerViews/removeEmployee.fxml");
	    }

	    @FXML
	    private void manageCrewSchedules() {
	        switchToAnyScreen("/managerViews/manageCrewSchedules.fxml");
	    }

	    @FXML
	    private void searchReservation() {
	        switchToAnyScreen("/managerViews/SearchReservation.fxml");
	    }

	    @FXML
	    private void modifyReservationDetails() {
	        switchToAnyScreen("/managerViews/ModifyReservation.fxml");
	    }

	    @FXML
	    private void accessInFlightServices() {
	        switchToAnyScreen("/managerViews/AccessServices.fxml");
	    }

	    @FXML
	    private void addMaintenanceSchedule() {
	        switchToAnyScreen("/managerViews/AddMaintenanceSchedule.fxml");
	    }

	    @FXML
	    private void updateBaggageLimit() {
	        switchToAnyScreen("/managerViews/updateBaggageLimit.fxml");
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
	    @FXML
	    private void searchReservatioon() {
	    	try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/SearchReservation.fxml"));
	            Parent root = loader.load();

	            // Close the current window
	            Stage currentStage = (Stage) searchbutton.getScene().getWindow();
	            
	            currentStage.setScene(new Scene(root));
		        currentStage.setTitle("SearchReservation");
		        currentStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	            showError("Error loading.");
	        }
	    }

	    @FXML
	    private void modifyReservationDetailss() {
	    	try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/ModifyReservation.fxml"));
	            Parent root = loader.load();

	            // Close the current window
	            Stage currentStage = (Stage) searchbutton.getScene().getWindow();
	            
	            currentStage.setScene(new Scene(root));
		        currentStage.setTitle("Modify Reservation");
		        currentStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	            showError("Error loading page.");
	        }
	    }

	    @FXML
	    private void accessInFlightServicess() {
	    	try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/AccessServices.fxml"));
	            Parent root = loader.load();

	            // Close the current window
	            Stage currentStage = (Stage) searchbutton.getScene().getWindow();
	            
	            currentStage.setScene(new Scene(root));
		        currentStage.setTitle("Acess Services");
		        currentStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	            showError("Error loading page.");
	        }
	    }

	    @FXML
	    private void addMaintenanceSchedulee() {
	    	try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/AddMiantenanceSchedule.fxml"));
	            Parent root = loader.load();

	            // Close the current window
	            Stage currentStage = (Stage) searchbutton.getScene().getWindow();
	            
	            currentStage.setScene(new Scene(root));
		        currentStage.setTitle("Maintenance Schedule");
		        currentStage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	            showError("Error loading page.");
	        }
	    }
	    @FXML
	    private void searchReservationn() {
	        String passengerid = passengerNameTextField.getText();
	        String flightNumber = flightNoTextField.getText();
	        String bookingIdStr = bookingIdTextField.getText();

	        if (passengerid.isEmpty() || flightNumber.isEmpty() || bookingIdStr.isEmpty()) {
	            showAlert("Error", "All fields must be filled out.");
	            return;
	        }

	        try {
	            int bookingId = Integer.parseInt(bookingIdStr);
	            // Start the chain of calls
	            Flight.FindFlight(flightNumber, passengerid, bookingId);

	        } catch (NumberFormatException e) {
	            showAlert("Error", "Booking ID must be a number.");
	        }
	    }

	    private void showAlert(String title, String content) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(content);
	        alert.showAndWait();
	    }
	
private void showError(String message) {
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    errorAlert.setTitle("Error");
    errorAlert.setHeaderText(null);
    errorAlert.setContentText(message);
    errorAlert.showAndWait();
}
}