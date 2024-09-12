package managerControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import util.DBConnection;
import classes.Aircraft;
import classes.Crew;
import classes.CrewSchedules;
import classes.Flight;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import classes.CrewSchedules;

public class AssignCrewToFlightController {
	
	private String flightNumber;

	  @FXML
	    private TableView<Crew> flightScheduleTableView;

	    @FXML
	    private TableColumn<Crew, String> crewNameColumn;

	    @FXML
	    private TableColumn<Crew, String> crewRoleColumn;

	    @FXML
	    private ComboBox<String> compartmentcombobox;

	    @FXML
	    private Button assignButton;
	    
	    List<Crew> availableCrewMembers ;
	    
	
	public void setFlightId(String flightId) {
		this.flightNumber = flightId;
		System.out.println("flight Number set"+flightNumber);
		initializewithData();
	}	
	
	public void initializewithData() {
		try {
			   Date departureDate = Flight.getFlightByNumber(flightNumber).getDepartureDate();
				System.out.println("departure Date for "+flightNumber+" is "+departureDate);
				
			   availableCrewMembers = getAvailableCrewForDateObservableList(departureDate);

		        // Convert to observable list
		        ObservableList<Crew> observableCrewMembers = FXCollections.observableArrayList(availableCrewMembers);

		        // Set the items in the TableView
		        flightScheduleTableView.setItems(observableCrewMembers);

	    	  crewNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
	    	  crewRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));	         	           
	          
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle the exception
	        }
	}
	 
	public void initialize() {
	
		   
	        

		// Populate compartment ComboBox
		ObservableList<String> compartmentOptions = Aircraft.setAircraftCompartmentsFromDatabase();
		compartmentcombobox.setItems(compartmentOptions);

		assignButton.setOnAction(event -> assignCrewToFlight());
	}
	 
	private ObservableList<Crew> getAvailableCrewForDateObservableList(Date departureDate) throws SQLException {
		System.out.println("getAvailableCrewForDateObservableList");
		
		 ObservableList<Crew> availableCrew = FXCollections.observableArrayList();
		  LocalDate localDate = departureDate.toLocalDate();
		  
		    try (Connection conn = DBConnection.getConnection1();
		         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Crew WHERE NOT EXISTS " +
		                 "(SELECT * FROM CrewSchedules " +
		        		 " JOIN Flight ON CrewSchedules.flightID = Flight.flightNumber " +
		                 "WHERE CrewSchedules.crewID = Crew.crewId AND departureDate = ?)")) {
		        
		        // Set the date parameter
		    	   // Set the date parameter
	            stmt.setDate(1, Date.valueOf(localDate));
	           
		        
		        // Execute the query
		        ResultSet rs = stmt.executeQuery();
		        
		        // Iterate over the result set and create Crew objects
		        while (rs.next()) {
		            int id = rs.getInt("crewId");
		            String name = rs.getString("name");
		            String role = rs.getString("role");
		            // Other properties
		            
		            // Create Crew object and add it to the list
		            Crew crewMember = new Crew();
		            crewMember.setCrewId(id);
		            crewMember.setName(name);
		            crewMember.setRole(role);
		            availableCrew.add(crewMember);
		        }
		    } catch (SQLException e) {
		        throw e;
		    }
		    
		    return availableCrew;
		}

	
	 private void assignCrewToFlight() {
	        
		 Crew selectedCrew = flightScheduleTableView.getSelectionModel().getSelectedItem();
		 String selectedCompartment = compartmentcombobox.getValue();
		
		   if (selectedCrew != null && selectedCompartment != null) {
			     boolean success = CrewSchedules.addNewSchedule(selectedCrew.getCrewId(), flightNumber, selectedCompartment);
		        
		        if (success) {
		            System.out.println("Crew assigned to flight successfully!");
		            Alert alert = new Alert(AlertType.INFORMATION);
		            alert.setTitle("Assignment Successful");
		            alert.setHeaderText(null);
		            alert.setContentText("Crew assigned to flight successfully!");
		            alert.showAndWait();
		            try {
				        // Load the ViewExistingSchedule.fxml file
				        FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/ManageCrewSchedule.fxml"));
				        Parent root = loader.load();
				        
				        // Create a new stage
				        Stage stage = new Stage();
				        stage.setScene(new Scene(root));
				        stage.setTitle("View Crew Schedule");
				        stage.show();
				        
				        Stage currentStage = (Stage) assignButton.getScene().getWindow();
			            currentStage.hide();
			            
				        
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
		  	
		        } else {
		        	 Alert alert = new Alert(AlertType.ERROR);
		             alert.setTitle("Assignment Failed");
		             alert.setHeaderText(null);
		             alert.setContentText("Failed to assign crew to flight.");
		             alert.showAndWait();
		            System.out.println("Failed to assign crew to flight.");

		        }
		   }
		   else {
		        // If no crew member or compartment is selected, display a message to the user
		        if (selectedCrew == null) {
		            System.out.println("Please select a crew member.");
		        }
		        if (selectedCompartment == null) {
		            System.out.println("Please select an aircraft compartment.");
		        }
		   }
	    }
	 
	  private void scheduleNewFlight() {
	    	try {
	            // Load the ScheduleNewFlight.fxml file
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/ScheduleNewFlight.fxml"));
	            Parent root = loader.load();

	            // Create a new stage
	            Stage stage = new Stage();
	            stage.setScene(new Scene(root));
	            stage.show();
	            
	         //   Stage currentStage = (Stage) scheduleNewFlightButton.getScene().getWindow();
	          //  currentStage.hide();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        System.out.println("Scheduling a new flight...");
	    }
	    
	    @FXML
	    private void addNewFlight() {
	        System.out.println("Adding a new flight...");
	        try {
	            // Load the ScheduleNewFlight.fxml file
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/ScheduleNewFlight.fxml"));
	            Parent root = loader.load();

	            // Create a new stage
	            Stage stage = new Stage();
	            stage.setScene(new Scene(root));
	            stage.show();
	            
	           // Stage currentStage = (Stage) scheduleNewFlightButton.getScene().getWindow();
	           // currentStage.hide();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        System.out.println("Adding a new flight...");
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
    	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/DeleteCrew.fxml"));
    	    Parent root = loader.load();
    	    Stage stage = new Stage();
    	    stage.setScene(new Scene(root));
    	    stage.setTitle("Delete Crew");
    	    stage.show();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}
	    }

	    @FXML
	    private void ManageCrewSchedules() {
      System.out.println("Manage Crew Schedules...");
	    }
}