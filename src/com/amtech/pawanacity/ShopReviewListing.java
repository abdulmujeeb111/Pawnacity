package com.amtech.pawanacity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.Bitmap.CompressFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopReviewListing extends ActionBarActivity  {
	ViewPager viewPager;
	public ArrayList<String> map = new ArrayList<String>();
	ImageView imageView;
	ArrayList<CustomGallery> deeta;
	int count = 0;
	Boolean flag = true;
	Boolean flag1 = true;
	String result;
	String response_str;
	ProgressDialog dialog;
	private boolean gps_enabled=false;
    private boolean network_enabled=false;
    Location location;
    MultipartEntity entity;
    ProgressDialog dialogPd;
	HttpEntity resEntity;
    Button back,pawndollar;
    Double MyLat, MyLong;
    String CityName="";
    String StateName="";
    String CountryName="";
    String newFhase="";
	GPSTracker gps;
	List<Bitmap> mThumbIds ;
	String[] allPaths;
	AppLocationService appLocationService;
	String productTieltStr,productPriceStr,addressStr,prodesStr,timePost,catStr,subcatStr,formattedDate;
	TextView productPrice,productTitle,address,description,time;
	//String cusZipStr;
	ShopSellingActivity ma=new ShopSellingActivity();
	
	//SubAdapter sa=new SubAdapter();
	
	CustomGalleryActivity cga=new CustomGalleryActivity();
	ShopSellingDescription csd=new ShopSellingDescription();
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	getSupportActionBar().hide();
	setContentView(R.layout.shop_review_listing);
String fontPath = "fonts/Verdana.ttf";
    
    Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
	back=(Button)findViewById(R.id.review_back);
	back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent in=new Intent(ShopReviewListing.this,ShopSellingDescription.class);
			startActivity(in);
			finish();
			
		}
	});
	pawndollar=(Button)findViewById(R.id.review_sell_dollar);
	pawndollar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//new ImageUploadTask().execute(count + "", "hm" + count
			  //      + ".jpg");
			dodldodld();
			
		}
	});
	productPrice=(TextView)findViewById(R.id.pro_price);
	productPrice.setTypeface(tf);
	productTitle=(TextView)findViewById(R.id.pro_title);
	productTitle.setTypeface(tf);
	description=(TextView)findViewById(R.id.pro_descriptin);
	description.setTypeface(tf);
	address=(TextView)findViewById(R.id.address_cust);
	address.setTypeface(tf);
	time=(TextView)findViewById(R.id.time_post);
	time.setTypeface(tf);
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
	formattedDate = sdf.format(date);
	productTieltStr=ma.listItemStr;
	productPriceStr=ma.askintPriceStr;
	catStr=ma.categoryStr;
	subcatStr=ma.subcatStr;
	prodesStr=csd.descriptionStr;
	//cusZipStr=csd.zipStr;
	productTitle.setText(productTieltStr);
	productPrice.setText("$ "+productPriceStr);
	description.setText(prodesStr);
	time.setText("posted : "+formattedDate);
	allPaths=cga.allPath;
	
	
	  
	 if (allPaths != null) {
		   
		   for (int i = 0; i < allPaths.length; i++) {
		    map.add(allPaths[i].toString());
		   }
		  } else {
		   Toast.makeText(getApplicationContext(), "No selected paths", 1000).show();
		  }
	deeta=GalleryAdapter.data;
	
	//mThumbIds=new ArrayList<Bitmap>();
	
	viewPager=(ViewPager)findViewById(R.id.view_pager);
	/*for(int i=0 ;i < allPaths.length;i++)
	{
		
		 String imageURL=allPaths[i];
			Log.i("Imgaurl", imageURL);
			
			//for exapmle 10
			URL url = new URL("Your URL");//image url is stored here
			mThumbIds[0]= BitmapFactory.decodeStream(url.openConnection().getInputStream())
//			Bitmap bitmap = null;
			try {
				// Download Image from URL
				InputStream input = new java.net.URL(imageURL).openStream();
				// Decode Bitmap
				mThumbIds.add(BitmapFactory.decodeStream(input)) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
	}*/
	
	ImageAdapter adapter = new ImageAdapter();
    viewPager.setAdapter(adapter);
    turnGPSOn(); // method to turn on the GPS if its in off state.
    getMyCurrentLocation();
	  if(!StateName.equals(null)){
	  address.setText(StateName);
	  }
	  else{
		  address.setText("No Address Found");
		  
	  }
}
private void doFileUpload() {
		
		String urlString = "http://pawn.amtechgcc.in/customerselling.php";

		try {
//			String[] values = ownerName.split(" ");
/*			firstName = values[0];
			lastName = values[1];*/
			try {
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(urlString);
//				FileBody bin1 = new FileBody(file1);

				MultipartEntity reqEntity = new MultipartEntity();
				reqEntity.addPart("category", new StringBody(catStr));
				reqEntity.addPart("subcategory", new StringBody(subcatStr));
				reqEntity.addPart("pawn", new StringBody("pawn"));
				reqEntity.addPart("reciept-kg", new StringBody("199"));
				reqEntity.addPart("itemname", new StringBody(productTieltStr));
				reqEntity.addPart("itemprice", new StringBody(productPriceStr));
				reqEntity.addPart("description", new StringBody(prodesStr));
			//	reqEntity.addPart("zipcode", new StringBody(cusZipStr));
				reqEntity.addPart("address", new StringBody(StateName));
				reqEntity.addPart("time", new StringBody(formattedDate));
				
				for(int i=0;i<map.size(); i++){
					File file1 = new File(map.get(i));
					FileBody bin1 = new FileBody(file1);
					reqEntity.addPart("file"+i, bin1);	
					System.out.println("fiel"+i);
				}
				//String reqString=reqEntity.getContentEncoding().toString();
				//System.out.println(reqString);
			//	reqEntity.addPart("multipleimages", new ByteArrayBody(data,params[0]));
				   // entity.addPart("multipleimages", new ByteArrayBody(data,params[0]));

				//reqEntity.addPart("uploadedfile1", bin1);

				/*
				 * reqEntity.addPart("uploadedfile4", bin4);
				 * reqEntity.addPart("uploadedfile4", bin4);
				 */
				reqEntity.addPart("user", new StringBody("User"));
				String url=reqEntity.toString();
				System.out.println(url+"    "+urlString);
				post.setEntity(reqEntity);
				HttpResponse response = client.execute(post);
				resEntity = response.getEntity();
				response_str = EntityUtils.toString(resEntity);
				if (resEntity != null) {
					Log.i("RESPONSE", response_str);
					dialogPd.dismiss();
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

	public void dodldodld(){
		

		dialogPd = new ProgressDialog(ShopReviewListing.this);
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

									Intent in = new Intent(ShopReviewListing.this,
											SellThanksScreen.class);
//									in.putExtra("emailKye", ownerEmail);
									startActivity(in);
									finish();

								} else if (Integer.parseInt(result) == 0) {
									dialogPd.dismiss();
									Toast.makeText(
											ShopReviewListing.this,
											"oops an error occoured",
											10000).show();
								} else if (Integer.parseInt(result) == 2) {
									dialogPd.dismiss();
//									dublicateAlert();
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
										"internal problem", 1000)
										.show();

							}

							
							
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						
						
						
						
						if (!flag)
							Toast.makeText(
									ShopReviewListing.this,
									"Please connect to the internet and try again.",
									1000).show();
						/*if (!flag1) {
							dialogPd.dismiss();
							owner_name.setText("");
							owner_name
									.setHint("invalid full name");
							owner_name.setHintTextColor(Color
									.parseColor("#FF0000"));
						}
						flag1 = true;*/
						if (dialogPd.isShowing())
							dialogPd.dismiss();
						flag = true;

					}
				});

			}
		});
		thread.start();
	}
	
	 class ImageUploadTask extends AsyncTask<String, Void, String> {

		  String sResponse = null;

		  @Override
		  protected void onPreExecute() {
		   // TODO Auto-generated method stub
		   super.onPreExecute();
		   dialog = ProgressDialog.show(ShopReviewListing.this, "Uploading",
		     "Please wait...", true);
		   dialog.show();
		  }

		  @Override
		  protected String doInBackground(String... params) {
		   try {
		    
		    String url = "http://pawn.amtechgcc.in/customerselling.php";
		    int i = Integer.parseInt(params[0]);
		    Bitmap bitmap = decodeFile(map.get(i));
		    HttpClient httpClient = new DefaultHttpClient();
		    HttpContext localContext = new BasicHttpContext();
		    HttpPost httpPost = new HttpPost(url);
		    entity = new MultipartEntity();

		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    bitmap.compress(CompressFormat.JPEG, 100, bos);
		    byte[] data = bos.toByteArray();

		    entity.addPart("email", new StringBody("sajid@gmail.com"));
		    //entity.addPart("category", new StringBody(catStr));
		   // entity.addPart("subcategory", new StringBody(subcatStr));
		    entity.addPart("pawn", new StringBody("pawn"));
		    entity.addPart("reciept-kg", new StringBody("199"));
		    entity.addPart("itemname", new StringBody(productTieltStr));
		    entity.addPart("itemprice", new StringBody(productPriceStr));
		    entity.addPart("description", new StringBody(prodesStr));
		  //  entity.addPart("zipcode", new StringBody(cusZipStr));
		    entity.addPart("address", new StringBody(StateName));
		    entity.addPart("time", new StringBody(formattedDate));
		//    entity.addPart("multipleimages", new ByteArrayBody(data,params[0]));

		    httpPost.setEntity(entity);
		    HttpResponse response = httpClient.execute(httpPost,
		      localContext);
		    sResponse = EntityUtils.getContentCharSet(response.getEntity());

		    System.out.println("sResponse : " + sResponse);
		   } catch (Exception e) {
		    if (dialog.isShowing())
		     dialog.dismiss();
		    Log.e(e.getClass().getName(), e.getMessage(), e);

		   }
		   return sResponse;
		  }

		  @Override
		  protected void onPostExecute(String sResponse) {
		   try {
		    if (dialog.isShowing())
		     dialog.dismiss();

		    if (sResponse != null) {
		     Toast.makeText(getApplicationContext(),
		       sResponse + " Photo uploaded successfully",
		       Toast.LENGTH_SHORT).show();
		     count++;
		     if (count < map.size()) {
		      new ImageUploadTask().execute(count + "", "hm" + count
		        + ".jpg");
		     }
		    }

		   } catch (Exception e) {
		    Toast.makeText(getApplicationContext(), e.getMessage(),
		      Toast.LENGTH_LONG).show();
		    Log.e(e.getClass().getName(), e.getMessage(), e);
		   }

		  }
		 }

		 public Bitmap decodeFile(String filePath) {
		  // Decode image size
		  BitmapFactory.Options o = new BitmapFactory.Options();
		  o.inJustDecodeBounds = true;
		  BitmapFactory.decodeFile(filePath, o);
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
		  BitmapFactory.Options o2 = new BitmapFactory.Options();
		  o2.inSampleSize = scale;
		  Bitmap bitmap = BitmapFactory.decodeFile(filePath, o2);
		  return bitmap;
		 }

		  
	    
	public void turnGPSOn(){
        try
        {
       
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

       
        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
        }
        catch (Exception e) {
           
        }
    }
	
	public class MyLocationListener implements LocationListener {
        public void onLocationChanged(Location location) {
            if (location != null) {
            }
        }

        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
        }
    }
	public static Location getLastKnownLocation(Context context)
    {
        Location location = null;
        LocationManager locationmanager = (LocationManager)context.getSystemService("location");
        List list = locationmanager.getAllProviders();
        boolean i = false;
        Iterator iterator = list.iterator();
        do
        {
            //System.out.println("---------------------------------------------------------------------");
            if(!iterator.hasNext())
                break;
            String s = (String)iterator.next();
            //if(i != 0 && !locationmanager.isProviderEnabled(s))
            if(i != false && !locationmanager.isProviderEnabled(s))
                continue;
           // System.out.println("provider ===> "+s);
            Location location1 = locationmanager.getLastKnownLocation(s);
            if(location1 == null)
                continue;
            if(location != null)
            {
                //System.out.println("location ===> "+location);
                //System.out.println("location1 ===> "+location);
                float f = location.getAccuracy();
                float f1 = location1.getAccuracy();
                if(f >= f1)
                {
                    long l = location1.getTime();
                    long l1 = location.getTime();
                    if(l - l1 <= 600000L)
                        continue;
                }
            }
            location = location1;
           // System.out.println("location  out ===> "+location);
            //System.out.println("location1 out===> "+location);
            i = locationmanager.isProviderEnabled(s);
           // System.out.println("---------------------------------------------------------------------");
        } while(true);
        return location;
    }
   
	void getMyCurrentLocation() {
	       
	       
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locListener = new MyLocationListener();
       
       
         try{gps_enabled=locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);}catch(Exception ex){}
           try{network_enabled=locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);}catch(Exception ex){}

            //don't start listeners if no provider is enabled
            //if(!gps_enabled && !network_enabled)
                //return false;

            if(gps_enabled){
                locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
               
            }
           
           
            if(gps_enabled){
                location=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
               
               
            }
           
 
            if(network_enabled && location==null){
                locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
               
            }
       
       
            if(network_enabled && location==null)    {
                location=locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); 
           
            }
       
        if (location != null) {
           
            MyLat = location.getLatitude();
            MyLong = location.getLongitude();

       
        } else {
            Location loc= getLastKnownLocation(this);
            if (loc != null) {
               
                MyLat = loc.getLatitude();
                MyLong = loc.getLongitude();
               

            }
        }
        locManager.removeUpdates(locListener); // removes the periodic updates from location listener to //avoid battery drainage. If you want to get location at the periodic intervals call this method using //pending intent.
       
        try
        {
// Getting address from found locations.
        Geocoder geocoder;
       
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
         addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
         if (addresses!= null && addresses.size() > 0) {
             Address address = addresses.get(0);
             StringBuilder sb = new StringBuilder();
             for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                 sb.append(address.getAddressLine(i)).append("\n");
             }
//newFhase=addresses.get(0).getSubLocality();
        StateName= sb.toString();
        CityName = addresses.get(0).getLocality();
        CountryName = addresses.get(0).getCountryName();
        // you can get more details other than this . like country code, state code, etc.
       
        
        System.out.println(" StateName " + StateName);
        System.out.println(" CityName " + CityName);
        System.out.println(" CountryName " + CountryName);
        
         }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
       
       
    }
  
	
	class ImageAdapter extends PagerAdapter {
		

        @Override
        public int getCount() {
            return allPaths.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
             imageView = new ImageView(getApplicationContext());
             imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ScaleType.CENTER_CROP);
             
             /*          int width = 370;
             int height = 200;
             LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
             imageView.setLayoutParams(parms);*/
//             imageView.setBackgroundResource(R.id.i)
            imageView.setImageBitmap(BitmapFactory.decodeFile(map.get(position)));
            
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }
	// Method to turn off the GPS
    public void turnGPSOff(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }
   
    // turning off the GPS if its in on state. to avoid the battery drain.
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        turnGPSOff();
    }
   
 @Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	Intent in=new Intent(ShopReviewListing.this,ShopSellingDescription.class);
	startActivity(in);
	finish();
}
}