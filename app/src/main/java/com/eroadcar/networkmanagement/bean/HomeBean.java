package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

/**
 * 首页模块bean
 * 
 * @author amos
 * 
 */
public class HomeBean implements Serializable {
	private String content;
	private String type;
	private int imageId;
	private int newsnum;

	public int getNewsnum() {
		return newsnum;
	}

	public void setNewsnum(int newsnum) {
		this.newsnum = newsnum;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}


}
