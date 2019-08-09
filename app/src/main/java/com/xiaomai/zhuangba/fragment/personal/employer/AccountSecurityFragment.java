package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;
import android.widget.EditText;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.personal.wallet.TradePhoneFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 *
 * 雇主钱包 账号安全设置
 */
public class AccountSecurityFragment extends TradePhoneFragment {

    @BindView(R.id.edtPhone)
    EditText edtPhone;

    public static AccountSecurityFragment newInstance() {
        Bundle args = new Bundle();
        AccountSecurityFragment fragment = new AccountSecurityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void btnCodeClick() {
        String phone = edtPhone.getText().toString();
        RxUtils.getObservable(ServiceUrl.getUserApi().getAuthenticationCode(phone, StringTypeExplain.REGISTERED_FORGET_PASSWORD.getCode())
                .compose(this.<HttpResult<Object>>bindToLifecycle()))
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        if (response != null) {
                            startFragmentAndDestroyCurrent(EmployerWalletUpdatePasswordFragment.newInstance(edtPhone.getText().toString(), response.toString()));
                        }
                    }
                });
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.set_up_the_transaction_password);
    }

    @Override
    public boolean isInSwipeBack() {
        statusBarWhite();
        return super.isInSwipeBack();
    }

    @Override
    protected void popBackStack() {
        statusBarWhite();
        super.popBackStack();
    }
}
