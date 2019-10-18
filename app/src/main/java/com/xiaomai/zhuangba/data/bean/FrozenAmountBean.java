package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/10/18 0018
 *
 * 冻结金额
 */
public class FrozenAmountBean {

    /**
     * orderCode : 231669777817448448
     * status : 0
     * enterTime : 2019-09-27
     * amount : 270.0
     */

    private String orderCode;
    private int status;
    private String enterTime;
    private double amount;

    public String getOrderCode() {
        return orderCode == null ? "" : orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEnterTime() {
        return enterTime == null ? "" : enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
