package passengerControllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import util.DBConnection;

public class passengerSignUpController {
	
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TextField dietaryRequirementsField;

    @FXML
    private Button signupButton;

    @FXML
    private void initialize() {
        signupButton.setOnAction(event -> {
            if (validateFields()) {
                savePassenger();
            }
        });
    }

    private boolean validateFields() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        LocalDate dob = dobPicker.getValue();
        String dietaryRequirements = dietaryRequirementsField.getText();

        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || dob == null) {
            showAlert(Alert.AlertType.ERROR, "Please fill in all fields.");
            return false;
        }

        if (password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Password should be at least 8 characters long.");
            return false;
        }

        if (!phoneNumber.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Phone number should contain only digits.");
            return false;
        }
        
        return true;
    }
    
    private void savePassenger() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();
        LocalDate dob = dobPicker.getValue();
        String dietaryRequirements = dietaryRequirementsField.getText();

        try (Connection connection = DBConnection.getConnection1();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Passengers (username, password, full_name, email, phone_number, date_of_birth, dietaryRequirements) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, fullName);
            statement.setString(4, email);
            statement.setString(5, phoneNumber);
            statement.setDate(6, java.sql.Date.valueOf(dob));
            statement.setString(7, dietaryRequirements);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Passenger Registered successfully.");
                switchToLoginScreen("/passengerViews/passengerLogin.fxml");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error saving passenger: " + e.getMessage());
        }
    }
    
    private void switchToLoginScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = signupButton.getScene();
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
