package com.test.wdoctor.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.test.wdoctor.R;

public class PengyouquanActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tu_wen);
	}
	
	public void tuwen_back(View view)
	{
		this.finish();
	}
}
