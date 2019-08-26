package com.xiaomai.zhuangba.fragment.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.module.login.ILoginRegisteredModule;
import com.xiaomai.zhuangba.data.module.login.LoginRegisteredModule;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.authentication.AuthenticationFragment;
import com.xiaomai.zhuangba.fragment.authentication.RoleSelectionFragment;
import com.xiaomai.zhuangba.fragment.authentication.employer.EmployerAuthenticationFragment;
import com.xiaomai.zhuangba.fragment.authentication.master.MasterAuthenticationFragment;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public class RegisteredUpdateFragment extends BaseLoginRegisteredFragment{

    private final static String TYPE = "type";
    private final static String PHONE_NUMBER = "phoneNumber";
    private final static String PASSWORD = "password";

    @BindView(R.id.tvTip)
    TextView tvTip;
    @BindView(R.id.btnRegisteredUpdate)
    Button btnRegisteredUpdate;

    public static RegisteredUpdateFragment newInstance(String type, String phoneNumber, String password) {
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        args.putString(PHONE_NUMBER, phoneNumber);
        args.putString(PASSWORD, password);
        RegisteredUpdateFragment fragment = new RegisteredUpdateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ILoginRegisteredModule initModule() {
        return new LoginRegisteredModule();
    }

    @Override
    public void initView() {
        if (getType().equals(StringTypeExplain.REGISTERED_REGISTER.getCode())){
            //注册成功
            tvTip.setText(getString(R.string.registered_success));
            btnRegisteredUpdate.setText(getString(R.string.enter_app));
        }else if (getType().equals(StringTypeExplain.REGISTERED_FORGET_PASSWORD.getCode())){
            //修改密码成功
            tvTip.setText(getString(R.string.password_settings_successful));
            btnRegisteredUpdate.setText(getString(R.string.go_login));
        }
    }

    public String getType() {
        return getArguments() != null ? getArguments().getString(TYPE) : "";
    }

    @OnClick(R.id.btnRegisteredUpdate)
    public void onViewClicked() {
        if (getType().equals(StringTypeExplain.REGISTERED_REGISTER.getCode())){
            //注册成功 自动登录
            iModule.obtainLogin();
        }else if (getType().equals(StringTypeExplain.REGISTERED_FORGET_PASSWORD.getCode())){
            //修改密码成功 返回登录页面
            startFragment(LoginFragment.newInstance());
        }
    }

    @Override
    public String getPhone() {
        return getArguments() != null ? getArguments().getString(PHONE_NUMBER) : "";
    }

    @Override
    public String getPass() {
        return getArguments() != null ? getArguments().getString(PASSWORD) : "";
    }

    @Override
    public void startRoleSelection() {
        // 选择角色
        startFragment(RoleSelectionFragment.newInstance());
    }

    @Override
    public void startMasterWorker() {
        //师傅端
        startFragment(MasterWorkerFragment.newInstance());
    }

    @Override
    public void startEmployer() {
        //雇主端
        startFragment(EmployerFragment.newInstance());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_registered_update;
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
