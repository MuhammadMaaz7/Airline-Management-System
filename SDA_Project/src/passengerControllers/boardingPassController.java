package passengerControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

import classes.boardingPass;

public class boardingPassController {

    @FXML
    private Label flightNumberLabel;
    @FXML
    private Label passengerNameLabel;
    @FXML
    private Label seatNumberLabel;
    @FXML
    private Label gateLabel;
    @FXML
    private Label boardingTimeLabel;
    @FXML
    private Button okButton;
    
    @FXML
    private void initialize() {
    	okButton.setOnAction(event -> handleOkButtonClick());
    }

    public void setBoardingPassDetails(boardingPass bp) {
        flightNumberLabel.setText(bp.getFlightNumber());
        passengerNameLabel.setText(bp.getPassengerName());
        seatNumberLabel.setText(bp.getSeatNumber());
        gateLabel.setText(bp.getGate());
        boardingTimeLabel.setText(bp.getBoardingTime().toString()); // Convert LocalTime to String
    }

    @FXML
    private void handleOkButtonClick() {
        switchToBoardingPassDisplay("/passengerViews/passengerHomeScreen.fxml");
    }
    
    private void switchToBoardingPassDisplay(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = okButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
