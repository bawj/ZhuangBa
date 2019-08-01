package com.xiaomai.zhuangba.fragment.personal.wallet;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.MessageEvent;
import com.xiaomai.zhuangba.enums.EventBusEnum;
import com.xiaomai.zhuangba.fragment.SuccessFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/7/31 0031
 */
public class WithdrawPasswordFragment extends SetTradePasswordFragment {

    public static WithdrawPasswordFragment newInstance(String name, String withdrawalsAccount, double amount) {
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("withdrawalsAccount", withdrawalsAccount);
        args.putDouble("amount", amount);
        WithdrawPasswordFragment fragment = new WithdrawPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void submission() {
        String account = withdrawalsAccount();
        String name = getName();
        Double amount = getAmount();
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(name)) {
            ToastUtil.showShort(getString(R.string.the_account_is_empty));
        } else if (amount <= 0) {
            ToastUtil.showShort(getString(R.string.error_in_input_amount));
        } else if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort(getString(R.string.input_wallet_trade_password_null));
        } else {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("amount", amount);
            hashMap.put("name", name);
            hashMap.put("withdrawalsAccount", account);
            hashMap.put("presentationPassword", password);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
            RxUtils.getObservable(ServiceUrl.getUserApi().cashWithdrawal(requestBody))
                    .compose(this.<HttpResult<String>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<String>(getActivity()) {
                        @Override
                        protected void onSuccess(String response) {
                            ToastUtil.showShort("提交成功");
                            EventBus.getDefault().post(new MessageEvent(EventBusEnum.CASH_SUCCESS.getCode()));
                            startFragmentAndDestroyCurrent(SuccessFragment.newInstance());
                        }
                    });
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_withdraw_password;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    public Double getAmount() {
        return getArguments().getDouble("amount");
    }

    public String getName() {
        return getArguments().getString("name");
    }

    public String withdrawalsAccount() {
        return getArguments().getString("withdrawalsAccount");
    }
}
