package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/8/11 0011
 */
public class DataDetailsContent {

    /** 订单数量 */
    private int orderNumber;
    /** 待处理订单*/
    private int pendingDisposal;
    /** 安装单*/
    private int installationsNumber;
    /** 维保单 金额*/
    private double maintenanceAmount;
    /** 维保数量  */
    private int maintenanceNumber;
    /** 分配中的订单 */
    private int distribution;
    /** 支出金额 */
    private double expenditureAmount;
    /** 安装金额 */
    private double installationsAmount;

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getPendingDisposal() {
        return pendingDisposal;
    }

    public void setPendingDisposal(int pendingDisposal) {
        this.pendingDisposal = pendingDisposal;
    }

    public int getInstallationsNumber() {
        return installationsNumber;
    }

    public void setInstallationsNumber(int installationsNumber) {
        this.installationsNumber = installationsNumber;
    }

    public double getMaintenanceAmount() {
        return maintenanceAmount;
    }

    public void setMaintenanceAmount(double maintenanceAmount) {
        this.maintenanceAmount = maintenanceAmount;
    }

    public int getMaintenanceNumber() {
        return maintenanceNumber;
    }

    public void setMaintenanceNumber(int maintenanceNumber) {
        this.maintenanceNumber = maintenanceNumber;
    }

    public int getDistribution() {
        return distribution;
    }

    public void setDistribution(int distribution) {
        this.distribution = distribution;
    }

    public double getExpenditureAmount() {
        return expenditureAmount;
    }

    public void setExpenditureAmount(double expenditureAmount) {
        this.expenditureAmount = expenditureAmount;
    }

    public double getInstallationsAmount() {
        return installationsAmount;
    }

    public void setInstallationsAmount(double installationsAmount) {
        this.installationsAmount = installationsAmount;
    }
}
