package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.DBConnection;

public class baggage {
    private int baggageId;
    private int bookingId;
    private int passengerId;
    private int baggageLimit;
    private double baggageWeight;

    public baggage(int baggageId, int bookingId, int passengerId, int baggageLimit, double baggageWeight) {
        this.baggageId = baggageId;
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.baggageLimit = baggageLimit;
        this.baggageWeight = baggageWeight;
    }

    public int getBaggageId() {
        return baggageId;
    }

    public void setBaggageId(int baggageId) {
        this.baggageId = baggageId;
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

    public int getBaggageLimit() {
        return baggageLimit;
    }

    public void setBaggageLimit(int baggageLimit) {
        this.baggageLimit = baggageLimit;
    }

    public double getBaggageWeight() {
        return baggageWeight;
    }

    public void setBaggageWeight(double baggageWeight) {
        this.baggageWeight = baggageWeight;
    }
    
    public static boolean createBaggage(int bookingId, int passengerId, int baggageLimit, double baggageWeight) {
        Connection connection = DBConnection.getConnection1();

        try {
            String query = "INSERT INTO Baggage (bookingId, passenger_id, baggageLimit, baggageWeight) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, bookingId);
            preparedStatement.setInt(2, passengerId);
            preparedStatement.setInt(3, baggageLimit);
            preparedStatement.setDouble(4, baggageWeight);
            preparedStatement.executeUpdate();

            DBConnection.close(connection, preparedStatement);

            return true; 
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; 
    }
}
