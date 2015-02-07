package com.test.wdoctor.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author 胡海亮  QQ:249782944
 * 学校:中南大学 商学院
 * EnterpriseIMServer :企业即时通讯
 * Apr 28, 2010
 */
public class MsgTeam extends Group implements Serializable {
	
	private List<MsgUser> userList;    //群的用户列表
	
	public List<MsgUser> getUserList() {
		return userList;
	}
	public void setUserList(List<MsgUser> userList) {
		this.userList = userList;
	}
	
	public List<MsgUser> getGrops() {
		return userList;
	}
	public String toString(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("\n"+this.getGroupName()+" 群信息如下:\n");
		buffer.append("teamID:\" "+this.getGroupID()+"\"");
		buffer.append("teamOwerID:\" "+this.getGroupOwerID()+"\"");
		buffer.append("teamName:\" "+this.getGroupName()+"\"\n");
		buffer.append("群的用户列表:\n");
		for(MsgUser user: userList){
			buffer.append("\t"+user+"\n");
		}
		buffer.append("\n");
		return buffer.toString();
	}

	
}
