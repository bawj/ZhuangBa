package com.xiaomai.zhuangba.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Bawj
 * CreateDate:     2020/3/9 0009 10:48
 * 加急单
 */
@Entity
public class Enumerate {

    /** 是否加急 */
    private String urgent = "n";
    /** 加急价格 */
    private double urgentPrice;
    @Generated(hash = 2091105576)
    public Enumerate(String urgent, double urgentPrice) {
        this.urgent = urgent;
        this.urgentPrice = urgentPrice;
    }
    @Generated(hash = 1111958)
    public Enumerate() {
    }
    public String getUrgent() {
        return this.urgent;
    }
    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }
    public double getUrgentPrice() {
        return this.urgentPrice;
    }
    public void setUrgentPrice(double urgentPrice) {
        this.urgentPrice = urgentPrice;
    }

}
