package com.test.wdoctor.db.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.util.Log;

import com.test.wdoctor.db.DBHelper;
import com.test.wdoctor.model.ChatMsg;

public class ChatMsgDAO {
	
	private static ChatMsgDAO instance;
	
	private DBHelper helper;
	
	private Set<String> friendQQs ;
	
	private SharedPreferences spfs ;
	
	private static final String RECORD_TAG = "friendQQs";
	
	 private ChatMsgDAO(Context cxt) {
		 helper = new DBHelper(cxt);
		 spfs = PreferenceManager.getDefaultSharedPreferences(cxt);
		
	}
	 
	 private String checkAndCreateTable(String friendQQ)
	 {
		 if(friendQQs == null)
		 {
			 friendQQs = spfs.getStringSet(RECORD_TAG, new HashSet<String>());
		 }
		 if(!friendQQs.contains(friendQQ))
		 {
			 craeteTable(friendQQ);
		 }
		 return "chatmsg_"+friendQQ;
		
	 }
	 
	 public static ChatMsgDAO createChatMsgDAO(Context cxt)
	 {
		 if(instance == null)
		 {
			 instance = new ChatMsgDAO(cxt);
		 }
		 return instance;
	 }
	 
	 public void craeteTable(String friendQQ)
	 {
		 SQLiteDatabase db = helper.getReadableDatabase();
		 String check = "SELECT COUNT(*) FROM sqlite_master where type='table' and name=?";
		 
         Cursor cursor = db.rawQuery(check, new String[]{"chatmsg_"+friendQQ});
         if(cursor.moveToNext()){
                 int count = cursor.getInt(0);
                 if(count>0){
                         return ;
                 }
         }
		 
		 db = helper.getWritableDatabase();
		  String sql = "create table chatmsg_"+friendQQ+"(" +
			      "_id integer primary key autoincrement," +
			      "name varchar(20)," +
			      "date varchar(20)," +
			      "msg text," +
			      "isComMeg integer)";

			  db.execSQL(sql);
			  
			  //保存到SharedPreference
			  Editor editor = spfs.edit();
			  friendQQs.add(friendQQ);
			  editor.putStringSet(RECORD_TAG, friendQQs);
			  editor.commit();
	 }
	 

	 // 插入操作
	 public void insertData(ChatMsg chatMsg) {
		 String tableName = checkAndCreateTable(chatMsg.getFriendQQ());
		 String sql = "insert into "+tableName+" (name,date,msg,isComMeg)values(?,?,?,?)";
		 SQLiteDatabase db = helper.getWritableDatabase();
		 db.execSQL(sql, new Object[] { chatMsg.getName(),chatMsg.getDate(),chatMsg.getText(),chatMsg.isComMeg()?0:1});
	 }
	 
	 public List<ChatMsg> getAllChatMsg(String friendsQQ)
	 {
		 String table = checkAndCreateTable(friendsQQ);
		 
		 List<ChatMsg> chatMsgs = new ArrayList<ChatMsg>();
  		//获取数据库中的信息
  		SQLiteDatabase db=helper.getReadableDatabase();
  		String sql="select name,date,msg,isComMeg from "+table ;
  		Cursor c = db.rawQuery(sql,new String[]{});
  		while(c.moveToNext()){
  			String name=c.getString(0);
  			String date=c.getString(1);
  			String context=c.getString(2);
  			int isComMeg=c.getInt(3);
  			Log.v("ceshi", name+date+context);
  			ChatMsg entity = new ChatMsg();
  			entity.setName(name);
  			
  			entity.setDate(date);
  			entity.setText(context);
  			entity.setComMeg(isComMeg==0);
  			
  			chatMsgs.add(entity);
  		}
  		return chatMsgs;
	 }
}
