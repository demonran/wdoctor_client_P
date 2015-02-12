package com.test.wdoctor.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.test.wdoctor.Constants;
import com.test.wdoctor.linstener.PhoneStateChangeListener;
import com.test.wdoctor.network.socket.ClientChatSocket;
import com.test.wdoctor.network.socket.ConnectSession;
import com.test.wdoctor.utils.ExecutorUtil;
import com.test.wdoctor.utils.LogUtil;

/**
 * Service that continues to run in background and respond to the push 
 * notification events from the server. This should be registered as service
 * in AndroidManifest.xml. 
 * 
 * @author 
 */
public class BackService extends Service {

    private static final String LOGTAG = LogUtil.makeLogTag(BackService.class);

    public static final String SERVICE_NAME = "ConnectService";

    private TelephonyManager telephonyManager;

    private BroadcastReceiver notificationReceiver;

    private BroadcastReceiver connectivityReceiver;

    private PhoneStateListener phoneStateListener;
    
    private ClientChatSocket chatSocket=ClientChatSocket.getInstance();
    
    public MyBinder myBinder = new MyBinder();
		
    @Override
    public void onCreate() {
        Log.d(LOGTAG, "onCreate()...");
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        
        notificationReceiver = new NotificationReceiver(this);
        connectivityReceiver = new ConnectivityReceiver(this);
        phoneStateListener = new PhoneStateChangeListener(this);

        start();

    }
    
    public void connect()
    {
    	ExecutorUtil.submit(new Runnable() {
			
			@Override
			public void run() {
		    	chatSocket.startSocket(BackService.this);
		    	
			}
		});
    	
    }
    
    public void disconnect()
    {
    	chatSocket.closedSocket();
    	ConnectSession.getInstance().close();
    }
 
    @Override
	public int onStartCommand(Intent intent, int flags, int startId) {
    	Log.d(LOGTAG, "onStartCommand()...");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
    public void onStart(Intent intent, int startId) {
        Log.d(LOGTAG, "onStart()...");
       
    }

    @Override
    public void onDestroy() {
        Log.d(LOGTAG, "onDestroy()...");
        stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOGTAG, "onBind()...");
        return myBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(LOGTAG, "onRebind()...");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(LOGTAG, "onUnbind()...");
        return true;
    }

    public static Intent getIntent() {
        return new Intent(SERVICE_NAME);
    }


    private void registerNotificationReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_MESSAGE_RECIEVED);
        filter.addAction(Constants.ACTION_NOTIFICATION_CLICKED);
        filter.addAction(Constants.ACTION_NOTIFICATION_CLEARED);
        registerReceiver(notificationReceiver, filter);
    }

    private void unregisterNotificationReceiver() {
        unregisterReceiver(notificationReceiver);
    }

    private void registerConnectivityReceiver() {
        Log.d(LOGTAG, "registerConnectivityReceiver()...");
        telephonyManager.listen(phoneStateListener,PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
        IntentFilter filter = new IntentFilter();
         filter.addAction(android.net.wifi.WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, filter);
    }

    private void unregisterConnectivityReceiver() {
        Log.d(LOGTAG, "unregisterConnectivityReceiver()...");
        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_NONE);
        unregisterReceiver(connectivityReceiver);
    }

    private void start() {
        Log.d(LOGTAG, "start()...");
        registerConnectivityReceiver();
        registerNotificationReceiver();
    }

    private void stop() {
        Log.d(LOGTAG, "stop()...");
        unregisterConnectivityReceiver();
        unregisterNotificationReceiver();
    }


    public class MyBinder extends Binder{
   	 public BackService getService() {  
            return BackService.this;  
        }  
		
	};
}
