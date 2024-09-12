package managerControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class managerHomeScreenController {

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
    private AnchorPane rootPane; // AnchorPane from your FXML

    @FXML
    public void initialize() {
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
        switchToAnyScreen("/managerViews/ManageFlightSchedule.fxml");
    }

    @FXML
    private void updateFlightSchedule() {
        switchToAnyScreen("/managerViews/UpdateOrCancelFlight.fxml");
    }

    @FXML
    private void cancelFlight() {
        switchToAnyScreen("/managerViews/CancelFlight.fxml");
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
        switchToAnyScreen("/managerViews/ManageCrewSchedule.fxml");
    }

    @FXML
    private void addEmployee() {
        switchToAnyScreen("/managerViews/addEmployee.fxml");
    }

    @FXML
    private void removeEmployee() {
        switchToAnyScreen("/managerViews/DeleteCrew.fxml");
    }

    @FXML
    private void manageCrewSchedules() {
        switchToAnyScreen("/managerViews/ManageCrewSchedule.fxml");
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
}
