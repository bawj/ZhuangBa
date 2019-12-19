package com.xiaomai.zhuangba.data;

/**
 * @author Bawj
 * CreateDate:     2019/12/19 0019 11:37
 * 空跑申请
 */
public class DryRunOrder {

    /**主键*/
    private int id;

    /**订单编号*/
    private String orderCode;

    /**申请金额*/
    private String amount;

    /**申请理由*/
    private String applyReason;

    /**发起时间*/
    private String initiateDate;

    /**发起人*/
    private String initiateUser;

    /**在约时间*/
    private String onceAgainDate;

    /**状态：0：发起申请；1：申请通过；2：申请不通过；3：已支付*/
    private Integer dryRunStatus;

    /**是否挂单: y是 n否*/
    private String suspendFlag;

    /**确认人*/
    private String confirmor;

    /**确认时间*/
    private String verifyTime;

    /**师傅得到的金额*/
    private double masterOrderAmount;

    /**理由*/
    private String refusalReason;

    private Integer rake;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode == null ? "" : orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApplyReason() {
        return applyReason == null ? "" : applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getInitiateDate() {
        return initiateDate;
    }

    public void setInitiateDate(String initiateDate) {
        this.initiateDate = initiateDate;
    }

    public String getInitiateUser() {
        return initiateUser == null ? "" : initiateUser;
    }

    public void setInitiateUser(String initiateUser) {
        this.initiateUser = initiateUser;
    }

    public String getOnceAgainDate() {
        return onceAgainDate;
    }

    public void setOnceAgainDate(String onceAgainDate) {
        this.onceAgainDate = onceAgainDate;
    }

    public Integer getDryRunStatus() {
        return dryRunStatus;
    }

    public void setDryRunStatus(Integer dryRunStatus) {
        this.dryRunStatus = dryRunStatus;
    }

    public String getSuspendFlag() {
        return suspendFlag == null ? "" : suspendFlag;
    }

    public void setSuspendFlag(String suspendFlag) {
        this.suspendFlag = suspendFlag;
    }

    public String getConfirmor() {
        return confirmor == null ? "" : confirmor;
    }

    public void setConfirmor(String confirmor) {
        this.confirmor = confirmor;
    }

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime;
    }

    public double getMasterOrderAmount() {
        return masterOrderAmount;
    }

    public void setMasterOrderAmount(double masterOrderAmount) {
        this.masterOrderAmount = masterOrderAmount;
    }

    public String getRefusalReason() {
        return refusalReason == null ? "" : refusalReason;
    }

    public void setRefusalReason(String refusalReason) {
        this.refusalReason = refusalReason;
    }

    public Integer getRake() {
        return rake;
    }

    public void setRake(Integer rake) {
        this.rake = rake;
    }
}
