package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public class OrderServicesBean {
    /** 项目金额 */
    private Double amount;
    /** 数量 */
    private int number;
    /** 订单编号 */
    private String orderCode;
    /** 服务项目id */
    private String serviceId;
    /** 服务项目名称 */
    private String serviceText;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
}
