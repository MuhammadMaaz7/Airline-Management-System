package managerControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import classes.*;
public class ModifyReservationController {
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
    private void searchReservationn()
    {
    	reviewReservation();
    }
    private void reviewReservation() {
        String bookingIdStr = bookingIdTextField.getText();
        if (bookingIdStr.isEmpty()) {
            showError("Please enter a Booking ID.");
            return;
        }

        int bookingId;
        try {
            bookingId = Integer.parseInt(bookingIdStr);
        } catch (NumberFormatException e) {
            showError("Invalid Booking ID format. Please enter a valid number.");
            return;
        }

        Flight flight = new Flight();
        booking book = flight.getBookingById(bookingId);

        if (book != null) {
            showModificationOptions(flight, book);
        } else {
            showError("Booking ID not found. Please try again.");
        }
    }

    private void showModificationOptions(Flight flight, booking book) {
        // Show confirmation dialog for modification
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Modify Reservation");
        confirmationAlert.setContentText("What would you like to modify?");
        ButtonType modifySeatButton = new ButtonType("Modify Seat Number");
        ButtonType modifyPassengerButton = new ButtonType("Modify Passenger Details");
        ButtonType cancelButton = new ButtonType("Cancel");
        confirmationAlert.getButtonTypes().setAll(modifySeatButton, modifyPassengerButton, cancelButton);

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType == modifySeatButton) {
                modifySeat(flight, book);
            } else if (buttonType == modifyPassengerButton) {
                modifyPassengerDetails(flight, book.getPassengerId());
            } else {
                // User canceled the modification
                System.out.println("Modification canceled.");
            }
        });
    }

    private void modifySeat(Flight flight, booking book) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modify Seat Number");
        dialog.setHeaderText("Enter the new seat number:");
        dialog.setContentText("Seat Number:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(seatNumberStr -> {
            int seatNumber;
            try {
                seatNumber = Integer.parseInt(seatNumberStr);
            } catch (NumberFormatException e) {
                showError("Invalid seat number format. Please enter a valid number.");
                return;
            }

            // Call updateSeatNumber of the Flight class
            if (flight.updateSeatNumber(book.getBookingId(), seatNumber)) {
                showInfo("Seat number updated successfully.");
            } else {
                showError("Failed to update seat number. Seat may not be available.");
            }
        });
    }

    private void modifyPassengerDetails(Flight flight, int passengerId) {
        passenger Passenger = flight.getPassengerById(passengerId);

        if (Passenger == null) {
            showError("Passenger not found.");
            return;
        }

        // Ask user what they want to modify
        Alert modificationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        modificationAlert.setTitle("Modify Passenger Details");
        modificationAlert.setHeaderText("What would you like to modify?");
        ButtonType modifyNameButton = new ButtonType("Modify Name");
        ButtonType modifyContactButton = new ButtonType("Modify Contact Number");
        ButtonType modifyBothButton = new ButtonType("Modify Both");
        ButtonType cancelButton = new ButtonType("Cancel");
        modificationAlert.getButtonTypes().setAll(modifyNameButton, modifyContactButton, modifyBothButton, cancelButton);

        Optional<ButtonType> result = modificationAlert.showAndWait();
        result.ifPresent(buttonType -> {
            if (buttonType == modifyNameButton) {
                modifyPassengerName(flight, Passenger);
            } else if (buttonType == modifyContactButton) {
                modifyPassengerContact(flight, Passenger);
            } else if (buttonType == modifyBothButton) {
                modifyPassengerName(flight, Passenger);
                modifyPassengerContact(flight, Passenger);
            } else {
                System.out.println("Modification canceled.");
            }
        });
    }

    private void modifyPassengerName(Flight flight, passenger Passenger) {
        TextInputDialog dialog = new TextInputDialog(Passenger.getFullName());
        dialog.setTitle("Modify Name");
        dialog.setHeaderText("Enter the new name for the passenger:");
        dialog.setContentText("Name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            Passenger.setFullName(name);
            flight.updatePassengerDetails(Passenger);
            showInfo("Passenger name updated successfully.");
        });
    }

    private void modifyPassengerContact(Flight flight, passenger Passenger) {
        TextInputDialog dialog = new TextInputDialog(Passenger.getPhoneNumber());
        dialog.setTitle("Modify Contact Number");
        dialog.setHeaderText("Enter the new contact number for the passenger:");
        dialog.setContentText("Contact Number:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(contactNumber -> {
            Passenger.setPhoneNumber(contactNumber);
            flight.updatePassengerDetails(Passenger);
            showInfo("Passenger contact number updated successfully.");
        });
    }

    private void showError(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }

    private void showInfo(String message) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("Info");
        infoAlert.setHeaderText(null);
        infoAlert.setContentText(message);
        infoAlert.showAndWait();
    }
}