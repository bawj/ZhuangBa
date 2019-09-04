package com.xiaomai.zhuangba.fragment.personal.wallet;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.verification.IVerificationCodeModule;
import com.xiaomai.zhuangba.data.module.verification.VerificationCodeModule;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/31 0031
 * 账号安全验证手机号
 */
public class TradePhoneFragment extends BaseFragment {

    @BindView(R.id.edtPhone)
    EditText edtPhone;

    public static TradePhoneFragment newInstance(String password) {
        Bundle args = new Bundle();
        args.putString("password", password);
        TradePhoneFragment fragment = new TradePhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IVerificationCodeModule initModule() {
        return new VerificationCodeModule();
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.btnCode)
    public void onViewClicked() {
        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String phone = edtPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort(getString(R.string.please_enter_your_cell_phone_number));
        } else if (!userInfo.getPhoneNumber().equals(phone)) {
            ToastUtil.showShort(getString(R.string.wallet_update_trade_input_error));
        } else {
            btnCodeClick();
        }
    }

    public void btnCodeClick() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getAuthenticationCode(edtPhone.getText().toString(),
                StringTypeExplain.REGISTERED_FORGET_PASSWORD.getCode())
                .compose(this.<HttpResult<Object>>bindToLifecycle()))
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        if (response != null) {
                            startFragment(WalletUpdatePasswordFragment.newInstance(edtPhone.getText().toString(), response.toString()));
                        }
                    }
                });
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_trade_phone;
    }

    @Override
    protected String getActivityTitle() {
        String str = getArguments().getString("password");
        return getString("2".equals(str) ? R.string.wallet_set_trade_password : R.string.wallet_update_trade_password);
    }
}
