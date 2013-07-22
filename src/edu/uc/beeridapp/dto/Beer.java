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
	public enum beerType{Any, Ale, Lager, Lambic, Hybrid};
	public enum beerColor{Any, Pale, Red, Brown, Dark};
	public beerType type;
	public beerColor color;
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

	public beerColor getColor() {
		return color;
	}

	public void setColor(beerColor color) {
		this.color = color;
	}
	
	
}
