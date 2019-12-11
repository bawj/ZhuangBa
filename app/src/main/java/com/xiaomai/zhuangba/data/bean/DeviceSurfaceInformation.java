package com.xiaomai.zhuangba.data.bean;

import java.util.ArrayList;
import java.util.List;

public class DeviceSurfaceInformation {

    private String orderCode;
    private String equipmentNum;
    private String deviceSurface;
    private String oldAdName;
    private String oldAdUrl;
    private String newAdName;
    private String newAdUrl;
    private List<OrderDateListBean> orderDateList;

    public String getOrderCode() {
        return orderCode == null ? "" : orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getEquipmentNum() {
        return equipmentNum == null ? "" : equipmentNum;
    }

    public void setEquipmentNum(String equipmentNum) {
        this.equipmentNum = equipmentNum;
    }

    public String getDeviceSurface() {
        return deviceSurface == null ? "" : deviceSurface;
    }

    public void setDeviceSurface(String deviceSurface) {
        this.deviceSurface = deviceSurface;
    }

    public String getOldAdName() {
        return oldAdName == null ? "" : oldAdName;
    }

    public void setOldAdName(String oldAdName) {
        this.oldAdName = oldAdName;
    }

    public String getOldAdUrl() {
        return oldAdUrl == null ? "" : oldAdUrl;
    }

    public void setOldAdUrl(String oldAdUrl) {
        this.oldAdUrl = oldAdUrl;
    }

    public String getNewAdName() {
        return newAdName == null ? "" : newAdName;
    }

    public void setNewAdName(String newAdName) {
        this.newAdName = newAdName;
    }

    public String getNewAdUrl() {
        return newAdUrl == null ? "" : newAdUrl;
    }

    public void setNewAdUrl(String newAdUrl) {
        this.newAdUrl = newAdUrl;
    }

    public List<OrderDateListBean> getOrderDateList() {
        if (orderDateList == null) {
            return new ArrayList<>();
        }
        return orderDateList;
    }

    public void setOrderDateList(List<OrderDateListBean> orderDateList) {
        this.orderDateList = orderDateList;
    }
}
