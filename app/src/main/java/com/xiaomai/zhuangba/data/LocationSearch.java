package com.xiaomai.zhuangba.data;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public class LocationSearch {

    private String name;
    private String address;
    private String distance;
    private Double longitude;
    private Double latitude;

    public LocationSearch() {
    }

    public LocationSearch(String name, String address, String distance, Double longitude, Double latitude) {
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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
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
