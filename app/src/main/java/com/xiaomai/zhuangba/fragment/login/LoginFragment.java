package com.xiaomai.zhuangba.fragment.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.toollib.weight.BtnToEditListenerUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.module.login.ILoginRegisteredModule;
import com.xiaomai.zhuangba.data.module.login.LoginRegisteredModule;
import com.xiaomai.zhuangba.fragment.authentication.AuthenticationFragment;
import com.xiaomai.zhuangba.fragment.authentication.RoleSelectionFragment;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 * 登录
 */
public class LoginFragment extends BaseLoginRegisteredFragment{

    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.editUserNumber)
    EditText editUserNumber;
    @BindView(R.id.editUserPass)
    EditText editUserPass;

    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
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
                .addEditView(editUserPass)
                .setBtn(btnLogin)
                .build();
    }

    @OnClick({R.id.btnLogin, R.id.tvRegistered, R.id.tvForgetPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                //登录
                iModule.obtainLogin();
                break;
            case R.id.tvRegistered:
                //注册
                startFragment(RegisteredFragment.newInstance());
                break;
            case R.id.tvForgetPassword:
                //忘记密码
                startFragment(ForgetPasswordFragment.newInstance());
                break;
            default:
        }
    }

    @Override
    public String getPhone() {
        return editUserNumber.getText().toString();
    }

    @Override
    public String getPass() {
        return editUserPass.getText().toString();
    }

    @Override
    public void startRoleSelection() {
        // 选择角色
        startFragment(RoleSelectionFragment.newInstance());
    }

    @Override
    public void startMasterWorker() {
        //师傅端
        startFragmentAndDestroyCurrent(MasterWorkerFragment.newInstance());
    }

    @Override
    public void startEmployer() {
        //雇主端
        startFragmentAndDestroyCurrent(EmployerFragment.newInstance());
    }

    @Override
    public void startAuthentication() {
        //去认证
        startFragmentAndDestroyCurrent(AuthenticationFragment.newInstance());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_login;
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
