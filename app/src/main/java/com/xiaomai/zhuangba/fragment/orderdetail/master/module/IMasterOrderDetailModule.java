package com.xiaomai.zhuangba.fragment.orderdetail.master.module;

import com.xiaomai.zhuangba.data.module.orderdetail.IOrderDetailModule;

/**
 * @author Administrator
 * @date 2019/8/3 0003
 */
public interface IMasterOrderDetailModule extends IOrderDetailModule<IMasterOrderDetailView> {

    /**
     * 取消任务
     */
    void requestCancelOrder();

    /**
     * 接单
     */
    void requestReceiptOrder();
}
