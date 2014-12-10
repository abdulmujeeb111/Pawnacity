package com.amtech.pawanacity;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class PawnLoginScreen extends Activity implements OnClickListener {

	Button pawn_login_btn;

	EditText login_email_text, login_password_text;

	CheckBox stay_loggedin;

	TextView forgot_password;

	public static String loginEmail, loginPassword, loginURL, result, userRole,userEmail;

	ProgressDialog dialogPd;
	
	JSONArray groups_array;
	
	SessionManager session;

	private AlertDialog myalertDialog = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();

		setContentView(R.layout.pawn_login);

		pawn_login_btn = (Button) findViewById(R.id.login_btn);
		login_email_text = (EditText) findViewById(R.id.login_email_txt);
		login_password_text = (EditText) findViewById(R.id.login_password_txt);
		stay_loggedin = (CheckBox) findViewById(R.id.checkBox1);
		forgot_password = (TextView) findViewById(R.id.forgot_pwd_tv);
		forgot_password.setPaintFlags(forgot_password.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		forgot_password.setText("Forgot Password?");
		forgot_password.setTextColor(Color.parseColor("#FFD336"));
		pawn_login_btn.setOnClickListener(this);
		forgot_password.setOnClickListener(this);
		
		session=new SessionManager(getApplicationContext());

	}

	@Override
	public void onClick(View v) {

		loginEmail = login_email_text.getText().toString();
		loginPassword = login_password_text.getText().toString();

		switch (v.getId()) {

		case R.id.login_btn:

			if (loginEmail.equals("")) {
				login_email_text.setHint("invalid email");
				login_email_text.setHintTextColor(Color.parseColor("#FF0000"));

			} else if (loginPassword.equals("")) {
				login_password_text.setHint("invalid password");
				login_password_text.setHintTextColor(Color
						.parseColor("#FF0000"));

			} else {

				login();
			}
			break;

		case R.id.forgot_pwd_tv:

			if (loginEmail.equals("")) {

				Toast.makeText(PawnLoginScreen.this,
						"Please fill in the email field first", 1000).show();

			} else {
                      forgorPassword();
			}

			break;
		}

	}

	

	public void login() {

		dialogPd = new ProgressDialog(PawnLoginScreen.this);
		dialogPd.setTitle("In Progress");
		dialogPd.setMessage("Please Wait");
		dialogPd.show();

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		try {

			params.put("tag", "login");
			Log.i("tag", "register");

			params.put("email", loginEmail);
			Log.i("email", loginEmail);

			params.put("password", loginPassword);
			Log.i("password", loginPassword);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// sharedpreferences
		loginURL = "http://pawn.amtechgcc.in/a.php";

		// RequestParams params = new RequestParams();
		client.get(loginURL, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String response) {

				Log.i("response for iservice connect", response);
				Log.e("URL", loginURL);
				
				try {
					JSONObject jobject = new JSONObject(response);
					result = jobject.getString("success");
					groups_array = jobject.getJSONArray("userlist");
					for (int i = 0; i < groups_array.length(); i++) {
						JSONObject e = groups_array.getJSONObject(i);
						userEmail=e.getString("email");
						userRole=e.getString("role");
					}
					


					// Log.e("result", result);

					if (Integer.parseInt(result) == 1) {
						dialogPd.dismiss();
						/*Toast.makeText(getApplicationContext(),
								"Successfully logged in", 1000).show();*/
						
//						if (stay_loggedin.isChecked()) {
							session.createLoginSession(userRole, userEmail);

							
					//	} 
						groups_array = jobject.getJSONArray("userlist");
						for (int i = 0; i < groups_array.length(); i++) {
							JSONObject e = groups_array.getJSONObject(i);
							userEmail=e.getString("email");
							userRole=e.getString("role");
						}
						  Intent logIntent = new Intent( PawnLoginScreen.this,
						  PawnMainPage.class);
						  startActivity(logIntent);
						  finish();
						

					} else if (Integer.parseInt(result) == 0) {
						dialogPd.dismiss();
						login_password_text.setText("");
						login_password_text.setHint("invalid password");
						login_password_text.setHintTextColor(Color
								.parseColor("#FF0000"));
						/*Toast.makeText(getApplicationContext(),
								"wrong password", 1000).show();
*/					} else if (Integer.parseInt(result) == 2) {
						dialogPd.dismiss();
						showAlert();
						/*Toast.makeText(getApplicationContext(),
								"confirm email", 1000).show();*/
					} else if (Integer.parseInt(result) == 3) {
						dialogPd.dismiss();
						Toast.makeText(getApplicationContext(),
								"Please enter valid details", 1000).show();
					}

				} catch (Exception e) {
					// TODO: handle exception
					dialogPd.dismiss();
					Toast tost = new Toast(getApplicationContext());
					tost.makeText(getApplicationContext(), "Enter valid details", 1000)
							.show();

					e.printStackTrace();
				}
				

			}

			@Override
			public void onFailure(Throwable e) {
				dialogPd.dismiss();
				Toast tost = new Toast(getApplicationContext());
				tost.makeText(getApplicationContext(),
						"Please connect to the internet and try again.", 1000)
						.show();
			}
		});

	}
private void showAlert() {
		

		AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
		LinearLayout layout       = new LinearLayout(this);
		TextView tvMessage        = new TextView(this);
		TextView tvMessage1        = new TextView(this);
		
		
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(tvMessage);
		layout.addView(tvMessage1);
		
		
		tvMessage.setText("Please confirm your email, if you can’t find the email we sent, please check your spam folder");
		tvMessage1.setText("----------OR----------");
		tvMessage1.setGravity(Gravity.CENTER);
		tvMessage.setGravity(Gravity.CENTER);
		tvMessage1.setTextColor(Color.parseColor("#FF0000"));
		
		
		alert1.setTitle("PawnaCity Says...");
		alert1.setView(layout);
		
		alert1.setNegativeButton("click here", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				resendEmail();
			}
		});
		
		alert1.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
			
		});
		alert1.setIcon(R.drawable.desktop_logo);
		alert1.show();
		
		}

public void alertForForgotPassword(){

	

	AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
	alert1.setMessage("Your login details has been sent to your email");
	alert1.setTitle("PawnaCity Says...");
	alert1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {

		}
		
	});
	alert1.setIcon(R.drawable.desktop_logo);
	alert1.show();
	
	
}

public void alertForNotRegister(){

	

	AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
	alert1.setMessage("Email is not registered");
	alert1.setTitle("PawnaCity Says...");
	alert1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {

		}
		
	});
	
	alert1.setPositiveButton("Login", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {

		}
		
	});
	alert1.setIcon(R.drawable.desktop_logo);
	alert1.show();
	
	
}
public void alertForCofirm(){

	

	AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
	alert1.setMessage("Email Successfully Send");
	alert1.setTitle("PawnaCity Says...");
	alert1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {

		}
		
	});
	alert1.setIcon(R.drawable.desktop_logo);
	alert1.show();
	
	
}


public void forgorPassword(){


	dialogPd = new ProgressDialog(PawnLoginScreen.this);
	dialogPd.setTitle("In Progress");
	dialogPd.setMessage("Please Wait");
	dialogPd.show();

	// sharedpreferences
	AsyncHttpClient client = new AsyncHttpClient();
	RequestParams params = new RequestParams();

	try {

		params.put("tag", "forgetpassword");
		Log.i("tag", "forgetpassword");

		params.put("email", loginEmail);
		Log.i("email", loginEmail);

	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}

	// sharedpreferences
	loginURL = "http://pawn.amtechgcc.in/a.php";

	// RequestParams params = new RequestParams();
	client.get(loginURL, params, new AsyncHttpResponseHandler()  {

		@Override
		public void onSuccess(String response) {

			Log.i("response for iservice connect", response);
			Log.e("URL", loginURL);

			try {
				JSONObject jobject = new JSONObject(response);
				result = jobject.getString("success");

				// Log.e("result", result);

				if (Integer.parseInt(result) == 1) {
					dialogPd.dismiss();
					
					alertForForgotPassword();
					
					

				} else if (Integer.parseInt(result) == 0) {
					
					Toast.makeText(getApplicationContext(),
							"oops an error occoured", 1000).show();
				}
				else if (Integer.parseInt(result) == 2) {
					dialogPd.dismiss();
					alertForNotRegister();
					/*Toast.makeText(getApplicationContext(),
							"This mail is not registered with us", 1000).show();*/
				}

			} catch (Exception e) {
				dialogPd.dismiss();
				// TODO: handle exception
				Toast tost = new Toast(getApplicationContext());
				tost.makeText(getApplicationContext(), "Please enter correct Email", 1000)
						.show();

				e.printStackTrace();
			}
			// Toast.makeText(activity.getParent(),
			// "posted", 1000).show();

		}

		@Override
		public void onFailure(Throwable e) {
			dialogPd.dismiss();
			Toast tost = new Toast(getApplicationContext());
			tost.makeText(getApplicationContext(),
					"Please connect to the internet and try again.", 1000)
					.show();
		}
	});


}
	public void resendEmail() {

		dialogPd = new ProgressDialog(PawnLoginScreen.this);
		dialogPd.setTitle("In Progress");
		dialogPd.setMessage("Please Wait");
		dialogPd.show();

		// sharedpreferences
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		try {

			params.put("tag", "resend");
			Log.i("tag", "resend");

			params.put("email", loginEmail);
			Log.i("email", loginEmail);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// sharedpreferences
		loginURL = "http://pawn.amtechgcc.in/a.php";

		// RequestParams params = new RequestParams();
		client.get(loginURL, params, new AsyncHttpResponseHandler()  {

			@Override
			public void onSuccess(String response) {

				Log.i("response for iservice connect", response);
				Log.e("URL", loginURL);

				try {
					JSONObject jobject = new JSONObject(response);
					result = jobject.getString("success");

					// Log.e("result", result);

					if (Integer.parseInt(result) == 1) {
						dialogPd.dismiss();
						alertForCofirm();
						
						

					} else if (Integer.parseInt(result) == 0) {
						dialogPd.dismiss();
						Toast.makeText(getApplicationContext(),
								"Invalid email", 1000).show();
					}

				} catch (Exception e) {
					dialogPd.dismiss();
					// TODO: handle exception
					Toast tost = new Toast(getApplicationContext());
					tost.makeText(getApplicationContext(), "Invalid email", 1000)
							.show();

					e.printStackTrace();
				}
				// Toast.makeText(activity.getParent(),
				// "posted", 1000).show();

			}

			@Override
			public void onFailure(Throwable e) {
				dialogPd.dismiss();
				Toast tost = new Toast(getApplicationContext());
				tost.makeText(getApplicationContext(),
						"Please connect to the internet and try again.", 1000)
						.show();
			}
		});

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in=new Intent(PawnLoginScreen.this,PawnaMain.class);
		startActivity(in);
		finish();
	}
}
