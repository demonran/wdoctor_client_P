package com.test.wdoctor.model;

public class Function {
	private int fid;
	
	private String fname;



	public Function(int fid, String fname) {
		this.fid = fid;
		this.fname = fname;
	}

	public int getFid() {
		return fid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}
	
	
}
