package com.xiaomai.zhuangba.fragment.login;

import android.os.Bundle;

import com.xiaomai.zhuangba.enums.StringTypeExplain;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class ForgetPassFragment extends RegisteredSetPasswordFragment{
    /**
     * 手机号码
     */
    private static final String PHONE_NUMBER = "phone_number";

    public static ForgetPassFragment newInstance(String phoneNumber) {
        Bundle args = new Bundle();
        args.putString(PHONE_NUMBER, phoneNumber);
        ForgetPassFragment fragment = new ForgetPassFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void btnNext() {
        //忘记密码
        iModule.obtainForgetPassword();
    }

    @Override
    public void forgetPasswordSuccess() {
        //密码修改成功
        startFragment(RegisteredUpdateFragment.newInstance(StringTypeExplain.REGISTERED_FORGET_PASSWORD.getCode() , getPhoneNumber() , getPasswordOne()));
    }
}
