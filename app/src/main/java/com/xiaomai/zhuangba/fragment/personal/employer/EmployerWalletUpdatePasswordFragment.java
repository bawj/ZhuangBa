package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.personal.wallet.WalletUpdatePasswordFragment;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 *
 * 雇主端 验证 验证码
 */
public class EmployerWalletUpdatePasswordFragment extends WalletUpdatePasswordFragment {

    public static EmployerWalletUpdatePasswordFragment newInstance(String phone, String verificationCode) {
        Bundle args = new Bundle();
        args.putString(PHONE, phone);
        args.putString(VERIFICATION_CODE, verificationCode);
        EmployerWalletUpdatePasswordFragment fragment = new EmployerWalletUpdatePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void verificationCode(Object tag) {
        //下一步
        if (getVerificationCode().equals(tag)) {
            //设置交易密码
            startFragmentAndDestroyCurrent(EmployerSetTradePasswordFragment.newInstance());
        } else {
            showToast(getString(R.string.verification_code_input_incorrectly));
        }
    }

}
