package com.xiaomai.zhuangba.data.bean;

public class BaseAdvertisementPhotoTabEntity {

    private String url;
    private String explain;

    public BaseAdvertisementPhotoTabEntity(String url, String explain) {
        this.url = url;
        this.explain = explain;
    }

    public String getUrl() {
        return url == null ? "" : url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExplain() {
        return explain == null ? "" : explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
