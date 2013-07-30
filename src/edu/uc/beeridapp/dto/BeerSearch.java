package edu.uc.beeridapp.dto;

import java.io.Serializable;

/**
 * Beer Search object for advanced search features
 * @author ddekok
 *
 */
public class BeerSearch extends Beer implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private double lessThanABV;
	private double greaterThanABV;
	private int styleGUID;
	

//	public BeerSearch(int id, int guid, String name, String style, double abv) {
//		super(id, guid, name, style, abv);
//		// TODO Auto-generated constructor stub
//	}

	public double getLessThanABV() {
		return lessThanABV;
	}


	public void setLessThanABV(double lessThanABV) {
		this.lessThanABV = lessThanABV;
	}


	public double getGreaterThanABV() {
		return greaterThanABV;
	}


	public void setGreaterThanABV(double greaterThanABV) {
		this.greaterThanABV = greaterThanABV;
	}


	public int getStyleGUID() {
		return styleGUID;
	}


	public void setStyleGUID(int styleGUID) {
		this.styleGUID = styleGUID;
	}

}
