package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 */
public class EarnestBean {

    private String accountNumber;
    private String amount;
    private String externalAccountNumber;
    private String phoneNumber;
    private int streamType;
    private String times;
    private int wallerType;
    private String withdrawalsAccount;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExternalAccountNumber() {
        return externalAccountNumber;
    }

    public void setExternalAccountNumber(String externalAccountNumber) {
        this.externalAccountNumber = externalAccountNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public int getWallerType() {
        return wallerType;
    }

    public void setWallerType(int wallerType) {
        this.wallerType = wallerType;
    }

    public String getWithdrawalsAccount() {
        return withdrawalsAccount;
    }

    public void setWithdrawalsAccount(String withdrawalsAccount) {
        this.withdrawalsAccount = withdrawalsAccount;
    }
}
