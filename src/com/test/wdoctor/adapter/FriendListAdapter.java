package com.test.wdoctor.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.test.wdoctor.R;
import com.test.wdoctor.model.MsgUser;

public class FriendListAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	
	private Context context;
	
	private ListView friendList;
	
	List<MsgUser> data = new ArrayList<MsgUser>();
	
	public FriendListAdapter(Context context )
	{
		mInflater = LayoutInflater.from(context);
		this.context = context;
	}
	
	public FriendListAdapter(Context context ,List<MsgUser> data)
	{
		mInflater = LayoutInflater.from(context);
		this.data = data;
		this.context = context;
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
		 ViewHolder viewHolder = null;	
	      if (convertView != null) {	    	
	    	  viewHolder = (ViewHolder) convertView.getTag();                  
	      } else {        
	    	  convertView = mInflater.inflate(R.layout.friend_item, null);
	    	  viewHolder = new ViewHolder();
			  viewHolder.nameText = (TextView) convertView.findViewById(R.id.friend_name);
			  viewHolder.headImage = (ImageView) convertView.findViewById(R.id.friend_head);
			  convertView.setTag(viewHolder);
	      } 
	      if(data.get(position).isOnline())
	      {
	    	  viewHolder.headImage.setImageResource(R.drawable.xiaohei);
	      }else
	      {
	    	  viewHolder.headImage.setImageBitmap(bitmap2Gray(context ,R.drawable.xiaohei));
	      }
	      viewHolder.nameText.setText(data.get(position).getUserName());
	      
//	      syncImageLoader.loadImage(position, data.get(position).get("image"), imageLoadListener);
	      return convertView;
		
	}
	
    static class ViewHolder { 
        public TextView nameText;
        public ImageView headImage;
    }
    
    /** 
     * 图片转灰度 
     *  
     * @param bmSrc 
     * @return 
     */  
    public static Bitmap bitmap2Gray(Context context,int resId)  
    {  
    	Bitmap bmSrc = BitmapFactory.decodeResource(context.getResources(), resId);
        int width, height;  
        height = bmSrc.getHeight();  
        width = bmSrc.getWidth();  
        Bitmap bmpGray = null;  
        bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);  
        Canvas c = new Canvas(bmpGray);  
        Paint paint = new Paint();  
        ColorMatrix cm = new ColorMatrix();  
        cm.setSaturation(0);  
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);  
        paint.setColorFilter(f);  
        c.drawBitmap(bmSrc, 0, 0, paint);  
      
        return bmpGray;  
    } 
}
