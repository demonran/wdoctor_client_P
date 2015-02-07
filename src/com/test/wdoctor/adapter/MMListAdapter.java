package com.test.wdoctor.adapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.wdoctor.R;
import com.test.wdoctor.model.MsgUser;

public class MMListAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	
	private DateFormat df =DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
	
	
	List<MsgUser> data = new ArrayList<MsgUser>();
	
	public MMListAdapter(Context context ,List<MsgUser> data)
	{
//		mInflater = LayoutInflater.from(context);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.data = data;
	}
	
	
	
	public void setData(List<MsgUser> data){
		this.data.clear();
		this.data.addAll(data);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public MsgUser getItem(int position) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		 View view = null;
	      if (convertView != null) {	    	
	    	  view = convertView;	                    
	      } else {        
	    	  view = mInflater.inflate(R.layout.mm_item, null);
	      } 
		
	      
	      TextView lastTimeText = (TextView) view.findViewById(R.id.lastTime);     
	      TextView nameText = (TextView) view.findViewById(R.id.name);
	      ImageView headImage = (ImageView) view.findViewById(R.id.head);  
	      TextView lastMsgText = (TextView) view.findViewById(R.id.lastMsg);     
	                
	      headImage.setImageResource(R.drawable.xiaohei);
	      nameText.setText(data.get(position).getUserName());
//	      List<ChatMsg> chatMsgs = data.get(position).getChatMsgs();
//	      ChatMsg lastMsg = chatMsgs.get(chatMsgs.size()-1);
//	      lastTimeText.setText(df.format(lastMsg.getDate()));
//	      lastMsgText.setText(lastMsg.getText());
	      
//	      syncImageLoader.loadImage(position, data.get(position).get("image"), imageLoadListener);
	      return view;
		
	}
	
//	OnImageLoadListener imageLoadListener = new OnImageLoadListener(){
//
//		@Override
//		public void onImageLoad(Integer t, Drawable drawable) {
//			View view = mListView.findViewWithTag(t);
//			if (view != null) {
//				ImageView iv = (ImageView) view
//						.findViewById(R.id.itemImage);
//				iv.setImageDrawable(drawable);
//			}
//			
//		}
//
//		@Override
//		public void onError(Integer t) {
//			View view = mListView.findViewWithTag(t);
//			if (view != null) {
//				ImageView iv = (ImageView) view
//						.findViewById(R.id.itemImage);
//				iv.setBackgroundResource(R.drawable.ic_launcher);
//			}
//			
//			
//		}
//		
//	};

}
