package passengerControllers;

import classes.Flight;
import classes.booking;
import classes.passenger;
import classes.airport;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class displayFlightsController {

    @FXML
    private TableView<Flight> flightsTableView;
    @FXML
    private TableColumn<Flight, String> flightNumberColumn;
    @FXML
    private TableColumn<Flight, String> sourceColumn;
    @FXML
    private TableColumn<Flight, String> destinationColumn;
    @FXML
    private TableColumn<Flight, Date> dateColumn;
    @FXML
    private TableColumn<Flight, Time> timeColumn;
    @FXML
    private TableColumn<Flight, Double> fareColumn;
    @FXML
    private Button backButton;

    public void setFlights(List<Flight> flights) {
        ObservableList<Flight> observableFlights = FXCollections.observableArrayList(flights);
        flightsTableView.setItems(observableFlights);
    }

    @FXML
    public void initialize() {
        flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        sourceColumn.setCellValueFactory(cellData -> {
            int airportId = cellData.getValue().getDepartureAirportId();
            return new SimpleStringProperty(airport.getAirportNameById(airportId));
        });
        destinationColumn.setCellValueFactory(cellData -> {
            int airportId = cellData.getValue().getArrivalAirportId();
            return new SimpleStringProperty(airport.getAirportNameById(airportId));
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        fareColumn.setCellValueFactory(new PropertyValueFactory<>("fare"));

        flightsTableView.setOnMouseClicked(event -> {
            passenger loggedInPassenger = passengerLoginController.getLoggedInPassenger();
            if (loggedInPassenger != null) {
                Flight selectedFlight = flightsTableView.getSelectionModel().getSelectedItem();
                if (selectedFlight != null) {
                    int passengerId = loggedInPassenger.getPassengerId();
                    String flightNumber = selectedFlight.getFlightNumber();
                    String bookingReference = booking.createBooking(passengerId, flightNumber);
                    if (bookingReference != null) {
                        double fare = selectedFlight.getFare();
                        showAlert(Alert.AlertType.INFORMATION, "Temporary booking created. Booking Reference: " + bookingReference);
                        switchToPaymentScreen("/passengerViews/ticketPayment.fxml", fare, bookingReference);
                    } else {
                        showAlert(Alert.AlertType.ERROR, "No available seat for the selected Flight.");
                    }
                }
            } else {
                System.out.println("No passenger logged in");
            }
        });

        backButton.setOnAction(event -> handleBackButton());
    }

    private void switchToPaymentScreen(String fxmlPath, double fare, String bookingReference) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            paymentController controller = loader.getController();
            controller.setTotalFare(fare);
            controller.setBookingReference(bookingReference);
            Scene scene = flightsTableView.getScene();
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
    
    private void switchToSearchFlights(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handleBackButton() {
    	switchToSearchFlights("/passengerViews/searchFlights.fxml");
    }
}
