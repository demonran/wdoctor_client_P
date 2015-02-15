package com.test.wdoctor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.test.wdoctor.R;

public class DiscoveryFunctionActivity extends Activity {
	
	private TextView titleText ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discovery_function);
		titleText = (TextView)findViewById(R.id.discovery_function_title);
		titleText.setText(getIntent().getStringExtra("title"));
	}
	
	
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		
	}



	public void fucntion_back(View view)
	{
		this.finish();
	}
}
