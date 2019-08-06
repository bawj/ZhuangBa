package com.xiaomai.zhuangba.data.bean;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public class ServiceSubcategoryProject {

    /**
     * serviceId : 13
     * serviceText : 门禁综合布线
     * parentServiceId : 7
     * price : 80
     * serviceStandard : 1：线路布置合理、整齐，安装牢固；
     2：布线在建筑物内安装要保持水平或垂直；
     3：布线应加套管保护；
     4：信号线不能与大功率电力线平行，更不能穿在同一管内。
     * iconUrl : https://zb.4000750222.com//zbimages/test/2019/5/14/2c90ef856ab3eef8016ab3eef89a0000.png
     * servicePoolList : []
     */

    private int serviceId;
    private String serviceText;
    private String parentServiceId;
    private Double price;
    private Double price2;
    private Double price3;
    private String serviceStandard;
    private String iconUrl;
    private List<Object> servicePoolList;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceText() {
        return serviceText;
    }

    public void setServiceText(String serviceText) {
        this.serviceText = serviceText;
    }

    public String getParentServiceId() {
        return parentServiceId;
    }

    public void setParentServiceId(String parentServiceId) {
        this.parentServiceId = parentServiceId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getServiceStandard() {
        return serviceStandard;
    }

    public void setServiceStandard(String serviceStandard) {
        this.serviceStandard = serviceStandard;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public List<Object> getServicePoolList() {
        return servicePoolList;
    }

    public void setServicePoolList(List<Object> servicePoolList) {
        this.servicePoolList = servicePoolList;
    }

    public Double getPrice2() {
        return price2;
    }

    public void setPrice2(Double price2) {
        this.price2 = price2;
    }

    public Double getPrice3() {
        return price3;
    }

    public void setPrice3(Double price3) {
        this.price3 = price3;
    }
}
