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
	private int id;
	private int guid;
	private String name;
	private String style;
	private double abv;

//	public Beer(int id, int guid, String name, String style, double abv) {
//		super();
//		this.id = id;
//		this.guid = guid;
//		this.name = name;
//		this.style = style;
//		this.abv = abv;
//	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getGuid() {
		return guid;
	}


	public void setGuid(int guid) {
		this.guid = guid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getStyle() {
		return style;
	}


	public void setStyle(String style) {
		this.style = style;
	}


	public double getAbv() {
		return abv;
	}


	public void setAbv(double abv) {
		this.abv = abv;
	}


//	@Override
//	public String toString() {
//		return beerName + " - " + color + " " + type;
//	}

	
}
