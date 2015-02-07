package com.csu.message;

/**
 * 
 * @author 胡海亮  QQ:249782944
 * 学校:中南大学 商学院
 * EnterpriseIMServer :企业即时通讯
 * Apr 27, 2010
 * 
 *   用户聊天消息
 *    包括 1:消息头 2：聊天内容
 */
public class TalkMessage extends BaseMessage {
	
	private  String talkMsg;  //聊天内容
	
	public TalkMessage(){
		this.setMsgType(MessageType.TalkMessage_Type);
	}

	public String getTalkMsg() {
		return talkMsg;
	}

	public void setTalkMsg(String talkMsg) {
		this.talkMsg = talkMsg;
	}
	
	public String toString(){
		return "客户端：用户"+this.getSrcQQ()+"给 好友"+this.getDestQQ()+"发送了聊天信息:"+talkMsg;
	}
}
