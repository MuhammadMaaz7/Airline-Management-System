package passengerControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import classes.bankAccounts;
import classes.booking;
import classes.payment;
import classes.ticket;
import classes.seat;
import util.DBConnection;

public class paymentController {

    @FXML
    private Label totalFareLabel;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField cardHolderNameField;

    @FXML
    private DatePicker expiryDatePicker;

    @FXML
    private TextField cvvField;

    @FXML
    private Button payNowButton;

    private double totalFare;
    private String bookingReference;

    public void setTotalFare(double fare) {
        totalFare = fare;
        totalFareLabel.setText(String.format("%.2f", fare));
    }

    public void setBookingReference(String reference) {
        bookingReference = reference;
    }

    @FXML
    public void initialize() {
        payNowButton.setOnAction(event -> handlePayNowButton());
    }

    @FXML
    private void handlePayNowButton() {
        String cardNumber = cardNumberField.getText();
        String cardHolderName = cardHolderNameField.getText();
        String cvvText = cvvField.getText();

        // Validate inputs
        if (cardNumber.isEmpty() || cardHolderName.isEmpty() || cvvText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill in all fields.");
            return;
        }

        if (!cardNumber.matches("\\d{16}")) {
            showAlert(Alert.AlertType.ERROR, "Card number must be 16 digits.");
            return;
        }

        LocalDate expiryDate = expiryDatePicker.getValue();
        if (expiryDate == null) {
            showAlert(Alert.AlertType.ERROR, "Please select an expiry date.");
            return;
        }

        // Convert CVV to integer and check if it is three digits
        int cvv;
        try {
            cvv = Integer.parseInt(cvvText);
            if (cvv < 100 || cvv > 999) {
                showAlert(Alert.AlertType.ERROR, "CVV must be a 3-digit number.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "CVV must be a valid number.");
            return;
        }

        // Verify card details with BankAccounts
        bankAccounts bankAccount = new bankAccounts();
        boolean paymentProcessed = bankAccount.processPayment(cardNumber, cardHolderName, java.sql.Date.valueOf(expiryDate), cvv, totalFare);

        if (paymentProcessed) {
            // Update booking status
            booking.updateBookingStatus(bookingReference, true);
            showAlert(Alert.AlertType.INFORMATION, "Payment successful. Booking status updated.");

            // Retrieve booking details and create ticket
            if (createTicket()) {
                // Record the payment
            	showAlert(Alert.AlertType.INFORMATION, "Ticket created successfully.");
                if (recordPayment()) {
//                	showAlert(Alert.AlertType.INFORMATION, "Payment Recorded.");	
                    switchToMainPage("/passengerViews/passengerHomeScreen.fxml");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Failed to record payment.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to create ticket.");
            }
        } else {
//            showAlert(Alert.AlertType.ERROR, "Payment failed. Please check your card details.");
        }
    }

    private boolean createTicket() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection1();

            // Retrieve booking details
            String sql = "SELECT bookingId, passengerId, flightNumber, seatNumber FROM bookings WHERE bookingReference = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, bookingReference);
            rs = ps.executeQuery();

            if (rs.next()) {
                int bookingId = rs.getInt("bookingId");
                int passengerId = rs.getInt("passengerId");
                String flightNumber = rs.getString("flightNumber");
                int seatNumber = rs.getInt("seatNumber");

                // Create ticket using the ticket class
                boolean ticketCreated = ticket.createTicket(bookingId, passengerId, flightNumber, seatNumber);

                // Update seat availability
                if (ticketCreated) {
                    seat.updateSeatAvailability(seatNumber, false); // Assuming seatNumber is the seat selected by the passenger
                    return true;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean recordPayment() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection1();

            // Retrieve booking details
            String sql = "SELECT bookingId, passengerId FROM bookings WHERE bookingReference = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, bookingReference);
            rs = ps.executeQuery();

            if (rs.next()) {
                int bookingId = rs.getInt("bookingId");
                int passengerId = rs.getInt("passengerId");
                Date paymentDate = Date.valueOf(LocalDate.now());
                return payment.recordPayment(bookingId, passengerId, totalFare, paymentDate);
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.name());
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void switchToMainPage(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = payNowButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
