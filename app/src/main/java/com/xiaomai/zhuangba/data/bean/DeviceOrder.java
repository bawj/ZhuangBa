package com.xiaomai.zhuangba.data.bean;

public class DeviceOrder {

    /** 设备ID */
    private Integer id;

    /** 设备编号 */
    private String equipmentNum;

    /** 设备地址 */
    private String address;

    /** 服务类型；0:单次服务，1：持续服务 */
    private Integer serviceType;

    /** 服务数量 */
    private Integer serviceNum;

    /** 订单总金额 */
    private Double orderAmount;

    /** 师傅得到的金额 */
    private Double masterOrderAmount;

    /** 发布次数 */
    private Integer numberOfReleases;

    /** 订单号集合 */
    private String orderCodes;

    /** 订单状态 */
    private Integer orderStatus;

    /** 预约时间 */
    private String reservation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(Integer serviceNum) {
        this.serviceNum = serviceNum;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Double getMasterOrderAmount() {
        return masterOrderAmount;
    }

    public void setMasterOrderAmount(Double masterOrderAmount) {
        this.masterOrderAmount = masterOrderAmount;
    }

    public Integer getNumberOfReleases() {
        return numberOfReleases;
    }

    public void setNumberOfReleases(Integer numberOfReleases) {
        this.numberOfReleases = numberOfReleases;
    }

    public String getOrderCodes() {
        return orderCodes == null ? "" : orderCodes;
    }

    public void setOrderCodes(String orderCodes) {
        this.orderCodes = orderCodes;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getReservation() {
        return reservation == null ? "" : reservation;
    }

    public void setReservation(String reservation) {
        this.reservation = reservation;
    }
}
