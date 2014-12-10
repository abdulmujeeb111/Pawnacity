package com.amtech.pawanacity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


@SuppressLint("NewApi")
public class PawnaMain extends Activity {
Button registerBtn,loginBtn;	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
    	getActionBar().hide();
		setContentView(R.layout.pawna_main_activity);
		SessionManager sm=new SessionManager(getApplicationContext());
		sm.checkLogin();
		registerBtn=(Button)findViewById(R.id.registration_btn);
		loginBtn=(Button)findViewById(R.id.login_btn);
		
	
		registerBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(PawnaMain.this,ChooseCustomer.class);
				startActivity(in);
				finish();
				
			}
		});
		
		loginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(PawnaMain.this,PawnLoginScreen.class);
				startActivity(in);
				finish();
			}
		});
	}

}
