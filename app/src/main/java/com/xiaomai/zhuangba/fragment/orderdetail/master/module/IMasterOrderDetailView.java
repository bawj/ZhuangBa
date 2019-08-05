package com.xiaomai.zhuangba.fragment.orderdetail.master.module;

import com.xiaomai.zhuangba.data.module.orderdetail.IOrderDetailView;

/**
 * @author Administrator
 * @date 2019/8/3 0003
 */
public interface IMasterOrderDetailView extends IOrderDetailView {

    /**
     * 取消成功
     */
    void cancelOrderSuccess();

    /**
     * 接单成功
     */
    void receiptOrderSuccess();
}
