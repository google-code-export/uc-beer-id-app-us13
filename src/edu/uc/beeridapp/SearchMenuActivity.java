package edu.uc.beeridapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import edu.uc.beeridapp.dao.IntentIntegrator;
import edu.uc.beeridapp.dao.IntentResult;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Brian Pumphrey Search Menu Activity
 */
public class SearchMenuActivity extends Activity {
	
	private Button btnSearchByDetails;
	private Button btnSearchByBarcode;
	private Button btnAdminLogin;
	private TextView formatTxt, contentTxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_menu);
		
		//Get Access to UI Components
		btnSearchByDetails = (Button) findViewById(R.id.btnSearchByDetails);
		btnSearchByBarcode = (Button) findViewById(R.id.btnSearchByBarcode);
		btnAdminLogin = (Button) findViewById(R.id.btnAdminLogin);
		formatTxt = (TextView)findViewById(R.id.scan_format);
		contentTxt = (TextView)findViewById(R.id.scan_content);
		
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
		
		Log.d("test", "button works!");
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
		startActivityForResult(intent, 0);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) 
	{
	    if (requestCode == 0) 
	    {
	        if (resultCode == RESULT_OK)
	        {
	            String contents = intent.getStringExtra("SCAN_RESULT");
	            String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
	            Log.i("xZing", "contents: "+contents+" format: "+format);
	            // Handle successful scan
	        } 
	        else if (resultCode == RESULT_CANCELED)
	        {
	            // Handle cancel
	            Log.i("xZing", "Cancelled");
	        }
	    }
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
