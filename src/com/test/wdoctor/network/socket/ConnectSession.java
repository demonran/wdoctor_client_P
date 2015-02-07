package com.test.wdoctor.network.socket;

import org.apache.mina.core.session.IoSession;

import android.util.Log;

import com.csu.message.BaseMessage;
import com.test.wdoctor.utils.LogUtil;


/**
 * 
 * 
 * 保存客户的连接对象 全局单例模式
 *  连接对象包括：
 *     1
 *     2
 *     3
 *
 */
public class ConnectSession {
	
	private static final String TAG = LogUtil.makeLogTag(ConnectSession.class);
	
private static ConnectSession instance;
	
	private IoSession textSession;     //聊天会话连接对象
	private IoSession fileSession;     //文件会话连接对象
	private IoSession cinemaSession;   //视频会话连接对象
	private String    owerQQ;          //此会话连接对象的所有者的qq号
	
	private ConnectSession(){
		
	}
	
	public static ConnectSession getInstance(){
		if(instance==null){
			instance=new ConnectSession();
		}
		return instance;
	}

	public void close(){
		if(textSession!=null){
			textSession.close();
		}
		if(fileSession!=null){
			fileSession.close();
		}
		if(cinemaSession!=null){
			cinemaSession.close();
		}
	}
	/**
	 * 
	 * @param msg
	 */
	public void sendTextMessage(BaseMessage msg){
		Log.i(TAG,"发送的消息为："+msg);
		textSession.write(msg);
	}
	/**
	 * 
	 * @param msg
	 */
	public void sendFileMessage(BaseMessage msg){
		fileSession.write(msg);
	}
	
	/**
	 * 
	 * @param msg
	 */
	public void sendCinemaeMessage(BaseMessage msg){
		cinemaSession.write(msg);
	}
	
	public IoSession getCinemaSession() {
		return cinemaSession;
	}

	public void setCinemaSession(IoSession cinemaSession) {
		this.cinemaSession = cinemaSession;
	}

	public IoSession getFileSession() {
		return fileSession;
	}

	public void setFileSession(IoSession fileSession) {
		this.fileSession = fileSession;
	}

	public String getOwerQQ() {
		return owerQQ;
	}

	public void setOwerQQ(String owerQQ) {
		this.owerQQ = owerQQ;
	}

	public IoSession getTextSession() {
		return textSession;
	}

	public void setTextSession(IoSession textSession) {
		this.textSession = textSession;
	}


}
