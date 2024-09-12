package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.*;

public class DBConnection {

	private static Connection connection;

    // Method to get a Connection object
    public static Connection getConnection1() {
        String url = "jdbc:mysql://localhost:3306/AMS";
        String user = "root";
        String password = "july(2004)";

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("Driver loaded");

            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
//            System.out.println("Connected Successfully");

        } catch (ClassNotFoundException e) {
            System.out.println("Error loading JDBC driver: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return connection;
    }

    // Method to get a Statement object
    public static Statement getConnection() {
        String url = "jdbc:mysql://localhost:3306/AMS";
        String user = "root";
        String password = "july(2004)";

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("Driver loaded");

            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);
//            System.out.println("Connected Successfully");

        } catch (ClassNotFoundException e) {
            System.out.println("Error loading JDBC driver: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            throw new RuntimeException(e);
        }

        Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Error creating statement: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return statement;
    }

    // Method to dispose of the connection
    public static void Dispose() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
//                System.out.println("Connection closed successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }

    // Method to close both Connection and Statement objects
    public static void close(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection or statement: " + e.getMessage());
        }
    }
}