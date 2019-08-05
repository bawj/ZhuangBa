package com.xiaomai.zhuangba.fragment.orderdetail.employer.module;

import com.xiaomai.zhuangba.data.module.orderdetail.IOrderDetailModule;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 */
public interface IEmployerOrderDetailModule extends IOrderDetailModule<IEmployerOrderDetailView> {

    /**
     * 取消订单
     */
    void obtainCancelTask();

}
