package com.test.wdoctor;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.csu.message.MessageType;
import com.test.wdoctor.utils.LogUtil;

public class LoadingActivity extends Activity{
	
	private static final String TAG = LogUtil.makeLogTag(LoadingActivity.class);
	
	private Context ctx;
	
	private BroadcastReceiver loginReceiver;
	
	private TextView infoText ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.loading);
		
		infoText = (TextView)this.findViewById(R.id.infoText);
		 ctx = this;
		
		 loginReceiver = new  BroadcastReceiver() {
				
				@Override
				public void onReceive(Context context, Intent intent) {
					String action = intent.getAction();
					if(Constants.ACTION_LOGIN.equals(action))
					{
						int  responseInfo = intent.getIntExtra("responseInfo",999);
						Log.i(TAG,responseInfo+"--");
						if(responseInfo == MessageType.LoginResponse_Succee)
						{
							Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
							infoText.setText("登陆成功，正在获取用户列表!!");
							
						}else if(responseInfo == MessageType.LoginResponse_Faile){
							String error = intent.getStringExtra("error");
							Log.i(TAG,error+"--");
							if(error!= null && !error.isEmpty())
							{
								Toast.makeText(ctx, "connect to server fail", Toast.LENGTH_SHORT).show();
								intent = new Intent (LoadingActivity.this,Login.class);	
								intent.putExtra("error", error);
								startActivity(intent);			
								LoadingActivity.this.finish();
							}
						}
					}else if(Constants.ACTION_SHOW_FRIEND.equals(action))
					{
						Toast.makeText(getApplicationContext(), "获取用户列表成功", Toast.LENGTH_SHORT).show();
						intent = new Intent (LoadingActivity.this,MainWeixin.class);	
						startActivity(intent);			
						LoadingActivity.this.finish();
					}
				}
			};
			
		 registerNotificationReceiver();
   }
	
	 private void registerNotificationReceiver() {
	        IntentFilter filter = new IntentFilter();
	        filter.addAction(Constants.ACTION_LOGIN);
	        filter.addAction(Constants.ACTION_SHOW_FRIEND);
	        registerReceiver(loginReceiver, filter);
	    }	
	 
	 private void unregisterNotificationReceiver() {
	        unregisterReceiver(loginReceiver);
	    }
	

	@Override
	protected void onDestroy() {
		stop();
		super.onDestroy();
		
	}
	
    private void stop() {
        Log.d(TAG, "stop()...");
        unregisterNotificationReceiver();
    }

}