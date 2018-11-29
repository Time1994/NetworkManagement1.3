package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

/**
 * 注册bean
 * 
 * @author amos
 * 
 */
public class EmployeeBean implements Serializable {
	private String mg_id;
	private String mg_shopsid;
	private String mg_shopname;
	private String mg_code;
	private String mg_cardid;
	private String mg_cellphone;
	private String mg_sex;
	private String mg_entry_date;
	private String mg_name;
	private String mg_lastlg_date;
	private String st_except_num;
	private String st_finish_num;
	private String mg_role_ids;
	private String mg_isonline;
	private String is_jobing;
	private String st_expect_zlnum;
	private String st_expect_xsnum;
	private String st_finish_zlnum;
	private String st_finish_xsnum;

	private boolean isGoodsChoosed;
	private boolean selectedOr = false ;

	public void toggle() {
		this.selectedOr = !this.selectedOr;
	}

	public boolean isSelectedOr() {
		return selectedOr;
	}

	public void setSelectedOr(boolean selectedOr) {
		this.selectedOr = selectedOr;
	}

	public boolean isGoodsChoosed() {
		return isGoodsChoosed;
	}

	public void setGoodsChoosed(boolean goodsChoosed) {
		isGoodsChoosed = goodsChoosed;
	}


	public String getSt_expect_zlnum() {
		return st_expect_zlnum;
	}

	public void setSt_expect_zlnum(String st_expect_zlnum) {
		this.st_expect_zlnum = st_expect_zlnum;
	}

	public String getSt_expect_xsnum() {
		return st_expect_xsnum;
	}

	public void setSt_expect_xsnum(String st_expect_xsnum) {
		this.st_expect_xsnum = st_expect_xsnum;
	}

	public String getSt_finish_zlnum() {
		return st_finish_zlnum;
	}

	public void setSt_finish_zlnum(String st_finish_zlnum) {
		this.st_finish_zlnum = st_finish_zlnum;
	}

	public String getSt_finish_xsnum() {
		return st_finish_xsnum;
	}

	public void setSt_finish_xsnum(String st_finish_xsnum) {
		this.st_finish_xsnum = st_finish_xsnum;
	}


	public String getIs_jobing() {
		return is_jobing;
	}

	public void setIs_jobing(String is_jobing) {
		this.is_jobing = is_jobing;
	}


	public String getMg_role_ids() {
		return mg_role_ids;
	}

	public void setMg_role_ids(String mg_role_ids) {
		this.mg_role_ids = mg_role_ids;
	}

	public String getMg_isonline() {
		return mg_isonline;
	}

	public void setMg_isonline(String mg_isonline) {
		this.mg_isonline = mg_isonline;
	}


	public String getMg_id() {
		return mg_id;
	}

	public void setMg_id(String mg_id) {
		this.mg_id = mg_id;
	}

	public String getMg_shopsid() {
		return mg_shopsid;
	}

	public void setMg_shopsid(String mg_shopsid) {
		this.mg_shopsid = mg_shopsid;
	}

	public String getMg_shopname() {
		return mg_shopname;
	}

	public void setMg_shopname(String mg_shopname) {
		this.mg_shopname = mg_shopname;
	}

	public String getMg_code() {
		return mg_code;
	}

	public void setMg_code(String mg_code) {
		this.mg_code = mg_code;
	}

	public String getMg_cardid() {
		return mg_cardid;
	}

	public void setMg_cardid(String mg_cardid) {
		this.mg_cardid = mg_cardid;
	}

	public String getMg_cellphone() {
		return mg_cellphone;
	}

	public void setMg_cellphone(String mg_cellphone) {
		this.mg_cellphone = mg_cellphone;
	}

	public String getMg_sex() {
		return mg_sex;
	}

	public void setMg_sex(String mg_sex) {
		this.mg_sex = mg_sex;
	}

	public String getMg_entry_date() {
		return mg_entry_date;
	}

	public void setMg_entry_date(String mg_entry_date) {
		this.mg_entry_date = mg_entry_date;
	}

	public String getMg_name() {
		return mg_name;
	}

	public void setMg_name(String mg_name) {
		this.mg_name = mg_name;
	}

	public String getMg_lastlg_date() {
		return mg_lastlg_date;
	}

	public void setMg_lastlg_date(String mg_lastlg_date) {
		this.mg_lastlg_date = mg_lastlg_date;
	}

	public String getSt_except_num() {
		return st_except_num;
	}

	public void setSt_except_num(String st_except_num) {
		this.st_except_num = st_except_num;
	}

	public String getSt_finish_num() {
		return st_finish_num;
	}

	public void setSt_finish_num(String st_finish_num) {
		this.st_finish_num = st_finish_num;
	}

}
