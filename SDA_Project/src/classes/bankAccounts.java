package classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import util.DBConnection;

public class bankAccounts {
	
	private int accountId;
    private String cardNumber;
    private String cardHolderName;
    private Date expiryDate;
    private int cvv;
    private double balance;	
	
    public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public double getBalance(String cardNumber) {
	    try (Connection conn = DBConnection.getConnection1();
	         PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM BankAccounts WHERE card_number = ?")) {

	        stmt.setString(1, cardNumber);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getDouble("balance");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0.0; // Return 0 if balance not found or an error occurs
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public boolean processPayment(String cardNumber, String cardHolderName, Date expiryDate, int cvv, double amount) {
	    // Validate card details from the database
	    if (validateCardDetails(cardNumber, cardHolderName, expiryDate, cvv)) {
	        // Set balance from the database
	        balance = getBalance(cardNumber);

	        // Check if the balance is sufficient
	        if (balance >= amount) {
	            // Deduct the amount from the balance
	            if (deductAmountFromAccount(cardNumber, amount)) {
//	                showAlert(Alert.AlertType.INFORMATION, "Payment processed successfully");
	                return true;
	            } else {
//	                showAlert(Alert.AlertType.ERROR, "Payment processing failed. Unable to deduct amount from account.");
	                return false;
	            }
	        } else {
	            showAlert(Alert.AlertType.ERROR, "Insufficient balance. Payment processing failed." );
	            return false;
	        }
	    } else {
	        showAlert(Alert.AlertType.ERROR, "Payment processing failed. Invalid card details.");
	        return false;
	    }
	}

    private boolean validateCardDetails(String cardNumber, String cardHolderName, Date expiryDate, int cvv) {
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM BankAccounts WHERE card_number = ? AND card_holder_name = ? AND expiry_date = ? AND cvv = ?")) {

            stmt.setString(1, cardNumber);
            stmt.setString(2, cardHolderName);
            stmt.setDate(3, expiryDate);
            stmt.setInt(4, cvv);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Return true if there is a matching record
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean deductAmountFromAccount(String cardNumber, double amount) {
        String sql = "UPDATE BankAccounts SET balance = balance - ? WHERE card_number = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, amount);
            pstmt.setString(2, cardNumber);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                // Update the balance in the object
                balance -= amount;
                // Update the balance in the database
                updateBalanceInDatabase(cardNumber);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
//            showAlert(Alert.AlertType.ERROR, "Error deducting amount from account: " + e.getMessage());
            return false;
        }
    }

    private void updateBalanceInDatabase(String cardNumber) {
        String sql = "SELECT balance FROM BankAccounts WHERE card_number = ?";
        try (Connection conn = DBConnection.getConnection1();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cardNumber);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    balance = rs.getDouble("balance");
                }
            }
        } catch (SQLException e) {
//            showAlert(Alert.AlertType.ERROR, "Error updating balance in the database: " + e.getMessage());
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType.name()); // Set the title to the name of the alert type
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
