package com.test.wdoctor.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import com.test.wdoctor.network.messageProcess.config.MessageProcessBean;
import com.test.wdoctor.network.messageProcess.config.MessageProcessConfig;
import com.thoughtworks.xstream.XStream;


public class ClientUtils {

	public static MessageProcessConfig getMessageProcessConfig(){
		MessageProcessConfig config=new MessageProcessConfig();
		XStream xstream =getProcessConfigXStream();
//		File file = getFile(MessageProcessConfig.class,"messageProcessConfig.xml");
		try {
			InputStream xml=MessageProcessConfig.class.getResourceAsStream("messageProcessConfig.xml");
			xstream.fromXML(xml, config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return config;
	}

	public static boolean writeProcessConfig(MessageProcessConfig processConfig) {
		XStream xstream =getProcessConfigXStream();
		String str=xstream.toXML(processConfig);
		File file = getFile(MessageProcessConfig.class.getPackage(),"messageProcessConfig.xml");
		try {
			write(file,str);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public static XStream getProcessConfigXStream(){
		XStream xstream = new XStream();
		xstream.alias("ServerProcess", MessageProcessConfig.class);
		xstream.alias("Process", MessageProcessBean.class);
		xstream.addImplicitCollection(MessageProcessConfig.class, "processBeans");
		xstream.useAttributeFor( MessageProcessBean.class,"processClass");
		return xstream;
	}
	
	public static File getFile(Package dirPackage, String fileName) {
		String filePackage = "src/" + dirPackage.getName().replace(".", "/")
				+ "/";
		File file = new File(filePackage, fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public static String write(File file, String xml, String charset)
	throws IOException {
		FileOutputStream out = null;
		out = new FileOutputStream(file);
		OutputStreamWriter write = new OutputStreamWriter(out, charset);
		BufferedWriter bw = new BufferedWriter(write);
		bw.write(xml);
		bw.flush();
		bw.close();
	return xml;
	}
	
	public static String write(File file, String xml) throws IOException {
		write(file, xml, "UTF-8");
		return xml;
	}
}
