package com.csu.message;

import java.util.Date;
/**
 * 
 * 
 * 用户上线消息
 *     包括
 *      1：消息头  2：上线日期 
 */
public class OnlineMessage extends BaseMessage {

	public OnlineMessage(String srcQQ){
		setMsgType(MessageType.OnlineMessage_Type);
		setDestQQ(MessageType.sysQQ);
		this.setSrcQQ(srcQQ);
		this.setOnlineDate(new Date());
	}
	
	private Date onlineDate;

	public Date getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}

}
