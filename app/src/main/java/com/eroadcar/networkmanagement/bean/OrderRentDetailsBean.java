package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 类别bean
 *
 * @author amos
 */
public class OrderRentDetailsBean implements Serializable {
    private OrderRentBean custormer;
    private ArrayList<AddCarsBean> cars;

    public OrderRentBean getCustormer() {
        return custormer;
    }

    public void setCustormer(OrderRentBean custormer) {
        this.custormer = custormer;
    }

    public ArrayList<AddCarsBean> getCars() {
        return cars;
    }

    public void setCars(ArrayList<AddCarsBean> cars) {
        this.cars = cars;
    }

}
