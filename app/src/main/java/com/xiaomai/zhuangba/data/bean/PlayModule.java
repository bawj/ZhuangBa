package com.xiaomai.zhuangba.data.bean;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.example.toollib.data.BaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.util.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.module.IPlayModule;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 * @date 2019/7/12 0012
 */
public class PlayModule<V> extends BaseModule<V> implements IPlayModule<V> {

    public static final String RESULT_STATUS = "9000";
    public static final String WE_CHAT_APP_ID = "wx6fb4ab8d28f3360a";

    @SuppressLint("CheckResult")
    @Override
    public void aplipayOrderPayment(final Context mContext, final PayData payData, final BaseCallback baseCallback) {
        Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, String>> emitter) throws Exception {
                PayTask aliPay = new PayTask((Activity) mContext);
                Map<String, String> result = aliPay.payV2(payData.getAliPay(), true);
                emitter.onNext(result);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Map<String, String>>() {
                    @Override
                    public void accept(Map<String, String> map) throws Exception {
                        PayResult payResult = new PayResult(map);
                        String resultStatus = payResult.getResultStatus();
                        if (TextUtils.equals(resultStatus, RESULT_STATUS)) {
                            //支付成功
                            ToastUtil.showShort(mContext.getString(R.string.payment_success));
                            baseCallback.onSuccess(payData);
                        }
                    }
                });
    }

    @Override
    public void weChatOrderPayment(Context mContext, PayData payData) {
        if (payData != null) {
            IWXAPI iwxapi = WXAPIFactory.createWXAPI(mContext, WE_CHAT_APP_ID, true);
            iwxapi.registerApp(WE_CHAT_APP_ID);
            PayReq req = new PayReq();
            req.appId = payData.getAppId();
            req.partnerId = payData.getPartnerId();
            req.prepayId = payData.getPrepayId();
            req.nonceStr = payData.getNonceStr();
            req.timeStamp = payData.getTimeStamp();
            req.packageValue = payData.getPackageName();
            req.sign = payData.getSign();
            req.extData = "app data";
            iwxapi.sendReq(req);
        }
    }
}
