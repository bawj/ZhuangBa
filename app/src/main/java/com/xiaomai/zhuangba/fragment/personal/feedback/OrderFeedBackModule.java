package com.xiaomai.zhuangba.fragment.personal.feedback;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.example.toollib.data.BaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.AmountUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.DryRunOrder;
import com.xiaomai.zhuangba.data.bean.EmployerAddProjectData;
import com.xiaomai.zhuangba.data.bean.OrderServiceCondition;
import com.xiaomai.zhuangba.data.bean.PayData;
import com.xiaomai.zhuangba.data.bean.PayResult;
import com.xiaomai.zhuangba.data.bean.PlayModule;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.xiaomai.zhuangba.data.bean.PlayModule.WE_CHAT_APP_ID;

/**
 * @author Bawj
 * CreateDate:     2020/3/11 0011 14:20
 */
public class OrderFeedBackModule extends BaseModule<IOrderFeedBackView> implements IOrderFeedBackModule {

    @Override
    public void requestSelectAirRun() {
        final int page = mViewRef.get().getPage();
        RxUtils.getObservable(ServiceUrl.getUserApi().selectAirRun(String.valueOf(page)
                , String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(mViewRef.get().<HttpResult<RefreshBaseList<DryRunOrder>>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<DryRunOrder>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<DryRunOrder> refreshBaseList) {
                        List<DryRunOrder> dryRunOrderList = refreshBaseList.getList();
                        if (page == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            mViewRef.get().setNewData(dryRunOrderList);
                            mViewRef.get().finishRefresh();
                        } else {
                            //加载
                            mViewRef.get().addData(dryRunOrderList);
                        }
                        if (dryRunOrderList.size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            mViewRef.get().loadMoreEnd();
                        } else {
                            //加载完成
                            mViewRef.get().loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                        mViewRef.get().finishRefresh();
                        mViewRef.get().loadError();
                    }
                });
    }

    @Override
    public void dryRunOrderAdopt() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getUserMonthly())
                .compose(mViewRef.get().<HttpResult<Boolean>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Boolean>(mContext.get()) {
                    @Override
                    protected void onSuccess(Boolean response) {
                        mViewRef.get().dryRunOrderAdoptSuccess(response, StringTypeExplain.DRY_RUN_PROJECT.getCode());
                    }
                });
    }

    @Override
    public void dryRunOrderApplicationFailed(int id) {
        RxUtils.getObservable(ServiceUrl.getUserApi().failAirRun("", String.valueOf(id)))
                .compose(mViewRef.get().<HttpResult<Object>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(mContext.get()) {
                    @Override
                    protected void onSuccess(Object response) {
                        mViewRef.get().viewRefresh();
                    }
                });
    }

    @Override
    public void requestPay(int id, final String payType, String password, String orderType, DryRunOrder dryRunOrder) {
        Observable<HttpResult<PayData>> httpResultObservable = null;
        if (orderType.equals(StringTypeExplain.ADD_PROJECT.getCode())) {
            //新增项目
            httpResultObservable = ServiceUrl.getUserApi().adoptIncrease(dryRunOrder.getOrderCode() , dryRunOrder.getCode() , payType , password);
        } else if (orderType.equals(StringTypeExplain.CUSTOM_PROJECT.getCode())) {
            //自定义项
            httpResultObservable = ServiceUrl.getUserApi().adoptCustomizeItem(String.valueOf(id),
                    dryRunOrder.getOrderCode(), dryRunOrder.getCode(), payType, password);
        } else if (orderType.equals(StringTypeExplain.DRY_RUN_PROJECT.getCode())) {
            //空跑
            httpResultObservable = ServiceUrl.getUserApi().adoptAirRun("", String.valueOf(id), payType, password);
        }
        RxUtils.getObservable(httpResultObservable)
                .compose(mViewRef.get().<HttpResult<PayData>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<PayData>(mContext.get()) {
                    @Override
                    protected void onSuccess(final PayData payData) {
                        if (payType.equals(StringTypeExplain.WE_WALLET.getCode()) || payType.equals(StringTypeExplain.MONTHLY_KNOTS.getCode())) {
                            //月结和钱包
                            mViewRef.get().viewRefresh();
                        } else if (payType.equals(StringTypeExplain.WE_CHAT_PAYMENT.getCode())) {
                            //微信
                            IWXAPI iwxapi = WXAPIFactory.createWXAPI(mContext.get(), WE_CHAT_APP_ID, true);
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
                        } else if (payType.equals(StringTypeExplain.A_ALIPAY_PAYMENT.getCode())) {
                            //支付宝
                            Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
                                @Override
                                public void subscribe(ObservableEmitter<Map<String, String>> emitter) throws Exception {
                                    PayTask aliPay = new PayTask(((Activity) mContext.get()));
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
                                            if (TextUtils.equals(resultStatus, PlayModule.RESULT_STATUS)) {
                                                //支付成功
                                                mViewRef.get().viewRefresh();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    @Override
    public void customApplicationFailed(DryRunOrder dryRunOrder) {
        RxUtils.getObservable(ServiceUrl.getUserApi().failCustomizeItem(dryRunOrder.getOrderCode(), dryRunOrder.getCode()))
                .compose(mViewRef.get().<HttpResult<Object>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(mContext.get()) {
                    @Override
                    protected void onSuccess(Object response) {
                        mViewRef.get().viewRefresh();
                    }
                });
    }

    @Override
    public void customForApproval(DryRunOrder dryRunOrder) {
        RxUtils.getObservable(ServiceUrl.getUserApi().getUserMonthly())
                .compose(mViewRef.get().<HttpResult<Boolean>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Boolean>(mContext.get()) {
                    @Override
                    protected void onSuccess(Boolean response) {
                        mViewRef.get().dryRunOrderAdoptSuccess(response, StringTypeExplain.CUSTOM_PROJECT.getCode());
                    }
                });
    }

    @Override
    public void requestIncreaseDecrease(final String orderCode) {
        RxUtils.getObservable(ServiceUrl.getUserApi().getIncreaseDecrease("1",orderCode))
                .compose(mViewRef.get().<HttpResult<EmployerAddProjectData>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<EmployerAddProjectData>() {
                    @Override
                    protected void onSuccess(EmployerAddProjectData employerAddProjectData) {
                        List<OrderServiceCondition> orderServiceList = employerAddProjectData.getOrderServiceList();
                        OrderServiceCondition orderServiceCondition = new OrderServiceCondition();
                        //必选项
                        orderServiceCondition.setServiceText(mContext.get().getString(R.string.required_options));
                        //价格
                        orderServiceCondition.setAmount(AmountUtil.add(employerAddProjectData.getMaterialsPrice() , employerAddProjectData.getSlottingPrice() , 2));
                        orderServiceCondition.setSlottingPrice(employerAddProjectData.getSlottingPrice());
                        orderServiceCondition.setSlottingStartLength(employerAddProjectData.getSlottingStartLength());
                        orderServiceCondition.setSlottingEndLength(employerAddProjectData.getSlottingEndLength());
                        orderServiceCondition.setMaterialPrice(employerAddProjectData.getMaterialsPrice());
                        orderServiceCondition.setMaterialsStartLength(employerAddProjectData.getMaterialsStartLength());
                        orderServiceCondition.setMaterialsEndLength(employerAddProjectData.getMaterialsEndLength());
                        orderServiceList.add(0 , orderServiceCondition);
                        mViewRef.get().increaseDecreaseSuccess(employerAddProjectData,orderServiceList);
                        mViewRef.get().finishRefresh();
                    }

                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                        mViewRef.get().finishRefresh();
                    }
                });
    }

    @Override
    public void failIncreaseDecrease(DryRunOrder dryRunOrder) {
        RxUtils.getObservable(ServiceUrl.getUserApi().failIncreaseDecrease(dryRunOrder.getOrderCode() , dryRunOrder.getCode()))
                .compose(mViewRef.get().<HttpResult<Object>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(mContext.get()) {
                    @Override
                    protected void onSuccess(Object response) {
                        mViewRef.get().viewRefresh();
                    }
                });
    }

    @Override
    public void customFailIncreaseDecrease(DryRunOrder dryRunOrder) {
        RxUtils.getObservable(ServiceUrl.getUserApi().getUserMonthly())
                .compose(mViewRef.get().<HttpResult<Boolean>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Boolean>(mContext.get()) {
                    @Override
                    protected void onSuccess(Boolean response) {
                        mViewRef.get().dryRunOrderAdoptSuccess(response, StringTypeExplain.ADD_PROJECT.getCode());
                    }
                });
    }

}
