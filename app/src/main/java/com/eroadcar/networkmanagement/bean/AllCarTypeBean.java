package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 车辆型号bean
 * 
 * @author amos
 * 
 */
public class AllCarTypeBean implements Serializable {
	private ArrayList<CarTypeBean> cartype;

	public ArrayList<CarTypeBean> getCartype() {
		return cartype;
	}

	public void setCartype(ArrayList<CarTypeBean> cartype) {
		this.cartype = cartype;
	}
}
