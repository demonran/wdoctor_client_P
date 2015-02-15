package com.test.wdoctor.componet;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

import com.test.wdoctor.MainWeixin;
import com.test.wdoctor.R;
import com.test.wdoctor.activity.TuWenActivity;
import com.test.wdoctor.adapter.FunctionGridAdapter;
import com.test.wdoctor.adapter.MMListAdapter;
import com.test.wdoctor.model.Function;
import com.test.wdoctor.model.MsgUser;

public class PrivateDoctorTab{
	
	private LayoutInflater mLi ;
	
	private View view;
	
	private MMListAdapter mmListAdapter ;
	
	private GridView fucntionGrid;
	
	public PrivateDoctorTab(final Activity mainActivity) {
		mLi = LayoutInflater.from(mainActivity);
		
		view = mLi.inflate(R.layout.main_tab_weixin, null);
	        
        GridView fucntionGV = (GridView)view.findViewById(R.id.grid_function);
        
        final List<Function> functions = new ArrayList<Function>();
        
        functions.add(new Function(Function.Type.TUWEN,"图文咨询"));
        functions.add(new Function(Function.Type.TUWEN,"电话咨询"));
        functions.add(new Function(Function.Type.TUWEN,"私人医生"));
        functions.add(new Function(Function.Type.TUWEN,"上门会诊"));
        functions.add(new Function(Function.Type.TUWEN,"紧急加号"));
        functions.add(new Function(Function.Type.TUWEN,"紧急住院"));
        functions.add(new Function(Function.Type.TUWEN,"科室动态"));
        functions.add(new Function(Function.Type.TUWEN,"医者联盟"));
        
        fucntionGV.setAdapter(new FunctionGridAdapter(mainActivity, functions));
        
        ListView mmList = (ListView)view.findViewById(R.id.mm_list);
        List<MsgUser> list = new ArrayList<MsgUser>();
        
    	list.add(new MsgUser("tuwenzixun","张三"));
    	list.add(new MsgUser("dianhuazixun","王四"));
    	
        mmListAdapter = new MMListAdapter(mainActivity,list);
        mmList.setAdapter(mmListAdapter);
	
      //添加事件
	fucntionGV.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
			Function function = functions.get(position);
			Intent intent = null;
			if(function.getType() == Function.Type.TUWEN)
			{
				intent = new Intent(mainActivity,TuWenActivity.class);
			}else
			{
				intent = new Intent (mainActivity,TuWenActivity.class);
			}
			mainActivity.startActivity(intent);
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
