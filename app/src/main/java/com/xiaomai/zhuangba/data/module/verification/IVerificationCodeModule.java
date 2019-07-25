package com.xiaomai.zhuangba.data.module.verification;

import com.example.toollib.data.IBaseModule;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 *
 * 验证码
 */
public interface IVerificationCodeModule<V extends IVerificationCodeView> extends IBaseModule<V> {

    /**
     * 获取验证码
     * @param phoneNumber 手机号
     * @param type        register:注册账号;forgetPassword:忘记密码
     */
    void getAuthenticationCode(String phoneNumber, String type);


    /**
     * 验证验证码是否正确
     *
     * @param phoneNumber      手机号
     * @param verificationCode 验证码
     */
    void requestProvingVerificationCode(String phoneNumber, String verificationCode);


}
