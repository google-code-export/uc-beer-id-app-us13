package edu.uc.beeridapp;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.uc.beeridapp.dao.BeerDAOStub;
import edu.uc.beeridapp.dao.IBeerDAO;
import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.dto.BeerSearch;

/**
 * 
 * @author Brian Pumphrey
 * Activity to display search results
 */

public class BeerResultsActivity extends ListActivity {
	
	public static final String SELECTED_BEER = "SELECTED_BEER";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Obtain beer search object
		BeerSearch bs = (BeerSearch) this.getIntent().getSerializableExtra(DetailsSearchActivity.BEER_SEARCH);

		// Find beers that match these results

		// Get a reference to BeerDAO
		IBeerDAO beerDAO = new BeerDAOStub();

		// Invoke the search
		List<Beer> results;

		try {

			results = beerDAO.fetchBeer(bs);
			
			// Merge data To screen
			ArrayAdapter<Beer> listAdapter = new ArrayAdapter<Beer>(this, android.R.layout.simple_list_item_1, results);

			// Display the data
			setListAdapter(listAdapter);
			
			if (results.isEmpty())
			{
				// Inform the user there are no results found
				Toast.makeText(this, getString(R.string.errNoResultsFound), Toast.LENGTH_LONG).show();
			}

		}
		catch (Exception e)
		{
			// Inform the user there was an error
			Toast.makeText(this, getString(R.string.errUnableToGetResults), Toast.LENGTH_LONG).show();
		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.beer_results, menu);
		return true;
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
}
