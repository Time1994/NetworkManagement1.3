package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

/**
 * 车辆型号bean
 * 
 * @author amos
 * 
 */
public class CarImageBean implements Serializable {
	private String ci_class;
	private String ci_car_img;

	public String getCi_class() {
		return ci_class;
	}

	public void setCi_class(String ci_class) {
		this.ci_class = ci_class;
	}

	public String getCi_car_img() {
		return ci_car_img;
	}

	public void setCi_car_img(String ci_car_img) {
		this.ci_car_img = ci_car_img;
	}
}
