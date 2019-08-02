package com.xiaomai.zhuangba.data.module.login;


import com.xiaomai.zhuangba.data.module.verification.IVerificationCodeView;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public interface ILoginRegisteredView extends IVerificationCodeView {

    /**
     * 第一次输入的密码
     *
     * @return 密码
     */
    String getPasswordOne();


    /**
     * 第二次输入的密码
     *
     * @return 密码
     */
    String getPasswordTwo();

    /**
     * 获取手机号
     *
     * @return 手机号
     */
    String getPhoneNumber();

    /**
     * 注册成功
     */
    void registeredSuccess();


    /**
     * 获取手机号
     *
     * @return 手机号
     */
    String getPhone();

    /**
     * 获取密码
     *
     * @return 密码
     */
    String getPass();

    /**
     * 选择角色
     */
    void startRoleSelection();


    /**
     *  师傅端
     */
    void startMasterWorker();

    /**
     * 雇主端
     */
    void startEmployer();


    /**
     * 密码修改成功
     */
    void forgetPasswordSuccess();

    /**
     * 登出
     */
    void logoutSuccess();

    /**
     * 师傅端 去认证
     */
    void startMasterAuthentication();

    /**
     * 雇主端 去认证
     */
    void startEmployerAuthentication();
}
