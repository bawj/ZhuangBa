package com.xiaomai.zhuangba.data.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/9/26 0026
 *
 * 服务订单地址
 */
public class OrderAddress {

    /** 姓名 */
    private String userText;
    /** 电话 */
    private String phoneNumber;
    /** 省 */
    private String province;
    /** 市 */
    private String city;
    /** 区 */
    private String area;
    /** 地址 */
    private String address;
    private long longitude;
    private long latitude;
    /** 详细地址 */
    private String addressDetail;
    /** 预约时间 */
    private String appointmentTime;
    /** 雇主描述 */
    private String employerDescribe;
    /** 图片 */
    private List<String> imgList;

    /** 合同编号 */
    private String contractNo;

    /** 客户经理 */
    private String accountManager;

    /** 项目名称 */
    private String projectName;

    /** 项目特点 */
    private String projectFeatures;

    /** 店铺名称 */
    private String shopName;

    /** 第三方订单编号 */
    private String orderNumber;

    public String getUserText() {
        return userText == null ? "" : userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getPhoneNumber() {
        return phoneNumber == null ? "" : phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail == null ? "" : addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAppointmentTime() {
        return appointmentTime == null ? "" : appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getProvince() {
        return province == null ? "" : province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area == null ? "" : area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getEmployerDescribe() {
        return employerDescribe == null ? "" : employerDescribe;
    }

    public void setEmployerDescribe(String employerDescribe) {
        this.employerDescribe = employerDescribe;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public List<String> getImgList() {
        if (imgList == null) {
            return new ArrayList<>();
        }
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getAccountManager() {
        return accountManager == null ? "" : accountManager;
    }

    public void setAccountManager(String accountManager) {
        this.accountManager = accountManager;
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectFeatures() {
        return projectFeatures == null ? "" : projectFeatures;
    }

    public void setProjectFeatures(String projectFeatures) {
        this.projectFeatures = projectFeatures;
    }

    public String getShopName() {
        return shopName == null ? "" : shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderNumber() {
        return orderNumber == null ? "" : orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
