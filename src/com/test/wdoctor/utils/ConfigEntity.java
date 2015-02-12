package com.test.wdoctor.utils;

public class ConfigEntity {
	public static final int VIDEO_MODE_SERVERCONFIG = 0;	// 服务器视频参数配�?
	public static final int VIDEO_MODE_CUSTOMCONFIG = 1;	// 自定义视频参数配�?
	
	public static final int VIDEO_QUALITY_NORMAL = 2;		// 普�?视频质量
	public static final int VIDEO_QUALITY_GOOD = 3;			// 中等视频质量
	public static final int VIDEO_QUALITY_BEST = 4;			// 较好视频质量
	
	public boolean IsSaveNameAndPw;
	public String name = "";
	public String password = "";

	public String ip = "";
	public int port;
	
	public int configMode = VIDEO_MODE_SERVERCONFIG;
	public int resolution_width = 0;
	public int resolution_height = 0;
	
	public int videoBitrate = 150*1000;						// 本地视频码率
	public int videoFps = 10;								// 本地视频帧率
	public int videoQuality = VIDEO_QUALITY_GOOD;
	public int videoPreset = 1;
	public int videoOverlay = 1;							// 本地视频是否采用Overlay模式
	public int videorotatemode = 0;							// 本地视频旋转模式
	public int fixcolordeviation = 0;						// 修正本地视频采集偏色�? 关闭(默认）， 1 �?��
	public int videoShowGPURender = 0;						// 视频数据通过GPU直接渲染�?  关闭(默认)�?1 �?��
	public int videoAutoRotation = 1;						// 本地视频自动旋转控制（参数为int型， 0表示关闭�?1 �?��[默认]，视频旋转时�?��参�?本地视频设备方向参数�?
	
	public int enableP2P = 1;
	public int useARMv6Lib = 0;								// 是否强制使用ARMv6指令集，默认是内核自动判�?
	public int enableAEC = 1;								// 是否使用回音消除功能
	public int useHWCodec = 0;								// 是否使用平台内置硬件编解码器
}
