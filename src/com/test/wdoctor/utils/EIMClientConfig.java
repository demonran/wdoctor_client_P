package com.test.wdoctor.utils;

import com.csu.message.MessageType;

public class EIMClientConfig {
	/**上线时托盘的图标  */
	public static final int  OnlineTryIcon_Type      =11111110;
	/**下线时托盘的图标  */
	public static final int  OffLineTryIcon_Type     =11111111;
	/**登入时托盘的图标  */
	public static final int  LoginingTryIcon_Type    =11111112;
	/**隐身时托盘的图标  */
	public static final int  YinShengTryIcon_Type     =11111113;
	
	public static String  getLoginErrrorReasonByType(int faile_reason){
		String reason="";
		switch(faile_reason){
		case MessageType.LoginResponse_Faile_QQReason:
			reason="用户不存在！";break;
		case MessageType.LoginResponse_Faile_PwdReason:
			reason="QQ密码错误！";break;
		case MessageType.LoginResponse_Faile_SerReason:
			reason="服务器内部错误！";break;
		case MessageType.LoginResponse_Faile_VisonReason:
			reason="QQ版本错误！";break;
		case MessageType.LoginResponse_Faile_OtherReason:
			reason="QQ登录未知错误！";break;
		}
		return reason;
	}
	
}
