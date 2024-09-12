package classes;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ServiceFactory {

		
		 public Object createObject(String objectType) {
		        if (objectType == null) {
		            return null;
		        }
		        if (objectType.equalsIgnoreCase("food")) {
		            return new FoodServices();
		        } else if (objectType.equalsIgnoreCase("entertainment")) {
		            return new Entertainment();
		        }
		        return null;
	}
}