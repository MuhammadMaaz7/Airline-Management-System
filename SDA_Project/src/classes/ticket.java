package classes;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

public class ticket {
    private String ticketNumber;
    private int bookingId;
    private int passengerId;
    private String flightNumber;
    private Timestamp issueDate;
    private int seatNumber;

    public ticket(String ticketNumber, int bookingId, int passengerId, String flightNumber, Timestamp issueDate, int seatNumber) {
        this.ticketNumber = ticketNumber;
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.flightNumber = flightNumber;
        this.issueDate = issueDate;
        this.seatNumber = seatNumber;
    }

    // Getters and setters
    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Timestamp getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Timestamp issueDate) {
        this.issueDate = issueDate;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public static void saveTicket(ticket newTicket) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DBConnection.getConnection1();
            String insertTicketSQL = "INSERT INTO tickets (ticketNumber, bookingId, passengerId, flightNumber, issueDate, seatNumber) VALUES (?, ?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(insertTicketSQL);
            ps.setString(1, newTicket.getTicketNumber());
            ps.setInt(2, newTicket.getBookingId());
            ps.setInt(3, newTicket.getPassengerId());
            ps.setString(4, newTicket.getFlightNumber());
            ps.setTimestamp(5, newTicket.getIssueDate());
            ps.setInt(6, newTicket.getSeatNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String generateTicketNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder ticketNumber = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            ticketNumber.append(characters.charAt(random.nextInt(characters.length())));
        }

        return ticketNumber.toString();
    }

    public static boolean createTicket(int bookingId, int passengerId, String flightNumber, int seatNumber) {
        String ticketNumber = generateTicketNumber();
        Timestamp issueDate = Timestamp.valueOf(LocalDateTime.now());

        ticket newTicket = new ticket(ticketNumber, bookingId, passengerId, flightNumber, issueDate, seatNumber);

        saveTicket(newTicket);

        return true;
    }
}

