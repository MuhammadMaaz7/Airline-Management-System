package managerControllers;

import java.io.IOException;

import classes.Flight;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConfirmFlightDialogController {
	   @FXML
	    private Button yesButton;

	    @FXML
	    private Button noButton;

	    private String flightNumber;
		
	    public void setFlightId(String flightId) {
	        this.flightNumber = flightId;
	    }
	    
	    @FXML
	    private void initialize() {
	        yesButton.setOnAction(event -> confirmFlight());
	        noButton.setOnAction(event -> cancelFlight());
	   
	    }

	    @FXML
	    private void confirmFlight() {
	    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Flight Confirmation");
	        alert.setHeaderText(null);
	        alert.setContentText("Flight " + flightNumber + " has been confirmed.");
	        alert.showAndWait();
	    	System.out.println("View Schedule..");
			 try {
			        // Load the ViewExistingSchedule.fxml file
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/ManageFlightSchedule.fxml"));
			        Parent root = loader.load();
			        
			        // Create a new stage
			        Stage stage = new Stage();
			        stage.setScene(new Scene(root));
			        stage.setTitle("Flight Schedule");
			        stage.show();
			        
			        Stage currentStage = (Stage) yesButton.getScene().getWindow();
		            currentStage.hide();
		            
			        
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
	        
	        System.out.println("Flight confirmed: " + flightNumber);
	        
	    }

	    @FXML
	    private void cancelFlight() {
	    	Flight.deleteFlight(flightNumber);
	    	System.out.println("View Schedule..");
			 try {
			        // Load the ViewExistingSchedule.fxml file
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/ManageFlightSchedule.fxml"));
			        Parent root = loader.load();
			        
			        // Create a new stage
			        Stage stage = new Stage();
			        stage.setScene(new Scene(root));
			        stage.setTitle("Flight Schedule");
			        stage.show();
			        
			        Stage currentStage = (Stage) yesButton.getScene().getWindow();
		            currentStage.hide();
		            
			        
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
	        System.out.println("Flight cancelled: " + flightNumber);
	        
	    }
	    
	    
}