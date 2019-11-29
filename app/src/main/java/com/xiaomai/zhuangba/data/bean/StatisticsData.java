package com.xiaomai.zhuangba.data.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Administrator
 * @date 2019/7/20 0020
 */
public class StatisticsData {

    private double totalAmount;
    private long orderNumber;
    private long userNumber;
    private long employerNumber;
    private long exclusiveNumber;
    @SerializedName("crowdsourcingNumber")
    private long crowdSourcingNumber;

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public long getEmployerNumber() {
        return employerNumber;
    }

    public void setEmployerNumber(long employerNumber) {
        this.employerNumber = employerNumber;
    }

    public long getExclusiveNumber() {
        return exclusiveNumber;
    }

    public void setExclusiveNumber(long exclusiveNumber) {
        this.exclusiveNumber = exclusiveNumber;
    }

    public long getCrowdSourcingNumber() {
        return crowdSourcingNumber;
    }

    public void setCrowdsourcingNumber(long crowdSourcingNumber) {
        this.crowdSourcingNumber = crowdSourcingNumber;
    }
}
