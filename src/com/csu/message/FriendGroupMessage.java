package com.csu.message;



public class FriendGroupMessage extends BaseMessage {

//	private MsgUser ower;         //登陆者的信息
	private String friendGroupXML;//好友列表信息
	private String teamXML;       //群列表信息
	
	public FriendGroupMessage(){
		this.setMsgType(MessageType.FriendGroupMessage_Type);
		this.setSrcQQ(MessageType.sysQQ);
	}
	
	public FriendGroupMessage(String friendGroupOwerQQ){
		this();
		this.setDestQQ(friendGroupOwerQQ);
	}

	public String getFriendGroupXML() {
		return friendGroupXML;
	}

	public void setFriendGroupXML(String friendGroupXML) {
		this.friendGroupXML = friendGroupXML;
	}

	public String getTeamXML() {
		return teamXML;
	}

	public void setTeamXML(String teamXML) {
		this.teamXML = teamXML;
	}

//	public MsgUser getOwer() {
//		return ower;
//	}
//
//	public void setOwer(MsgUser ower) {
//		this.ower = ower;
//	}
	

}
