package com.xiaomai.zhuangba.data.module.shop;

import com.example.toollib.data.base.IBaseView;

/**
 * @author Administrator
 * @date 2019/9/27 0027
 */
public interface IShopCarView extends IBaseView {
    /**
     * 订单地址信息
     * @return  string
     */
    String getOrderAddressGson();

    /**
     * 服务ID
     * @return  String
     */
    String getServiceId();

    /**
     * 服务名称
     * @return string
     */
    String getServiceText();

    /**
     * 提交成功
     * @param s string
     */
    void placeOrderSuccess(String s);
}
