package edu.uc.beeridapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.services.BeerService;
import edu.uc.beeridapp.services.IBeerService;

/**
 * This class displays information about a selected beer from the previous
 * ListView of Results
 * 
 * @author Brian Pumphrey
 */

public class BeerDetailsActivity extends BeerIDActivity {

	private TextView txtBeerName;
	private TextView txtBeerStyle;
	private TextView txtCalories;
	private TextView txtAlcohol_Percentage;
	private Beer beer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beer_details);

		// Get access to UI components

		txtBeerName = (TextView) findViewById(R.id.txtBeerName);
		txtBeerStyle = (TextView) findViewById(R.id.txtBeerStyle);
		txtCalories = (TextView) findViewById(R.id.txtCalories);
		txtAlcohol_Percentage = (TextView) findViewById(R.id.txtAlcohol_Percentage);

		if (this.getIntent().hasExtra(BeerIDActivity.SEARCH_BARCODE)) {
			String searchCode = (String) this.getIntent().getSerializableExtra(
					BeerIDActivity.SEARCH_BARCODE);
			BarcodeSearchTask bcst = new BarcodeSearchTask();
			bcst.execute(searchCode);
		} else {
			// Obtain beer object and its attributes
			beer = (Beer) this.getIntent().getSerializableExtra(
					BeerResultsActivity.SELECTED_BEER);
			loadBeerDetails();
		}
	}

	private void loadBeerDetails() {
		String name = beer.getName().toString();
		String style = beer.getStyle().toString();
		String abv = beer.getAbv().toString();
		String calories = beer.getCalories().toString();

		// Set text values from beer object
		txtBeerName.setText(name);
		txtBeerStyle.setText(style);
		txtCalories.setText(calories);
		txtAlcohol_Percentage.setText(abv);
	}

	private class BarcodeSearchTask extends AsyncTask<String, Integer, Beer> {

		@Override
		protected Beer doInBackground(String... params) {

			IBeerService bs = new BeerService(BeerDetailsActivity.this);
			Beer result = new Beer();

			try {
				result = bs.fetchBeerByBarcode(params[0]);
			} catch (Exception e) {
				// TODO: handle exception
			}
			// TODO Auto-generated method stub
			return result;
		}

		@Override
		protected void onPostExecute(Beer result) {
			if (result.equals(null)) {

				// Inform the user there are no results found
				Toast.makeText(BeerDetailsActivity.this,
						getString(R.string.errNoResultsFound),
						Toast.LENGTH_LONG).show();
			} else {
				// marry together the data with the screen.
				beer = result;
				loadBeerDetails();
			}
		}

	}

}
