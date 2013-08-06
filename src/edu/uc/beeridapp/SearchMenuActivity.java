package edu.uc.beeridapp;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;

import edu.uc.beeridapp.services.BeerService;
import edu.uc.beeridapp.services.IBeerService;

/**
 * @author Brian Pumphrey Search Menu Activity
 */
public class SearchMenuActivity extends BeerIDActivity {

	private Button btnSearchByDetails;
	private Button btnSearchByBarcode;
	private Button btnFacebookLogout;
	private Button btnFacebookLogin;
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private LocationManager locationManager;
	private LocationListener locationListener;
	private TextView txtLong;
	private TextView txtLat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// lets start by updating the beer style cache for the details search
		// spinner
		updateBeerStyleCache();

		setContentView(R.layout.activity_search_menu);

		// Get Access to UI Components
		btnSearchByDetails = (Button) findViewById(R.id.btnSearchByDetails);
		btnSearchByBarcode = (Button) findViewById(R.id.btnSearchByBarcode);
		btnFacebookLogin = (Button) findViewById(R.id.btnFacebookLogin);
		btnFacebookLogout = (Button) findViewById(R.id.btnFacebookLogout);
		txtLong = (TextView) findViewById(R.id.txtLong);
		txtLat = (TextView) findViewById(R.id.txtLat);

		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

		// initializes a new FB session and sets the correct status if the user
		// has logged in
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

		// get a location manager from the operating system.
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

		locationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location loc) {
				// update our lat and lng values.
				txtLat.setText(Double.toString(loc.getLatitude()));
				txtLong.setText(Double.toString(loc.getLongitude()));

			}

			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub

			}

		};

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				60 * 1000, 0, locationListener);

		// Create Listeners for Buttons
		OnClickListener searchByDetailsListener = new OnSearchByDetailsListener();
		OnClickListener searchByBarcodeListener = new OnSearchByBarcodeListener();
		OnClickListener onLoginClickListener = new OnLoginClickListener();
		OnClickListener onLogoutClickListener = new OnLogoutClickListener();

		btnSearchByDetails.setOnClickListener(searchByDetailsListener);
		btnSearchByBarcode.setOnClickListener(searchByBarcodeListener);
		btnFacebookLogin.setOnClickListener(onLoginClickListener);
		btnFacebookLogout.setOnClickListener(onLogoutClickListener);

		// initially set the FB login/logout buttons invisible until the session
		// status is determined
		btnFacebookLogin.setVisibility(View.GONE);
		btnFacebookLogout.setVisibility(View.GONE);

		// show or hide the FB login/logout button based on the current session
		// status
		updateView();
	}

	/**
	 * updates the beer styles locally from the online data source
	 */
	private void updateBeerStyleCache() {

		try {
			CacheBeerStylesTask task = new CacheBeerStylesTask();
			task.execute();
		} catch (Exception e) {
			Log.i("SearchMenuActivity.updateBeerStylesCache", e.toString());
		}

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
				// String contents = "08992475";

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
			// the Activity result is from the FB login; set the session
			Session.getActiveSession().onActivityResult(this, requestCode,
					resultCode, intent);
			updateView();
		}
	}

	/**
	 * gets the FB session back from a saved instance
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	/**
	 * shows the proper FB login/logout button based on the current session
	 * status
	 */
	private void updateView() {
		Session session = Session.getActiveSession();
		if (session.isOpened()) {
			btnFacebookLogin.setVisibility(View.GONE);
			btnFacebookLogout.setVisibility(View.VISIBLE);
		} else {
			btnFacebookLogin.setVisibility(View.VISIBLE);
			btnFacebookLogout.setVisibility(View.GONE);
		}
	}

	/**
	 * on click event for FB login
	 */
	private void onClickLogin() {
		// get the current session status
		Session session = Session.getActiveSession();

		// if the session is not opened, open a new one
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this)
					.setCallback(statusCallback));
		} else {
			// process the open session
			Session.openActiveSession(this, true, statusCallback);
		}
	}

	/**
	 * on click event for FB logout
	 */
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

	class OnLoginClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			onClickLogin();
		}
	}

	class OnLogoutClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			onClickLogout();
		}
	}

	/**
	 * Gets the beer style from the online data source and updates the local
	 * cache; this is called here so that the spinner on the BeerDetailsSearch
	 * loads quicker
	 * 
	 * @author Tim Guibord
	 * 
	 */
	public class CacheBeerStylesTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			IBeerService bs = new BeerService(SearchMenuActivity.this);

			try {
				bs.getBeerStylesForCache();
			} catch (Exception e) {
				Log.i("CacheBeerStylesTask.doInBackground", e.toString());
			}
			return null;
		}
	}

}
