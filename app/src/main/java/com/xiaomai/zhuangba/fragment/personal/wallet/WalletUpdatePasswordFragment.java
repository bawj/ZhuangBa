package com.xiaomai.zhuangba.fragment.personal.wallet;

import android.os.Bundle;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.personal.set.UpdatePassTwoFragment;

/**
 * @author Administrator
 * @date 2019/7/31 0031
 */
public class WalletUpdatePasswordFragment extends UpdatePassTwoFragment {

    public static WalletUpdatePasswordFragment newInstance(String phone, String verificationCode) {
        Bundle args = new Bundle();
        args.putString(PHONE, phone);
        args.putString(VERIFICATION_CODE, verificationCode);
        WalletUpdatePasswordFragment fragment = new WalletUpdatePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void verificationCode(Object tag) {
        //下一步
        if (getVerificationCode().equals(tag)) {
            //设置交易密码
            startFragmentAndDestroyCurrent(SetTradePasswordFragment.newInstance());
        } else {
            showToast(getString(R.string.verification_code_input_incorrectly));
        }
    }

    @Override
    protected String getActivityTitle() {
        return "";
    }
}
