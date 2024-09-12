package passengerControllers;

import java.io.IOException;
import classes.checkIn;
import classes.boardingPass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class checkInController {

    @FXML
    private TextField bookingReferenceField;

    @FXML
    private Button checkInButton;

    @FXML
    private void initialize() {
        checkInButton.setOnAction(event -> handleCheckInButton());
    }

    @FXML
    private void handleCheckInButton() {
        String bookingReference = bookingReferenceField.getText();

        // Validate input
        if (bookingReference.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please enter the booking reference.");
            return;
        }

        // Perform check-in
        boolean checkInSuccess = checkIn.checkInPassenger(bookingReference);

        if (checkInSuccess) {
            showAlert(Alert.AlertType.INFORMATION, "Check-in successful. Boarding pass generated.");

            // Retrieve the boarding pass object
            boardingPass bp = boardingPass.getBoardingPassByCheckInId(checkIn.getCheckInIdByBookingReference(bookingReference));

            // Switch to boarding pass display and pass the boarding pass object
            switchToBoardingPassDisplay("/passengerViews/displayBoardingPass.fxml", bp);
        } else {
            showAlert(Alert.AlertType.ERROR, "Already Checked-In or wrong Booking Reference");
        }
    }

    private void switchToBoardingPassDisplay(String fxmlPath, boardingPass bp) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Get the controller and set the boarding pass details
            boardingPassController controller = loader.getController();
            controller.setBoardingPassDetails(bp);

            Scene scene = checkInButton.getScene();
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
