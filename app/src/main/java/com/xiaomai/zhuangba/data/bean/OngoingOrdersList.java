package com.xiaomai.zhuangba.data.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.xiaomai.zhuangba.util.DateUtil;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public class OngoingOrdersList implements Parcelable{

    /** 订单编号 */
    private String orderCode;
    /** 服务 类型 大类*/
    private int serviceId;
    /** 公共安全-门禁  大类名称-小类名称 */
    private String serviceText;
    /** 任务数量 */
    private int number;
    /** 订单金额 */
    private double orderAmount;
    /** 订单状态  */
    private int orderStatus;
    /** 现场联系人姓名 */
    private String name;
    /** 现场联系人电话 */
    private String telephone;
    /** 现场联系人地址 */
    private String address;
    /** 预约时间 */
    private String appointmentTime;
    /** 经度 */
    private float longitude;
    /** 纬度 */
    private float latitude;
    /** 发布人 */
    private String publisher;
    /** 服务项目 */
    private List<Object> orderServices;

    /** 师傅端的部分字段 */
    /**  */
    private String masterOrderAmount;
    /** 是否确认时间 */
    private String confirmationTime;
    /** 修改时间 */
    private String modifyTime;
    /** 已超时 订单状态 = 7 已过期  内容 18:22 */
    /** 已超时 内容 已超时 */
    private String expireTime;

    /** 接受方名称 */
    private String userText;
    /** 接受方头像 */
    private String bareheadedPhotoUrl;

    /** 是否有维保 */
    private int maintenanceFlag;
    public OngoingOrdersList() {
    }

    protected OngoingOrdersList(Parcel in) {
        orderCode = in.readString();
        serviceId = in.readInt();
        serviceText = in.readString();
        number = in.readInt();
        orderAmount = in.readDouble();
        orderStatus = in.readInt();
        name = in.readString();
        telephone = in.readString();
        address = in.readString();
        appointmentTime = in.readString();
        longitude = in.readFloat();
        latitude = in.readFloat();
        publisher = in.readString();
        masterOrderAmount = in.readString();
        modifyTime = in.readString();
        expireTime = in.readString();
    }

    public static final Parcelable.Creator<OngoingOrdersList> CREATOR = new Parcelable.Creator<OngoingOrdersList>() {
        @Override
        public OngoingOrdersList createFromParcel(Parcel in) {
            return new OngoingOrdersList(in);
        }

        @Override
        public OngoingOrdersList[] newArray(int size) {
            return new OngoingOrdersList[size];
        }
    };

    public String getOrderCode() {
        return TextUtils.isEmpty(orderCode) ? "" : orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getName() {
        return TextUtils.isEmpty(name) ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return TextUtils.isEmpty(telephone) ? "" : telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return TextUtils.isEmpty(address) ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /** 格式化预约时间 */
    public String getAppointmentTime() {
        String date = DateUtil.getDate(appointmentTime, "yyyy-MM-dd HH:mm");
        return TextUtils.isEmpty(date) ? appointmentTime : date;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getMasterOrderAmount() {
        return TextUtils.isEmpty(masterOrderAmount) ? "" : masterOrderAmount;
    }

    public void setMasterOrderAmount(String masterOrderAmount) {
        this.masterOrderAmount = masterOrderAmount;
    }

    public List<Object> getOrderServices() {
        return orderServices;
    }

    public void setOrderServices(List<Object> orderServices) {
        this.orderServices = orderServices;
    }

    public String getUserText() {
        return TextUtils.isEmpty(userText) ? "" : userText;
    }

    public void setUserText(String userText) {
        this.userText = userText;
    }

    public String getBareheadedPhotoUrl() {
        return TextUtils.isEmpty(bareheadedPhotoUrl) ? "" : bareheadedPhotoUrl;
    }

    public void setBareheadedPhotoUrl(String bareheadedPhotoUrl) {
        this.bareheadedPhotoUrl = bareheadedPhotoUrl;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getExpireTime() {
        return TextUtils.isEmpty(expireTime) ? "" : expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getConfirmationTime() {
        return TextUtils.isEmpty(confirmationTime) ? "" : confirmationTime;
    }

    public void setConfirmationTime(String confirmationTime) {
        this.confirmationTime = confirmationTime;
    }

    public int getMaintenanceFlag() {
        return maintenanceFlag;
    }

    public void setMaintenanceFlag(int maintenanceFlag) {
        this.maintenanceFlag = maintenanceFlag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderCode);
        dest.writeInt(serviceId);
        dest.writeString(serviceText);
        dest.writeInt(number);
        dest.writeDouble(orderAmount);
        dest.writeInt(orderStatus);
        dest.writeString(name);
        dest.writeString(telephone);
        dest.writeString(address);
        dest.writeString(appointmentTime);
        dest.writeFloat(longitude);
        dest.writeFloat(latitude);
        dest.writeString(publisher);
        dest.writeString(masterOrderAmount);
        dest.writeString(modifyTime);
        dest.writeString(expireTime);
    }
}
