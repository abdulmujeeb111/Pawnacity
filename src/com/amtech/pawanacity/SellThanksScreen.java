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

public class SellThanksScreen extends Activity implements OnClickListener{
	
	TextView settings,message,home;
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
		setContentView(R.layout.sell_thanks_screen);
		settings=(TextView)findViewById(R.id.thanks_settings);
		message=(TextView)findViewById(R.id.thanks_message);
		home=(TextView)findViewById(R.id.thanks_home);
		
		settings.setPaintFlags(settings.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		
		settings.setTextColor(Color.parseColor("#FFD336"));
		message.setPaintFlags(message.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		//message.setText("Resend Email");
		message.setTextColor(Color.parseColor("#FFD336"));
		
		home.setPaintFlags(home.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

		home.setOnClickListener(this);
		settings.setOnClickListener(this);
		message.setOnClickListener(this);
		
		
		
		
	}
	@Override
	public void onBackPressed() {
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.thanks_home:
			Intent in=new Intent(SellThanksScreen.this,PawnMainPage.class);
			startActivity(in);
			finish();
			break;
case R.id.thanks_settings:
			Toast.makeText(getApplicationContext(), "Under Construction.......", 10000).show();
			break;
case R.id.thanks_message:
	Toast.makeText(getApplicationContext(), "Under Construction.......", 10000).show();
	break;

		default:
			break;
		}
		
	}

}
