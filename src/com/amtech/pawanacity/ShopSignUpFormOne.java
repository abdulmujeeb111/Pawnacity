package com.amtech.pawanacity;

import java.util.ArrayList;
import java.util.Arrays;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("NewApi")
public class ShopSignUpFormOne extends Activity implements OnClickListener,
		OnItemClickListener {

	String matchMail = "[a-zA-Z0-9._-]+@+[a-z]+.+[a-z]";

	EditText store_name, store_phone, store_address, store_city,
			store_password, store_confirmPassword, store_zipCode;

	Button signup_one_back_btn, signup_one_forward_btn, signup_one_state_btn,select_state;

	public static String storeName, storePhone, storeAddress, storeCity,storeStateEdit,
			storeState, storePassword, storeConfirmPassword, storeZipCode;
	
	public static String stateName=null;

	ProgressDialog dialogPd;

	private String TitleName[] = { 
			"Alabama",
			"Alaska",
			"Arizona",
			"Arkansas",
			"California",
			"Colorado",
			"Connecticut",
			"Delaware",
			"Florida",
			"Georgia",
			"Hawaii",
			"Idaho",
			"Illinois",
			"Indiana",
			"Iowa",
			"Kansas",
			"Kentucky",
			"Louisiana",
			"Maine",
			"Maryland",
			"Massachusetts",
			"Michigan",
			"Minnesota",
			"Mississippi",
			"Missouri",
			"Montana",
			"Nebraska",
			"Nevada",
			"New Hampshire",
			"New Jersey",
			"NewMexico",
			"New York",
			"North Carolina",
			"North Dakota",
			"Ohio",
			"Oklahoma",
			"Oregon",
			"Pennsylvania",
			"Rhode Island",
			"South Carolina",
			"South Dakota",
			"Tennessee",
			"Texas",
			"Utah",
			"Vermont",
			"Virginia",
			"Washington",
			"West Virginia",
			"Wisconsin",
			"Wyoming"
 };

	private ArrayList<String> array_sort;

	int textlength = 0;

	private AlertDialog myalertDialog = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.pawnshop_signup_one);
		store_name = (EditText) findViewById(R.id.store_name_txt);
		store_phone = (EditText) findViewById(R.id.store_phone_txt);
		store_address = (EditText) findViewById(R.id.store_address_txt);
		store_city = (EditText) findViewById(R.id.store_city_edit);
		store_password = (EditText) findViewById(R.id.store_password_txt);
		store_confirmPassword = (EditText) findViewById(R.id.store_confirm_password_txt);
		store_zipCode = (EditText) findViewById(R.id.store_zipcode_txt);

		signup_one_forward_btn = (Button) findViewById(R.id.signup_one_forward);
		signup_one_back_btn = (Button) findViewById(R.id.signup_one_back);
		select_state = (Button) findViewById(R.id.state_selct_);

		signup_one_forward_btn.setOnClickListener(this);
		signup_one_back_btn.setOnClickListener(this);
		select_state.setOnClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

		myalertDialog.dismiss();
		stateName = TitleName[position];
		select_state.setText(stateName);
		select_state.setTextColor(Color.parseColor("#FFFFFF"));
		//Toast.makeText(ShopSignUpFormOne.this, stateName, 10000).show();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.state_selct_:

			AlertDialog.Builder myDialog = new AlertDialog.Builder(
					ShopSignUpFormOne.this);


			final ListView listview = new ListView(ShopSignUpFormOne.this);

			array_sort = new ArrayList<String>(Arrays.asList(TitleName));
			LinearLayout layout = new LinearLayout(ShopSignUpFormOne.this);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setBackgroundResource(R.drawable.plain_bg);

			//layout.addView(editText);
			layout.addView(listview);
			myDialog.setView(layout);
			StateAlertAdapter arrayAdapter = new StateAlertAdapter(
					ShopSignUpFormOne.this, array_sort);
			listview.setAdapter(arrayAdapter);
			listview.setOnItemClickListener(this);
			
			myDialog.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			
			myalertDialog = myDialog.show();
			
			break;

		case R.id.signup_one_forward:

			storeName = store_name.getText().toString();
			storePhone = store_phone.getText().toString();
			storeAddress = store_address.getText().toString();
			storeCity = store_city.getText().toString();
			storeStateEdit=select_state.getText().toString();
			storePassword = store_password.getText().toString();
			storeConfirmPassword = store_confirmPassword.getText().toString();
			storeZipCode = store_zipCode.getText().toString();
			/*if (storeName.equals("") && storePhone.equals("")
					&& storeAddress.equals("") && storeCity.equals("")
					&& storePassword.equals("")
					&& storeConfirmPassword.equals("")) {
				store_name.setHint("invalid full name");
				store_name.setHintTextColor(Color.parseColor("#FF0000"));
				store_phone.setHint("invalid phone");
				store_phone.setHintTextColor(Color.parseColor("#FF0000"));
				store_address.setHint("invalid address");
				store_address.setHintTextColor(Color.parseColor("#FF0000"));
				store_city.setHint("invalid city name");
				store_city.setHintTextColor(Color.parseColor("#FF0000"));
				store_password.setHint("invalid password");
				store_password.setHintTextColor(Color.parseColor("#FF0000"));
				store_confirmPassword.setHint("invalid password");
				store_confirmPassword.setHintTextColor(Color
						.parseColor("#FF0000"));

			}*/
			 if (storeName.equals("")) {
				store_name.setHint("invalid store name");
				store_name.setHintTextColor(Color.parseColor("#FF0000"));

			}

			else if (storePhone.equals("")) {
				store_phone.setHint("invalid phone");
				store_phone.setHintTextColor(Color.parseColor("#FF0000"));

			}
			 
			else if (storeAddress.equals("")) {
				store_address.setHint("invalid address");
				store_phone.setHintTextColor(Color.parseColor("#FF0000"));

			} else if (storeCity.equals("")) {
				store_city.setHint("invalid city");
				store_city.setHintTextColor(Color.parseColor("#FF0000"));

			}
			else if (stateName==null) {
				select_state.setText("invalid state");
				select_state.setTextColor(Color.parseColor("#FF0000"));

			}
			else if (storeZipCode.equals("")) {
				store_zipCode.setHint("invalid zipcode");
				store_zipCode.setHintTextColor(Color.parseColor("#FF0000"));

			}else if (storePassword.equals("")) {
				store_phone.setHint("invalid password");
				store_phone.setHintTextColor(Color.parseColor("#FF0000"));

			} else if (storeConfirmPassword.equals("")) {
				store_confirmPassword.setHint("invalid password");
				store_confirmPassword.setHintTextColor(Color
						.parseColor("#FF0000"));

			} else if (!storeConfirmPassword.equals(storePassword)) {
				store_confirmPassword.setText("");
				store_confirmPassword.setHint("invalid password");
				store_confirmPassword.setHintTextColor(Color
						.parseColor("#FF0000"));
				//Toast.makeText(ShopSignUpFormOne.this, "Password must be same", 1000).show();
			}

			else {

				Intent in = new Intent(ShopSignUpFormOne.this,
						ShopSignUpFormTwo.class);
				startActivity(in);
				finish();
			}
			break;

		case R.id.signup_one_back:
			onBackPressed();
			break;

		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in=new Intent(ShopSignUpFormOne.this,ChooseCustomer.class);
		startActivity(in);
		finish();
	}
}
