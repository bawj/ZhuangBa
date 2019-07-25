package com.xiaomai.zhuangba.data;

import android.text.TextUtils;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public class SubmissionOrder {

    /** 详细地址 */
    private String address;
    private String addressDetail;
    /** 预约时间 */
    private String appointmentTime;
    /** 地址的纬度 */
    private String latitude;
    /** 过期和即将过期的时间、是否超时 */
    private String expireTime;
    /** 地址的经度 */
    private String longitude;
    /** 师傅得到的金额  */
    private int masterOrderAmount;
    /** 订单时间 */
    private String modifyTime;
    /** 姓名 */
    private String name;
    /** 任务数量 */
    private int number;
    /** 订单总金额 */
    private double orderAmount;
    /** 订单编号 */
    private String orderCode;
    /** 订单状态:雇主:0:分配中;1:已接单;2:处理中;3:去验收;4:已完成;5:已取消;师傅:0:新任务;1:待处理;2:处理中;3:验收中;4:已完成;5:已取消; */
    private int orderStatus;
    /** 发布方 */
    private String publisher;
    /** 大类服务ID  */
    private int serviceId;
    /** 服务名称  */
    private String serviceText;
    /** 电话 */
    private String telephone;
    /** 服务项目 */
    private List<OrderServicesBean> orderServices;

    public String getAddress() {
        return TextUtils.isEmpty(address) ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return TextUtils.isEmpty(addressDetail) ? "" : addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAppointmentTime() {
        return TextUtils.isEmpty(appointmentTime) ? "" : appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getMasterOrderAmount() {
        return masterOrderAmount;
    }

    public void setMasterOrderAmount(int masterOrderAmount) {
        this.masterOrderAmount = masterOrderAmount;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getName() {
        return TextUtils.isEmpty(name) ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceText() {
        return TextUtils.isEmpty(serviceText) ? "" : serviceText;
    }

    public void setServiceText(String serviceText) {
        this.serviceText = serviceText;
    }

    public String getTelephone() {
        return TextUtils.isEmpty(telephone) ? "" : telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<OrderServicesBean> getOrderServices() {
        return orderServices;
    }

    public void setOrderServices(List<OrderServicesBean> orderServices) {
        this.orderServices = orderServices;
    }
}
