package com.xiaomai.zhuangba.data.bean;

import android.text.TextUtils;

/**
 * @author Administrator
 * @date 2019/8/26 0026
 */
public class EmployerAdvertisingReplacement {

    /**
     * batchCode : GG01234
     * orderTime : 3
     * remark : null
     * publishTime : 2019-08-24 11:47:22
     * orderEndTime : 2019-11-24 00:00:00
     * sumMoney : 30
     * count : 6
     */

    private String batchCode;
    private String orderTime;
    private String remark;
    private String publishTime;
    private String orderEndTime;
    private int sumMoney;
    private int count;

    public String getBatchCode() {
        return TextUtils.isEmpty(batchCode) ? "" : batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getRemark() {
        return TextUtils.isEmpty(remark) ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPublishTime() {
        return TextUtils.isEmpty(publishTime) ? "" : publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public int getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(int sumMoney) {
        this.sumMoney = sumMoney;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
