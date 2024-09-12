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

public class CancelFlightController {
	 @FXML
	    private Button yesButton;

	    @FXML
	    private Button noButton;

	    private String flightNumber;
		
	    public void setFlightNumber(String flightId) {
	        this.flightNumber = flightId;
	    }
	    
	    @FXML
	    private void initialize() {
	        noButton.setOnAction(event -> confirmFlight());
	        yesButton.setOnAction(event -> cancelFlight());
	   
	    }

	    @FXML
	    private void confirmFlight() {
	    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Flight Confirmation");
	        alert.setHeaderText(null);
	        alert.setContentText("Flight " + flightNumber + " has not been cancelled.");
	        alert.showAndWait();
	    	System.out.println("View Schedule..");
			 try {
			        // Load the ViewExistingSchedule.fxml file
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/UpdateOrCancelFlight.fxml"));
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
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("/managerViews/UpdateOrCancelFlight.fxml"));
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