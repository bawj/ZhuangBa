package com.xiaomai.zhuangba.data.bean;

import android.text.TextUtils;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 *
 * 维保
 */
public class MaintenancePolicyBean {

    /**
     * id : null
     * serviceId : null
     * orderCode : 212439943627259904
     * number : 3
     * amount : 1000
     * residualQuantity : null
     * residualAmount : null
     * overmanName : null
     * overmanPhone : null
     * serviceName : 咳咳咳可
     * startTime : 2019-08-06
     * endTime : 2019-08-30
     * startDate : null
     * endDate : null
     * status : 0
     * serviceImg : https://zb.4000750222.com//zbimages/test/2019/7/24/2c90ef856c21ec0e016c221ed8b00002.png
     */

    private String id;
    private String serviceId;
    private String orderCode;
    private int number;
    private String amount;
    private String residualQuantity;
    private String residualAmount;
    private String overmanName;
    private String overmanPhone;
    private String serviceName;
    private String startTime;
    private String endTime;
    private String startDate;
    private String endDate;
    private String status;
    private String serviceImg;
    /** 服务数量 */
    private int serviceNumber;

    /** 入账金额 */
    private double earningsMoney;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getResidualQuantity() {
        return residualQuantity;
    }

    public void setResidualQuantity(String residualQuantity) {
        this.residualQuantity = residualQuantity;
    }

    public String getResidualAmount() {
        return residualAmount;
    }

    public void setResidualAmount(String residualAmount) {
        this.residualAmount = residualAmount;
    }

    public String getOvermanName() {
        return TextUtils.isEmpty(overmanName) ? "" : overmanName;
    }

    public void setOvermanName(String overmanName) {
        this.overmanName = overmanName;
    }

    public String getOvermanPhone() {
        return TextUtils.isEmpty(overmanPhone) ? "" : overmanPhone;
    }

    public void setOvermanPhone(String overmanPhone) {
        this.overmanPhone = overmanPhone;
    }

    public String getServiceName() {
        return TextUtils.isEmpty(serviceName) ? "" : serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return TextUtils.isEmpty(endTime) ? "" : endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceImg() {
        return serviceImg;
    }

    public void setServiceImg(String serviceImg) {
        this.serviceImg = serviceImg;
    }

    public int getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(int serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public double getEarningsMoney() {
        return earningsMoney;
    }

    public void setEarningsMoney(double earningsMoney) {
        this.earningsMoney = earningsMoney;
    }
}
