package com.xiaomai.zhuangba.data.module.pay;

import com.xiaomai.zhuangba.data.module.IPlayModule;

/**
 * @author Administrator
 * @date 2019/7/12 0012
 */
public interface IPaymentDetailsModule extends IPlayModule<IPaymentDetailView> {

    /**
     * 去支付
     */
    void goOrderPay();

}
