package managerControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import classes.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class searchandmodifyentertainmentController implements Initializable{
	  @FXML
	    private TextField contentIdField;

	    @FXML
	    private Label titleLabel;

	    @FXML
	    private Label genreLabel;

	    @FXML
	    private Label durationLabel;

	    @FXML
	    private Label ratingLabel;
	    @FXML
	    private Button searchbutton;
	    @FXML
	    private ComboBox<String> operationComboBox;

	    @FXML
	    private TextField newTitleField;

	    @FXML
	    private TextField newGenreField;

	    @FXML
	    private TextField newDurationField;

	    @FXML
	    private TextField newRatingField;

	    private Flight flight = new Flight();
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
	    
	    @Override
	    public void initialize(URL location, ResourceBundle resources) {
	    	operationComboBox.setItems(FXCollections.observableArrayList("Add Content", "Remove Content"));
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
	    public void handleSearchContent() {
	        int contentId = Integer.parseInt(contentIdField.getText());
	        flight.RequestContent(contentId, titleLabel, genreLabel, durationLabel, ratingLabel);
	    }

	    @FXML
	    public void handleModifyContent() {
	        String operation = operationComboBox.getValue();
	        int contentId = Integer.parseInt(contentIdField.getText());

	        if ("Add Content".equals(operation)) {
	            String newTitle = newTitleField.getText();
	            String newGenre = newGenreField.getText();
	            String newDuration = newDurationField.getText();
	            String newRating = newRatingField.getText();
	            flight.addContent(contentId, newTitle, newGenre, newDuration, newRating);
	        } else if ("Remove Content".equals(operation)) {
	            flight.removeContent(contentId);
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