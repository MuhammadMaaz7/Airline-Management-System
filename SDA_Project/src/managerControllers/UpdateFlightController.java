package managerControllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import classes.Flight;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import classes.airport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

public class UpdateFlightController {
   
	String flightNumber;
	
	@FXML
	private ComboBox<String> departureCityComboBox;

	@FXML
	private ComboBox<String> arrivalCityComboBox;

	@FXML
	private ComboBox<String> FlightStatusComboBox;
	
	 private final ObservableList<String> statusOptions = FXCollections.observableArrayList(
	            "Scheduled", "On Time", "Delayed", "Cancelled", "Completed");

	
	@FXML
	private DatePicker departureDatePicker;

	@FXML
	private Spinner<Integer> departureTimeSpinnerHrs;

	@FXML
	private Spinner<Integer> departureTimeSpinnerMins;

	@FXML
	private Spinner<Integer> flightdurationHrs;

	@FXML
	private Spinner<Integer> flightdurationMins;

	@FXML
	private Button UpdateFlightButton; 
	
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
	    

	public void setFlightNumber(String flightNumber) {
		 System.out.println("setting the flightNumber "+flightNumber);
	    this.flightNumber = flightNumber;
	    initializewithData();
	}
	
	@FXML		   
	public void initializewithData() {
		 System.out.println("Update Flight Controller");
		  System.out.println(flightNumber);
				
		if (flightNumber == null) {
		    // Display an error message indicating that no flight was found
		  System.out.println("No flight found");
			    
		    Alert alert = new Alert(Alert.AlertType.ERROR);
		    alert.setTitle("Error");
		    alert.setHeaderText(null);
		    alert.setContentText("No flight found");
		    alert.showAndWait();
		    return;
		}
		
		   try {
		        // Retrieve the flight details using the flightNumber
		        Flight flight = Flight.getFlightByNumber(flightNumber);

		        // Populate UI elements with the retrieved flight details
		        departureCityComboBox.setValue(airport.getAirportNameById(flight.getDepartureAirportId()));
		        arrivalCityComboBox.setValue(airport.getAirportNameById(flight.getArrivalAirportId()));
		        departureDatePicker.setValue(flight.getDepartureDate().toLocalDate());
		        departureTimeSpinnerHrs.getValueFactory().setValue(flight.getDepartureTime().toLocalTime().getHour());
		        departureTimeSpinnerMins.getValueFactory().setValue(flight.getDepartureTime().toLocalTime().getMinute());
		        
		        // Calculate duration in hours and minutes
		        int totalMinutes = flight.getDurationMinutes();
		        int durationHours = totalMinutes / 60;
		        int durationMinutes = totalMinutes % 60;
		        flightdurationHrs.getValueFactory().setValue(durationHours);
		        flightdurationMins.getValueFactory().setValue(durationMinutes);
		    } catch (SQLException e) {
		        e.printStackTrace();
		        // Handle the exception
		    }
		
	}
	
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
		  

		
		FlightStatusComboBox.setItems(statusOptions);
		 
		try {
			ObservableList<String> airportNames = airport.getAllAirportNames();
			departureCityComboBox.setItems(airportNames);
			arrivalCityComboBox.setItems(airportNames);
		} catch (SQLException e) {
			e.printStackTrace();

		}

		departureDatePicker.setDayCellFactory(picker -> new DateCell() {
			@Override
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				if (date.isBefore(LocalDate.now())) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;"); // Change style if you want
				}
			}
		});

		departureTimeSpinnerHrs.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));

		departureTimeSpinnerMins.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5));
		departureTimeSpinnerMins.getValueFactory().setConverter(new StringConverter<Integer>() {
			@Override
			public String toString(Integer value) {
				if (value == 0) return "00";
				return String.format("%02d", value);
			}

			@Override
			public Integer fromString(String string) {
				return Integer.valueOf(string);
			}
		});

		flightdurationHrs.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 0));

		flightdurationMins.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5));
		flightdurationMins.getValueFactory().setConverter(new StringConverter<Integer>() {
			@Override
			public String toString(Integer value) {
				if (value == 0) return "00";
				return String.format("%02d", value);
			}

			@Override
			public Integer fromString(String string) {
				return Integer.valueOf(string);
			}
		});

		UpdateFlightButton.setOnAction(event -> UpdateFlightSchedule());
}
	
	@FXML
	private void UpdateFlightSchedule() {
		if (departureCityComboBox.getValue() == null ||
				arrivalCityComboBox.getValue() == null ||
				departureDatePicker.getValue() == null ||
				departureTimeSpinnerHrs.getValue() == null ||
				departureTimeSpinnerMins.getValue() == null ||
				flightdurationHrs.getValue() == null ||
				flightdurationMins.getValue() == null ||
	            FlightStatusComboBox.getValue() == null) {
			// Display an error message indicating that all fields must be filled
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("All fields must be filled.");
			alert.showAndWait();
			return;
		}
		
		try {

			String departureCity = departureCityComboBox.getValue(); // Get departure city from the ComboBox
			String arrivalCity = arrivalCityComboBox.getValue(); // Get arrival city from the ComboBox
			LocalDate departureDate = departureDatePicker.getValue(); // Get departure date from the DatePicker
			int departureHour = departureTimeSpinnerHrs.getValue(); // Get departure hour from the Spinner
			int departureMinute = departureTimeSpinnerMins.getValue(); // Get departure minute from the Spinner
			int durationHours = flightdurationHrs.getValue(); // Get flight duration hours from the Spinner
			int durationMinutes = flightdurationMins.getValue(); // Get flight duration minutes from the Spinner
			int durationInMinutes = durationHours * 60 + durationMinutes;

			if(durationInMinutes <= 30) {
				 Alert alert = new Alert(Alert.AlertType.ERROR);
		            alert.setTitle("Error");
		            alert.setHeaderText(null);
		            alert.setContentText("Flight duration must be at least 30 minutes.");
		            alert.showAndWait();
		            return;
			}
			 String status = FlightStatusComboBox.getValue(); 
			// Get departure and arrival airport IDs from the database using the airport names
			int departureAirportId = airport.getAirportId(departureCity);
			int arrivalAirportId = airport.getAirportId(arrivalCity);

			// Check if departure and arrival airports are different
			if (departureAirportId == arrivalAirportId) {
				// Display an error message indicating that departure and arrival airports must be different
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText(null);
				alert.setContentText("Departure and arrival airports must be different.");
				alert.showAndWait();
				return;
			}


			// Create java.sql.Date and java.sql.Time objects from LocalDate and int values
			Date departureSqlDate = Date.valueOf(departureDate);
			Time departureSqlTime = Time.valueOf(LocalTime.of(departureHour, departureMinute));

			// Calculate the arrival date and time based on the departure date, time, and duration
			LocalDateTime arrivalLocalDateTime = departureDate.atTime(departureHour, departureMinute)
					.plusHours(durationHours)
					.plusMinutes(durationMinutes);
			Date arrivalSqlDate = Date.valueOf(arrivalLocalDateTime.toLocalDate());
			Time arrivalSqlTime = Time.valueOf(arrivalLocalDateTime.toLocalTime());

			System.out.println("flight class function call updateflight()");
			
			
			
			Flight.updateFlight(flightNumber,departureAirportId, arrivalAirportId,
					departureSqlDate, departureSqlTime, arrivalSqlDate, arrivalSqlTime,
					durationInMinutes,status);
		

		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to update flight: " + e.getMessage());
		}

		System.out.println("View Flight Schedule..");
		 try {
		        // Load the ViewExistingSchedule.fxml file
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/UpdateOrCancelFlight.fxml"));
		        Parent root = loader.load();
		        
		        // Create a new stage
		        Stage stage = new Stage();
		        stage.setScene(new Scene(root));
		        stage.setTitle("View Existing Schedule");
		        stage.show();
		        
		        Stage currentStage = (Stage) UpdateFlightButton.getScene().getWindow();
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