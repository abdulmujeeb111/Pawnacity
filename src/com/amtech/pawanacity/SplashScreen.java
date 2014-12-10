package com.amtech.pawanacity;




import java.io.IOException;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class SplashScreen extends Activity{
	ImageView image;
	Animation animBounce;
	// flag for Internet connection status
    Boolean isInternetPresent = false;

	Context context;
	String regId;
    // Connection detector class
    ConnectionDetector cd;
	
	    protected boolean _active = true;
	    protected int _splashTime =5000;
	    
	    /** Called when the activity is first created. */
	    @SuppressLint("NewApi")
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    	getActionBar().hide();
	setContentView(R.layout.activity_splash_screen);
	
checkInternetConnection();
	       
	    }
	    
	    @Override
	    public boolean onTouchEvent(MotionEvent event) {
	        if (event.getAction() == MotionEvent.ACTION_DOWN) {
	            _active = false;
	        }
	        return true;
	    }
	    
	    public void checkInternetConnection(){
	    	cd = new ConnectionDetector(getApplicationContext());
	    	 isInternetPresent = cd.isConnectingToInternet();
	    	 
             // check for Internet status
             if (isInternetPresent) {
                
            	 Thread splashTread = new Thread() {
     	            @Override
     	            public void run() {
     	                try {
     	                    int waited = 0;
     	                    while(_active && (waited < _splashTime)) {
     	                        sleep(100);
     	                        if(_active) {
     	                            waited += 100;
     	                        }
     	                    }
     	                } catch(InterruptedException e) {
     	                    // do nothing
     	                } finally {
     	                    finish();
     	                    startActivity(new Intent(SplashScreen.this,PawnaMain.class));
     	                    
     	                }
     	            }
     	        };
     	        splashTread.start();
             } else {
                 // Internet connection is not present
                 // Ask user to connect to Internet
                 showAlertDialog(SplashScreen.this, "No Internet Connection",
                         "You don't have internet connection.", false);
             }}
             /**
              * Function to display simple Alert Dialog
              * @param context - application context
              * @param title - alert dialog title
              * @param message - alert message
              * @param status - success/failure (used to set icon)
              * */
             public void showAlertDialog(Context context, String title, String message, Boolean status) {
                 AlertDialog alertDialog = new AlertDialog.Builder(context).create();
          
                 // Setting Dialog Title
                 alertDialog.setTitle(title);
          
                 // Setting Dialog Message
                 alertDialog.setMessage(message);
                  
                 // Setting alert dialog icon
//                 alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
          
                 // Setting OK Button
                 alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {
                    	 System.exit(0);
                     }
                 });
          
                 // Showing Alert Message
                 alertDialog.show();
	    	
	    }
}
