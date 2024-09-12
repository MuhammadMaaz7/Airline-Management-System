package classes;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class seat {
    private int seatId;
    private int seatNumber;
    private boolean availabilityStatus;
    private String flightNumber;

    public seat(int seatId, int seatNumber, boolean availabilityStatus, String flightNumber) {
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.availabilityStatus = availabilityStatus;
        this.flightNumber = flightNumber;
    }

    // Getters and setters
    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(boolean availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    // Function to fetch a random available seat for a specific Flight from the database
    public static seat getRandomAvailableSeat(String flightNumber) {
        List<seat> availableSeats = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection1();
            String query = "SELECT * FROM seats WHERE availabilityStatus = true AND flightNumber = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, flightNumber);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int seatId = resultSet.getInt("seatId");
                int seatNumber = resultSet.getInt("seatNumber");
                boolean availabilityStatus = resultSet.getBoolean("availabilityStatus");
                availableSeats.add(new seat(seatId, seatNumber, availabilityStatus, flightNumber));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection, statement);
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (!availableSeats.isEmpty()) {
            Random random = new Random();
            return availableSeats.get(random.nextInt(availableSeats.size()));
        } else {
            return null; // No available seat
        }
    }

    // Method to update the seat availability status
    public static void updateSeatAvailability(int seatId, boolean availabilityStatus) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection1();
            String query = "UPDATE seats SET availabilityStatus = ? WHERE seatId = ?";
            statement = connection.prepareStatement(query);
            statement.setBoolean(1, availabilityStatus);
            statement.setInt(2, seatId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(connection, statement);
        }
    }
    
    public static boolean checkSeatAvailability(int newSeatNumber) {
		 try (Statement stmt = DBConnection.getConnection()) {
	            ResultSet rs = stmt.executeQuery("SELECT availabilityStatus FROM seats WHERE seatNumber = " + newSeatNumber);
	            if (rs.next()) {
	                return true;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
}
