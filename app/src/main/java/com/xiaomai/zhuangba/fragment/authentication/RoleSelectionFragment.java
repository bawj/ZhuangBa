package com.xiaomai.zhuangba.fragment.authentication;

import android.os.Bundle;
import android.view.View;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.module.login.ILoginRegisteredModule;
import com.xiaomai.zhuangba.data.module.login.LoginRegisteredModule;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.authentication.employer.EmployerAuthenticationFragment;
import com.xiaomai.zhuangba.fragment.authentication.employer.EmployerRealNameAuthenticationFragment;
import com.xiaomai.zhuangba.fragment.authentication.master.MasterAuthenticationFragment;
import com.xiaomai.zhuangba.fragment.authentication.master.RealNameAuthenticationFragment;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.fragment.login.BaseLoginRegisteredFragment;
import com.xiaomai.zhuangba.fragment.login.LoginFragment;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.util.UserInfoUtil;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 *
 * 角色选择
 */
public class RoleSelectionFragment extends BaseLoginRegisteredFragment {

    public static RoleSelectionFragment newInstance() {
        Bundle args = new Bundle();
        RoleSelectionFragment fragment = new RoleSelectionFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected ILoginRegisteredModule initModule() {
        return new LoginRegisteredModule();
    }
    @Override
    public void initView() {
    }

    @OnClick({R.id.relMasterWorker, R.id.relEmployer , R.id.tvLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relMasterWorker:
                //选择角色 我是师傅
                iModule.obtainRole(StaticExplain.FU_FU_SHI.getCode());
                break;
            case R.id.relEmployer:
                //选择角色 我是雇主
                iModule.obtainRole(StaticExplain.EMPLOYER.getCode());
                break;
            case R.id.tvLogout:
                //退出登录
                iModule.logout();
                break;
            default:
        }
    }

    @Override
    public void startMasterAuthentication() {
        //师傅端 去认证
        startFragment(RealNameAuthenticationFragment.newInstance());
    }

    @Override
    public void startEmployerAuthentication() {
        startFragment(EmployerRealNameAuthenticationFragment.newInstance());
    }

    @Override
    public void logoutSuccess() {
        startFragment(LoginFragment.newInstance());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_role_selection;
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
