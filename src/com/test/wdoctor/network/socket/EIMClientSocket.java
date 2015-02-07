package com.test.wdoctor.network.socket;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import android.content.Context;

/**
 * 
 * @author 胡海亮  QQ:249782944
 * 学校:中南大学 商学院
 * EnterpriseIMClient :企业即时通讯
 * May 8, 2010
 * 
 * 在mina基础上自己封装的客户端的连接Socket（简单实现，其子类为单例）
 *   包括
 *     1:客户端连接器 与 客户端连接
 *     2:服务的ip与端口号
 *     3:初始化 打开  关闭 Socket的一列方法
 */
public abstract class EIMClientSocket {

	private boolean isStart=false;                          //判断Socket是否已经启动了
	public final static int timeOutMillis=60*1000;         //连接超时的时间
	private  int     server_Socket_Port=9090;                   //要连接的Socket的监听端口
	private  String     server_IP_Port="115.28.92.230";                    //要连接的Socket的IP
	private IoHandlerAdapter handlerAdapter;               //消息处理器
	private NioSocketConnector connector;                  //客户端连接器
	public  ConnectFuture connectFuture;                   //客户端连接
	
	/**
	 * 初始化Socket
	 */
	public abstract void initSocket(Context context);
	/**
	 * 启动Socket
	 */
	public abstract boolean startSocket(Context context) ;
	/**
	 * 关闭Socket
	 */
	public abstract boolean closedSocket() ;
	
	
	public IoHandlerAdapter getHandlerAdapter() {
		return handlerAdapter;
	}
	public void setHandlerAdapter(IoHandlerAdapter handlerAdapter) {
		this.handlerAdapter = handlerAdapter;
	}
	public boolean isStart() {
		return isStart;
	}
	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	public NioSocketConnector getConnector() {
		return connector;
	}
	public void setConnector(NioSocketConnector connector) {
		this.connector = connector;
	}
	public int getServer_Socket_Port() {
		return server_Socket_Port;
	}
	public void setServer_Socket_Port(int server_Socket_Port) {
		this.server_Socket_Port = server_Socket_Port;
	}
	public String getServer_IP_Port() {
		return server_IP_Port;
	}
	public void setServer_IP_Port(String server_IP_Port) {
		this.server_IP_Port = server_IP_Port;
	}
}
