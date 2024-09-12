package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import util.DBConnection;

public class payment {
    private int paymentId;
    private int bookingId;
    private int passengerId;
    private double amount;
    private Date paymentDate;

    public payment(int bookingId, int passengerId, double amount, Date paymentDate) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public static boolean recordPayment(int bookingId, int passengerId, double amount, Date paymentDate) {
        String sql = "INSERT INTO Payment (bookingId, passengerId, amount, payment_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ps.setInt(2, passengerId);
            ps.setDouble(3, amount);
            ps.setDate(4, paymentDate);

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static double calculateBaggagePaymentAmount(int baggageLimit) {
        double fixedAmount = 10.0; // Default fixed amount

        // Calculate payment amount based on the baggage limit range
        if (baggageLimit >= 1 && baggageLimit <= 10) {
            fixedAmount = 1000.0;
        } else if (baggageLimit > 10 && baggageLimit <= 20) {
            fixedAmount = 1500.0;
        } else if (baggageLimit > 20 && baggageLimit <= 30) {
            fixedAmount = 2400.0;
        } else if (baggageLimit > 30 && baggageLimit <= 40) {
            fixedAmount = 3200.0;
        } else if (baggageLimit > 40 && baggageLimit <= 50) {
            fixedAmount = 5000.0;
        } else if (baggageLimit > 50 && baggageLimit <= 60) {
            fixedAmount = 5800.0;
        } else if (baggageLimit > 60 && baggageLimit <= 70) {
            fixedAmount = 6500.0;
        } else if (baggageLimit > 70 && baggageLimit <= 80) {
            fixedAmount = 7900.0;
        } else if (baggageLimit > 80 && baggageLimit <= 90) {
            fixedAmount = 8700.0;
        } else if (baggageLimit > 90 && baggageLimit <= 100) {
            fixedAmount = 9900.0;
        }

        return fixedAmount;
    }

}
