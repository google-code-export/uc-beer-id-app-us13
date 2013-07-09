package edu.uc.beeridapp.dto;

import java.io.Serializable;
/**
 * describes general beer object
 * @author Dyllon Dekok
 *
 */
public class Beer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String beerName;
	public enum beerType{Light, Dark, Brown, Red, Wheat, Pilsner};
	public beerType type;
	double percentAlcohol;
	double calories;
	public int getId() {
		return id;
	}
	
	public beerType getType() {
		return type;
	}

	public void setType(beerType type) {
		this.type = type;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getBeerName() {
		return beerName;
	}
	public void setBeer(String beerName) {
		this.beerName = beerName;
	}
	public double getPercentAlcohol() {
		return percentAlcohol;
	}
	public void setPercentAlcohol(double percentAlcohol) {
		this.percentAlcohol = percentAlcohol;
	}
	public double getCalories() {
		return calories;
	}
	public void setCalories(double calories) {
		this.calories = calories;
	}
	@Override
	public String toString() {
		return "Beer [id=" + id + ", beer=" + beerName + ", percentAlcohol="
				+ percentAlcohol + ", calories=" + calories + "]";
	}
	
	
}
