package managerControllers;

import java.io.IOException;

import classes.Crew;
import classes.Maintenance;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AssignStaffController {
	  @FXML
	    private TextField maintenanceIdField;
	 @FXML
	    private TextField staffIdField;
	    @FXML
	    private Label staffExpertiseLabel;
	    @FXML
	    private Label notificationLabel;
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
	    private Maintenance maintenance = new Maintenance();

	    @FXML
	    private void handleCheckAvailability() {
	        String staffIdStr = staffIdField.getText();
	        if (staffIdStr.isEmpty()) {
	            showError("Please enter a Staff ID.");
	            return;
	        }

	        int staffId;
	        try {
	            staffId = Integer.parseInt(staffIdStr);
	        } catch (NumberFormatException e) {
	            showError("Invalid Staff ID format. Please enter a valid number.");
	            return;
	        }

	        Crew availableStaff = maintenance.CheckStaff(staffId);
	        if (availableStaff != null) {
	            staffExpertiseLabel.setText(availableStaff.getExpertise());
	        } else {
	            showError("Staff ID not found or staff is not available.");
	        }
	    }

	    @FXML
	    private void handleAssignStaff() {
	    	 String maintenanceIdStr = maintenanceIdField.getText();
	         String staffIdStr = staffIdField.getText();
	         if (maintenanceIdStr.isEmpty() || staffIdStr.isEmpty()) {
	             showError("Please enter Maintenance ID and Staff ID.");
	             return;
	         }
	         int maintenanceId, staffId;
	         try {
	             maintenanceId = Integer.parseInt(maintenanceIdStr);
	             staffId = Integer.parseInt(staffIdStr);
	         } catch (NumberFormatException e) {
	             showError("Invalid Maintenance ID or Staff ID format. Please enter valid numbers.");
	             return;
	         }


	        boolean success = maintenance.assigningstaff(maintenanceId,staffId);
	        if (success) {
	            notificationLabel.setText("Staff assigned successfully.");
	        } else {
	            showError("Failed to assign staff.");
	        }
	    }

	    private void showError(String message) {
	        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
	        errorAlert.setTitle("Error");
	        errorAlert.setHeaderText(null);
	        errorAlert.setContentText(message);
	        errorAlert.showAndWait();
	    }
}