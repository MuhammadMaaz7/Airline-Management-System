package managerControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class mainPageController {

    @FXML
    private Button managerButton;

    @FXML
    private Button passengerButton;

    @FXML
    public void initialize() {
        managerButton.setOnAction(event -> switchToManagerLogin());
        passengerButton.setOnAction(event -> switchToPassengerLogin());
    }

    private void switchToManagerLogin() {
        openLoginScreen("/managerViews/managerLogin.fxml");
    }

    private void switchToPassengerLogin() {
        openLoginScreen("/passengerViews/passengerLogin.fxml");
    }

    private void openLoginScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = managerButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
