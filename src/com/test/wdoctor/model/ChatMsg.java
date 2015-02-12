package com.test.wdoctor.model;


public class ChatMsg{
	
	private String friendQQ;
	
    private String name;

    private String date;

    private String text;

    private boolean isComMeg = true;
    


	public ChatMsg(String friendQQ, String name, String date, String text,
			boolean isComMeg) {
		super();
		this.friendQQ = friendQQ;
		this.name = name;
		this.date = date;
		this.text = text;
		this.isComMeg = isComMeg;
	}

	public ChatMsg() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFriendQQ() {
		return friendQQ;
	}

	public void setFriendQQ(String friendQQ) {
		this.friendQQ = friendQQ;
	}

	public boolean isComMeg() {
		return isComMeg;
	}

	public void setComMeg(boolean isComMeg) {
		this.isComMeg = isComMeg;
	}
	
	

}
