package managerControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import classes.*;

public class AccessServicesController {
	@FXML
    private ListView<String> menuListView;

    @FXML
    private ListView<String> entertainmentListView;

    @FXML
    private Button menuButton;
    @FXML
    private Button entertainmentButton;
    @FXML
    private Button specialDietaryRequirementsButton;
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
    private void handleMenu() {
    	try {
    	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/searchandmodifymenu.fxml"));
    	    Parent root = loader.load();

    	    // Get the current stage using one of the buttons in your current scene
    	    Stage currentStage = (Stage) menuButton.getScene().getWindow();
    	    
    	    // Set the new scene on the current stage
    	    currentStage.setScene(new Scene(root));
    	    currentStage.setTitle("Menu");
    	    currentStage.show();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	    showError("Error loading search and modify menu page.");
    	}
    }

    @FXML
    private void handleEntertainment() {
    	  try {
    	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/searchandmodifyentertainment.fxml"));
    	        Parent root = loader.load();
    	        Stage currentStage = (Stage) entertainmentButton.getScene().getWindow();
    	        currentStage.setScene(new Scene(root));
    	        currentStage.setTitle("Entertainment");
    	        currentStage.show();
    	    } catch (IOException e) {
    	        e.printStackTrace();
    	        showError("Error loading search and modify entertainment page.");
    	    }
    }

    @FXML
    private void handleSpecialMeal() {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/specialMealRequest.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) specialDietaryRequirementsButton.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Special Meal Request");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error loading special meal request page.");
        }
    }

    private Flight flight;

    public void initialize() {
        // Initially clear list views
    	flight = new Flight();
        menuListView.getItems().clear();
        entertainmentListView.getItems().clear();
        AccessServices();
        menuItemGoToMainPage.setOnAction(event -> goToMainPage());
        menuItemGoToManagerHomeScreen.setOnAction(event -> goToManagerHomeScreen());
        menuItemAddNewFlight.setOnAction(event -> addNewFlight());
        menuItemUpdateFlightSchedule.setOnAction(event -> updateFlightSchedule());
        menuItemCancelFlight.setOnAction(event -> cancelFlight());
        //menuItemViewPassengerList.setOnAction(event -> viewPassengerList());
//        menuItemAddPassenger.setOnAction(event -> addPassenger());
//        menuItemRemovePassenger.setOnAction(event -> removePassenger());
        menuItemViewEmployeeList.setOnAction(event -> viewEmployeeList());
//        menuItemAddEmployee.setOnAction(event -> addEmployee());
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
    void AccessServices()
    {
    	 flight.handleAccessRequest(menuListView, entertainmentListView);
    }

    private void showError(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(null);
        errorAlert.setContentText(message);
        errorAlert.showAndWait();
    }
   
}