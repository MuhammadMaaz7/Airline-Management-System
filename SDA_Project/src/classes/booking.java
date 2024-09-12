package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.sql.Statement;

import javafx.scene.control.Alert;
import util.DBConnection;

import javafx.scene.control.TableView;
import java.sql.Timestamp;

public class booking {
    private int bookingId;
    private int passengerId;
    private String flightNumber;
    private int seatNumber;
    private LocalDateTime bookingDate;
    private boolean status;
    private String bookingReference;
    
    public booking() {
    	
    	}

    
    public booking(int bookingId, int passengerId, String flightNumber, int seatNumber, LocalDateTime bookingDate,
                    boolean status, String bookingReference) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.flightNumber = flightNumber;
        this.seatNumber = seatNumber;
        this.bookingDate = bookingDate;
        this.status = status;
        this.bookingReference = bookingReference;
    }  
    
        // Getters and setters...
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

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public static String createBooking(int passengerId, String flightNumber) {
        Connection connection = DBConnection.getConnection1();
        seat availableSeat = seat.getRandomAvailableSeat(flightNumber);

        if (availableSeat != null) {
            int seatNumber = availableSeat.getSeatNumber();
            LocalDateTime bookingDate = LocalDateTime.now();
            boolean status = false;    // status true after payment
            String bookingReference = generateBookingReference();

            try {
                String query = "INSERT INTO bookings (passengerId, flightNumber, seatNumber, bookingDate, status, bookingReference) " +
                               "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, passengerId);
                preparedStatement.setString(2, flightNumber);
                preparedStatement.setInt(3, seatNumber);
                preparedStatement.setObject(4, bookingDate);
                preparedStatement.setBoolean(5, status);
                preparedStatement.setString(6, bookingReference);
                preparedStatement.executeUpdate();

                // Update seat availability status
                availableSeat.setAvailabilityStatus(false);
                updateSeatAvailability(availableSeat);

                DBConnection.close(connection, preparedStatement);

                return bookingReference;  // Return the booking reference
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
//            System.out.println("No available seat.");s
        }
        return null;  // Return null if booking creation fails
    }

    private static String generateBookingReference() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder bookingReference = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            bookingReference.append(characters.charAt(random.nextInt(characters.length())));
        }

        return bookingReference.toString();
    }

    private static void updateSeatAvailability(seat seat) {
        Connection connection = DBConnection.getConnection1();

        try {
            String query = "UPDATE seats SET availabilityStatus = ? WHERE seatId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, seat.isAvailabilityStatus());
            preparedStatement.setInt(2, seat.getSeatId());
            preparedStatement.executeUpdate();
            DBConnection.close(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBookingStatus(String bookingReference, boolean status) {
        Connection connection = DBConnection.getConnection1();

        try {
            String query = "UPDATE bookings SET status = ? WHERE bookingReference = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, status);
            preparedStatement.setString(2, bookingReference);
            preparedStatement.executeUpdate();
            DBConnection.close(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBookingStatusbyId(int bookingId, boolean status) {
        Connection connection = DBConnection.getConnection1();

        try {
            String query = "UPDATE bookings SET status = ? WHERE bookingId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, status);
            preparedStatement.setInt(2, bookingId);
            preparedStatement.executeUpdate();
            DBConnection.close(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static String getFlightNumberByBookingReference(String bookingReference) {
        Connection connection = DBConnection.getConnection1();
        String flightNumber = null;

        try {
            String query = "SELECT flightNumber FROM booking WHERE bookingReference = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookingReference);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                flightNumber = resultSet.getString("flightNumber");
            }
            
            resultSet.close();
            DBConnection.close(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flightNumber;
    }
    
    public static booking getBookingByReference(String bookingReference) {
        Connection connection = DBConnection.getConnection1();
        booking booking = null;

        try {
            String query = "SELECT * FROM bookings WHERE bookingReference = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookingReference);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int bookingId = resultSet.getInt("bookingId");
                int passengerId = resultSet.getInt("passengerId");
                String flightNumber = resultSet.getString("flightNumber");
                int seatNumber = resultSet.getInt("seatNumber");
                LocalDateTime bookingDate = resultSet.getTimestamp("bookingDate").toLocalDateTime();
                boolean status = resultSet.getBoolean("status");
                String reference = resultSet.getString("bookingReference");

                booking = new booking(bookingId, passengerId, flightNumber, seatNumber, bookingDate, status, reference);
            }

            resultSet.close();
            DBConnection.close(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }
    
    public static void FindBooking(String flightnumber2, String passengerid, int bookingId2) {
        Statement stmt = DBConnection.getConnection();
        String query = "SELECT * FROM bookings WHERE bookingId = " + bookingId2 + " AND passengerId = " + passengerid + " AND flightNumber = '" + flightnumber2 + "'";

        try (ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                int passengerId = rs.getInt("passengerId");
                String flightNumber = rs.getString("flightNumber");
                int seatNumber = rs.getInt("seatNumber");
                LocalDateTime bookingDate = rs.getTimestamp("bookingDate").toLocalDateTime();
                boolean status = rs.getBoolean("status");
                String bookingReference = rs.getString("bookingReference");

                booking booking = new booking(bookingId2, passengerId, flightNumber, seatNumber, bookingDate, status, bookingReference);
                showAlert("Booking Details", booking.toString());
            } else {
                // Check if the booking exists but passenger ID or flight number is incorrect
                String query1 = "SELECT * FROM bookings WHERE bookingId = " + bookingId2;
                ResultSet rs1 = stmt.executeQuery(query1);
                if (rs1.next()) {
                    int existingPassengerId = rs1.getInt("passengerId");
                    String existingFlightNumber = rs1.getString("flightNumber");
                    if (existingPassengerId != Integer.parseInt(passengerid)) {
                        showAlert("Error", "Passenger ID is not correct.");
                    } else if (!existingFlightNumber.equals(flightnumber2)) {
                        showAlert("Error", "Flight number is not correct.");
                    }
                } else {
                    showAlert("Error", "Booking not found.");
                }
            }
        } catch (SQLException e) {
        	System.out.println("error here");
            e.printStackTrace();
        } finally {
            DBConnection.Dispose();
        }
    }

    private static void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static booking fetchBookingById(int bookingId2) {
        try (Statement stmt = DBConnection.getConnection()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM bookings WHERE bookingId = " + bookingId2);
            if (rs.next()) {
                return new booking(
                    rs.getInt("bookingId"),
                    rs.getInt("passengerId"),
                    rs.getString("flightNumber"),
                    rs.getInt("seatNumber"),
                    rs.getTimestamp("bookingDate").toLocalDateTime(),
                    rs.getBoolean("status"),
                    rs.getString("bookingReference")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean updateSeatNumber(int bookingId, int newSeatNumber) {
        if (!seat.checkSeatAvailability(newSeatNumber)) {
            return false;
        }

        try (Statement stmt = DBConnection.getConnection()) {
            String sql = "UPDATE bookings SET seatNumber = " + newSeatNumber + " WHERE bookingId = " + bookingId;
            int rowsUpdated = stmt.executeUpdate(sql);
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static List<booking> loadFlightsBookedByPassenger(int passengerId) {
        List<booking> bookedFlights = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection1();
            String query = "SELECT * FROM bookings WHERE passengerId = ?";          
            
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, passengerId);
     
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
            	booking booking = new booking();
                booking.setBookingId(resultSet.getInt("bookingId"));
                booking.setPassengerId(resultSet.getInt("passengerId"));
                booking.setFlightNumber(resultSet.getString("flightNumber"));
                booking.setSeatNumber(resultSet.getInt("seatNumber"));
                
                Timestamp timestamp = resultSet.getTimestamp("bookingDate");
                if (timestamp != null) {
                    LocalDateTime bookingDate = timestamp.toLocalDateTime();
                    booking.setBookingDate(bookingDate);
                }
                booking.setStatus(resultSet.getBoolean("status"));
                booking.setBookingReference(resultSet.getString("bookingReference"));

                bookedFlights.add(booking);      
                }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }

        return bookedFlights;
    }
    
    public static int getBookingIdFromPNR(String bookingPNR) {
        int foundBookingId = -1; // Default value if booking is not found
        try {
            Connection conn = DBConnection.getConnection1();
            String query = "SELECT bookingId FROM bookings WHERE bookingReference = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, bookingPNR);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                foundBookingId = resultSet.getInt("bookingId");
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundBookingId;
    }
    
    
    
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", passengerId=" + passengerId +
                ", flightNumber='" + flightNumber + '\'' +
                ", seatNumber=" + seatNumber +
                ", bookingDate='" + bookingDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + '\'' +
                ", status=" + status +
                ", bookingReference='" + bookingReference + '\'' +
                '}';
    }

}
