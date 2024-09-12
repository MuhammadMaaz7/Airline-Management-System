package managerControllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import classes.airport;
import classes.CrewSchedules;
import classes.Flight;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

public class ManageCrewSchedulesController {

	 @FXML
	    private TableView<CrewSchedules> crewTableView;
	    
	    @FXML
	    private TableColumn<CrewSchedules, String> nameColumn;
	    
	    @FXML
	    private TableColumn<CrewSchedules, String> roleColumn;
	    
	    @FXML
	    private TableColumn<CrewSchedules, String> flightNumberColumn;
	    
	    @FXML
	    private TableColumn<CrewSchedules, String> compartmentColumn;
	    
	    @FXML
	    private TableColumn<CrewSchedules, String> dateColumn;
	    
	    @FXML
	    private TableColumn<CrewSchedules, String> statusColumn;
	    
	    @FXML
	    private Button addScheduleButton;

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
	    

	    
	    List<CrewSchedules> crewSchedules ;
    	
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
	   	  

	    	
	        try {
	        	crewSchedules = CrewSchedules.getAllCrewSchedulesWithDetails();
	    	  ObservableList<CrewSchedules> observableCrewSchedules = FXCollections.observableArrayList(crewSchedules);
	    	  crewTableView.setItems(observableCrewSchedules);

	    	  nameColumn.setCellValueFactory(new PropertyValueFactory<>("crewName"));
	          roleColumn.setCellValueFactory(new PropertyValueFactory<>("crewRole"));
	          compartmentColumn.setCellValueFactory(new PropertyValueFactory<>("aircraftCompartment"));
		         
	          flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightID"));
	          
	            dateColumn.setCellValueFactory(cellData -> {
	                CrewSchedules schedule = cellData.getValue();
	                try {
	                    Flight flight = Flight.getFlightByNumber(schedule.getFlightID());
	                    return new SimpleStringProperty(flight.getDepartureDate().toString()); // Assuming date is stored as LocalDate
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                    return new SimpleStringProperty("Error fetching date");
	                }
	            });

	            statusColumn.setCellValueFactory(cellData -> {
	                CrewSchedules schedule = cellData.getValue();
	                try {
	                    Flight flight = Flight.getFlightByNumber(schedule.getFlightID());
	                    return new SimpleStringProperty(flight.getStatus());
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                    return new SimpleStringProperty("Error fetching status");
	                }
	            });
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle the exception
	        }
	        
	        addScheduleButton.setOnAction(event -> addNewCrewSchedule());
	}
	    
	    @FXML
	    private void addNewCrewSchedule() {
	    	  System.out.println("Add new crew schedule button click...");
	    	  try {
		            // Load the ScheduleNewFlight.fxml file
		            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/ScheduleCrewToFlight.fxml"));
		            Parent root = loader.load();

		            // Create a new stage
		            Stage stage = new Stage();
		            stage.setScene(new Scene(root));
		            stage.show();
		            
		            Stage currentStage = (Stage) addScheduleButton.getScene().getWindow();
		            currentStage.hide();
		            
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
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