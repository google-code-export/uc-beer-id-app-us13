package edu.uc.beeridapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;

/**
 * @author Brian Pumphrey Search Menu Activity
 */
public class SearchMenuActivity extends Activity {

	private static final String SCAN_RESULT_FORMAT = "SCAN_RESULT_FORMAT";
	private static final String SCAN_RESULT = "SCAN_RESULT";
	private static final String SCAN_MODE = "PRODUCT_MODE";
	private static final String SCAN_MODE_CLASS = "com.google.zxing.client.android.SCAN.SCAN_MODE";
	private static final String SCAN_CLASS = "com.google.zxing.client.android.SCAN";
	public static final String SEARCH_BARCODE = "SEARCH_BARCODE";
	private Button btnSearchByDetails;
	private Button btnSearchByBarcode;
	private Button btnFacebookLoginLogout;

	private Session.StatusCallback statusCallback = new SessionStatusCallback();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_menu);

		// Get Access to UI Components
		btnSearchByDetails = (Button) findViewById(R.id.btnSearchByDetails);
		btnSearchByBarcode = (Button) findViewById(R.id.btnSearchByBarcode);
		btnFacebookLoginLogout = (Button) findViewById(R.id.btnFacebookLoginLogout);

		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(this, null, statusCallback,
						savedInstanceState);
			}
			if (session == null) {
				session = new Session(this);
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this)
						.setCallback(statusCallback));
			}
		}

		// Create Listeners for Buttons
		OnClickListener searchByDetailsListener = new OnSearchByDetailsListener();
		OnClickListener searchByBarcodeListener = new OnSearchByBarcodeListener();

		btnSearchByDetails.setOnClickListener(searchByDetailsListener);
		btnSearchByBarcode.setOnClickListener(searchByBarcodeListener);
		// btnFacebookLoginLogout.setOnClickListener(adminLoginListener);

		updateView();

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

	@Override
	public void onStart() {
		super.onStart();
		Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	public void onStop() {
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
	}

	/**
	 * Starts Activity to Search By Details of Beverage
	 */
	private void searchByDetails() {
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
	private void searchByBarcode() {
		// Sets up the Intent of the barcode scanner that was imported in the
		// application
		Intent intent = new Intent(SCAN_CLASS);
		intent.putExtra(SCAN_MODE_CLASS, SCAN_MODE);

		// Starts the Activity to receive the barcode number from the beverage
		startActivityForResult(intent, 0);
	}

	/**
	 * This method is called once the scanner finds valid barcode within the
	 * barcode scanner
	 * 
	 * @author Dyllon Dekok
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

		super.onActivityResult(requestCode, resultCode, intent);

		// if the request code is 0 that means the scanner at least activated
		// and came back with something
		if (requestCode == 0) {
			// if the result successfully read the barcode
			if (resultCode == RESULT_OK) {
				// put the results in the string values
				String contents = intent.getStringExtra(SCAN_RESULT);
				String format = intent.getStringExtra(SCAN_RESULT_FORMAT);

				// initialize new result intent, pass the scanned value and
				// start the activity
				Intent barcodeResultIntent = new Intent(this,
						BeerDetailsActivity.class);
				barcodeResultIntent.putExtra(SEARCH_BARCODE, contents);
				startActivity(barcodeResultIntent);

			}
			// did not activity therefore the result was cancelled
			else if (resultCode == 1) {
				
			}
		} else {
			Session.getActiveSession().onActivityResult(this, requestCode,
					resultCode, intent);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	private void updateView() {
		Session session = Session.getActiveSession();
		if (session.isOpened()) {
			btnFacebookLoginLogout.setText(R.string.facebook_logout);
			btnFacebookLoginLogout.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					onClickLogout();
				}
			});
		} else {
			btnFacebookLoginLogout.setText(R.string.facebook_login);
			btnFacebookLoginLogout.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					onClickLogin();
				}
			});
		}
	}

	private void onClickLogin() {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this)
					.setCallback(statusCallback));
		} else {
			Session.openActiveSession(this, true, statusCallback);
		}
	}

	private void onClickLogout() {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			updateView();
		}
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
}
