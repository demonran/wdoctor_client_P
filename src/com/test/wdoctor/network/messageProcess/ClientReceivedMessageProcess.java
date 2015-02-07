package com.test.wdoctor.network.messageProcess;

import android.content.Context;

import com.csu.message.BaseMessage;

public class ClientReceivedMessageProcess {

	public void processMessage(BaseMessage message, Context context) {
		int messageType=message.getMsgType();
		MessageProcessFactory factory=MessageProcessFactory.getInstance();
		IMessageProcess process=factory.creatMessageProcess(messageType);
		process.processMessage(message,context);		
	}
}
