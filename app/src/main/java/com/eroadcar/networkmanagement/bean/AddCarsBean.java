package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

/**
 * 销售租赁添加车辆bean
 * 
 * @author amos
 * 
 */
public class AddCarsBean implements Serializable {
	private String yfs_id;// 销售订单id
	private String yfs_leasesq_code;// 销售订单code
	private String yfs_car_cartype;// 车辆类型
	private String yfs_car_carcolor;// 车身颜色
	private String yfs_car_carinsurance;// 保险
	private String yfs_car_remark;// 备注
	private String yfs_car_pricetype;// 0,定金；1，全款
	private String yfs_car_carprice;// 单价
	private String yfs_car_paytype;// 支付方式
	private String yfs_car_tichepoint;// 提车网点
	private String yfs_car_chargpostaddr;// 充电桩地址
	private String yfs_car_zlstart;// 开始日期
	private String yfs_car_zlend;// 结束日期
	private String yfs_car_tichepointid;
	private String yfs_car_preprice  ;// 定金
	private String yfs_car_vin;
	private String yfs_car_license  ;
	private String yfs_car_carvin;
	private String yfs_car_id;
	private String yfs_car_state;
	private String yfs_car_jctime;

	public String getYfs_car_jctime() {
		return yfs_car_jctime;
	}

	public void setYfs_car_jctime(String yfs_car_jctime) {
		this.yfs_car_jctime = yfs_car_jctime;
	}


	public String getYfs_car_state() {
		return yfs_car_state;
	}

	public void setYfs_car_state(String yfs_car_state) {
		this.yfs_car_state = yfs_car_state;
	}


	public String getYfs_car_id() {
		return yfs_car_id;
	}

	public void setYfs_car_id(String yfs_car_id) {
		this.yfs_car_id = yfs_car_id;
	}


	public String getYfs_car_carvin() {
		return yfs_car_carvin;
	}

	public void setYfs_car_carvin(String yfs_car_carvin) {
		this.yfs_car_carvin = yfs_car_carvin;
	}

	public String getYfs_car_carlicense() {
		return yfs_car_carlicense;
	}

	public void setYfs_car_carlicense(String yfs_car_carlicense) {
		this.yfs_car_carlicense = yfs_car_carlicense;
	}

	private String yfs_car_carlicense;

	public String getYfs_car_num() {
		return yfs_car_num;
	}

	public void setYfs_car_num(String yfs_car_num) {
		this.yfs_car_num = yfs_car_num;
	}

	private String yfs_car_num;

	public String getYfs_car_vin() {
		return yfs_car_vin;
	}

	public void setYfs_car_vin(String yfs_car_vin) {
		this.yfs_car_vin = yfs_car_vin;
	}

	public String getYfs_car_license() {
		return yfs_car_license;
	}

	public void setYfs_car_license(String yfs_car_license) {
		this.yfs_car_license = yfs_car_license;
	}


	public String getYfs_car_preprice() {
		return yfs_car_preprice;
	}

	public void setYfs_car_preprice(String yfs_car_preprice) {
		this.yfs_car_preprice = yfs_car_preprice;
	}

	public String getYfs_car_tichepointid() {
		return yfs_car_tichepointid;
	}

	public void setYfs_car_tichepointid(String yfs_car_tichepointid) {
		this.yfs_car_tichepointid = yfs_car_tichepointid;
	}

	public String getYfs_id() {
		return yfs_id;
	}

	public void setYfs_id(String yfs_id) {
		this.yfs_id = yfs_id;
	}

	public String getYfs_leasesq_code() {
		return yfs_leasesq_code;
	}

	public void setYfs_leasesq_code(String yfs_leasesq_code) {
		this.yfs_leasesq_code = yfs_leasesq_code;
	}

	public String getYfs_car_cartype() {
		return yfs_car_cartype;
	}

	public void setYfs_car_cartype(String yfs_car_cartype) {
		this.yfs_car_cartype = yfs_car_cartype;
	}

	public String getYfs_car_carcolor() {
		return yfs_car_carcolor;
	}

	public void setYfs_car_carcolor(String yfs_car_carcolor) {
		this.yfs_car_carcolor = yfs_car_carcolor;
	}

	public String getYfs_car_carinsurance() {
		return yfs_car_carinsurance;
	}

	public void setYfs_car_carinsurance(String yfs_car_carinsurance) {
		this.yfs_car_carinsurance = yfs_car_carinsurance;
	}

	public String getYfs_car_remark() {
		return yfs_car_remark;
	}

	public void setYfs_car_remark(String yfs_car_remark) {
		this.yfs_car_remark = yfs_car_remark;
	}

	public String getYfs_car_pricetype() {
		return yfs_car_pricetype;
	}

	public void setYfs_car_pricetype(String yfs_car_pricetype) {
		this.yfs_car_pricetype = yfs_car_pricetype;
	}

	public String getYfs_car_carprice() {
		return yfs_car_carprice;
	}

	public void setYfs_car_carprice(String yfs_car_carprice) {
		this.yfs_car_carprice = yfs_car_carprice;
	}

	public String getYfs_car_paytype() {
		return yfs_car_paytype;
	}

	public void setYfs_car_paytype(String yfs_car_paytype) {
		this.yfs_car_paytype = yfs_car_paytype;
	}

	public String getYfs_car_tichepoint() {
		return yfs_car_tichepoint;
	}

	public void setYfs_car_tichepoint(String yfs_car_tichepoint) {
		this.yfs_car_tichepoint = yfs_car_tichepoint;
	}

	public String getYfs_car_chargpostaddr() {
		return yfs_car_chargpostaddr;
	}

	public void setYfs_car_chargpostaddr(String yfs_car_chargpostaddr) {
		this.yfs_car_chargpostaddr = yfs_car_chargpostaddr;
	}

	public String getYfs_car_zlstart() {
		return yfs_car_zlstart;
	}

	public void setYfs_car_zlstart(String yfs_car_zlstart) {
		this.yfs_car_zlstart = yfs_car_zlstart;
	}

	public String getYfs_car_zlend() {
		return yfs_car_zlend;
	}

	public void setYfs_car_zlend(String yfs_car_zlend) {
		this.yfs_car_zlend = yfs_car_zlend;
	}

}
