package edu.uc.beeridapp.dto;

import java.io.Serializable;

/**
 * Beer Search object for advanced search features
 * 
 * @author ddekok
 * 
 */
public class BeerSearch extends Beer implements Serializable {

	// BeerSearch attributes; Strings initialized to prevent
	// NullPointerExceptions on DetailsSearch
	private static final long serialVersionUID = 2L;
	private String lessThanABV = "";
	private String lessThanCalories = "";
	private String styleGUID = "";

	public String getLessThanABV() {
		return lessThanABV;
	}

	public void setLessThanABV(String lessThanABV) {
		this.lessThanABV = lessThanABV;
	}

	public String getLessThanCalories() {
		return lessThanCalories;
	}

	public void setLessThanCalories(String lessThanCalories) {
		this.lessThanCalories = lessThanCalories;
	}

	public String getStyleGUID() {
		return styleGUID;
	}

	public void setStyleGUID(String styleGUID) {
		this.styleGUID = styleGUID;
	}

}
