package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import util.DBConnection;

public class checkIn {
    private int checkInId;
    private int bookingId;
    private int passengerId;
    private Timestamp checkInTime;
    private String seatNumber;
    private boolean isCheckedIn;

    // Constructors
    public checkIn() {}

    public checkIn(int bookingId, int passengerId, Timestamp checkInTime, String seatNumber, boolean isCheckedIn) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.checkInTime = checkInTime;
        this.seatNumber = seatNumber;
        this.isCheckedIn = isCheckedIn;
    }

    // Getters and setters
    public int getCheckInId() {
        return checkInId;
    }

    public void setCheckInId(int checkInId) {
        this.checkInId = checkInId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public Timestamp getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Timestamp checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(boolean isCheckedIn) {
        this.isCheckedIn = isCheckedIn;
    }

    // Method to save check-in information to the database
    public boolean saveCheckIn() {
        try (Connection connection = DBConnection.getConnection1();
             PreparedStatement statement = connection.prepareStatement(
                 "INSERT INTO CheckIn (bookingId, passenger_id, checkInTime, seatNumber, isCheckedIn) VALUES (?, ?, ?, ?, ?)",
                 PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, bookingId);
            statement.setInt(2, passengerId);
            statement.setTimestamp(3, checkInTime);
            statement.setString(4, seatNumber);
            statement.setBoolean(5, isCheckedIn);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating check-in failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.checkInId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating check-in failed, no ID obtained.");
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to check if a check-in record already exists for a booking
    private static boolean checkInExists(int bookingId) {
        try (Connection connection = DBConnection.getConnection1();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM CheckIn WHERE bookingId = ?")) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // If there is a next, a check-in already exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to check in a passenger using booking reference
    public static boolean checkInPassenger(String bookingReference) {
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT b.bookingId, b.passengerId, b.seatNumber, f.flightNumber " +
                 "FROM Bookings b " +
                 "JOIN Flight f ON b.flightNumber = f.flightNumber " +
                 "WHERE b.bookingReference = ?")) {
            ps.setString(1, bookingReference);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int bookingId = rs.getInt("bookingId");
                    int passengerId = rs.getInt("passengerId");
                    String seatNumber = rs.getString("seatNumber");
                    String flightNumber = rs.getString("flightNumber");

                    // Check if there is an existing check-in record for this booking
                    if (checkInExists(bookingId)) {
                        return false; // Already checked in
                    }

                    // Create check-in record
                    checkIn newCheckIn = new checkIn(bookingId, passengerId, new Timestamp(System.currentTimeMillis()), seatNumber, true);
                    boolean checkInSuccess = newCheckIn.saveCheckIn();

                    if (checkInSuccess) {
                        // Create boarding pass
                        boardingPass.createBoardingPass(newCheckIn.getCheckInId(), flightNumber, passengerId, seatNumber);
                        return true;
                    }
                }
                return false; // Booking does not exist
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getCheckInIdByBookingReference(String bookingReference) {
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT c.checkInId " +
                 "FROM CheckIn c " +
                 "JOIN Bookings b ON c.bookingId = b.bookingId " +
                 "WHERE b.bookingReference = ?")) {
            ps.setString(1, bookingReference);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("checkInId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return an invalid ID if not found
    }
}
