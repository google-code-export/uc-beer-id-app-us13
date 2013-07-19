package edu.uc.beeridapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class BarcodeSearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barcode_search);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.barcode_search, menu);
		return true;
	}

}
