package com.test.wdoctor.utils;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class InetAddressTool {

	/**
	 * 得到本机的ip地址
	 * @return
	 */
	public static String getLocalIP(){
		InetAddress addr=null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return addr.getHostAddress().toString();//获得本机IP
	}
	
	/**
	 * 得到本机的空闲的端口号
	 * @return
	 */
	public static int getAvailablePort(){
		int port=100;
		for(int i=100;i<65535;i++){
			try {
				ServerSocket serverSocket=new ServerSocket(i);
				port=i;
				serverSocket.close();
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		return port;
	}
}
