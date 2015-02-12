
package com.test.wdoctor.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.wdoctor.R;
import com.test.wdoctor.model.ChatMsg;
import com.test.wdoctor.utils.HttpUtil;

public class ChatMsgViewAdapter extends BaseAdapter {
	
	public static interface IMsgViewType
	{
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}
	
    private List<ChatMsg> coll;

    
    private LayoutInflater mInflater;
    
    private File cache ;
    
    private String friendQQ;
    private String userId;

    public ChatMsgViewAdapter(Context context, List<ChatMsg> coll,String friendQQ,String userId) {
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
        this.friendQQ = friendQQ;
        this.userId = userId;
        cache = new File(Environment.getExternalStorageDirectory(), "cache");  
		 if(!cache.exists())
		 {
			 cache.mkdir();
		 }
    }
    
    public void addItem(ChatMsg chatMsg)
    {
    	coll.add(chatMsg);
    }
    public int getCount() {
        return coll.size();
    }

    public Object getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
    
    

	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
	 	ChatMsg entity = coll.get(position);
	 	
	 	if (entity.isComMeg())
	 	{
	 		return IMsgViewType.IMVT_COM_MSG;
	 	}else{
	 		return IMsgViewType.IMVT_TO_MSG;
	 	}
	 	
	}


	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	ChatMsg entity = coll.get(position);
    	boolean isComMsg = entity.isComMeg();
    		
    	ViewHolder viewHolder = null;	
	    if (convertView == null)
	    {
	    	  if (isComMsg)
			  {
				  convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
			  }else{
				  convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
			  }

	    	  viewHolder = new ViewHolder();
	    	  viewHolder.tvHeadImage = (ImageView) convertView.findViewById(R.id.iv_userhead);
			  viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			  viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
			  viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			  viewHolder.isComMsg = isComMsg;
			  
			  convertView.setTag(viewHolder);
	    }else{
	        viewHolder = (ViewHolder) convertView.getTag();
	    }
	
	    
	    viewHolder.tvSendTime.setText(entity.getDate());
	    viewHolder.tvUserName.setText(entity.getName());
	    viewHolder.tvContent.setText(entity.getText());
	    if (isComMsg)
		  {
	    	 asyncloadImage(viewHolder.tvHeadImage,friendQQ);
		  }else{
			  asyncloadImage(viewHolder.tvHeadImage,userId);
		  }
	   
	    
	    return convertView;
    }
    

    static class ViewHolder { 
    	public ImageView tvHeadImage;
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }
    
    private void asyncloadImage(ImageView iv_header, String userId) {
        AsyncImageTask task = new AsyncImageTask(userId, iv_header);
        task.execute();
    }

    private final class AsyncImageTask extends AsyncTask<String, Integer, File> {
        private ImageView iv_header;
        private String userId;

        public AsyncImageTask(String userId, ImageView iv_header) {
            this.userId = userId;
            this.iv_header = iv_header;
        }

        @Override
        protected File doInBackground(String... params) {
            try {            	 
                return HttpUtil.getImage("http://7vzmr3.com1.z0.glb.clouddn.com/head_"+userId+".jpg", cache);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(File result) {
            super.onPostExecute(result); 
            if (iv_header != null && result != null) {
            	iv_header.setImageBitmap(BitmapFactory.decodeFile(result.getAbsolutePath()));
            }
        }
    }


}
