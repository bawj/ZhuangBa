package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/11/7 0007
 */
public class GuaranteeDeatil {

    /**
     * id : null
     * serviceId : 228
     * orderCode : 900
     * number : 2
     * amount : 10
     * employerName : 林志成
     * employerPhone : 13757188697
     * serviceName : 排队机安装
     * startTime : 2019-11-03
     * endTime : 2020-01-03
     * status : 2
     * earningsMoney : null
     * orderType : 1
     * address : 湖北省襄阳市老河口市
     */

    private String id;
    private int serviceId;
    private String orderCode;
    private int number;
    private int amount;
    private String employerName;
    private String employerPhone;
    private String serviceName;
    private String startTime;
    private String endTime;
    private String status;
    private String earningsMoney;
    private String orderType;
    private String address;

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getOrderCode() {
        return orderCode == null ? "" : orderCode;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
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

    public String getEarningsMoney() {
        return earningsMoney == null ? "" : earningsMoney;
    }

    public void setEarningsMoney(String earningsMoney) {
        this.earningsMoney = earningsMoney;
    }

    public String getOrderType() {
        return orderType == null ? "" : orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
