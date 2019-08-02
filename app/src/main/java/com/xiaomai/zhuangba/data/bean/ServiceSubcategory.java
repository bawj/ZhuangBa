package com.xiaomai.zhuangba.data.bean;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public class ServiceSubcategory {

    /**
     * serviceId : 7
     * serviceText : 门禁
     * parentServiceId : 1
     * price : null
     * serviceStandard : null
     * iconUrl : https://zb.4000750222.com//zbimages/test/2019/5/14/2c90ef856ab3eef8016ab3eef89a0000.png
     * servicePoolList : [{"serviceId":13,"serviceText":"门禁综合布线","parentServiceId":"7","price":80,"serviceStandard":"1：线路布置合理、整齐，安装牢固；\r\n2：布线在建筑物内安装要保持水平或垂直；\r\n3：布线应加套管保护；\r\n4：信号线不能与大功率电力线平行，更不能穿在同一管内。","iconUrl":"https://zb.4000750222.com//zbimages/test/2019/5/14/2c90ef856ab3eef8016ab3eef89a0000.png","servicePoolList":[]},{"serviceId":14,"serviceText":"门禁综合布线1","parentServiceId":"7","price":70,"serviceStandard":"1：线路布置合理、整齐，安装牢固；\r\n2：布线在建筑物内安装要保持水平或垂直；\r\n3：布线应加套管保护；\r\n4：信号线不能与大功率电力线平行，更不能穿在同一管内。\r\n5: 12eqwsdfdsfgsfdgafwedfgbsfbsdfbsdfgsdfgsdfvxcx","iconUrl":"https://zb.4000750222.com//zbimages/test/2019/5/14/2c90ef856ab3eef8016ab3eef89a0000.png","servicePoolList":[]},{"serviceId":15,"serviceText":"门禁综合布线2","parentServiceId":"7","price":100,"serviceStandard":null,"iconUrl":"https://zb.4000750222.com//zbimages/test/2019/5/14/2c90ef856ab3eef8016ab3eef89a0000.png","servicePoolList":[]},{"serviceId":16,"serviceText":"门禁综合布线3","parentServiceId":"7","price":50,"serviceStandard":null,"iconUrl":"https://zb.4000750222.com//zbimages/test/2019/5/14/2c90ef856ab3eef8016ab3eef89a0000.png","servicePoolList":[]},{"serviceId":17,"serviceText":"门禁综合布线4","parentServiceId":"7","price":60,"serviceStandard":null,"iconUrl":"https://zb.4000750222.com//zbimages/test/2019/5/14/2c90ef856ab3eef8016ab3eef89a0000.png","servicePoolList":[]},{"serviceId":18,"serviceText":"门禁综合布线7","parentServiceId":"7","price":70,"serviceStandard":null,"iconUrl":"https://zb.4000750222.com//zbimages/test/2019/5/14/2c90ef856ab3eef8016ab3eef89a0000.png","servicePoolList":[]}]
     */

    private int serviceId;
    private String serviceText;
    private String parentServiceId;
    private Object price;
    private Object serviceStandard;
    private String iconUrl;
    private List<ServiceSubcategoryProject> servicePoolList;

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

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public Object getServiceStandard() {
        return serviceStandard;
    }

    public void setServiceStandard(Object serviceStandard) {
        this.serviceStandard = serviceStandard;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public List<ServiceSubcategoryProject> getServicePoolList() {
        return servicePoolList;
    }

    public void setServicePoolList(List<ServiceSubcategoryProject> servicePoolList) {
        this.servicePoolList = servicePoolList;
    }
}
