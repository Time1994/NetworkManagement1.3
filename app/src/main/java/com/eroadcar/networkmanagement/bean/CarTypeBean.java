package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 车辆型号bean
 * 
 * @author amos
 * 
 */
public class CarTypeBean implements Serializable {
	private String ct_id;
	private String ct_shopid;
	private String ct_car_type;
	private String ct_car_remark;
	private String ct_car_price;
	private String ct_car_ywtype;
	private String ct_car_typeno;
	private String ct_car_xinghao;
	private String ct_car_color;
	
	private ArrayList<CarColorBean> color;

	public ArrayList<CarColorBean> getColor() {
		return color;
	}

	public void setColor(ArrayList<CarColorBean> color) {
		this.color = color;
	}

	public String getCt_car_typeno() {
		return ct_car_typeno;
	}

	public void setCt_car_typeno(String ct_car_typeno) {
		this.ct_car_typeno = ct_car_typeno;
	}

	public String getCt_car_xinghao() {
		return ct_car_xinghao;
	}

	public void setCt_car_xinghao(String ct_car_xinghao) {
		this.ct_car_xinghao = ct_car_xinghao;
	}

	public String getCt_car_color() {
		return ct_car_color;
	}

	public void setCt_car_color(String ct_car_color) {
		this.ct_car_color = ct_car_color;
	}

	public String getCt_car_ywtype() {
		return ct_car_ywtype;
	}

	public void setCt_car_ywtype(String ct_car_ywtype) {
		this.ct_car_ywtype = ct_car_ywtype;
	}

	public String getCt_car_price() {
		return ct_car_price;
	}

	public void setCt_car_price(String ct_car_price) {
		this.ct_car_price = ct_car_price;
	}

	public String getCt_id() {
		return ct_id;
	}

	public void setCt_id(String ct_id) {
		this.ct_id = ct_id;
	}

	public String getCt_shopid() {
		return ct_shopid;
	}

	public void setCt_shopid(String ct_shopid) {
		this.ct_shopid = ct_shopid;
	}

	public String getCt_car_type() {
		return ct_car_type;
	}

	public void setCt_car_type(String ct_car_type) {
		this.ct_car_type = ct_car_type;
	}

	public String getCt_car_remark() {
		return ct_car_remark;
	}

	public void setCt_car_remark(String ct_car_remark) {
		this.ct_car_remark = ct_car_remark;
	}
}
