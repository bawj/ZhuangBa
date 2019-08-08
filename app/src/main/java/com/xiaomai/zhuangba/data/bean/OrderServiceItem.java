package com.xiaomai.zhuangba.data.bean;

import android.text.TextUtils;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 * 服务项目
 */
public class OrderServiceItem {

    /**
     * orderCode : 20190517124905903415
     * serviceId : 13
     * serviceText : 门禁综合布线
     * number : 2
     * amount : 140.0
     * iconUrl : https://zb.4000750222.com//zbimages/test/2019/5/14/2c90ef856ab3eef8016ab3eef89a0000.png
     */

    private String orderCode;
    private int serviceId;
    private String serviceText;
    private int number;
    private double amount;
    private double price2;
    private double price3;
    private String iconUrl;
    private String serviceStandard;

    /** 维保时间 单位（月） */
    private int monthNumber;

    /** 维保的金额 */
    private double maintenanceAmount;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceText() {
        return serviceText;
    }

    public void setServiceText(String serviceText) {
        this.serviceText = serviceText;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getServiceStandard() {
        return TextUtils.isEmpty(serviceStandard) ? "" : serviceStandard;
    }

    public void setServiceStandard(String serviceStandard) {
        this.serviceStandard = serviceStandard;
    }

    public double getPrice2() {
        return price2;
    }

    public void setPrice2(double price2) {
        this.price2 = price2;
    }

    public double getPrice3() {
        return price3;
    }

    public void setPrice3(double price3) {
        this.price3 = price3;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public double getMaintenanceAmount() {
        return maintenanceAmount;
    }

    public void setMaintenanceAmount(double maintenanceAmount) {
        this.maintenanceAmount = maintenanceAmount;
    }
}
