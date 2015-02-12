package com.test.wdoctor.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.net.Uri;
import android.util.Log;

public class HttpUtil {

	 /*
     * 从网络上获取图片，如果图片在本地存在的话就直接拿，如果不存在再去服务器上下载图片
     * 这里的path是图片的地址
     */
    public static File getImage(String path, File cache) throws Exception { 
    	String name = null ;
    	if (path!=null && path.contains("/")) {
    		name = path.substring(path.lastIndexOf("/"));  //+1
    		
    		File file = new File(cache, name);
            Log.d("wonderdemo","getImageURI="+ path + ", local_file=" + file.getAbsolutePath());
            // 如果图片存在本地缓存目录，则不去服务器下载 
            if (file.exists()) {
                return file;//Uri.fromFile(path)这个方法能得到文件的URI
            } else {
                // 从网络上获取图片
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                if (conn.getResponseCode() == 200) {

                    InputStream is = conn.getInputStream();
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    is.close();
                    fos.close();
                    return file;
                }
            }
    	}        
        return null;
    }
}
