package com.xiaomai.zhuangba.data.module.advertisement;

import com.example.toollib.data.base.IBaseView;

/**
 * @author Administrator
 * @date 2019/8/27 0027
 */
public interface IBaseAdvertisementView extends IBaseView {


    /**
     * 订单编号
     * @return string
     */
    String getOrderCode();

    /**
     * 订单状态 1 安装单 2广告单
     * @return string
     */
    String getOrderType();

    /**
     * 停止刷新
     */
    void finishRefresh();

    /**
     * 请求成功
     * @param object object
     */
    void requestOrderDetailSuccess(Object object);
}
