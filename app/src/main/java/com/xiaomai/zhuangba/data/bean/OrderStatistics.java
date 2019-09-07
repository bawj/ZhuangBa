package com.xiaomai.zhuangba.data.bean;

import java.math.BigDecimal;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public class OrderStatistics {

    /** 今日流水 */
    private double masterOrderAmount;
    /** 师傅端今日接单 -- 雇主端待处理的订单数量*/
    private int pendingDisposal;
    /** 今日完成 */
    private int complete;

    /** 雇主端 分配中的订单数量 */
    private int distribution;

    public double getMasterOrderAmount() {
        return new BigDecimal(masterOrderAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public void setMasterOrderAmount(double masterOrderAmount) {
        this.masterOrderAmount = masterOrderAmount;
    }

    public int getPendingDisposal() {
        return pendingDisposal;
    }

    public void setPendingDisposal(int pendingDisposal) {
        this.pendingDisposal = pendingDisposal;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getDistribution() {
        return distribution;
    }

    public void setDistribution(int distribution) {
        this.distribution = distribution;
    }

}
