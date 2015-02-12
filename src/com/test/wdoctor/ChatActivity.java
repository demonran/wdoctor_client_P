package com.test.wdoctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;
import com.bairuitech.anychat.AnyChatVideoCallEvent;
import com.csu.message.TalkMessage;
import com.test.wdoctor.adapter.ChatMsgViewAdapter;
import com.test.wdoctor.db.dao.ChatMsgDAO;
import com.test.wdoctor.model.Cache;
import com.test.wdoctor.model.ChatMsg;
import com.test.wdoctor.model.MsgUser;
import com.test.wdoctor.network.socket.ConnectSession;
import com.test.wdoctor.utils.ConfigEntity;
import com.test.wdoctor.utils.DialogFactory;
import com.test.wdoctor.utils.LogUtil;
import com.test.wdoctor.utils.Ulities;
import com.test.wdoctor.videochat.BussinessCenter;


/**
 * 
 */
public class ChatActivity extends Activity implements OnClickListener,AnyChatVideoCallEvent
,AnyChatBaseEvent{
    /** Called when the activity is first created. */
	private static final String TAG = LogUtil.makeLogTag(ChatActivity.class);
	
	private Button mBtnSend;
	private Button mBtnBack;
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private GridView extensionGrid;
	
	private List<ChatMsg> mDataArrays = new ArrayList<ChatMsg>();
	
	private MsgUser friend;
	private MsgUser owerUser;
	
	private ConfigEntity configEntity;
	private AnyChatCoreSDK anychat;
	private boolean bNeedRelease = false;
	private Dialog dialog;
	
	private BroadcastReceiver messageReceiver;
	
	private ChatMsgDAO chatMsgDAO;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        String friendQQ = intent.getStringExtra(Constants.MESSAGE_FRIENDQQ);
        
        chatMsgDAO = ChatMsgDAO.createChatMsgDAO(this);
        owerUser = Cache.getInstance().getOwerUser();
        if(friendQQ !=null && !friendQQ.isEmpty())
        {
        	friend = Cache.getInstance().getUserByID(friendQQ);
        	
        }
        initSdk();
        initView();
        
        messageReceiver = new  BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if(Constants.ACTION_MESSAGE_RECIEVED.equals(action))
				{
					NotificationManager nm = (NotificationManager) context
			                .getSystemService(Context.NOTIFICATION_SERVICE);
					String friendQQ = intent.getStringExtra(Constants.MESSAGE_FRIENDQQ);
					
					String text = intent.getStringExtra(Constants.MESSAGE_TEXT);
					if(friendQQ.equals(friend.getUserID()))
					{
						nm.cancel(Integer.parseInt(friendQQ));
						showMsg(friend.getUserName(),Ulities.getDate(),text,true);
					}
					
				}
				
			}
		};
		registerNotificationReceiver();
    }
    
	private void initSdk() {
		if (anychat == null) {
			anychat = new AnyChatCoreSDK();
		}
		anychat.SetBaseEvent(this);
		anychat.SetVideoCallEvent(this);
		Log.i("ANYCHAT", "initSdk");
	}
    
    
    
    
    @Override
	protected void onStart() {
		super.onStart();
		chatRecords();
	}
    
  //从数据库中读取信息，并在listView中显示，作为本地的聊天记录
  	private void chatRecords(){
		mDataArrays = chatMsgDAO.getAllChatMsg(friend.getUserID());
		mAdapter = new ChatMsgViewAdapter(this, mDataArrays,friend.getUserID(),owerUser.getUserID());
		mListView.setAdapter(mAdapter);
  	}




	public void initView()
    {
		setContentView(R.layout.chat_xiaohei);
	        //启动activity时不自动弹出软键盘
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
    	mListView = (ListView) findViewById(R.id.listview);
    	mBtnSend = (Button) findViewById(R.id.btn_send);
    	mBtnSend.setOnClickListener(this);
    	mBtnBack = (Button) findViewById(R.id.btn_back);
    	mBtnBack.setOnClickListener(this);
    	TextView friendName = (TextView)findViewById(R.id.friendName);
    	friendName.setText(friend.getUserName());
    	
    	mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
    	mEditTextContent.setOnClickListener(this);
    	
    	extensionGrid = (GridView) findViewById(R.id.extensionGrid);
    	
    	//生成动态数组，并且转入数据  
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();  
        HashMap<String, Object> map = new HashMap<String, Object>();  
        map.put("extension_icon", R.drawable.icon);//添加图像资源的ID  
        map.put("extension_name", "视频");//按序号做ItemText  
        lstImageItem.add(map);  
    	 SimpleAdapter saImageItems = new SimpleAdapter(this, //没什么解释  
                  lstImageItem,//数据来源   
                  R.layout.extension_item,//night_item的XML实现  
                    
                  //动态数组与ImageItem对应的子项          
                  new String[] {"extension_icon","extension_name"},   
                    
                  //ImageItem的XML文件里面的一个ImageView,两个TextView ID  
                  new int[] {R.id.extension_icon,R.id.extension_name});  
		//添加并且显示  
    	 extensionGrid.setAdapter(saImageItems);  
		//添加消息处理  
    	 extensionGrid.setOnItemClickListener(new ItemClickListener());  
    	 
    	mEditTextContent.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length()>0)
				{
					mBtnSend.setText("发送");
				}else
				{
					mBtnSend.setText("+");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
    }
    
    
    private void registerNotificationReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_MESSAGE_RECIEVED);
        registerReceiver(messageReceiver, filter);
    }	
 
 private void unregisterNotificationReceiver() {
        unregisterReceiver(messageReceiver);
    }



	@Override
	public void onClick(View v) {
		Log.i(TAG,v.getId()+"-");
		Log.i(TAG,"btn_send:"+R.id.btn_send);
		switch(v.getId())
		{
		case R.id.btn_send:
			Log.i(TAG,((Button)v).getText()+"-");
			if(((Button)v).getText().equals("+"))
			{
				if(extensionGrid.getVisibility() == View.GONE){
				    //隐藏软键盘
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);  
					if(imm.isActive()){
						imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘 
					}
					extensionGrid.setVisibility(View.VISIBLE);
				}else
				{
					extensionGrid.setVisibility(View.GONE);
				}
			}else if(((Button)v).getText().equals("发送"))
				{
					send();
				}
			break;
		case R.id.btn_back:
			finish();
			break;
		case R.id.et_sendmessage:
			extensionGrid.setVisibility(View.GONE);
			break;
		}
	}
	
	private void call()
	{
		Log.i(TAG," 发送视频请求");
		dialog = DialogFactory.getDialog(DialogFactory.DIALOGID_CALLRESUME,
				friend.getUserName(), this);
		dialog.show();
	}
	
	
	private void send()
	{
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0)
		{
			TalkMessage msg=new TalkMessage();
			msg.setDestQQ(friend.getUserID());
        	msg.setSrcQQ(owerUser.getUserID());
        	msg.setTalkMsg(contString);
        	ConnectSession connectSession=ConnectSession.getInstance();
        	connectSession.sendTextMessage(msg);
        	
        	String date = Ulities.getDate();
        	String name =  owerUser.getUserName();
        	chatMsgDAO.insertData(new ChatMsg(owerUser.getUserID(),name,date, contString, false));
        	showMsg(name,date,contString,false);
        	
		}
		
	}
	
	  public void showMsg(String username,String date,String msg,boolean isComMeg){
		  ChatMsg entity = new ChatMsg();
			entity.setDate(date);
			entity.setName(username);
			entity.setComMeg(isComMeg);
			entity.setText(msg);
			
			mAdapter.addItem(entity);
			mAdapter.notifyDataSetChanged();
			
			mEditTextContent.setText("");
			
			mListView.setSelection(mListView.getCount() - 1);
	    }
	
    
    public void head_xiaohei(View v) {     //标题栏 返回按钮
    	Intent intent = new Intent (ChatActivity.this,InfoXiaohei.class);			
		startActivity(intent);	
     }
    
    


	@Override
	protected void onResume() {
		Log.i(TAG, "onResume");
		super.onResume();
		// TODO Auto-generated method stub
		BussinessCenter.mContext = this;
		initSdk();
		unregisterNotificationReceiver();
	}

	@Override
	protected void onPause() {
		Log.i(TAG, "onPause");
		super.onPause();
		registerNotificationReceiver();
	}


	@Override
	protected void onDestroy() {
		Log.i(TAG, "chatActivity destroy..");
		unregisterNotificationReceiver();
		super.onDestroy();
	}




	@Override
	public void OnAnyChatVideoCallEvent(int dwEventType, int dwUserId,
			int dwErrorCode, int dwFlags, int dwParam, String userStr) {
		Log.i(TAG, "dwEventType : "+ dwEventType);
		switch (dwEventType) {

		case AnyChatDefine.BRAC_VIDEOCALL_EVENT_REQUEST:
			BussinessCenter.getBussinessCenter().onVideoCallRequest(
					dwUserId, dwFlags, dwParam, userStr);
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
			dialog = DialogFactory.getDialog(DialogFactory.DIALOGID_REQUEST,
					dwUserId, this);
			dialog.show();
			break;
		case AnyChatDefine.BRAC_VIDEOCALL_EVENT_REPLY:
			BussinessCenter.getBussinessCenter().onVideoCallReply(
					dwUserId, dwErrorCode, dwFlags, dwParam, userStr);
			if (dwErrorCode == AnyChatDefine.BRAC_ERRORCODE_SUCCESS) {
				dialog = DialogFactory.getDialog(
						DialogFactory.DIALOGID_CALLING, dwUserId,
						this);
				dialog.show();

			} else {
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
			}
			break;
		case AnyChatDefine.BRAC_VIDEOCALL_EVENT_START:
			if (dialog != null && dialog.isShowing())
				dialog.dismiss();
			BussinessCenter.getBussinessCenter().onVideoCallStart(
					dwUserId, dwFlags, dwParam, userStr);
			break;
		case AnyChatDefine.BRAC_VIDEOCALL_EVENT_FINISH:
			BussinessCenter.getBussinessCenter().onVideoCallEnd(dwUserId,
					dwFlags, dwParam, userStr);
			break;
		}
		
	}




	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		// TODO Auto-generated method stub
		
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

	 private class  ItemClickListener implements OnItemClickListener  
	  {  
		 public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened   
                  View arg1,//The view within the AdapterView that was clicked  
                  int arg2,//The position of the view in the adapter  
                  long arg3//The row id of the item that was clicked  
                  ) {  
		    //在本例中arg2=arg3  
		    HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2);  
		    //显示所选Item的ItemText  
		    if("视频".equals(item.get("extension_name"))){
		    	call();
		    }
		 }
	  }
}
