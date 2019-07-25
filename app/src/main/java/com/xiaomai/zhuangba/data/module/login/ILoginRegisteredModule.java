package com.xiaomai.zhuangba.data.module.login;

import com.xiaomai.zhuangba.data.module.verification.IVerificationCodeModule;
import com.xiaomai.zhuangba.data.module.verification.IVerificationCodeView;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public interface ILoginRegisteredModule<V extends IVerificationCodeView> extends IVerificationCodeModule<V>{


    /**
     * 注册账号
     */
    void obtainRegistered();


    /**
     * 登录
     */
    void obtainLogin();


    /**
     * 忘记密码
     */
    void obtainForgetPassword();

    /**
     * 选择角色
     * @param code code
     */
    void obtainRole(int code);


    /**
     * 退出登录
     */
    void logout();
}
