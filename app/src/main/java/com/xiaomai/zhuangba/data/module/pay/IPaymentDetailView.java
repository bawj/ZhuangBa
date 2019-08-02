package com.xiaomai.zhuangba.data.module.pay;

import com.example.toollib.data.base.IBaseView;
import com.xiaomai.zhuangba.data.bean.SubmissionOrder;

/**
 * @author Administrator
 * @date 2019/7/12 0012
 */
public interface IPaymentDetailView extends IBaseView {

    /**
     * 订单
     * @return sub order
     */
    SubmissionOrder getSubmissionOrder();

    /**
     * 微信支付
     * @return boolean
     */
    boolean getChkPaymentWeChat();

    /**
     * 支付宝支付
     * @return boolean
     */
    boolean getChkPaymentPlay();


    /**
     * 支付成功
     */
    void paymentSuccess();
}
