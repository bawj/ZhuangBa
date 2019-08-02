package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 *
 * 钱包
 */
public class WalletBean {

    private double bond;
    private double cashAlreadyAvailable;
    private String phoneNumber;
    private String presentationPassword;
    private double withDrawableCash;

    public double getBond() {
        return bond;
    }

    public void setBond(double bond) {
        this.bond = bond;
    }

    public double getCashAlreadyAvailable() {
        return cashAlreadyAvailable;
    }

    public void setCashAlreadyAvailable(double cashAlreadyAvailable) {
        this.cashAlreadyAvailable = cashAlreadyAvailable;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPresentationPassword() {
        return presentationPassword;
    }

    public void setPresentationPassword(String presentationPassword) {
        this.presentationPassword = presentationPassword;
    }

    public double getWithDrawableCash() {
        return withDrawableCash;
    }

    public void setWithDrawableCash(double withDrawableCash) {
        this.withDrawableCash = withDrawableCash;
    }

}
