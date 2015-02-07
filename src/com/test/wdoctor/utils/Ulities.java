package com.test.wdoctor.utils;

import java.text.DateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.test.wdoctor.model.ChatMsg;

public class Ulities {
	
	private static DateFormat df =DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
	
	public static String chatMsgTOJson(ChatMsg chatMsg)
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("name", chatMsg.getName());
			jsonObject.put("date", chatMsg.getDate());
			jsonObject.put("text", chatMsg.getText());
			jsonObject.put("isComMeg", chatMsg.isComMeg());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObject.toString();
	}
	
	public static  ChatMsg jsonToChatMsg(String json)
	{
		try {
			JSONObject jsonObject = new JSONObject(json);
			ChatMsg chatMsg = new ChatMsg();
			chatMsg.setName(jsonObject.optString("name"));
			chatMsg.setDate(jsonObject.optString("date"));
			chatMsg.setText(jsonObject.optString("text"));
			chatMsg.setComMeg(jsonObject.optBoolean("isComMeg"));
			return chatMsg;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static  String getDate() {
    	return df.format(new Date());					
    }
}
