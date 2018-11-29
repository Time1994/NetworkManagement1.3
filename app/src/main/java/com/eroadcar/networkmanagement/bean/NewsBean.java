package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

/**
 * 消息bean
 * 
 * @author amos
 * 
 */
public class NewsBean implements Serializable {
	private String ic_id;
	private String ic_shopsid;
	private String ic_info_type;
	private String ic_info_role;
	private String ic_info_state;
	private String ic_user_code;
	private String ic_user_name;
	private String ic_app_idregisterid;
	private String ic_app_pushtext;
	private String ic_app_pushdate;
	private String ic_read_state;

	public String getIc_id() {
		return ic_id;
	}

	public void setIc_id(String ic_id) {
		this.ic_id = ic_id;
	}

	public String getIc_shopsid() {
		return ic_shopsid;
	}

	public void setIc_shopsid(String ic_shopsid) {
		this.ic_shopsid = ic_shopsid;
	}

	public String getIc_info_type() {
		return ic_info_type;
	}

	public void setIc_info_type(String ic_info_type) {
		this.ic_info_type = ic_info_type;
	}

	public String getIc_info_role() {
		return ic_info_role;
	}

	public void setIc_info_role(String ic_info_role) {
		this.ic_info_role = ic_info_role;
	}

	public String getIc_info_state() {
		return ic_info_state;
	}

	public void setIc_info_state(String ic_info_state) {
		this.ic_info_state = ic_info_state;
	}

	public String getIc_user_code() {
		return ic_user_code;
	}

	public void setIc_user_code(String ic_user_code) {
		this.ic_user_code = ic_user_code;
	}

	public String getIc_user_name() {
		return ic_user_name;
	}

	public void setIc_user_name(String ic_user_name) {
		this.ic_user_name = ic_user_name;
	}

	public String getIc_app_idregisterid() {
		return ic_app_idregisterid;
	}

	public void setIc_app_idregisterid(String ic_app_idregisterid) {
		this.ic_app_idregisterid = ic_app_idregisterid;
	}

	public String getIc_app_pushtext() {
		return ic_app_pushtext;
	}

	public void setIc_app_pushtext(String ic_app_pushtext) {
		this.ic_app_pushtext = ic_app_pushtext;
	}

	public String getIc_app_pushdate() {
		return ic_app_pushdate;
	}

	public void setIc_app_pushdate(String ic_app_pushdate) {
		this.ic_app_pushdate = ic_app_pushdate;
	}

	public String getIc_read_state() {
		return ic_read_state;
	}

	public void setIc_read_state(String ic_read_state) {
		this.ic_read_state = ic_read_state;
	}


}
