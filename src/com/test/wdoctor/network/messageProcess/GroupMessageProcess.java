package com.test.wdoctor.network.messageProcess;

import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.csu.message.BaseMessage;
import com.csu.message.FriendGroupMessage;
import com.test.wdoctor.Constants;
import com.test.wdoctor.model.Cache;
import com.test.wdoctor.model.MsgFriendGroup;
import com.test.wdoctor.model.MsgFriendGroupList;
import com.test.wdoctor.model.MsgTeam;
import com.test.wdoctor.model.MsgTeamList;
import com.test.wdoctor.model.MsgUser;
import com.thoughtworks.xstream.XStream;

public class GroupMessageProcess implements IMessageProcess {

	public void processMessage(BaseMessage message,Context context) {
		
		FriendGroupMessage groupMessage=(FriendGroupMessage)message;
		List<MsgFriendGroup> friendGroups=getFriendGroupList(groupMessage.getFriendGroupXML());
		List<MsgTeam> teams=getTeamList(groupMessage.getTeamXML());
		Cache cache=Cache.getInstance();
		cache.setFriendGroups(friendGroups);
		cache.setTeams(teams);
		MsgUser ower=cache.getUserByID(message.getDestQQ());
		
		cache.setOwerUser(ower);
		
		Intent intent = new Intent(Constants.ACTION_SHOW_FRIEND);
		context.sendBroadcast(intent);
	}
	
	
	public List<MsgFriendGroup> getFriendGroupList(String friendGroupXML ){
		XStream xstream = new XStream();
		xstream.alias("FriendGrous", MsgFriendGroupList.class);
		xstream.alias("friendGroup", MsgFriendGroup.class);
		xstream.alias("user", MsgUser.class);
		xstream.addImplicitCollection(MsgFriendGroupList.class, "FriendGroups");
		xstream.addImplicitCollection(MsgFriendGroup.class, "userList");
		return ((MsgFriendGroupList)xstream.fromXML(friendGroupXML)).getFriendGroups();
	}
	
	public List<MsgTeam> getTeamList(String teamXML ){
		XStream xstream = new XStream();
		xstream.alias("Teams", MsgTeamList.class);
		xstream.alias("team", MsgTeam.class);
		xstream.alias("user", MsgUser.class);
		xstream.addImplicitCollection(MsgTeamList.class, "teams");
		xstream.addImplicitCollection(MsgTeam.class, "userList");
		return  ((MsgTeamList)xstream.fromXML(teamXML)).getTeams();
	}
}