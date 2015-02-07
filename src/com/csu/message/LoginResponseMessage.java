package com.csu.message;

/**
 * 
 * @author 胡海亮  QQ:249782944
 * 学校:中南大学 商学院
 * EnterpriseIMServer :企业即时通讯
 * Apr 27, 2010
 * 
 * 登入响应消息
 *     包括 1:消息头 2： 验证结果
 */
public class LoginResponseMessage extends BaseMessage {

	private int loginResponse;     //服务器还回登入验证结果 1：正确 0：错误
	private int errorReason;       //如果验证结果错误 则有这个属性 
	                               //0：服务器内部错误 1：QQ号不对  2：密码错误  3:版本错误 4：其他原因
	public LoginResponseMessage(){
		this.setMsgType(MessageType.LoginMessage_Type);
	}
	
	public int getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(int errorReason) {
		this.errorReason = errorReason;
	}
	public int getLoginResponse() {
		return loginResponse;
	}
	public void setLoginResponse(int loginResponse) {
		this.loginResponse = loginResponse;
	}

	public String toString(){
		String str=null;
		if(loginResponse==1){
			str="登入成功";
		}else if(loginResponse==0){
			switch(errorReason){
			  case   0:str="登入失败，服务器内部错误，错误代码为："+errorReason;break;
			  case   1:str="登入失败，QQ号不对，错误代码为："+errorReason;break;
			  case   2:str="登入失败，密码错误，错误代码为："+errorReason;break;
			  case   3:str="登入失败，版本错误，错误代码为："+errorReason;break;
			  case   4:str="登入失败，其他原因，错误代码为："+errorReason;break;
			  default :str="登入失败，程序未知原因";break;
			}
		}
		return str;
	}
}
