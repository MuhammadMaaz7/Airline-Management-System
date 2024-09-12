package classes;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.sql.Statement;

import util.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


public class Flight {
	
	    private String flightNumber;
	    private int departureAirportId;
	    private int arrivalAirportId;
	    private Date departureDate;
	    private Time departureTime;
	    private Date arrivalDate;
	    private Time arrivalTime;
	    private String status;  
	    private int duration;
	    private double fare;

	    InFlightServices flightservices=new InFlightServices();
	    


    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getDepartureAirportId() {
        return departureAirportId;
    }

    public void setDepartureAirportId(int departureAirportId) {
        this.departureAirportId = departureAirportId;
    }

    public int getArrivalAirportId() {
        return arrivalAirportId;
    }

    public void setArrivalAirportId(int arrivalAirportId) {
        this.arrivalAirportId = arrivalAirportId;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public int getDurationMinutes() {
        return duration;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.duration= durationMinutes;
    }
    
    public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Time getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Time arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getStatus() {
		return status;
	}
	
    public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

	@Override
	public String toString() {
		return "Flight [flightNumber=" + flightNumber + ", departureAirportId=" + departureAirportId
				+ ", arrivalAirportId=" + arrivalAirportId + ", departureDate=" + departureDate + ", departureTime="
				+ departureTime + ", duration=" + duration + ", fare=" + fare + "]";
	}
	
	public static List<Flight> searchFlights(String source, String destination, LocalDate departureDate) throws SQLException {
        List<Flight> flights = new ArrayList<>();
        try (Connection connection = util.DBConnection.getConnection1()) {
            String query = "SELECT * FROM Flight WHERE departureAirportId = (SELECT airport_id FROM Airport WHERE airportName = ?) " +
                    "AND arrivalAirportId = (SELECT airport_id FROM Airport WHERE airportName = ?) " +
                    "AND departureDate = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, source);
            statement.setString(2, destination);
            statement.setDate(3, Date.valueOf(departureDate));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Flight flight = new Flight();
                flight.setFlightNumber(resultSet.getString("flightNumber"));
                flight.setDepartureAirportId(resultSet.getInt("departureAirportId"));
                flight.setArrivalAirportId(resultSet.getInt("arrivalAirportId"));
                flight.setDepartureDate(resultSet.getDate("departureDate"));
                flight.setDepartureTime(resultSet.getTime("departureTime"));
                flight.setDurationMinutes(resultSet.getInt("duration"));
                flight.setFare(resultSet.getDouble("fare"));

                flights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return flights;
    }
	
	public void handleAccessRequest(ListView<String> menuListView, ListView<String> entertainmentListView) {
        flightservices.RetrieveAvailableContent(menuListView, entertainmentListView);

		
	}
	public void RequestMenuSelection(String itemId, TextField descriptionField, TextField priceField) {
		  flightservices.SelectItem(itemId, descriptionField, priceField);
		
	}
	public void ModifyMenuItem(String itemId, String newDescription, double newPrice) {
		 flightservices.HandleMenuModification(itemId, newDescription, newPrice);
		
	}

	public void RequestContent(int contentId, Label titleLabel, Label genreLabel, Label durationLabel,
			Label ratingLabel) {
		        flightservices.SelectContent(contentId, titleLabel, genreLabel, durationLabel, ratingLabel);
		    }

		    public void addContent(int contentId, String title, String genre, String duration, String rating) {
		        flightservices.handleContentAddition(contentId, title, genre, duration, rating);
		    }

		    public void removeContent(int contentId) {
		        flightservices.handleContentRemoval(contentId);
		    }


			public void handleSpecialMealRequest(int passengerId,String dietaryRequirements) {
				 passenger passenger = new passenger(passengerId);
				 System.out.println(passenger.getPassengerId()+ passenger.getDietaryRequirements());// Assuming there's a constructor with only passengerId parameter
			        

			        // Request special meal based on dietary requirements
			        System.out.println(dietaryRequirements);
			        flightservices.requestSpecialMeal(dietaryRequirements);
				
			}

			public static void FindFlight(String flightNumber2, String passengerid, int bookingId) {
				Statement stmt = DBConnection.getConnection();
			        String query = "SELECT * FROM Flight WHERE flightNumber = '" + flightNumber2 + "'";

			        try (ResultSet rs = stmt.executeQuery(query)) {
			            if (rs.next()) {
			                passenger.FindPassenger(flightNumber2, passengerid, bookingId);
			            } else {
			                showAlert("Error", "Flight not found.");
			            }
			        } catch (SQLException e) {
			            e.printStackTrace();
			        } finally {
			            DBConnection.Dispose();
			        }
				
			}
			
			private static void showAlert(String title, String content) {
		        Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        alert.setTitle(title);
		        alert.setHeaderText(null);
		        alert.setContentText(content);
		        alert.showAndWait();
		    }

			public booking getBookingById(int bookingId) {
				return booking.fetchBookingById(bookingId);
			}

			public boolean updateSeatNumber(int bookingId, int seatNumber) {
				return booking.updateSeatNumber(bookingId, seatNumber);
			}

			public passenger getPassengerById(int passengerId) {
				return passenger.fetchPassengerById(passengerId);
			}

			public void updatePassengerDetails(passenger passenger) {
				 passenger.updatePassengerDetails();
				
			}
			
			public static List<Flight> getAllFlights() throws SQLException {
				List<Flight> flights = new ArrayList<>();
				try (Connection connection = util.DBConnection.getConnection1()) {
					String query = "SELECT * FROM Flight";
					PreparedStatement statement = connection.prepareStatement(query);

					ResultSet resultSet = statement.executeQuery();
					while (resultSet.next()) {
						Flight flight = new Flight();
						flight.setFlightNumber(resultSet.getString("flightNumber"));
						flight.setDepartureAirportId(resultSet.getInt("departureAirportId"));
						flight.setArrivalAirportId(resultSet.getInt("arrivalAirportId"));
						flight.setDepartureDate(resultSet.getDate("departureDate"));
						flight.setDepartureTime(resultSet.getTime("departureTime"));
						flight.setArrivalDate(resultSet.getDate("arrivalDate"));
						flight.setArrivalTime(resultSet.getTime("arrivalTime"));
						flight.setStatus(resultSet.getString("status"));
						flight.setDurationMinutes(resultSet.getInt("duration"));
						flight.setFare(resultSet.getDouble("fare"));

						flights.add(flight);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
				}
				return flights;
			}



			public static String addFlight( int departureAirportId, int arrivalAirportId,
					Date departureDate, Time departureTime, Date arrivalDate,
					Time arrivalTime, int duration) throws SQLException {

				if (flightExists(departureAirportId, arrivalAirportId, departureDate, departureTime, arrivalDate, arrivalTime, duration)) {
					System.err.println("Failed to add flight: Another flight with the same details already exists.");
					return null;
				}

				String query = "INSERT INTO Flight (flightNumber , departureAirportId , arrivalAirportId , " +
						"departureDate , departureTime , arrivalDate , arrivalTime , status, duration) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				String flightNumber="";

				try (Connection connection = util.DBConnection.getConnection1();
						PreparedStatement statement = connection.prepareStatement(query)) {
					flightNumber = generateUniqueFlightNumber();

					statement.setString(1, flightNumber);
					statement.setInt(2, departureAirportId);
					statement.setInt(3, arrivalAirportId);
					statement.setDate(4, departureDate);
					statement.setTime(5, departureTime);
					statement.setDate(6, arrivalDate);
					statement.setTime(7, arrivalTime);
					statement.setString(8, "Scheduled");
					statement.setInt(9, duration);

					statement.executeUpdate();
					System.out.println("flight added");
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Failed to add flight: " + e.getMessage());
				}
				return flightNumber;
			}

			public static String generateUniqueFlightNumber() {
				String flightNumber;
				Random rand = new Random();

				do {
					int randomNumber = rand.nextInt(900) + 100; 

					flightNumber = "PK" + randomNumber;
				} while (flightNumberExists(flightNumber)); // Check if the flight number already exists

				return flightNumber;
			}

			private static boolean flightNumberExists(String flightNumber) {
				try (Connection connection = util.DBConnection.getConnection1()) {
					String query = "SELECT COUNT(*) AS count FROM Flight WHERE flightNumber = ?";
					try (PreparedStatement statement = connection.prepareStatement(query)) {
						statement.setString(1, flightNumber);
						try (ResultSet resultSet = statement.executeQuery()) {
							if (resultSet.next()) {
								int count = resultSet.getInt("count");
								return count > 0; // Returns true if flight number exists, false otherwise
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();

				}
				return false;
			}


			public static void addFareToFlight(String flightId, double fare) throws SQLException {
				// SQL query to update the fare of the flight in the database
				String sql = "UPDATE Flight SET fare = ? WHERE flightNumber = ?";

				try (Connection conn = util.DBConnection.getConnection1();
						PreparedStatement pstmt = conn.prepareStatement(sql)) {

					pstmt.setDouble(1, fare);
					pstmt.setString(2, flightId);

					// Execute the update query
					pstmt.executeUpdate();

					System.out.println("Fare added to flight " + flightId + ": " + fare);
				}
			}

			public static boolean deleteFlight(String flightNumber) {
				try (Connection connection = DBConnection.getConnection1()) {
					// Check if the flight number exists before attempting to delete
					if (!flightNumberExists(flightNumber)) {
						System.out.println("Flight number does not exist.");
						return false;
					}

					// Delete the flight from the database
					String query = "DELETE FROM Flight WHERE flightNumber = ?";
					try (PreparedStatement statement = connection.prepareStatement(query)) {
						statement.setString(1, flightNumber);
						int rowsAffected = statement.executeUpdate();
						if (rowsAffected > 0) {
							System.out.println("Flight deleted successfully.");
							return true;
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
					// Handle exception appropriately, e.g., log it or show an error message
				}
				return false;
			}


			private static boolean flightExists(int departureAirportId, int arrivalAirportId,
					Date departureDate, Time departureTime, Date arrivalDate,
					Time arrivalTime, int duration) throws SQLException {
				// Query to check if a flight with the same details exists
				String query = "SELECT COUNT(*) AS count FROM Flight WHERE " +
						"departureAirportId = ? AND arrivalAirportId = ? AND " +
						"departureDate = ? AND departureTime = ? AND " +
						"arrivalDate = ? AND arrivalTime = ? AND " +
						"duration = ?";

				try (Connection connection = util.DBConnection.getConnection1();
						PreparedStatement statement = connection.prepareStatement(query)) {
					statement.setInt(1, departureAirportId);
					statement.setInt(2, arrivalAirportId);
					statement.setDate(3, departureDate);
					statement.setTime(4, departureTime);
					statement.setDate(5, arrivalDate);
					statement.setTime(6, arrivalTime);
					statement.setInt(7, duration);

					try (ResultSet resultSet = statement.executeQuery()) {
						if (resultSet.next()) {
							int count = resultSet.getInt("count");
							return count > 0; // Returns true if a flight exists with the same details
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
					System.err.println("Error checking if flight exists: " + e.getMessage());
				}
				return false; // Default to false if an exception occurs
			}

			public static void updateFlight(String flightNumber, int departureAirportId, int arrivalAirportId,
					Date departureDate, Time departureTime, Date arrivalDate,
					Time arrivalTime, int duration, String status) throws SQLException {
				String query = "UPDATE Flight SET departureAirportId = ?, arrivalAirportId = ?, " +
						"departureDate = ?, departureTime = ?, arrivalDate = ?, arrivalTime = ?, duration = ?, status = ? " +
						"WHERE flightNumber = ?";

				try (Connection connection = DBConnection.getConnection1();
						PreparedStatement statement = connection.prepareStatement(query)) {
					statement.setInt(1, departureAirportId);
					statement.setInt(2, arrivalAirportId);
					statement.setDate(3, departureDate);
					statement.setTime(4, departureTime);
					statement.setDate(5, arrivalDate);
					statement.setTime(6, arrivalTime);
					statement.setInt(7, duration);
					statement.setString(8, status);
					statement.setString(9, flightNumber);

					statement.executeUpdate();
					System.out.println("Flight updated: " + flightNumber);
				} catch (SQLException e) {
					e.printStackTrace();
					throw new SQLException("Failed to update flight: " + e.getMessage());
				}
			}

			 public static Flight getFlightByNumber(String flightNumber) throws SQLException {
			        String query = "SELECT * FROM Flight WHERE flightNumber = ?";
			        try (Connection connection = DBConnection.getConnection1();
			             PreparedStatement statement = connection.prepareStatement(query)) {
			            statement.setString(1, flightNumber);
			            try (ResultSet resultSet = statement.executeQuery()) {
			                if (resultSet.next()) {
			                    int departureAirportId = resultSet.getInt("departureAirportId");
			                    int arrivalAirportId = resultSet.getInt("arrivalAirportId");
			                    Date departureDate = resultSet.getDate("departureDate");
			                    Time departureTime = resultSet.getTime("departureTime");
			                    Date arrivalDate = resultSet.getDate("arrivalDate");
			                    Time arrivalTime = resultSet.getTime("arrivalTime");
			                    int duration = resultSet.getInt("duration");
			                    String status = resultSet.getString("status");
			                    int fare = resultSet.getInt("fare");
			                    // Create and return a new Flight object with the retrieved data
			                    
			                    Flight flight = new Flight();
			                    flight.setArrivalAirportId(arrivalAirportId);
			                    flight.setArrivalDate(arrivalDate);
			                    flight.setArrivalTime(arrivalTime);
			                    flight.setDepartureAirportId(departureAirportId);
			                    flight.setDepartureDate(departureDate);
			                    flight.setDepartureTime(departureTime);
			                    flight.setDurationMinutes(duration);
			                    flight.setFare(fare);
			                    flight.setStatus(status);
			                  
			                    return flight;
			                } else {
			                    // Flight with the given flightNumber does not exist
			                    return null;
			                }
			            }
			        }
			    }

}
