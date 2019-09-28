package com.xiaomai.zhuangba.data.module.pay;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.function.BaseHttpConsumer;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.DensityUtils;
import com.example.toollib.util.Log;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.data.bean.OrderServicesBean;
import com.xiaomai.zhuangba.data.bean.PayData;
import com.xiaomai.zhuangba.data.bean.PlayModule;
import com.xiaomai.zhuangba.data.bean.SubmissionOrder;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ShopCarUtil;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/7/12 0012
 */
public class PaymentDetailsModule extends PlayModule<IPaymentDetailView> implements IPaymentDetailsModule {

    private Observable<HttpResult<PayData>> observablePay = null;

    @SuppressLint("CheckResult")
    @Override
    public void goOrderPay() {
        SubmissionOrder submissionOrder = mViewRef.get().getSubmissionOrder();
        //任务总数量
        int number = 0;
        //订单总金额
        Double orderAmount = ShopCarUtil.getTotalMoney();
        //服务项目
        List<OrderServicesBean> orderServicesBeans = ShopCarUtil.getOrderServicesBean();
        for (OrderServicesBean orderServicesBean : orderServicesBeans) {
            number = number + orderServicesBean.getNumber();
        }
        //任务数量
        submissionOrder.setNumber(number);
        //订单总金额
        submissionOrder.setOrderAmount(orderAmount);
        //服务项目
        submissionOrder.setOrderServices(orderServicesBeans);
        boolean isChkPaymentWeChat = mViewRef.get().getChkPaymentWeChat();
        boolean isChkPaymentPlay = mViewRef.get().getChkPaymentPlay();
        final boolean isChkPaymentWallet = mViewRef.get().getChkPaymentWallet();
        String orderData = getOrderData(submissionOrder);
        if (!TextUtils.isEmpty(orderData)) {
            // TODO: 2019/8/7 0007 串联请求
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), orderData);
            //修改订单
            final Observable<HttpResult<Object>> updateOrder = ServiceUrl.getUserApi().updateOrder(requestBody);
            if (isChkPaymentPlay) {
                //支付宝支付
                observablePay = RxUtils.getObservable(ServiceUrl.getUserApi()
                        .orderPay(submissionOrder.getOrderCode(), StringTypeExplain.A_ALIPAY_PAYMENT.getCode(), ""));
            } else if (isChkPaymentWeChat) {
                //微信支付
                observablePay = RxUtils.getObservable(ServiceUrl.getUserApi()
                        .orderPay(submissionOrder.getOrderCode(), StringTypeExplain.WE_CHAT_PAYMENT.getCode(), ""));
            } else if (isChkPaymentWallet) {
                String walletPassword = mViewRef.get().getWalletPassword();
                //钱包支付
                observablePay = RxUtils.getObservable(ServiceUrl.getUserApi()
                        .orderPay(submissionOrder.getOrderCode(), StringTypeExplain.WE_WALLET.getCode(), walletPassword));
            }
            RxUtils.getObservable(updateOrder)
                    .compose(mViewRef.get().<HttpResult<Object>>bindLifecycle())
                    .doOnNext(new BaseHttpConsumer<Object>() {
                        @Override
                        public void httpConsumerAccept(HttpResult<Object> httpResult) {
                            Log.e("httpConsumerAccept httpResult = " + httpResult.toString());
                        }
                    })
                    .concatMap(new Function<HttpResult<Object>, ObservableSource<HttpResult<PayData>>>() {
                        @Override
                        public ObservableSource<HttpResult<PayData>> apply(HttpResult<Object> httpResult) throws Exception {
                            Log.e("flatMap 1 " + httpResult.toString());
                            return observablePay;
                        }
                    })
                    .subscribe(new BaseHttpRxObserver(mContext.get()) {
                        @Override
                        protected void onSuccess(Object response) {
                            if (response != null && isChkPaymentWallet){
                                PayData payData = (PayData) response;
                                mViewRef.get().paymentSuccess();
                            }else if (response != null) {
                                PayData payData = (PayData) response;
                                Log.e("subscribe onSuccess = " + payData.toString());
                                if (!TextUtils.isEmpty(payData.getAliPay())) {
                                    aplipayOrderPayment(mContext.get(), payData, new BaseCallback() {
                                        @Override
                                        public void onSuccess(Object obj) {
                                            mViewRef.get().paymentSuccess();
                                        }
                                    });
                                } else {
                                    weChatOrderPayment(mContext.get(), payData);
                                }
                            }
                        }
                    });
        }
    }


    private String getOrderData(SubmissionOrder submissionOrder) {
        HashMap<String, Object> hashMap = new HashMap<>();
        String orderCode = submissionOrder.getOrderCode();
        hashMap.put("orderCode", orderCode);
        //大类ID
        hashMap.put("serviceId", String.valueOf(submissionOrder.getServiceId()));
        //总数量
        hashMap.put("number", String.valueOf(submissionOrder.getNumber()));
        //总金额
        hashMap.put("orderAmount", String.valueOf(submissionOrder.getOrderAmount()));
        //姓名
        String name = submissionOrder.getName();
        hashMap.put("name", name);
        //电话
        String telephone = submissionOrder.getTelephone();
        hashMap.put("telephone", telephone);
        //地址
        String address = submissionOrder.getAddress();
        hashMap.put("address", String.valueOf(address + submissionOrder.getAddressDetail()));
        //预约时间
        String appointmentTime = submissionOrder.getAppointmentTime();
        hashMap.put("appointmentTime", appointmentTime);
        //经纬度
        hashMap.put("longitude", DensityUtils.stringTypeFloat(submissionOrder.getLongitude()));
        hashMap.put("latitude", DensityUtils.stringTypeFloat(submissionOrder.getLatitude()));
        //服务项目信息
        hashMap.put("orderServices", submissionOrder.getOrderServices());
        return new Gson().toJson(hashMap);
    }

}
