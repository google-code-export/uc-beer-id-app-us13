package edu.uc.beeridapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private EditText edtFirst, edtLast, edtDOB, edtEmail, edtConfirmEmail, edtPassword, edtConfirmPassword;
	private Button btnSubmit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		// Get access to the UI components
		edtFirst = (EditText) findViewById(R.id.edtFirst);
		edtLast = (EditText) findViewById(R.id.edtLast);
		edtDOB = (EditText) findViewById(R.id.edtDOB);
		edtEmail = (EditText) findViewById(R.id.edtEmail);
		edtConfirmEmail = (EditText) findViewById(R.id.edtConfirmEmail);
		edtPassword = (EditText) findViewById(R.id.edtPassword);
		edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
		btnSubmit = (Button) findViewById(R.id.btnSubmit);


		// Create Listeners for Buttons
		OnClickListener submitListener = new OnSubmitListener();
		
		btnSubmit.setOnClickListener(submitListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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

	class OnSubmitListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			submit();
		}

		private void submit() {
			// TODO Auto-generated method stub
			String firstText = edtFirst.getText().toString();
			String lastText = edtLast.getText().toString();
			String dobText = edtDOB.getText().toString();
			String emailText = edtEmail.getText().toString();
			String confirmEmailText = edtConfirmEmail.getText().toString();
			String passwordText = edtPassword.getText().toString();
			String confirmPasswordText = edtConfirmPassword.getText().toString();
			
			if (firstText.equals("") || lastText.equals("") || dobText.equals("")|| emailText.equals("") || confirmEmailText.equals("") || passwordText.equals("") || confirmPasswordText.equals(""))
			{
				// Notify User One or More Fields are Empty
				Toast.makeText(RegisterActivity.this, "One Or More Fields Are Empty! \r\n All Information Is Required!", Toast.LENGTH_LONG).show();
				
			}
			else if (!emailText.equals(confirmEmailText))
			{
				// Notify User Emails Do Not Match
				Toast.makeText(RegisterActivity.this, "Email Fields Do Not Match!", Toast.LENGTH_LONG).show();
			}
			else if (!passwordText.equals(confirmPasswordText))
			{
				//Notify User Passwords Do Not Match
				Toast.makeText(RegisterActivity.this, "Password Fields Do Not Match!", Toast.LENGTH_LONG).show();
			}
			else
			{
				//TODO Attempt to Save Information
			}

			
		}
	}
}
