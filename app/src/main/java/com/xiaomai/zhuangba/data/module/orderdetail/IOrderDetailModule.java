package com.xiaomai.zhuangba.data.module.orderdetail;

import com.example.toollib.data.IBaseModule;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public interface IOrderDetailModule<V extends IOrderDetailView> extends IBaseModule<V> {

    /**
     * 请求订单详情
     */
    void requestOrderDetail();


    /**
     * 请求订单池详情
     */
    void requestOrderPoolOrderDetail();

}
