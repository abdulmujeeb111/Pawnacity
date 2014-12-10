package com.amtech.pawanacity;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

@SuppressLint("NewApi")
public class PawnMainPage extends Activity {
	SessionManager session;
	String role,email;
	LinearLayout search,sell;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.pawn_main_page);
		search=(LinearLayout)findViewById(R.id.search_layout);
		sell=(LinearLayout)findViewById(R.id.sell_layout);
		session=new SessionManager(getApplicationContext());
		 HashMap<String, String> user = session.getUserDetails();
         
         // name
         role = user.get(SessionManager.KEY_NAME);
         
         // email
          email = user.get(SessionManager.KEY_EMAIL);
         Toast.makeText(getApplicationContext(), role+" "+email, 1000).show();
		sell.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(role.equals("shop")){
					Intent in=new Intent(PawnMainPage.this,ShopSellingActivity.class);
					startActivity(in);
				}
				if(role.equals("user")){
					
					Intent in=new Intent(PawnMainPage.this,CustomerSelling.class);
					startActivity(in);
				}
			}
		});
		
	}

	
	public void alertForCofirm() {

		AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
		alert1.setMessage("Exit from the app?");
		alert1.setTitle("PawnaCity Says...");
		alert1.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
System.exit(0);

			}

		});
		alert1.setPositiveButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				System.exit(0);
			}

		});
		alert1.setIcon(R.drawable.desktop_logo);
		alert1.show();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		alertForCofirm();
	}


}
