package managerControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

import classes.Crew;

public class DeleteCrewController {

    @FXML
    private TableView<Crew> flightScheduleTableView;

    @FXML
    private TableColumn<Crew, String> crewNameColumn;

    @FXML
    private TableColumn<Crew, String> crewRoleColumn;

    @FXML
    private TableColumn<Crew, String> crewExpertiseColumn;


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
    private BorderPane rootPane;

    
    @FXML
    private Button deleteButton;

    private ObservableList<Crew> crewData;

    @FXML
    private void initialize() {

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

        crewNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        crewRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        crewExpertiseColumn.setCellValueFactory(new PropertyValueFactory<>("expertise"));

        crewData = FXCollections.observableArrayList();

        loadCrewData();
        flightScheduleTableView.setItems(crewData);
       // deleteButton.setOnAction(event -> deleteCrewMember());
    }

    private void loadCrewData() {
    	  List<Crew> crewList = Crew.loadAllCrew();
          crewData.addAll(crewList);
    }

    @FXML
    private void deleteCrewMember() {
        Crew selectedCrew = flightScheduleTableView.getSelectionModel().getSelectedItem();

        if (selectedCrew != null) {
            boolean success = Crew.deleteCrewMember(selectedCrew.getCrewId());

            if (success) {
                System.out.println("Crew member deleted successfully!");
                crewData.remove(selectedCrew);
            } else {
                System.out.println("Failed to delete crew member.");
            }
        } else {
            System.out.println("Please select a crew member to delete.");
        }
    }

    // Navigation methods for MenuItems
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
        switchToAnyScreen("/managerViews/ScheduleNewFlight.fxml");
    }

    @FXML
    private void updateFlightSchedule() {
        switchToAnyScreen("/managerViews/UpdateOrCancelFlight.fxml");
    }

    @FXML
    private void cancelFlight() {
        switchToAnyScreen("/managerViews/UpdateOrCancelFlight.fxml");
    }

    @FXML
    private void viewPassengerList() {
        //switchToAnyScreen("/managerViews/viewPassengerList.fxml");
    }

    @FXML
    private void addPassenger() {
        //switchToAnyScreen("/managerViews/addPassenger.fxml");
    }

    @FXML
    private void removePassenger() {
        //switchToAnyScreen("/managerViews/removePassenger.fxml");
    }

    @FXML
    private void viewEmployeeList() {
        switchToAnyScreen("/managerViews/ManageCrewSchedule.fxml");
    }

    @FXML
    private void addEmployee() {
      //  switchToAnyScreen("/managerViews/addEmployee.fxml");
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
        switchToAnyScreen("/managerViews/searchReservation.fxml");
    }

    @FXML
    private void modifyReservationDetails() {
        switchToAnyScreen("/managerViews/modifyReservationDetails.fxml");
    }

    @FXML
    private void accessInFlightServices() {
        switchToAnyScreen("/managerViews/accessInFlightServices.fxml");
    }

    @FXML
    private void addMaintenanceSchedule() {
        switchToAnyScreen("/managerViews/addMaintenanceSchedule.fxml");
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