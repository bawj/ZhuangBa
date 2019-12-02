package com.xiaomai.zhuangba.data;

import android.text.TextUtils;

/**
 * @author Administrator
 * @date 2019/8/24 0024
 */
public class AdvertisingList {

    /** 广告订单编号 */
    private String orderCode;

    /** 设备编号 */
    private String equipmentNum;

    /** 订单周期 */
    private String orderTime;

    /** 地址 */
    private String address;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 订单金额 */
    private double orderAmount;

    /** 设备面 */
    private String equipmentSurface;
    /** 每月入账金额 */
    private double monthMoney;


    public String getOrderCode() {
        return TextUtils.isEmpty(orderCode) ? "" : orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getEquipmentNum() {
        return TextUtils.isEmpty(equipmentNum) ? "" : equipmentNum;
    }

    public void setEquipmentNum(String equipmentNum) {
        this.equipmentNum = equipmentNum;
    }

    public String getOrderTime() {
        return TextUtils.isEmpty(orderTime) ? "" : orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getAddress() {
        return TextUtils.isEmpty(address) ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getMonthMoney() {
         return monthMoney;
    }

    public void setMonthMoney(double monthMoney) {
        this.monthMoney = monthMoney;
    }

    public String getEquipmentSurface() {
        return TextUtils.isEmpty(equipmentSurface) ? "" : equipmentSurface;
    }

    public void setEquipmentSurface(String equipmentSurface) {
        this.equipmentSurface = equipmentSurface;
    }
}
