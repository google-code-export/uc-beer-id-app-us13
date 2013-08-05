package edu.uc.beeridapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

import edu.uc.beeridapp.dto.Beer;
import edu.uc.beeridapp.services.BeerService;
import edu.uc.beeridapp.services.IBeerService;

/**
 * This class displays information about a selected beer from the previous
 * ListView of Results
 * 
 * @author Brian Pumphrey
 */

public class BeerDetailsActivity extends BeerIDActivity {

	private static final String BEERAPI_BASE_URL = "http://beerid-api.herokuapp.com/beer/";
	private static final String LOGO_LINK = "https://dl.dropboxusercontent.com/u/7833722/beerid-logo.png";
	private TextView txtBeerName;
	private TextView txtBeerStyle;
	private TextView txtCalories;
	private TextView txtAlcohol_Percentage;
	private Button btnFacebookShare;
	private Beer beer;

	private Session.StatusCallback statusCallback = new SessionStatusCallback();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_beer_details);

		// Get access to UI components

		txtBeerName = (TextView) findViewById(R.id.txtBeerName);
		txtBeerStyle = (TextView) findViewById(R.id.txtBeerStyle);
		txtCalories = (TextView) findViewById(R.id.txtCalories);
		txtAlcohol_Percentage = (TextView) findViewById(R.id.txtAlcohol_Percentage);
		btnFacebookShare = (Button) findViewById(R.id.btnFacebookShare);

		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

		Session session = Session.getActiveSession();
		if (session != null) {
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this)
						.setCallback(statusCallback));
			}
		} else if (savedInstanceState != null) {
			session = Session.restoreSession(this, null, statusCallback,
					savedInstanceState);
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this)
						.setCallback(statusCallback));
			}
		} else {
			btnFacebookShare.setVisibility(View.GONE);
		}

		if (this.getIntent().hasExtra(BeerIDActivity.SEARCH_BARCODE)) {
			String searchCode = (String) this.getIntent().getSerializableExtra(
					BeerIDActivity.SEARCH_BARCODE);
			BarcodeSearchTask bcst = new BarcodeSearchTask();
			bcst.execute(searchCode);
		} else {
			// Obtain beer object and its attributes
			beer = (Beer) this.getIntent().getSerializableExtra(
					BeerResultsActivity.SELECTED_BEER);
			loadBeerDetails();
		}
	}

	private void checkFacebookStatus() {
		Session session = Session.getActiveSession();
		if (!session.isOpened()) {
			btnFacebookShare.setVisibility(View.GONE);
		} else {

		}
	}

	private void loadBeerDetails() {
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

	public void facebookShare(View view) {
		Bundle params = new Bundle();
		params.putString("name", beer.getName());
		params.putString("caption", "A refreshing " + beer.getStyle()
				+ " style beer!");
		params.putString("description", "I found " + beer.getName()
				+ " with the BeerID App. It has an ABV of " + beer.getAbv()
				+ " and " + beer.getCalories() + " calories.");
		params.putString("link", BEERAPI_BASE_URL + beer.getGuid());
		params.putString("picture", LOGO_LINK);

		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(
				BeerDetailsActivity.this, Session.getActiveSession(), params))
				.setOnCompleteListener(new OnCompleteListener() {

					@Override
					public void onComplete(Bundle values,
							FacebookException error) {
						if (error == null) {
							// When the story is posted, echo the success
							// and the post Id.
							final String postId = values.getString("post_id");
							if (postId != null) {
								Toast.makeText(BeerDetailsActivity.this,
										"Posted story, id: " + postId,
										Toast.LENGTH_SHORT).show();
							} else {
								// User clicked the Cancel button
								Toast.makeText(
										BeerDetailsActivity.this
												.getApplicationContext(),
										"Publish cancelled", Toast.LENGTH_SHORT)
										.show();
							}
						} else if (error instanceof FacebookOperationCanceledException) {
							// User clicked the "x" button
							Toast.makeText(
									BeerDetailsActivity.this
											.getApplicationContext(),
									"Publish cancelled", Toast.LENGTH_SHORT)
									.show();
						} else {
							// Generic, ex: network error
							Toast.makeText(
									BeerDetailsActivity.this
											.getApplicationContext(),
									"Error posting story", Toast.LENGTH_SHORT)
									.show();
						}
					}

				}).build();
		feedDialog.show();

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

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			checkFacebookStatus();
		}
	private class BarcodeSearchTask extends AsyncTask<String, Integer, Beer> {

		@Override
		protected Beer doInBackground(String... params) {

			IBeerService bs = new BeerService(BeerDetailsActivity.this);
			Beer result = new Beer();

			try {
				result = bs.fetchBeerByBarcode(params[0]);
			} catch (Exception e) {
				// TODO: handle exception
			}
			// TODO Auto-generated method stub
			return result;
		}

		@Override
		protected void onPostExecute(Beer result) {
			if (result.equals(null)) {

				// Inform the user there are no results found
				Toast.makeText(BeerDetailsActivity.this,
						getString(R.string.errNoResultsFound),
						Toast.LENGTH_LONG).show();
			} else {
				// marry together the data with the screen.
				beer = result;
				loadBeerDetails();
			}
		}

	}

}
