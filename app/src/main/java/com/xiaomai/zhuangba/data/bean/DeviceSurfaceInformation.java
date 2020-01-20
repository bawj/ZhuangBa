package com.xiaomai.zhuangba.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DeviceSurfaceInformation {

    private String orderCode;
    private String equipmentNum;
    private String deviceSurface;
    private String oldAdName;
    private String oldAdUrl;
    /**
     * 上刊默认图
     */
    private String publishedPhotos;
    /**
     * 上刊描述
     */
    private String publishedRemark;
    /**
     * 下刊默认图
     */
    private String nextIssuePhotos;
    /**
     * 下刊描述
     */
    private String nextIssueRemark;

    @SerializedName("newlyAdName")
    private String newAdName;
    @SerializedName("newlyAdUrl")
    private String newAdUrl;
    /**
     * 操作类型：1.上下刊；2.上刊；3.下刊
     */
    private String type;
    private List<OrderDateList> orderDateList;

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

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPublishedPhotos() {
        return publishedPhotos == null ? "" : publishedPhotos;
    }

    public void setPublishedPhotos(String publishedPhotos) {
        this.publishedPhotos = publishedPhotos;
    }

    public String getNextIssuePhotos() {
        return nextIssuePhotos == null ? "" : nextIssuePhotos;
    }

    public void setNextIssuePhotos(String nextIssuePhotos) {
        this.nextIssuePhotos = nextIssuePhotos;
    }

    public String getPublishedRemark() {
        return publishedRemark == null ? "" : publishedRemark;
    }

    public void setPublishedRemark(String publishedRemark) {
        this.publishedRemark = publishedRemark;
    }

    public String getNextIssueRemark() {
        return nextIssueRemark == null ? "" : nextIssueRemark;
    }

    public void setNextIssueRemark(String nextIssueRemark) {
        this.nextIssueRemark = nextIssueRemark;
    }

    public List<OrderDateList> getOrderDateList() {
        if (orderDateList == null) {
            return new ArrayList<>();
        }
        return orderDateList;
    }

    public void setOrderDateList(List<OrderDateList> orderDateList) {
        this.orderDateList = orderDateList;
    }
}