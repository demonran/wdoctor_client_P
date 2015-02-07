package com.test.wdoctor.network.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.csu.message.BaseMessage;
import com.test.wdoctor.CommonContainer;
import com.test.wdoctor.network.messageProcess.ClientReceivedMessageProcess;
import com.test.wdoctor.network.socket.ConnectSession;
import com.test.wdoctor.utils.LogUtil;


public class ClientChatHandler extends IoHandlerAdapter {
	
	private static final String TAG = LogUtil.makeLogTag(ClientChatHandler.class);

	private Context context;
	public ClientChatHandler(Context context) {
		this.context = context;
	}

	/**
	 * 
	 * 当接口中其他方法抛出异常未被捕获时触发此方法 
	 */
	public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
		
	}

	/**
	 * 当接收到消息后被触发 
	 * 
	 */
	public void messageReceived(IoSession ioSession, Object msg) throws Exception {
		BaseMessage message=(BaseMessage)msg;
		Log.i(TAG,"接受到得消息为："+message);
		ClientReceivedMessageProcess messageProcess=new ClientReceivedMessageProcess();
		messageProcess.processMessage(message,context);
	}

	/**
	 * 当发送消息后被触发 
	 * 
	 */
	public void messageSent(IoSession ioSession, Object msg) throws Exception {
		
	}

	/**
	 * 当会话关闭时被触发
	 * 
	 */
	public void sessionClosed(IoSession ioSession) throws Exception {
		Log.e(TAG,"与服务器断开连接！！");
//		EIMTrayIcon trayIcon=EIMTrayIcon.getInStance();
//		trayIcon.showIcon(EIMClientConfig.OffLineTryIcon_Type);
	}

	/**
	 * 当会话创建时被触发 
	 * 
	 */
	public void sessionCreated(IoSession ioSession) throws Exception {
		
	}
	/**
	 * 
	 * 当会话空闲时被触发 
	 */
	public void sessionIdle(IoSession ioSession, IdleStatus msg) throws Exception {
		
	}
	/**
	 * 
	 * 当会话开始时被触发 
	 */
	public void sessionOpened(IoSession ioSession) throws Exception {
		ConnectSession clientConnect=ConnectSession.getInstance();
		Log.i(TAG,"会话已经打开");
		clientConnect.setTextSession(ioSession);
		clientConnect.sendTextMessage(CommonContainer.loginMessage);
	}



}
