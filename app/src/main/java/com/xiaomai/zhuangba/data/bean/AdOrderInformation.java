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

    private String orderCodes;
    private String equipmentNum;
    private String address;
    private int orderAmount;
    private int masterOrderAmount;
    private int orderStatus;
    private String reservation;
    private String equipmentSurface;
    private String batchCode;
    private String serviceCycle;
    private String remark;
    private List<DeviceSurfaceInformation> list;

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

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public int getMasterOrderAmount() {
        return masterOrderAmount;
    }

    public void setMasterOrderAmount(int masterOrderAmount) {
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
}
