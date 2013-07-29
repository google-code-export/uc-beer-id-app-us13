package edu.uc.beeridapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import edu.uc.beeridapp.dto.Beer;

/**
 * This class displays information about a selected beer from the previous ListView of Results
 * @author Brian Pumphrey
 */

public class BeerDetailsActivity extends Activity {

	private TextView txtBeerName;
	private TextView txtBeerColor;
	private TextView txtBeerType;
	private TextView txtCalories;
	private TextView txtAlcohol_Percentage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beer_details);
		
		//Get access to UI components
		
		txtBeerName = (TextView) findViewById(R.id.txtBeerName);
		txtBeerColor = (TextView) findViewById(R.id.txtBeerColor);
		txtBeerType = (TextView) findViewById(R.id.txtBeerType);
		txtCalories = (TextView) findViewById(R.id.txtCalories);
		txtAlcohol_Percentage = (TextView) findViewById(R.id.txtAlcohol_Percentage);
		
		// Obtain beer object and its attributes
		
		Beer beer = (Beer) this.getIntent().getSerializableExtra(BeerResultsActivity.SELECTED_BEER);
		
		String name = beer.getBeerName().toString();
		String color = beer.getColor().toString();
		String type = beer.getType().toString();
		String calories = Double.toString(beer.getCalories());
		String abv = Double.toString(beer.getPercentAlcohol());
		
		//Set text values from beer object
		
		txtBeerName.setText(name);
		txtBeerColor.setText(color);
		txtBeerType.setText(type);
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
