package com.xiaomai.zhuangba.fragment.orderdetail.employer;

import android.os.Bundle;

import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.MaintenanceProjects;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/9 0009
 *
 * 新增维保
 */
public class AddMaintenanceFragment extends BaseContinuedMaintenanceFragment implements PayDialog.IHeadPortraitPopupCallBack{

    public static final String PROVINCE = "province";
    public static final String CITY = "city";

    public static AddMaintenanceFragment newInstance(String province , String city) {
        Bundle args = new Bundle();
        args.putString(PROVINCE , province);
        args.putString(CITY , city);
        AddMaintenanceFragment fragment = new AddMaintenanceFragment();
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
    public Double calculateThePrice() {
        List<OrderServiceItem> maintenanceList = DBHelper.getInstance()
                .getOrderServiceItemDao().queryBuilder().list();
        double price = 0;
        for (OrderServiceItem orderServiceItem : maintenanceList) {
            //服务数量
            int number = orderServiceItem.getNumber();
            //维保金额
            double maintenanceAmount = orderServiceItem.getMaintenanceAmount();
            price += number * maintenanceAmount;
        }
        totalPrice = price;
        return price;
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
        HashMap<String, Object> hashMap = getHashMap(payType, code);
        String body = new Gson().toJson(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), body);
        Observable<HttpResult<PayData>> httpResultObservable = ServiceUrl.getUserApi().addMaintenance(requestBody);
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

    private HashMap<String, Object> getHashMap(String payType, String code) {
        HashMap<String, Object> hashMap = new HashMap<>();
        List<OrderServiceItem> list = DBHelper.getInstance().getOrderServiceItemDao().queryBuilder().list();
        if (list.isEmpty()) {
            return hashMap;
        }

        //总价
        double pay = 0d;
        String orderCode = "";
        List<MaintenanceProjects> maintenanceProjectsList = new ArrayList<>();
        for (OrderServiceItem orderServiceItem : list) {
            //商品数量
            int number = orderServiceItem.getNumber();
            //维保价格
            double maintenanceAmount = orderServiceItem.getMaintenanceAmount();
            pay += number * maintenanceAmount;
            //订单编号
            orderCode = orderServiceItem.getOrderCode();

            MaintenanceProjects maintenanceProjects = new MaintenanceProjects();
            //维保时间 月份
            int monthNumber = orderServiceItem.getMonthNumber();
            maintenanceProjects.setNumber(monthNumber);
            //维保金额
            maintenanceProjects.setAmout(number * maintenanceAmount);
            //服务项目ID
            maintenanceProjects.setServiceId(orderServiceItem.getServiceId());
            if (maintenanceAmount > 0){
                maintenanceProjectsList.add(maintenanceProjects);
            }
        }

        hashMap.put("maintenanceProjects" , maintenanceProjectsList);
        //支付密码
        hashMap.put("code" , code);
        //维保金额
        hashMap.put("maintenanceAmount", String.valueOf(pay));
        //支付类型:1:支付宝;2:微信;3:钱包
        hashMap.put("payType", payType);
        //订单编号
        hashMap.put("orderCode", orderCode);
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
    public String getProvince() {
        if (getArguments() != null){
            return getArguments().getString(PROVINCE);
        }
        return "";
    }

    @Override
    public String getCity() {
        if (getArguments() != null){
            return getArguments().getString(CITY);
        }
        return "";
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.add_maintenance);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
