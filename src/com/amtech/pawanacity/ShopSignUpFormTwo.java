package com.amtech.pawanacity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("NewApi")
public class ShopSignUpFormTwo extends Activity {
	// Boolean refreshflag;

	private static final int SELECT_FILE1 = 1;
	private static final int SELECT_FILE2 = 2;
	private static final int SELECT_FILE3 = 3;
	private static final int SELECT_FILE4 = 4;
	// ProgressDialog dialogPd;
	String selectedPath1 = null;
	String selectedPath2 = null;
	String selectedPath3 = null;
	String selectedPath4 = null;
	Boolean flag = true;
	Boolean flag1 = true;
	
	String response_str;

	TextView tv, res;
	int code = 0;
	// Uri imageUri;

	String location1;

	String lon, lat;
	double latitude, longitude;

	Button b3;

	HttpEntity resEntity;

	AlertDialog.Builder bu1;

	String citiesString, areaStr, crimeStr, currentTimeStamp;
	String result;
	// private ProgressDialog dialog;
	Uri imageUri;
	int imageNumber = 0;
	private Bitmap bitmap, bitmap1, bitmap2, bitmap3;
	String subcatpostn, firstName, lastName;
	String cityid, subcatid;
	Spinner category_spinner, subcategory_spinner2;
	ListView listV;

	private static final String TAG_SUCCESS = "success";

	private static final int PICK_IMAGE = 1;
	private static final int PICK_Camera_IMAGE = 2;
	ImageView imageOne, imageTwo, imageThree, imageFour;

	List<String> categoryName = new ArrayList<String>();

	List<String> categoryId = new ArrayList<String>();
	List<String> subcategoryId = new ArrayList<String>();
	List<String> subcategoryName = new ArrayList<String>();
	String getname, getcolor, getprice, getcontact, getdescription;
	// Progress Dialog
	ProgressDialog dialogPd;
	JSONObject json;

	String matchMail = "[a-zA-Z0-9._-]+@+[a-z]+.+[a-z]";

	ImageView shopLogo;
	EditText owner_name, owner_phone, owner_email;
	Button backButton, forwardButton;
	String ownerName, ownerPhone, ownerEmail, storeNameStr, storePhoneStr,
			storeAddressStr, storeCityStr, storeStateStr, storePasswordStr,
			storeConfirmPasswordStr, storeZipCodeStr, stateName;

	ArrayAdapter<String> categoryAdapter, subcategoryAdapter;

	EditText inputname, inputcolor, inputprice, inputcontact, inputdescription,
			inputimage, inputArea;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
		setContentView(R.layout.pawnshop_signup_two);
		bu1 = new AlertDialog.Builder(ShopSignUpFormTwo.this);
		owner_name = (EditText) findViewById(R.id.owner_name_txt);
		owner_phone = (EditText) findViewById(R.id.owner_phone_txt);
		owner_email = (EditText) findViewById(R.id.owner_email_txt);
		ShopSignUpFormOne ssf = new ShopSignUpFormOne();
		storeNameStr = ssf.storeName;
		storePhoneStr = ssf.storePhone;
		storeAddressStr = ssf.storeAddress;
		storeCityStr = ssf.storeCity;
		storeStateStr = ssf.stateName;
		storePasswordStr = ssf.storePassword;
		storeZipCodeStr = ssf.storeZipCode;

		shopLogo = (ImageView) findViewById(R.id.shoplogo_img);

		backButton = (Button) findViewById(R.id.signup_two_back);

		forwardButton = (Button) findViewById(R.id.signup_two_forword);

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent in=new Intent(ShopSignUpFormTwo.this,ShopSignUpFormOne.class);
				startActivity(in);

			}
		});

		shopLogo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				code = SELECT_FILE1;
				getDialog();
				// openGallery(SELECT_FILE1);

			}
		});

		forwardButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// // TODO Auto-generated method stub
				ownerName = owner_name.getText().toString();
				ownerPhone = owner_phone.getText().toString();
				ownerEmail = owner_email.getText().toString();
				if (ownerName.equals("") && ownerPhone.equals("")
						&& ownerEmail.equals("")) {
					owner_name.setHint("invalid full name");
					owner_name.setHintTextColor(Color.parseColor("#FF0000"));
					owner_phone.setHint("invalid phone");
					owner_phone.setHintTextColor(Color.parseColor("#FF0000"));
					owner_email.setHint("invalid email");
					owner_email.setHintTextColor(Color.parseColor("#FF0000"));

				} else if (ownerName.equals("")) {
					owner_name.setHint("invalid full name");
					owner_name.setHintTextColor(Color.parseColor("#FF0000"));

				} else if (ownerPhone.equals("")) {
					owner_phone.setHint("invalid phone");
					owner_phone.setHintTextColor(Color.parseColor("#FF0000"));

				} else if (!(ownerEmail.matches(matchMail))) {
					owner_email.setText("");
					owner_email.setHint("invalid email");
					owner_email.setHintTextColor(Color.parseColor("#FF0000"));
				}

				else {
					
					
					

					if (!(selectedPath1 == null)) {

						dialogPd = new ProgressDialog(ShopSignUpFormTwo.this);
						dialogPd.setTitle("In Progress");
						dialogPd.setMessage("Please Wait");
						dialogPd.show();
						Thread thread = new Thread(new Runnable() {
							public void run() {
								doFileUpload();

								runOnUiThread(new Runnable() {
									public void run() {
										try {
											JSONObject jobject=new JSONObject(response_str);
											result = jobject
													.getString("success");
											try {
												if (Integer.parseInt(result) == 1) {
													dialogPd.dismiss();
													/*
													 * Toast.makeText(
													 * getApplicationContext(),
													 * "Successfully logged in",
													 * 1000).show();
													 */

													Intent in = new Intent(ShopSignUpFormTwo.this,
															ThanksScreen.class);
													in.putExtra("emailKye", ownerEmail);
													startActivity(in);
													finish();

												} else if (Integer.parseInt(result) == 0) {
													dialogPd.dismiss();
													Toast.makeText(
															ShopSignUpFormTwo.this,
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

											
											
										} catch (Exception e) {
											// TODO: handle exception
										}
										
										
										
										
										
										if (!flag)
											Toast.makeText(
													ShopSignUpFormTwo.this,
													"Please connect to the internet and try again.",
													1000).show();
										if (!flag1) {
											dialogPd.dismiss();
											owner_name.setText("");
											owner_name
													.setHint("invalid full name");
											owner_name.setHintTextColor(Color
													.parseColor("#FF0000"));
										}
										flag1 = true;
										if (dialogPd.isShowing())
											dialogPd.dismiss();
										flag = true;

									}
								});

							}
						});
						thread.start();
					}

					else {
						Toast.makeText(getApplicationContext(),
								"Please select store logo.", Toast.LENGTH_SHORT)
								.show();
					}

				}
			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri selectedImageUri = null;
		// String filePath = null;

		// user is returning from capturing an image using the camera
		switch (requestCode) {
		case PICK_IMAGE:
			if (resultCode == Activity.RESULT_OK) {
				selectedImageUri = data.getData();

			}
			break;
		case PICK_Camera_IMAGE:
			if (resultCode == RESULT_OK) {
				// use imageUri here to access the image
				selectedImageUri = imageUri;

				/*
				 * Intent intnt = new Intent(A.this,Bclass);
				 * intnt.putExtra("photo", photo); startActivity(intnt);
				 * 
				 * And on activity B- Intent intnt = getIntent(); final Bitmap
				 * crop_photo = (Bitmap) intnt.getParcelableExtra("photo");
				 */

				/*
				 * Bitmap mPic = (Bitmap) data.getExtras().get("data");
				 * selectedImageUri =
				 * Uri.parse(MediaStore.Images.Media.insertImage
				 * (getContentResolver(), mPic,
				 * getResources().getString(R.string.app_name),
				 * Long.toString(System.currentTimeMillis())));
				 */
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Picture was not taken",
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Picture was not taken",
						Toast.LENGTH_SHORT).show();
			}
			break;
		}
		if (code == SELECT_FILE1) {

			if (selectedImageUri != null) {
				try {
					// OI FILE Manager
					String filemanagerstring = selectedImageUri.getPath();

					// MEDIA GALLERY
					String selectedImagePath = getPath(selectedImageUri);

					if (selectedImagePath != null) {
						selectedPath1 = selectedImagePath;
					} else if (filemanagerstring != null) {
						selectedPath1 = filemanagerstring;
					} else {
						Toast.makeText(getApplicationContext(), "Unknown path",
								Toast.LENGTH_LONG).show();
						Log.e("Bitmap", "Unknown path");
					}

					if (selectedPath1 != null) {
						decodeFile(selectedPath1, code);
					} else {
						bitmap = null;
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Internal error",
							Toast.LENGTH_LONG).show();
					Log.e(e.getClass().getName(), e.getMessage(), e);
				}
			}
		}

		if (code == SELECT_FILE2) {

			if (selectedImageUri != null) {
				try {
					// OI FILE Manager
					String filemanagerstring = selectedImageUri.getPath();

					// MEDIA GALLERY
					String selectedImagePath = getPath(selectedImageUri);

					if (selectedImagePath != null) {
						selectedPath2 = selectedImagePath;
					} else if (filemanagerstring != null) {
						selectedPath2 = filemanagerstring;
					} else {
						Toast.makeText(getApplicationContext(), "Unknown path",
								Toast.LENGTH_LONG).show();
						Log.e("Bitmap", "Unknown path");
					}

					if (selectedPath2 != null) {
						decodeFile(selectedPath2, code);
					} else {
						bitmap = null;
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Internal error",
							Toast.LENGTH_LONG).show();
					Log.e(e.getClass().getName(), e.getMessage(), e);
				}
			}
		}

		if (code == SELECT_FILE3) {

			if (selectedImageUri != null) {
				try {
					// OI FILE Manager
					String filemanagerstring = selectedImageUri.getPath();

					// MEDIA GALLERY
					String selectedImagePath = getPath(selectedImageUri);

					if (selectedImagePath != null) {
						selectedPath3 = selectedImagePath;
					} else if (filemanagerstring != null) {
						selectedPath3 = filemanagerstring;
					} else {
						Toast.makeText(getApplicationContext(), "Unknown path",
								Toast.LENGTH_LONG).show();
						Log.e("Bitmap", "Unknown path");
					}

					if (selectedPath3 != null) {
						decodeFile(selectedPath3, code);
					} else {
						bitmap = null;
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Internal error",
							Toast.LENGTH_LONG).show();
					Log.e(e.getClass().getName(), e.getMessage(), e);
				}
			}
		}

		if (code == SELECT_FILE4) {

			if (selectedImageUri != null) {
				try {
					// OI FILE Manager
					String filemanagerstring = selectedImageUri.getPath();

					// MEDIA GALLERY
					String selectedImagePath = getPath(selectedImageUri);

					if (selectedImagePath != null) {
						selectedPath4 = selectedImagePath;
					} else if (filemanagerstring != null) {
						selectedPath4 = filemanagerstring;
					} else {
						Toast.makeText(getApplicationContext(), "Unknown path",
								Toast.LENGTH_LONG).show();
						Log.e("Bitmap", "Unknown path");
					}

					if (selectedPath4 != null) {
						decodeFile(selectedPath4, code);
					} else {
						bitmap = null;
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Internal error",
							Toast.LENGTH_LONG).show();
					Log.e(e.getClass().getName(), e.getMessage(), e);
				}
			}
		}

	}

	public void getDialog() {
		// bu1.setTitle("Chooce one");
		bu1.setTitle("Select Configuration");
		bu1.setNeutralButton("Camera", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				// define the file-name to save photo taken by Camera activity
				String fileName = "new-photo-name.jpg";
				// create parameters for Intent with filename
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.TITLE, fileName);
				values.put(MediaStore.Images.Media.DESCRIPTION,
						"Image captured by camera");
				// imageUri is the current activity attribute, define and save
				// it for later usage (also in onSaveInstanceState)
				imageUri = getContentResolver().insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
				// create new Intent
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

				startActivityForResult(intent, PICK_Camera_IMAGE);

			}
		});

		bu1.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					Intent gintent = new Intent();
					gintent.setType("image/*");
					gintent.setAction(Intent.ACTION_GET_CONTENT);

					startActivityForResult(
							Intent.createChooser(gintent, "Select Picture"),
							PICK_IMAGE);

					Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
					gintent.setType("image/*");
					gintent.putExtra("crop", "true");
					gintent.putExtra("aspectX", 116);
					gintent.putExtra("aspectY", 116);
					gintent.putExtra("outputX", 116);
					gintent.putExtra("outputY", 116);
					gintent.putExtra("scale", 116);
					gintent.putExtra("return-data", true);
					gintent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					gintent.putExtra("outputFormat",
							Bitmap.CompressFormat.JPEG.toString());

				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), e.getMessage(),
							Toast.LENGTH_LONG).show();
					Log.e(e.getClass().getName(), e.getMessage(), e);
				}

			}

		});

		bu1.setPositiveButton("Cancel", null);
		bu1.show();

	}

	private void doFileUpload() {
		File file1 = new File(selectedPath1);

		String urlString = "http://pawn.amtechgcc.in/a.php";

		try {
			String[] values = ownerName.split(" ");
			firstName = values[0];
			lastName = values[1];
			try {
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(urlString);
				FileBody bin1 = new FileBody(file1);

				MultipartEntity reqEntity = new MultipartEntity();
				reqEntity.addPart("tag", new StringBody("register"));
				reqEntity.addPart("firstname", new StringBody(firstName));
				reqEntity.addPart("lastname", new StringBody(lastName));
				reqEntity.addPart("store_name", new StringBody(storeNameStr));
				reqEntity.addPart("store_phone", new StringBody(storePhoneStr));
				reqEntity.addPart("email", new StringBody(ownerEmail));
				reqEntity.addPart("password", new StringBody(storePasswordStr));
				reqEntity.addPart("city", new StringBody(storeCityStr));
				reqEntity.addPart("store_address", new StringBody(
						storeAddressStr));
				reqEntity.addPart("state", new StringBody(storeStateStr));
				reqEntity.addPart("zipcode", new StringBody(storeZipCodeStr));
				reqEntity.addPart("owner_name", new StringBody(ownerName));
				reqEntity.addPart("owner_phone", new StringBody(ownerPhone));
				reqEntity.addPart("role", new StringBody("shop"));

				reqEntity.addPart("uploadedfile1", bin1);

				/*
				 * reqEntity.addPart("uploadedfile4", bin4);
				 * reqEntity.addPart("uploadedfile4", bin4);
				 */
				reqEntity.addPart("user", new StringBody("User"));
				post.setEntity(reqEntity);
				HttpResponse response = client.execute(post);
				resEntity = response.getEntity();
				response_str = EntityUtils.toString(resEntity);
				if (resEntity != null) {
					Log.i("RESPONSE", response_str);
					runOnUiThread(new Runnable() {
						public void run() {
							try {
							

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			} catch (Exception ex) {
				dialogPd.dismiss();
				flag = false;
				Log.e("Debug", "error: " + ex.getMessage(), ex);
			}
		} catch (Exception e) {
			flag1 = false;
		}

	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			// HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
			// THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	public void decodeFile(String filePath, int code) {

		if (code == SELECT_FILE1) {// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(selectedPath1, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 1024;

			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			bitmap = BitmapFactory.decodeFile(selectedPath1, o2);

			shopLogo.setImageBitmap(bitmap);

		}

		if (code == SELECT_FILE2) {// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(selectedPath2, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 1024;

			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			bitmap = BitmapFactory.decodeFile(selectedPath2, o2);

			// img2.setImageBitmap(bitmap);

		}

		if (code == SELECT_FILE3) {
			// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(selectedPath3, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 1024;

			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			bitmap = BitmapFactory.decodeFile(selectedPath3, o2);

			// img3.setImageBitmap(bitmap);

		}

		if (code == SELECT_FILE4) {// Decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(selectedPath4, o);

			// The new size we want to scale to
			final int REQUIRED_SIZE = 1024;

			// Find the correct scale value. It should be the power of 2.
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			bitmap = BitmapFactory.decodeFile(selectedPath4, o2);

			// img4.setImageBitmap(bitmap);

		}
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
						Intent in=new Intent(ShopSignUpFormTwo.this,PawnLoginScreen.class);
						startActivity(in);
						finish();

					}
				});

		alert1.setNegativeButton("Logout",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent in=new Intent(ShopSignUpFormTwo.this,PawnaMain.class);
						startActivity(in);
						finish();


					}

				});
		alert1.setIcon(R.drawable.desktop_logo);
		alert1.show();

	}
	

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// Intent categoryintent=new
		// Intent(getParent(),FirstLoginActivity.class);
		// TabGroupActivity tbg = (TabGroupActivity)getParent();

		// tbg.startChildActivity("Category",categoryintent);
		// finish();
		/*
		 * Intent i = new Intent(NewJinniPostAdd.this,MainActivity.class);
		 * startActivity(i);
		 */
	}

}
