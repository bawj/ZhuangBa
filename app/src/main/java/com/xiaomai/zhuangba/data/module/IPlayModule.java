package com.xiaomai.zhuangba.data.module;

import android.content.Context;

import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.xiaomai.zhuangba.data.bean.PayData;

/**
 * @author Administrator
 * @date 2019/7/12 0012
 */
public interface IPlayModule<V> extends IBaseModule<V> {

    /**
     * 支付宝支付
     * 订单支付
     *
     * @param mContext  context
     * @param payData   play
     * @param baseCallback callback
     */
    void aplipayOrderPayment(Context mContext, PayData payData , BaseCallback baseCallback);


    /**
     * 微信支付
     * 订单支付
     *
     * @param mContext     context
     * @param payData      play
     */
    void weChatOrderPayment(Context mContext, PayData payData);
}
