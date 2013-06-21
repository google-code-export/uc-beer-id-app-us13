package edu.uc.beeridapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.uc.beeridapp.services.UserServiceStub;


/**
 * 
 * @author Brian Pumphrey
 * Login Screen Activity
 */
public class LoginActivity extends Activity {

	private EditText edtEmail, edtPassword;
	private Button btnLogin, btnRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Get access to the UI components
		edtEmail = (EditText) findViewById(R.id.edtEmail);
		edtPassword = (EditText) findViewById(R.id.edtPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnRegister = (Button) findViewById(R.id.btnRegister);

		// Create Listeners for Buttons
		OnClickListener loginListener = new OnLoginListener();
		OnClickListener registerListener = new OnRegisterListener();
		
		btnLogin.setOnClickListener(loginListener);
		btnRegister.setOnClickListener(registerListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/**
	 * Performs a login with provided credentials
	 */
	private void login() {
		// Get the text that the user entered.
		String emailText = edtEmail.getText().toString();
		String passwordText = edtPassword.getText().toString();
		
		UserServiceStub us = new UserServiceStub();

		// Attempt to login with user's credentials
		try {
			us.logon(emailText, passwordText);
			Toast.makeText(LoginActivity.this, "Login Succeeded!", Toast.LENGTH_LONG).show();
			
			// Call an activity to direct to menu screen
			
			// Invoke the menu screen
			
		} catch (Exception e) {
			// Notify user of invalid username/password combination
			Toast.makeText(LoginActivity.this, "Invalid username/password!", Toast.LENGTH_LONG).show();
		}

	}
	
	private void register(){
		// TODO Auto-generated method stub
		
		// Call an activity to direct to the menu screen
	}

	class OnLoginListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			login();
		}
	}
	
	class OnRegisterListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			register();
		}
	}

}

