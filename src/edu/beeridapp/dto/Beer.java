package edu.beeridapp.dto;

import java.io.Serializable;

public class Beer implements Serializable{
	int id;
	String beer;
	enum type{Light, Dark, Brown, Red, Wheat, Pilsner};
	double percentAlcohol;
	double calories;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBeer() {
		return beer;
	}
	public void setBeer(String beer) {
		this.beer = beer;
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
		return "Beer [id=" + id + ", beer=" + beer + ", percentAlcohol="
				+ percentAlcohol + ", calories=" + calories + "]";
	}
	
	
}
