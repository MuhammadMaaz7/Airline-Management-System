package managerControllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import classes.airport;
import classes.Flight;
import javafx.beans.property.SimpleStringProperty;
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


public class UpdateOrCancelController {
	 String flightNumber;
	
	 @FXML
	    private TableView<Flight> flightScheduleTableView;

	    @FXML
		   
	    private TableColumn<Flight, String> flightNumberColumn;

	    @FXML
	    private TableColumn<Flight, String> departureAirportIdColumn;

	    @FXML
	    private TableColumn<Flight, String> arrivalAirportIdColumn;

	    @FXML
	    private TableColumn<Flight, Date> departureDateColumn;

	    @FXML
	    private TableColumn<Flight, Time> departureTimeColumn;

	    @FXML
	    private TableColumn<Flight, Date> arrivalDateColumn;

	    @FXML
	    private TableColumn<Flight, Time> arrivalTimeColumn;

	    @FXML
	    private TableColumn<Flight, String> statusColumn;
	 
	    @FXML
	    private Button UpdateFlight;
	    
	    @FXML
	    private Button CancelFlightButton;

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
	    
	    
	    List<Flight> flights ;
	    
	    @FXML		 
	    public void initialize() {
	        try {
	        	flights = Flight.getAllFlights();		          
	            ObservableList<Flight> observableFlights = FXCollections.observableArrayList(flights);
	            flightScheduleTableView.setItems(observableFlights);

	            // Set up column cell value factories
	            flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
	            departureAirportIdColumn.setCellValueFactory(cellData -> {
	                int airportId = cellData.getValue().getDepartureAirportId();
	                return new SimpleStringProperty(airport.getAirportNameById(airportId));
	            });
	            arrivalAirportIdColumn.setCellValueFactory(cellData -> {
	                int airportId = cellData.getValue().getArrivalAirportId();
	                return new SimpleStringProperty(airport.getAirportNameById(airportId));
	            });
	            departureDateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
	            departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
	            arrivalDateColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
	            arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
	            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle the exception
	        }
	        
	        UpdateFlight.setOnAction(event -> updateFlightSchedulee());
	        CancelFlightButton.setOnAction(event -> cancelFlightt());;
	        
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
	    private void updateFlightSchedulee() {
	    	 Flight selectedFlight = flightScheduleTableView.getSelectionModel().getSelectedItem();
	    	  if (selectedFlight == null) {
	    	        // If no flight is selected, display an error message and return
	    	        Alert alert = new Alert(Alert.AlertType.ERROR);
	    	        alert.setTitle("Error");
	    	        alert.setHeaderText(null);
	    	        alert.setContentText("Please select a flight to update.");
	    	        alert.showAndWait();
	    	        return;
	    	    }
	    	    
	    	 flightNumber = selectedFlight.getFlightNumber();
	    	 
	    	 System.out.println("flightNumber : "+flightNumber);
		        
	    	   
	        System.out.println("Updating flight schedule...");
	        try {
	            // Load the ScheduleNewFlight.fxml file
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/UpdateFlightSchedule.fxml"));
	            Parent root = loader.load();
     // Create a new stage
	            UpdateFlightController updateFlightController = loader.getController();
	            updateFlightController.setFlightNumber(flightNumber);

	            Stage stage = new Stage();
	            stage.setScene(new Scene(root));
	            stage.show();
	            
	            Stage currentStage = (Stage) UpdateFlight.getScene().getWindow();
	            currentStage.hide();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    @FXML
	    private void cancelFlightt() {
	    	Flight selectedFlight = flightScheduleTableView.getSelectionModel().getSelectedItem();
	    	  if (selectedFlight == null) {
	    	        // If no flight is selected, display an error message and return
	    	        Alert alert = new Alert(Alert.AlertType.ERROR);
	    	        alert.setTitle("Error");
	    	        alert.setHeaderText(null);
	    	        alert.setContentText("Please select a flight to update.");
	    	        alert.showAndWait();
	    	        return;
	    	    }
	    	    
	    	 flightNumber = selectedFlight.getFlightNumber();
	    	 
	         System.out.println("Canceling a flight...");
	         try {
		            // Load the ScheduleNewFlight.fxml file
		            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/CancelFlight.fxml"));
		            Parent root = loader.load();

		            CancelFlightController cancelFlightController = loader.getController();
		            cancelFlightController.setFlightNumber(flightNumber);

		            // Create a new stage
		            Stage stage = new Stage();
		            stage.setScene(new Scene(root));
		            stage.show();
		            
		            Stage currentStage = (Stage) UpdateFlight.getScene().getWindow();
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