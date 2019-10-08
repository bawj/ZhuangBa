package com.xiaomai.zhuangba.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Administrator
 * @date 2019/10/8 0008
 */
public class InspectionSheetBean implements Parcelable {

    /**
     * province : 北京市
     * city : 北京市
     * district : 朝阳区
     * street : 1
     * villageName : 1
     * num : 1
     * pageNum : 0
     * pageSize : 0
     * phoneNumber : null
     * orderStatus : null
     */

    private String province;
    private String city;
    private String district;
    private String street;
    private String villageName;
    private int num;
    private int pageNum;
    private int pageSize;
    private String phoneNumber;
    private String orderStatus;

    public InspectionSheetBean() {
    }

    protected InspectionSheetBean(Parcel in) {
        province = in.readString();
        city = in.readString();
        district = in.readString();
        street = in.readString();
        villageName = in.readString();
        num = in.readInt();
        pageNum = in.readInt();
        pageSize = in.readInt();
        phoneNumber = in.readString();
        orderStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(district);
        dest.writeString(street);
        dest.writeString(villageName);
        dest.writeInt(num);
        dest.writeInt(pageNum);
        dest.writeInt(pageSize);
        dest.writeString(phoneNumber);
        dest.writeString(orderStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InspectionSheetBean> CREATOR = new Creator<InspectionSheetBean>() {
        @Override
        public InspectionSheetBean createFromParcel(Parcel in) {
            return new InspectionSheetBean(in);
        }

        @Override
        public InspectionSheetBean[] newArray(int size) {
            return new InspectionSheetBean[size];
        }
    };

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

    public String getDistrict() {
        return district == null ? "" : district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street == null ? "" : street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getVillageName() {
        return villageName == null ? "" : villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getPhoneNumber() {
        return phoneNumber == null ? "" : phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderStatus() {
        return orderStatus == null ? "" : orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
