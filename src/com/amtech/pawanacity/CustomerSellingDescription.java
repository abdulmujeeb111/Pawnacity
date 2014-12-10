package com.amtech.pawanacity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomerSellingDescription extends ActionBarActivity implements OnClickListener{
	
	EditText description,zipcode;
	Button forward,backward;
	public static String descriptionStr,zipStr;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	getSupportActionBar().hide();
	setContentView(R.layout.customer_selling_description);
	description=(EditText)findViewById(R.id.sell_description);
	zipcode=(EditText)findViewById(R.id.sell_zipcode_txt);
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
	
	

	
	
}

@Override
public void onClick(View v) {
	descriptionStr=description.getText().toString();
	zipStr=zipcode.getText().toString();
	
	switch (v.getId()) {
	case R.id.description_forward:
		
		if(descriptionStr.equals("")){
			description.setHint("Please provide description.....");
			description.setHintTextColor(Color.parseColor("#FF0000"));
			
		}
		
		else if(zipStr.equals("")){
			zipcode.setHint("invalid zip code");
			zipcode.setHintTextColor(Color.parseColor("#FF0000"));
			
		}
		else{
			
			Intent in1=new Intent(CustomerSellingDescription.this,CustomerReviewListing.class);
			startActivity(in1);
			finish();
		}
		
		break;
		
	case R.id.description_back:
		
		Intent in=new Intent(CustomerSellingDescription.this,CustomerSelling.class);
		startActivity(in);
		finish();
		
		break;

	default:
		break;
	}
	
}
}
