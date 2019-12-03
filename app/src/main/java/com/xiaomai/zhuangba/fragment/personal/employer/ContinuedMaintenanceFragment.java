package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;

import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.MessageEvent;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
import com.xiaomai.zhuangba.data.bean.PayData;
import com.xiaomai.zhuangba.data.bean.PlayModule;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.fragment.personal.employer.base.BaseContinuedMaintenanceFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.PayPassView;
import com.xiaomai.zhuangba.weight.dialog.PayDialog;
import com.xiaomai.zhuangba.weight.dialog.PayPassDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 * <p>
 * 续维保
 */
public class ContinuedMaintenanceFragment extends BaseContinuedMaintenanceFragment implements PayDialog.IHeadPortraitPopupCallBack {

    public static ContinuedMaintenanceFragment newInstance() {
        Bundle args = new Bundle();
        ContinuedMaintenanceFragment fragment = new ContinuedMaintenanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void confirmationOfPayment() {
        //确认支付
        PayDialog.getInstance()
                .initView(getActivity())
                .setDialogCallBack(this)
                .show();
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.getDefault().register(this);
    }

    @Override
    public void walletPay() {
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
    public Double calculateThePrice() {
        List<OrderServiceItem> maintenanceList = DBHelper.getInstance()
                .getOrderServiceItemDao().queryBuilder().list();
        double totalPrice = 0d;
        for (OrderServiceItem orderServiceItem : maintenanceList) {
            //服务数量
            int number = orderServiceItem.getNumber();
            //维保金额
            double maintenanceAmount = orderServiceItem.getMaintenanceAmount();
            totalPrice = number * maintenanceAmount;
        }
        return totalPrice;
    }

    @Override
    public void weChatPay() {
        requestPay(StringTypeExplain.WE_CHAT_PAYMENT.getCode() , "");
    }

    @Override
    public void aliPay() {
        requestPay(StringTypeExplain.A_ALIPAY_PAYMENT.getCode() , "");
    }

    /**
     * @param payType 支付方式
     * @param code    密码
     */
    private void requestPay(final String payType, String code) {
        HashMap<String, String> hashMap = getHashMap(payType, code);
        String body = new Gson().toJson(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), body);
        Observable<HttpResult<PayData>> httpResultObservable = ServiceUrl.getUserApi().continuedMaintenance(requestBody);
        RxUtils.getObservable(httpResultObservable)
                .compose(this.<HttpResult<PayData>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<PayData>(getActivity()) {
                    @Override
                    protected void onSuccess(PayData payData) {
                        if (payType.equals(StringTypeExplain.WE_WALLET.getCode())) {
                            //钱包支付
                            paymentSuccess();
                        } else if (payType.equals(StringTypeExplain.WE_CHAT_PAYMENT.getCode())) {
                            //微信支付
                            new PlayModule().weChatOrderPayment(getActivity(), payData);
                        } else if (payType.equals(StringTypeExplain.A_ALIPAY_PAYMENT.getCode())) {
                            //支付宝支付
                            new PlayModule().aplipayOrderPayment(getActivity(), payData, new BaseCallback() {
                                @Override
                                public void onSuccess(Object obj) {
                                    paymentSuccess();
                                }
                            });
                        }
                    }
                });
    }

    private HashMap<String, String> getHashMap(String payType, String code) {
        HashMap<String, String> hashMap = new HashMap<>();
        List<OrderServiceItem> list = DBHelper.getInstance().getOrderServiceItemDao().queryBuilder().list();
        if (list.isEmpty()) {
            return hashMap;
        }
        //总价
        double pay = 0d;
        OrderServiceItem orderServiceItem = list.get(0);
        //商品数量
        int number = orderServiceItem.getNumber();
        //维保价格
        double maintenanceAmount = orderServiceItem.getMaintenanceAmount();
        pay += number * maintenanceAmount;
        //维保金额
        hashMap.put("amount", String.valueOf(pay));
        //钱包密码
        hashMap.put("code", code);
        //维保月份
        int monthNumber = orderServiceItem.getMonthNumber();
        hashMap.put("number", String.valueOf(monthNumber));
        //订单编号
        hashMap.put("orderCode", orderServiceItem.getOrderCode());
        //支付类型:1:支付宝;2:微信;3:钱包
        hashMap.put("payType", payType);
        //服务项目ID
        hashMap.put("serviceId", String.valueOf(orderServiceItem.getServiceId()));
        return hashMap;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeChatSuccess(MessageEvent messageEvent) {
        switch (messageEvent.getErrCode()) {
            case 0:
                //微信支付成功
                paymentSuccess();
                break;
            default:
        }
    }

    private void paymentSuccess() {
        showToast(getString(R.string.payment_success));
        startFragment(EmployerFragment.newInstance());
    }


    @Override
    protected String getActivityTitle() {
        return getString(R.string.continued_maintenance);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
