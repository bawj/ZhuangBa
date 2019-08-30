package com.xiaomai.zhuangba.data;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2019/8/30 0030
 */
public class AdvertisingBillsBean implements Serializable {

    /**
     * province : 北京市
     * city : 北京市
     * district : 东城区
     * street : 朝阳门街道
     * villageName : h
     * num : 2
     * pageNum : 0
     * pageSize : 0
     * phoneNumber : null
     * orderStatus : null
     */

    private String province;
    private String city;
    private String district;
    private String street;
    private String villageName;
    private int num;
    private int pageNum;
    private int pageSize;
    private String phoneNumber;
    private String orderStatus;

    public String getProvince() {
        return TextUtils.isEmpty(province) ? "" : province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return TextUtils.isEmpty(city) ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return TextUtils.isEmpty(district) ? "" : district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return TextUtils.isEmpty(street) ? "" : street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getVillageName() {
        return TextUtils.isEmpty(villageName) ? "" : villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
