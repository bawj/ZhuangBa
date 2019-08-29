package com.xiaomai.zhuangba.data.bean;

import android.text.TextUtils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
@Entity
public class PushNotificationDB {

    @Id(autoincrement = true)
    private Long id;
    /**
     * 根据手机号查询
     */
    private String phone;
    private String aliasType;
    private String alias;
    /**
     * 描述
     */
    private String description;
    private String appkey;
    private String type;
    private String productionMode;
    /**
     * 时间 时间戳
     */
    private String time;
    private String displayType;
    /**
     * type = 2   code = 订单编号
     */
    private String orderCode;
    /**
     * 页面跳转   1 首页 2订单详情 3提现详情
     */
    private String orderType;

    private String afterOpen;
    /**
     * 通知标题
     */
    private String ticker;
    /**
     * 通知内容
     */
    private String text;
    /**
     * 通知标题
     */
    private String title;

    @Generated(hash = 501428101)
    public PushNotificationDB(Long id, String phone, String aliasType, String alias,
                              String description, String appkey, String type, String productionMode,
                              String time, String displayType, String orderCode, String orderType,
                              String afterOpen, String ticker, String text, String title) {
        this.id = id;
        this.phone = phone;
        this.aliasType = aliasType;
        this.alias = alias;
        this.description = description;
        this.appkey = appkey;
        this.type = type;
        this.productionMode = productionMode;
        this.time = time;
        this.displayType = displayType;
        this.orderCode = orderCode;
        this.orderType = orderType;
        this.afterOpen = afterOpen;
        this.ticker = ticker;
        this.text = text;
        this.title = title;
    }

    @Generated(hash = 13020160)
    public PushNotificationDB() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAliasType() {
        return this.aliasType;
    }

    public void setAliasType(String aliasType) {
        this.aliasType = aliasType;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppkey() {
        return this.appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductionMode() {
        return this.productionMode;
    }

    public void setProductionMode(String productionMode) {
        this.productionMode = productionMode;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDisplayType() {
        return this.displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getOrderCode() {
        return TextUtils.isEmpty(this.orderCode) ? "" : this.orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderType() {
        return TextUtils.isEmpty(this.orderType) ? "" : this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getAfterOpen() {
        return this.afterOpen;
    }

    public void setAfterOpen(String afterOpen) {
        this.afterOpen = afterOpen;
    }

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
