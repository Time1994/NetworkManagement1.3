package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

/**
 * bean
 * 
 * @author amos
 * 
 */
public class GeneralBean implements Serializable {
	private String yfs_id;
	private String yfs_sellsq_code;

	public String getYfs_id() {
		return yfs_id;
	}

	public void setYfs_id(String yfs_id) {
		this.yfs_id = yfs_id;
	}

	public String getYfs_sellsq_code() {
		return yfs_sellsq_code;
	}

	public void setYfs_sellsq_code(String yfs_sellsq_code) {
		this.yfs_sellsq_code = yfs_sellsq_code;
	}
}
