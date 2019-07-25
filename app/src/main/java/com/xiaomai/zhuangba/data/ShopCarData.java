package com.xiaomai.zhuangba.data;

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

    @Generated(hash = 902570041)
    public ShopCarData(Long id, String money, String text, String icon,
                       String number, String serviceId, String parentServiceId) {
        this.id = id;
        this.money = money;
        this.text = text;
        this.icon = icon;
        this.number = number;
        this.serviceId = serviceId;
        this.parentServiceId = parentServiceId;
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


}
