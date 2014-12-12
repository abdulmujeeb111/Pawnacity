package com.amtech.pawanacity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewSwitcher;


import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

@SuppressLint("NewApi")
public class ShopSellingActivity extends ActionBarActivity implements OnClickListener{

	GridView gridGallery;
	Handler handler;
	ViewPager viewPager;
public static GalleryAdapterTwo adapter;
 
int count=0;
	ImageView imgSinglePick,imageView;
	Button home,forward;
	EditText listingItem,askinPrice;
	
	public static String categoryStr, subcatStr,listItemStr,askintPriceStr;
	Spinner category_spinn, subcat_spin;
	private ArrayList<String> array_sort;
	ArrayAdapter<String> arrayAdapter, subcatArray, jewrlyArAd,watchesArAd,diamondsArAd,coinsArAd,videoArAd,lightArAd,yardArAd,musicArAd,electroicsArAd,compArAd,tabletaArAd,cellArAd,bicycleArAd;
	String action;
	ViewSwitcher viewSwitcher;
	public static ImageLoader imageLoader;

	 String[] celebrities =
	 {"Category...","Jewerly","Whatches","Diamonds","Coins&Bullion","Video Games","Light Machines","Yard&Garden","Music Inst","Electronics","Computers","Tablets","Cell Phone","Bicycles"};
	// String[]
	// categoryName={"Necklace","Bracelet","Earrings","Rings","Broach"};
	String[] categories, subCat, jewrlySubCat, watchesSubCat, diamodsSubCat,
			coinsSubCat, videoSubCat, lightSubCat, yardSubCat, musicSubCat,
			electroinsSubCat, computerSubCat, tabletsSubCat, cellSubCat,
			bicyclesSubCat;
	private AlertDialog myalertDialog = null;
	public static ImageLoaderConfiguration config;
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getSupportActionBar().hide();
		setContentView(R.layout.shop_selling);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy); 
        
		
		category_spinn = (Spinner) findViewById(R.id.shop_cat);
		subcat_spin = (Spinner) findViewById(R.id.shop_subcat);
		home=(Button)findViewById(R.id.shop_sell_home_btn);
		forward=(Button)findViewById(R.id.shop_sell_forward_btn);
		listingItem=(EditText)findViewById(R.id.shop_sell_listing_item);
		askinPrice=(EditText)findViewById(R.id.shop_sell_asking_price);
		forward.setOnClickListener(this);
		home.setOnClickListener(this);
		viewPager=(ViewPager)findViewById(R.id.view_pager);
		categories = getResources().getStringArray(R.array.categoryArray);
		subCat = getResources().getStringArray(R.array.subCategory);
		jewrlySubCat = getResources().getStringArray(R.array.jewerlySubCategor);
		watchesSubCat = getResources().getStringArray(R.array.watechesArray);
		diamodsSubCat = getResources().getStringArray(R.array.diamondArray);
		coinsSubCat = getResources().getStringArray(R.array.coinsArray);
		videoSubCat = getResources().getStringArray(R.array.videoArray);
		lightSubCat = getResources().getStringArray(R.array.lightArray);
		yardSubCat = getResources().getStringArray(R.array.yardArray);
		musicSubCat = getResources().getStringArray(R.array.musicArray);
		electroinsSubCat = getResources().getStringArray(
				R.array.electronicsArray);
		computerSubCat = getResources().getStringArray(R.array.computerArray);
		tabletsSubCat = getResources().getStringArray(R.array.tabletsArray);
		cellSubCat = getResources().getStringArray(R.array.cellphoneArray);
		bicyclesSubCat = getResources().getStringArray(R.array.bicyclesArray);

		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, celebrities);

		subcatArray = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, subCat);

		category_spinn.setAdapter(arrayAdapter);

		subcat_spin.setAdapter(subcatArray);
		
		
		
		        

		category_spinn
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						int position = category_spinn.getSelectedItemPosition();
						categoryStr = celebrities[arg2];
						// Toast.makeText(getApplicationContext(), categoryStr,
						// 10000).show();
						if (categoryStr.equals("Jewerly")) {
							subCatCall("Jewerly");
						}
						else if (categoryStr.equals("Whatches")) {
							subCatCall("Whatches");
						}
						

						else if (categoryStr.equals("Diamonds")) {
							subCatCall("Diamonds");
						}
						else if (categoryStr.equals("Coins&Bullion")) {
							subCatCall("Coins&Bullion");
						}
						else if (categoryStr.equals("Video Games")) {
							subCatCall("Video Games");
						}
						else if (categoryStr.equals("Light Machines")) {
							subCatCall("Light Machines");
						}
						else if (categoryStr.equals("Yard&Garden")) {
							subCatCall("Yard&Garden");
						}
						else if (categoryStr.equals("Music Inst")) {
							subCatCall("Music Inst");
						}
						else if (categoryStr.equals("Electronics")) {
							subCatCall("Electronics");
						}
						else if (categoryStr.equals("Computers")) {
							subCatCall("Computers");
						}
						else if (categoryStr.equals("Tablets")) {
							subCatCall("Tablets");
						}
						else if (categoryStr.equals("Cell Phone")) {
							subCatCall("Cell Phone");
						}
						else if (categoryStr.equals("Bicycles")) {
							subCatCall("Bicycles");
						}
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});

		subcat_spin
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						int position = category_spinn.getSelectedItemPosition();
						subcatStr = categories[arg2];
						// Toast.makeText(getApplicationContext(),subcatStr,
						// 10000).show();

					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});
		initImageLoader();
		init();
	}

	private void initImageLoader() {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc().imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(
				this).defaultDisplayImageOptions(defaultOptions).memoryCache(
				new WeakMemoryCache());

		config = builder.build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
	}

	private void init() {

		handler = new Handler();
		gridGallery = (GridView) findViewById(R.id.gridGallery);
		gridGallery.setFastScrollEnabled(true);
		adapter = new GalleryAdapterTwo(getApplicationContext(), imageLoader);
		adapter.setMultiplePick(false);
		gridGallery.setAdapter(adapter);

		viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
		viewSwitcher.setDisplayedChild(1);

		imgSinglePick = (ImageView) findViewById(R.id.imgSinglePick);
		imgSinglePick.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				count=count+1;
				Intent i = new Intent(Action.ACTION_MULTIPLE_PICK);
				startActivityForResult(i, 200);
			}
		});

		/*
		 * btnGalleryPick = (Button) findViewById(R.id.btnGalleryPick);
		 * btnGalleryPick.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * Intent i = new Intent(Action.ACTION_PICK); startActivityForResult(i,
		 * 100);
		 * 
		 * } });
		 */

		// btnGalleryPickMul = (Button) findViewById(R.id.btnGalleryPickMul);

		/*
		 * btnGalleryPickMul.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { Intent i = new
		 * Intent(Action.ACTION_MULTIPLE_PICK); startActivityForResult(i, 200);
		 * } });
		 */

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
			adapter.clear();

			viewSwitcher.setDisplayedChild(1);
			String single_path = data.getStringExtra("single_path");
			imageLoader.displayImage("file://" + single_path, imgSinglePick);

		} else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
			String[] all_path = data.getStringArrayExtra("all_path");

			ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();

			for (String string : all_path) {
				CustomGallery item = new CustomGallery();
				item.sdcardPath = string;

				dataT.add(item);
			}

			viewSwitcher.setDisplayedChild(0);
			adapter.addAll(dataT);
		}
	}

	public void subCatCall(String value) {
		
		
		if(value.equals("Jewerly")){
		jewrlyArAd = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, jewrlySubCat);
		subcat_spin.setAdapter(jewrlyArAd);
		}
		else if(value.equals("Whatches")){
			watchesArAd = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, watchesSubCat);
			subcat_spin.setAdapter(watchesArAd);
			}
		else if(value.equals("Diamonds")){
			diamondsArAd = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, diamodsSubCat);
			subcat_spin.setAdapter(diamondsArAd);
			}
		else if(value.equals("Coins&Bullion")){
			coinsArAd = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, coinsSubCat);
			subcat_spin.setAdapter(coinsArAd);
			}
		else if(value.equals("Video Games")){
			videoArAd = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, videoSubCat);
			subcat_spin.setAdapter(videoArAd);
			}
		else if(value.equals("Light Machines")){
			lightArAd = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, lightSubCat);
			subcat_spin.setAdapter(lightArAd);
			}
		else if(value.equals("Yard&Garden")){
			yardArAd = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, yardSubCat);
			subcat_spin.setAdapter(yardArAd);
			}
		else if(value.equals("Music Inst")){
			musicArAd = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, musicSubCat);
			subcat_spin.setAdapter(musicArAd);
			}
		else if(value.equals("Electronics")){
			electroicsArAd = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, electroinsSubCat);
			subcat_spin.setAdapter(electroicsArAd);
			}
		else if(value.equals("Computers")){
			compArAd = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, computerSubCat);
			subcat_spin.setAdapter(compArAd);
			}
		else if(value.equals("Tablets")){
			tabletaArAd = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, tabletsSubCat);
			subcat_spin.setAdapter(tabletaArAd);
			}
		else if(value.equals("Cell Phone")){
			cellArAd = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, cellSubCat);
			subcat_spin.setAdapter(cellArAd);
			}
		else if(value.equals("Bicycles")){
			bicycleArAd = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, bicyclesSubCat);
			subcat_spin.setAdapter(bicycleArAd);
			}
		
		
		}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.shop_sell_home_btn:
			Intent in=new Intent(ShopSellingActivity.this,PawnMainPage.class);
			startActivity(in);
			finish();
			break;
			
		case R.id.shop_sell_forward_btn:
			listItemStr=listingItem.getText().toString();
			askintPriceStr=askinPrice.getText().toString();
			
			 if(category_spinn.getSelectedItemPosition()==0)
				 
			 {
				 Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG).show();
			 }
			 else if(subcat_spin.getSelectedItemPosition()==0)
				 
			 {
				 Toast.makeText(getApplicationContext(), "Please Select Sub Category", Toast.LENGTH_LONG).show();
			 }
			 
 else if(count==0)
				 
			 {
				 Toast.makeText(getApplicationContext(), "select image", 1000).show();
			 }
			 else if(listItemStr.equals(""))
				 
			 {
				 listingItem.setHint("invalid listing title");
				 listingItem.setHintTextColor(Color.parseColor("#FF0000"));
			 }
			 else if(askintPriceStr.equals(""))
				 
			 {
				 listingItem.setHint("invalid aking price");
				 listingItem.setHintTextColor(Color.parseColor("#FF0000"));
			 }
			 
			 else{
				 
				 Intent intent=new Intent(ShopSellingActivity.this,ShopSellingDescription.class);
				 startActivity(intent);
			 }
			break;
		default:
			break;
		}		
	}
	

}
