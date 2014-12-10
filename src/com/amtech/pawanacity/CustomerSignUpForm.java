package com.amtech.pawanacity;

import org.json.JSONException;
import org.json.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class CustomerSignUpForm extends Activity {
	String space = " ";
	String matchMail = "[a-zA-Z0-9._-]+@+[a-z]+.+[a-z]";
	String matchFullName = "[a-zA-Z]+\\s+[a-zA-Z]";
	String result;
	public static int i = 1;
	Boolean flag = true;
	EditText fullName, phone, email, password, confirmPassword, zipCode;
	Button customerSignup;
	public static String cusFullName, cusPhone, cusEmail, cusPassword,
			cusConfirmPassword, cusZipCode, firstName, lastName;
	ProgressDialog dialogPd;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.customer_signup);
		fullName = (EditText) findViewById(R.id.full_name_txt);
		phone = (EditText) findViewById(R.id.phone_txt);
		email = (EditText) findViewById(R.id.email_txt);
		password = (EditText) findViewById(R.id.password_txt);
		confirmPassword = (EditText) findViewById(R.id.confirm_password_txt);
		zipCode = (EditText) findViewById(R.id.zipcode_txt);
		customerSignup = (Button) findViewById(R.id.signup_customer_btn);

		customerSignup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				cusFullName = fullName.getText().toString();
				cusPhone = phone.getText().toString();
				cusEmail = email.getText().toString();
				cusPassword = password.getText().toString();
				cusConfirmPassword = confirmPassword.getText().toString();
				cusZipCode = zipCode.getText().toString();

				if (cusFullName.equals("") && cusPhone.equals("")
						&& cusEmail.equals("") && cusPassword.equals("")
						&& cusConfirmPassword.equals("")
						&& cusZipCode.equals("")) {
					fullName.setHint("invalid full name");
					fullName.setHintTextColor(Color.parseColor("#FF0000"));
					phone.setHint("invalid phone");
					phone.setHintTextColor(Color.parseColor("#FF0000"));
					email.setHint("invalid email");
					email.setHintTextColor(Color.parseColor("#FF0000"));
					password.setHint("password don't  match");
					password.setHintTextColor(Color.parseColor("#FF0000"));
					confirmPassword.setHint("password don't  match");
					confirmPassword.setHintTextColor(Color
							.parseColor("#FF0000"));
					zipCode.setHint("invalid zip code");
					zipCode.setHintTextColor(Color.parseColor("#FF0000"));

				} else if (cusFullName.equals("")) {
					fullName.setHint("invalid full name");
					fullName.setHintTextColor(Color.parseColor("#FF0000"));

				}/*
				 * else if (!(cusFullName.matches(matchFullName))) {
				 * fullName.setText(""); fullName.setHint("invalid full name");
				 * fullName.setHintTextColor(Color.parseColor("#FF0000"));
				 * //Toast.makeText(CustomerSignUpForm.this,
				 * "Enter Valid Email", 1000).show(); }
				 */

				else if (cusPhone.equals("")) {
					phone.setHint("invalid phone");
					phone.setHintTextColor(Color.parseColor("#FF0000"));

				} else if (!(cusEmail.matches(matchMail))) {
					email.setText("");
					email.setHint("invalid email");
					email.setHintTextColor(Color.parseColor("#FF0000"));
					// Toast.makeText(CustomerSignUpForm.this,
					// "Enter Valid Email", 1000).show();
				}

				else if (cusPassword.equals("")) {
					password.setHint("password don't  match");
					password.setHintTextColor(Color.parseColor("#FF0000"));

				}

				else if (cusConfirmPassword.equals("")) {

					confirmPassword.setHint("password don't  match");
					confirmPassword.setHintTextColor(Color
							.parseColor("#FF0000"));

				}

				else if (!cusPassword.equals(cusConfirmPassword)) {

					confirmPassword.setText("");
					confirmPassword.setHint("Password must be same");
					confirmPassword.setHintTextColor(Color
							.parseColor("#FF0000"));
					// Toast.makeText(CustomerSignUpForm.this,
					// "Password must be same", 1000).show();
				} else if (cusZipCode.equals("")) {
					zipCode.setHint("invalid zip code");
					zipCode.setHintTextColor(Color.parseColor("#FF0000"));

				} else {
					try {
						String[] values = cusFullName.split(" ");
						firstName = values[0];
						lastName = values[1];

						dialogPd = new ProgressDialog(CustomerSignUpForm.this);
						dialogPd.setTitle("In Progress");
						dialogPd.setMessage("Please Wait");
						dialogPd.show();

						AsyncHttpClient client = new AsyncHttpClient();
						RequestParams params = new RequestParams();

						try {

							params.put("tag", "customer");
							Log.i("tag", "customer");

							params.put("firstname", firstName);
							Log.i("firstname", firstName);

							params.put("lastname", lastName);
							Log.i("lastname", lastName);

							params.put("store_phone", cusPhone);
							Log.i("phone", cusPhone);

							params.put("email", cusEmail);
							Log.i("email", cusEmail);

							params.put("password", cusPassword);
							Log.i("password", cusPassword);

							params.put("zipcode", cusZipCode);
							Log.e("zipcode", cusZipCode);

							params.put("role", "user");
							Log.e("role", "user");

						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						client.post("http://pawn.amtechgcc.in/a.php", params,
								new AsyncHttpResponseHandler() {

									@Override
									public void onSuccess(String response) {
										// dialog.dismiss();
										Log.i("response for iservice connect",
												response);

										JSONObject jarray;
										try {
											jarray = new JSONObject(response);
											result = jarray
													.getString("success");
										} catch (JSONException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}

										// Log.e("result", result);

										try {
											if (Integer.parseInt(result) == 1) {
												dialogPd.dismiss();
												/*
												 * Toast.makeText(
												 * getApplicationContext(),
												 * "Successfully logged in",
												 * 1000).show();
												 */

												Intent logIntent = new Intent(
														CustomerSignUpForm.this,
														ThanksScreen.class);
												logIntent.putExtra("emailKye",
														cusEmail);
												startActivity(logIntent);
												finish();

											} else if (Integer.parseInt(result) == 0) {
												dialogPd.dismiss();
												Toast.makeText(
														CustomerSignUpForm.this,
														"oops an error occoured",
														10000).show();
											} else if (Integer.parseInt(result) == 2) {
												dialogPd.dismiss();
												dublicateAlert();
												// Toast.makeText(CustomerSignUpForm.this,"oops an error occoured",
												// 10000).show();
											}

										} catch (Exception e) {
											dialogPd.dismiss();
											// TODO: handle exception
											Toast tost = new Toast(
													getApplicationContext());
											tost.makeText(
													getApplicationContext(),
													"Enter valid  eamil", 1000)
													.show();

										}

									}

									@Override
									public void onFailure(Throwable e) {

										dialogPd.dismiss();
										Toast tost = new Toast(
												getApplicationContext());
										tost.makeText(
												getApplicationContext(),
												"Please connect to the internet and try again.",
												1000).show();
									}
								});

					} catch (Exception e) {
						flag = false;

						// TODO: handle exception
						fullName.setText("");
						fullName.setHint("invalid full name");
						fullName.setHintTextColor(Color.parseColor("#FF0000"));
					}
					if (false) {
						dialogPd.dismiss();
					}
				}

			}

		});

	}

	public void dublicateAlert() {

		AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
		LinearLayout layout = new LinearLayout(this);
		TextView tvMessage = new TextView(this);

		layout.setOrientation(LinearLayout.VERTICAL);
		layout.addView(tvMessage);

		tvMessage.setText("Oops, looks like you already have an account");

		tvMessage.setGravity(Gravity.CENTER);

		alert1.setTitle("PawnaCity Says...");
		alert1.setView(layout);

		alert1.setPositiveButton("Login",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent in=new Intent(CustomerSignUpForm.this,PawnLoginScreen.class);
						startActivity(in);
						finish();

					}
				});

		alert1.setNegativeButton("Logout",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent in=new Intent(CustomerSignUpForm.this,PawnaMain.class);
						startActivity(in);
						finish();


					}

				});
		alert1.setIcon(R.drawable.desktop_logo);
		alert1.show();

	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent in=new Intent(CustomerSignUpForm.this,ChooseCustomer.class);
		startActivity(in);
		finish();
	}
}
