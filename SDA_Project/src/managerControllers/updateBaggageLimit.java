package managerControllers;

import java.io.IOException;
import classes.baggage;
import classes.booking;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class updateBaggageLimit {

    @FXML
    private TextField bookingReferenceField;

    @FXML
    private TextField baggageWeightField;

    @FXML
    private TextField baggageLimitField;

    @FXML
    private Button changeButton;

    @FXML
    public void initialize() {
        changeButton.setOnAction(event -> handleChangeButton());
    }

    @FXML
    private void handleChangeButton() {
        String bookingReference = bookingReferenceField.getText();

        // Verify booking reference and retrieve booking details
        booking booking1 = booking.getBookingByReference(bookingReference);

        if (booking1 != null) {
            // Booking exists, create baggage
            int bookingId = booking1.getBookingId();
            int passengerId = booking1.getPassengerId();
            int baggageLimit;
            double baggageWeight;

            try {
                baggageLimit = Integer.parseInt(baggageLimitField.getText());
                baggageWeight = Double.parseDouble(baggageWeightField.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Please enter valid numeric values for baggage limit and weight.");
                return;
            }

            if (baggageLimit <= 100) {
                if (baggageLimit >= baggageWeight) {
                    boolean baggageCreated = baggage.createBaggage(bookingId, passengerId, baggageLimit, baggageWeight);

                    if (baggageCreated) {
                        // Baggage created successfully
                        switchToPaymentView("/managerViews/baggagePayment.fxml", bookingId, passengerId, baggageLimit);
                    } else {
                        // Baggage creation failed
                        showAlert(Alert.AlertType.ERROR, "Failed to update baggage limit.");
                    }
                } else {
                    // Baggage limit is less than baggage weight
                    showAlert(Alert.AlertType.ERROR, "Baggage limit must be greater than or equal to baggage weight.");
                }
            } else {
                // Baggage limit is greater than 100
                showAlert(Alert.AlertType.ERROR, "Baggage limit must be less than or equal to 100.");
            }
        } else {
            // Booking does not exist
            showAlert(Alert.AlertType.ERROR, "Invalid booking reference.");
        }
    }

    private void switchToPaymentView(String fxmlPath, int bookingId, int passengerId, int baggageLimit) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Get the controller instance
            baggagePaymentController controller = loader.getController();

            // Set the bookingId, passengerId, and baggageLimit in the controller
            controller.setBookingId(bookingId);
            controller.setPassengerId(passengerId);
            controller.setBaggageLimit(baggageLimit);

            Scene scene = changeButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.name());
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
