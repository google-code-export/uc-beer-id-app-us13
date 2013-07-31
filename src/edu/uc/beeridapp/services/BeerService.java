package edu.uc.beeridapp.services;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import edu.uc.beeridapp.dao.IBeerDAO;
import edu.uc.beeridapp.dao.OfflineBeerDAO;
import edu.uc.beeridapp.dao.OnlineBeerDAO;
import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;

public class BeerService implements IBeerService {

	private IBeerDAO onlineBeerDAO;
	private IBeerDAO offlineBeerDAO;

	// initializes the DAO objects
	public BeerService(Context context) {
		onlineBeerDAO = new OnlineBeerDAO();
		offlineBeerDAO = new OfflineBeerDAO(context);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Beer fetchBeer(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<BeerStyle> fetchBeerStyles() throws Exception {

		try {
			// gets the beer styles from an online data source
			ArrayList<BeerStyle> allStyles = onlineBeerDAO.fetchStyles();

			// caches the results for offline use
			cacheStyles((ArrayList<BeerStyle>) allStyles.clone());
			return allStyles;
		} catch (Exception e) {

			// device is offline, pull styles from local SQLite DB
			return offlineBeerDAO.fetchStyles();
		}
	}

	/**
	 * caches the beer styles in the local SQLite DB
	 * 
	 * @param allStyles
	 */
	private void cacheStyles(ArrayList<BeerStyle> allStyles) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Beer> fetchBeers(BeerSearch beerSearch) throws Exception {
		try {
			// get the beer list from an Online source
			ArrayList<Beer> beerResults = onlineBeerDAO.searchBeers(beerSearch);

			// cache the results for offline use
			cacheBeers((ArrayList<Beer>) beerResults.clone());
			return beerResults;

		} catch (Exception e) {

			// device is offline, get the beers from the local cache
			return offlineBeerDAO.searchBeers(beerSearch);
		}
	}

	/**
	 * caches the beers in the local SQLite DB
	 * 
	 * @param beerList
	 */
	private void cacheBeers(ArrayList<Beer> beerList) {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Beer fetchBeerByBarcode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
