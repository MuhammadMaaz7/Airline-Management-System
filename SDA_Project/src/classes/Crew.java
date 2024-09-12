package classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import util.DBConnection;

public class Crew {
	 private int crewId;
	    private String name;
	    private String role;
	    private String expertise;
	    private boolean available;

	    public Crew(int crewId, String name, String role, String expertise, boolean available) {
	        this.crewId = crewId;
	        this.name = name;
	        this.role = role;
	        this.expertise = expertise;
	        this.available = available;
	    }

	    public Crew() {
		}
	    
	    
	    public int getCrewId() {
			return crewId;
		}



		public void setCrewId(int crewId) {
			this.crewId = crewId;
		}



		public String getName() {
			return name;
		}



		public void setName(String name) {
			this.name = name;
		}



		public String getRole() {
			return role;
		}



		public void setRole(String role) {
			this.role = role;
		}



		public String getExpertise() {
			return expertise;
		}



		public void setExpertise(String expertise) {
			this.expertise = expertise;
		}



		public boolean isAvailable() {
			return available;
		}



		public void setAvailable(boolean available) {
			this.available = available;
		}

		    public static Crew getAvailableStaff(int staffId) {
		        try (Statement stmt = DBConnection.getConnection()) {
		            ResultSet rs = stmt.executeQuery("SELECT * FROM crew WHERE crewId = " + staffId + " AND available = true");
		            if (rs.next()) {
		                int crewId = rs.getInt("crewId");
		                String name = rs.getString("name");
		                String role = rs.getString("role");
		                String expertise = rs.getString("expertise");
		                boolean available = rs.getBoolean("available");
		                return new Crew(crewId, name, role, expertise, available);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return null;
		    }


		    public static void assignAvailableCrew(int maintenanceId) {
		        try (Statement stmt = DBConnection.getConnection()) {
		            ResultSet rs = stmt.executeQuery("SELECT * FROM crew WHERE available = true and expertise like '%maintenance%' LIMIT 1");
		            if (rs.next()) {
		                int crewId = rs.getInt("crewId");
		                // Update crew availability
		                stmt.executeUpdate("UPDATE crew SET available = false WHERE crewId = " + crewId);
		                // Assign crew to the maintenance
		                new Maintenance().assigningstaff(maintenanceId, crewId);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    
		    public static List<Crew> loadAllCrew() {
		        List<Crew> crewList = new ArrayList<>();
		        String sql = "SELECT * FROM Crew";
		        try (Connection conn = DBConnection.getConnection1();
		             Statement stmt = conn.createStatement();
		             ResultSet rs = stmt.executeQuery(sql)) {

		            while (rs.next()) {
		                int crewId = rs.getInt("crewId");
		                String name = rs.getString("name");
		                String role = rs.getString("role");
		                String expertise = rs.getString("expertise");
		                boolean available = rs.getBoolean("available");

		                Crew crew = new Crew(crewId, name, role, expertise, available);
		                crewList.add(crew);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return crewList;
		    }
		    
		    public static boolean deleteCrewMember(int crewId) {
		        String sql = "DELETE FROM Crew WHERE crewId = ?";
		        try (Connection conn = DBConnection.getConnection1();
		             PreparedStatement pstmt = conn.prepareStatement(sql)) {

		            pstmt.setInt(1, crewId);
		            int affectedRows = pstmt.executeUpdate();

		            return affectedRows > 0;

		        } catch (SQLException e) {
		            e.printStackTrace();
		            return false;
		        }
		    }

}