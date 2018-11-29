package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

/**
 * 注册bean
 * 
 * @author amos
 * 
 */
public class UserBean implements Serializable {
	private String mg_id;
	private String mg_name;
	private String mg_shopsid;
	private String mg_groupid;
	private String mg_shopname;
	private String mg_code;
	private String mg_groupname;
	private String mg_posid;
	private String mg_posname;
	private String pmg_points_type;
	private String pmg_points_ywtype;
	private String pmg_points_functions;
	private String mg_isboss;
	private String mg_shopcode;
	private String mg_invitation_code;
	private String mg_invitation_fcode;

	private String ljd_worker_name;
	private String count;
	private String ljd_create_time;
	private String ljd_context;
	private String ljd_order_code;
	private String ljd_worker_id;
	private String ljd_lujiu_state;
	
	private String pmg_points_province;

	public String getMg_cellphone() {
		return mg_cellphone;
	}

	public void setMg_cellphone(String mg_cellphone) {
		this.mg_cellphone = mg_cellphone;
	}

	private String mg_cellphone;

	public String getMg_role_ids() {
		return mg_role_ids;
	}

	public void setMg_role_ids(String mg_role_ids) {
		this.mg_role_ids = mg_role_ids;
	}

	private String mg_role_ids;

	public String getPmg_points_province() {
		return pmg_points_province;
	}

	public void setPmg_points_province(String pmg_points_province) {
		this.pmg_points_province = pmg_points_province;
	}

	public String getLjd_lujiu_state() {
		return ljd_lujiu_state;
	}

	public void setLjd_lujiu_state(String ljd_lujiu_state) {
		this.ljd_lujiu_state = ljd_lujiu_state;
	}

	public String getLjd_worker_id() {
		return ljd_worker_id;
	}

	public void setLjd_worker_id(String ljd_worker_id) {
		this.ljd_worker_id = ljd_worker_id;
	}

	public String getLjd_context() {
		return ljd_context;
	}

	public void setLjd_context(String ljd_context) {
		this.ljd_context = ljd_context;
	}

	public String getLjd_order_code() {
		return ljd_order_code;
	}

	public void setLjd_order_code(String ljd_order_code) {
		this.ljd_order_code = ljd_order_code;
	}

	public String getLjd_create_time() {
		return ljd_create_time;
	}

	public void setLjd_create_time(String ljd_create_time) {
		this.ljd_create_time = ljd_create_time;
	}

	public String getLjd_worker_name() {
		return ljd_worker_name;
	}

	public void setLjd_worker_name(String ljd_worker_name) {
		this.ljd_worker_name = ljd_worker_name;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getMg_invitation_code() {
		if (mg_invitation_code == null) {
			mg_invitation_code = "";
		}
		return mg_invitation_code;
	}

	public void setMg_invitation_code(String mg_invitation_code) {
		this.mg_invitation_code = mg_invitation_code;
	}

	public String getMg_invitation_fcode() {
		if (mg_invitation_fcode == null) {
			mg_invitation_fcode = "";
		}
		return mg_invitation_fcode;
	}

	public void setMg_invitation_fcode(String mg_invitation_fcode) {
		this.mg_invitation_fcode = mg_invitation_fcode;
	}

	public String getMg_id() {
		if (mg_id == null) {
			mg_id = "";
		}
		return mg_id;
	}

	public void setMg_id(String mg_id) {
		this.mg_id = mg_id;
	}

	public String getMg_name() {
		if (mg_name == null) {
			mg_name = "";
		}
		return mg_name;
	}

	public void setMg_name(String mg_name) {
		this.mg_name = mg_name;
	}

	public String getMg_shopsid() {
		if (mg_shopsid == null) {
			mg_shopsid = "";
		}
		return mg_shopsid;
	}

	public void setMg_shopsid(String mg_shopsid) {
		this.mg_shopsid = mg_shopsid;
	}

	public String getMg_groupid() {
		if (mg_groupid == null) {
			mg_groupid = "";
		}
		return mg_groupid;
	}

	public void setMg_groupid(String mg_groupid) {
		this.mg_groupid = mg_groupid;
	}

	public String getMg_shopname() {
		if (mg_shopname == null) {
			mg_shopname = "";
		}
		return mg_shopname;
	}

	public void setMg_shopname(String mg_shopname) {
		this.mg_shopname = mg_shopname;
	}

	public String getMg_code() {
		if (mg_code == null) {
			mg_code = "";
		}
		return mg_code;
	}

	public void setMg_code(String mg_code) {
		this.mg_code = mg_code;
	}

	public String getMg_groupname() {
		if (mg_groupname == null) {
			mg_groupname = "";
		}
		return mg_groupname;
	}

	public void setMg_groupname(String mg_groupname) {
		this.mg_groupname = mg_groupname;
	}

	public String getMg_posid() {
		if (mg_posid == null) {
			mg_posid = "";
		}
		return mg_posid;
	}

	public void setMg_posid(String mg_posid) {
		this.mg_posid = mg_posid;
	}

	public String getMg_posname() {
		if (mg_posname == null) {
			mg_posname = "";
		}
		return mg_posname;
	}

	public void setMg_posname(String mg_posname) {
		this.mg_posname = mg_posname;
	}

	public String getPmg_points_type() {
		if (pmg_points_type == null) {
			pmg_points_type = "";
		}
		return pmg_points_type;
	}

	public void setPmg_points_type(String pmg_points_type) {
		this.pmg_points_type = pmg_points_type;
	}

	public String getPmg_points_ywtype() {
		if (pmg_points_ywtype == null) {
			pmg_points_ywtype = "";
		}
		return pmg_points_ywtype;
	}

	public void setPmg_points_ywtype(String pmg_points_ywtype) {
		this.pmg_points_ywtype = pmg_points_ywtype;
	}

	public String getPmg_points_functions() {
		if (pmg_points_functions == null) {
			pmg_points_functions = "";
		}
		return pmg_points_functions;
	}

	public void setPmg_points_functions(String pmg_points_functions) {
		this.pmg_points_functions = pmg_points_functions;
	}

	public String getMg_isboss() {
		if (mg_isboss == null) {
			mg_isboss = "";
		}
		return mg_isboss;
	}

	public void setMg_isboss(String mg_isboss) {
		this.mg_isboss = mg_isboss;
	}

	public String getMg_shopcode() {
		if (mg_shopcode == null) {
			mg_shopcode = "";
		}
		return mg_shopcode;
	}

	public void setMg_shopcode(String mg_shopcode) {
		this.mg_shopcode = mg_shopcode;
	}

}
