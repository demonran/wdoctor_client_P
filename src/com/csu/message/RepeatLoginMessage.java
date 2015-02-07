package com.csu.message;

/**
 * 
 * @author 胡海亮  QQ:249782944
 * 学校:中南大学 商学院
 * EnterpriseIMServer :企业即时通讯
 * Apr 27, 2010
 * 
 * 
 */
public class RepeatLoginMessage extends BaseMessage {

	public RepeatLoginMessage(){
		this.setMsgType(MessageType.RepeatLoginMessage_Type);
	}
	
	
	public RepeatLoginMessage(String destQQ){
		this();
		this.setSrcQQ(MessageType.sysQQ);
		this.setDestQQ(destQQ);
		
	}
	
	private String repeatLoginIP;

	public String getRepeatLoginIP() {
		return repeatLoginIP;
	}

	public void setRepeatLoginIP(String repeatLoginIP) {
		this.repeatLoginIP = repeatLoginIP;
	}
}
