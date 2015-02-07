package com.test.wdoctor.utils;

import android.graphics.Color;

/**
 * 
 * @author 胡海亮  qq：249782944
 * 项目：jinyaostarQQ
 * 时间：2009-8-5下午09:11:59
 *
 */
public class FontAttrib {

	public static final int GENERAL = 0; // 常规

	  public static final int BOLD = 1; // 粗体

	  public static final int ITALIC = 2; // 斜体

	  public static final int BOLD_ITALIC = 3; // 粗斜体

	  private String text = null, name = null; // 要输入的文本和字体名称

	  private int style = 0, size = 0; // 样式和字号

	  private Color color = null, backColor = null; // 文字颜色和背景颜色

	  /**
	   * 一个空的构造（可当做换行使用）
	   */
	  public FontAttrib() {
		  
	  }



	  /* 后面的注释就不写了，一看就明白 */

	  public String getText() {
	   return text;
	  }

	  public void setText(String text) {
	   this.text = text;
	  }

	  public Color getColor() {
	   return color;
	  }

	  public void setColor(Color color) {
	   this.color = color;
	  }

	  public Color getBackColor() {
	   return backColor;
	  }

	  public void setBackColor(Color backColor) {
	   this.backColor = backColor;
	  }

	  public String getName() {
	   return name;
	  }

	  public void setName(String name) {
	   this.name = name;
	  }

	  public int getSize() {
	   return size;
	  }

	  public void setSize(int size) {
	   this.size = size;
	  }

	  public int getStyle() {
	   return style;
	  }

	  public void setStyle(int style) {
	   this.style = style;
	  }
}
