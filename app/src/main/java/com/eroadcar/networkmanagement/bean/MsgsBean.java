package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

public class MsgsBean implements Serializable {
    private Sell sell;

    public Sell getSell() {
        return sell;
    }

    public void setSell(Sell sell) {
        this.sell = sell;
    }

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    private Lease lease;


    public class Sell implements Serializable {
        private String total = "0";
        private String zxz = "0";
        private String zxtg = "0";
        private String zxbh = "0";
        private String dsdj = "0";
        private String zsdj = "0";
        private String yhhcdz = "0";
        private String dsqk = "0";
        private String ysqk = "0";
        private String ywc = "0";
        private String ybqzl = "0";

        public String getYbqzl() {
            return ybqzl;
        }

        public void setYbqzl(String ybqzl) {
            this.ybqzl = ybqzl;
        }


        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getZxz() {
            return zxz;
        }

        public void setZxz(String zxz) {
            this.zxz = zxz;
        }

        public String getZxtg() {
            return zxtg;
        }

        public void setZxtg(String zxtg) {
            this.zxtg = zxtg;
        }

        public String getZxbh() {
            return zxbh;
        }

        public void setZxbh(String zxbh) {
            this.zxbh = zxbh;
        }

        public String getDsdj() {
            return dsdj;
        }

        public void setDsdj(String dsdj) {
            this.dsdj = dsdj;
        }

        public String getZsdj() {
            return zsdj;
        }

        public void setZsdj(String zsdj) {
            this.zsdj = zsdj;
        }

        public String getYhhcdz() {
            return yhhcdz;
        }

        public void setYhhcdz(String yhhcdz) {
            this.yhhcdz = yhhcdz;
        }

        public String getDsqk() {
            return dsqk;
        }

        public void setDsqk(String dsqk) {
            this.dsqk = dsqk;
        }

        public String getYsqk() {
            return ysqk;
        }

        public void setYsqk(String ysqk) {
            this.ysqk = ysqk;
        }

        public String getYwc() {
            return ywc;
        }

        public void setYwc(String ywc) {
            this.ywc = ywc;
        }

    }

    public class Lease implements Serializable {
        private String total = "0";
        private String sqz = "0";
        private String yjc = "0";
        private String ywc = "0";

        public String getYjc() {
            return yjc;
        }

        public void setYjc(String yjc) {
            this.yjc = yjc;
        }

        public String getYwc() {
            return ywc;
        }

        public void setYwc(String ywc) {
            this.ywc = ywc;
        }


        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getSqz() {
            return sqz;
        }

        public void setSqz(String sqz) {
            this.sqz = sqz;
        }

    }
}
