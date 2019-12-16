package com.xiaomai.zhuangba.data.bean;

public class Rules {

    /** 规则图片地址 */
    private String pictUrl;
    /** 规则说明 */
    private String notice;

    public String getPictUrl() {
        return pictUrl == null ? "" : pictUrl;
    }

    public void setPictUrl(String pictUrl) {
        this.pictUrl = pictUrl;
    }

    public String getNotice() {
        return notice == null ? "" : notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
