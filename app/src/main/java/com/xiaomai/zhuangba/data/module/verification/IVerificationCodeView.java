package com.xiaomai.zhuangba.data.module.verification;

import com.example.toollib.data.base.IBaseView;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public interface IVerificationCodeView extends IBaseView {

    /**
     * 开始计时
     */
    void startSingleVerificationCode();

    /**
     * 重置验证码计时
     */
    void retSingleVerificationCode();


    /**
     * 下一步
     */
    void next();
}