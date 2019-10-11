package com.xiaomai.zhuangba.data.bean;

/**
 * @author Administrator
 * @date 2019/10/10 0010
 */
public class PatrolMissionDetailListBean{

    /**
     * id : 65
     * count : null
     * suCount : null
     * province : 北京市
     * city : 北京市
     * region : 朝阳区
     * street : 1
     * address : 1
     * detailNo : 231671268242382848
     * equipmentNo : fgdgdg
     * cover : A,B,C
     * status : processing
     * createTime : null
     */

    private int id;
    private String count;
    private String suCount;
    private String province;
    private String city;
    private String region;
    private String street;
    private String address;
    private String detailNo;
    private String equipmentNo;
    private String cover;
    private String status;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCount() {
        return count == null ? "" : count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSuCount() {
        return suCount == null ? "" : suCount;
    }

    public void setSuCount(String suCount) {
        this.suCount = suCount;
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

    public String getRegion() {
        return region == null ? "" : region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStreet() {
        return street == null ? "" : street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address == null ? "" : address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailNo() {
        return detailNo == null ? "" : detailNo;
    }

    public void setDetailNo(String detailNo) {
        this.detailNo = detailNo;
    }

    public String getEquipmentNo() {
        return equipmentNo == null ? "" : equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public String getCover() {
        return cover == null ? "" : cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getStatus() {
        return status == null ? "" : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime == null ? "" : createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
