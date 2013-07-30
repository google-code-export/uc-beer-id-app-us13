package edu.uc.beeridapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import edu.uc.beeridapp.dao.OnlineBeerDAO;
import edu.uc.beeridapp.dto.BeerSearch;
import edu.uc.beeridapp.dto.BeerStyle;
import edu.uc.beeridapp.services.BeerService;
import edu.uc.beeridapp.services.IBeerService;

/**
 * 
 * @author Brian Pumphrey
 * Activity for performing Detailed Search for a Beer determined by user
 */

public class DetailsSearchActivity extends Activity {

	public static final String BEER_SEARCH = "BEER_SEARCH";
	
	private HashMap<Integer, String> beerStylesMap;

//	public beerType type;
//	public beerColor color;
//	public String calorieValue;
//	public String abvValue;
//	public int calories;
//	public int abv;

//	private RadioGroup rdoBeerType;
//	private RadioGroup rdoBeerColor;

	private Button btnDetailsSubmit;

	private AutoCompleteTextView actBeerName;
	private EditText edtMinABV;
	private EditText edtMaxABV;
	private Spinner spnBeerStyle;
//	private EditText edtCalories;
//	private EditText edtAlcoholByVolume;
	
	IBeerService beerService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details_search);

		//Get Access to UI Components
		actBeerName = (AutoCompleteTextView) findViewById(R.id.actBeerName);
		edtMinABV = (EditText) findViewById(R.id.edtMinAlcoholByVolume);
		edtMaxABV = (EditText) findViewById(R.id.edtMaxAlcoholByVolume);
		spnBeerStyle = (Spinner) findViewById(R.id.spnBeerStyles);
//		edtCalories = (EditText) findViewById(R.id.edtCalories);
//		edtAlcoholByVolume = (EditText) findViewById(R.id.edtAlcoholByVolume);
		btnDetailsSubmit = (Button) findViewById(R.id.btnDetailsSubmit);
//		rdoBeerType = (RadioGroup) findViewById(R.id.rdoBeerType);
//		rdoBeerColor = (RadioGroup) findViewById(R.id.rdoBeerColor);
		
		loadBeerStylesSpinner();

		// Create Listeners for Buttons
		OnClickListener btnDetailsSubmitListener = new OnDetailsSubmitListener();
//		RadioGroup.OnCheckedChangeListener rdoBeerTypeListener = new BeerTypeChangedListener();
//		RadioGroup.OnCheckedChangeListener rdoBeerColorListener = new BeerColorChangedListener();
//
//		rdoBeerType.setOnCheckedChangeListener(rdoBeerTypeListener);
//		rdoBeerColor.setOnCheckedChangeListener(rdoBeerColorListener);
		btnDetailsSubmit.setOnClickListener(btnDetailsSubmitListener);

		// TODO: Add Adapter for AutoCompleteTextView on Brand Name field
	}

	private void loadBeerStylesSpinner() {
		try {
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

//	class BeerTypeChangedListener implements RadioGroup.OnCheckedChangeListener {
//
//		@Override
//		public void onCheckedChanged(RadioGroup group, int checkedID) {
//			// TODO Auto-generated method stub
//
////			switch (checkedID){
////			case R.id.rdoAle:
////				type = beerType.Ale;
////				break;
////			case R.id.rdoLager:
////				type = beerType.Lager;
////				break;
////			case R.id.rdoLambic:
////				type = beerType.Lambic;
////				break;
////			case R.id.rdoHybrid:
////				type = beerType.Hybrid;
////				break;
////			default:
////				type = beerType.Any;
////				break;
////			}
//		}
//
//	}
//
//	class BeerColorChangedListener implements RadioGroup.OnCheckedChangeListener {
//
//		@Override
//		public void onCheckedChanged(RadioGroup group, int checkedID) {
//			// TODO Auto-generated method stub
//
////			switch (checkedID){
////			case R.id.rdoPale:
////				color = beerColor.Pale;
////				break;
////			case R.id.rdoRed:
////				color = beerColor.Red;
////				break;
////			case R.id.rdoBrown:
////				color = beerColor.Brown;
////				break;
////			case R.id.rdoDark:
////				color = beerColor.Dark;
////				break;
////			default:
////				color = beerColor.Any;
////				break;
////			}
//		}
//
//	}

	class OnDetailsSubmitListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			submitDetails();
		}
	}

	class GetBeerStylesTask extends AsyncTask<Void, Integer, HashMap<Integer, String>> {

		@Override
		protected HashMap<Integer, String> doInBackground(Void... params) {
			IBeerService beerService = new BeerService(DetailsSearchActivity.this);
			HashMap<Integer, String>map = (HashMap<Integer, String>) beerService.fetchBeerStyles();
			return map;
		}
		
		protected void onPostExecute(HashMap<Integer, String> map) {
			setBeerStylesMap(map);
			onGetBeerStylesTaskComplete();
		}
	}
	/**
	 * Performs search with details provided by user
	 */
	private void submitDetails() {

		// Get the text entered by user
		String beerName = actBeerName.getText().toString();

		// Create and populate a beer search object
		BeerSearch bs = new BeerSearch();

		if (beerName != null && beerName !="")
		{
			bs.setName(beerName);
		}

//		bs.setType(type);
//		bs.setColor(color);

		// Determine amount of max calories specified by user and convert to integer
//		try {
//			int calories = Integer.parseInt(edtCalories.getText().toString());
//			bs.setCalories(calories);
//		}
//		catch (Exception e) {
//			int calories = 0;
//			bs.setCalories(calories);
//		}
//
//		// Determine amount of max alcohol by volume specified by user and convert to integer
//		try { 
//			int abv = Integer.parseInt(edtAlcoholByVolume.getText().toString());
//			bs.setPercentAlcohol(abv);
//		}
//		catch (Exception e) {
//			int abv = 0;
//			bs.setPercentAlcohol(abv);
//		}

		// call an activity that will search and show results.
		Intent beerResultsIntent = new Intent(this, BeerResultsActivity.class);

		// pass along our search criteria to the results screen.
		beerResultsIntent.putExtra(BEER_SEARCH, bs);

		// invoke the results screen
		startActivity(beerResultsIntent);

	}
	
	private void onGetBeerStylesTaskComplete() {
		ArrayList<String> styles = new ArrayList<String>();
		
		for (int key : beerStylesMap.keySet()) {
			styles.add(beerStylesMap.get(key));
		}
		
		try {
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, styles);
			spnBeerStyle.setAdapter(adapter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setBeerStylesMap(HashMap<Integer, String> map) {
		this.beerStylesMap = map;
	}
}
