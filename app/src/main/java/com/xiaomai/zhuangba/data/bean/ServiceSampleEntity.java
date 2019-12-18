package com.xiaomai.zhuangba.data.bean;

public class ServiceSampleEntity {

    private String picUrl;
    private String adverName;
    /** 本地图片的名称 */
    private String imgName;

    public ServiceSampleEntity(String picUrl, String adverName) {
        this.picUrl = picUrl;
        this.adverName = adverName;
    }

    public ServiceSampleEntity(String picUrl, String adverName , String imgName) {
        this.picUrl = picUrl;
        this.adverName = adverName;
        this.imgName = imgName;
    }

    public String getPicUrl() {
        return picUrl == null ? "" : picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getAdverName() {
        return adverName == null ? "" : adverName;
    }

    public void setAdverName(String adverName) {
        this.adverName = adverName;
    }

    public String getImgName() {
        return imgName == null ? "" : imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
}
