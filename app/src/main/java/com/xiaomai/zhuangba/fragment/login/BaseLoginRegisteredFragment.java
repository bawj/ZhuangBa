package com.xiaomai.zhuangba.fragment.login;

import com.example.toollib.base.BaseFragment;
import com.xiaomai.zhuangba.data.module.login.ILoginRegisteredModule;
import com.xiaomai.zhuangba.data.module.login.ILoginRegisteredView;
import com.xiaomai.zhuangba.fragment.authentication.employer.EmployerAuthenticationFragment;
import com.xiaomai.zhuangba.fragment.authentication.master.MasterAuthenticationFragment;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public abstract class BaseLoginRegisteredFragment extends BaseFragment<ILoginRegisteredModule> implements ILoginRegisteredView {


    @Override
    public String getPasswordOne() {
        return null;
    }

    @Override
    public String getPasswordTwo() {
        return null;
    }

    @Override
    public String getPhoneNumber() {
        return null;
    }

    @Override
    public void registeredSuccess() {

    }

    @Override
    public void startSingleVerificationCode() {

    }

    @Override
    public void retSingleVerificationCode() {

    }

    @Override
    public void next() {

    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public String getPass() {
        return null;
    }

    @Override
    public void forgetPasswordSuccess() {

    }

    @Override
    public void startRoleSelection() {
        // 选择角色
    }

    @Override
    public void startMasterWorker() {
        //师傅端
    }

    @Override
    public void startEmployer() {
        //雇主端
    }

    @Override
    public void startMasterAuthentication() {
    }

    @Override
    public void startEmployerAuthentication() {
    }


    @Override
    public void logoutSuccess() {

    }
}
