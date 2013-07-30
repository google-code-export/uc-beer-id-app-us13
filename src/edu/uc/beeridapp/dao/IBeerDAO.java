package edu.uc.beeridapp.dao;

import java.util.ArrayList;

import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;


/**
 * BeerApp DAO Interface
 * @author Dyllon Dekok
 */
public interface IBeerDAO 
{
	/**
	 * gets a list of BeerStyles from a data source
	 * 
	 * @return a list of BeerStyles
	 * @throws Exception
	 */
	public ArrayList<BeerStyle> fetchStyles() throws Exception;

	/**
	 * gets a list of Beers matching the search criteria
	 * 
	 * @param beerSearch
	 * @return a list of Beers
	 * @throws Exception
	 */
	public ArrayList<Beer> searchBeers(BeerSearch beerSearch) throws Exception;

}
