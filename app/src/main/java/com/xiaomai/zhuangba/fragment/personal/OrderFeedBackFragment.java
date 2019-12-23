package com.xiaomai.zhuangba.fragment.personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.OrderFeedBackAdapter;
import com.xiaomai.zhuangba.data.DryRunOrder;
import com.xiaomai.zhuangba.data.bean.MessageEvent;
import com.xiaomai.zhuangba.data.bean.PayData;
import com.xiaomai.zhuangba.data.bean.PayResult;
import com.xiaomai.zhuangba.data.bean.PlayModule;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.PayPassView;
import com.xiaomai.zhuangba.weight.dialog.ApplyForARunDialog;
import com.xiaomai.zhuangba.weight.dialog.PayApplyForArunDialog;
import com.xiaomai.zhuangba.weight.dialog.PayPassDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
 * 订单反馈
 *
 * @author Bawj
 * CreateDate:     2019/12/19 0019 11:23
 */
public class OrderFeedBackFragment extends BaseListFragment<IBaseModule, OrderFeedBackAdapter> implements PayApplyForArunDialog.IHeadPortraitPopupCallBack {

    private int id;
    private OrderFeedBackAdapter orderFeedBackAdapter;

    public static OrderFeedBackFragment newInstance() {
        Bundle args = new Bundle();
        OrderFeedBackFragment fragment = new OrderFeedBackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        requestSelectAirRun();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        requestSelectAirRun();
    }

    private void requestSelectAirRun() {
        RxUtils.getObservable(ServiceUrl.getUserApi().selectAirRun(String.valueOf(getPage())
                , String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(this.<HttpResult<RefreshBaseList<DryRunOrder>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<DryRunOrder>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<DryRunOrder> refreshBaseList) {
                        List<DryRunOrder> dryRunOrderList = refreshBaseList.getList();
                        if (getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            orderFeedBackAdapter.setNewData(dryRunOrderList);
                            finishRefresh();
                        } else {
                            //加载
                            orderFeedBackAdapter.addData(dryRunOrderList);
                        }
                        if (dryRunOrderList.size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            loadMoreEnd();
                        } else {
                            //加载完成
                            loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                        finishRefresh();
                        loadError();
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final DryRunOrder dryRunOrder = (DryRunOrder) view.findViewById(R.id.tvMemberPhone).getTag();
        id = dryRunOrder.getId();
        //弹出申请空跑
        ApplyForARunDialog.getInstance().initView(getActivity())
                .setApplicationAmount(dryRunOrder.getAmount())
                .setDialogContent(dryRunOrder.getApplyReason())
                .setReAppointmentTime(getString(R.string.re_appointment_time_, dryRunOrder.getOnceAgainDate()))
                .setICallBase(new ApplyForARunDialog.BaseCallback() {
                    @Override
                    public void applicationForApproval() {
                        //申请通过
                        applicationForApprovalPayment();
                    }

                    @Override
                    public void applicationFailed() {
                        //申请不通过
                        applicationFailedRequestFailAirRun();
                    }
                })
                .showDialog();
    }


    private void applicationForApprovalPayment() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getUserMonthly())
                .compose(this.<HttpResult<Boolean>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Boolean>(getActivity()) {
                    @Override
                    protected void onSuccess(Boolean response) {
                        //是否显示月结挂账
                        //确认支付
                        PayApplyForArunDialog.getInstance().initView(getActivity())
                                .isPaymentMonthlyAccount(response)
                                .setDialogCallBack(OrderFeedBackFragment.this)
                                .show();
                    }
                });
    }

    @Override
    public void walletPay() {
        //钱包
        final PayPassDialog dialog = new PayPassDialog(getActivity());
        dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        dialog.dismiss();
                        requestPay(StringTypeExplain.WE_WALLET.getCode(), passContent);
                    }

                    @Override
                    public void onPayClose() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onPayForget() {
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void paymentMonthlyAccount() {
        //月结
        final PayPassDialog dialog = new PayPassDialog(getActivity());
        dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        dialog.dismiss();
                        requestPay(StringTypeExplain.MONTHLY_KNOTS.getCode(), passContent);
                    }

                    @Override
                    public void onPayClose() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onPayForget() {
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void weChatPay() {
        //微信
        requestPay(StringTypeExplain.WE_CHAT_PAYMENT.getCode(), "");
    }

    @Override
    public void aliPay() {
        //支付宝
        requestPay(StringTypeExplain.A_ALIPAY_PAYMENT.getCode(), "");
    }

    /**
     * @param payType  支付类型
     * @param password 钱包 和 挂账 支付密码
     */
    private void requestPay(final String payType, String password) {
        RxUtils.getObservable(ServiceUrl.getUserApi().adoptAirRun("", String.valueOf(id), payType, password))
                .compose(this.<HttpResult<PayData>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<PayData>(getActivity()) {
                    @Override
                    protected void onSuccess(final PayData payData) {
                        if (payType.equals(StringTypeExplain.WE_WALLET.getCode()) || payType.equals(StringTypeExplain.MONTHLY_KNOTS.getCode())) {
                            //月结和钱包
                            refresh();
                        } else if (payType.equals(StringTypeExplain.WE_CHAT_PAYMENT.getCode())) {
                            //微信
                            IWXAPI iwxapi = WXAPIFactory.createWXAPI(getActivity(), WE_CHAT_APP_ID, true);
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
                                    PayTask aliPay = new PayTask(getActivity());
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
                                                refresh();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeChatSuccess(MessageEvent messageEvent) {
        switch (messageEvent.getErrCode()) {
            case 0:
                //微信支付成功
                refresh();
                break;
            default:
        }
    }

    private void applicationFailedRequestFailAirRun() {
        RxUtils.getObservable(ServiceUrl.getUserApi().failAirRun("", String.valueOf(id)))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        refresh();
                    }
                });
    }

    @Override
    public OrderFeedBackAdapter getBaseListAdapter() {
        return orderFeedBackAdapter = new OrderFeedBackAdapter();
    }

    @Override
    public int getIvNotDataBackground() {
        return R.drawable.bg_search_empty;
    }

    @Override
    public String getTvNotData() {
        return getString(R.string.search_empty);
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_feedback);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
