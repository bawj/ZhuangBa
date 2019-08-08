package com.xiaomai.zhuangba.data.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Administrator
 * @date 2019/7/10 0010
 * 购物车
 */
@Entity
public class ShopCarData {

    @Id
    private Long id;
    /**
     * 单价
     */
    private String money;
    /**
     * 2台单价
     */
    private String money2;
    /**
     * 3台单价
     */
    private String money3;
    /**
     * 名称
     */
    private String text;
    /**
     * 图片
     */
    private String icon;
    /**
     * 数量
     */
    private String number;

    /**
     * 服务项目ID
     */
    private String serviceId;
    /**
     * 大类服务ID
     */
    private String parentServiceId;

    /** 维保价格 */
    private String maintenanceMoney;
    /** 维保 id */
    private int maintenanceId;
    /** 维保时间 */
    private String maintenanceTime;

    @Generated(hash = 1756493303)
    public ShopCarData(Long id, String money, String money2, String money3,
            String text, String icon, String number, String serviceId,
            String parentServiceId, String maintenanceMoney, int maintenanceId,
            String maintenanceTime) {
        this.id = id;
        this.money = money;
        this.money2 = money2;
        this.money3 = money3;
        this.text = text;
        this.icon = icon;
        this.number = number;
        this.serviceId = serviceId;
        this.parentServiceId = parentServiceId;
        this.maintenanceMoney = maintenanceMoney;
        this.maintenanceId = maintenanceId;
        this.maintenanceTime = maintenanceTime;
    }

    @Generated(hash = 786737547)
    public ShopCarData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoney() {
        return this.money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getParentServiceId() {
        return this.parentServiceId;
    }

    public void setParentServiceId(String parentServiceId) {
        this.parentServiceId = parentServiceId;
    }

    public String getMoney2() {
        return money2;
    }

    public void setMoney2(String money2) {
        this.money2 = money2;
    }

    public String getMoney3() {
        return money3;
    }

    public void setMoney3(String money3) {
        this.money3 = money3;
    }

    public String getMaintenanceMoney() {
        return maintenanceMoney;
    }

    public void setMaintenanceMoney(String maintenanceMoney) {
        this.maintenanceMoney = maintenanceMoney;
    }

    public int getMaintenanceId() {
        return maintenanceId;
    }

    public void setMaintenanceId(int maintenanceId) {
        this.maintenanceId = maintenanceId;
    }

    public String getMaintenanceTime() {
        return maintenanceTime;
    }

    public void setMaintenanceTime(String maintenanceTime) {
        this.maintenanceTime = maintenanceTime;
    }
}
