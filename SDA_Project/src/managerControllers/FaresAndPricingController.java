package managerControllers;

import java.io.IOException;
import java.sql.SQLException;

import classes.Flight;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class FaresAndPricingController {
	
	    private String flightNumber;
	
	    @FXML
	    private Spinner<Integer> baseFareSpinner;

	    @FXML
	    private Spinner<Integer> taxesSpinner;

	    @FXML
	    private TextField totalFareField;

	    @FXML
	    private Button confirmFlightButton;

	    public void setFlightId(String flightId) {
	        this.flightNumber = flightId;
	    }
	    
	    @FXML
	    private void initialize() {
	    	
	    	  // Set up base fare spinner
	        SpinnerValueFactory.IntegerSpinnerValueFactory baseFareFactory =
	                new SpinnerValueFactory.IntegerSpinnerValueFactory(10000, 3000000, 10000);
	        int increment = (int) (baseFareFactory.getValue() * 0.25); // Cast to int
	        baseFareFactory.setAmountToStepBy(increment); // Set increment to 25% of current value
	        baseFareSpinner.setValueFactory(baseFareFactory);

	        // Set up taxes spinner
	        SpinnerValueFactory.IntegerSpinnerValueFactory taxesFactory =
	                new SpinnerValueFactory.IntegerSpinnerValueFactory(1500, 100000, 2000);
	        taxesFactory.setAmountToStepBy(2000); // Set increment
	        taxesSpinner.setValueFactory(taxesFactory);
	    	
	    	  totalFareField.setEditable(false);
	        baseFareSpinner.valueProperty().addListener((observable, oldValue, newValue) -> calculateTotalFare());
	        taxesSpinner.valueProperty().addListener((observable, oldValue, newValue) -> calculateTotalFare());
	   
	        confirmFlightButton.setOnAction(event -> handleConfirmFlight());

	    }

	    private void calculateTotalFare() {
	        int baseFare = baseFareSpinner.getValue();
	        int taxes = taxesSpinner.getValue();
	        int totalFare = baseFare + taxes;
	        totalFareField.setText(String.valueOf(totalFare));
	    }
	    

	    @FXML
	    private void addNewFlight() {
	        // Implement the logic to confirm flight here
	        System.out.println("add New Flight");
	    }

	    
	    @FXML
	    private void handleConfirmFlight() {
	    	 try {
	             double fare = Double.parseDouble(totalFareField.getText()); // Assuming the total fare is entered in the TextField
	             Flight.addFareToFlight(flightNumber, fare);
	             System.out.println("Flight confirmed!");
	         	System.out.println("View Schedule..");
				 try {
				        // Load the ViewExistingSchedule.fxml file
				        FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/ConfirmFlightDialogBox.fxml"));
				        Parent root = loader.load();
				        
				        ConfirmFlightDialogController confirmFlightDialogController =  loader.getController();
				        confirmFlightDialogController.setFlightId(flightNumber);
				       
				        // Create a new stage
				        Stage stage = new Stage();
				        stage.setScene(new Scene(root));
				        stage.setTitle("Flight Schedule");
				        stage.show();
				        
				        Stage currentStage = (Stage) confirmFlightButton.getScene().getWindow();
			            currentStage.hide();
			            
				        
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
	         } catch (NumberFormatException e) {
	             Alert alert = new Alert(Alert.AlertType.ERROR);
	             alert.setTitle("Error");
	             alert.setHeaderText(null);
	             alert.setContentText("Please enter a valid fare amount.");
	             alert.showAndWait();
	         } catch (SQLException e) {
	             e.printStackTrace();
	             System.err.println("Failed to add fare to flight: " + e.getMessage());
	         }
	     
	    
	    System.out.println("Flight confirmed!");
	    }
	    

		@FXML
		private void updateFlightSchedule() {
			System.out.println("Updating flight schedule...");
		}

		@FXML
		private void cancelFlight() {
			System.out.println("Canceling a flight...");
		}

		@FXML
		private void viewPassengerList() {
			System.out.println("Viewing passenger list...");
		}

		@FXML
		private void addPassenger() {
			System.out.println("Adding a new passenger...");
		}

		@FXML
		private void removePassenger() {
			System.out.println("Removing a passenger...");
		}

		@FXML
		private void viewEmployeeList() {
			System.out.println("Viewing employee list...");
		}

		@FXML
		private void addEmployee() {
			System.out.println("Adding a new employee...");
		}

		@FXML
		private void removeEmployee() {
			System.out.println("Removing an employee...");
			try {
			    FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/DeleteCrew.fxml"));
			    Parent root = loader.load();
			    Stage stage = new Stage();
			    stage.setScene(new Scene(root));
			    stage.setTitle("Delete Crew");
			    stage.show();
			    Stage currentStage = (Stage) confirmFlightButton.getScene().getWindow();
	            currentStage.hide();
			} catch (IOException e) {
			    e.printStackTrace();
			}
		}

		@FXML
		private void ManageCrewSchedules() {
			System.out.println("Manage Crew Schedules...");
		}
	    
}