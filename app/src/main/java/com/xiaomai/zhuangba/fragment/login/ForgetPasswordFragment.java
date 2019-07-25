package com.xiaomai.zhuangba.fragment.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.toollib.weight.BtnToEditListenerUtils;
import com.example.toollib.weight.SingleCountDownView;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.module.login.ILoginRegisteredModule;
import com.xiaomai.zhuangba.data.module.login.LoginRegisteredModule;
import com.xiaomai.zhuangba.enums.StringTypeExplain;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class ForgetPasswordFragment extends BaseLoginRegisteredFragment{

    @BindView(R.id.editUserNumber)
    EditText editUserNumber;
    @BindView(R.id.singleVerificationCode)
    SingleCountDownView singleVerificationCode;
    @BindView(R.id.editVerificationCode)
    EditText editVerificationCode;
    @BindView(R.id.btnNext)
    Button btnNext;

    public static ForgetPasswordFragment newInstance() {
        Bundle args = new Bundle();
        ForgetPasswordFragment fragment = new ForgetPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ILoginRegisteredModule initModule() {
        return  new LoginRegisteredModule();
    }

    @Override
    public void initView() {
        statusBarBlack();
        BtnToEditListenerUtils.getInstance()
                .addEditView(editUserNumber)
                .addEditView(editVerificationCode)
                .setBtn(btnNext)
                .build();
    }
    @OnClick({R.id.singleVerificationCode, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.singleVerificationCode:
                //请求验证码
                iModule.getAuthenticationCode(editUserNumber.getText().toString()
                        , StringTypeExplain.REGISTERED_FORGET_PASSWORD.getCode());
                break;
            case R.id.btnNext:
                //下一步 同时验证 手机号 验证码 输入是否正确
                iModule.requestProvingVerificationCode(editUserNumber.getText().toString()
                        , editVerificationCode.getText().toString());
                break;
            default:
        }
    }

    @Override
    public void startSingleVerificationCode() {
        singleVerificationCode.startCountDown();
    }

    @Override
    public void retSingleVerificationCode() {
        singleVerificationCode.stopCountDown();
    }

    @Override
    public void next() {
        //修改密码
        startFragment(ForgetPassFragment.newInstance(editUserNumber.getText().toString()));
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_forget_password;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }
}
