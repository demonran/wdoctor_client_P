package com.test.wdoctor.componet;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.test.wdoctor.MainWeixin;
import com.test.wdoctor.R;
import com.test.wdoctor.ShakeActivity;
import com.test.wdoctor.adapter.MMListAdapter;
import com.test.wdoctor.model.Function;

public class DiscoveryTab{
	
	private View view;
	
	private GridView fucntionGrid;
	
	private Activity mainActivity;
	
	public DiscoveryTab(final Activity mainActivity) {
		this.mainActivity = mainActivity;
		
		LayoutInflater mLi = LayoutInflater.from(mainActivity);
		
		view = mLi.inflate(R.layout.main_tab_friends, null);
	        
        
        final List<Function> functions = new ArrayList<Function>();
        
        functions.add(new Function(Function.Type.PENGYOUQUAN,"朋友圈"));
        functions.add(new Function(Function.Type.PENGYOUQUAN,"扫一扫"));
        functions.add(new Function(Function.Type.PENGYOUQUAN,"摇一摇"));
//        functions.add(new Function(3,"上门会诊"));
//        functions.add(new Function(4,"紧急加号"));
//        functions.add(new Function(5,"紧急住院"));
//        functions.add(new Function(6,"科室动态"));
//        functions.add(new Function(7,"医者联盟"));
        
        
	}
	
	public View getView()
	{
		return view;
	}
	
	public GridView getGucntionGrid()
	{
		return fucntionGrid;
	}
	
	public void btn_shake(View v) {                                   //手机摇一摇
		Intent intent = new Intent (mainActivity,ShakeActivity.class);			
		mainActivity.startActivity(intent);	
	}

}
