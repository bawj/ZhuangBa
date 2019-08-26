package com.xiaomai.zhuangba.data.bean;

import android.text.TextUtils;

/**
 * @author Administrator
 * @date 2019/8/26 0026
 */
public class AdvertisingReplacementDetailBean {

    /**
     * orderCode : 219279077271912448
     * makeStartTime : 2019-08-23 17:33:00
     * makeEndTIme : 2019-08-26 17:33:00
     * equipmentNum : sb118
     * equipmentSurface : A
     * address : 北京市北京市东城区朝阳门街道h11
     * orderStatus : 0
     */

    private String orderCode;
    private String makeStartTime;
    private String makeEndTIme;
    private String equipmentNum;
    private String equipmentSurface;
    private String address;
    private int orderStatus;

    public String getOrderCode() {
        return TextUtils.isEmpty(orderCode) ? "" : orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getMakeStartTime() {
        return makeStartTime;
    }

    public void setMakeStartTime(String makeStartTime) {
        this.makeStartTime = makeStartTime;
    }

    public String getMakeEndTIme() {
        return makeEndTIme;
    }

    public void setMakeEndTIme(String makeEndTIme) {
        this.makeEndTIme = makeEndTIme;
    }

    public String getEquipmentNum() {
        return TextUtils.isEmpty(equipmentNum) ? "" : equipmentNum;
    }

    public void setEquipmentNum(String equipmentNum) {
        this.equipmentNum = equipmentNum;
    }

    public String getEquipmentSurface() {
        return TextUtils.isEmpty(equipmentSurface) ? "" : equipmentSurface;
    }

    public void setEquipmentSurface(String equipmentSurface) {
        this.equipmentSurface = equipmentSurface;
    }

    public String getAddress() {
        return TextUtils.isEmpty(address) ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
