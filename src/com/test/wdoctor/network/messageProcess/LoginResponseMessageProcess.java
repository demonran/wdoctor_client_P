package com.test.wdoctor.network.messageProcess;

import android.content.Context;
import android.content.Intent;

import com.csu.message.BaseMessage;
import com.csu.message.LoginResponseMessage;
import com.csu.message.MessageType;
import com.csu.message.OnlineMessage;
import com.test.wdoctor.Constants;
import com.test.wdoctor.network.socket.ClientChatSocket;
import com.test.wdoctor.network.socket.ConnectSession;
import com.test.wdoctor.utils.EIMClientConfig;


public class LoginResponseMessageProcess implements IMessageProcess{

	public void processMessage(BaseMessage message,Context context) {
		LoginResponseMessage loginResponse=(LoginResponseMessage)message;

		Intent intent = new Intent(Constants.ACTION_LOGIN);
	    
		int responseInfo=loginResponse.getLoginResponse();
		intent.putExtra("responseInfo", responseInfo);
		if(responseInfo==MessageType.LoginResponse_Faile){
			
			int errorReason=loginResponse.getErrorReason();
			String error =EIMClientConfig.getLoginErrrorReasonByType(errorReason);
			intent.putExtra("error", error);
			ClientChatSocket.getInstance().setStart(false);
			ConnectSession.getInstance().close();
				
		}else{
			//发送上线消息
			//扩展:登陆时选择......
			OnlineMessage onlineMessage=new OnlineMessage(message.getDestQQ());
			ConnectSession.getInstance().sendTextMessage(onlineMessage);
		}
		context.sendBroadcast(intent);
		
	}

}
