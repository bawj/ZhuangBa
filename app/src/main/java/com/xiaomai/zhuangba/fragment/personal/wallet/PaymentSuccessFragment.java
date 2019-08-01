package com.xiaomai.zhuangba.fragment.personal.wallet;

import android.os.Bundle;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 */
public class PaymentSuccessFragment extends BaseFragment {

    public static PaymentSuccessFragment newInstance() {
        Bundle args = new Bundle();
        PaymentSuccessFragment fragment = new PaymentSuccessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.btnPaymentBackHome)
    public void onViewClicked() {
        //返回首页
        startFragment(MasterWorkerFragment.newInstance());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_payment_success;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.successful_pay);
    }
}
