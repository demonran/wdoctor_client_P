package com.csu.message;

/**
 * 
 * @author 胡海亮  QQ:249782944
 * 学校:中南大学 商学院
 * EnterpriseIMServer :企业即时通讯
 * Apr 27, 2010
 * 
 * 登入消息
 *      包括 1：消息头 2：登入者的账号和密码
 */
public class LoginMessage extends BaseMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String owerQQ;   //登入者的QQ号
	private String pwd;     //登入者的密码
	private String state;   //登入者的状态
	private String loginID;
	
	public LoginMessage(){
		this.setDestQQ(MessageType.sysQQ);
		this.setMsgType(MessageType.LoginMessage_Type);
	}
	public String getOwerQQ() {
		return owerQQ;
	}
	
	public void setOwerQQ(String owerQQ) {
		this.owerQQ = owerQQ;
	}
	
	public String getPwd() {
		return pwd;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLoginID() {
		return loginID;
	}
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	} 
}
