package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import javafx.scene.control.Alert;
import util.DBConnection;

public class passenger {
    private int passengerId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String dietaryRequirements;
    
    public passenger() {
		// TODO Auto-generated constructor stub
	}

	public passenger(int passengerId2, String dietaryRequirement2) {
		passengerId=passengerId2;
		dietaryRequirements=dietaryRequirement2;
	}

	public passenger(int passengerId2) {
		passengerId=passengerId2;
	}

	public passenger(int passengerId, String fullName, String phoneNumber) {
        this.passengerId = passengerId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDietaryRequirements() {
        return dietaryRequirements;
    }

    public void setDietaryRequirements(String dietaryRequirements) {
        this.dietaryRequirements = dietaryRequirements;
    }

    // Method to get Passenger by username
    public static passenger getPassengerByUsername(String username) {
        try (Connection connection = DBConnection.getConnection1();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Passengers WHERE username=?")) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                	passenger passenger = new passenger();
                    passenger.setPassengerId(rs.getInt("passenger_id"));
                    passenger.setUsername(rs.getString("username"));
                    passenger.setPassword(rs.getString("password"));
                    passenger.setFullName(rs.getString("full_name"));
                    passenger.setEmail(rs.getString("email"));
                    passenger.setPhoneNumber(rs.getString("phone_number"));
                    passenger.setDateOfBirth(rs.getString("date_of_birth"));
                    passenger.setDietaryRequirements(rs.getString("dietaryRequirements"));
                    return passenger;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        }
        return null;
    }

    // Method to get Passenger by ID
    public static passenger getPassengerById(int passengerId) {
        try (Connection connection = DBConnection.getConnection1();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Passengers WHERE passenger_id=?")) {
            statement.setInt(1, passengerId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                	passenger passenger = new passenger();
                    passenger.setPassengerId(rs.getInt("passenger_id"));
                    passenger.setUsername(rs.getString("username"));
                    passenger.setPassword(rs.getString("password"));
                    passenger.setFullName(rs.getString("full_name"));
                    passenger.setEmail(rs.getString("email"));
                    passenger.setPhoneNumber(rs.getString("phone_number"));
                    passenger.setDateOfBirth(rs.getString("date_of_birth"));
                    passenger.setDietaryRequirements(rs.getString("dietaryRequirements"));
                    return passenger;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
        }
        return null;
    }

    // Method to get Passenger name by ID
    public static String getPassengerNameById(int passengerId) {
    	passenger passenger = getPassengerById(passengerId);
        return passenger != null ? passenger.getFullName() : null;
    }
    
    public passenger(int passengerId, String username, String password, String fullName, String email,
			String phoneNumber, String dateOfBirth) {
		super();
		this.passengerId = passengerId;
		this.username = username;
		this.password = password;
		this.fullName = fullName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
	}

	



    public static passenger fetchPassengerById(int passengerId) {
        try (Statement stmt = DBConnection.getConnection()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM passengers WHERE passenger_Id = " + passengerId);
            if (rs.next()) {
                return new passenger(
                    rs.getInt("passenger_Id"),
                    rs.getString("full_Name"),
                    rs.getString("phone_Number")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    // Method to update passenger details in the database
    public void updatePassengerDetails() {
    	try (Statement stmt = DBConnection.getConnection()) {
            String sql = "UPDATE passengers SET full_Name = '" + this.fullName + "', phone_Number = '" + this.phoneNumber + "' WHERE passenger_Id = " + this.passengerId;
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

	public static void FindPassenger(String FlightNumber,String passengerid, int bookingId) {
		Statement stmt = DBConnection.getConnection();
        String query = "SELECT * FROM passengers WHERE passenger_id = " + passengerid;

        try (ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                int passengerId = rs.getInt("passenger_id");
                booking.FindBooking(FlightNumber, passengerid,bookingId);
            } else {
                showAlert("Error", "Passenger not found.");
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
}
