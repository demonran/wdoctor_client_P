package com.test.wdoctor.network.messageProcess;

import android.content.Context;

import com.csu.message.BaseMessage;

/**
 * 
 * 
 */
public interface IMessageProcess {

	void processMessage(BaseMessage message, Context context);
}
