package edu.uc.beeridapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import edu.uc.beeridapp.dto.Beer.*;
import edu.uc.beeridapp.dto.BeerSearch;

/**
 * 
 * @author Brian Pumphrey
 * Activity for performing Detailed Search for a Beer determined by user
 */

public class DetailsSearchActivity extends Activity {

	public static final String BEER_SEARCH = "BEER_SEARCH";
	public static final String LESS_THAN = "LESS_THAN";
	public static final String GREATER_THAN = "GREATER_THAN";

	public beerType type;
	public beerColor color;
	public String calorieValue;
	public String abvValue;
	public int calories;
	public int abv;

	private RadioGroup rdoBeerType;
	private RadioGroup rdoBeerColor;

	private Button btnDetailsSubmit;

	private AutoCompleteTextView actBeerName;
	private EditText edtCalories;
	private EditText edtAlcoholByVolume;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details_search);

		//Get Access to UI Components
		actBeerName = (AutoCompleteTextView) findViewById(R.id.actBeerName);
		edtCalories = (EditText) findViewById(R.id.edtCalories);
		edtAlcoholByVolume = (EditText) findViewById(R.id.edtAlcoholByVolume);
		btnDetailsSubmit = (Button) findViewById(R.id.btnDetailsSubmit);
		rdoBeerType = (RadioGroup) findViewById(R.id.rdoBeerType);
		rdoBeerColor = (RadioGroup) findViewById(R.id.rdoBeerColor);

		// Create Listeners for Buttons
		OnClickListener btnDetailsSubmitListener = new OnDetailsSubmitListener();
		RadioGroup.OnCheckedChangeListener rdoBeerTypeListener = new BeerTypeChangedListener();
		RadioGroup.OnCheckedChangeListener rdoBeerColorListener = new BeerColorChangedListener();

		rdoBeerType.setOnCheckedChangeListener(rdoBeerTypeListener);
		rdoBeerColor.setOnCheckedChangeListener(rdoBeerColorListener);
		btnDetailsSubmit.setOnClickListener(btnDetailsSubmitListener);

		// TODO: Add Adapter for AutoCompleteTextView on Brand Name field
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details_search, menu);
		return true;
	}

	class BeerTypeChangedListener implements RadioGroup.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedID) {
			// TODO Auto-generated method stub

			switch (checkedID){
			case 1:
				type = beerType.Ale;
				break;
			case 2:
				type = beerType.Lager;
				break;
			case 3:
				type = beerType.Lambic;
				break;
			case 4:
				type = beerType.Hybrid;
				break;
			default:
				type = beerType.Any;
				break;
			}
		}

	}

	class BeerColorChangedListener implements RadioGroup.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedID) {
			// TODO Auto-generated method stub

			switch (checkedID){
			case 1:
				color = beerColor.Pale;
				break;
			case 2:
				color = beerColor.Red;
				break;
			case 3:
				color = beerColor.Brown;
				break;
			case 4:
				color = beerColor.Dark;
				break;
			default:
				color = beerColor.Any;
				break;
			}
		}

	}

	class OnDetailsSubmitListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			submitDetails();
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
			bs.setBeer(beerName);
		}

		bs.setType(type);
		bs.setColor(color);

		// Determine amount of max calories specified by user and convert to integer
		try {
			int calories = Integer.parseInt(edtCalories.getText().toString());
			bs.setCalories(calories);
		}
		catch (Exception e) {
			int calories = 0;
			bs.setCalories(calories);
		}

		// Determine amount of max alcohol by volume specified by user and convert to integer
		try { 
			int abv = Integer.parseInt(edtAlcoholByVolume.getText().toString());
			bs.setPercentAlcohol(abv);
		}
		catch (Exception e) {
			int abv = 0;
			bs.setPercentAlcohol(abv);
		}

		// call an activity that will search and show results.
		Intent beerResultsIntent = new Intent(this, BeerResultsActivity.class);

		// pass along our search criteria to the results screen.
		beerResultsIntent.putExtra(BEER_SEARCH, bs);

		// invoke the results screen
		startActivity(beerResultsIntent);

	}

}
