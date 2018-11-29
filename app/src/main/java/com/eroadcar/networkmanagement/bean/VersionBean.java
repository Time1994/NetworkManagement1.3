package com.eroadcar.networkmanagement.bean;

import java.io.Serializable;

public class VersionBean implements Serializable {
    private String au_id;
    private String au_appname;
    private String au_appversion;
    private String au_appurl;
    private String au_appcontext;
    private String au_appupdate;
    private String versionName;
    private String versionCode;
    private String apkLink;
    private String remark;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getApkLink() {
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getAu_id() {
        return au_id;
    }

    public void setAu_id(String au_id) {
        this.au_id = au_id;
    }

    public String getAu_appname() {
        return au_appname;
    }

    public void setAu_appname(String au_appname) {
        this.au_appname = au_appname;
    }

    public String getAu_appversion() {
        return au_appversion;
    }

    public void setAu_appversion(String au_appversion) {
        this.au_appversion = au_appversion;
    }

    public String getAu_appurl() {
        return au_appurl;
    }

    public void setAu_appurl(String au_appurl) {
        this.au_appurl = au_appurl;
    }

    public String getAu_appcontext() {
        return au_appcontext;
    }

    public void setAu_appcontext(String au_appcontext) {
        this.au_appcontext = au_appcontext;
    }

    public String getAu_appupdate() {
        return au_appupdate;
    }

    public void setAu_appupdate(String au_appupdate) {
        this.au_appupdate = au_appupdate;
    }

}
