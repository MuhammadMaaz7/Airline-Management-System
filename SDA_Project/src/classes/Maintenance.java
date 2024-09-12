package classes;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import util.DBConnection;

public class Maintenance {
	private int maintenanceId;
    private int aircraftId;
    private String maintenanceType;
    private LocalDate scheduledDate;
    private boolean completionStatus;
Crew crew=new Crew();
    // Constructor, Getters, and Setters
    public Maintenance(int maintenanceId, int aircraftId, String maintenanceType, LocalDate scheduledDate, boolean completionStatus) {
        this.maintenanceId = maintenanceId;
        this.aircraftId = aircraftId;
        this.maintenanceType = maintenanceType;
        this.scheduledDate = scheduledDate;
        this.completionStatus = completionStatus;
    }

    public Maintenance() {}

    public boolean validateAircraft(int aircraftId) {
        return Aircraft.checkAircraftAvailability(aircraftId);
    }

    public boolean createEntry(int aircraftId, String maintenanceType, LocalDate scheduledDate) {
        try (Statement stmt = DBConnection.getConnection()) {
            String sql = "INSERT INTO maintenance (aircraftId, maintenanceType, scheduledDate, completionStatus) VALUES (" +
                         aircraftId + ", '" + maintenanceType + "', '" + scheduledDate + "', false)";
            int rowsInserted = stmt.executeUpdate(sql);
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Crew CheckStaff(int staffId) {
        // First, check staff availability using the Crew class
        Crew availableStaff = Crew.getAvailableStaff(staffId);
        if (availableStaff != null) {
            return availableStaff;
        } else {
            return null;
        }
    }

    public boolean assigningstaff(int maintenanceid,int staffId) {
    	 try (Statement stmt = DBConnection.getConnection()) {
    	        // Check if the staffId exists in the crew table
    	        if (!crewExists(stmt, staffId)) {
    	            // Handle the case where the staffId doesn't exist in the crew table
    	            System.err.println("Staff with ID " + staffId + " does not exist in the crew table.");
    	            return false;
    	        }

    	        // Update the maintenance record with the crewId
    	        int rowsAffected = stmt.executeUpdate("UPDATE maintenance SET crewId = " + staffId + " WHERE maintenanceId = " + maintenanceid);
    	        return rowsAffected > 0;
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	        return false;
    	    }
    	}

    	private boolean crewExists(Statement stmt, int crewId) throws SQLException {
    	    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM crew WHERE crewId = " + crewId);
    	    rs.next();
    	    int count = rs.getInt(1);
    	    return count > 0;
    }
    	
    	
    	  public int createImpromptuEntry(int aircraftId, String maintenanceType) {
    		  try (Statement stmt = DBConnection.getConnection()) {
    	            String sql = "INSERT INTO maintenance (aircraftId, maintenanceType,  completionStatus) VALUES (" +
    	                         aircraftId + ", '" + maintenanceType + "', false)";
    	            int rowsInserted = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
    	            if (rowsInserted > 0) {
    	                ResultSet rs = stmt.getGeneratedKeys();
    	                if (rs.next()) {
    	                    return rs.getInt(1); // Return the generated maintenanceId
    	                }
    	            }
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	        return -1; // Return -1 indicating failure
    	    }
}