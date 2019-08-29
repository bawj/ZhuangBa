package com.xiaomai.zhuangba.data.module.advertisement;

import com.example.toollib.data.IBaseModule;

/**
 * @author Administrator
 * @date 2019/8/27 0027
 */
public interface IBaseAdvertisementModule extends IBaseModule<IBaseAdvertisementView> {

    /**
     * 请求广告单详情
     */
    void requestAdvertisementDetail();

    /**
     * 订单池详情
     */
    void requestAdvertisementOrderPoolOrderDetail();
}
