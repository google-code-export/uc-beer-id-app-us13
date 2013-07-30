package edu.uc.beeridapp.dto;

import java.io.Serializable;

/**
 * BeerStyle Object to hold and manipulate beer styles
 * @author Tim Guibord
 *
 */
public class BeerStyle implements Serializable {


	private static final long serialVersionUID = 1L;	
	private int id;
	private int guid;
	private String style;
	
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
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
}
