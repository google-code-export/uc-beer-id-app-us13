package edu.uc.beeridapp;

/**
 * Everything general to all activities in our app.
 * @author Brian Pumphrey
 */

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class BeerIDActivity extends Activity { 

	private static final int MENU_DETAILS_SEARCH = 1;
	private static final int MENU_BARCODE_SEARCH = 2;
	protected static final String SCAN_RESULT_FORMAT = "SCAN_RESULT_FORMAT";
	protected static final String SCAN_RESULT = "SCAN_RESULT";
	private static final String SCAN_MODE = "PRODUCT_MODE";
	private static final String SCAN_MODE_CLASS = "com.google.zxing.client.android.SCAN.SCAN_MODE";
	private static final String SCAN_CLASS = "com.google.zxing.client.android.SCAN";
	public static final String SEARCH_BARCODE = "SEARCH_BARCODE";

	public BeerIDActivity() {
		super();
	}
 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		// add a menu item.
		menu.add(0, MENU_DETAILS_SEARCH, Menu.NONE, getString(R.string.menu_details_search)).setAlphabeticShortcut('d');
		menu.add(0, MENU_BARCODE_SEARCH, Menu.NONE, getString(R.string.menu_barcode_search)).setAlphabeticShortcut('b');

		menu.setQwertyMode(true);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);

		switch(item.getItemId()) {
		case MENU_DETAILS_SEARCH:
			searchByDetails();
			break;
		case MENU_BARCODE_SEARCH:
			searchByBarcode();
			break;
		}
		return true;
	}

	/**
	 * Starts Activity to Search By Details of Beverage
	 */
	protected void searchByDetails() {
		// Call an activity to direct to the menu screen
		Intent searchByDetailsIntent = new Intent(this,
				DetailsSearchActivity.class);

		// Invoke the register screen
		startActivity(searchByDetailsIntent);
	}

	/**
	 * Starts Activity to Search by Barcode Label on Beverage
	 * 
	 * @author Dyllon Dekok
	 */
	protected void searchByBarcode() {
		// Sets up the Intent of the barcode scanner that was imported in the
		// application
		Intent intent = new Intent(SCAN_CLASS);
		intent.putExtra(SCAN_MODE_CLASS, SCAN_MODE);

		// Starts the Activity to receive the barcode number from the beverage
		startActivityForResult(intent, 0);
	}

}
