package edu.uc.beeridapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * @author Brian Pumphrey Search Menu Activity
 */
public class SearchMenuActivity extends Activity {
	
	private Button btnSearchByDetails;
	private Button btnSearchByUPC;
	private Button btnSearchByLabel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_menu);
		
		//Get Access to UI Components
		btnSearchByDetails = (Button) findViewById(R.id.btnSearchByDetails);
		btnSearchByUPC = (Button) findViewById(R.id.btnSearchByUPC);
		btnSearchByLabel = (Button) findViewById(R.id.btnSearchByLabel);
		
		// Create Listeners for Buttons
		OnClickListener searchByDetailsListener = new OnSearchByDetailsListener();
		OnClickListener searchByUPCListener = new OnSearchByUPCListener();
		OnClickListener searchByLabelListener = new OnSearchByLabelListener();
		
		btnSearchByDetails.setOnClickListener(searchByDetailsListener);
		btnSearchByUPC.setOnClickListener(searchByUPCListener);
		btnSearchByLabel.setOnClickListener(searchByLabelListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_menu, menu);
		return true;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	/**
	 * Starts Activity to Search By Details of Beverage
	 */
	private void searchByDetails(){		
		// Call an activity to direct to the menu screen
		Intent searchByDetailsIntent = new Intent(this, DetailsSearchActivity.class);
		
		// Invoke the register screen
		startActivity(searchByDetailsIntent);
	}
	
	/**
	 * Starts Activity to Search by UPC Label on Beverage
	 */
	private void searchByUPC(){		
		// Call an activity to direct to the menu screen
		//Intent searchByUPCIntent = new Intent(this, UPCSearchActivity.class);
		
		// Invoke the register screen
		//startActivity(searchByUPCIntent);
	}
	
	/**
	 * Starts Activity to Search by Brand Label on Beverage
	 */
	private void searchByLabel(){		
		// Call an activity to direct to the menu screen
		//Intent searchByLabelIntent = new Intent(this, LabelSearchActivity.class);
		
		// Invoke the register screen
		//startActivity(searchByLabelIntent);
	}
	
	class OnSearchByDetailsListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			searchByDetails();
		}
	}

	class OnSearchByUPCListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			searchByUPC();
		}
	}
	
	class OnSearchByLabelListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			searchByLabel();
		}
	}
}
