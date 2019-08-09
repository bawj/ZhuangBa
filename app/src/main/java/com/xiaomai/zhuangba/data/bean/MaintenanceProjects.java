package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/8/9 0009
 */
public class MaintenanceProjects {

    /** 维保金额 */
    private double amount;
    /** 维保数量（月份） */
    private int number;
    /** 服务项目ID */
    private int serviceId;

    public double getAmout() {
        return amount;
    }

    public void setAmout(double amount) {
        this.amount = amount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
