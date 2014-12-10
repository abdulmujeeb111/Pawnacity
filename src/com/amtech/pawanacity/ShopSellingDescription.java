package com.amtech.pawanacity;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.amtech.pawanacity.CustomerReviewListing.MyLocationListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShopSellingDescription extends ActionBarActivity implements OnClickListener{
	TextView address,updatelink;
	EditText description;
	Button forward,backward;
	 Double MyLat, MyLong;
    String CityName="";
    String StateName="";
    String CountryName="";
    String newFhase="";
	GPSTracker gps;
	private boolean gps_enabled=false;
    private boolean network_enabled=false;
    Location location;
	public static String descriptionStr,zipStr;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	getSupportActionBar().hide();
	setContentView(R.layout.shop_selling_description);
	description=(EditText)findViewById(R.id.shop_sell_description);
	address=(TextView)findViewById(R.id.shop_address);
	updatelink=(TextView)findViewById(R.id.update_link);
	updatelink.setPaintFlags(updatelink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
	updatelink.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
Toast.makeText(getApplicationContext(), "please provide functionality...", 1000).show();

			
		}
	});
	
	forward=(Button)findViewById(R.id.description_forward);
	backward=(Button)findViewById(R.id.description_back);
	forward.setOnClickListener(this);
	backward.setOnClickListener(this);

	
	
	
	
	description.addTextChangedListener(new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        	//Toast.makeText(getApplicationContext(), "Numer of Characters you wrote:  "+s.length(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {

            // TODO Auto-generated method stub
        }
    });
	
	
	turnGPSOn(); // method to turn on the GPS if its in off state.
    getMyCurrentLocation();
	  if(!StateName.equals(null)){
	  address.setText(StateName);
	  }
	  else{
		  address.setText("No Address Found");
		  
	  }
	
	
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
//Getting address from found locations.
    Geocoder geocoder;
   
    List<Address> addresses;
    geocoder = new Geocoder(this, Locale.getDefault());
     addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
     if (addresses!= null && addresses.size() > 0) {
         Address address = addresses.get(0);
         StringBuilder sb = new StringBuilder();
         for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
             sb.append(address.getAddressLine(i)).append(",");
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

@Override
public void onClick(View v) {
	descriptionStr=description.getText().toString();
//	zipStr=zipcode.getText().toString();
	
	switch (v.getId()) {
	case R.id.description_forward:
		
		if(descriptionStr.equals("")){
			description.setHint("Please provide description.....");
			description.setHintTextColor(Color.parseColor("#FF0000"));
			
		}
		
		
		else{
			
			Intent in1=new Intent(ShopSellingDescription.this,ShopReviewListing.class);
			startActivity(in1);
			finish();
		}
		
		break;
		
	case R.id.description_back:
		
		Intent in=new Intent(ShopSellingDescription.this,ShopSellingActivity.class);
		startActivity(in);
		finish();
		
		break;

	default:
		break;
	}
	
}
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

private void onBAack() {
	Intent in=new Intent(ShopSellingDescription.this,ShopSellingActivity.class);
	startActivity(in);
	finish();

}

}
