package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

/**
 * 注册bean
 * 
 * @author amos
 * 
 */
public class RegistBean implements Serializable {
	private String name;// ----用户名称
	private String gender;// ----用户性别
	private String idCard;// ----用户身份证
	private String lzrq;// ----驾驶证领证时间
	private String license_type;// ----驾驶证类型
	private String license;// ----驾驶证号码
	private String paid;// ----支付方式
	private String phone;//
	private String state;//
	private String msg;
	private String pw;
	private String userSta;
	private String qiyeCode;
	private String id;
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQiyeCode() {
		return qiyeCode;
	}

	public void setQiyeCode(String qiyeCode) {
		this.qiyeCode = qiyeCode;
	}

	public String getUserSta() {
		return userSta;
	}

	public void setUserSta(String userSta) {
		this.userSta = userSta;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getLzrq() {
		return lzrq;
	}

	public void setLzrq(String lzrq) {
		this.lzrq = lzrq;
	}

	public String getLicense_type() {
		return license_type;
	}

	public void setLicense_type(String license_type) {
		this.license_type = license_type;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
