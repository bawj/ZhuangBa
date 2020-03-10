package com.xiaomai.zhuangba.data.bean;

import android.text.TextUtils;

import com.xiaomai.zhuangba.util.DateUtil;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 *
 * 订单时间信息
 */
public class OrderDateList {
    /**
     * orderCode : 20190517124905903415
     * time : 2019-05-18T14:03:24.000+0000
     * typeText : 发布时间
     */

    private String orderCode;
    private String time;
    private String typeText;

    public OrderDateList() {
    }

    public OrderDateList(String orderCode, String time, String typeText) {
        this.orderCode = orderCode;
        this.time = time;
        this.typeText = typeText;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTime() {
        String date = DateUtil.getDate(time, "yyyy-MM-dd HH:mm");
        return TextUtils.isEmpty(date) ? time : date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTypeText() {
        return TextUtils.isEmpty(typeText) ? "" : typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }
}
