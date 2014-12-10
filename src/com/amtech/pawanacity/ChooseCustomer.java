package com.amtech.pawanacity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

@SuppressLint("NewApi")
public class ChooseCustomer extends Activity {
	Button customerSignupBtn, pawnShopBtn;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.choose_customer);

		customerSignupBtn = (Button) findViewById(R.id.customer_btn);
		pawnShopBtn = (Button) findViewById(R.id.pawna_shop_btn);

		customerSignupBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(ChooseCustomer.this,
						CustomerSignUpForm.class);
				startActivity(in);
				finish();

			}
		});

		pawnShopBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(ChooseCustomer.this,
						ShopSignUpFormOne.class);
				startActivity(in);
				finish();

			}
		});

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in=new Intent(ChooseCustomer.this,PawnaMain.class);
		startActivity(in);
		finish();
		
	}
}
