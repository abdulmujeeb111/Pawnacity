package com.amtech.pawanacity;

import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ThanksScreen extends Activity{
	
	TextView signinHere,resendEmail;
	ProgressDialog dialogPd;
	String result,loginURL,sOne;
	 
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
    	getActionBar().hide();
		setContentView(R.layout.thanks_screen);
		signinHere=(TextView)findViewById(R.id.sign_in_here);
		resendEmail=(TextView)findViewById(R.id.resend_email);
		signinHere.setPaintFlags(signinHere.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		signinHere.setText("Sign in here");
		signinHere.setTextColor(Color.parseColor("#FFD336"));
		resendEmail.setPaintFlags(resendEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		resendEmail.setText("Resend Email");
		resendEmail.setTextColor(Color.parseColor("#FFD336"));
//		Intent inten=getIntent();
		sOne=getIntent().getStringExtra("emailKye");
		
//		Log.i("email in thanks screen", emailNew);
//		Toast.makeText(ThanksScreen.this, sOne, 1000).show();
		signinHere.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent in=new Intent(ThanksScreen.this,PawnLoginScreen.class);
				startActivity(in);
				finish();
			}
		});
		
		resendEmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				resendEmail();
				/*Intent in=new Intent(ThanksScreen.thi,PawnLoginScreen.class);
				startActivity(in);*/
			}
		});
		
		
	}
	@Override
	public void onBackPressed() {
		
	}
public void resendEmail(){
	


	dialogPd = new ProgressDialog(ThanksScreen.this);
	dialogPd.setTitle("In Progress");
	dialogPd.setMessage("Please Wait");
	dialogPd.show();

	// sharedpreferences
	AsyncHttpClient client = new AsyncHttpClient();
	RequestParams params = new RequestParams();

	try {

		params.put("tag", "resend");
		Log.i("tag", "resend");

		params.put("email", sOne);
		Log.i("email", sOne);

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
							"Invalid Email", 1000).show();
				}

			} catch (Exception e) {
				dialogPd.dismiss();
				Toast tost = new Toast(getApplicationContext());
				tost.makeText(getApplicationContext(), "Invalid email", 1000)
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
}
