package classes;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBConnection;

public class loyaltyProgram {
	private String membershipNumber;
    private int passengerId;
    private int milesTravelled;
    private int pointsEarned;
    private int flightcount;
    private String passengerName;
    
    public int getTotalFlightCount(int passengerId) {
        int totalFlightCount = 0;
        try {
            Connection conn = DBConnection.getConnection1();
            String query = "SELECT COUNT(*) AS totalFlights FROM bookings WHERE passengerId = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, passengerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                totalFlightCount = resultSet.getInt("totalFlights");
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }
        return totalFlightCount;
    }

    
    public loyaltyProgram() {
        // Initialize variables
    }

	    public loyaltyProgram(String membershipNumber, int passengerId, int milesTravelled, int pointsEarned) {
	        this.membershipNumber = membershipNumber;
	        this.passengerId = passengerId;
	        this.milesTravelled = milesTravelled;
	        this.pointsEarned = pointsEarned;
	    }

	    public String getMembershipNumber() {
	        return membershipNumber;
	    }

	    public void setPassengerName(String passengerName) {
	        this.passengerName = passengerName;
	    }
	    public void setMembershipNumber(String membershipNumber) {
	        this.membershipNumber = membershipNumber;
	    }

	    public int getPassengerId() {
	        return passengerId;
	    }

	    public void setPassengerId(int passengerId) {
	        this.passengerId = passengerId;
	    }

	    public int getMilesTravelled() {
	        return milesTravelled;
	    }

	    public void setMilesTravelled(int milesTravelled) {
	        this.milesTravelled = milesTravelled;
	    }

	    public int getPointsEarned() {
	        return pointsEarned;
	    }

	    public void setPointsEarned(int pointsEarned) {
	        this.pointsEarned = pointsEarned;
	    }
	    
	    public static loyaltyProgram getPassengerDetails(int passengerId) {
	        loyaltyProgram passenger =null;
	        try {
	            Connection conn = DBConnection.getConnection1();
	            String query = "SELECT p.full_name, lp.membershipNumber, lp.milesTravelled, lp.pointsEarned FROM Passengers p " +
	                       "JOIN LoyaltyProgram lp ON p.passenger_id = lp.passengerId " +
	                       "WHERE p.passenger_id = ?";
	         PreparedStatement statement = conn.prepareStatement(query);
	            statement.setInt(1, passengerId);
	            ResultSet resultSet = statement.executeQuery();

	                if (resultSet.next()) {
	                	System.out.println("ResultSet has data.");
	                	 passenger = new loyaltyProgram();
	                    passenger.setPassengerName(resultSet.getString("full_name"));
	                    passenger.setMembershipNumber(resultSet.getString("membershipNumber"));
	                    passenger.setPassengerId(passengerId);
	                    passenger.setMilesTravelled(resultSet.getInt("milesTravelled"));
	                    passenger.setPointsEarned(resultSet.getInt("pointsEarned"));
	                    
	                } else {
	                    System.out.println("No data found for passengerId: " + passengerId);
	                }

	            resultSet.close();
	            statement.close();
	            conn.close();
	            System.out.println("Resources closed.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle database errors
	        }
	        return passenger;
	    }

		public String getPassengerName() {
			return passengerName;
			
		}
		
		 public static String getPassengerName(int passengerId) {
		        String passengerName = null;
		        try {
		            Connection conn = DBConnection.getConnection1();
		            String query = "SELECT full_name FROM Passengers WHERE passenger_id = ?";
		            PreparedStatement statement = conn.prepareStatement(query);
		            statement.setInt(1, passengerId);
		            ResultSet resultSet = statement.executeQuery();

		            if (resultSet.next()) {
		                passengerName = resultSet.getString("full_name");
		            }

		            resultSet.close();
		            statement.close();
		            conn.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		            // Handle database errors
		        }
		        return passengerName;
		    }

		 
		 public static String getMembershipNumber(int passengerId) {
		        String membershipNumber = null;
		        try {
		            Connection conn = DBConnection.getConnection1();
		            String query = "SELECT membershipNumber FROM LoyaltyProgram WHERE passengerId = ?";
		            PreparedStatement statement = conn.prepareStatement(query);
		            statement.setInt(1, passengerId);
		            ResultSet resultSet = statement.executeQuery();

		            if (resultSet.next()) {
		                membershipNumber = resultSet.getString("membershipNumber");
		            }

		            resultSet.close();
		            statement.close();
		            conn.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		            // Handle database errors
		        }
		     
		        return membershipNumber;
		    }
}