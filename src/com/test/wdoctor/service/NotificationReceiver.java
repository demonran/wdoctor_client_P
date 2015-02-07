/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.test.wdoctor.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.test.wdoctor.Constants;
import com.test.wdoctor.db.dao.ChatMsgDAO;
import com.test.wdoctor.model.Cache;
import com.test.wdoctor.model.ChatMsg;
import com.test.wdoctor.model.MsgUser;
import com.test.wdoctor.utils.LogUtil;
import com.test.wdoctor.utils.Ulities;

public final class NotificationReceiver extends BroadcastReceiver {

    private static final String LOGTAG = LogUtil.makeLogTag(NotificationReceiver.class);

        private ChatMsgDAO chatMsgDAO;

    public NotificationReceiver(Context context) {
    	 chatMsgDAO = ChatMsgDAO.createChatMsgDAO(context);
    }

    //    public NotificationReceiver(NotificationService notificationService) {
    //        this.notificationService = notificationService;
    //    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOGTAG, "NotificationReceiver.onReceive()...");
        String action = intent.getAction();
        Log.d(LOGTAG, "action=" + action);
        
        if(Constants.ACTION_MESSAGE_RECIEVED.equals(action))
		{
			String friendQQ = intent.getStringExtra(Constants.MESSAGE_FRIENDQQ);
			String text = intent.getStringExtra(Constants.MESSAGE_TEXT);
			
		    Log.d(LOGTAG, "friendQQ=" + friendQQ);
            Log.d(LOGTAG, "text=" + text);
            MsgUser friend = Cache.getInstance().getUserByID(friendQQ);
            chatMsgDAO.insertData(new ChatMsg(friendQQ, friend.getUserName(), Ulities.getDate(), text, true));
            Notifier notifier = new Notifier(context);
            int notificationid = 0;
            try
            {
            	notificationid = Integer.parseInt(friendQQ);
            }catch(Exception e)
            {
            	 Log.w(LOGTAG, "friendQQ is not a num :" + friendQQ);
            }
            
            notifier.notify(notificationid, friendQQ,friend.getUserName(),text);
            
		}


    }

}
