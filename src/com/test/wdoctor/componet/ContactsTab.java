package com.test.wdoctor.componet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.test.wdoctor.ChatActivity;
import com.test.wdoctor.Constants;
import com.test.wdoctor.R;
import com.test.wdoctor.adapter.FriendListAdapter;
import com.test.wdoctor.model.Cache;
import com.test.wdoctor.model.MsgUser;

public class ContactsTab{
	
	private LayoutInflater mLi ;
	
	private View view;
	
	private FriendListAdapter frinedListAdapter ;
	
	private GridView fucntionGrid;
	
	public ContactsTab(final Activity mainActivity) {
		mLi = LayoutInflater.from(mainActivity);
		
		 view = mLi.inflate(R.layout.main_tab_address, null);
        ListView friendList = null;
        if(view != null)
        {
        	friendList =(ListView)view.findViewById(R.id.friend_list);
        	frinedListAdapter = new FriendListAdapter(mainActivity,Cache.getInstance().getfriendList());
            friendList.setAdapter(frinedListAdapter);
        }
        GridView friendGrid =(GridView)view.findViewById(R.id.friend_grid);
        final List<Map<String,String>> data = new ArrayList<Map<String,String>>();
        Map<String,String> map = new HashMap<String,String>();
        map.put("kind", "病人库");
        data.add(map);
        map = new HashMap<String,String>();
        map.put("kind", "医生群");
        data.add(map);
        
        // init data
        final List<MsgUser> doctors = new ArrayList<MsgUser>();
        doctors.add(new MsgUser("80001", "王医生"));
        doctors.add(new MsgUser("80002", "张医生"));
        doctors.add(new MsgUser("80003","李医生"));
        final List<MsgUser>  patients = new ArrayList<MsgUser>();
        patients.add(new MsgUser("8001", "王小姐"));
        patients.add(new MsgUser("8002", "张先生"));
        patients.add(new MsgUser("8003","刘先生"));
        
        
        SimpleAdapter adapter = new SimpleAdapter(mainActivity, data, R.layout.friend_grid_item, new String[]{"kind"}, new int[]{R.id.kind_name});
    	friendGrid.setAdapter(adapter);
    	
    	 friendList.setOnItemClickListener(new OnItemClickListener() {
 			@Override
 			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
 				MsgUser msgUser = frinedListAdapter.getItem(position);
 				Intent intent = new Intent (mainActivity,ChatActivity.class);
 				intent.putExtra(Constants.MESSAGE_FRIENDQQ, msgUser.getUserID());
 				mainActivity.startActivity(intent);
 			}
 		});
 	    
 	    friendGrid.setOnItemClickListener(new OnItemClickListener() {
 			@Override
 			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
 				Map<String,String> friendKind = data.get(position);
 				if(friendKind.get("kind").equals("医生群"))
 				{
 					frinedListAdapter.setData(doctors);
 				}else
 				{
 					frinedListAdapter.setData(patients);
 				}
 				frinedListAdapter.notifyDataSetChanged();
 			}
 		});
	}
	
	
	public View getView()
	{
		return view;
	}
	
	public GridView getGucntionGrid()
	{
		return fucntionGrid;
	}

}
