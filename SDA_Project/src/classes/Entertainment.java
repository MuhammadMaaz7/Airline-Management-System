package classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

public class Entertainment {
	private int entertainmentId;
    private String title;
    private String genre;
    private String duration;
    private String rating;

    public Entertainment()
    {
    	entertainmentId=0;
    	title="";
    	genre="";
    	duration="";
    	rating="";
    }
	
	public Entertainment(int entertainmentId, String title, String genre, String duration, String rating) {
		super();
		this.entertainmentId = entertainmentId;
		this.title = title;
		this.genre = genre;
		this.duration = duration;
		this.rating = rating;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public int getEntertainmentId() {
		return entertainmentId;
	}
	public void setEntertainmentId(int entertainmentId) {
		this.entertainmentId = entertainmentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public void populateEntertainmentList(ListView<String> entertainmentListView) {
		Statement stmt = DBConnection.getConnection();
        String query = "SELECT * FROM entertainment WHERE availabilitystatus = true";

        try (ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String title = rs.getString("title");
                entertainmentListView.getItems().add(title);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching entertainment services: " + e.getMessage());
        } finally {
            DBConnection.Dispose();
        }
		
	}
	
	public Entertainment FindContent(int contentId) {
       
		 try {
	            Statement stmt = DBConnection.getConnection();
	            String query = "SELECT * FROM entertainment WHERE entertainmentid = " + contentId;
	            ResultSet rs = stmt.executeQuery(query);

	            if (rs.next()) {
	            	 if (rs.getBoolean("availabilityStatus"))
	            	 {
	                return new Entertainment(
	                        rs.getInt("entertainmentid"),
	                        rs.getString("title"),
	                        rs.getString("genre"),
	                        rs.getString("duration"),
	                        rs.getString("rating")
	                );
	            	 }
	            	 else
	            	 {
	            		 showAlert("Info", "Content not available.");
	            	 }
	            	 
	            }
	            else {
	                showAlert("Error", "Entertainment Content not found.");
	            }
	        } catch (SQLException e) {
	            System.out.println("Error finding content: " + e.getMessage());
	        } finally {
	            DBConnection.Dispose();
	        }
	        return null;
    }
	public void addContent(int contentId, String title2, String genre2, String duration2, String rating2) {
		 try {
	            Statement stmt = DBConnection.getConnection();
	            String query = "INSERT INTO entertainment (title, genre, duration, rating) VALUES ('"
	                    + title2 + "', '"
	                    + genre2 + "', '"
	                    + duration2 + "', '"
	                    + rating2 + "')";
	            stmt.executeUpdate(query);
	            showAlert("Info", "Added Successful.");
	        } catch (SQLException e) {
	            System.out.println("Error adding content: " + e.getMessage());
	        } finally {
	            DBConnection.Dispose();
	        }
		
	}
    
	public void removeContent(int contentId) {
        try {
            Statement stmt = DBConnection.getConnection();
            String query = "DELETE FROM entertainment WHERE entertainmentid = " + contentId;
            stmt.executeUpdate(query);
            showAlert("Info", "Removed Successful.");
        } catch (SQLException e) {
            System.out.println("Error removing content: " + e.getMessage());
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
}