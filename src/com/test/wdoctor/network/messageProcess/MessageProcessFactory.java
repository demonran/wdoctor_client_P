package com.test.wdoctor.network.messageProcess;

import com.test.wdoctor.network.messageProcess.config.MessageProcessConfig;
import com.test.wdoctor.utils.ClientUtils;



public class MessageProcessFactory {

	private static MessageProcessFactory instance;
	private MessageProcessConfig config;

	private MessageProcessFactory() {
		config=ClientUtils.getMessageProcessConfig();
	}

	public static MessageProcessFactory getInstance() {

		if (null == instance) {
			instance = new MessageProcessFactory();
		}
		return instance;
	}
	
	public IMessageProcess creatMessageProcess(int messageType) {
		if(null==config){
			config=ClientUtils.getMessageProcessConfig();
		}
		String processClass = config.getProcessClass(messageType);
		IMessageProcess process=null;
			try {
				process=(IMessageProcess)Class.forName(processClass).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		return process;
	}
	
	
}
