package com.xiaomai.zhuangba.data;

/**
 * @author Bawj
 * CreateDate:     2020/3/5 0005 16:45
 */
public class EquipmentSurfaceRules {

    private String pictUrl;
    private String notice;
    private int popupFlag;

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

    public int getPopupFlag() {
        return popupFlag;
    }

    public void setPopupFlag(int popupFlag) {
        this.popupFlag = popupFlag;
    }
}
