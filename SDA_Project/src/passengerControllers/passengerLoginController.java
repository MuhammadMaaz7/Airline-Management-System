package passengerControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import classes.passenger;

public class passengerLoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    private static passenger loggedInPassenger;

    public static passenger getLoggedInPassenger() {
        return loggedInPassenger;
    }

    public static void setLoggedInPassenger(passenger passengerInstance) {
        loggedInPassenger = passengerInstance;
    }

    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> handlePassengerLogin());
        registerButton.setOnAction(event -> switchToPassengerSignUp());
    }

    private boolean isValidCredentials(String username, String password) {
        try (Connection connection = DBConnection.getConnection1();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Passengers WHERE Username=? AND Password=?")) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // If a record with the given user name and password exists, credentials are valid
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        }
        return false;
    }

    @FXML
    private void handlePassengerLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (isValidCredentials(username, password)) {
            passenger loggedInPassenger = passenger.getPassengerByUsername(username); // Fetch the logged-in passenger from the database
            if (loggedInPassenger != null) {
                setLoggedInPassenger(loggedInPassenger); // Set the logged-in passenger
                
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/passengerViews/passengerHomeScreen.fxml"));
                    Parent root = loader.load();
                    Scene scene = registerButton.getScene();
                    
                    passengerHomeScreenController passengerhomescreenController = loader.getController();
                    passengerhomescreenController.setpassenger(loggedInPassenger.getPassengerId());
                    
                    scene.setRoot(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
              //  switchToSearchFlight("/passengerViews/passengerHomeScreen.fxml");
            } else {
                System.out.println("Error: Passenger not found");
            }
        } else {
            // Show an alert for invalid credentials
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password. Please try again.");
            alert.showAndWait();
        }
    }

    private void switchToSearchFlight(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = loginButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToPassengerSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/passengerViews/passengerSignUp.fxml"));
            Parent root = loader.load();
            Scene scene = registerButton.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
