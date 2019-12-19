package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/7/12 0012
 */
public class PayData {
    private String appId;
    private String nonceStr;
    private String prepayId;
    private String partnerId;
    private String timeStamp;
    private String sign;
    private String packageName;
    private String aliPay;

    public String getAppId() {
        return appId == null ? "" : appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr == null ? "" : nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPrepayId() {
        return prepayId == null ? "" : prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getPartnerId() {
        return partnerId == null ? "" : partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getTimeStamp() {
        return timeStamp == null ? "" : timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSign() {
        return sign == null ? "" : sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPackageName() {
        return packageName == null ? "" : packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAliPay() {
        return aliPay == null ? "" : aliPay;
    }

    public void setAliPay(String aliPay) {
        this.aliPay = aliPay;
    }
}
