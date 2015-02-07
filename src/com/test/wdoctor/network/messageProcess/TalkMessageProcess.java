package com.test.wdoctor.network.messageProcess;


import android.content.Context;
import android.content.Intent;

import com.csu.message.BaseMessage;
import com.csu.message.TalkMessage;
import com.test.wdoctor.Constants;

public class TalkMessageProcess implements IMessageProcess{

	public void processMessage(BaseMessage message,Context context) {
		final TalkMessage talkMessage=(TalkMessage)message;
		
		  Intent intent = new Intent(Constants.ACTION_MESSAGE_RECIEVED);
          intent.putExtra(Constants.MESSAGE_FRIENDQQ,message.getSrcQQ());
          intent.putExtra(Constants.MESSAGE_TEXT,talkMessage.getTalkMsg());
//          intent
//                  .putExtra(Constants.NOTIFICATION_TITLE,
//                          notificationTitle);
//          intent.putExtra(Constants.NOTIFICATION_MESSAGE,
//         		 messageStr);
//          intent.putExtra(Constants.NOTIFICATION_URI, notificationUri);
//          //                intent.setData(Uri.parse((new StringBuilder(
//          //                        "notif://notification.androidpn.org/")).append(
//          //                        notificationApiKey).append("/").append(
//          //                        System.currentTimeMillis()).toString()));

          context.sendBroadcast(intent);
          
//		java.awt.EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				Cache cache=Cache.getInstance();
//				EIMTalkFrame qqTalkFrame=cache.getTalkFrame(friendQQ);
//				EIMTrayIcon trayIcon=EIMTrayIcon.getInStance();
//				qqTalkFrame.showMsg(qqTalkFrame.friend.getUserName(), talkMessage.getTalkMsg());
//				if(!qqTalkFrame.isVisible()){
//					trayIcon.addRevedMessage(talkMessage);
//					trayIcon.showIcon(talkMessage.getMsgType());
//					trayIcon.setGetMsg(true);
//				}
//			}
//		});
	
	}
}
