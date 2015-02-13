package com.test.wdoctor.adapter;

import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.wdoctor.R;
import com.test.wdoctor.model.Function;

public class FunctionGridAdapter extends BaseAdapter{
	
private LayoutInflater mInflater;
	
	
	List<Function> data ;
	
	public FunctionGridAdapter(Context context)
	{
		this(context, Collections.<Function>emptyList());
	}
	
	public FunctionGridAdapter(Context context, List<Function> data)
	{
		mInflater = LayoutInflater.from(context);
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Function getItem(int position) {
		if (position >= getCount()) {
			return null;
		}
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		 ViewHolder viewHolder = null;	
	      if (convertView != null) {	    	
	    	  viewHolder = (ViewHolder) convertView.getTag();                  
	      } else {        
	    	  convertView = mInflater.inflate(R.layout.grid_function_item, null);
	    	  viewHolder = new ViewHolder();
			  viewHolder.nameText = (TextView) convertView.findViewById(R.id.function_name);
			  viewHolder.iconImage = (ImageView) convertView.findViewById(R.id.function_icon);
			  convertView.setTag(viewHolder);
	      } 
	      viewHolder.nameText.setText(getItem(position).getFname());
	      viewHolder.iconImage.setImageResource(R.drawable.default_tuwenchat);
	      convertView.setBackgroundColor(Color.GREEN);
	      
	      return convertView;
		
	}
	
   static class ViewHolder { 
       public TextView nameText;
       public ImageView iconImage;
   }

}
