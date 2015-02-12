package com.test.wdoctor.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.wdoctor.R;
import com.test.wdoctor.model.MsgUser;
import com.test.wdoctor.utils.HttpUtil;

public class FriendListAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	
	private File cache ;
	
	List<MsgUser> data ;
	
	public FriendListAdapter(Context context )
	{
		this(context,new ArrayList<MsgUser>());
	}
	
	public FriendListAdapter(Context context ,List<MsgUser> data)
	{
		mInflater = LayoutInflater.from(context);
		this.data = data;
		 cache = new File(Environment.getExternalStorageDirectory(), "cache");  
		 if(!cache.exists())
		 {
			 cache.mkdir();
		 }
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
	      viewHolder.nameText.setText(data.get(position).getUserName());
	      
	      asyncloadImage(viewHolder.headImage, data.get(position)); 
	      
//	      syncImageLoader.loadImage(position, data.get(position).get("image"), imageLoadListener);
	      return convertView;
		
	}
	
    static class ViewHolder { 
        public TextView nameText;
        public ImageView headImage;
    }
    
    private void asyncloadImage(ImageView iv_header, MsgUser msgUser) {
        AsyncImageTask task = new AsyncImageTask(msgUser, iv_header);
        task.execute();
    }

    private final class AsyncImageTask extends AsyncTask<String, Integer, File> {
        private ImageView iv_header;
        private MsgUser msgUser;

        public AsyncImageTask(MsgUser msgUser, ImageView iv_header) {
            this.msgUser = msgUser;
            this.iv_header = iv_header;
        }

        @Override
        protected File doInBackground(String... params) {
            try {            	 
                return HttpUtil.getImage("http://7vzmr3.com1.z0.glb.clouddn.com/head_"+msgUser.getUserID()+".jpg", cache);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(File result) {
            super.onPostExecute(result); 
            if (iv_header != null && result != null) {
            	if(msgUser.isOnline())
      	      {
            		iv_header.setImageBitmap(BitmapFactory.decodeFile(result.getAbsolutePath()));
      	      }else
      	      {
      	    	  iv_header.setImageBitmap(bitmap2Gray(result.getAbsolutePath()));
      	      }
            }
        }
    }
    
    /** 
     * 图片转灰度 
     *  
     * @param bmSrc 
     * @return 
     */  
    public static Bitmap bitmap2Gray(String pathName)  
    {  
//    	Bitmap bmSrc = BitmapFactory.decodeResource(context.getResources(), resId);
    	Bitmap bmSrc = BitmapFactory.decodeFile(pathName);
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
