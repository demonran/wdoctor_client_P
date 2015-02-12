package com.test.wdoctor;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.csu.message.MessageType;
import com.test.wdoctor.network.socket.ClientChatSocket;
import com.test.wdoctor.network.socket.ConnectSession;
import com.test.wdoctor.utils.ConfigEntity;
import com.test.wdoctor.utils.ConfigService;
import com.test.wdoctor.utils.LogUtil;

public class LoadingActivity extends Activity implements AnyChatBaseEvent{
	
	private static final String TAG = LogUtil.makeLogTag(LoadingActivity.class);
	
	private Context ctx;
	
	private BroadcastReceiver loginReceiver;
	
	private TextView infoText ;
	
	private ConfigEntity configEntity;
	private AnyChatCoreSDK anychat;
	private boolean bNeedRelease = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.loading);
		initSdk();
		intParams();
		initView();
		Login();
		
			
		 registerNotificationReceiver();
   }
	
	private void initView()
	{
		infoText = (TextView)this.findViewById(R.id.infoText);
		 ctx = this;
	}
	
	protected void intParams() {
		configEntity = ConfigService.LoadConfig(this);
	}
	
	private void initSdk() {
		if (anychat == null) {
			anychat = new AnyChatCoreSDK();
			anychat.SetBaseEvent(this);
			anychat.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
			bNeedRelease = true;
		}
	}
	
	private void Login() {
		ConfigService.SaveConfig(this, configEntity);
		this.anychat.Connect("192.168.1.114", configEntity.port);
		this.anychat.Login("11", "123");
	}
	
	 private void registerNotificationReceiver() {
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
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			  ConnectSession.getInstance().close();
		        ClientChatSocket.getInstance().closedSocket();
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		Log.i(TAG,"连接成功");
		
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		if (dwErrorCode == 0) {
			Log.i(TAG,"登陆成功");
//			BussinessCenter.selfUserId = dwUserId;
//			BussinessCenter.selfUserName=mEditAccount.getText()
//					.toString();
//			Intent intent=new Intent();
//			intent.setClass(this, HallActivity.class);
//			this.startActivity(intent);
		} else{
			Log.i(TAG,"登陆失败");
//			BaseMethod.showToast(
//					getString(R.string.str_lggin_failed), this);
		}
		
	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		// TODO Auto-generated method stub
		
	}

}