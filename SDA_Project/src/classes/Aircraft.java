package classes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.DBConnection;

public class Aircraft {
	 private int aircraftId;
	    private String aircraftType;
	    private int capacity;
	    private String status;
	    
	    private static List<String> aircraftCompartments;

	    // Constructor, Getters, and Setters
	    public Aircraft(int aircraftId, String aircraftType, int capacity, String status) {
	        this.aircraftId = aircraftId;
	        this.aircraftType = aircraftType;
	        this.capacity = capacity;
	        this.status = status;
	    }
	    
	    public Aircraft() {
	    }
	    
	    public static ObservableList<String> setAircraftCompartmentsFromDatabase() {
	        // Initialize the list to store compartments
	        aircraftCompartments = new ArrayList<>();

	        // Connection and statement objects
	        try (Connection connection = util.DBConnection.getConnection1();
	             Statement statement = connection.createStatement()) {

	            // Execute query to fetch compartments
	            String sql = "SELECT compartmentName FROM AircraftCompartments";
	            ResultSet resultSet = statement.executeQuery(sql);

	            // Iterate over the result set and add compartments to the list
	            while (resultSet.next()) {
	                String compartmentName = resultSet.getString("compartmentName");
	                aircraftCompartments.add(compartmentName);
	            }

	            // Close the result set
	            resultSet.close();

	        } catch (SQLException e) {
	            // Handle the exception gracefully
	            e.printStackTrace();
	            // You might throw a custom exception or log the error
	        }
	        
	        // Return the list as an ObservableList
	        return FXCollections.observableArrayList(aircraftCompartments);
	    }
	    
	 // Getters and Setters
	    public int getAircraftId() {
	        return aircraftId;
	    }

	    public void setAircraftId(int aircraftId) {
	        this.aircraftId = aircraftId;
	    }

	    public String getAircraftType() {
	        return aircraftType;
	    }

	    public void setAircraftType(String aircraftType) {
	        this.aircraftType = aircraftType;
	    }

	    public int getCapacity() {
	        return capacity;
	    }

	    public void setCapacity(int capacity) {
	        this.capacity = capacity;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    // toString() method for debugging
	    @Override
	    public String toString() {
	        return "Aircraft [aircraftId=" + aircraftId + ", aircraftType=" + aircraftType + ", capacity=" + capacity
	                + ", status=" + status + "]";
	    }

	    public static boolean checkAircraftAvailability(int aircraftId) {
	        try (Statement stmt = DBConnection.getConnection()) {
	            ResultSet rs = stmt.executeQuery("SELECT status FROM aircraft WHERE aircraftId = " + aircraftId);
	            if (rs.next()) {
	                String status = rs.getString("status");
	                return status.equalsIgnoreCase("available");
	                
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	    
}