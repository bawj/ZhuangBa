package com.xiaomai.zhuangba.data.module.login;

import android.text.TextUtils;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.spf.SPUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.application.PretendApplication;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.module.verification.VerificationCodeModule;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.example.toollib.util.spf.SpfConst;
import com.xiaomai.zhuangba.util.UMengUtil;
import com.xiaomai.zhuangba.util.Util;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public class LoginRegisteredModule extends VerificationCodeModule<ILoginRegisteredView> implements ILoginRegisteredModule<ILoginRegisteredView> {

    @Override
    public void obtainRegistered() {
        String passwordOne = mViewRef.get().getPasswordOne();
        String passwordTwo = mViewRef.get().getPasswordTwo();
        String phoneNumber = mViewRef.get().getPhoneNumber();
        if (!passwordOne.equals(passwordTwo)) {
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.password_mismatch_entered_twice));
        } else if (passwordOne.length() < 6) {
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.min_pass_length));
        } else if (passwordOne.length() > 20) {
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.max_pass_length));
        } else {
            RxUtils.getObservable(ServiceUrl.getUserApi().register(phoneNumber, passwordOne))
                    .compose(mViewRef.get().<HttpResult<Object>>bindLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>(mContext.get()) {
                        @Override
                        protected void onSuccess(Object response) {
                            mViewRef.get().registeredSuccess();
                        }
                    });
        }
    }


    @Override
    public void obtainForgetPassword() {
        String passwordOne = mViewRef.get().getPasswordOne();
        String passwordTwo = mViewRef.get().getPasswordTwo();
        String phoneNumber = mViewRef.get().getPhoneNumber();
        if (TextUtils.isEmpty(passwordOne)) {
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.please_input_pass));
        } else if (TextUtils.isEmpty(passwordTwo)) {
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.please_input_pass));
        } else if (!passwordOne.equals(passwordTwo)) {
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.password_mismatch_entered_twice));
        } else if (passwordOne.length() < 6) {
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.min_pass_length));
        } else if (passwordOne.length() > 20) {
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.max_pass_length));
        } else {
            RxUtils.getObservable(ServiceUrl.getUserApi().forgetPassword(phoneNumber, passwordOne))
                    .compose(mViewRef.get().<HttpResult<Object>>bindLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>(mContext.get()) {
                        @Override
                        protected void onSuccess(Object response) {
                            mViewRef.get().forgetPasswordSuccess();
                        }
                    });
        }
    }


    @Override
    public void obtainLogin() {
        //登录
        String phoneNumber = mViewRef.get().getPhone();
        String password = mViewRef.get().getPass();
        if (TextUtils.isEmpty(phoneNumber)) {
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.please_enter_your_cell_phone_number));
        } else if (!TextUtils.isEmpty(password) && password.length() < 6) {
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.min_pass_length));
        } else if (password.length() > 20) {
            mViewRef.get().showToast(PretendApplication.getInstance().getString(R.string.max_pass_length));
        } else {
            RxUtils.getObservable(ServiceUrl.getUserApi().login(phoneNumber, password))
                    .compose(mViewRef.get().<HttpResult<UserInfo>>bindLifecycle())
                    .subscribe(new BaseHttpRxObserver<UserInfo>(mContext.get()) {
                        @Override
                        protected void onSuccess(UserInfo userInfo) {
                            DBHelper.getInstance().getUserInfoDao().deleteAll();
                            DBHelper.getInstance().getUserInfoDao().insert(userInfo);
                            String token = userInfo.getToken();
                            if (!TextUtils.isEmpty(token)){
                                SPUtils.getInstance().put(SpfConst.TOKEN, token);
                            }
                            UMengUtil.alias(userInfo.getPhoneNumber());

                            int status = StaticExplain.CERTIFIED.getCode();
                            int authenticationStatue = userInfo.getAuthenticationStatue();
                            String role = userInfo.getRole();
                            //判断是否 选择过 用师傅端  用户端
                            if (TextUtils.isEmpty(role)) {
                                //未选择过 跳转到选择角色
                                mViewRef.get().startRoleSelection();
                            } else if (role.equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode())) && authenticationStatue != status) {
                                //师傅端 未认证
                                mViewRef.get().startMasterAuthentication();
                            } else if (userInfo.getRole().equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
                                //师傅端
                                mViewRef.get().startMasterWorker();
                            } else if (role.equals(String.valueOf(StaticExplain.EMPLOYER.getCode())) && authenticationStatue != status) {
                                //雇主端 未认证
                                mViewRef.get().startEmployerAuthentication();
                            } else if (userInfo.getRole().equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
                                //雇主端
                                mViewRef.get().startEmployer();
                            }
                        }
                    });
        }

    }


    @Override
    public void obtainRole(int role) {
        RxUtils.getObservable(ServiceUrl.getUserApi().role(String.valueOf(role)))
                .compose(mViewRef.get().<HttpResult<UserInfo>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<UserInfo>(mContext.get()) {
                    @Override
                    protected void onSuccess(UserInfo userInfo) {
                        String token = userInfo.getToken();
                        if (!TextUtils.isEmpty(token)){
                            SPUtils.getInstance().put(SpfConst.TOKEN, token);
                        }
                        DBHelper.getInstance().getUserInfoDao().deleteAll();
                        DBHelper.getInstance().getUserInfoDao().insert(userInfo);
                        //角色选择成功
                        if (userInfo.getRole().equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
                            //师傅端 去认证
                            mViewRef.get().startMasterAuthentication();
                        } else if (userInfo.getRole().equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
                            //雇主端 去认证
                            mViewRef.get().startEmployerAuthentication();
                        }
                    }
                });
    }

    @Override
    public void logout() {
        RxUtils.getObservable(ServiceUrl.getUserApi().logout())
                .compose(mViewRef.get().<HttpResult<String>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<String>(mContext.get()) {
                    @Override
                    protected void onSuccess(String response) {
                        Util.logout();
                        mViewRef.get().logoutSuccess();
                    }
                });
    }
}
