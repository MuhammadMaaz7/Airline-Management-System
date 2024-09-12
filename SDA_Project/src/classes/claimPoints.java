package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBConnection;

public class claimPoints {
	 private int id;
	    private int bookingId;
	    private String membershipId;
	    private boolean claimedPoints;

	    public claimPoints(int id, int bookingId, String membershipId, boolean claimedPoints) {
	        this.id = id;
	        this.bookingId = bookingId;
	        this.membershipId = membershipId;
	        this.claimedPoints = claimedPoints;
	    }

	    // Getters and setters
	    public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }

	    public int getBookingId() {
	        return bookingId;
	    }

	    public void setBookingId(int bookingId) {
	        this.bookingId = bookingId;
	    }

	    public String getMembershipId() {
	        return membershipId;
	    }

	    public void setMembershipId(String membershipId) {
	        this.membershipId = membershipId;
	    }

	    public boolean isClaimedPoints() {
	        return claimedPoints;
	    }

	    public void setClaimedPoints(boolean claimedPoints) {
	        this.claimedPoints = claimedPoints;
	    }
	    public static boolean isPointsClaimed(int bookingId) {
	        boolean claimed = false;
	        try (Connection conn = DBConnection.getConnection1()) {
	            String query = "SELECT claimedpoints FROM claimpoints WHERE bookingid = ?";
	            PreparedStatement statement = conn.prepareStatement(query);
	            statement.setInt(1, bookingId);
	            ResultSet resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                claimed = resultSet.getBoolean("claimedpoints");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return claimed;
	    }

	    public static boolean claimPoints(int bookingId) {
	        try (Connection conn = DBConnection.getConnection1()) {
	            // Get the membership number and points to be claimed from bookings and loyalty program
	            String query = "SELECT lp.membershipNumber, lp.pointsEarned, lp.milesTravelled " +
	                           "FROM bookings b JOIN LoyaltyProgram lp ON b.passengerId = lp.passengerId " +
	                           "WHERE b.bookingId = ?";
	            PreparedStatement statement = conn.prepareStatement(query);
	            statement.setInt(1, bookingId);
	            ResultSet resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	                String membershipNumber = resultSet.getString("membershipNumber");
	                int pointsEarned = resultSet.getInt("pointsEarned");
	                int milesTravelled = resultSet.getInt("milesTravelled");
	                
	                // Assuming pointsEarned is calculated based on milesTravelled
	                int pointsToAdd = milesTravelled / 100;  // Example conversion rate
	                
	                // Update the points in loyalty program
	                query = "UPDATE LoyaltyProgram SET pointsEarned = pointsEarned + ? WHERE membershipNumber = ?";
	                PreparedStatement updateStatement = conn.prepareStatement(query);
	                updateStatement.setInt(1, pointsToAdd);
	                updateStatement.setString(2, membershipNumber);
	                updateStatement.executeUpdate();
	                
	                // Update the claimpoints table
	                query = "INSERT INTO claimpoints (bookingid, membershipid, claimedpoints) VALUES (?, ?, ?)";
	                updateStatement = conn.prepareStatement(query);
	                updateStatement.setInt(1, bookingId);
	                updateStatement.setString(2, membershipNumber);
	                updateStatement.setBoolean(3, true);
	                updateStatement.executeUpdate();
	                
	                return true;
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

}