package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 注册bean
 * 
 * @author amos
 * 
 */
public class StoreBean implements Serializable {
	private String pmg_points_id;
	private String pmg_points_code;
	private String pmg_points_name;
	private ArrayList<EmployeeBean> wks;

	private boolean selectedOr = false;

	public boolean isSelectedOr() {
		return selectedOr;
	}

	public void setSelectedOr(boolean selectedOr) {
		this.selectedOr = selectedOr;
	}


	public String getPmg_points_id() {
		return pmg_points_id;
	}

	public void setPmg_points_id(String pmg_points_id) {
		this.pmg_points_id = pmg_points_id;
	}

	public String getPmg_points_code() {
		return pmg_points_code;
	}

	public void setPmg_points_code(String pmg_points_code) {
		this.pmg_points_code = pmg_points_code;
	}

	public String getPmg_points_name() {
		return pmg_points_name;
	}

	public void setPmg_points_name(String pmg_points_name) {
		this.pmg_points_name = pmg_points_name;
	}

	public ArrayList<EmployeeBean> getWks() {
		return wks;
	}

	public void setWks(ArrayList<EmployeeBean> wks) {
		this.wks = wks;
	}

	public void toggle() {
		this.selectedOr = !this.selectedOr;
	}

	public int getChildCount() {
		return wks.size();
	}

	public EmployeeBean getChildItem(int index) {
		return wks.get(index);
	}
}
