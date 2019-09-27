package com.xiaomai.zhuangba.data.module.orderdetail;

import com.example.toollib.data.base.IBaseView;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public interface IOrderDetailView extends IBaseView {


    /**
     * 请求成功
     * @param object object
     */
    void requestOrderDetailSuccess(Object object);

    /**
     * 停止刷新
     */
    void finishRefresh();


    /**
     * code
     * @return string
     */
    String getOrderCode();

    /**
     * 1 安装单 2 广告单
     * @return 安装类型
     */
    String getOrderType();

    /**
     * 去认证
     */
    void goAuthentication();

//    /**
//     * 取消任务成功
//     */
//    void cancelOrderSuccess();
}
