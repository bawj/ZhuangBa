package com.xiaomai.zhuangba.data.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class SkillAddressCondition {
    /** 详细地址 */
    private String address;
    /** 地址的经度 */
    private float longitude;
    /** 地址的纬度 */
    private float latitude;
    /** 技能列表 */
    private List<SkillList> skills = new ArrayList<>();

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<SkillList> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillList> skills) {
        this.skills = skills;
    }
}
