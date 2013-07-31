package edu.uc.beeridapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import edu.uc.beeridapp.dto.Beer;

/**
 * This class displays information about a selected beer from the previous
 * ListView of Results
 * 
 * @author Brian Pumphrey
 */

public class BeerDetailsActivity extends Activity {

	private TextView txtBeerName;
	private TextView txtBeerStyle;
	private TextView txtCalories;
	private TextView txtAlcohol_Percentage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beer_details);

		// Get access to UI components

		txtBeerName = (TextView) findViewById(R.id.txtBeerName);
		txtBeerStyle = (TextView) findViewById(R.id.txtBeerStyle);
		txtCalories = (TextView) findViewById(R.id.txtCalories);
		txtAlcohol_Percentage = (TextView) findViewById(R.id.txtAlcohol_Percentage);

		// Obtain beer object and its attributes

		Beer beer = (Beer) this.getIntent().getSerializableExtra(
				BeerResultsActivity.SELECTED_BEER);

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.beer_details, menu);
		return true;
	}

}
