package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 */
public class EmployerWalletBean {

    /**
     * phoneNumber : 13757188697
     * withDrawableCash : 5.22
     * cashAlreadyAvailable : 0.12
     * bond : 0.0
     * presentationPassword : 1
     * accountNumber :
     * accountNumber2 :
     * freezeMoney : null
     */

    private String phoneNumber;
    private double withDrawableCash;
    private double cashAlreadyAvailable;
    private double bond;
    private String presentationPassword;
    private String accountNumber;
    private String accountNumber2;
    private Object freezeMoney;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getWithDrawableCash() {
        return withDrawableCash;
    }

    public void setWithDrawableCash(double withDrawableCash) {
        this.withDrawableCash = withDrawableCash;
    }

    public double getCashAlreadyAvailable() {
        return cashAlreadyAvailable;
    }

    public void setCashAlreadyAvailable(double cashAlreadyAvailable) {
        this.cashAlreadyAvailable = cashAlreadyAvailable;
    }

    public double getBond() {
        return bond;
    }

    public void setBond(double bond) {
        this.bond = bond;
    }

    public String getPresentationPassword() {
        return presentationPassword;
    }

    public void setPresentationPassword(String presentationPassword) {
        this.presentationPassword = presentationPassword;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber2() {
        return accountNumber2;
    }

    public void setAccountNumber2(String accountNumber2) {
        this.accountNumber2 = accountNumber2;
    }

    public Object getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(Object freezeMoney) {
        this.freezeMoney = freezeMoney;
    }
}
