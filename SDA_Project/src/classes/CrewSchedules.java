package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CrewSchedules {
	 private int scheduleID;
	    private int crewID;
	    private String flightID;
	    private String aircraftCompartment;

	    
	
	    // Getters and setters
	    public int getScheduleID() {
	        return scheduleID;
	    }

	    public void setScheduleID(int scheduleID) {
	        this.scheduleID = scheduleID;
	    }

	    public int getCrewID() {
	        return crewID;
	    }

	    public void setCrewID(int crewID) {
	        this.crewID = crewID;
	    }

	    public String getFlightID() {
	        return flightID;
	    }

	    public void setFlightID(String flightID) {
	        this.flightID = flightID;
	    }

	    public String getAircraftCompartment() {
	        return aircraftCompartment;
	    }

	    public void setAircraftCompartment(String aircraftCompartment) {
	        this.aircraftCompartment = aircraftCompartment;
	    }
	    
	    public static List<Crew> getAvailableCrewForDate(LocalDate date) throws SQLException {
	        List<Crew> availableCrew = new ArrayList<>();
	        
	        // Establish connection and prepare SQL statement
	        try (Connection conn = util.DBConnection.getConnection1();
	             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Crew WHERE NOT EXISTS " +
	                     "(SELECT * FROM CrewSchedules WHERE CrewSchedules.crewID = Crew.id AND date = ?)")) {
	            
	            // Set the date parameter
	            stmt.setDate(1, Date.valueOf(date));
	            
	            // Execute the query
	            ResultSet rs = stmt.executeQuery();
	            
	            // Iterate over the result set and create CrewMember objects
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String name = rs.getString("name");
	                // Other properties
	                
	                // Create CrewMember object and add it to the list
	                Crew crewMember = new Crew();
	                crewMember.setCrewId(id);
	                crewMember.setName(name);
	               availableCrew.add(crewMember);
	            }
	        }
	        
	        return availableCrew;
	    }
	    
	   
	    
	    private String crewName;
	    private String crewRole;

	    // Getters and setters for Crew details
	    public String getCrewName() {
	        return crewName;
	    }

	    public void setCrewName(String crewName) {
	        this.crewName = crewName;
	    }

	    public String getCrewRole() {
	        return crewRole;
	    }

	    public void setCrewRole(String crewRole) {
	        this.crewRole = crewRole;
	    }
	    
	    public static List<CrewSchedules> getAllCrewSchedules() throws SQLException {
	        List<CrewSchedules> crewSchedules = new ArrayList<>();
	        try (Connection connection = util.DBConnection.getConnection1()) {
	            String query = "SELECT * FROM CrewSchedules";
	            PreparedStatement statement = connection.prepareStatement(query);
	            ResultSet resultSet = statement.executeQuery();
	            
	            while (resultSet.next()) {
	                CrewSchedules schedule = new CrewSchedules();
	                schedule.setScheduleID(resultSet.getInt("scheduleID"));
	                schedule.setCrewID(resultSet.getInt("crewID"));
	                schedule.setFlightID(resultSet.getString("flightID"));
	                schedule.setAircraftCompartment(resultSet.getString("aircraftCompartment"));
	                crewSchedules.add(schedule);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw e;
	        }
	        return crewSchedules;
	    }
	    
	    public static List<CrewSchedules> getAllCrewSchedulesWithDetails() throws SQLException {
	        List<CrewSchedules> schedules = new ArrayList<>();
	        Connection connection =  util.DBConnection.getConnection1();
	        String query = "SELECT cs.scheduleID, cs.crewID, cs.flightID, cs.aircraftCompartment, c.name, c.role " +
	                       "FROM CrewSchedules cs " +
	                       "JOIN Crew c ON cs.crewID = c.crewID";

	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);

	        while (resultSet.next()) {
	            CrewSchedules schedule = new CrewSchedules();
	            schedule.setScheduleID(resultSet.getInt("scheduleID"));
	            schedule.setCrewID(resultSet.getInt("crewID"));
	            schedule.setFlightID(resultSet.getString("flightID"));
	            schedule.setAircraftCompartment(resultSet.getString("aircraftCompartment"));
	            schedule.setCrewName(resultSet.getString("name"));
	            schedule.setCrewRole(resultSet.getString("role"));
	            schedules.add(schedule);
	        }

	        resultSet.close();
	        statement.close();
	        connection.close();

	        return schedules;
	    }
	    
	    public static boolean addNewSchedule(int crewID, String flightID, String aircraftCompartment) {
	        // Database connection and insertion logic here
	        try {
	            Connection connection =  util.DBConnection.getConnection1();   
	            PreparedStatement statement =
	            		connection.prepareStatement("INSERT INTO CrewSchedules (crewID, flightID, aircraftCompartment) "
	            				+ " VALUES (?, ?, ?)");
	            statement.setInt(1, crewID);
	            statement.setString(2, flightID); // Assuming you have the flight ID available
	            statement.setString(3, aircraftCompartment); // Assuming you have the aircraft compartment available
	            int rowsAffected = statement.executeUpdate();
	            statement.close();
	            connection.close();
	            return rowsAffected > 0; // Return true if insertion was successful
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
}