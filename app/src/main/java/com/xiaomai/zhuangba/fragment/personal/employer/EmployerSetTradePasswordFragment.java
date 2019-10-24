package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.fragment.personal.wallet.SetTradePasswordFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 */
public class EmployerSetTradePasswordFragment extends SetTradePasswordFragment {

    public static EmployerSetTradePasswordFragment newInstance() {
        Bundle args = new Bundle();
        EmployerSetTradePasswordFragment fragment = new EmployerSetTradePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void submission() {
        //提交交易密码
        RxUtils.getObservable(ServiceUrl.getUserApi().updatePresentationPassword(password))
                .compose(this.<HttpResult<String>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<String>(getActivity()) {
                    @Override
                    protected void onSuccess(String response) {
                        ToastUtil.showShort(getString(R.string.wallet_set_trade_success));
                        startFragmentAndDestroyCurrent(EmployerFragment.newInstance());
                    }
                });
    }
}
