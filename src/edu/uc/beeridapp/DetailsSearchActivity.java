package edu.uc.beeridapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;

public class DetailsSearchActivity extends Activity {

	private RadioButton rdoAnyCalories;
	private RadioButton rdoAnyAbv;
	private RadioButton rdoLTCalories;
	private RadioButton rdoGTCalories;
	private RadioButton rdoLTAbv;
	private RadioButton rdoGTAbv;

	private EditText edtCalories;
	private EditText edtAlcoholByVolume;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details_search);

		//Get Access to UI Components
		rdoAnyCalories = (RadioButton) findViewById(R.id.rdoAnyCalories);
		rdoAnyAbv = (RadioButton) findViewById(R.id.rdoAnyAbv);
		rdoLTCalories = (RadioButton) findViewById(R.id.rdoLTCalories);
		rdoGTCalories = (RadioButton) findViewById(R.id.rdoGTCalories);
		rdoLTAbv = (RadioButton) findViewById(R.id.rdoLTAbv);
		rdoGTAbv = (RadioButton) findViewById(R.id.rdoGTAbv);
		edtCalories = (EditText) findViewById(R.id.edtCalories);
		edtAlcoholByVolume = (EditText) findViewById(R.id.edtAlcoholByVolume);

		// Create Listeners for Buttons
		OnClickListener rdoAnyCaloriesListener = new OnRadioAnyCaloriesListener();
		OnClickListener rdoLTCaloriesListener = new OnRadioLTCaloriesListener();
		OnClickListener rdoGTCaloriesListener = new OnRadioGTCaloriesListener();
		OnClickListener rdoAnyAbvListener = new OnRadioAnyAbvListener();
		OnClickListener rdoLTAbvListener = new OnRadioLTAbvListener();
		OnClickListener rdoGTAbvListener = new OnRadioGTAbvListener();

		rdoAnyCalories.setOnClickListener(rdoAnyCaloriesListener);
		rdoLTCalories.setOnClickListener(rdoLTCaloriesListener);
		rdoGTCalories.setOnClickListener(rdoGTCaloriesListener);
		rdoAnyAbv.setOnClickListener(rdoAnyAbvListener);
		rdoLTAbv.setOnClickListener(rdoLTAbvListener);
		rdoGTAbv.setOnClickListener(rdoGTAbvListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details_search, menu);
		return true;
	}

	class OnRadioAnyCaloriesListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			disableCalories();
		}
	}

	class OnRadioLTCaloriesListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			enableCalories();
		}
	}

	class OnRadioGTCaloriesListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			enableCalories();
		}
	}

	class OnRadioAnyAbvListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			disableABV();
		}
	}

	class OnRadioGTAbvListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			enableABV();
		}
	}

	class OnRadioLTAbvListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			enableABV();
		}
	}

	private void disableCalories() {
		// When the "Any Calories" radio button is selected, set text to null
		edtCalories.setText(null);
		// Disable Edit Text Box for Calories
		edtCalories.setEnabled(false);
	}

	private void enableCalories() {
		// Enable Edit Text Box for Calories
		edtCalories.setEnabled(true);
	}

	private void disableABV() {
		// When the "Any ABV" radio button is selected, set text to null
		edtAlcoholByVolume.setText(null);
		// Disable Edit Text Box for ABV
		edtAlcoholByVolume.setEnabled(false);
	}

	private void enableABV() {
		// Enable Edit Text Box for ABV
		edtAlcoholByVolume.setEnabled(true);		
	}

}
