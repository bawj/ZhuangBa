package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public class OrderServicesBean {
    /** 项目金额 不包含维保金额*/
    private double amount;
    /** 数量 */
    private int number;
    /** 订单编号 */
    private String orderCode;
    /** 服务项目id */
    private String serviceId;
    /** 服务项目名称 */
    private String serviceText;
    /**
     * 单价
     */
    private String money;
    /**
     * 2台单价
     */
    private String money2;
    /**
     * 3台单价
     */
    private String money3;

    /** 维保时间 单位 (月) */
    private int monthNumber;
    /** 维保金额 */
    private double maintenanceAmount;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceText() {
        return serviceText;
    }

    public void setServiceText(String serviceText) {
        this.serviceText = serviceText;
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

    public String getMoney() {
        return money == null ? "" : money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getMoney2() {
        return money2 == null ? "" : money2;
    }

    public void setMoney2(String money2) {
        this.money2 = money2;
    }

    public String getMoney3() {
        return money3 == null ? "" : money3;
    }

    public void setMoney3(String money3) {
        this.money3 = money3;
    }
}
