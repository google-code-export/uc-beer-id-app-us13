package edu.uc.beeridapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;
import edu.uc.beeridapp.services.BeerService;
import edu.uc.beeridapp.services.IBeerService;

/**
 * 
 * @author Brian Pumphrey Activity for performing Detailed Search for a Beer
 *         determined by user
 */

public class DetailsSearchActivity extends Activity {

	public static final String BEER_SEARCH = "BEER_SEARCH";

	// public beerType type;
	// public beerColor color;
	// public String calorieValue;
	// public String abvValue;
	// public int calories;
	// public int abv;

	// private RadioGroup rdoBeerType;
	// private RadioGroup rdoBeerColor;

	private Button btnDetailsSubmit;

	private AutoCompleteTextView actBeerName;
	private EditText edtMinABV;
	private EditText edtMaxABV;
	private Spinner spnBeerStyle;
	// private EditText edtCalories;
	// private EditText edtAlcoholByVolume;

	IBeerService beerService;
	private String toastAlert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details_search);

		// Get Access to UI Components
		actBeerName = (AutoCompleteTextView) findViewById(R.id.actBeerName);
		edtMinABV = (EditText) findViewById(R.id.edtMinAlcoholByVolume);
		edtMaxABV = (EditText) findViewById(R.id.edtMaxAlcoholByVolume);
		spnBeerStyle = (Spinner) findViewById(R.id.spnBeerStyles);

		// edtCalories = (EditText) findViewById(R.id.edtCalories);
		// edtAlcoholByVolume = (EditText)
		// findViewById(R.id.edtAlcoholByVolume);

		btnDetailsSubmit = (Button) findViewById(R.id.btnDetailsSubmit);

		// rdoBeerType = (RadioGroup) findViewById(R.id.rdoBeerType);
		// rdoBeerColor = (RadioGroup) findViewById(R.id.rdoBeerColor);

		// load the beer style spinner from data source
		loadBeerStylesSpinner();

		// Create Listeners for Buttons
		OnClickListener btnDetailsSubmitListener = new OnDetailsSubmitListener();

		// RadioGroup.OnCheckedChangeListener rdoBeerTypeListener = new
		// BeerTypeChangedListener();
		// RadioGroup.OnCheckedChangeListener rdoBeerColorListener = new
		// BeerColorChangedListener();
		//
		// rdoBeerType.setOnCheckedChangeListener(rdoBeerTypeListener);
		// rdoBeerColor.setOnCheckedChangeListener(rdoBeerColorListener);

		btnDetailsSubmit.setOnClickListener(btnDetailsSubmitListener);

		// TODO: Add Adapter for AutoCompleteTextView on Brand Name field
	}

	/**
	 * Gets list of beer styles via AsyncTask and loads the spinner list
	 */
	private void loadBeerStylesSpinner() {
		try {
			// call an AsyncTask to fetch beer styles and load spinner
			GetBeerStylesTask task = new GetBeerStylesTask();
			task.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details_search, menu);
		return true;
	}

	// class BeerTypeChangedListener implements
	// RadioGroup.OnCheckedChangeListener {
	//
	// @Override
	// public void onCheckedChanged(RadioGroup group, int checkedID) {
	// // TODO Auto-generated method stub
	//
	// // switch (checkedID){
	// // case R.id.rdoAle:
	// // type = beerType.Ale;
	// // break;
	// // case R.id.rdoLager:
	// // type = beerType.Lager;
	// // break;
	// // case R.id.rdoLambic:
	// // type = beerType.Lambic;
	// // break;
	// // case R.id.rdoHybrid:
	// // type = beerType.Hybrid;
	// // break;
	// // default:
	// // type = beerType.Any;
	// // break;
	// // }
	// }
	//
	// }
	//
	// class BeerColorChangedListener implements
	// RadioGroup.OnCheckedChangeListener {
	//
	// @Override
	// public void onCheckedChanged(RadioGroup group, int checkedID) {
	// // TODO Auto-generated method stub
	//
	// // switch (checkedID){
	// // case R.id.rdoPale:
	// // color = beerColor.Pale;
	// // break;
	// // case R.id.rdoRed:
	// // color = beerColor.Red;
	// // break;
	// // case R.id.rdoBrown:
	// // color = beerColor.Brown;
	// // break;
	// // case R.id.rdoDark:
	// // color = beerColor.Dark;
	// // break;
	// // default:
	// // color = beerColor.Any;
	// // break;
	// // }
	// }
	//
	// }

	class OnDetailsSubmitListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			submitDetails();
		}
	}

	/**
	 * AsyncTask class to fetch beer styles from datasource and load spinner
	 * 
	 * @author Tim Guibord
	 * 
	 */
	class GetBeerStylesTask extends
			AsyncTask<Void, Integer, ArrayList<BeerStyle>> {

		@Override
		protected ArrayList<BeerStyle> doInBackground(Void... params) {
			// initialize a beer service
			IBeerService beerService = new BeerService(
					DetailsSearchActivity.this);

			// initialize BeerStyle Array to hold results
			ArrayList<BeerStyle> allStyles = new ArrayList<BeerStyle>();

			try {
				// fetch the BeerStyles from the data source
				allStyles = beerService.fetchBeerStyles();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return allStyles;
		}

		protected void onPostExecute(ArrayList<BeerStyle> styles) {

			// create an ArrayAdapter of BeerStyles and add it to the spinner
			ArrayAdapter<BeerStyle> adapter = new ArrayAdapter<BeerStyle>(
					DetailsSearchActivity.this,
					android.R.layout.simple_list_item_1, styles);
			spnBeerStyle.setAdapter(adapter);
		}
	}

	/**
	 * Performs search with details provided by user
	 */
	private void submitDetails() {

		// Get the text entered by user
		String beerName = actBeerName.getText().toString();
		String minAbv = edtMinABV.getText().toString();
		String maxAbv = edtMaxABV.getText().toString();
		BeerStyle beerStyle = (BeerStyle) spnBeerStyle.getSelectedItem();
		String styleGuid = beerStyle.getGuid();

		// checks to see that valid abv values were entered
		if (minLessThanMax(minAbv, maxAbv)) {
			// Create and populate a beer search object
			BeerSearch bs = new BeerSearch();

			// if a name search value was entered
			if (!beerName.isEmpty()) {
				bs.setName(beerName);
			}

			// if a min abv search value was entered
			if (!minAbv.isEmpty()) {
				bs.setGreaterThanABV(minAbv);
			}

			// if a max abv search value was entered
			if (!maxAbv.isEmpty()) {
				bs.setLessThanABV(maxAbv);
			}

			// if a beer style search value was selected
			if (!styleGuid.isEmpty()) {
				bs.setStyleGUID(styleGuid);
			}

			// call an activity that will search and show results.
			Intent beerResultsIntent = new Intent(this,
					BeerResultsActivity.class);

			// pass along our search criteria to the results screen.
			beerResultsIntent.putExtra(BEER_SEARCH, bs);

			// invoke the results screen
			startActivity(beerResultsIntent);

		} else {
			Toast.makeText(this, toastAlert, Toast.LENGTH_LONG).show();
		}

		// bs.setType(type);
		// bs.setColor(color);

		// Determine amount of max calories specified by user and convert to
		// integer
		// try {
		// int calories = Integer.parseInt(edtCalories.getText().toString());
		// bs.setCalories(calories);
		// }
		// catch (Exception e) {
		// int calories = 0;
		// bs.setCalories(calories);
		// }
		//
		// // Determine amount of max alcohol by volume specified by user and
		// convert to integer
		// try {
		// int abv = Integer.parseInt(edtAlcoholByVolume.getText().toString());
		// bs.setPercentAlcohol(abv);
		// }
		// catch (Exception e) {
		// int abv = 0;
		// bs.setPercentAlcohol(abv);
		// }

	}

	/**
	 * checks to make sure that if a minABV and maxABV are entered, that the min
	 * <= the max
	 * 
	 * @param min
	 * @param max
	 * @return true if only 1 or no abv values are entered, or if min is <= max
	 */
	private boolean minLessThanMax(String min, String max) {

		// initalizes doubles to compare values
		double minVal = 0.0;
		double maxVal = 0.0;

		try {
			// checks to see if a min was entered
			if (!min.isEmpty()) {
				// yes, parse a double
				minVal = Double.parseDouble(min);
			} else {
				// no value return true
				return true;
			}

			// checks to see if a max was entered
			if (!max.isEmpty()) {
				// yes, parse double
				maxVal = Double.parseDouble(max);
			} else {
				// no return true
				return true;
			}

			// compares the 2 values
			if (minVal <= maxVal) {
				return true;
			}

			// values are wrong
			toastAlert = getString(R.string.min_max_error);
			return false;
		} catch (NumberFormatException e) {
			// bad values entered, return false
			toastAlert = getString(R.string.min_max_numeric_error);
			return false;
		}
	}
}
