package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/7/31 0031
 */
public class AliPayAccountBean {

    /**
     * amount : 0
     * name : string
     * phoneNumber : string
     * presentationPassword : string
     * withdrawalsAccount : string
     */

    private int amount;
    private String name;
    private String phoneNumber;
    private String presentationPassword;
    private String withdrawalsAccount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getWithdrawalsAccount() {
        return withdrawalsAccount;
    }

    public void setWithdrawalsAccount(String withdrawalsAccount) {
        this.withdrawalsAccount = withdrawalsAccount;
    }

}
