package edu.uc.beeridapp.dao;

import java.util.ArrayList;

import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerStyle;

/**
 * Offline Beer DAO Interface
 * 
 * @author metzgecl
 *
 */
public interface IOfflineBeerDAO extends IBeerDAO {

	/**
	 * Searches the Beer Table in the Offline database for a record 
	 * with the specified GUID and returns the data in a Beer object
	 * 
	 * @param guid
	 * @return Beer
	 */
	public Beer searchBeerByGuid(String guid);

	/**
	 * Inserts a BeerStyle record in the offline database
	 * 
	 * @param beerStyle
	 */
	void insert(BeerStyle beerStyle);

	/**
	 * Searches the Styles Table in the Offline database for a record 
	 * with the specified GUID and returns the data in a BeerStyle object
	 * 
	 * @param guid
	 * @return BeerStyle
	 */
	public BeerStyle searchBeerStyleByGuid(String guid);

	/**
	 * Returns an ArrayList of all Beer names in the Offline Database
	 * @return ArrayList
	 */
	public ArrayList<String> fetchBeerNames();

	

}
