package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 类别bean
 *
 * @author amos
 */
public class OrderDetailsBean implements Serializable {
    private OrderSalesBean custormer;
    private ArrayList<CarBean> cars;

    public OrderSalesBean getCustormer() {
        return custormer;
    }

    public void setCustormer(OrderSalesBean custormer) {
        this.custormer = custormer;
    }

    public ArrayList<CarBean> getCars() {
        return cars;
    }

    public void setCars(ArrayList<CarBean> cars) {
        this.cars = cars;
    }

}
