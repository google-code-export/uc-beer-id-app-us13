package edu.uc.beeridapp;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.services.BeerService;
import edu.uc.beeridapp.services.IBeerService;

/**
 * 
 * @author Brian Pumphrey Activity to display search results
 */

public class BeerResultsActivity extends ListActivity {

	public static final String SELECTED_BEER = "SELECTED_BEER";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Obtain beer search object
		BeerSearch bs = (BeerSearch) this.getIntent().getSerializableExtra(
				DetailsSearchActivity.BEER_SEARCH);

		try {
			// invoke the AsyncTask to get our results.
			BeerSearchTask bst = new BeerSearchTask();
			bst.execute(bs);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			// inform the user there was an error.
			Toast.makeText(this, getString(R.string.errUnableToGetResults),
					Toast.LENGTH_LONG).show();
		}

		// // Find beers that match these results
		//
		// // Get a reference to BeerDAO
		// IBeerDAO beerDAO = new BeerDAOStub();
		//
		// // Invoke the search
		// List<Beer> results;
		//
		// try {
		//
		// results = beerDAO.fetchBeer(bs);
		//
		// // Merge data To screen
		// ArrayAdapter<Beer> listAdapter = new ArrayAdapter<Beer>(this,
		// android.R.layout.simple_list_item_1, results);
		//
		// // Display the data
		// setListAdapter(listAdapter);
		//
		// if (results.isEmpty())
		// {
		// // Inform the user there are no results found
		// Toast.makeText(this, getString(R.string.errNoResultsFound),
		// Toast.LENGTH_LONG).show();
		// }
		//
		// }
		// catch (Exception e)
		// {
		// // Inform the user there was an error
		// Toast.makeText(this, getString(R.string.errUnableToGetResults),
		// Toast.LENGTH_LONG).show();
		// }

	}

	/**
	 * Find out which beer the user clicked, go to the details page.
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// Find the beer that the user selected.
		Beer selectedBeer = (Beer) getListView().getItemAtPosition(position);

		// create an intent to navigate to the details screen.
		Intent beerDetailsIntent = new Intent(this, BeerDetailsActivity.class);

		// find the beer ID, and put it into the intent.
		beerDetailsIntent.putExtra(SELECTED_BEER, selectedBeer);

		// go to the beer details page for this beer.
		startActivity(beerDetailsIntent);
	}

	class BeerSearchTask extends AsyncTask<BeerSearch, Integer, List<Beer>> {

		/**
		 * This method will run in the background thread.
		 */
		@Override
		protected List<Beer> doInBackground(BeerSearch... params) {
			IBeerService beerService = new BeerService(BeerResultsActivity.this);

			// invoke the search.
			List<Beer> results = new ArrayList<Beer>();

			try {
				// this is the network operation that will run in the new
				// thread.
				results = beerService.fetchBeers(params[0]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// return the plants that we got from the network.
			return results;
		}

		/**
		 * This method will run when the background thread is complete. This
		 * method will run in the UI thread, so we can update the UI.
		 */
		@Override
		protected void onPostExecute(List<Beer> result) {

			if (result.isEmpty()) {
				// Inform the user there are no results found
				Toast.makeText(BeerResultsActivity.this,
						getString(R.string.errNoResultsFound),
						Toast.LENGTH_LONG).show();
			} else {
				// marry together the data with the screen.
				ArrayAdapter<Beer> listAdapter = new ArrayAdapter<Beer>(
						BeerResultsActivity.this,
						android.R.layout.simple_list_item_1, result);

				// show the data.
				setListAdapter(listAdapter);
			}
		}

	}
}
