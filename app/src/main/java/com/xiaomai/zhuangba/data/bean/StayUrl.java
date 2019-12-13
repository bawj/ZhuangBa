package com.xiaomai.zhuangba.data.bean;

/**
 * 上下刊 拍照 的图片
 */
public class StayUrl {

    /** 全景 */
    private String panorama;
    /** 报头近景 */
    private String headerCloseRange;
    /** 报头远景 */
    private String headerProspect;
    /** 其它 */
    private String other;

    public String getPanorama() {
        return panorama == null ? "" : panorama;
    }

    public void setPanorama(String panorama) {
        this.panorama = panorama;
    }

    public String getHeaderCloseRange() {
        return headerCloseRange == null ? "" : headerCloseRange;
    }

    public void setHeaderCloseRange(String headerCloseRange) {
        this.headerCloseRange = headerCloseRange;
    }

    public String getHeaderProspect() {
        return headerProspect == null ? "" : headerProspect;
    }

    public void setHeaderProspect(String headerProspect) {
        this.headerProspect = headerProspect;
    }

    public String getOther() {
        return other == null ? "" : other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
