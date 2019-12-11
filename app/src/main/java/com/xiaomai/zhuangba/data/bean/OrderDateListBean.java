package com.xiaomai.zhuangba.data.bean;

public class OrderDateListBean {

    private String orderCode;
    private String time;
    private String typeText;

    public String getOrderCode() {
        return orderCode == null ? "" : orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTypeText() {
        return typeText == null ? "" : typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }
}
