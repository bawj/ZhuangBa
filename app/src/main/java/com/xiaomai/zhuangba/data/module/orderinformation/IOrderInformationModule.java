package com.xiaomai.zhuangba.data.module.orderinformation;

import com.example.toollib.data.IBaseModule;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public interface IOrderInformationModule extends IBaseModule<IOrderInformationView> {

    /**
     * 提交订单
     */
    void submitOrder();

    /**
     * 修改订单
     */
    void requestUpdateOrder();
}
