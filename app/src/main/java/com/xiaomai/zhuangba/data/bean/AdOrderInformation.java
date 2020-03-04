package com.xiaomai.zhuangba.data.bean;

import java.util.ArrayList;
import java.util.List;

public class AdOrderInformation {


    /**
     * orderCodes : 255868593706336256
     * equipmentNum : fd345
     * address : 浙江省杭州市余杭区闲林街道防守打法
     * orderAmount : 40
     * masterOrderAmount : 32
     * orderStatus : 9
     * reservation : 2019-12-19 00:00:00~2019-12-20 23:59:59
     * equipmentSurface : 234
     * batchCode : 13811203954554795
     * serviceCycle : 2019-12-03 11:01:02~2020-02-03 00:00:00
     * remark : null
     * list : [{"orderCode":"255868593706336256","equipmentNum":"fd345","deviceSurface":"234","oldAdName":"的","oldAdUrl":"|","newAdName":null,"newAdUrl":null,"orderDateList":[{"orderCode":"255868593706336256","time":"2019-12-03 11:01:02","typeText":"发布时间"},{"orderCode":"255868593706336256","time":"2019-12-03 11:03:09","typeText":"接单时间"},{"orderCode":"255868593706336256","time":"2019-12-03 15:48:38","typeText":"出发时间"},{"orderCode":"255868593706336256","time":"2019-12-03 15:48:55","typeText":"开始时间"},{"orderCode":"255868593706336256","time":"2019-12-03 15:51:50","typeText":"提交验证时间"}]}]
     */
    private int equipmentId;
    /** 图片地址 用 , 隔开 */
    private String addressUrl;
    private String orderCodes;
    private String equipmentNum;
    private String address;
    private double orderAmount;
    private double masterOrderAmount;
    private int orderStatus;
    private String reservation;
    private String equipmentSurface;
    private String batchCode;
    private String serviceCycle;
    private String remark;
    private Integer serviceId;
    private float lat;
    private float lon;
    private String userText;
    private String bareheadedPhotoUrl;
    /** 拍照模板 */
    private String serviceSample;
    /** 1：下刊 2：上刊  */
    private String operating;
    private List<DeviceSurfaceInformation> list;

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getAddressUrl() {
        return addressUrl == null ? "" : addressUrl;
    }

    public void setAddressUrl(String addressUrl) {
        this.addressUrl = addressUrl;
    }

    public String getOrderCodes() {
        return orderCodes == null ? "" : orderCodes;
    }

    public void setOrderCodes(String orderCodes) {
        this.orderCodes = orderCodes;
    }

    public String getEquipmentNum() {
        return equipmentNum == null ? "" : equipmentNum;
    }

    public void setEquipmentNum(String equipmentNum) {
        this.equipmentNum = equipmentNum;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public double getMasterOrderAmount() {
        return masterOrderAmount;
    }

    public void setMasterOrderAmount(double masterOrderAmount) {
        this.masterOrderAmount = masterOrderAmount;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReservation() {
        return reservation == null ? "" : reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }

    public String getEquipmentSurface() {
        return equipmentSurface == null ? "" : equipmentSurface;
    }

    public void setEquipmentSurface(String equipmentSurface) {
        this.equipmentSurface = equipmentSurface;
    }

    public String getBatchCode() {
        return batchCode == null ? "" : batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getServiceCycle() {
        return serviceCycle == null ? "" : serviceCycle;
    }

    public void setServiceCycle(String serviceCycle) {
        this.serviceCycle = serviceCycle;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<DeviceSurfaceInformation> getList() {
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    public void setList(List<DeviceSurfaceInformation> list) {
        this.list = list;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public String getUserText() {
        return userText == null ? "" : userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getBareheadedPhotoUrl() {
        return bareheadedPhotoUrl == null ? "" : bareheadedPhotoUrl;
    }

    public void setBareheadedPhotoUrl(String bareheadedPhotoUrl) {
        this.bareheadedPhotoUrl = bareheadedPhotoUrl;
    }

    public String getOperating() {
        return operating == null ? "" : operating;
    }

    public void setOperating(String operating) {
        this.operating = operating;
    }

    public String getServiceSample() {
        return serviceSample == null ? "" : serviceSample;
    }

    public void setServiceSample(String serviceSample) {
        this.serviceSample = serviceSample;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
}
