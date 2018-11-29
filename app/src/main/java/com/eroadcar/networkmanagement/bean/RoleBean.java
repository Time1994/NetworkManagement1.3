package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

/**
 * 注册bean
 * 
 * @author amos
 * 
 */
public class RoleBean implements Serializable {
	private String role_app_id;
	private String role_app_shopid;
	private String role_app_shopcode;
	private String role_app_shoptype;
	private String role_app_name;
	private String role_app_auth_ids;
	private String role_app_auth_ac;
	private String role_app_level;
	private String role_app_path;

	public String getRole_app_id() {
		return role_app_id;
	}

	public void setRole_app_id(String role_app_id) {
		this.role_app_id = role_app_id;
	}

	public String getRole_app_shopid() {
		return role_app_shopid;
	}

	public void setRole_app_shopid(String role_app_shopid) {
		this.role_app_shopid = role_app_shopid;
	}

	public String getRole_app_shopcode() {
		return role_app_shopcode;
	}

	public void setRole_app_shopcode(String role_app_shopcode) {
		this.role_app_shopcode = role_app_shopcode;
	}

	public String getRole_app_shoptype() {
		return role_app_shoptype;
	}

	public void setRole_app_shoptype(String role_app_shoptype) {
		this.role_app_shoptype = role_app_shoptype;
	}

	public String getRole_app_name() {
		return role_app_name;
	}

	public void setRole_app_name(String role_app_name) {
		this.role_app_name = role_app_name;
	}

	public String getRole_app_auth_ids() {
		return role_app_auth_ids;
	}

	public void setRole_app_auth_ids(String role_app_auth_ids) {
		this.role_app_auth_ids = role_app_auth_ids;
	}

	public String getRole_app_auth_ac() {
		return role_app_auth_ac;
	}

	public void setRole_app_auth_ac(String role_app_auth_ac) {
		this.role_app_auth_ac = role_app_auth_ac;
	}

	public String getRole_app_level() {
		return role_app_level;
	}

	public void setRole_app_level(String role_app_level) {
		this.role_app_level = role_app_level;
	}

	public String getRole_app_path() {
		return role_app_path;
	}

	public void setRole_app_path(String role_app_path) {
		this.role_app_path = role_app_path;
	}


}
