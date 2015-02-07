package com.test.wdoctor;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.csu.message.TalkMessage;
import com.test.wdoctor.adapter.ChatMsgViewAdapter;
import com.test.wdoctor.db.dao.ChatMsgDAO;
import com.test.wdoctor.model.Cache;
import com.test.wdoctor.model.ChatMsg;
import com.test.wdoctor.model.MsgUser;
import com.test.wdoctor.network.socket.ConnectSession;
import com.test.wdoctor.utils.LogUtil;
import com.test.wdoctor.utils.Ulities;


/**
 * 
 */
public class ChatActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	private static final String TAG = LogUtil.makeLogTag(ChatActivity.class);
	
	private Button mBtnSend;
	private Button mBtnBack;
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsg> mDataArrays = new ArrayList<ChatMsg>();
	
	private MsgUser friend;
	private MsgUser owerUser;
	
	private BroadcastReceiver messageReceiver;
	
	private ChatMsgDAO chatMsgDAO;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_xiaohei);
        //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        Intent intent = getIntent();
        String friendQQ = intent.getStringExtra(Constants.MESSAGE_FRIENDQQ);
        
        chatMsgDAO = ChatMsgDAO.createChatMsgDAO(this);
        owerUser = Cache.getInstance().getOwerUser();
        if(friendQQ !=null && !friendQQ.isEmpty())
        {
        	friend = Cache.getInstance().getUserByID(friendQQ);
        	
        }
        
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
    
    
    
    
    @Override
	protected void onStart() {
		super.onStart();
		chatRecords();
	}
    
  //从数据库中读取信息，并在listView中显示，作为本地的聊天记录
  	private void chatRecords(){
		mDataArrays = chatMsgDAO.getAllChatMsg(friend.getUserID());
		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
  	}




	public void initView()
    {
    	mListView = (ListView) findViewById(R.id.listview);
    	mBtnSend = (Button) findViewById(R.id.btn_send);
    	mBtnSend.setOnClickListener(this);
    	mBtnBack = (Button) findViewById(R.id.btn_back);
    	mBtnBack.setOnClickListener(this);
    	TextView friendName = (TextView)findViewById(R.id.friendName);
    	friendName.setText(friend.getUserName());
    	
    	mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
    	
    	mEditTextContent.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length()>0)
				{
					mBtnSend.setEnabled(true);
				}else
				{
					mBtnSend.setEnabled(false);
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
		switch(v.getId())
		{
		case R.id.btn_send:
			send();
			break;
		case R.id.btn_back:
			finish();
			break;
		}
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
	protected void onDestroy() {
		Log.i(TAG, "chatActivity destroy..");
		unregisterNotificationReceiver();
			
		super.onDestroy();
	} 
	
    
}
