package com.xiaomai.zhuangba.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LatAndLon implements Parcelable {

    /**
     * 地址的纬度
     */
    private float lat;
    /**
     * 地址的经度
     */
    private float lon;

    private String address;


    protected LatAndLon(Parcel in) {
        lat = in.readFloat();
        lon = in.readFloat();
        address = in.readString();
    }

    public static final Creator<LatAndLon> CREATOR = new Creator<LatAndLon>() {
        @Override
        public LatAndLon createFromParcel(Parcel in) {
            return new LatAndLon(in);
        }

        @Override
        public LatAndLon[] newArray(int size) {
            return new LatAndLon[size];
        }
    };

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(lat);
        parcel.writeFloat(lon);
        parcel.writeString(address);
    }
}
