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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEquipmentNum() {
        return equipmentNum;
    }

    public void setEquipmentNum(String equipmentNum) {
        this.equipmentNum = equipmentNum;
    }

    public String getAddress() {
        return address;
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
}
