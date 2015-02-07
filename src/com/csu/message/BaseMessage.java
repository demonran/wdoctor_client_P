package com.csu.message;

/**
 * 
 * @author 胡海亮  QQ:249782944
 * 学校:中南大学 商学院
 * EnterpriseIMServer :企业即时通讯
 * Apr 27, 2010
 * 
 * 所有消息的父类，消息头，其属性是所有消息都必须拥有的。
 */
public class BaseMessage implements java.io.Serializable{
	
	private int msgType;        //消息类型
	private String srcQQ;       //消息的发送者
	private String destQQ;      //消息的接受者
	
	public String getDestQQ() {
		return destQQ;
	}
	public void setDestQQ(String destQQ) {
		this.destQQ = destQQ;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getSrcQQ() {
		return srcQQ;
	}
	public void setSrcQQ(String srcQQ) {
		this.srcQQ = srcQQ;
	}
	
	public String toString(){
		return "消息类型:"+msgType+"  发送者:"+srcQQ+"  接受者:"+destQQ;
	}
}
