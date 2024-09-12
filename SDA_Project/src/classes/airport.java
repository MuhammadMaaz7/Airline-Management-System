package classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class airport {
    private String airportCode;
    private String airportName;
    private String city;
    private String country;

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return airportName + " (" + airportCode + ") - " + city + ", " + country;
    }

    // Static method to get airport name by ID
    public static String getAirportNameById(int airportId) {
        String airportName = null;
        try (Connection connection = util.DBConnection.getConnection1()) {
            String query = "SELECT airportName FROM Airport WHERE airport_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, airportId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                airportName = resultSet.getString("airportName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return airportName;
    }

    public static ObservableList<String> getAllAirportNames() throws SQLException {
        List<String> airportList = new ArrayList<>();
        try (Connection connection = util.DBConnection.getConnection1()) {
            String airportQuery = "SELECT airportName FROM Airport";
            PreparedStatement airportStatement = connection.prepareStatement(airportQuery);
            ResultSet airportResultSet = airportStatement.executeQuery();

            while (airportResultSet.next()) {
                String airportName = airportResultSet.getString("airportName");
                airportList.add(airportName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return FXCollections.observableArrayList(airportList);
    }
    
    
    public static int getAirportId(String airportName) {
        int airportid=-1;
        try (Connection connection = util.DBConnection.getConnection1()) {
            String query ="SELECT airport_id FROM Airport WHERE airportName = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, airportName);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
             	   airportid = resultSet.getInt("airport_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return airportid;
     }
}
