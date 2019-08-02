package com.xiaomai.zhuangba.fragment.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.toollib.weight.BtnToEditListenerUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.module.login.ILoginRegisteredModule;
import com.xiaomai.zhuangba.data.module.login.LoginRegisteredModule;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.weight.PasswordEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 * <p>
 * 注册账号 下一步设置密码
 */
public class RegisteredSetPasswordFragment extends BaseLoginRegisteredFragment {

    @BindView(R.id.editPassword)
    PasswordEditText editPassword;
    @BindView(R.id.editConfirmPassword)
    PasswordEditText editConfirmPassword;
    @BindView(R.id.btnNext)
    Button btnNext;

    /**
     * 手机号码
     */
    private static final String PHONE_NUMBER = "phone_number";

    public static RegisteredSetPasswordFragment newInstance(String phoneNumber) {
        Bundle args = new Bundle();
        args.putString(PHONE_NUMBER, phoneNumber);
        RegisteredSetPasswordFragment fragment = new RegisteredSetPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ILoginRegisteredModule initModule() {
        return new LoginRegisteredModule();
    }

    @Override
    public void initView() {
        BtnToEditListenerUtils.getInstance()
                .addEditView(editPassword)
                .addEditView(editConfirmPassword)
                .setBtn(btnNext)
                .build();
    }

    @OnClick(R.id.btnNext)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                btnNext();
                break;
            default:
        }
    }

    public void btnNext() {
        iModule.obtainRegistered();
    }

    @Override
    public String getPasswordOne() {
        return editPassword.getText() != null ? editPassword.getText().toString() : "";
    }

    @Override
    public String getPasswordTwo() {
        return editConfirmPassword.getText() != null ? editConfirmPassword.getText().toString() : "";
    }

    @Override
    public String getPhoneNumber() {
        return getArguments() != null ? getArguments().getString(PHONE_NUMBER) : "";
    }

    @Override
    public void registeredSuccess() {
        //注册成功
        startFragment(RegisteredUpdateFragment.newInstance(StringTypeExplain.REGISTERED_REGISTER.getCode()
                , getPhoneNumber(), getPasswordOne()));
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_set_password;
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
