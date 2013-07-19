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
	private Button btnSearchByBarcode;
	private Button btnAdminLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_menu);
		
		//Get Access to UI Components
		btnSearchByDetails = (Button) findViewById(R.id.btnSearchByDetails);
		btnSearchByBarcode = (Button) findViewById(R.id.btnSearchByBarcode);
		btnAdminLogin = (Button) findViewById(R.id.btnAdminLogin);
		
		// Create Listeners for Buttons
		OnClickListener searchByDetailsListener = new OnSearchByDetailsListener();
		OnClickListener searchByBarcodeListener = new OnSearchByBarcodeListener();
		OnClickListener adminLoginListener = new OnAdminLoginListener();
		
		btnSearchByDetails.setOnClickListener(searchByDetailsListener);
		btnSearchByBarcode.setOnClickListener(searchByBarcodeListener);
		btnAdminLogin.setOnClickListener(adminLoginListener);

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
	 * Starts Activity to Search by Barcode Label on Beverage
	 */
	private void searchByBarcode(){		
		// Call an activity to direct to the menu screen
		Intent searchByBarcodeIntent = new Intent(this, BarcodeSearchActivity.class);
		
		// Invoke the register screen
		startActivity(searchByBarcodeIntent);
	}
	
	/**
	 * Starts Activity to perform Admin Login
	 */
	private void adminLogin(){		
		// Call an activity to direct to the menu screen
		//Intent adminLoginIntent = new Intent(this, AdminLoginActivity.class);
		
		// Invoke the register screen
		//startActivity(adminLoginIntent);
	}
		
	class OnSearchByDetailsListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			searchByDetails();
		}
	}

	class OnSearchByBarcodeListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			searchByBarcode();
		}
	}
	
	class OnAdminLoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			adminLogin();
		}
	}
	
}
