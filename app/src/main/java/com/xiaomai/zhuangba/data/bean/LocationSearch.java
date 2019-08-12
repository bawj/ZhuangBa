package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public class LocationSearch {

    private String name;
    private String address;
    private String distance;
    private double longitude;
    private double latitude;

    public LocationSearch() {
    }

    public LocationSearch(String name, String address, String distance, double longitude, double latitude) {
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "LocationSearch{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", distance='" + distance + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

}
