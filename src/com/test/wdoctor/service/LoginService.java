package com.test.wdoctor.service;


public class LoginService {
	
	
	public LoginService()
	{
	}

//	public boolean login(String username, String password) {
//		
//		Client client = Client.getInstance();
//		boolean connect = client.startSocket();
//		
//		LoginMessage loginMessage = new LoginMessage();
//		if(connect)
//		{
//			loginMessage.setDestIp(NioSocketManager.xmppHost);
//			loginMessage.setDestPort(NioSocketManager.xmppPort);
//          
//			loginMessage.setUsername(username);
//			loginMessage.setPassword(password);
//			loginMessage = client.sendMessageForResult(loginMessage);
//	  		Log.i("Login", loginMessage.toString());
////	  		myHandler.sendEmptyMessage(CONNECT_SERVER_SUCCESS);
//		}
//		if(loginMessage.getType() != Type.RESULT)
//		{
//			client.close();
//		}
//		client.setUsername(loginMessage.getUsername());
//		return loginMessage.getType() == Type.RESULT;
//	}

}
