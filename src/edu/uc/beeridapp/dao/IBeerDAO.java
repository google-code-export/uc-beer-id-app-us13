package edu.uc.beeridapp.dao;

import java.util.List;

import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;


/**
 * BeerApp DAO Interface
 * @author Dyllon Dekok
 *
 *
 */
public interface IBeerDAO 
{
	/**
	 * fetches the list of beer objects from the Data Layer
	 * @param beerSearch
	 * @return
	 */
	public List<Beer> fetchBeer(BeerSearch beerSearch);
}
