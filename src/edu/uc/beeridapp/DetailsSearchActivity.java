package edu.uc.beeridapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class DetailsSearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details_search);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		Spinner spnBeerType = (Spinner) findViewById(R.id.spnBeerType);
		Spinner spnAlcoholPercentage = (Spinner) findViewById(R.id.spnAlcoholPercentage);
		Spinner spnCalories = (Spinner) findViewById(R.id.spnCalories);
		
		ArrayAdapter<CharSequence> adapterBT = ArrayAdapter.createFromResource(this, R.array.beer_types_array, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapterAP = ArrayAdapter.createFromResource(this, R.array.comparison_array, android.R.layout.simple_spinner_item);
		ArrayAdapter<CharSequence> adapterCal = ArrayAdapter.createFromResource(this, R.array.comparison_array, android.R.layout.simple_spinner_item);
		
		// Specify the layout to use when the list of choices appears
		adapterBT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterAP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapterCal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		// Apply the adapter to the spinner
		spnBeerType.setAdapter(adapterBT);
		spnAlcoholPercentage.setAdapter(adapterAP);
		spnCalories.setAdapter(adapterCal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details_search, menu);
		return true;
	}

}
