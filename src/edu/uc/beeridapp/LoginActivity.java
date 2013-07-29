package edu.uc.beeridapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

import edu.uc.beeridapp.services.UserServiceStub;

/**
 * @author Brian Pumphrey Login Screen Activity
 */
public class LoginActivity extends Activity {

	private static final String WELCOME = "Welcome ";
	private static final String CLOSE = "Close";
	private static final String LOGIN_FAILURE_TITLE = "Login Failure";
	private static final String LOGIN_FAILURE_NOTIFICATION = "We're sorry, there was a problem attempting to log you in.";
	private static final String INVALID_USERNAME_PASSWORD = "Invalid username/password!";
	private static final String LOGIN_SUCCEEDED = "Login Succeeded!";
	
	private EditText edtEmail;
	private EditText edtPassword;
	private Button btnLogin;
	private Button btnRegister;
	private Button btnFacebookLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// Get access to the UI components
		edtEmail = (EditText) findViewById(R.id.edtEmail);
		edtPassword = (EditText) findViewById(R.id.edtPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnFacebookLogin = (Button) findViewById(R.id.btnFacbookLogin);

		// Create Listeners for Buttons
		OnClickListener loginListener = new OnLoginListener();
		OnClickListener registerListener = new OnRegisterListener();
		OnClickListener facebookLoginListener = new OnFacebookLoginListener();

		btnLogin.setOnClickListener(loginListener);
		btnRegister.setOnClickListener(registerListener);
		btnFacebookLogin.setOnClickListener(facebookLoginListener);
	}

	/**
	 * Used by FB SDK to check session state of user
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
	 * Performs a login with provided credentials
	 */
	private void login() {
		// Get the text that the user entered.
		String emailText = edtEmail.getText().toString();
		String passwordText = edtPassword.getText().toString();
		
		UserServiceStub us = new UserServiceStub();

		// Attempt to login with user's credentials
		try {
			if(us.logon(emailText, passwordText)){
				Toast.makeText(LoginActivity.this, LOGIN_SUCCEEDED, Toast.LENGTH_LONG).show();
				
				// Call an activity to direct to menu screen
				Intent searchMenuIntent = new Intent(this, SearchMenuActivity.class);
				
				// Invoke the menu screen
				startActivity(searchMenuIntent);
				
			} else {
				// Notify user of invalid username/password combination
				Toast.makeText(LoginActivity.this, INVALID_USERNAME_PASSWORD, Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			new AlertDialog.Builder(this).setTitle(LOGIN_FAILURE_TITLE).setMessage(LOGIN_FAILURE_NOTIFICATION).setNeutralButton(CLOSE, null).show();
		}

	}
	
    /**
     * Performs basic FB login
     */
    private void facebookLogin() {
            //opens FB Session object
            Session.openActiveSession(this, true, new Session.StatusCallback() {

                   
                    //calls FB SDK to retrieve current FB session status
                    @Override
                    public void call(Session session, SessionState state,
                                    Exception exception) {

                            //checks to see if session is already active
                            if (session.isOpened()) {
                                   
                                    //grabs the logged in FB user's Graph data
                                    Request.executeMeRequestAsync(session,
                                                    new Request.GraphUserCallback() {

                                            @Override
                                            public void onCompleted(GraphUser user,
                                                            Response response) {
                                                   
                                                    //shows message to user
                                                    Toast.makeText(LoginActivity.this,
                                                                    WELCOME + user.getName() + "!",
                                                                    Toast.LENGTH_LONG).show();
                                            }
                                    });
                            }

                    }
            });

    }

    /**
     * Starts Activity to send user to Registration Menu
     */
	private void register(){		
		// Call an activity to direct to the menu screen
		Intent registerIntent = new Intent(this, RegisterActivity.class);
		
		// Invoke the register screen
		startActivity(registerIntent);
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
	
    class OnFacebookLoginListener implements OnClickListener {

        @Override
        public void onClick(View v) {
                facebookLogin();

        }

}


}

