package edu.uc.beeridapp.services;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import edu.uc.beeridapp.dao.IBeerDAO;
import edu.uc.beeridapp.dao.IOfflineBeerDAO;
import edu.uc.beeridapp.dao.OfflineBeerDAO;
import edu.uc.beeridapp.dao.OnlineBeerDAO;
import edu.uc.beeridapp.dto.BarcodeSearchResult;
import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;

public class BeerService implements IBeerService {

	private IBeerDAO onlineBeerDAO;
	private IOfflineBeerDAO offlineBeerDAO;

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
	 * Caches the beer styles in the local SQLite DB.
	 * Sets up and starts in a new Thread
	 * 
	 * @param allStyles
	 */
	private void cacheStyles(ArrayList<BeerStyle> allStyles) {
		
		CacheStyles cs = new CacheStyles(allStyles);
		
		Thread csThread = new Thread(cs);
		
		csThread.start();

	}

	/**
	 * An inner class containing logic for caching the beer styles
	 * Implements Runnable for threading
	 * 
	 * @author metzgecl
	 *
	 */
	class CacheStyles implements Runnable {
		
		ArrayList<BeerStyle> allStyles;
		
		/**
		 * Sets the allStyles ArrayList to the one that is to be cached.
		 * 
		 * @param allStyles
		 */
		public CacheStyles(ArrayList<BeerStyle> allStyles) {
		
			this.allStyles = allStyles;
		
		}

		@Override
		public void run() {
			
			for(BeerStyle beerStyle : allStyles) {
				try {
					
					if (offlineBeerDAO.searchBeerByGuid(beerStyle.getGuid()) == null) {
						offlineBeerDAO.insert(beerStyle);
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
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
	 * {@inheritDoc}
	 * 
	 * @throws Exception
	 */
	@Override
	public BarcodeSearchResult fetchBeerByBarcode(String code) throws Exception {

		try {
			// get the Beer from an online source
			BarcodeSearchResult bsr = onlineBeerDAO.searchBeerByBarcode(code);
			
			//cache the results for offline use
			cacheBeerAndBarcode((BarcodeSearchResult) bsr.clone());
			return bsr;

		} catch (Exception e) {
			// device is offline, get the beer from the local cache
			return offlineBeerDAO.searchBeerByBarcode(code);
		}
	}

	/**
	 * caches the beers in the local SQLite DB
	 * 
	 * @param beerList
	 *            list of beers from search
	 */
	private void cacheBeers(ArrayList<Beer> beerList) {
		// instantiate an object of the inner class CacheBeers.  This is a separate object, because it implements Runnable.
        CacheBeers cp = new CacheBeers(beerList);
        // pass the object to a new thread.
        Thread cpThread = new Thread (cp);
        // invoke the start method on that thread which will start a new thread, and run the CacheBeers object in that new thread.
        cpThread.start();


	}

	class CacheBeers implements Runnable {

        // The collection of beers that we wish to cache.
        List<Beer> beerList;

        /**
         * Parameterized constructor ensures that we have populated beerList if we have an object of this class.
         * @param beerList the collection of beers we want to cache.
         */
        public CacheBeers(List<Beer> beerList) {
                this.beerList = beerList;
        }


        /**
         * The run method will execute in a new thread when the start() method is executed on that thread.
         */
        @Override
        public void run() {
                // TODO Auto-generated method stub
                // iterate over the collection of beers.
                for (Beer beer : beerList) {

                        try {
                                if (offlineBeerDAO.searchBeers((BeerSearch) beer) == null) {
                                        ((OfflineBeerDAO) offlineBeerDAO).insert(beer);
                                }
                        } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                        }

                }               
                
        }
	
	}
	
	/**
	 * caches the beer and itself barcode in the local SQLite DB
	 * 
	 * @param bsr
	 *            beer result from barcode search
	 */
	private void cacheBeerAndBarcode(BarcodeSearchResult bsr) {
		// TODO Auto-generated method stub

	}

}
