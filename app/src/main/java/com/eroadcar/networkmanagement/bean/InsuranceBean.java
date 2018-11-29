package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

/**
 * 保险险种bean
 * 
 * @author amos
 * 
 */
public class InsuranceBean implements Serializable {
	private String it_id;
	private String it_shopsid;
	private String it_insurance_type;
	private String it_insurance_remark;

	public String getIt_id() {
		return it_id;
	}

	public void setIt_id(String it_id) {
		this.it_id = it_id;
	}

	public String getIt_shopsid() {
		return it_shopsid;
	}

	public void setIt_shopsid(String it_shopsid) {
		this.it_shopsid = it_shopsid;
	}

	public String getIt_insurance_type() {
		return it_insurance_type;
	}

	public void setIt_insurance_type(String it_insurance_type) {
		this.it_insurance_type = it_insurance_type;
	}

	public String getIt_insurance_remark() {
		return it_insurance_remark;
	}

	public void setIt_insurance_remark(String it_insurance_remark) {
		this.it_insurance_remark = it_insurance_remark;
	}
}
