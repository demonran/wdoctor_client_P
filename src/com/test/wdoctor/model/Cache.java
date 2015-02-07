package com.test.wdoctor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author 胡海亮  QQ:249782944
 * 学校:中南大学 商学院
 * EnterpriseIMClient :企业即时通讯
 * May 12, 2010
 */
public class Cache {

	private static Cache instance;
	
	private List<MsgFriendGroup> friendGroups;
	private List<MsgTeam> teams;
	private boolean isChange=false;
	private boolean isChangeList=false;
	private Map<String ,MsgUser> friendMap=new HashMap<String ,MsgUser>();
	private List<MsgUser> friendList=new ArrayList<MsgUser>();
	
	private MsgUser owerUser;
	
	
	public static void setInstance(Cache instance) {
		Cache.instance = instance;
	}

	public static Cache getInstance() {
		if(instance==null){
			instance=new Cache();
		}
		return instance;
	}
	
	private Cache(){
		 friendGroups=new ArrayList<MsgFriendGroup>();
		 teams=new ArrayList<MsgTeam>();
	}
	
	public Map<String ,MsgUser> getfriendMap(){
		if(isChange){
			isChange=false;
			for(MsgFriendGroup group:friendGroups){
				for(MsgUser user:group.getUserList()){
					friendMap.put(user.getUserID(), user);
				}
			}
		}
		return friendMap;
	}
	
	public List<MsgUser> getfriendList(){
		if(isChangeList){
			isChangeList=false;
			for(MsgFriendGroup group:friendGroups){
				for(MsgUser user:group.getUserList()){
					friendList.add(user);
				}
			}
		}
		return friendList;
	}
	
	
	public MsgUser getUserByID(String id){
		return this.getfriendMap().get(id);
	}
	
	public List<MsgFriendGroup> getFriendGroups() {
		return friendGroups;
	}

	public void setFriendGroups(List<MsgFriendGroup> friendGroups) {
		isChangeList = true;
		isChange = true;
		this.friendGroups = friendGroups;
	}

	public List<MsgTeam> getTeams() {
		return teams;
	}

	public void setTeams(List<MsgTeam> teams) {
		this.teams = teams;
	}

	public void setOwerUser(MsgUser owerUser) {
		this.owerUser = owerUser;
	}

	public MsgUser getOwerUser() {
		return owerUser;
	}
	
}
