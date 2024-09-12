package passengerControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import classes.airport;
import classes.Flight;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class searchFlightController {

    @FXML
    private ComboBox<Integer> departureDayComboBox;

    @FXML
    private ComboBox<Integer> departureMonthComboBox;

    @FXML
    private ComboBox<String> destinationComboBox;

    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<String> sourceComboBox;

    @FXML
    public void initialize() throws SQLException {
        loadFields();
        searchButton.setOnAction(event -> handleSearchButtonAction());
    }

    private void loadFields() throws SQLException {
        try {
            sourceComboBox.setItems(airport.getAllAirportNames());
            destinationComboBox.setItems(airport.getAllAirportNames());

            List<Integer> dayList = new ArrayList<>();
            for (int i = 1; i <= 31; i++) {
                dayList.add(i);
            }
            ObservableList<Integer> observableDayList = FXCollections.observableArrayList(dayList);
            departureDayComboBox.setItems(observableDayList);

            List<Integer> monthList = new ArrayList<>();
            for (int i = 1; i <= 12; i++) {
                monthList.add(i);
            }
            ObservableList<Integer> observableMonthList = FXCollections.observableArrayList(monthList);
            departureMonthComboBox.setItems(observableMonthList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSearchButtonAction() {
        String source = sourceComboBox.getValue();
        String destination = destinationComboBox.getValue();
        Integer departureDay = departureDayComboBox.getValue();
        Integer departureMonth = departureMonthComboBox.getValue();

        if (source == null || destination == null || departureDay == null || departureMonth == null) {
            showAlert(Alert.AlertType.ERROR, "All fields must be filled.");
            return;
        }

        LocalDate departureDate = LocalDate.of(LocalDate.now().getYear(), departureMonth, departureDay);

        try {
            List<Flight> flights = Flight.searchFlights(source, destination, departureDate);
            switchToDisplaySearchedFlights(flights);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void switchToDisplaySearchedFlights(List<Flight> flights) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/passengerViews/displaySearchedFlights.fxml"));
            Parent root = loader.load();
            displayFlightsController controller = loader.getController();
            controller.setFlights(flights);

            Scene currentScene = searchButton.getScene();
            currentScene.setRoot(root);

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
