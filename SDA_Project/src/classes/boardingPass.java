package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Random;
import util.DBConnection;

public class boardingPass {
    private int boardingPassId;
    private int checkInId;
    private String flightNumber;
    private String passengerName;
    private String seatNumber;
    private String gate;
    private LocalTime boardingTime;

    // Constructors
    public boardingPass() {}

    public boardingPass(int checkInId, String flightNumber, String passengerName, String seatNumber, String gate, LocalTime boardingTime) {
        this.checkInId = checkInId;
        this.flightNumber = flightNumber;
        this.passengerName = passengerName;
        this.seatNumber = seatNumber;
        this.gate = gate;
        this.boardingTime = boardingTime;
    }

    // Getters and setters
    public int getBoardingPassId() {
        return boardingPassId;
    }

    public void setBoardingPassId(int boardingPassId) {
        this.boardingPassId = boardingPassId;
    }

    public int getCheckInId() {
        return checkInId;
    }

    public void setCheckInId(int checkInId) {
        this.checkInId = checkInId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public LocalTime getBoardingTime() {
        return boardingTime;
    }

    public void setBoardingTime(LocalTime boardingTime) {
        this.boardingTime = boardingTime;
    }

    // Method to save boarding pass information to the database
    public boolean saveBoardingPass() {
        try (Connection connection = DBConnection.getConnection1();
             PreparedStatement statement = connection.prepareStatement(
                 "INSERT INTO BoardingPass (checkInId, flightNumber, passengerName, seatNumber, gate, boardingTime) VALUES (?, ?, ?, ?, ?, ?)",
                 PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, checkInId);
            statement.setString(2, flightNumber);
            statement.setString(3, passengerName);
            statement.setString(4, seatNumber);
            statement.setString(5, gate);
            statement.setTime(6, Time.valueOf(boardingTime));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating boarding pass failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.boardingPassId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating boarding pass failed, no ID obtained.");
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get boarding pass details by checkInId
    public static boardingPass getBoardingPassByCheckInId(int checkInId) {
        try (Connection connection = DBConnection.getConnection1();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM BoardingPass WHERE checkInId = ?")) {
            statement.setInt(1, checkInId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    boardingPass bp = new boardingPass();
                    bp.setBoardingPassId(rs.getInt("boardingPassId"));
                    bp.setCheckInId(rs.getInt("checkInId"));
                    bp.setFlightNumber(rs.getString("flightNumber"));
                    bp.setPassengerName(rs.getString("passengerName"));
                    bp.setSeatNumber(rs.getString("seatNumber"));
                    bp.setGate(rs.getString("gate"));
                    bp.setBoardingTime(rs.getTime("boardingTime").toLocalTime());
                    return bp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to create a new boarding pass
    public static boolean createBoardingPass(int checkInId, String flightNumber, int passengerId, String seatNumber) {
        // Retrieve necessary information
        String passengerName = getPassengerNameById(passengerId);
        String gate = generateRandomGate();
        LocalTime boardingTime = calculateBoardingTime();

        boardingPass bp = new boardingPass(checkInId, flightNumber, passengerName, seatNumber, gate, boardingTime);
        return bp.saveBoardingPass();
    }

    // Helper method to calculate boarding time
    private static LocalTime calculateBoardingTime() {
        return LocalTime.now();
    }

    // Helper method to retrieve passenger name by ID
    private static String getPassengerNameById(int passengerId) {
        try (Connection connection = DBConnection.getConnection1();
             PreparedStatement statement = connection.prepareStatement("SELECT full_name FROM Passengers WHERE passenger_id = ?")) {
            statement.setInt(1, passengerId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("full_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Helper method to generate a random gate number
    private static String generateRandomGate() {
        Random rand = new Random();
        int gateNumber = rand.nextInt(10) + 1; // Random number between 1 and 10
        char gateLetter = (char) (rand.nextInt(4) + 'A'); // Random letter between A and D
        return gateNumber + "" + gateLetter;
    }
}
