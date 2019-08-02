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
    private String iconUrl;
    private String serviceStandard;

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

}
