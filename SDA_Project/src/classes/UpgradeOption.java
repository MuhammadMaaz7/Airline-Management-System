package classes;

import javafx.beans.property.StringProperty;

public class UpgradeOption {
	 private String option;
	    private int pointsRequired;

	    public UpgradeOption(String option, int pointsRequired) {
	        this.option = option;
	        this.pointsRequired = pointsRequired;
	    }

	    public String getOption() {
	        return option;
	    }

	    public void setOption(String option) {
	        this.option = option;
	    }

	    public int getPointsRequired() {
	        return pointsRequired;
	    }

	    public void setPointsRequired(int pointsRequired) {
	        this.pointsRequired = pointsRequired;
	    } 
}