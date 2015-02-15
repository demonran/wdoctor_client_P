package com.test.wdoctor;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Welcome extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }
    public void welcome_login(View v) {  
      	Intent intent = new Intent();
		intent.setClass(Welcome.this,Whatsnew.class);
		startActivity(intent);
      }  
    public void welcome_register(View v) {  
      	Intent intent = new Intent();
		intent.setClass(Welcome.this,MainWeixin.class);
		startActivity(intent);
		//this.finish();
      }
    
    public void welcome_switchUser(View v)
    {
    	Log.i("TAG","welcome_switchUser");
    	Intent intent = new Intent();
		intent.setClass(Welcome.this,Login.class);
		startActivity(intent);
    }
   
}
