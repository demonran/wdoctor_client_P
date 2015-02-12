package com.test.wdoctor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.csu.message.LoginMessage;
import com.test.wdoctor.network.socket.ClientChatSocket;
import com.test.wdoctor.network.socket.ConnectSession;
import com.test.wdoctor.service.BackService;
import com.test.wdoctor.service.BackService.MyBinder;
import com.test.wdoctor.utils.DialogFactory;
import com.test.wdoctor.utils.LogUtil;

public class Login extends Activity {
	private EditText mUser; // 帐号编辑框
	private EditText mPassword; // 密码编辑框
	
    private String qqAcount;
    private String pwd;
    private String state;
    private LoginMessage  loginMessage;
    
    private boolean isBound;
    
    private static final String TAG = LogUtil.makeLogTag(Login.class);
    
    private ServiceConnection serviceConnection = new ServiceConnection() {
		
    	@Override
		public void onServiceDisconnected(ComponentName name) {
    		Log.i(TAG,"取消绑定");
			isBound = false; 
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder server) {
			Log.i(TAG,"绑定成功");
			isBound = true;
			BackService service = ((MyBinder)server).getService();
			service.connect();
		}
	};
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        mUser = (EditText)findViewById(R.id.login_user_edit);
        mPassword = (EditText)findViewById(R.id.login_passwd_edit);
        
        Intent intent = getIntent();
        String error = intent.getStringExtra("error");
        if(error !=null && !error.isEmpty())
        {
        	 showDialog(R.drawable.login_error_icon,"登录失败",error+"\n请检查后重新输入！");
        }
       
    }

	private void showDialog(int icon,String title,String message) {
		new AlertDialog.Builder(Login.this)
		.setIcon(getResources().getDrawable(icon))
		.setTitle(title)
		.setMessage(message)
		.create().show();
	}
    
    
    /**
     * 
     *
     */
    private boolean initQQAcount(){
	    	if(null==this.qqAcount){
	    		qqAcount=mUser.getText().toString();
	    	}
	    	if(qqAcount.equals("")||null==qqAcount){
	        	showDialog(R.drawable.login_error_icon,"登录失败","请输入ID!!..");
	        	return false;
	    	}
        	
        	pwd=mPassword.getText().toString();
        	
        	if(pwd.equals("")||pwd==null){
        		showDialog(R.drawable.login_error_icon,"登录失败","请输入密码!!..");
        		return false;
        	}
    		loginMessage=new LoginMessage();
			loginMessage.setSrcQQ(qqAcount);
			loginMessage.setPwd(pwd);
			loginMessage.setOwerQQ(qqAcount);
			loginMessage.setState( "上线");
			CommonContainer.loginMessage = loginMessage;
		    return true;
    }
    
    /**
     * 
     * 发送登入消息方法
     * @param handler 
     *
     */
    public void startLoginServer(){
    	Intent service = new Intent(this,BackService.class);
    	this.startService(service);
    	this.bindService(service, serviceConnection, Context.BIND_AUTO_CREATE);
    }


    public void login_mainweixin(View v) {
    	if(initQQAcount()){
    		 Intent intent = new Intent();
             intent.setClass(Login.this,LoadingActivity.class);
             intent.putExtra("username", mUser.getText().toString());
             intent.putExtra("password", mPassword.getText().toString());
             startActivity(intent);
             this.finish();
           
            startLoginServer();
            	    	
    	}
   			
      }  
    public void login_back(View v) {     //标题栏 返回按钮
      	this.finish();
      }  
    public void login_pw(View v) {     //忘记密码按钮
    	Uri uri = Uri.parse("http://3g.qq.com"); 
    	Intent intent = new Intent(Intent.ACTION_VIEW, uri); 
    	startActivity(intent);
      }

	@Override
	protected void onDestroy() {
		if(isBound)
		{
			this.unbindService(serviceConnection);
		}
		super.onDestroy();
	}  
    
    
}
