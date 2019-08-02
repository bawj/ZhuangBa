package com.xiaomai.zhuangba.data.bean;

import android.text.TextUtils;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class ServiceData {

    /** 服务ID */
    private int serviceId;
    /** 服务名称 */
    private String serviceText;
    /** 父服务ID */
    private String parentServiceId;
    /** 单价 */
    private String price;
    /** 服务标准 */
    private String serviceStandard;
    /** 图标 */
    private String iconUrl;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceText() {
        return TextUtils.isEmpty(this.serviceText) ? "" : this.serviceText;
    }

    public void setServiceText(String serviceText) {
        this.serviceText = serviceText;
    }

    public String getParentServiceId() {
        return TextUtils.isEmpty(this.parentServiceId) ? "" : this.parentServiceId;
    }

    public void setParentServiceId(String parentServiceId) {
        this.parentServiceId = parentServiceId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServiceStandard() {
        return serviceStandard;
    }

    public void setServiceStandard(String serviceStandard) {
        this.serviceStandard = serviceStandard;
    }

    public String getIconUrl() {
        return TextUtils.isEmpty(iconUrl) ? "" : iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

}
