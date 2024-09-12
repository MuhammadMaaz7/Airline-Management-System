package managerControllers;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import classes.payment;
import classes.booking;

import classes.bankAccounts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class baggagePaymentController {

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

    @FXML
    private Label totalFareLabel;

    private int bookingId;
    private int passengerId;
    private int baggageLimit;

    private double totalFare;

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public void setBaggageLimit(int baggageLimit) {
        this.baggageLimit = baggageLimit;
        this.totalFare = payment.calculateBaggagePaymentAmount(baggageLimit);
        totalFareLabel.setText("" + totalFare);
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
            // Record the payment
            boolean paymentRecorded = payment.recordPayment(bookingId, passengerId, totalFare, Date.valueOf(LocalDate.now()));

            if (paymentRecorded) {
                // Update booking status
                booking.updateBookingStatusbyId(bookingId, true);
                showAlert(Alert.AlertType.INFORMATION, "Payment successful. Baggage Limit Updated:"+baggageLimit);
                switchToMainPage("/managerViews/managerHomeScreen.fxml");
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to record payment.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Payment failed. Please check your card details.");
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
