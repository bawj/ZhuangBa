package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/11/7 0007
 */
public class MaintenanceOverman {

    /**"主键id"*/
    private Integer id;

    /**"服务id"*/
    private Integer serviceId;

    /**"订单id"*/
    private String orderCode;

    /**"维保数量(月份)"*/
    private Integer number;

    /**"维保金额"*/
    private Double amount;

    /**"雇主姓名"*/
    private String employerName;

    /**"雇主手机号"*/
    private String employerPhone;

    /**"服务名"*/
    private String serviceName;

    /**"开始时间"*/
    private String startTime;

    /**"结束时间"*/
    private String endTime;

    /** 状态 1新任务 2进行中 3.未开始 4.已结束*/
    private String status;

    /** 订单类型 1 安装单 2 广告单*/
    private String orderType;

    /**"每月的收益金额"*/
    private Double earningsMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getOrderCode() {
        return orderCode == null ? "" : orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getEmployerName() {
        return employerName == null ? "" : employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerPhone() {
        return employerPhone == null ? "" : employerPhone;
    }

    public void setEmployerPhone(String employerPhone) {
        this.employerPhone = employerPhone;
    }

    public String getServiceName() {
        return serviceName == null ? "" : serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStartTime() {
        return startTime == null ? "" : startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime == null ? "" : endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderType() {
        return orderType == null ? "" : orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Double getEarningsMoney() {
        return earningsMoney;
    }

    public void setEarningsMoney(Double earningsMoney) {
        this.earningsMoney = earningsMoney;
    }
}
