package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/7/20 0020
 */
public class StatisticsData {

    private double totalAmount;
    private int orderNumber;
    private int userNumber;
    private int employerNumber;

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }

    public int getEmployerNumber() {
        return employerNumber;
    }

    public void setEmployerNumber(int employerNumber) {
        this.employerNumber = employerNumber;
    }
}
