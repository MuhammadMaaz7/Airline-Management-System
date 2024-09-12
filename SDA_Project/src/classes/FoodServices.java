package classes;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class FoodServices {
	 private String menuId;
	 private String menuItems;
	 private double price;
	 private String description;
	public String getMenuId() {
		return menuId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(String menuItems) {
		this.menuItems = menuItems;
	}
	public void populateMenuList(ListView<String> menuListView) {
		 Statement stmt = DBConnection.getConnection();
	        String query = "SELECT * FROM foodservices WHERE availabilitystatus = true";

	        try (ResultSet rs = stmt.executeQuery(query)) {
	            while (rs.next()) {
	                String menuItem = rs.getString("menuitems");
	                menuListView.getItems().add(menuItem);
	            }
	        } catch (SQLException e) {
	            System.out.println("Error fetching food services: " + e.getMessage());
	        } finally {
	            DBConnection.Dispose();
	        }
	}
	public void FindMenuItem(String itemId, TextField descriptionField, TextField priceField) {
		  Statement stmt = DBConnection.getConnection();
	        String query = "SELECT description, price, availabilityStatus FROM foodservices WHERE menuID = " + itemId;

	        try {
	            ResultSet rs = stmt.executeQuery(query);
	            if (rs.next()) {
	                if (rs.getBoolean("availabilityStatus")) {
	                    descriptionField.setText(rs.getString("description"));
	                    priceField.setText(String.valueOf(rs.getDouble("price")));
	                } else {
	                    showAlert("Info", "The food item is not available.");
	                }
	            } else {
	                showAlert("Error", "Food item not found.");
	            }
	        } catch (SQLException e) {
	            System.out.println("Error fetching food item: " + e.getMessage());
	        } finally {
	            DBConnection.Dispose();
	        }
	    }
	
	

	    private void showAlert(String title, String message) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
		public void UpdateMenuItem(String itemId, String newDescription, double newPrice) {
			 Statement stmt = DBConnection.getConnection();
		        String query = "update foodservices set price='"+newPrice+"',description='"+newDescription+"' WHERE menuID = " + itemId;
		        

		        try {
		            stmt.executeUpdate(query);
		            showAlert("Info", "Update Successful.");
		           
		        } catch (SQLException e) {
		            System.out.println("Error fetching food item: " + e.getMessage());
		        } finally {
		            DBConnection.Dispose();
		        }
			
		}
		public void prepareSpecialMeal( String dietaryRequirements) {
			 try {
		            // Connect to database
		            Statement stmt = DBConnection.getConnection();

		            // Prepare SQL statement to insert special meal into foodservices table
		            String insertQuery = "INSERT INTO foodservices (menuItems, price, description,availabilityStatus) VALUES ('Special Meal', 15.99, 'Special Dietary Meal: " + dietaryRequirements+"',true);";
		            // Execute the insert query
		            stmt.executeUpdate(insertQuery);

		            System.out.println("Special meal added successfully.");
		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		            DBConnection.Dispose();
		        }
		    }
		}