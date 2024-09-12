package classes;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class InFlightServices {
	private String serviceId;
    private String description;
    private String availabilityStatus;
    private String serviceType;
    ServiceFactory services= new ServiceFactory();
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAvailabilityStatus() {
		return availabilityStatus;
	}
	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
 
	public void RetrieveAvailableContent(ListView<String> menuListView, ListView<String> entertainmentListView)
	{
		
		Object serviceObject = services.createObject("food");
		 ((FoodServices) serviceObject).populateMenuList(menuListView);
		 Object serviceObject1 = services.createObject("entertainment");
		 ((Entertainment) serviceObject1).populateEntertainmentList(entertainmentListView);
	}
	public void SelectItem(String itemId, TextField descriptionField, TextField priceField) {
		 Object serviceObject = services.createObject("food");
		 ((FoodServices) serviceObject).FindMenuItem(itemId, descriptionField, priceField);
		
	}
	public void HandleMenuModification(String itemId, String newDescription, double newPrice) {
		Object serviceObject = services.createObject("food");
		((FoodServices) serviceObject).UpdateMenuItem(itemId, newDescription, newPrice);
		
	}
	public void SelectContent(int contentId, Label titleLabel, Label genreLabel, Label durationLabel, Label ratingLabel) {
		Object serviceObject = services.createObject("entertainment");
		 Entertainment content=((Entertainment) serviceObject).FindContent(contentId);
		 if (content != null) {
	            titleLabel.setText(content.getTitle());
	            genreLabel.setText(content.getGenre());
	            durationLabel.setText(content.getDuration());
	            ratingLabel.setText(content.getRating());
	        } else {
	            titleLabel.setText("Not Found");
	            genreLabel.setText("");
	            durationLabel.setText("");
	            ratingLabel.setText("");
	        }
		
	}
	 public void handleContentAddition(int contentId, String title, String genre, String duration, String rating)
	 {
	 Object serviceObject = services.createObject("entertainment");
	 ((Entertainment) serviceObject).addContent(contentId, title, genre, duration, rating);
	 }

	 public void handleContentRemoval(int contentId) {
		 Object serviceObject = services.createObject("entertainment");
		 ((Entertainment) serviceObject).removeContent(contentId);
	    }
	
	public void requestSpecialMeal(String dietaryRequirements) {
		Object serviceObject = services.createObject("food");
		 ((FoodServices) serviceObject).prepareSpecialMeal(dietaryRequirements);
	}
		
	}