package com.test.wdoctor.network.socket;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import android.content.Context;
import android.util.Log;

import com.test.wdoctor.network.handler.ClientChatHandler;
import com.test.wdoctor.utils.LogUtil;

/**
 * 
 * @author 胡海亮  QQ:249782944
 * 学校:中南大学 商学院
 * EnterpriseIMClient :企业即时通讯
 * May 11, 2010
 * 
 * 客户的聊天消息发送socket 单例
 */
public class ClientChatSocket extends EIMClientSocket {
	
	private static final String TAG = LogUtil.makeLogTag(ConnectSession.class);
	
	private static ClientChatSocket instance;
	
	public static ClientChatSocket getInstance(){
		if(instance==null){
			instance= new ClientChatSocket();
		}
		return instance;
	}
	
	private ClientChatSocket(){
	}
	
	public boolean closedSocket() {
		this.getConnector().dispose();
		this.setStart(false);
		Log.i(TAG,"客户关闭了与服务器的连接");
		return true;
	}

	public void initSocket(Context context) {
		this.setHandlerAdapter(new ClientChatHandler(context));
		this.setConnector(new NioSocketConnector());
		Log.i(TAG,"客户初始化了与服务器的连接");
	}

	/**
	 * @param handler 
	 * 
	 */
	public boolean startSocket(Context context)  {
		try{
			if(this.isStart()==false){
				this.initSocket(context);
				/**创建接收数据的过滤器		             */
				DefaultIoFilterChainBuilder chain=this.getConnector().getFilterChain();
				ObjectSerializationCodecFactory objscf=new ObjectSerializationCodecFactory();
				ProtocolCodecFilter protocolCodecFilter=new ProtocolCodecFilter(objscf);
				chain.addLast("myTextChain", protocolCodecFilter);
			    this.getConnector().setHandler(this.getHandlerAdapter());
				/**设置连接超时的时间 为：一分钟  这个超时不可以过小		 */
				this.getConnector().setConnectTimeoutMillis(EIMClientSocket.timeOutMillis);
				Log.i(TAG,"ip/port:"+this.getServer_IP_Port()+"/"+this.getServer_Socket_Port());
				InetSocketAddress textInetSocketAddress=new InetSocketAddress(this.getServer_IP_Port(),this.getServer_Socket_Port());
				/**建立连接		 */
				this.connectFuture=this.getConnector().connect(textInetSocketAddress);
				Log.i(TAG,"已经建立用户的连接");
				this.setStart(true);
				Log.i(TAG,"客户开启了与服务器的连接");
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
		
	}

}
