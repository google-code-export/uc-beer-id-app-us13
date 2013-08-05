package edu.uc.beeridapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;
import edu.uc.beeridapp.services.BeerService;
import edu.uc.beeridapp.services.IBeerService;

/**
 * 
 * @author Brian Pumphrey Activity for performing Detailed Search for a Beer
 *         determined by user
 */

public class DetailsSearchActivity extends BeerIDActivity {

	public static final String BEER_SEARCH = "BEER_SEARCH";

	private Button btnDetailsSubmit;
	private Button btnDetailsReset;
	private AutoCompleteTextView actBeerName;
	private EditText edtMaxCalories;
	private EditText edtMaxABV;
	private Spinner spnBeerStyle;

	IBeerService beerService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details_search);

		// Create new BeerService
		beerService = new BeerService(this);
		
		// Get Access to UI Components
		actBeerName = (AutoCompleteTextView) findViewById(R.id.actBeerName);
		edtMaxCalories = (EditText) findViewById(R.id.edtMaxCalories);
		edtMaxABV = (EditText) findViewById(R.id.edtMaxAlcoholByVolume);
		spnBeerStyle = (Spinner) findViewById(R.id.spnBeerStyles);

		btnDetailsSubmit = (Button) findViewById(R.id.btnDetailsSubmit);
		btnDetailsReset = (Button) findViewById(R.id.btnDetailsReset);

		// load the beer style spinner from data source
		loadBeerStylesSpinner();

		// Create Listeners for Buttons
		OnClickListener btnDetailsSubmitListener = new OnDetailsSubmitListener();

		btnDetailsSubmit.setOnClickListener(btnDetailsSubmitListener);
		
		OnClickListener btnDetailsResetListener = new OnDetailsResetListener();

		btnDetailsReset.setOnClickListener(btnDetailsResetListener);

		// TODO: Add Adapter for AutoCompleteTextView on Brand Name field
		
		try{
			//get the list of distinct beer names
			List<String> fetchBeerNames = beerService.fetchBeerNames();
			// create an array Adapter. Note: using select_dialog_item will display black auto complete text entries
			ArrayAdapter beerNameAdapter = new ArrayAdapter(this, android.R.layout.select_dialog_item, fetchBeerNames);
			
			// associate the array adapter with the Auto Complete.
			actBeerName.setAdapter(beerNameAdapter);
			
			registerForContextMenu(actBeerName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			e.printStackTrace();
		}
	}

	/**
	 * Performs search with details provided by user
	 */
	private void submitDetails() {

		// Get the text entered by user
		String beerName = actBeerName.getText().toString();
		String maxCalories = edtMaxCalories.getText().toString();
		String maxAbv = edtMaxABV.getText().toString();
		BeerStyle beerStyle = (BeerStyle) spnBeerStyle.getSelectedItem();
		String styleGuid = beerStyle.getGuid();

		// Create and populate a beer search object
		BeerSearch bs = new BeerSearch();

		// if a name search value was entered
		if (!TextUtils.isEmpty(beerName)) {
			bs.setName(beerName);
		}

		// if a max calories search value was entered
		if (!TextUtils.isEmpty(maxCalories)) {
			bs.setLessThanCalories(maxCalories);
		}

		// if a max abv search value was entered
		if (!TextUtils.isEmpty(maxAbv)) {
			bs.setLessThanABV(maxAbv);
		}

		// if a beer style search value was selected
		if (!styleGuid.equals("-1")) {
			bs.setStyleGUID(styleGuid);
		}

		// call an activity that will search and show results.
		Intent beerResultsIntent = new Intent(this, BeerResultsActivity.class);

		// pass along our search criteria to the results screen.
		beerResultsIntent.putExtra(BEER_SEARCH, bs);

		// invoke the results screen
		startActivity(beerResultsIntent);
	}
	
	/**
	 * Reset form fields to defaults
	 */
	private void resetDetails() {
		
		actBeerName.setText(null);
		edtMaxCalories.setText(null);
		edtMaxABV.setText(null);
		spnBeerStyle.setSelection(0);
		
	}

	class OnDetailsSubmitListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			submitDetails();
		}
	}
	class OnDetailsResetListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			resetDetails();
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
}
