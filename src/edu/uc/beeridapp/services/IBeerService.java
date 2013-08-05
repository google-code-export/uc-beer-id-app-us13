package edu.uc.beeridapp.services;

import java.util.ArrayList;
import java.util.List;

import edu.uc.beeridapp.dto.BarcodeSearchResult;
import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;

public interface IBeerService {
	/**
	 * returns a Beer Object via id
	 * 
	 * @param id
	 * @return Beer
	 */
	public Beer fetchBeer(int id);

	/**
	 * gets all beer styles
	 * 
	 * @return BeerStyle
	 * @throws Exception
	 */
	public ArrayList<BeerStyle> fetchBeerStyles() throws Exception;

	/**
	 * gets a Beer with the associated barcode
	 * 
	 * @param code
	 *            scanned barcode
	 * @return BarcodeSearchresult
	 * @throws Exception
	 */
	public BarcodeSearchResult fetchBeerByBarcode(String code) throws Exception;

	/**
	 * returns a list of Beers matching the BeerSearch attributes
	 * 
	 * @param beerSearch
	 * @return List of Beers
	 * @throws Exception
	 */
	public List<Beer> fetchBeers(BeerSearch beerSearch) throws Exception;

	/**
	 * returns a list of distinct Beer Names
	 * 
	 * @return List of Beer Names
	 * @throws Exception
	 */
	List<String> fetchBeerNames() throws Exception;
}
