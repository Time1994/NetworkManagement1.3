package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

public class StatisticsBean implements Serializable {
    private String count;
    private String profit;
    private String xs_order_count;
    private String xs_order_price;
    private String zl_order_count;
    private String zl_order_price;
    private String wx_order_count;
    private String wx_order_price;
    private String qk_person_count;

    private String pm_sort_rank;//排名
    private String pm_order_price;// 总金额

    private String xs_shop_code;
    private String xs_shop_name;

    public String getXs_shop_code() {
        return xs_shop_code;
    }

    public void setXs_shop_code(String xs_shop_code) {
        this.xs_shop_code = xs_shop_code;
    }

    public String getXs_shop_name() {
        return xs_shop_name;
    }

    public void setXs_shop_name(String xs_shop_name) {
        this.xs_shop_name = xs_shop_name;
    }


    public String getPm_sort_rank() {
        return pm_sort_rank;
    }

    public void setPm_sort_rank(String pm_sort_rank) {
        this.pm_sort_rank = pm_sort_rank;
    }

    public String getPm_order_price() {
        return pm_order_price;
    }

    public void setPm_order_price(String pm_order_price) {
        this.pm_order_price = pm_order_price;
    }


    public String getXs_order_count() {
        return xs_order_count;
    }

    public void setXs_order_count(String xs_order_count) {
        this.xs_order_count = xs_order_count;
    }

    public String getXs_order_price() {
        return xs_order_price;
    }

    public void setXs_order_price(String xs_order_price) {
        this.xs_order_price = xs_order_price;
    }

    public String getZl_order_count() {
        return zl_order_count;
    }

    public void setZl_order_count(String zl_order_count) {
        this.zl_order_count = zl_order_count;
    }

    public String getZl_order_price() {
        return zl_order_price;
    }

    public void setZl_order_price(String zl_order_price) {
        this.zl_order_price = zl_order_price;
    }

    public String getWx_order_count() {
        return wx_order_count;
    }

    public void setWx_order_count(String wx_order_count) {
        this.wx_order_count = wx_order_count;
    }

    public String getWx_order_price() {
        return wx_order_price;
    }

    public void setWx_order_price(String wx_order_price) {
        this.wx_order_price = wx_order_price;
    }

    public String getQk_person_count() {
        return qk_person_count;
    }

    public void setQk_person_count(String qk_person_count) {
        this.qk_person_count = qk_person_count;
    }


    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


}
