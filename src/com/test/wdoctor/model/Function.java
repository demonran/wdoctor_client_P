package com.test.wdoctor.model;

import java.util.HashMap;
import java.util.Map;

public class Function {
	private Type type;
	
	private String fname;
	
	private Map<String,Object> attributes=new HashMap<String,Object>();

	public enum Type{
		TUWEN,
		PENGYOUQUAN
	}

	public Function(Type type, String fname) {
		this.type = type;
		this.fname = fname;
	}

	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
	
	public Object getAttribute(String key) {
		return attributes.get(key);
	}
	public void setAttribute(String key, Object value) {
		attributes.put(key, value);
	}
	
	
}
