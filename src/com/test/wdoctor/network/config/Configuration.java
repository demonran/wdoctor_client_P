package com.test.wdoctor.network.config;

public class Configuration {
	
	private String nioHost;
	
	private int nioPort;
	
	

	public Configuration() {
		super();
	}

	public Configuration(String nioHost, int nioPort) {
		super();
		this.nioHost = nioHost;
		this.nioPort = nioPort;
	}

	public String getNioHost() {
		return nioHost;
	}

	public void setNioHost(String nioHost) {
		this.nioHost = nioHost;
	}

	public int getNioPort() {
		return nioPort;
	}

	public void setNioPort(int nioPort) {
		this.nioPort = nioPort;
	}
	
	
	
	
}
